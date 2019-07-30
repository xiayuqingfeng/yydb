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
 <div id="logo" class="fn-clear">
  <h2>重置密码</h2>
 </div>
 <div class="dl-box fn-clear">
  <form action="/findPassword/resetPasswordAjax" target="iframeNews" id="reset_form" method="post">
   <div class="fn-left dl-boxl">
    <div class="form-box fn-clear">
     <label id="Label1"><font class="label">新密码</font>：</label><input name="password" type="password" class="inpt-style Validform_error" autocomplete="false" datatype="*"
      nullmsg="请填写新密码！"> <span class="Validform_checktip Validform_wrong">请填写新密码！</span>
    </div>
    <div class="form-box fn-clear">
     <label id="Label1"><font class="label">确认密码</font>：</label><input name="confirm_password" recheck="password" type="password" datatype="*"
      class="inpt-style Validform_error" nullmsg="请确认密码！"> <span class="Validform_checktip Validform_wrong">请确认密码！</span>
    </div>
    <div class="form-box fn-clear">
     <label id="Label1">&nbsp;</label><input name="Submit" type="submit" value="确认重置" class="hy-btn">
    </div>
   </div>
  </form>
  <div class="fn-left dl-boxr">
   <p>我没有${comName}帐号，</p>
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
