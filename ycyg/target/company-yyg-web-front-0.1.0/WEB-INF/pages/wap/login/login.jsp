<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../commons/commons_resource_head.jsp"%>
 <title>会员中心_${comName}</title>
 <meta charset="utf-8">
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=2,user-scalable=no">
   <link rel="stylesheet" href="${wapBase}/style/mobile/css/common.css">
    <link rel="stylesheet" href="${wapBase}/style/mobile/css/font/iconfont.css">
     <script src="${wapBase}/style/jquery-1.8.3.min.js"></script>
     <script type="text/javascript" src="${wapBase}/style/layer/layer.min.js"></script>
     <script type="text/javascript" src="${wapBase}/style/common.js"></script>
</head>
<body>
 <header id="header2">
 <h1>会员登录</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container main">
  <div class="user-box">
   <form action="/login/check" id="loginform" method="post">
    <div class="form-box">
     <div class="Validform_checktip color01"></div>
     <div class="input">
      <i class="ap-icon icon-name"></i> <input name="username" value="" class="txt" type="text" placeholder="请输入手机" autocomplete="false" datatype="*" nullmsg="请输入手机" />
     </div>
    </div>
    <div class="form-box">
     <div class="Validform_checktip color01"></div>
     <div class="input">
      <i class="ap-icon icon-psw"></i> <input name="password" class="txt" value="" type="password" placeholder="输入密码" datatype="*" nullmsg="请输入密码}" />
     </div>
    </div>
    <input type="hidden" name=backUrl value="${backUrl }"> <input class="btn" name="Submit" type="submit" value="立 即 登 录">
      <div class="ubox-b ui-clr">
       <%--      <a href="javascript:void(0);" onclick="oauth('oauth/qq@open=1')"><img src="${wapBase}/style/images/qq.png" /> QQ登录</a> <a href="javascript:void(0);" --%>
       <%--       onclick="oauth('oauth/weibo@open=1')"><img src="${wapBase}/style/images/weibo.png" /> 微博登录</a> --%>
       <p class="ui-right">
        <a href="/findPassword" class="color04">忘记密码？</a> <a href="/regist" class="color04">注册账号</a>
       </p>
      </div>
   </form>
  </div>
 </div>
 <script type="text/javascript" src="${wapBase}/style/Validform_min.js"></script>
 <script type="text/javascript" src="${wapBase}/style/mobile/js/script.js"></script>
 <script type="text/javascript">
		$(function() {
			$("#loginform").Validform({
				tiptype : function(msg, o, cssctl) {
					validTip(msg, o, cssctl);
				},
				showAllError : true
			});
		})
	</script>
 <script type="text/javascript">
		<c:if test="${not empty error}">
		alert("${error}");
		</c:if>
	</script>
 <iframe name="iframeNews" style="display: none;"></iframe>
</body>
</html>