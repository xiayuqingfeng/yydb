package cn.com.yyg.front.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * xss过滤器
 * 
 * @author nibili
 * 
 */
public class XssProjectFilter implements Filter {

	private String excludeURL;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeURL = filterConfig.getInitParameter("excludeURL");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String requestURI = ((HttpServletRequest) request).getRequestURI();
		if (StringUtils.isNotBlank(this.excludeURL) == true && requestURI.contains(this.excludeURL) == true) {
			chain.doFilter(request, response);
		} else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}

	}

	@Override
	public void destroy() {

	}

	public String getExcludeURL() {
		return excludeURL;
	}

	public void setExcludeURL(String excludeURL) {
		this.excludeURL = excludeURL;
	}

}
