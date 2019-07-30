package cn.com.yyg.front.web.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公告管理首页
 * 
 * @author nibili 2016年5月24日
 * 
 */
@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeController {

	/**
	 * 公告管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月24日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/notice/notice";
	}
}
