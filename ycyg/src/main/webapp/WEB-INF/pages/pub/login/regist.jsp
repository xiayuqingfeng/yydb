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
 <script type="text/javascript" src="${ygBase}/style/jquery.scrollLoading-min.js"></script>
 <script type="text/javascript" src="${ygBase}/style/common.js"></script>
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <div class="dl-box regist-box fn-clear">
  <div class="regnav fn-clear">
   <ul class="ul1">
    <li class="li0"><i></i> <span>欢迎注册${comName}</span></li>
    <li class="li1 hover"><i></i> <span>填写帐户信息</span></li>
    <li class="li2"><i></i> <span>验证帐户信息</span></li>
    <li class="li3"><i></i> <span>注册成功</span></li>
   </ul>
  </div>
  <form id="regForm" method="post">
   <div class="fn-left dl-boxl" id="step1">
    <div class="form-box fn-clear">
     <label> <font class="label">手机</font> ：
     </label> <input type="text" class="inpt-style icon-user" id="username" name="username" value="${username}" autocomplete="false" datatype="m|e,*2-40"
      ajaxurl="/regist/check_username" nullmsg="请填写手机号" errormsg="手机格式不正确"> <span>*</span>
      <div class="Validform_info" style="left: 567px; top: 452px; display: none;">
       <span class="Validform_checktip Validform_right"></span> <span class="dec"> <s class="dec1">◆</s> <s class="dec2">◆</s>
       </span>
      </div>
    </div>
    <div class="form-box fn-clear">
     <label> <font class="label">设置密码</font> ：
     </label> <input type="password" id="password" name="password" value="${password}" datatype="*" class="inpt-style icon-pass" nullmsg="请设置密码"> <span>*</span>
      <div class="Validform_info" style="left: 567px; top: 501px; display: none;">
       <span class="Validform_checktip Validform_right"></span> <span class="dec"> <s class="dec1">◆</s> <s class="dec2">◆</s>
       </span>
      </div>
    </div>
    <div class="form-box fn-clear">
     <label> <font class="label">确认密码</font> ：
     </label> <input type="password" id="confirm_password" name="confirm_password" value="${confirm_password}" recheck="password" datatype="*" class="inpt-style icon-pass"
      nullmsg="请确认密码！"> <span>*</span>
      <div class="Validform_info" style="left: 567px; top: 550px; display: none;">
       <span class="Validform_checktip Validform_right"></span> <span class="dec"> <s class="dec1">◆</s> <s class="dec2">◆</s>
       </span>
      </div>
    </div>
    <input type="text" id="yaoqingUserId" name="yaoqingUserId" value="${yaoqingUserId}" style="display: none;"></input>
    <div class="fn-clear hy-zcxy">
     <input name="agree" type="checkbox" value="1" datatype="*" nullmsg="请同意服务协议" checked=""> 我已阅读并同意《<a href="javascript:;" id="Agree">${comName}用户服务协议</a> 》 
    </div>
    <div class="form-box fn-clear">
     <label>&nbsp;</label> <input name="Submit" type="submit" value="立即注册" class="hy-btn">
    </div>
   </div>
   <div class="fn-left dl-boxl" id="step2" style="display: none">
    <div class="form-box fn-clear">
     <label> <font class="label">手机号</font> ：
     </label> <input name="mobile" id="mobile" value="" type="text" class="inpt-style"> <span>*</span>
    </div>
    <div class="form-box fn-clear" style="height: auto;">
     <label> <font class="label">验证码</font> ：
     </label> <input type="text" id="scode" name="scode" value="${scode}" class="inpt-style" placeholder="请输入图形码" style="width: 190px"> <img src="/captcha" class="imgcode"
      onclick="this.src='/captcha?xrand='+Math.random();" style="width: 95px;">
       <div class="Validform_info" style="display: none;">
        <span class="Validform_checktip"></span> <span class="dec"> <s class="dec1">◆</s> <s class="dec2">◆</s>
        </span>
       </div>
    </div>
    <div class="form-box fn-clear">
     <label>手机验证码：</label> <input type="text" name="sms_code" id="sms_code" class="inpt-style" value="${sms_code}" style="width: 170px;"> <br>
       <p style="padding: 5px 0 0 75px;">
        <input type="button" value="获取短信验证码" onclick="getSmsVerify('sms_register')" id="btnSms" class="inpt2 hy-btn2">
       </p>
    </div>
    <div class="fn-clear" style="margin-bottom: 10px;"></div>
    <div class="form-box fn-clear">
     <label>&nbsp;</label> <input name="Submit" type="submit" value="确认注册" class="hy-btn" id="submit-button">
    </div>
   </div>
  </form>
  <div id="regSuc" style="display: none;">
   <div style="text-align: center; padding: 20px 0;">
    <h2 style="font-size: 30px;" class="msg_">恭喜您成为${comName}尊贵的会员！</h2>
    <div style="text-align: center; padding: 10px 0; font-size: 24px;" class="msg_link">
     <a href="/member" class="color02" style="margin: 0 5px;">会员中心</a> <a href="/login" class="color02" style="margin: 0 5px;">返回上一页</a>
    </div>
   </div>
  </div>
 </div>
 <div class="layer_agree">
  <div class="con">
   <h4 style="text-align: center;" class="color01">
    <span style="font-size: 18px;">用户服务协议</span><br />
   </h4>
   ${xieyiContent }
  </div>
 </div>
 <script type="text/javascript" src="${ygBase}/style/theme_01/css/jquery.scrollTo.js"></script>
 <script type="text/javascript" src="${ygBase}/style/theme_01/css/common_02.js"></script>
 <script type="text/javascript" src="${ygBase}/style/Validform_min.js"></script>
 <script type="text/javascript" src="${ygBase}/style/jquery.form.js"></script>
 <script type="text/javascript" src="${ygBase}/style/foot.js"></script>
 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>
<script type="text/javascript">
	var step = 1;
	//触点验证码
	var typeCod = "ss";

	$(function() {
		$("#regForm").Validform({
			tiptype : function(msg, o, cssctl) {
				validTip(msg, o, cssctl);
			},
			showAllError : true,
			label : ".label",
			beforeSubmit : function() {
				//触点验证码
				if (typeCod == 'tou' && step == 2 && tou_submit('regForm') == false) {
					return false;
				}

				var D = $('#regForm').formSerialize();
				D = D + '&step=' + step;
				$.post("/regist/submit", D, function(data) {
					if (data.isSuccess == false) {
						if (typeCod == 'tou')
							is_checked = false;
						layer.alert(data.message, 8);
					} else {
						if (step == 1) {
							step = 2;
							$('#step1').hide();
							$('#step2').show();
							$('#mobile').val(data.message);
							$('.regnav ul li').removeClass('hover');
							$('.regnav ul .li2').addClass('hover');
						} else if (step == 2) {
							$('#step1_form').hide();
							$('#step2').hide();
							$('.regist-box').css('background', 'none');
							$('#regSuc').show();
							$('.regnav ul li').removeClass('hover');
							$('.regnav ul .li3').addClass('hover');
						}
					}
				}, "json");
				return false;
			}
		});
		$('#Agree').bind('click', function() {
			$.layer({
				type : 1,
				shade : [ 0.5, '#000' ],
				shadeClose : true,
				fix : true,
				area : [ 'auto', 'auto' ],
				title : false,
				border : [ 0 ],
				page : {
					dom : '.layer_agree'
				}
			});
		})
	});
</script>
