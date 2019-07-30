package cn.com.yyg.front.web.wap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 异常页面控制器
 * 
 * @author nibili 2015年11月2日
 * 
 */
@Controller
@RequestMapping("/wap")
public class WapErrorController {

	@RequestMapping("/403")
	public String error403(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/error/403";
	}

	@RequestMapping("/404")
	public String error404(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/error/404";
	}

	@RequestMapping("/500")
	public String error500(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/error/500";
	}
	@RequestMapping("/error/custom")
	public String errorCustom(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/error/custom";
	}
}
