/** 
 * @autor linwk 2015年12月28日
 * 
 */
package cn.com.yyg.front.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author linwk 2015年12月28日
 * 
 */
public class ValidUtils {
	/**
	 * 验证输入手机号码
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 * @author linwk 2015年10月27日
	 */
	public static boolean IsMobileNumber(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			String regex = "^[1]+[3,4,5,7,8]+\\d{9}$";
			return match(regex, str);
		}
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 * @author linwk 2015年10月27日
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 邮箱
	 * 
	 * @param email
	 * @return
	 * @author linwk 2016年4月13日
	 */
	public static boolean IsEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		} else {
			String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(email);
			return m.matches();
		}
	}

	/**
	 * 
	 * @param password
	 * @return
	 * @author linwk 2016年4月13日
	 */
	public static boolean IsPassword(String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		} else {
			if (StringUtils.length(password) > 32 || StringUtils.length(password) < 6) {
				return false;
			} else {
				return true;
			}
		}
	}
}
