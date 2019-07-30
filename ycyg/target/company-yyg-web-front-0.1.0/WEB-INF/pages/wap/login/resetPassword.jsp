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
 <h1>重置密码</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container main">
  <div class="user-box">
   <form action="/findPassword/resetPasswordAjax" target="iframeNews" id="resetform" method="post">
    <div class="form-box">
     <div class="Validform_checktip color01"></div>
     <div class="input">
      <i class="ap-icon icon-psw"></i> <input name="password" id="password" class="txt" type="password" placeholder="新密码" autocomplete="false" datatype="*" nullmsg="请输入新密码">
     </div>
    </div>
    <div class="form-box">
     <div class="Validform_checktip color01"></div>
     <div class="input">
      <i class="ap-icon icon-psw"></i> <input name="confirm_password" class="txt" type="password" placeholder="确认密码" autocomplete="false" datatype="*" nullmsg="请确认密码"
       errormsg="两次输入的密码不一致">
     </div>
    </div>
    <input class="btn" name="Submit" type="submit" value="确 认 重 置">
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