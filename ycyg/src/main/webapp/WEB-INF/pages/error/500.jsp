
<%
	response.setStatus(HttpServletResponse.SC_OK); //这句也一定要写,不然IE不会跳转到该页面
	String path = request.getContextPath();
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>500</title>
</head>
<body>
    <div class="error_remind_bg_500">
        <div class="error_remind">
            <img alt="" src="${mimeBase }/assets/images/500.png" />
            <a class="return" href="/">首页</a>
            <a onclick="javascript:history.go(-1);">返回</a>
        </div>
    </div>
</body>
</html>