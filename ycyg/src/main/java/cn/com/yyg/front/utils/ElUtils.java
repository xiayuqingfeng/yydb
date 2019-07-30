package cn.com.yyg.front.utils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import cn.com.easy.utils.DateUtils;

import com.google.common.collect.Lists;

/**
 * 
 * 用于前端的间接调用的一些工具方法的工具类
 * 
 * @author nibili 2015年11月10日
 * 
 */
public class ElUtils {
	/** 自身实例 */
	private static ElUtils elUtils = new ElUtils();

	/**
	 * 创建
	 * 
	 * @param servletRequest
	 * @return
	 * @author nibili 2015年11月10日
	 */
	public static void newInstanceToRequest(ServletRequest servletRequest) {
		servletRequest.setAttribute("ElUtils", elUtils);
	}

	public ElUtils() {

	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @author nibili 2016年6月1日
	 */
	public String getNowTimeString() {
		return DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
	}

	/**
	 * 字符串转成html
	 * 
	 * @param str
	 * @return
	 * @author linwk 2016年1月15日
	 */
	public String unescapeHtml3(String str) {
		str = StringEscapeUtils.unescapeHtml3(str);
		str = StringUtils.replace(str, "”", "\"");
		return str;
	}

	/**
	 * 字符串转成html并替换增加懒加载
	 * 
	 * @param str
	 * @return
	 * @author linwk 2016年1月15日
	 */
	public String unescapeHtml3Lazy(String str) {
		// 转换html
		str = StringEscapeUtils.unescapeHtml3(str);
		str = this.changeHtmlTag(str, "img", "src", "data-original", "\"");
		str = this.updateAllHtmlTag(str, "img", "src", "/assets/images/pub/loading_bg.gif", "\"");
		str = this.updateHtmlTag(str, "img", "class", "", " lazy\"");
		return str;
	}

	/**
	 * 链接参数编码(UTF-8)
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 * @author nibili 2015年11月10日
	 */
	public String encode(String string) throws Exception {
		return java.net.URLEncoder.encode(string, "UTF-8");
	}

	/**
	 * 链接参数解码(UTF-8)
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 * @author nibili 2015年11月10日
	 */
	public String decode(String string) throws Exception {
		return java.net.URLDecoder.decode(string, "UTF-8");
	}

	/**
	 * 替换 split过的string，如:"b,a,b,c,b,d,b",然后要把b替换为空,并且去掉相应的 ","
	 * 
	 * @param string
	 * @param split
	 * @param oldString
	 * @param replacement
	 * @return
	 * @author nibili 2015年11月12日
	 */
	public String replaceSplitString(String string, String split, String oldString, String replacement) {
		String[] strs = string.split(split);
		List<String> list = Lists.newArrayList(strs);
		for (java.util.Iterator<String> it = list.iterator(); it.hasNext();) {
			String str = it.next();
			if (str.contains(oldString) == true) {
				it.remove();
			}
		}
		return StringUtils.join(list, split);
	}

	/**
	 * @param htmlStr
	 *            html文本
	 * @param searchTag
	 *            要修改的目标标签
	 * @param searchAttrib
	 *            目标标签中的属性
	 * @param newStr
	 *            修改值 保留原本
	 */
	public String updateHtmlTag(String htmlStr, String searchTag, String searchAttrib, String startStr, String endStr) {
		String regxpForTag = "<\\s*" + searchTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = searchAttrib + "\\s*=\\s*[\"|']([^\"|']+)[\"|']";// "=[\"|']http://([^[\"|']]+)[\"|']";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(htmlStr);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer("<" + searchTag + " ");
			// System.out.println("原本的matcherForTag:" + matcherForTag.group(1));
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));

			if (matcherForAttrib.find()) {
				// System.out.println("原本的matcherForAttrib:" +
				// matcherForAttrib.group(1));
				matcherForAttrib.appendReplacement(sbreplace, searchAttrib + "=\"" + startStr + matcherForAttrib.group(1) + endStr);
			}
			matcherForAttrib.appendTail(sbreplace);
			matcherForTag.appendReplacement(sb, sbreplace.toString() + ">");
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

	/**
	 * @param htmlStr
	 *            html文本
	 * @param searchTag
	 *            要修改的目标标签
	 * @param searchAttrib
	 *            目标标签中的属性
	 * @param newStr
	 *            修改值 不保留原本
	 */
	public String updateAllHtmlTag(String htmlStr, String searchTag, String searchAttrib, String startStr, String endStr) {
		String regxpForTag = "<\\s*" + searchTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = searchAttrib + "\\s*=\\s*[\"|']([^\"|']+)[\"|']";// "=[\"|']http://([^[\"|']]+)[\"|']";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(htmlStr);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer("<" + searchTag + " ");
			// System.out.println("原本的matcherForTag:" + matcherForTag.group(1));
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));

			if (matcherForAttrib.find()) {
				// System.out.println("原本的matcherForAttrib:" +
				// matcherForAttrib.group(1));
				matcherForAttrib.appendReplacement(sbreplace, searchAttrib + "=\"" + startStr + endStr);
			}
			matcherForAttrib.appendTail(sbreplace);
			matcherForTag.appendReplacement(sb, sbreplace.toString() + ">");
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

	/**
	 * @param htmlStr
	 *            html文本
	 * @param searchTag
	 *            要修改的目标标签
	 * @param searchAttrib
	 *            目标标签中的属性
	 * @param newStr
	 *            修改值 不保留原本
	 */
	public String changeHtmlTag(String htmlStr, String searchTag, String searchAttrib, String startStr, String endStr) {
		String regxpForTag = "<\\s*" + searchTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = searchAttrib + "\\s*=\\s*[\"|']([^\"|']+)[\"|']";// "=[\"|']http://([^[\"|']]+)[\"|']";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(htmlStr);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer("<" + searchTag + " ");
			// System.out.println("原本的matcherForTag:" + matcherForTag.group(1));
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));

			if (matcherForAttrib.find()) {
				// System.out.println("原本的matcherForAttrib:" +
				// matcherForAttrib.group(1));
				matcherForAttrib.appendReplacement(sbreplace, startStr + "=\"" + matcherForAttrib.group(1) + "\" " + searchAttrib + "=\"" + matcherForAttrib.group(1) + "\" ");
			}
			matcherForAttrib.appendTail(sbreplace);
			matcherForTag.appendReplacement(sb, sbreplace.toString() + ">");
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

}
