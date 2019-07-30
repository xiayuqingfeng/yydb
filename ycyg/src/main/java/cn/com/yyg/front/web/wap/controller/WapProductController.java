package cn.com.yyg.front.web.wap.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.ContentEntity;
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserYgEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.ContentDao;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dao.SysDictDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.dto.ContentDto;
import cn.com.yyg.front.service.FrontIndexService;
import cn.com.yyg.front.service.YgBuyService;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;

/**
 * 商品
 * 
 * @author lvzf 2016年8月20日
 * 
 */
@Controller
@RequestMapping("/wap/product")
public class WapProductController {

	private Logger logger = LoggerFactory.getLogger(WapProductController.class);
	@Autowired
	private FrontIndexService frontIndexService;
	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;
	/** 商品 */
	@Autowired
	private ProductDao productDao;
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;

	/** 云购记录 */
	@Autowired
	private UserYgDao userYgDao;
	/** 分享记录 */
	@Autowired
	private YgShareDao ygShareDao;
	/** 云购 */
	@Autowired
	private YgBuyService ygBuyService;
	/** 正文 */
	@Autowired
	private ContentDao contentDao;
	/** 字典分类 */
	@Autowired
	private SysDictDao sysDictDao;

	/** 用户 */
	@Autowired
	private UserDao userDao;

	/**
	 * 商品列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年8月22日
	 */
	@RequestMapping
	public String list_index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String keyword) {
		return this.list(modelMap, req, res, 0L, keyword);
	}

	/**
	 * 商品列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年8月22日
	 */
	@RequestMapping("/list/{cateId:\\d+}")
	public String list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long cateId, String keyword) {
		return this.list(modelMap, req, res, cateId + "-0-1", keyword);
	}

	/**
	 * 商品列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param list
	 *            分类id-品牌id-排序id
	 * @param pageNo
	 * @return
	 * @author lvzf 2016年8月22日
	 */
	@RequestMapping("/list/{list}-{pageNo}")
	public String list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable String list, String keyword) {
		// 商品分类
		List<SysDictEntity> pcates = sysDictDao.findByParentId(SysDictEntity.ROOT_PRODUCT);
		modelMap.addAttribute("pcates", pcates);
		String ids[] = list.split("-");
		if (ids.length < 2) {
			return this.list_index(modelMap, req, res, null);
		}
		String cateId = ids[0];
		modelMap.addAttribute("cateId", cateId);
		String orderType = ids[ids.length - 1];
		modelMap.addAttribute("orderType", orderType);

		PageRequest pageRequest = new PageRequest(1, 20);
		// 人气商品seqno
		if ("1".equals(orderType)) {
			pageRequest.setOrderBy("seqNo");
			pageRequest.setOrderDir("desc");
		} else if ("2".equals(orderType)) {// 剩余人次
			pageRequest.setOrderBy("leaveNum");
			pageRequest.setOrderDir("desc");
		} else if ("3".equals(orderType)) {// 最新
			pageRequest.setOrderBy("createTime");
			pageRequest.setOrderDir("desc");
		} else if ("4".equals(orderType)) {// 总需人次升序
			pageRequest.setOrderBy("totalNum");
			pageRequest.setOrderDir("asc");
		} else if ("4".equals(orderType)) {// 总需人次降序
			pageRequest.setOrderBy("totalNum");
			pageRequest.setOrderDir("desc");
		}
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgProductEntity> springPage = null;

		if (StringUtils.isNoneBlank(keyword)) {// 按名称查找
			springPage = ygProductDao.findByStatusAndNameLike(YgProductStatusEnum.ING.getValue(), "%" + keyword + "%", pb);
		} else if ("0".equals(cateId)) { // 全部
			springPage = ygProductDao.findByStatus(YgProductStatusEnum.ING.getValue(), pb);
		} else {
			springPage = ygProductDao.findByStatusAndCateId(YgProductStatusEnum.ING.getValue(), cateId, pb);
		}
		cn.com.easy.utils.Page<YgProductEntity> page = PageUtils.getPage(springPage);
		modelMap.addAttribute("page", page);
		// 查询
		return "/wap/product/product_list";
	}

	@RequestMapping("/listAjax/{list}-{pageNo}")
	public String listJson(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable String list, String keyword, @PathVariable int pageNo) {
		// 商品分类
		List<SysDictEntity> pcates = sysDictDao.findByParentId(SysDictEntity.ROOT_PRODUCT);
		modelMap.addAttribute("pcates", pcates);
		String ids[] = list.split("-");
		if (ids.length < 2) {
			return this.list_index(modelMap, req, res, null);
		}
		String cateId = ids[0];
		modelMap.addAttribute("cateId", cateId);
		String orderType = ids[ids.length - 1];
		modelMap.addAttribute("orderType", orderType);

		PageRequest pageRequest = new PageRequest(pageNo, 20);
		// 人气商品seqno
		if ("1".equals(orderType)) {
			pageRequest.setOrderBy("seqNo");
			pageRequest.setOrderDir("desc");
		} else if ("2".equals(orderType)) {// 剩余人次
			pageRequest.setOrderBy("leaveNum");
			pageRequest.setOrderDir("desc");
		} else if ("3".equals(orderType)) {// 最新
			pageRequest.setOrderBy("createTime");
			pageRequest.setOrderDir("desc");
		} else if ("4".equals(orderType)) {// 总需人次升序
			pageRequest.setOrderBy("totalNum");
			pageRequest.setOrderDir("asc");
		} else if ("4".equals(orderType)) {// 总需人次降序
			pageRequest.setOrderBy("totalNum");
			pageRequest.setOrderDir("desc");
		}
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgProductEntity> springPage = null;

		if (StringUtils.isNoneBlank(keyword)) {// 按名称查找
			springPage = ygProductDao.findByStatusAndNameLike(YgProductStatusEnum.ING.getValue(), "%" + keyword + "%", pb);
		} else if ("0".equals(cateId)) { // 全部
			springPage = ygProductDao.findByStatus(YgProductStatusEnum.ING.getValue(), pb);
		} else {
			springPage = ygProductDao.findByStatusAndCateId(YgProductStatusEnum.ING.getValue(), cateId, pb);
		}
		cn.com.easy.utils.Page<YgProductEntity> page = PageUtils.getPage(springPage);
		modelMap.addAttribute("page", page);
		// 查询
		return "/wap/product/product_list_json";
	}

	@RequestMapping("/search")
	public String search(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Integer pageNo, String keyword) {
		return this.list_index(modelMap, req, res, keyword);
	}

	/**
	 * 商品详情
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author lvzf 2016年8月22日
	 */
	@RequestMapping("/{id}")
	public String view(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		YgProductEntity product = ygProductDao.findOne(id);
		modelMap.addAttribute("product", product);
		if (product == null) {
			return "/error/404";
		}
		// 期数列表
		modelMap.addAttribute("periods", ygProductDao.findAllByProductId(product.getProductId()));
		// 商品
		ProductEntity op = productDao.findOne(product.getProductId());
		if (op != null) {
			// 获取正文
			if (op.getContentId() != null) {
				ContentEntity ct = contentDao.findOne(op.getContentId());
				if (ct != null) {
					op.setContent(ct.getContent());
				}
			}
		}
		modelMap.addAttribute("oProduct", op);
		// 本期我的云购记录
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 中奖者
		if (product.getWinUserId() != null) {
			user = userDao.findOne(product.getWinUserId());
		}
		if (user != null) {
			List<UserYgEntity> myBuyRecords = userYgDao.findByYgProductIdAndBuyUserId(product.getId(), user.getId());
			long myTotalBuyNums = 0;
			for (UserYgEntity re : myBuyRecords) {
				myTotalBuyNums += re.getBuyNum();
			}
			List<String> mBuyNos = Lists.newArrayList();
			for (UserYgEntity re : myBuyRecords) {
				mBuyNos.addAll(Lists.newArrayList(re.getBuyNos().split(",")));
			}
			if (mBuyNos.size() >= 9) {
				modelMap.addAttribute("mBuyNos", mBuyNos.subList(0, 9));
			} else {
				modelMap.addAttribute("mBuyNos", mBuyNos);
			}
			modelMap.addAttribute("myTotalBuyNums", myTotalBuyNums);
			modelMap.addAttribute("myBuyRecords", myBuyRecords);
		}
		// 获取前50条购买记录
/*		List<UserYgEntity> buyRecords = ygBuyService.getBuyRecords(product, 55);

		long totals = 0;
		for (int i = 0; i < buyRecords.size(); i++) {
			if (i > 49) {
				break;
			}
			String dl = DateUtil.getDateToString(new Date(buyRecords.get(i).getBuyDateLong().longValue()), "HHmmssSSS");
			totals += Long.parseLong(dl);

		}
		modelMap.addAttribute("buyDateTotals", totals);
		modelMap.addAttribute("buyRecords", buyRecords);*/

		modelMap.addAttribute("nowDate", DateUtil.getNowDateToString("yyyyMMdd"));
		// 进行中
		if (product.getStatus() == YgProductStatusEnum.ING.getValue()) {
			// 获取上一期商品
			if (product.getPreYgProductId() != null) {
				modelMap.addAttribute("perYgProduct", ygProductDao.findOne(product.getPreYgProductId()));
			}
			return "/wap/product/product_detail_ing";
		}
		// 即将揭晓
		else if (product.getStatus() == YgProductStatusEnum.PRE.getValue() || product.getStatus() == YgProductStatusEnum.DO.getValue()) {
			return "/wap/product/product_detail_pre";
		}
		// 已揭晓
		else if (product.getStatus() == YgProductStatusEnum.END.getValue()) {
			// 获取最新一期商品
			modelMap.addAttribute("newYgProduct", ygProductDao.findByProductIdAndPeriod(op.getId(), op.getPeriod()));
			// 中奖号码拆分
			List<Object> winNoChars = Lists.newArrayList();
			if (product.getWinNo() != null) {
				for (int i = 0; i < product.getWinNo().toString().length(); i++) {
					winNoChars.add(product.getWinNo().toString().charAt(i));
				}
				modelMap.addAttribute("winNoChars", winNoChars);
			}
			// 获取上一期商品
			if (product.getPreYgProductId() != null) {
				modelMap.addAttribute("perYgProduct", ygProductDao.findOne(product.getPreYgProductId()));
			}
			return "/wap/product/product_detail_end";
		}
		return "/wap/product/product_detail_ing";
	}

	/**
	 * 商品详情
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年9月22日
	 */
	@RequestMapping("/{id}/info")
	public String info(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		YgProductEntity product = ygProductDao.findOne(id);
		modelMap.addAttribute("product", product);
		// 商品
		ProductEntity op = productDao.findOne(product.getProductId());
		if (op != null) {
			// 获取正文
			if (op.getContentId() != null) {
				ContentEntity ct = contentDao.findOne(op.getContentId());
				if (ct != null) {
					op.setContent(ct.getContent());
				}
			}
		}
		modelMap.addAttribute("oProduct", op);
		return "/wap/product/product_detail_info";
	}

	/**
	 * 往期揭晓
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年9月23日
	 */
	@RequestMapping("/{id}/win")
	public String win(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		modelMap.addAttribute("productId", id);
		return "/wap/product/product_detail_win";
	}

	/**
	 * 往期揭晓
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年9月23日
	 */
	@RequestMapping("/{id}/win/{pageNo}")
	public void winAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, @PathVariable int pageNo) {
		// 当前商品
		YgProductEntity product = ygProductDao.findOne(id);
		if (product == null) {
			return;
		}
		// 商品
		ProductEntity op = productDao.findOne(product.getProductId());
		if (op == null) {
			return;
		}
		PageRequest pageRequest = new PageRequest(pageNo, 10);
		// 期数列表
		Page<YgProductEntity> periods = ygProductDao.findAllByProductId(product.getProductId(), PageRequestUtils.buildSpringDataPageRequest(pageRequest));

		List<UserEntity> users = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(periods.getContent())) {
			for (YgProductEntity ygProductEntity : periods.getContent()) {
				UserEntity user = new UserEntity();
				// 中奖者
				// 每一期的中奖者为1个
				if (ygProductEntity.getWinUserId() != null) {
					user = userDao.findOne(ygProductEntity.getWinUserId());
					users.add(user);
				}
			}
		}

		String html = "";
		List<ContentDto> contents = Lists.newArrayList();
		for (YgProductEntity ygProductEntity : periods) {
			if (ygProductEntity.getWinNo() != null) {
				// 日期
				html += "	<li>	";
				html += "	    <div class=\"win-title\"><a href=\"/product/" + ygProductEntity.getId() + ".html\"><span class=\"color\">第" + ygProductEntity.getPeriod()
						+ "期</span>( 揭晓时间：" + ygProductEntity.getCreateTime() + " )</a></div>	";
				html += "	    <div class=\"win-cont ui-clr\">	";
				html += "	        <div class=\"win-pic\"><a href=\"/user/" + ygProductEntity.getWinUserId() + "#dbCod\"><img src=\"" + ygProductEntity.getLogoPath()
						+ "\"></a></div>	";
				html += "	        <div class=\"win-detail\">	";
				html += "	            <p class=\"to\">获奖者：<a class=\"blue\" href=\"/user/" + ygProductEntity.getWinUserId() + "#dbCod\">" + ygProductEntity.getWinUserNickName()
						+ "</a></p>	";
				// html +=
				// "	            <p class=\"to gray\">( 河南省郑州市 IP：123.149.214.15 )</p>	";
				html += "	            <p>幸运号码：<span class=\"color\">" + ygProductEntity.getWinNo() + "</span> </p>	";
				html += "	            <p>本期参与：" + ygProductEntity.getWinUserBuyNum() + "人次</p>	";
				html += "	            <p>云购时间：" + DateUtil.getDateToString(ygProductEntity.getWinUserBuyDate(), DateUtil.YMDhhmmss) + "</p>	";
				html += "	        </div>	";
				html += "	    </div>	";
				html += "	</li>	";
			}
		}
		ContentDto contentDto = new ContentDto();
		contentDto.setContent(html);
		contents.add(contentDto);
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contentDto));
	}

	/**
	 * 商品购买记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param pageNo
	 * @return
	 * @author lvzf 2016年9月4日
	 */
	@RequestMapping("/{id}/buyRecord")
	public String buyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, Integer pageNo) {
		modelMap.addAttribute("productId", id);
		return "/wap/product/product_buy_record";
	}

	/**
	 * 计算结果
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param pageNo
	 * @return
	 * @author linwk 2016年9月29日
	 */
	@RequestMapping("/{id}/luck")
	public String buyLuck(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, Integer pageNo) {
		modelMap.addAttribute("productId", id);
		YgProductEntity product = ygProductDao.findOne(id);
		modelMap.addAttribute("product", product);
		// 商品
		ProductEntity op = productDao.findOne(product.getProductId());
		if (op != null) {
			// 获取正文
			if (op.getContentId() != null) {
				ContentEntity ct = contentDao.findOne(op.getContentId());
				if (ct != null) {
					op.setContent(ct.getContent());
				}
			}
		}
		modelMap.addAttribute("oProduct", op);
		// 获取前50条购买记录
		List<UserYgEntity> buyRecords = ygBuyService.getBuyRecords(product, 55);

		long totals = 0;
		for (int i = 0; i < buyRecords.size(); i++) {
			if (i > 49) {
				break;
			}
			String dl = DateUtil.getDateToString(new Date(buyRecords.get(i).getBuyDateLong().longValue()), "HHmmssSSS");
			totals += Long.parseLong(dl);

		}
		modelMap.addAttribute("buyDateTotals", totals);
		modelMap.addAttribute("buyRecords", buyRecords);
		return "/wap/product/product_detail_luck";
	}

	/**
	 * 商品购买记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param pageNo
	 * @return
	 * @author lvzf 2016年9月4日
	 */
	@RequestMapping("/{id}/buyRecord/{pageNo}")
	public void buyRecordAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, @PathVariable int pageNo) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		PageRequest pageRequest = new PageRequest(pageNo, 3);
		pageRequest.setOrderBy("buyDateLong");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByYgProductId(id, pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);

		// 按天分组
		Map<String, List<UserYgEntity>> resultMap = new LinkedHashMap<String, List<UserYgEntity>>();
		for (UserYgEntity u : page.getResult()) {
			String dl = DateUtil.getDateToString(new Date(u.getBuyDateLong()), DateUtil.YMD);
			List<UserYgEntity> tl = resultMap.get(dl);
			if (tl == null) {
				tl = Lists.newArrayList();
			}
			tl.add(u);
			resultMap.put(dl, tl);
		}
		String html = "";
		List<ContentDto> contents = Lists.newArrayList();
		for (Map.Entry<String, List<UserYgEntity>> map : resultMap.entrySet()) {
			// 日期
			html += "<div class=\"day-box\">";
			html += "<div class=\"record-time\">" + map.getKey() + "</div>";
			html += "<ul class=\"record-list\">";
			// 数组
			List<UserYgEntity> lists = resultMap.get(map.getKey());
			if (CollectionUtils.isNotEmpty(page.getResult())) {
				for (UserYgEntity userYgEntity : lists) {
					html += "<li>";
					String buyUserLogoPath = userYgEntity.getBuyUserLogoPath();
					if (StringUtils.contains(buyUserLogoPath, "http") == false) {
						buyUserLogoPath = "/" + buyUserLogoPath;
					}
					html += "<div class=\"pic\"><a href=\"/user/" + userYgEntity.getBuyUserId() + "\"><img src=\"" + buyUserLogoPath + "\"> </a> </div>";
					html += "<div class=\"text\">";
					html += "<p class=\"to\"><a class=\"blue\" href=\"/user/" + userYgEntity.getBuyUserId() + "\">" + userYgEntity.getBuyUserNickName() + "</a> </p>";

					html += "<p class=\"to\">(" + userYgEntity.getIpAddr() + " IP：" + userYgEntity.getClientIp() + ")</p>";
					html += "<p>参与了<span class=\"color\">" + userYgEntity.getBuyNum() + "</span>人次 "
							+ DateUtil.getDateToString(new Date(userYgEntity.getBuyDateLong()), DateUtil.Hhmmss) + "</p>";
					// +userYgEntity.getBuyDate()+ "</p>";
					html += "</div>";
					html += "</li>";
				}
			}
			html += "</ul>";
			html += "</div>";
		}
		ContentDto contentDto = new ContentDto();
		contentDto.setContent(html);
		contents.add(contentDto);
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contentDto));
	}

	/**
	 * 商品分享记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param pageNo
	 * @return
	 * @author lvzf 2016年9月4日
	 */
	@RequestMapping("/{id}/shareRecord")
	public String shareRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, Integer pageNo) {
		modelMap.addAttribute("productId", id);
		return "/wap/product/product_share_record";
	}

	/**
	 * 商品分享记录--分页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param pageNo
	 * @return
	 * @author linwk 2016年9月22日
	 */
	@RequestMapping("/{id}/shareRecord/{pageNo}")
	public void shareRecordAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, @PathVariable int pageNo) {
		if (pageNo > 0) {
			pageNo--;
		}
		PageRequest pageRequest = new PageRequest(pageNo, 50);
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		if (id == null) {
			return;
		}
		// 找到productId
		YgProductEntity ygProductEntity = ygProductDao.findOne(id);
		if (ygProductEntity.getProductId() == null) {
			return;
		}
		Page<YgShareEntity> springPage = ygShareDao.findByProductIdAndAudit(ygProductEntity.getProductId(), pb);
		cn.com.easy.utils.Page<YgShareEntity> page = PageUtils.getPage(springPage);
		for (YgShareEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}

		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (YgShareEntity ygShareEntity : page.getResult()) {
				String photoPath = "";
				if (CollectionUtils.isNotEmpty(ygShareEntity.getPhotoList())) {
					photoPath = ygShareEntity.getPhotoList().get(0);
				}
				String html = "<div class=\"item\" id=\"more_element_1\">";
				html += "<p class=\"title\"><a href=\"/share/" + ygShareEntity.getId() + "\">";
				html += "" + ygShareEntity.getYgProduct().getName() + "";
				html += "<span class=\"color03\">";
				html += "（第" + ygShareEntity.getPeriod() + "期）";
				html += "</span></a></p> <div class=\"cont ui-clr\"> <div class=\"pic\">";
				html += "<a href=\"/share/" + ygShareEntity.getId() + "\">";
				html += "<img src=\"" + photoPath + "\" alt=\"" + ygShareEntity.getTitle() + "\"></a></div>";
				html += "<div class=\"txt\"> <p class=\"mb5\"><b style=\"color:#000\">" + ygShareEntity.getTitle() + "</b></p>";
				html += "<a href=\"/share/" + ygShareEntity.getId() + "\">" + ygShareEntity.getContent() + "</a> </div>";
				html += "</div> <div class=\"author ui-clr\">";
				html += "<a href=\"/share/" + ygShareEntity.getId() + "\" class=\"color04\">" + ygShareEntity.getId() + "</a>";
				html += "<time datetime=\"" + ygShareEntity.getCreateTime() + "\">" + ygShareEntity.getCreateTime() + "</time> </div></div>";
				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}

	/**
	 * 按期数分页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param pageNo
	 * @return
	 * @author lvzf 2016年9月8日
	 */
	@RequestMapping("/{id}/periodPage")
	public String periodPage(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, Integer pageNo) {
		YgProductEntity product = ygProductDao.findOne(id);
		if (product == null) {
			return "/error/404";
		}
		modelMap.addAttribute("product", product);
		if (pageNo == null) {
			pageNo = 1;
		}
		PageRequest pageRequest = new PageRequest(pageNo, 18);
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgProductEntity> springPage = ygProductDao.findAllByProductId(product.getProductId(), pb);
		cn.com.easy.utils.Page<YgProductEntity> page = PageUtils.getPage(springPage);
		modelMap.addAttribute("page", page);
		return "/wap/product/product_period_page";
	}

	@RequestMapping("/lottery/{id}")
	public void calLottery(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		// 马上计算
		YgProductEntity product = ygProductDao.findOne(id);
		ygBuyService.calWinNo(product);
	}
}
