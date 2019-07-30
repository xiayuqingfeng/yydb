package cn.com.yyg.front.web.wap.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.utils.CaptchaUtils;
import cn.com.easy.utils.MailUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.easy.utils.SecurityUtils;
//import cn.com.platform.service.security.Md5PasswordEncoder;
//import cn.com.platform.service.security.dto.UserDetailsDto;
//import cn.com.platform.service.security.utils.CaptchaUtils;
//import cn.com.platform.service.security.utils.SpringSecurityRequestUtils;
//import cn.com.platform.utils.ValidUtils;
//import cn.com.platform.web.pub.dto.MessageDTO;
import cn.com.easy.utils.ValidUtils;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.UserService;
import cn.com.yyg.front.utils.RequestUtils;

//import com.bzw.shopping.service.IUserService;

/**
 * 注册控制器
 * 
 * @author linwk 2016年9月12日
 * 
 */
@Controller
@RequestMapping("/wap/findPassword")
public class WapFindPasswordController {
	/** log **/
	private Logger logger = LoggerFactory.getLogger(WapFindPasswordController.class);

	/** 验证通过效验字段 **/
	private final static String VALID_MESSAGE_SUCCESS = "success";

	/** 初始化密码 **/
	private final static String ONLOAD_PASSWORD = "!@#$%^&*()0987654321^&";

	/** 验证次数Session记录 **/
	private final static String SESSION_VERIFY_TIMES = "verify_times";

	/** 验证码发送时间_session名称 **/
	private final static String SESSION_SENDCODE_DATE_STR = "mobile_code_send_date";

	/** 验证码_session名称 **/
	private final static String SESSION_CODE_STR = "mobile_code";

	/** 找回密码手机号_session名称 **/
	private final static String SESSION_MOBILE_STR = "mobile_number";

	/** 发送短信验证码 次数Session记录 **/
	private final static String SESSION_SENDCODE_TIMES = "sendcode_times";

	/** 允许(注册,找回密码)验证错误次数 **/
	private final static int ALLOW_ERROR_TIMES = 50;

	/** 允许短信超时   10分钟内**/
	private final static int SENDCODEALLOWTIME = 60;

	@Autowired
	private UserService userService;

	/** 用户dao */
	@Autowired
	private UserDao userDao;

	/**
	 * 找回密码界面入口
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年9月12日
	 */
	@RequestMapping("")
	public String findpassword(ModelAndView model, HttpServletRequest req, HttpServletResponse res) {
		// 用户已登录 跳转到首页
		// UserDetailsDto userDetailsDto =
		// SpringSecurityRequestUtils.getCurrentUserDetails(req);
		// if (userDetailsDto != null) {
		// return "redirect:/";
		// }
		model.addObject("", "");
		return "/wap/login/findPassword";
	}

	/**
	 * 重置密码界面入口
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年9月26日
	 */
	@RequestMapping("/resetPassword")
	public String resetPassword(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String password, String confirm_password) {
		// 判断用户是否处于重置密码状态
		// 获取验证码的手机
		String sentUsername = (String) req.getSession().getAttribute(SESSION_MOBILE_STR);
		if (StringUtils.isNoneBlank(sentUsername)) {
			return "/wap/login/resetPassword";
		} else {
			return "/wap/login/findPassword";
		}
	}

	@RequestMapping("/resetPasswordAjax")
	public void resetPasswordAjax(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String password, String confirm_password) {
		String sentUsername = (String) req.getSession().getAttribute(SESSION_MOBILE_STR);
		if (StringUtils.isNoneBlank(sentUsername)) {
			if (StringUtils.isBlank(password) || StringUtils.isBlank(confirm_password)) {
				ResponseOutputUtils.renderHtml(res, scriptErrorMessage("密码不能为空"));
				return;
			}
			if (StringUtils.endsWith(password, confirm_password) == false) {
				ResponseOutputUtils.renderHtml(res, scriptErrorMessage("两次密码不一致"));
				return;
			}
			if (ValidUtils.IsPassword(password) == false) {
				ResponseOutputUtils.renderHtml(res, scriptErrorMessage("密码必须是大于等于6个字符小于32个字符"));
				return;
			}
			// 登录保存 跳转
			UserEntity user = userService.getUserByMobile(sentUsername);
			// 执行找回密码操作
			// 登录操作
			if (user != null) {
				RequestUtils.setCurrentUser(req, user);
				user.setPassword(SecurityUtils.SHA256(password));
				user = userDao.save(user);
				// 清除session
				req.getSession().removeAttribute(SESSION_MOBILE_STR);
				ResponseOutputUtils.renderHtml(res, "<script type=\"text/javascript\">parent.layer.alert(\"重置成功\",1,function(){parent.location.href='/'});</script>");
			} else {
				ResponseOutputUtils.renderHtml(res,
						scriptErrorMessage("<script type=\"text/javascript\">parent.layer.alert(\"您的手机尚未注册,请先注册\",0,function(){parent.location.href='/regist'});</script>"));
			}
		}

	}

	/**
	 * 验证码找回密码--成功返回script -跳转到密码重置页面
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param act
	 * @param mobile
	 * @param username
	 * @param password
	 * @param email
	 * @param sms_code
	 * @throws Exception
	 * @author linwk 2016年9月26日
	 */
	@RequestMapping("/mobile")
	public void findPassword(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String mobile, String username, String password, String email, String sms_code,
			String scode) throws Exception {
		try {
			// 1.频繁操作
			String sendCodeSessionName = SESSION_VERIFY_TIMES;
			int count = 0;// 默认次数
			String str = (String) req.getSession().getAttribute(sendCodeSessionName);// 获取发送次数
			if (StringUtils.isNotBlank(str)) {
				boolean isNum = str.matches("[0-9]+");// 判断是否为数字
				if (isNum) {
					count = Integer.valueOf(str);
					req.getSession().setAttribute(sendCodeSessionName, "" + (count + 1));
				}

			} else {
				req.getSession().setAttribute(sendCodeSessionName, "" + 0);
			}
			if (count >= ALLOW_ERROR_TIMES) {
				ResponseOutputUtils.renderHtml(res, scriptErrorMessage("请勿频繁操作,稍后再试"));
				return;
			}
			// 2.已发送的验证码
			String sentCode = (String) req.getSession().getAttribute(SESSION_CODE_STR);
			// 获取验证码的手机
			String sentUsername = (String) req.getSession().getAttribute(SESSION_MOBILE_STR);
			String validMessage = validFindPasswordString(mobile, sentUsername, sms_code, sentCode, password);
			// 验证码时间是否超过10分钟
			Date sendCodeTime = (Date) req.getSession().getAttribute(SESSION_SENDCODE_DATE_STR);
			if (sendCodeTime != null) {
				int sendDate = calLastedTime(sendCodeTime);
				if (sendDate >= 10 * SENDCODEALLOWTIME) {
					ResponseOutputUtils.renderHtml(res, scriptErrorMessage("验证码已超过时间期限，请重新获取验证码！"));
					return;
				}
			}
			// 3.验证通过
			if (StringUtils.equals(validMessage, VALID_MESSAGE_SUCCESS)) {
				// 执行
				// 判断 保存用户还是更新
				// 4.手机号已经存在
				if (userService.exsitMobile(mobile) == true) {
					// UserEntity user = userService.getUserByMobile(mobile);
					// 执行找回密码操作
					// 登录操作
					// RequestUtils.setCurrentUser(req, user);
					// user.setPassword(SecurityUtils.SHA256(password));
					// user = userDao.save(user);
					ResponseOutputUtils.renderHtml(res,
					// "<script type=\"text/javascript\">parent.layer.alert(\"重置成功\",1,function(){parent.location.href='/findPassword/resetPassword'});</script>");
							"<script type=\"text/javascript\">parent.location.href='/findPassword/resetPassword';</script>");
				}
				// 5.手机号不存在
				else {
					ResponseOutputUtils.renderHtml(res,
							scriptErrorMessage("<script type=\"text/javascript\">parent.layer.alert(\"您的手机尚未注册,请先注册\",0,function(){parent.location.href='/regist'});</script>"));
				}
				// 7.清除session
				req.getSession().removeAttribute(SESSION_CODE_STR);
				req.getSession().removeAttribute(SESSION_SENDCODE_DATE_STR);
				req.getSession().removeAttribute(SESSION_SENDCODE_TIMES);
				req.getSession().removeAttribute(SESSION_VERIFY_TIMES);
			}
			// 否则返回错误类型
			else {
				ResponseOutputUtils.renderHtml(res, scriptErrorMessage(validMessage));
			}
		} catch (Exception e) {
			logger.error("找回密码失败", e);
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("找回密码失败"));
		}
	}

	private String scriptErrorMessage(String message) {
		// "$(\".imgcode\").attr(\"src\", \"/captcha?t=\" + Math.random());"
		return "<script type=\"text/javascript\">parent.layer.alert(\"" + message + "\",0);" + "" + "</script>";
	}

	/**
	 * 验证找回密码信息方法
	 * 
	 * @param username
	 * @param sentUsername
	 * @param code
	 * @param sentCode
	 * @param password
	 * @return
	 * @author linwk 2016年9月26日
	 */
	private String validFindPasswordString(String username, String sentUsername, String code, String sentCode, String password) {
		String message = VALID_MESSAGE_SUCCESS;
		// 1.用户名或者密码为空
		if (StringUtils.isBlank(username)) {
			message = "用户为空";
			return message;
		}
		// 2.未发送验证码
		if (StringUtils.isBlank(sentCode)) {
			message = "短信验证码未发送";
			return message;
		}
		// 3 .验证码问题 绕过前端验证 非法操作
		if (StringUtils.equals(code, sentCode) == false) {
			message = "短信验证码错误";
			return message;
		}
		// 4.判断是否发送验证码的手机号
		if (StringUtils.equals(username, sentUsername) == false) {
			message = "短信验证码未发送";
			return message;
		}
		// 5.判断是否手机号码
		try {
			Validate.matchesPattern(username, "^[1]+[3,4,5,7,8]+\\d{9}$", "手机号码格式错误");
		} catch (Exception e) {
			return "手机号码格式错误";
		}

		// 6.判断密码
		if (ValidUtils.IsPassword(password) == false) {
			// message = "密码格式错误";
			// return message;
		}

		return message;
	}

	/**
	 * 两次时间差,用来计算短信发送倒计时
	 * 
	 * @param startDate
	 * @return
	 * @author linwk 2015年11月2日
	 */
	private int calLastedTime(Date startDate) {
		long a = new Date().getTime();
		long b = startDate.getTime();
		int c = (int) ((a - b) / 1000);
		return c;
	}

	/**
	 * 找回密码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2015年11月20日
	 * @throws Exception
	 */
	@RequestMapping("/password_retrieve")
	public void password_retrieve(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String username, String email, String code) throws Exception {

	}
}
