package cn.com.yyg.front.web.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户管理后台
 * 
 * @author nibili 2016年5月20日
 * 
 */
@Controller
@RequestMapping("/admin")
public class AdminIndexController {
	/**
	 * 首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月20日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		//return "/admin/index/index";
		return "redirect:/admin/members#3001";
	}

}
