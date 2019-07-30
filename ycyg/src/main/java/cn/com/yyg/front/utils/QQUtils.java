package cn.com.yyg.front.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.easy.utils.HttpUtils;

import com.google.common.collect.Lists;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.PageFans;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.PageFansBean;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.javabeans.weibo.Company;
import com.qq.connect.oauth.Oauth;

public class QQUtils {
	// http://wiki.connect.qq.com/开发攻略_server-side#Step2.EF.BC.9A.E8.8E.B7.E5.8F.96AuthorizationCode
	private static Logger logger = LoggerFactory.getLogger(QQUtils.class);

	// app_ID = 101269888 101334493
	// app_KEY = 6568344d93c8503741847fe79b9e5209
	// 80595a0b3951ebdc1bbb8e4c5c2ad852
	public static String accessToken = "80595a0b3951ebdc1bbb8e4c5c2ad852", app_ID = "101334493", domailString = "http://www.linweikun.com";

	// https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101334493&redirect_uri=http://www.linweikun.com
	// https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=[YOUR_APP_ID]&client_secret=[YOUR_APP_Key]&code=[The_AUTHORIZATION_CODE]&state=[The_CLIENT_STATE]&redirect_uri=[YOUR_REDIRECT_URI]
	/**
	 * qq登录验证
	 * 
	 * @param request
	 * @param response
	 * @author linwk 2016年6月15日
	 * @throws Exception
	 */
	public static void doPost(HttpServletRequest request, HttpServletResponse response, String code) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		// PrintWriter out = response.getWriter();
		try {
			// code 格式:7C3741B3B38C48BC42FFDA5A5D95D56B
			// https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=[YOUR_APP_ID]&client_secret=[YOUR_APP_Key]&code=[The_AUTHORIZATION_CODE]&state=[The_CLIENT_STATE]&redirect_uri=[YOUR_REDIRECT_URI]
			String url = " https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + app_ID + "&client_secret=" + accessToken + "&code=" + code
					+ "&redirect_uri=" + domailString;
			String accrssString = HttpUtils.doGet(url);
			logger.info("access_token=" + accrssString);
			// access_token=A8737F532ECF20BD081BDEE51A8C34F5&expires_in=7776000&refresh_token=1CCF78452DD3832B74144093171572E5

			String accrssString_access_token = null;
			// 关键
			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (StringUtils.isNoneBlank(accrssString)) {
				String pattern_acessString = "access_token=(.*?)&expires_in=";
				Pattern p_acessString = Pattern.compile(pattern_acessString);
				Matcher m_acessString = p_acessString.matcher(accrssString);
				ArrayList<String> strs_acessString = Lists.newArrayList();
				while (m_acessString.find()) {
					strs_acessString.add(m_acessString.group(1));
				}
				for (String s : strs_acessString) {
					accessToken = s;
					logger.info("通过access_token=" + s + ",获取openid");
					String url_access_token = "https://graph.qq.com/oauth2.0/me?access_token=" + s;
					accrssString_access_token = HttpUtils.doGet(url_access_token);
					logger.info(accrssString_access_token);
					// callback(
					// {"client_id":"101334493","openid":"DD5460B710B4524962810BB007C81AD4"}
					// );
				}
			}

			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			if (StringUtils.isNoneBlank(accrssString_access_token)) {
				// 获取 openid clientid
				// {"client_id":"101334493","openid":"DD5460B710B4524962810BB007C81AD4"}
				String openidString = "{\"client_id\":\"101334493\",\"openid\":\"DD5460B710B4524962810BB007C81AD4\"}";
				String client_id = "\"client_id\":\"(.*?)\",\"openid\"";
				String openid = "\"openid\":\"(.*?)\"}";
				Pattern p_client_id = Pattern.compile(client_id);
				Matcher m_client_id = p_client_id.matcher(openidString);
				ArrayList<String> strs_client_id = Lists.newArrayList();
				while (m_client_id.find()) {
					strs_client_id.add(m_client_id.group(1));
				}
				for (String s : strs_client_id) {
					logger.info(s);
				}

				Pattern p_openid = Pattern.compile(openid);
				Matcher m_openid = p_openid.matcher(openidString);
				ArrayList<String> strs_openid = Lists.newArrayList();
				while (m_openid.find()) {
					strs_openid.add(m_openid.group(1));
				}
				for (String s : strs_openid) {
					logger.info(s);
					openID = s;
				}
			}

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				logger.info("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("demo_access_token", accessToken);
				request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				logger.info("欢迎你，代号为 " + openID + " 的用户!");
				request.getSession().setAttribute("demo_openid", openID);
				logger.info("<a href=" + "/shuoshuoDemo.html" + " target=\"_blank\">去看看发表说说的demo吧</a>");
				// 利用获取到的accessToken 去获取当前用户的openid --------- end
			}

			if (StringUtils.isBlank(openID) || StringUtils.isBlank(accessToken)) {
				return;
			}

			logger.info("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- start </p>");
			UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
			UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
			logger.info("<br/>");
			if (userInfoBean.getRet() == 0) {
				logger.info(userInfoBean.getNickname() + "<br/>");
				logger.info(userInfoBean.getGender() + "<br/>");
				logger.info("黄钻等级： " + userInfoBean.getLevel() + "<br/>");
				logger.info("会员 : " + userInfoBean.isVip() + "<br/>");
				logger.info("黄钻会员： " + userInfoBean.isYellowYearVip() + "<br/>");
				logger.info("<image src=" + userInfoBean.getAvatar().getAvatarURL30() + "/><br/>");
				logger.info("<image src=" + userInfoBean.getAvatar().getAvatarURL50() + "/><br/>");
				logger.info("<image src=" + userInfoBean.getAvatar().getAvatarURL100() + "/><br/>");
			} else {
				logger.info("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
			}
			logger.info("<p> end -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- end </p>");

			logger.info("<p> start ----------------------------------- 验证当前用户是否为认证空间的粉丝------------------------------------------------ start <p>");
			PageFans pageFansObj = new PageFans(accessToken, openID);
			PageFansBean pageFansBean = pageFansObj.checkPageFans("97700000");
			if (pageFansBean.getRet() == 0) {
				logger.info("<p>验证您" + (pageFansBean.isFans() ? "是" : "不是") + "QQ空间97700000官方认证空间的粉丝</p>");
			} else {
				logger.info("很抱歉，我们没能正确获取到您的信息，原因是： " + pageFansBean.getMsg());
			}
			logger.info("<p> end ----------------------------------- 验证当前用户是否为认证空间的粉丝------------------------------------------------ end <p>");

			logger.info("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在微博的昵称等信息 ---------------------------- start </p>");
			com.qq.connect.api.weibo.UserInfo weiboUserInfo = new com.qq.connect.api.weibo.UserInfo(accessToken, openID);
			com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo.getUserInfo();
			if (weiboUserInfoBean.getRet() == 0) {
				// 获取用户的微博头像----------------------start
				logger.info("<image src=" + weiboUserInfoBean.getAvatar().getAvatarURL30() + "/><br/>");
				logger.info("<image src=" + weiboUserInfoBean.getAvatar().getAvatarURL50() + "/><br/>");
				logger.info("<image src=" + weiboUserInfoBean.getAvatar().getAvatarURL100() + "/><br/>");
				// 获取用户的微博头像 ---------------------end

				// 获取用户的生日信息 --------------------start
				logger.info("<p>尊敬的用户，你的生日是： " + weiboUserInfoBean.getBirthday().getYear() + "年" + weiboUserInfoBean.getBirthday().getMonth() + "月"
						+ weiboUserInfoBean.getBirthday().getDay() + "日");
				// 获取用户的生日信息 --------------------end

				StringBuffer sb = new StringBuffer();
				sb.append("<p>所在地:" + weiboUserInfoBean.getCountryCode() + "-" + weiboUserInfoBean.getProvinceCode() + "-" + weiboUserInfoBean.getCityCode()
						+ weiboUserInfoBean.getLocation());

				// 获取用户的公司信息---------------------------start
				ArrayList<Company> companies = weiboUserInfoBean.getCompanies();
				if (companies.size() > 0) {
					// 有公司信息
					for (int i = 0, j = companies.size(); i < j; i++) {
						sb.append("<p>曾服役过的公司：公司ID-" + companies.get(i).getID() + " 名称-" + companies.get(i).getCompanyName() + " 部门名称-" + companies.get(i).getDepartmentName()
								+ " 开始工作年-" + companies.get(i).getBeginYear() + " 结束工作年-" + companies.get(i).getEndYear());
					}
				} else {
					// 没有公司信息
				}
				// 获取用户的公司信息---------------------------end

				logger.info(sb.toString());

			} else {
				logger.info("很抱歉，我们没能正确获取到您的信息，原因是： " + weiboUserInfoBean.getMsg());
			}

			logger.info("<p> end -----------------------------------利用获取到的accessToken,openid 去获取用户在微博的昵称等信息 ---------------------------- end </p>");
		} catch (QQConnectException e) {
		}

	}

}
