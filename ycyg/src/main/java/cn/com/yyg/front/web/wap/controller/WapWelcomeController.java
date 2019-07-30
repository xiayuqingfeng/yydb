package cn.com.yyg.front.web.wap.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
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
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.UserService;
//import cn.com.platform.service.security.Md5PasswordEncoder;
//import cn.com.platform.service.security.dto.UserDetailsDto;
//import cn.com.platform.service.security.utils.CaptchaUtils;
//import cn.com.platform.service.security.utils.SpringSecurityRequestUtils;
//import cn.com.platform.utils.ValidUtils;
//import cn.com.platform.web.pub.dto.MessageDTO;
import cn.com.yyg.sms.SmsServcie;

/**
 * 注册控制器
 * 
 * @author linwk 2016年9月26日
 * 
 */
@Controller
@RequestMapping("/wap/welcome")
public class WapWelcomeController {

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

	/** 允许短信超时 10分钟内 **/
	private final static int SENDCODEALLOWTIME = 60;

	/** log **/
	private Logger logger = LoggerFactory.getLogger(WapWelcomeController.class);

	@Autowired
	private UserService userService;

	/** 用户dao */
	@Autowired
	private UserDao userDao;

	/**
	 * 发送短信
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param act
	 * @param mobile
	 * @param mobile
	 * @param password
	 * @param email
	 * @param scode
	 * @throws Exception
	 * @author linwk 2016年9月26日
	 */
	@RequestMapping("/sms")
	public void password_retrieve(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String act, String mobile, String username, String password, String email,
			String scode) throws Exception {
		// 注册 act:sms_password act:sms_regist
		try {
			String message = "发送失败";
			boolean b = CaptchaUtils.validCaptcha(req, scode);
			if (b == false) {
				// 验证码 验证不通过
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "图片验证码不正确"));
				return;
			}
			// 用户名为空
			if (StringUtils.isBlank(mobile)) {
				message = "手机号码不能为空";
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
				return;
			}

			// 获取短信发送时间 第一次为空
			Date senddate = (Date) req.getSession(false).getAttribute(SESSION_SENDCODE_DATE_STR);
			// 不满足可以发送的(发送过,上次发送间隔小于60秒),防止用户非法操作
			if (null != senddate && calLastedTime(senddate) <= SENDCODEALLOWTIME) {
				message = "请勿频繁获取验证码,稍后再试";
				req.getSession().setAttribute(SESSION_SENDCODE_DATE_STR, new Date());
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
				return;
			}

			// 判断是否手机号码
			try {
				Validate.matchesPattern(mobile, "^[1]+[3,4,5,7,8]+\\d{9}$", "手机号码格式错误");
			} catch (Exception e) {
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "手机号码格式错误"));
				return;
			}

			// 频繁操作
			int count = 0;
			// 设置发送次数 防止一直发
			String str = (String) req.getSession().getAttribute(SESSION_SENDCODE_TIMES);
			if (StringUtils.isNotBlank(str)) {
				// 判断是否为数字
				boolean isNum = str.matches("[0-9]+");
				if (isNum) {
					count = Integer.valueOf(str);
					req.getSession().setAttribute(SESSION_SENDCODE_TIMES, "" + (count + 1));
				}

			} else {
				req.getSession().setAttribute(SESSION_SENDCODE_TIMES, "" + 0);
			}
			if (count >= ALLOW_ERROR_TIMES) {
				message = "请勿频繁操作,稍后再试";
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
				return;
			}

			String mobile_code = RandomStringUtils.random(6, "123567890");

			// 找回密码 判断是否已经注册
			if (StringUtils.endsWith("sms_password", act)) {
				// 找回密码
				// 用户名不存在
				if (userService.exsitMobile(mobile) == false) {
					message = "您的手机尚未注册,请先注册";
					ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
					return;
				}
				// 发送代码 手机号为用户名 获取手机验证码 _并发送
				try {
					SmsServcie.sendFindPasswdCode(mobile, mobile_code);
				} catch (Exception e) {
					ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
					return;
				}
			} else {
				// 注册
				// 用户名已经存在
				if (userService.exsitMobile(username) == true) {
					message = "您的手机号码已经注册了";
					ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
					return;
				}
				// 发送代码 手机号为用户名 获取手机验证码 _并发送
				try {
					SmsServcie.sendRegistCode(mobile, mobile_code);
				} catch (Exception e) {
					logger.error("短信发送失败", e);
					ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, message));
					return;
				}
			}

			// 将验证码 验证码发送时间 保存到session
			req.getSession().setAttribute(SESSION_SENDCODE_DATE_STR, new Date());
			req.getSession().setAttribute(SESSION_CODE_STR, mobile_code);
			req.getSession().setAttribute(SESSION_MOBILE_STR, mobile);
			message = "短信已经发送到手机号:" + mobile + "的手机上面"+mobile_code;
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, message));
		} catch (Exception e) {
			logger.error("短信发送失败", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "短信发送失败"));
		}
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
}
