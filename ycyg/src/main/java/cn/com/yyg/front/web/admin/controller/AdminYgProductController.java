package cn.com.yyg.front.web.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserYgEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.FileUploadVO;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.base.utils.UploadFileUtil;
import cn.com.yyg.front.dao.BrandDao;
import cn.com.yyg.front.dao.ContentDao;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.service.ProductService;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

/**
 * 后台管理云购商品
 * 
 * @author linwk 2016年8月26日
 * 
 */
@Controller
@RequestMapping("/admin/ygproduct")
public class AdminYgProductController {
	private Logger logger = LoggerFactory.getLogger(AdminYgProductController.class);
	/** 公共字典 */
	@Autowired
	private CommonService frontCommonService;
	/** 云购商品 */
	@Autowired
	private ProductDao productDao;
	/** 云购 云购商品 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 云购商品服务 */
	@Autowired
	private ProductService productService;
	/** 大字段 */
	@Autowired
	private ContentDao contentDao;
	/** 品牌分类 */
	@Autowired
	private BrandDao brandDao;
	/** 云购记录 */
	@Autowired
	private UserYgDao userYgDao;
	/**
	 * 云购商品管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		this.common(modelMap);
		return "/admin/ygproduct/product_list";
	}

	/**
	 * 搜索
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @param product
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/search.json")
	public void search(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, final PageRequestParamDTO pageReqeustParam, final YgProductEntity product) {
		Page<YgProductEntity> springPage = ygProductDao.findAll(new Specification<YgProductEntity>() {

			public Predicate toPredicate(Root<YgProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
 
				Path<Integer> t_status = root.get("status");
				Path<Integer> t_seqNo = root.get("seqNo");
				
				Path<String> t_cateId = root.get("cateId");
				Path<String> t_name = root.get("name");

				Path<Date> createTime = root.get("createTime");
				List<Predicate> p = Lists.newArrayList();
				//不显示删除的
				//p.add(cb.notEqual(t_status, YgProductStatusEnum.DEL.getValue()));
				//商品分类
				if(!StringUtils.isEmpty(product.getCateId())){
					p.add(cb.equal(t_cateId, product.getCateId()));
				}
				if (!StringUtils.isEmpty(pageReqeustParam.getSearchText())) {
					p.add(cb.like(t_name, "%" + pageReqeustParam.getSearchText() + "%"));
				}
				if (p != null) {
					query.where(p.toArray(new Predicate[] {})); // 这里可以设置任意条查询条件
				}
				List<Order> orders = Lists.newArrayList();
				orders.add(cb.asc(t_status));
				orders.add(cb.desc(t_seqNo));
				orders.add(cb.desc(createTime));
				query.orderBy(orders);
				return null;
			}
		}, pageReqeustParam.buildSpringDataPageRequest());
		cn.com.easy.utils.Page<YgProductEntity> page = PageUtils.getPage(springPage);
		/*
		 * Table<String, Long, SysDictEntity>
		 * dicts=frontCommonService.getAllDicts(); for(ProductEntity
		 * p:page.getResult()){
		 * 
		 * }
		 */
		ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
	}

	/**
	 * 商品列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @param name
	 * @author linwk 2016年8月27日
	 */
	@RequestMapping("/product.json")
	public void product(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam, String name) {
		try {
			if (StringUtils.isBlank(name)) {
				throw new BusinessException("商品关键词为空");
			}
			Page<ProductEntity> springPage = null;
			pageReqeustParam.setPageSize(10);
			springPage = productDao.findByName("%" + name + "%", pageReqeustParam.buildSpringDataPageRequest());
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, springPage.getContent()));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "云购管理获取商品信息异常"));
		}
	}
	/**添加商品
	 * 
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param id
	 *@return
	 * @author linwk 2016年8月27日
	 */
	@RequestMapping("/addProduct/{id}")
	public void addProduct(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		ProductEntity product = productDao.findOne(id);
		if (product == null) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "该商品不存在"));
		}
		ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, product));
	}


	/**
	 * 进入云购商品添加页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/add")
	public String add(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		common(modelMap);
		return "/admin/ygproduct/product_edit";
	}

	/**
	 * 进入云购商品编辑页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author lvzf 2016年8月4日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		YgProductEntity product = ygProductDao.findOne(id);
		if (product == null) {
			return this.add(modelMap, req, res);
		}
		modelMap.addAttribute("product", product);

		common(modelMap);
		return "/admin/ygproduct/product_edit";
	}

	/**
	 * 
	 * @param modelMap
	 * @author linwk 2016年8月26日
	 */
	private void common(ModelMap modelMap) {
		Table<Long, Long, SysDictEntity> tables=frontCommonService.getAllDicts();
		List<SysDictEntity> cateList=frontCommonService.getAllChilds(tables, SysDictEntity.ROOT_PRODUCT, 0); 
		// 商品分类
		 modelMap.addAttribute("cateList", cateList);
	}

	/**
	 * 保存云购商品
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param productEntity
	 * @param bxzrDatas
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, YgProductEntity productEntity) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			YgProductEntity product = new YgProductEntity();
			if (productEntity.getId() != null) {
				// 更新
				product = ygProductDao.findOne(productEntity.getId());
				if (product == null) {
					throw new BusinessException("ID无效");
				}
				if(product.getStatus()==YgProductStatusEnum.END.getValue()){
					throw new BusinessException(YgProductStatusEnum.END.getName()+",不能编辑");
				}
			} else {
				// 新增
				product.setCreateBy(user.getUserName());
				product.setCreateById(user.getId());
			}
			product.setLastModifyBy(user.getUserName());
			product.setName(productEntity.getName());
			product.setTitle(productEntity.getTitle()); 
			product.setRecommend(productEntity.isRecommend()); 
			product.setLogoPath(productEntity.getLogoPath());
			product.setSeqNo(productEntity.getSeqNo());
			product.setWinNo(productEntity.getWinNo());
			if(productEntity.getWinNo()!=null){
				if(productEntity.getWinNo().longValue()>(product.getTotalNum()+YgProductEntity.INIT_BUY_NO) ||
						productEntity.getWinNo().longValue()<YgProductEntity.INIT_BUY_NO){
					throw new BusinessException("指定号码要在("+YgProductEntity.INIT_BUY_NO
							+","+(product.getTotalNum()+YgProductEntity.INIT_BUY_NO)+"]之间");
				}
				product.setZdWin(true);
			}else{
				product.setZdWin(false);
			}
			ygProductDao.save(product);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, " 保存成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error(" 保存云购商品错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, " 保存错误"));
		}
	}
	@RequestMapping("/delete.json")
	public void delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,Long id) {
		try {
			YgProductEntity product = ygProductDao.findOne(id);
			if(product.getStatus()!=YgProductStatusEnum.ING.getValue() ||  product.getUsedNum()>0){
				throw new BusinessException("只能删除未开始购买的记录");
			}
			product.setStatus(YgProductStatusEnum.DEL.getValue());
			ygProductDao.save(product);		
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, " 删除成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error(" 删除云购商品错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除云购商品错误"));
		}
	}
	/**
	 * 上传图片
	 * 
	 * @param xxtkFiles
	 * @param req
	 * @param res
	 * @throws Exception
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping(value = "/uploadXxtkFile", method = RequestMethod.POST)
	public void uploadXxtkFile(@RequestParam(value = "photos") MultipartFile[] xxtkFiles, HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			// 细节条款文件存放路径
			String storageFilePath = "product/xxtl/" + DateUtil.getNowDateToString("yyyy") + "/" + DateUtil.getNowDateToString("MM") + "/" + DateUtil.getNowDateToString("dd");
			List<AccessoryEntity> files = Lists.newArrayList();
			for (int i = 0; i < xxtkFiles.length; i++) {
				FileUploadVO fileUpload = new FileUploadVO();
				// 图片文件判断
				fileUpload.setFile(xxtkFiles[i]);
				fileUpload.setStorageFilePath(storageFilePath);
				fileUpload.setStorageFileName(UUID.randomUUID().toString());
				// 上传文件到硬盘
				AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
				acc.setInfo(fileUpload.getFile().getOriginalFilename());
				files.add(acc);
			}
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, files));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error(" 上传图片错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, " 上传图片错误"));
		}
	}

	/**
	 * 删除图片
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param accessory
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/deleteXxtkFile.json")
	public void deleteXxtkFile(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, AccessoryEntity accessory) {
		try {
			if (StringUtils.isBlank(accessory.getPath())) {
				throw new BusinessException("文件path为空");
			}
			if (StringUtils.isBlank(accessory.getName())) {
				throw new BusinessException("文件name为空");
			}
			UploadFileUtil.deleteFile(accessory);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "删除成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error(" 删除图片错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, " 删除图片错误"));
		}
	}

	/**
	 * 购买记录
	 *@param modelMap
	 *@param req
	 *@param res
	 *@return
	 * @author lvzf 2016年10月17日
	 */
	@RequestMapping("/buyRecord/{id}.html")
	public String buyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long id) {
		YgProductEntity product = ygProductDao.findOne(id);
		modelMap.addAttribute("ygProduct", product);
		this.common(modelMap);
		return "/admin/ygproduct/buy_record";
	}
	/**
	 * 购买记录
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pageRequest
	 *@param id
	 * @author lvzf 2016年10月17日
	 */
	@RequestMapping("/buyRecord.json")
	public void buyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,PageRequest pageRequest,Long id) {
		pageRequest.setPageSize(10);
		pageRequest.setOrderBy("buyDateLong");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage=userYgDao.findByYgProductId(id,  pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage); 
		ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
	}
}
