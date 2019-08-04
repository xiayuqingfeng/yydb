<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="serverPort" value=":${pageContext.request.serverPort}" />
<c:if test="${serverPort==':80'}">
	<c:set var="serverPort" value="" />
</c:if>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${contextPath=='/'}">
	<c:set var="contextPath" value="" />
</c:if>
<c:set var="base" value="${pageContext.request.scheme}://${pageContext.request.serverName}${serverPort}${contextPath}" />
<%--版本变量 --%>
<c:set var="version" value="14"></c:set>
<c:set var="comName" value="幸运一点"></c:set>
<c:set var="comTel" value="0592-5630282"></c:set>

<%--定义资源路径变量 --%>
<c:set var="mimeBase" value="/assets" />
<c:set var="vendorsBase" value="/assets/vendors" />
<c:set var="uploadImageBase" value="http://dapeng.nat300.top/assets"></c:set>

<%--云购资源路径变量 --%>
<c:set var="ygBase" value="/assets/yg" />
<c:set var="wapBase" value="/assets/wap" />
<c:set var="ycDcBase" value="/assets/1y" />

