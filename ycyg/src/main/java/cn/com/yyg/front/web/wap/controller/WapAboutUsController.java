package cn.com.yyg.front.web.wap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 关于我们
 * 
 * @author nibili 2016年6月10日
 * 
 */
@Controller
@RequestMapping("/wap/pub/aboutus")
public class WapAboutUsController {

	/**
	 * 首页
	 * 
	 * @return
	 * @author nibili 2016年6月10日
	 */
	@RequestMapping
	public String index() {
		return "/wap/about_us/about_us";
	}
}
