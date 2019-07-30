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
 <script type="text/javascript" src="${ygBase}/style/jquery-1.8.3.min.js"></script>
 <script type="text/javascript" src="${ygBase}/style/layer/layer.min.js"></script>
 <script type="text/javascript" src="${ygBase}/style/respond.min.js"></script>
 <script type="text/javascript" src="${ygBase}/style/jquery.scrollLoading-min.js"></script>
 <script type="text/javascript" src="${ygBase}/style/common.js"></script>
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <div class="dl-box fn-clear">
  <form action="/findPassword/mobile" target="iframeNews" id="forget_form" method="post">
   <div class="fn-left dl-boxl">
    <div class="form-box fn-clear">
     <label> <font class="label">手机号码</font> ：
     </label> <input name="mobile" id="mobile" type="text" class="inpt-style" nullmsg="请输入您绑定的手机号码" errormsg="手机格式不正确" datatype="m"> <span class="Validform_checktip"></span>
    </div>
    <div class="form-box fn-clear" style="height: auto;">
     <label> <font class="label">验证码</font> ：
     </label> <input type="text" id="scode" name="scode" value="" class="inpt-style" placeholder="请输入图形码" style="width: 190px"> <img src="/captcha" class="imgcode"
      onclick="this.src='/captcha?xrand='+Math.random();" style="width: 95px;">
       <div class="Validform_info" style="display: none;">
        <span class="dec"> <s class="dec1">◆</s> <s class="dec2">◆</s>
        </span>
       </div>
    </div>
    <div class="form-box fn-clear">
     <label> <font class="label">短信验证码</font> ：
     </label> <input name="sms_code" id="sms_code" type="text" nullmsg="请输入短信验证码" errormsg="邮箱格式不正确" class="inpt-style2"> <input type="button" value="获取短信验证码"
      onclick="getSmsVerify('sms_password')" id="btnSms">
    </div>
    <div class="form-box fn-clear">
     <label>&nbsp;</label> <input name="Submit2" type="submit" value="立即找回" class="hy-btn">
    </div>
   </div>
  </form>
  <div class="fn-left dl-boxr">
   <p>我没有${comName }帐号，</p>
   <div class="fn-clear">
    <a href="/regist" class="hy-a1">立即注册</a>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>
<script type="text/javascript" src="${ygBase}/style/Validform_min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#forget_form").Validform({
			tiptype : 3,
			showAllError : true,
			label : ".label"
		});
	});
</script>
 <iframe name="iframeNews" style="display: none;"></iframe>
