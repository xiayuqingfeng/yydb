<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<meta name="Keywords" content="${seoKeywords}" />
<meta name="Description" content="${seoDescription}" />
<%@ include file="../commons/commons_resource_head.jsp"%>
</head>
<body>
    <%--顶部 --%>
    <%@ include file="index_top.jsp"%>
    <div class="admin-wrapper">
        <%--左边菜单 --%>
        <%@ include file="index_left.jsp"%>
        <%--适配手机时的菜单 --%>
        <%@ include file="index_sm_menu.jsp"%>
    </div>
    <%@ include file="../commons/commons_resource_foot.jsp"%>
</body>
</html>
