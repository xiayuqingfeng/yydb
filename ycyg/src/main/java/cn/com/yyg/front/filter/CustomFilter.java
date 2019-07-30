package cn.com.yyg.front.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.com.yyg.base.commons.YygConstants;
import cn.com.yyg.base.entity.SysConfigEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.front.service.FrontIndexService;
import cn.com.yyg.front.service.SysConfigService;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.ElUtils;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.utils.WeixinUtils;

import com.google.common.collect.Lists;

/**
 * 自定义过滤器把工具类等注入到request等对象中
 * 
 * @author nibili 2016年5月7日
 * 
 */
@Component
public class CustomFilter extends OncePerRequestFilter {

	// private static Logger logger =
	// LoggerFactory.getLogger(CustomFilter.class);
	/** 是否已初始化 */
	private boolean isExternalUrlsInit = false;
	/** 排除不过滤的链接 */
	private String externalUrlsString = ".*/assets/.*,/user/.*";
	/** 认证要排除的url列表 */
	private List<String> externalUrls;

	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;
	@Autowired
	private FrontIndexService frontIndexService;
	@Autowired
	private SysConfigService sysConfigService;

	public void destroy() {

	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		// 一、工具类注入到request中
		ElUtils.newInstanceToRequest(request);
		// 二、统计商品买入份数
		String nums = (String) request.getSession().getAttribute(YygConstants.KEY_SESSION_BUY_NUMS);
		if (nums == null) {
			request.getSession().setAttribute(YygConstants.KEY_SESSION_BUY_NUMS, frontCommonService.totalBuyNums());
		}
		if (StringUtils.isNoneBlank(request.getParameter("yaoqingUserId"))) {
			request.getSession().setAttribute("yaoqingUserId", request.getParameter("yaoqingUserId"));
		}
		// 商品分类
		@SuppressWarnings("unchecked")
		List<SysDictEntity> pcates = (List<SysDictEntity>) request.getSession().getAttribute("pcates");
		if (pcates == null) {
			pcates = frontIndexService.getProductCates(7);
			request.getSession().setAttribute("pcates", pcates);
		}
		// 公共配置项
		SysConfigEntity sysConfig = (SysConfigEntity) request.getSession().getAttribute("sysConfig");
		if (sysConfig == null) {
			sysConfig = sysConfigService.getById(SysConfigEntity.ID_COMMON);
			if (StringUtils.isBlank((String)request.getSession().getAttribute("sysConfig"))) {
				request.getSession().setAttribute("sysConfig", sysConfig);
			}
		}
		request.getSession().setAttribute("isWeixinBrowerisWeixinBrower", false);
		request.getSession().setAttribute("isWeixinBrower", false);
		request.getSession().removeAttribute("errorNoMobile");

		// 如果用户登录
		if (RequestUtils.getCurrentUser(request) != null) {
			WeixinUtils.extracted(request);
			request.getSession().setAttribute("currentUserId", RequestUtils.getCurrentUser(request).getId());
		} else {
			String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
			if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
				// RequestUtils.setBackUrl(request);
				// 未登录，跳转到登录页
				// response.sendRedirect("/login");
				// return;
			}
		}
		filterChain.doFilter(request, response);
	}

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
