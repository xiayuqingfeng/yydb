package cn.com.yyg.front.web.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.com.easy.utils.SecurityUtils;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 登录控制器
 * 
 * @author nibili 2016年5月23日
 * 
 */
@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {

	/** 用户dao */
	@Autowired
	private UserDao userDao;

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
		return "/admin/login/login";
	}

	/**
	 * 退出
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月23日
	 */
	@RequestMapping("logout")
	public String logout(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		req.getSession().invalidate();
		return "redirect:/admin/login";
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
	@RequestMapping("/check")
	public String loginCheck(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, final RedirectAttributes redirectAttributes, String username, String password) {
		// ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false,
		// "用户名或者密码错"));
		boolean isCheckSuccess = false;
		// 校验代码
		if (StringUtils.isNoneBlank(username, password) == true) {
			UserEntity userEntity = userDao.findUserByUserName(username);
			// 必须是管理员
			if (userEntity != null && userEntity.getUserType() == 1) {
				String passwordTemp = SecurityUtils.SHA256(password);
				if (StringUtils.equals(passwordTemp, userEntity.getPassword()) == true) {
					isCheckSuccess = true;
					// 当前用户信息放到session中
					RequestUtils.setCurrentAdminUser(req, userEntity);
				}
			}
		}
		if (isCheckSuccess == true) {
			// 通过访问首页
			return "redirect:/admin";
		} else {
			// 异常退到登录页
			// 设置未成功变量
			redirectAttributes.addFlashAttribute("isCheckSuccess", false);
			return "redirect:/admin/login";
		}
	}
}
