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
 <h1>会员注册</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container main">
  <div class="user-box">
   <form id="regForm" method="post">
    <div id="step1">
     <div class="form-box">
      <div class="Validform_checktip color01"></div>
      <div class="input">
       <i class="ap-icon icon-name"></i> <input class="txt" type="text" name="username" placeholder="手机" autocomplete="false" datatype="m|e,*2-40"
        ajaxurl="/regist/check_username" nullmsg="请输入手机号" errormsg="手机格式不正确" />
      </div>
     </div>
     <div class="form-box">
      <div class="Validform_checktip color01"></div>
      <div class="input">
       <i class="ap-icon icon-psw"></i> <input class="txt" type="password" id="password" name="password" placeholder="请输入密码" datatype="*" nullmsg="请设置密码" />
      </div>
     </div>
     <div class="form-box">
      <div class="Validform_checktip color01"></div>
      <div class="input">
       <i class="ap-icon icon-psw"></i> <input class="txt" type="password" name="confirm_password" placeholder="确认密码" recheck="password" datatype="*" nullmsg="请确认密码"
        errormsg="两次输入的密码不一致" />
      </div>
      <input type="text" id="yaoqingUserId" name="yaoqingUserId" value="${yaoqingUserId}" style="display: none;"></input>
     </div>
    </div>
    <div id="step2" style="display: none;">
     <div class="input" style="margin-bottom: 5px;">
      <i class="ap-icon icon-phone"></i> <input class="txt" type="text" placeholder="请输入手机号" name="mobile" id="mobile" value="" />
     </div>
     <div style="padding: 0 0 10px 0;">
      保持通话，畅通无阻，<b class="color01" style="font-size: 12px;">发货验证</b>，准确送达！
     </div>
     <div class="ui-clr"></div>
     <div class="input code">
      <i class="ap-icon icon-code"></i> <input class="txt" type="text" placeholder="请输入图形码" name="scode" id="scode" style="width: 100px;" />
     </div>
     <img src="/captcha" class="imgcode" onclick="this.src='/captcha?t='+Math.random();" style="vertical-align: middle; width: 9.2rem;" />
     <div class="input code">
      <i class="ap-icon icon-code"></i> <input class="txt" type="text" placeholder="请输入验证码" name="sms_code" id="sms_code" />
     </div>
     <input type="button" value="获取短信" onclick="getSmsVerify('sms_register')" id="btnSms" style="font-size: 12px;" />
    </div>
    <input class="btn" type="submit" value="提 交 注 册" />
    <div class="ubox-b ui-clr">
     <p class="ui-right">
      <a href="/login">已有账号？立即登录</a>
     </p>
    </div>
   </form>
   <div id="regSuc" style="display: none;">
    <div style="text-align: center; padding: 30px 0 0;">
     <h2 style="font-size: 20px;" class="msg_">恭喜您成为${comName}尊贵的会员！</h2>
     <div style="text-align: center; padding: 10px 0; font-size: 14px;" class="msg_link">
      <a href="/member" class="color02" style="margin: 0 5px;">会员中心</a> <a href="/login" class="color02" style="margin: 0 5px;">返回上一页</a>
     </div>
    </div>
   </div>
  </div>
 </div>
 <script type="text/javascript" src="${wapBase}/style/jquery.form.js"></script>
 <script type="text/javascript" src="${wapBase}/style/Validform_min.js"></script>
 <script type="text/javascript" src="${wapBase}/style/mobile/js/script.js"></script>
 <script type="text/javascript">
		var step = 1;
		//触点验证码
		var typeCod = "{$smarty.const.TYPE_CODE}";

		$(function() {
			$("#regForm").Validform({
				tiptype : function(msg, o, cssctl) {
					validTip(msg, o, cssctl);
				},
				showAllError : true,
				beforeSubmit : function() {
					//触点验证码
					if (typeCod == 'tou' && step == 2 && tou_submit('regForm') == false) {
						return false;
					}

					var D = $('#regForm').formSerialize();
					D = D + '&step=' + step;
					$.post("/regist/submit", D, function(data) {
						if (data.isSuccess == false) {
							layer.alert(data.message, 8);
						} else {
							if (step == 1) {
								step = 2;
								$('#step1').hide();
								$('#step2').show();
								$('#mobile').val(data.message);
							} else if (step == 2) {
								step = 2;
								$('#regForm').hide();
								$('#regSuc').show();
							}
						}
					}, "json");
					return false;
				}
			});
		})
	</script>
</body>
</html>