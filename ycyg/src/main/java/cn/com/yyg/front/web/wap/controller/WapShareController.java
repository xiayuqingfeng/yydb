package cn.com.yyg.front.web.wap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.dto.ContentDto;
import cn.com.yyg.front.service.common.CommonService;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/wap/share")
public class WapShareController {
	/** 用户 */
	@Autowired
	private UserDao userDao;
	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;
	/** 用户晒单 */
	@Autowired
	private YgShareDao ygShareDao;
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 商品 */
	@Autowired
	private ProductDao productDao;

	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String keyword) {
		long total = ygShareDao.countByAudit(true);
		modelMap.addAttribute("total", total);
		return "/wap/share/share";
	}

	@RequestMapping("/share_ajax/{pageNo}")
	public void list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo, Integer amount) {
		PageRequest pageRequest = new PageRequest(pageNo, amount);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgShareEntity> springPage = ygShareDao.findByAudit(true, pb);
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
	 * 分享详情
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param id
	 * @return
	 * @author lvzf 2016年9月28日
	 */
	@RequestMapping("/{id}")
	public String shareDetail(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {

		YgShareEntity share = ygShareDao.findOne(id);
		if (share == null) {
			return "redirect:/";
		}
		YgProductEntity ygProduct = ygProductDao.findOne(share.getYgProductId());
		share.setYgProduct(ygProduct);
		UserEntity userInfo = userDao.findOne(share.getUserId());
		modelMap.addAttribute("userInfo", userInfo);
		if (!share.getUserId().equals(userInfo.getId())) {
			return "redirect:/";
		}
		modelMap.addAttribute("share", share);
		// 商品
		ProductEntity op = productDao.findOne(share.getProductId());
		if (op == null) {
			return "redirect:/";
		}
		// 获取最新一期商品
		modelMap.addAttribute("newYgProduct", ygProductDao.findByProductIdAndPeriod(op.getId(), op.getPeriod()));
		return "/wap_member/member_share_detail";
	}
}
