package cn.com.yyg.front.web.pub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/doc" })
public class PubIntroduceController {
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// return "/pub/index/doc";
		return "/about/1y/index";
	}
}