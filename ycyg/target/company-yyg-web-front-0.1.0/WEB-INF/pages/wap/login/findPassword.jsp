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
 <h1>密码找回</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container main">
  <div class="user-box">
   <form action="/findPassword/mobile" target="iframeNews" id="forgetform" method="post">
    <div class="form-box">
     <div class="Validform_checktip color01"></div>
     <div class="input">
      <i class="ap-icon icon-phone"></i> <input name="mobile" id="mobile" class="txt" type="text" placeholder="手机号" datatype="m" nullmsg="请输入手机号" errormsg="手机格式不正确" />
     </div>
    </div>
    <div class="ui-clr"></div>
    <div class="input code">
     <i class="ap-icon icon-code"></i> <input class="txt" type="text" placeholder="请输入图形码" name="scode" id="scode" />
    </div>
    <img src="/captcha" class="imgcode" onclick="this.src='/captcha?t='+Math.random()" style="vertical-align: middle; width: 9.4rem;" />
    <div class="form-box">
     <div class="Validform_checktip color01"></div>
     <div class="input code">
      <i class="ap-icon icon-code"></i> <input class="txt" type="text" placeholder="短信验证码" name="sms_code" id="sms_code" />
     </div>
     <input type="button" value="获取短信验证码" onclick="getSmsVerify('sms_password')" id="btnSms" style="font-size: 12px;" />
    </div>
    <input class="btn" name="Submit2" type="submit" value="立 即 找 回">
   </form>
  </div>
 </div>
 <script type="text/javascript" src="${wapBase}/style/Validform_min.js"></script>
 <script type="text/javascript" src="${wapBase}/style/mobile/js/script.js"></script>
 <script type="text/javascript">
		$(function() {
			$("#forgetform").Validform({
				tiptype : function(msg, o, cssctl) {
					validTip(msg, o, cssctl);
				},
				showAllError : true
			});
		})
	</script>
 <iframe name="iframeNews" style="display: none;"></iframe>
</body>
</html>