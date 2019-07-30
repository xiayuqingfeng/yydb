package cn.com.yyg.front.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.com.yyg.front.utils.ElUtils;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;

/**
 * 自定义过滤器把工具类等注入到request等对象中
 * 
 * @author nibili 2016年5月7日
 * 
 */
@Component
public class AdminFilter extends OncePerRequestFilter {

	// private static Logger logger =
	// LoggerFactory.getLogger(CustomFilter.class);
	/** 是否已初始化 */
	private boolean isExternalUrlsInit = false;
	/** 排除不过滤的链接 */
	private String externalUrlsString = ".*/assets/.*,/admin/login,/admin/login/check,/admin/login/logout";
	/** 认证要排除的url列表 */
	private List<String> externalUrls;

	@Override
	public void destroy() {

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (RequestUtils.getCurrentAdminUser(request) == null) {
			// 未登录，跳转到登录页
			response.sendRedirect("/admin/login");
		} else {
			// 已登录
			// 一、工具类注入到request中
			ElUtils.newInstanceToRequest(request);
			//
			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		if (isExternalUrlsInit == false) {
			if (StringUtils.isNotBlank(externalUrlsString) == true) {
				externalUrls = Lists.newArrayList(externalUrlsString.split(","));
			}
			// 如果要排除过滤的路径变量是空的，则置为true，这样，下次就不会再执行这一段被始化代码
			isExternalUrlsInit = true;
		}
		String uri = request.getRequestURI();
		if (CollectionUtils.isEmpty(externalUrls) == false) {
			// 不为空，匹配的都不过滤，直接获取网站资源
			for (String patten : externalUrls) {
				if (uri.matches(patten) == true) {
					return true;
				}
			}
		}
		return false;
	}
}
