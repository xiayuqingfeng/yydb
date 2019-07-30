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
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.BrandEntity;
import cn.com.yyg.base.entity.ContentEntity;
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.FileUploadVO;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.base.utils.UploadFileUtil;
import cn.com.yyg.front.dao.BrandDao;
import cn.com.yyg.front.dao.ContentDao;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dto.ProductPhotoDto;
import cn.com.yyg.front.service.ProductService;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

/**
 * 后台管理商品
 * 
 * @author linwk 2016年8月26日
 * 
 */
@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
	private Logger logger = LoggerFactory.getLogger(AdminProductController.class);
	/** 公共字典 */
	@Autowired
	private CommonService frontCommonService;
	/** 商品(险种) */
	@Autowired
	private ProductDao productDao;
	/** 商品服务 */
	@Autowired
	private ProductService productService;
	/** 大字段 */
	@Autowired
	private ContentDao contentDao; 
	/** 品牌分类 */
	@Autowired
	private BrandDao brandDao;

	/**
	 * 商品管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		common(modelMap);
		return "/admin/product/product_list";
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
	public void search(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, final PageRequestParamDTO pageReqeustParam, final ProductEntity product) {
		Page<ProductEntity> springPage = productDao.findAll(new Specification<ProductEntity>() {

			public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Path<Integer> deleteStatus = root.get("deleteStatus");
				Path<String> t_cateId = root.get("cateId");
				Path<String> t_name = root.get("name");

				Path<Date> createTime = root.get("createTime");
				List<Predicate> p = Lists.newArrayList();
				p.add(cb.equal(deleteStatus, false));
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
				orders.add(cb.desc(createTime));
				query.orderBy(orders);
				return null;
			}
		}, pageReqeustParam.buildSpringDataPageRequest());
		cn.com.easy.utils.Page<ProductEntity> page = PageUtils.getPage(springPage);

		ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
	}

	/**
	 * 进入商品添加页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/add")
	public String add(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		ProductEntity product = new ProductEntity();
		product.setStatus(ProductEntity.STATUS_UP);
		product.setSinglePrice(1);
		modelMap.addAttribute("product", product);
		common(modelMap);
		return "/admin/product/product_edit";
	}

	/**
	 * 进入商品编辑页
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
		ProductEntity product = productDao.findOne(id);
		if (product == null) {
			return this.add(modelMap, req, res);
		}
		// 投保案例
		if (product.getContentId() != null) {
			ContentEntity content = contentDao.findOne(product.getContentId());
			if (content != null) {
				product.setContent(content.getContent());
			}
		}
		modelMap.addAttribute("product", product);

		common(modelMap);
		return "/admin/product/product_edit";
	}

	/**
	 * 
	 * @param modelMap
	 * @author linwk 2016年8月26日
	 */
	private void common(ModelMap modelMap) {
		this.setCateList(modelMap);
	}

	/**
	 * 保存商品
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param productEntity
	 * @param bxzrDatas
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, ProductEntity productEntity) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			productService.save(user, productEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, " 保存成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error(" 保存商品错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, " 保存错误"));
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
			List<ProductPhotoDto> files = Lists.newArrayList();
			for (int i = 0; i < xxtkFiles.length; i++) {
				FileUploadVO fileUpload = new FileUploadVO();
				// 图片文件判断
				fileUpload.setFile(xxtkFiles[i]);
				fileUpload.setStorageFilePath(storageFilePath);
				fileUpload.setStorageFileName(UUID.randomUUID().toString());
				// 上传文件到硬盘
				AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
				acc.setInfo(fileUpload.getFile().getOriginalFilename());
				ProductPhotoDto p=new ProductPhotoDto();
				p.setPhotoPath(acc.getPath()+"/"+acc.getName());
				p.setRemark(acc.getInfo());
				files.add(p);
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
	 * 设置分类list到modelMap中
	 * 
	 * @param modelMap
	 * @author linwk 2016年8月26日
	 */
	private void setCateList(ModelMap modelMap) {
		Table<Long, Long, SysDictEntity> tables=frontCommonService.getAllDicts();
		List<SysDictEntity> cateList=frontCommonService.getAllChilds(tables, SysDictEntity.ROOT_PRODUCT, 0); 
		// 商品分类
		 modelMap.addAttribute("cateList", cateList);
		// 品牌
		List<BrandEntity> brandList = brandDao.findAll();
		modelMap.addAttribute("brandList", brandList);
	}
 

}
