package cn.com.yyg.front.web.wap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.commons.YygConstants;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dto.IndexFloorDto;
import cn.com.yyg.front.service.FrontIndexService;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.utils.WeixinUtils;

import com.google.common.collect.Lists;

/**
 * pc首页
 * 
 * @author lvzf 2016年8月20日
 * 
 */
@Controller
@RequestMapping("/wap")
public class WapIndexController {

	private Logger logger = LoggerFactory.getLogger(WapIndexController.class);
	@Autowired
	private FrontIndexService frontIndexService;
	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;
	@Autowired
	private UserDao userDao;

	/**
	 * 首页入口
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年8月20日
	 * @throws IOException
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String mobile) throws IOException {
		// 如果微信登录 自动登录
		String ua = ((HttpServletRequest) req).getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			UserEntity user = RequestUtils.getCurrentUser(req);
			// 需用户登录
			if (user == null) {
				res.sendRedirect("/login");
				return null;
			}
		}

		if (StringUtils.isNoneBlank(mobile)) {
			req.getSession().setAttribute("mobile", "mobile");
		}
		// 幻灯片
		modelMap.addAttribute("slides", frontIndexService.getWapIndexSlides(5));
		// 商品分类
		List<SysDictEntity> pcates = frontIndexService.getProductCates(7);
		modelMap.addAttribute("pcates", pcates);
		// 热门推荐
		modelMap.addAttribute("recomments", frontIndexService.getRecommendYgProduct(8));
		// 最新已揭晓
		// modelMap.addAttribute("newJieXiaos",frontIndexService.getNewJiexiaoed(5));
		// 新品上线
		modelMap.addAttribute("newProducts", frontIndexService.getNewYgProduct(4));
		// 显示楼层
		List<IndexFloorDto> floors = Lists.newArrayList();
		for (SysDictEntity c : pcates) {
			IndexFloorDto floor = new IndexFloorDto();
			BeanUtils.copyProperties(c, floor);
			floor.setProducts(frontIndexService.getYgProductByCateId(String.valueOf(c.getId()), 5));
			if (floor.getProducts().size() > 0) {
				floors.add(floor);
			}
		}
		modelMap.addAttribute("floors", floors);
		// 最新晒单
		modelMap.addAttribute("shares", frontIndexService.getNewYgShare(8));

		return "/wap/index/index";
	}

	@RequestMapping("/newJieXiaos.html")
	public String newJieXiaos(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 获取最新准备揭晓
		List<YgProductEntity> list = frontIndexService.getNewJiexiaoPre(5);
		if (list.size() < 5) {
			// 取最新已揭晓
			List<YgProductEntity> tt = frontIndexService.getNewJiexiaoed(5 - list.size());
			if (tt.size() > 0) {
				list.addAll(tt);
			}
		}
		modelMap.addAttribute("newJieXiaos", list);
		return "/wap/index/index_jiexiao_item";
	}

	/**
	 * 统计云购份数
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年8月20日
	 */
	@RequestMapping("/buyCount.html")
	public String buyCount(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		req.getSession().setAttribute(YygConstants.KEY_SESSION_BUY_NUMS, frontCommonService.totalBuyNums());
		return "redirect:/";
	}

	/**
	 * 统计云购份数
	 * 
	 * @param res
	 * @author lvzf 2016年8月20日
	 */
	@RequestMapping("/buyCount.json")
	public void buyCount(HttpServletRequest req, HttpServletResponse res) {
		try {
			String count = frontCommonService.totalBuyNums();
			req.getSession().setAttribute(YygConstants.KEY_SESSION_BUY_NUMS, count);
			ResponseOutputUtils.renderJson(res, count);
		} catch (Exception ex) {
			ResponseOutputUtils.renderJson(res, "");
			logger.error(ex.getMessage(), ex);
		}
	}
}
