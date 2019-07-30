package cn.com.yyg.front.web.pub.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.com.easy.utils.SecurityUtils;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dto.WeixinDto;
import cn.com.yyg.front.service.UserService;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.utils.WeixinUtils;

/**
 * 登录控制器
 * 
 * @author nibili 2016年5月23日
 * 
 */
@Controller
@RequestMapping("")
public class LoginController {

	/** 用户dao */
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	@Value("#{configProperties['www.domain']}")
	private String domain;

	/**
	 * 首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月20日
	 */
	@RequestMapping("login")
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/pub/login/login";
	}

	/**
	 * 微信登录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月20日
	 */
	@RequestMapping("weixinLogin")
	public String weixin(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + WeixinUtils.app_ID + "&redirect_uri=" + WeixinUtils.domailString
				+ "&response_type=code&scope=snsapi_login&state=22138#wechat_redirect";
		return "redirect:" + url;
	}

	/**
	 * 微信登录回调地址
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月20日
	 * @throws Exception
	 */
	@RequestMapping("weixinRedirectLogin")
	public String weixin(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String code) throws Exception {
		if (StringUtils.isNoneBlank(code)) {
			WeixinDto weixinDto = WeixinUtils.doPost(req, res, code);
			if (weixinDto != null) {
				// 执行登录或者注册
				userService.loginOrRegistByWeiXinLongin(req, res, weixinDto);
				String backUrl = (String) req.getSession().getAttribute("backUrl");
				if (StringUtils.contains(backUrl, "/wap")) {
					backUrl = StringUtils.replaceChars(backUrl, "/wap", "");
				}
				if (StringUtils.isNoneBlank(backUrl)) {
					return "redirect:" + backUrl;
				}
			}
		}
		res.sendRedirect("/");
		return null;
	}

	/**
	 * 退出
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月23日
	 * @throws IOException
	 */
	@RequestMapping("logout")
	public String logout(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		res.sendRedirect("/login");
		return null;
		// return "redirect:/login";
	}

	/**
	 * 登录校验
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param redirectAttributes
	 * @param username
	 * @param password
	 * @return
	 * @author nibili 2016年5月24日
	 */
	@RequestMapping(value = "/login/check", method = RequestMethod.POST)
	public String loginCheck(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, final RedirectAttributes redirectAttributes, String username, String password) {
		// ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false,
		// "用户名或者密码错"));
		boolean isCheckSuccess = false;
		// 校验代码
		if (StringUtils.isNoneBlank(username, password) == true) {
			UserEntity userEntity = userDao.findUserByMobile(username);
			if (userEntity != null) {
				String passwordTemp = SecurityUtils.SHA256(password);
				if (StringUtils.equals(passwordTemp, userEntity.getPassword()) == true) {
					isCheckSuccess = true;
					// 当前用户信息放到session中
					RequestUtils.setCurrentUser(req, userEntity);
				} else {
					redirectAttributes.addFlashAttribute("error", "用户名或者密码错");
				}
			} else {
				redirectAttributes.addFlashAttribute("error", "用户名不存在");
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "用户名或者密码不能为空");
		}
		redirectAttributes.addFlashAttribute("username", username);
		if (isCheckSuccess == true) {
			// 登录成功返回来源页面
			String backUrl = req.getParameter("backUrl");
			if (StringUtils.isBlank(backUrl) || backUrl.indexOf("login") >= 0 || backUrl.indexOf("logout") >= 0) {
				// 通过访问首页
				return "redirect:/";
			} else {
				// 通过访问首页
				return "redirect:" + backUrl;
			}
		} else {
			// 异常退到登录页
			return "redirect:/login";
		}
	}
}
