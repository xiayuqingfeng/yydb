/**
 * 
 */
package cn.com.yyg.front.web.wap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.yyg.front.utils.CaptchaUtils;

/**
 * 验证码控制类
 * 
 * @author nibili 2015年10月25日下午9:01:15
 * 
 */
@Controller
@RequestMapping("/wap/captcha")
public class WapCaptchaController {
	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @auth nibili 2015年8月28日 下午3:59:10
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CaptchaUtils.sendCaptcha(request, response);
	}

}
