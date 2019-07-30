/**
 * 
 */
package cn.com.yyg.front.web.pub.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.yyg.front.service.common.ImgService;

/**
 * 图片上传、访问控制器
 * 
 * @author nibili 2015年1月11日上午11:05:01
 * 
 */
@Controller
@RequestMapping("/upload")
public class ImgController {

	/** 图片服务 */
	@Autowired
	private ImgService imgService;

	/**
	 * 读取图片
	 * 
	 * @param response
	 * @param value
	 * @throws Exception
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void img(String name, HttpServletResponse response) throws Exception {
		// "image/pjpeg", "image/gif", "image/bmp", "image/x-png";
		response.setContentType("image/gif;charset=utf-8");
		FileCopyUtils.copy(imgService.get(name), response.getOutputStream());
	}

}
