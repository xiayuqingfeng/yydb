package cn.com.yyg.front.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

/**
 * httpServletRequestWrapper重写,<br>
 * 主要是重写getParameter方法，过滤Xss攻击代码
 * 
 * @author nibili
 * 
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/** 要过滤的字符 */
	//private static String regex = "</|'|\\\\|\"|;|<|>|\\(|\\)";
	private static String regex = "<script>|</script>";
	
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public static void main(String[] args) {
		String temp = "<script>alert('hello')</script>";
		System.out.println(temp.replaceAll(regex, ""));
		temp = "http://www.baidu.com/s?wd=%E6%89%8B%E6%9C%BA&rsv_spt=1&issp=1&f=8&rsv_bp=0&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=2&rsv_sug4=45&rsv_sug1=1&rsv_sug2=0&inputT=3333";
		System.out.println(temp.replaceAll(regex, ""));
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value != null) {
			value = this.filterParamString(value);
		}
		return value;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> rawMap = super.getParameterMap();
		Map<String, String[]> filteredMap = new HashMap<String, String[]>(rawMap.size());
		Set<String> keys = rawMap.keySet();
		for (String key : keys) {
			String[] rawValue = rawMap.get(key);
			String[] filteredValue = filterStringArray(rawValue);
			filteredMap.put(key, filteredValue);
		}
		return filteredMap;

	}

	protected String[] filterStringArray(String[] rawValue) {
		String[] filteredArray = new String[rawValue.length];
		for (int i = 0; i < rawValue.length; i++) {
			filteredArray[i] = filterParamString(rawValue[i]);
		}
		return filteredArray;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] rawValues = super.getParameterValues(name);
		if (rawValues == null)
			return null;
		String[] filteredValues = new String[rawValues.length];
		for (int i = 0; i < rawValues.length; i++) {
			filteredValues[i] = filterParamString(rawValues[i]);
		}
		return filteredValues;
	}

	/**
	 * 过滤字符品，并返回过滤后的字符串
	 * 
	 * @param rawValue
	 * @return
	 */
	protected String filterParamString(String rawValue) {
		if (rawValue == null) {
			return null;
		} else {
			if (rawValue.startsWith("{") == true || rawValue.startsWith("[") == true) {

			} else {
				rawValue = rawValue.replaceAll(regex, "");
				//rawValue = rawValue.replaceAll("\"", "”");
				//rawValue = rawValue.replaceAll("'", "’");
				//rawValue = StringEscapeUtils.escapeHtml3(rawValue);
				// rawValue = rawValue.replaceAll("http:", "http://");
			}
		}
		return rawValue;
	}

	@Override
	public String getQueryString() {
		String queryString = super.getQueryString();
		if (StringUtils.isNotBlank(queryString) == true) {
			String[] params = queryString.split("&");
			String param;
			for (int i = 0, len = params.length; i < len; i++) {
				param = params[i];
				params[i] = filterParamString(param);
			}
			queryString = StringUtils.join(params, "&");
		}
		return queryString;
	}
}
