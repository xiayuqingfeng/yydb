package cn.com.yyg.front.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 操作cookie工具类
 * 
 * @author nibili 2015年11月2日
 * 
 */
public class CookieUtils {

	// private static Logger logger =
	// LoggerFactory.getLogger(CookieUtils.class);

	/** 登录成功跳转的cookie名 */
	private final static String REDIRECT_COOKIE_NAME = "REDIRECT_COOKIE_NAME";
	/** 用于获取未登录 状态下的虚拟会话id */
	private final static String SESSION_ID_COOKIE_NAME = "SESSION_ID_COOKIE";

	/**
	 * 获取一个临时会话id放于cookie中，时效为永久
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author nibili 2016年1月27日
	 */
	public static long getSessionId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String value = CookieUtils.getCookieValue(request, SESSION_ID_COOKIE_NAME);
		if (StringUtils.isBlank(value) == true) {
			// 设置cookie
			// 为空，设置cookie
			long l = new Date().getTime();
			long userId = l + RandomUtils.nextLong(0, l);
			String userIdCookie = String.valueOf(userId);
			CookieUtils.setCookie(request, response, SESSION_ID_COOKIE_NAME, userIdCookie);
			return userId;
		} else {
			// 不为空
			return Long.valueOf(value);
		}
	}

	/**
	 * 设置登录跳转cookie的url
	 * 
	 * @param request
	 * @param response
	 * @param value
	 * @throws Exception
	 * @author nibili 2015年11月3日
	 */
	public static void setCookieRedirectUrl(HttpServletRequest request, HttpServletResponse response, String value) throws Exception {
		CookieUtils.setCookie(request, response, REDIRECT_COOKIE_NAME, value);
	}

	/**
	 * 获取 登录跳转cookie的url
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author nibili 2015年11月3日
	 */
	public static String getCookieRedirectUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String value = CookieUtils.getCookieValue(request, REDIRECT_COOKIE_NAME);
		CookieUtils.setCookie(request, response, REDIRECT_COOKIE_NAME, "");
		return value;
	}

	/**
	 * 根据cookie的名称获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null || name == null || name.length() == 0) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (name.equals(cookies[i].getName())) {
				// && request.getServerName().equals(cookies[i].getDomain())) {
				return cookies[i];
			}
		}
		return null;
	}

	/**
	 * 获取cookie值
	 * 
	 * @param request
	 * @param name
	 * @return
	 * @throws Exception
	 * @author nibili 2015年11月2日
	 */
	public static String getCookieValue(HttpServletRequest request, String name) throws Exception {
		Cookie ck = getCookie(request, name);
		if (ck != null) {
			return URLDecoder.decode(ck.getValue(), "UTF-8");
		} else {
			return null;
		}
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookie
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			cookie.setPath(getPath(request));
			cookie.setValue("");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * 设置cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 *            如果不设置时间，默认永久
	 * @throws Exception
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) throws Exception {
		setCookie(request, response, name, value, 0x278d00);
	}

	/**
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 *            设置cookie，设定时间
	 * @throws Exception
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) throws Exception {
		String cookieValue = URLEncoder.encode(value, "utf-8");
		Cookie cookie = new Cookie(name, value == null ? "" : cookieValue.replaceAll("\r\n", ""));
		cookie.setMaxAge(maxAge);
		cookie.setPath(getPath(request));
		response.addCookie(cookie);
	}

	private static String getPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return (path == null || path.length() == 0) ? "/" : path;
	}

	public static void main(String[] args) {

	}
}
