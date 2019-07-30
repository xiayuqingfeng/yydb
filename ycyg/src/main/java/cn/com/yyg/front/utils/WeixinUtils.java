package cn.com.yyg.front.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.easy.utils.HttpUtils;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dto.WeixinDto;

import com.github.sd4324530.fastweixin.exception.WeixinException;
import com.qq.connect.utils.json.JSONException;
import com.qq.connect.utils.json.JSONObject;

public class WeixinUtils {

	// https://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
	private static Logger logger = LoggerFactory.getLogger(WeixinUtils.class);
	public static String app_ID = "wx160ded85d3101b00";
	public static String app_Secret = "e30056f6d0d1a14855e92902a012d778";
	public static String domailString = "http://localhost/weixinRedirectLogin";

	/**
	 * 获取code 访问到主页 判断获取到code 再去获取用户accessToken
	 * 
	 * @author linwk 2016年10月13日
	 */
	public static String getCode() {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + app_ID + "&redirect_uri=" + domailString
				+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		return "redirect:" + url;
	}

	/**
	 * 获取用户openId 用户accessToken
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 * @throws Exception
	 */
	public static String getOpenIdAndAccessToken(String code) throws Exception {
		try {
			logger.info("code=" + code);
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + app_ID + "&secret=" + app_Secret + "&code=" + code + "&grant_type=authorization_code";
			String accrssString = HttpUtils.doGet(url);
			logger.info("access_token=" + accrssString);
			return accrssString;
		} catch (WeixinException e) {
			logger.info("code=" + code);
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + app_ID + "&secret=" + app_Secret + "&code=" + code + "&grant_type=authorization_code";
			String accrssString = HttpUtils.doGet(url);
			logger.info("access_token=" + accrssString);
			return accrssString;
		}
	}

	/**
	 * 获取用户openId 用户accessToken
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 * @throws Exception
	 */
	public static String getUserInfo(String openid, String access_token) throws Exception {
		try {
			logger.info("openid=" + openid + "*************************** access_token=" + access_token);
			String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
			String UserInfo = HttpUtils.doGet(url);
			logger.info("UserInfo=" + UserInfo);
			return UserInfo;
		} catch (WeixinException e) {
			logger.info("openid=" + openid + "*************************** access_token=" + access_token);
			String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
			String UserInfo = HttpUtils.doGet(url);
			logger.info("UserInfo=" + UserInfo);
			return UserInfo;
		}
	}

	/**
	 * 微信登录验证
	 * 
	 * @param request
	 * @param response
	 * @author linwk 2016年6月15日
	 * @throws Exception
	 */
	public static WeixinDto doPost(HttpServletRequest request, HttpServletResponse response, String code) throws Exception {
		try {
			response.setContentType("text/html; charset=utf-8");
			// 获取用户openId 用户accessToken
			String accrssString = getOpenIdAndAccessToken(code);
			if (StringUtils.isNoneBlank(accrssString)) {
				logger.info("获取***************************openid*************************************信息成功");
				JSONObject demoJson = new JSONObject(accrssString);
				String openid = getJsonName(demoJson, "openid");
				String access_token = getJsonName(demoJson, "access_token");
				String UserInfo = getUserInfo(openid, access_token);
				if (StringUtils.isNoneBlank(UserInfo)) {
					logger.info("获取***************************openid*************************************信息成功");
					JSONObject UserInfoJson = new JSONObject(UserInfo);
					String nickname = getJsonName(UserInfoJson, "nickname");
					String sex = getJsonName(UserInfoJson, "sex");
					String headimgurl = getJsonName(UserInfoJson, "headimgurl");
					WeixinDto weixin = new WeixinDto(openid, nickname, sex, headimgurl);
					return weixin;
				}
			}
		} catch (WeixinException e) {
		}
		return null;

	}

	private static String getJsonName(JSONObject demoJson, String tString) throws JSONException {
		return demoJson.getString(tString);
	}

	/**
	 * 微信浏览器判读
	 * 
	 * @param req
	 * @author linwk 2016年10月17日
	 * @param modelMap
	 */
	public static void extracted(HttpServletRequest request) {
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			// [微信登录脚本]
			request.getSession().setAttribute("isWeixinBrowerisWeixinBrower", true);
			request.getSession().setAttribute("isWeixinBrower", true);
			request.getSession().setAttribute("appId", WeixinUtils.app_ID);
			String jsapi_ticket = WeixinUtils.getTicket();
			// 注意 URL 一定要动态获取，不能 hardcode
			String url = RequestUtils.getUrl(request);
			Map<String, String> ret = WeixinSign.sign(jsapi_ticket, url);
			for (@SuppressWarnings("rawtypes")
			Map.Entry entry : ret.entrySet()) {
				request.getSession().setAttribute(entry.getKey().toString(), entry.getValue());
			}
			
			//是否设置手机号
			//微信登录
			UserEntity user = RequestUtils.getCurrentUser(request);
			if (user!=null) {
				String tel = user.getMobile();
				if (StringUtils.isBlank(tel)) {
					request.getSession().setAttribute("errorNoMobile", "请设置手机号");
				}
			}
		}
	}

	/**
	 * get accessToken
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public static String getAccessToken() {
		if (StringUtils.isBlank(System.getProperty("accessToken"))) {
			System.setProperty("accessToken", WeixinAccessToken.getAccessToken());
		}
		return System.getProperty("accessToken");
	}

	/**
	 * set accessToken
	 * 
	 * @param accessToken
	 * @author linwk 2016年10月13日
	 */
	public static void setAccessToken(String accessToken) {
		System.setProperty("accessToken", accessToken);
	}

	/**
	 * get ticket
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public static String getTicket() {
		if (StringUtils.isBlank(System.getProperty("ticket"))) {
			System.setProperty("ticket", WeixinTicket.getTicket());
		}
		return System.getProperty("ticket");
	}

	/**
	 * set ticket
	 * 
	 * @param ticket
	 * @author linwk 2016年10月13日
	 */
	public static void setTicket(String ticket) {
		System.setProperty("ticket", ticket);
	}
}
