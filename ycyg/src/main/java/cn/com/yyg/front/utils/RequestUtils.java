package cn.com.yyg.front.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.com.yyg.base.entity.UserEntity;

/**
 * request对象工具类
 * 
 * @author nibili 2016年5月23日
 * 
 */
public class RequestUtils {

	/** 普通用户标志 */
	public static String USER_TAG = "user";
	/** 管理员标志 */
	public static String USER_TAG_ADMIN = "user_admin";
	/** 当前用户的未读应用数标志 */
	public static String USER_APP_DATA_COUNT = "user_app_data_count";

	public static String getBackUrl(HttpServletRequest req) {
		String backUrl = req.getParameter("backUrl");
		if (StringUtils.isBlank(backUrl)) {
			backUrl = (String) req.getSession().getAttribute("backUrl");
		}
		if (StringUtils.contains(backUrl, "/wap")) {
			backUrl = StringUtils.replace(backUrl, "/wap", "");
		}
		// logger.error("backUrl:" + backUrl);
		return backUrl;
	}

	public static void setBackUrl(HttpServletRequest req) {
		String backUrl = req.getRequestURL().toString();
		if (StringUtils.isNoneBlank(req.getQueryString())) {
			backUrl += "?" + req.getQueryString();
		}
		req.getSession().setAttribute("backUrl", backUrl);
	}

	/**
	 * 获取url 替换掉wap参数 手机模式的链接
	 * 
	 * @param request
	 * @return
	 * @author linwk 2016年10月18日
	 */
	public static String getUrl(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		String queryString = "";
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}
		// 去掉最后一个空格
		if (StringUtils.isNoneBlank(queryString)) {
			queryString = queryString.substring(0, queryString.length() - 1);
			queryString = "?" + queryString;
		}
		queryString=request.getRequestURL()+queryString;
		queryString = StringUtils.replace(queryString, "/wap/", "");
		return queryString;
	}

	/**
	 * 获取当前登录的管理员
	 * 
	 * @param request
	 * @return
	 * @author nibili 2016年5月23日
	 */
	public static UserEntity getCurrentAdminUser(HttpServletRequest request) {
		return (UserEntity) request.getSession().getAttribute(USER_TAG_ADMIN);
	}

	/**
	 * 设置当前登录的管理员
	 * 
	 * @param request
	 * @param object
	 * @author nibili 2016年5月23日
	 */
	public static void setCurrentAdminUser(HttpServletRequest request, Object object) {
		request.getSession().setAttribute(USER_TAG_ADMIN, object);
	}

	/**
	 * get current user
	 * 
	 * @param request
	 * @return
	 */
	public static UserEntity getCurrentUser(HttpServletRequest request) {
		return (UserEntity) request.getSession().getAttribute(USER_TAG);
	}

	/**
	 * set current user
	 * 
	 * @auth nibili 2015-2-3
	 */
	public static void setCurrentUser(HttpServletRequest request, Object object) {
		request.getSession().setAttribute(USER_TAG, object);
	}

}
