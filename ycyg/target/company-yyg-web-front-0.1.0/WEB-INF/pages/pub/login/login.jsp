<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>会员登录_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
 <link type="text/css" rel="stylesheet" href="${ygBase}/style/css/member.css" />
 <link type="text/css" rel="stylesheet" href="${ygBase}/style/css/validform.css" />
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <div class="dl-box login-box fn-clear">
  <form action="/login/check" id="login_form" method="post">
   <input name="username" type="text" class="input input-user" autocomplete="false" placeholder="输入手机号" value="" />
   <input name="password" type="password" class="input input-pass" value="" placeholder="输入密码" />
   <div class="dl-btna fn-clear">
<%--     <div class="ui-left oauth">
     <a href="javascript:void(0);" onclick="oauth('oauth/qq-open=1')">
      <img src="${ygBase}/style/images/qq.png" />
      QQ登录
     </a>
     <a href="javascript:void(0);" onclick="oauth('oauth/weibo-open=1')">
      <img src="${ygBase}/style/images/weibo.png" />
      微博登录
     </a>
     <a
      href="../../open.weixin.qq.com/connect/qrconnect-appid=&redirect_uri=http-3A-2F-2Fapp3.yyaiwopai.com-2F&response_type=code&scope=snsapi_login&state=22138#wechat_redirect">
      <img src="${ygBase}/style/images/wechat.png" />
      微信登录
     </a>
    </div> --%>
    <a href="regist" style="margin-right: 10px;" class="ui-left">注册会员</a>
    <a href="/findPassword" class="ui-left">忘记密码？</a>
    <span id="msgTip" style="margin-left: 30px;"></span>
   </div>
   <input type="hidden" name=backUrl value="${backUrl }">
    <input name="Submit" type="submit" value=" " class="hy-btn" id="submit-button" />
  </form>
  <div class="fn-left dl-boxr"></div>
 </div> 
 <script type="text/javascript">
 <c:if test="${not empty error}">
 alert("${error}");
 </c:if>
</script>
 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>
