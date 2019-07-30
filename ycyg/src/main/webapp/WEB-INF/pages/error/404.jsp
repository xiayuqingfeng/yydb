<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>404</title>
</head>
 <%@ include file="../commons/taglibs.jsp"%>
<body class="error_body" style="text-align: center;padding-top: 20px;">
    <a href="/"><img src="${mimeBase }/assets/images/404.png"></a>
    <script type="text/javascript">
        setTimeout(function(){ location.href='/' },5000);
    </script>
</body>
</html>