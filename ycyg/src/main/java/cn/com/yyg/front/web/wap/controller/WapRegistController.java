package cn.com.yyg.front.web.wap.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
//import cn.com.bzw.sms.SmsService;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.easy.utils.SecurityUtils;
//import cn.com.platform.service.security.CustomDaoAuthenticationProvider;
//import cn.com.platform.service.security.Md5PasswordEncoder;
//import cn.com.platform.service.security.utils.CaptchaUtils;
//import cn.com.platform.utils.ValidUtils;
//import cn.com.platform.web.pub.dto.MessageDTO;
//
//import com.bzw.base.exception.BusinessException;
//import com.bzw.base.util.DateUtil;
//import com.bzw.shopping.entity.User;
//import com.bzw.shopping.service.IUserService;
import cn.com.easy.utils.ValidUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dto.UserNameCheckInfo;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.service.UserService;

/**
 * 注册控制器
 * 
 * @author linwk 2016年9月12日
 * 
 */
@Controller
@RequestMapping("/wap/regist")
public class WapRegistController {
	/** 日志 **/
	private Logger logger = LoggerFactory.getLogger(WapRegistController.class);

	@Autowired
	private UserService userService;

	/** 用户dao */
	@Autowired
	private UserDao userDao;

	/**积分 */
	@Autowired
	private ScoreService scoreService;
	/** 验证通过效验字段 **/
	private final static String VALID_MESSAGE_SUCCESS = "success";
 

	/** 验证次数Session记录 **/
	private final static String SESSION_VERIFY_TIMES = "verify_times";

	/** 验证码发送时间_session名称 **/
	private final static String SESSION_SENDCODE_DATE_STR = "mobile_code_send_date";

	/** 验证码_session名称 **/
	private final static String SESSION_CODE_STR = "mobile_code";

	/** 注册手机号_session名称 **/
	private final static String SESSION_MOBILE_STR = "mobile_number";

	/** 发送短信验证码 次数Session记录 **/
	private final static String SESSION_SENDCODE_TIMES = "sendcode_times";

	/** 允许(注册,注册)验证错误次数 **/
	private final static int ALLOW_ERROR_TIMES = 50;

	/** 允许短信超时 10分钟内 **/
	private final static int SENDCODEALLOWTIME = 60;

	/**
	 * 注册邀请人
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author lvzf 2016年10月4日
	 */
	@RequestMapping("/{id}")
	public String regist_yaoqin(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		modelMap.addAttribute("yaoqingUserId", id);
		return "/wap/login/regist";
	}

	/**
	 * 注册界面入口
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年9月12日
	 */
	@RequestMapping("")
	public String regist(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String mobile, String username, String password, String confirm_password,
			String sms_code, String scode) {
		if (StringUtils.isNoneBlank(username) && StringUtils.isNoneBlank(password) && StringUtils.isNoneBlank(confirm_password) && ValidUtils.IsPassword(password) == true
				&& StringUtils.endsWith(password, confirm_password)) {
			model.addObject("password", password);
			model.addObject("mobile", mobile);
			model.addObject("scode", scode);
		}
		return "/wap/login/regist";
	}

	/**
	 * 注册验证手机是否存在
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2015年10月30日
	 */
	@RequestMapping("/check_username")
	public void registVerifyMobile(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String param) {
		UserNameCheckInfo userNameCheckInfo = new UserNameCheckInfo();
		try {
			// 1.非空判断
			if (StringUtils.isBlank(param)) {
				throw new BusinessException("你还没有填写手机号哦");
			}
			// 2.手机号码格式判断
			try {
				Validate.matchesPattern(param, "^[1]+[3,4,5,7,8]+\\d{9}$", "手机号码格式错误");
			} catch (Exception e) {
				throw new BusinessException("手机号码格式错误");
			}
			// 3.存在这个用户,满足条件
			if (userService.exsitMobile(param)) {
				// 手机号已存在。忘记密码
				throw new BusinessException("手机号已注册。");
			}
			userNameCheckInfo.setStatus("y");
			ResponseOutputUtils.renderJson(res, userNameCheckInfo);
		} catch (BusinessException e) {
			userNameCheckInfo.setStatus("n");
			userNameCheckInfo.setInfo(e.getMessage());
			ResponseOutputUtils.renderJson(res, userNameCheckInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			userNameCheckInfo.setStatus("n");
			ResponseOutputUtils.renderJson(res, userNameCheckInfo);
		}

	}

	/**
	 * 验证手机密码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2015年10月30日
	 */
	@RequestMapping("/submit")
	public void submit(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String mobile, String username, String password, String confirm_password,
			String sms_code, String scode, String step, String yaoqingUserId) {
		// 第一步验证
		if (StringUtils.endsWith("1", step)) {
			if (StringUtils.isNoneBlank(username) && StringUtils.isNoneBlank(password) && StringUtils.isNoneBlank(confirm_password) && ValidUtils.IsPassword(password) == true
					&& StringUtils.endsWith(password, confirm_password)) {
				req.getSession().setAttribute(SESSION_MOBILE_STR, mobile);
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, username));
			} else {
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "注册异常"));
			}
			return;
		} else {
			// 第二步验证
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
					ResponseOutputUtils.renderJson(res, scriptErrorMessage("请勿频繁操作,稍后再试"));
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
						ResponseOutputUtils.renderJson(res, scriptErrorMessage("验证码已超过时间期限，请重新获取验证码！"));
						return;
					}
				}
				// 3.验证通过
				if (StringUtils.equals(validMessage, VALID_MESSAGE_SUCCESS)) {
					// 4.手机号不存在
					if (userService.exsitMobile(mobile) == false) {
						UserEntity user = new UserEntity();
						user.setMobile(sentUsername);
						user.setUserName(sentUsername);
						if (StringUtils.isNoneBlank(yaoqingUserId)) {
							user.setYaoqingUserId(Long.valueOf(yaoqingUserId));
						}
						// 保存密码
						user.setPassword(SecurityUtils.SHA256(password));
						user.setAccountBalance(new BigDecimal(0));
						userDao.save(user);
						//新会员添加积分 
						scoreService.addScore(user, UserScoreTypeEnum.Regist, "注册成为新会员："+user.getUserName());
						//邀请好友添加积分
						if (StringUtils.isNoneBlank(yaoqingUserId)) {
							UserEntity userYq=userService.findById(Long.valueOf(yaoqingUserId));
							scoreService.addScore(userYq, UserScoreTypeEnum.Yaoqing, "邀请好友："+user.getUserName());
						}
						ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
					}
					// 5.手机号已经存在
					else {
						ResponseOutputUtils.renderJson(res, scriptErrorMessage("您的手机已经注册过了"));
					}
					// 7.清除session
					req.getSession().removeAttribute(SESSION_CODE_STR);
					req.getSession().removeAttribute(SESSION_SENDCODE_DATE_STR);
					req.getSession().removeAttribute(SESSION_SENDCODE_TIMES);
					req.getSession().removeAttribute(SESSION_VERIFY_TIMES);
					req.getSession().removeAttribute(SESSION_MOBILE_STR);
				}
				// 否则返回错误类型
				else {
					ResponseOutputUtils.renderJson(res, scriptErrorMessage(validMessage));
				}
			} catch (Exception e) {
				logger.error("注册失败", e);
				ResponseOutputUtils.renderJson(res, scriptErrorMessage("注册失败"));
			}
		}

	}

	/**
	 * 验证码注册--成功返回script -跳转到密码重置页面
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

	}

	private MessageDTO scriptErrorMessage(String message) {
		// "$(\".imgcode\").attr(\"src\", \"/captcha?t=\" + Math.random());"
		MessageDTO messageDTO = new MessageDTO("", false, message, "");
		return messageDTO;
	}

	/**
	 * 验证注册信息方法
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
}
