<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>修改密码_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_addr.css" />
</head>
<body>
 <%@ include file="../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="baseinfo" />
     <%@ include file="member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <!-- begin right main  -->
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item">
         <a href="/member/">我的夺宝</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item w-crumbs-active">个人资料/修改密码</li>
       </ul>
       <div class="m-user-address" id="pro-view-2" module-id="module-3" module-launched="true">
  
        <div pro="addressedit" class="m-user-address-edit">
         <div pro="addressform" class="m-address-form">
 
         <form  action="/member/changePwdSave" method="post" id="saveForm"> 
          <div class="w-address-form-item">
           <div class="w-address-form-name">  <span class="w-txt-impt">*&nbsp;</span>原密码</div>
           <div class="w-input" >
            <input class="w-input-input" name="oldPwd" type="password" maxlength="20" style="width: 200px;">
           </div>
          </div>
           <div class="w-address-form-item">
           <div class="w-address-form-name">  <span class="w-txt-impt">*&nbsp;</span>新密码</div>
           <div class="w-input" >
            <input class="w-input-input" name="pass1" type="password" maxlength="20" style="width: 200px;">
           </div>
          </div>
           <div class="w-address-form-item">
           <div class="w-address-form-name">  <span class="w-txt-impt">*&nbsp;</span>确认密码</div>
           <div class="w-input" >
            <input class="w-input-input" name="pass2" type="password" maxlength="20" style="width: 200px;">
           </div>
          </div>
          <div class="w-address-form-item">
           <div class="w-address-form-name">&nbsp;</div>
           <button class="w-button w-button-main" type="button" id="btn-save"  style="width: 140px; height: 45px; font-size: 18px; font-weight: normal;">
            <span>保存</span>
           </button>
          </div>
          </form>
         </div>
        </div>
       </div>
      </div>
      <div class="m-user-frame-clear"></div>
       <!-- end right main -->
     </div>
    </div>
   </div>
  </div>
 <%@ include file="../pub/index/index_foot.jsp"%>
 
 <script src="${mimeBase}/scripts/commons/common.js?v=${version}" type="text/javascript"></script>
   <script type="text/javascript">
 
   				//提交保存
				$("#btn-save").click(function() {
					var $oldPwd = $("#saveForm input[name='oldPwd']");
					var $pass1 = $("#saveForm input[name='pass1']");
					if ($oldPwd.val() == "") {
						ygAlert("请输入原密码", function() {
							$oldPwd.focus();
						});
						return false;
					}
					if ($pass1.val() == "") {
						ygAlert("请输入新密码", function() {
							$pass1.focus();
						});
						return false;
					}
					$("#saveForm").ajaxSubmit({
						dataType : "json",
						success : function(data) {
							if (data != undefined) {
								if (data.isSuccess == true) {
									 ygAlert("保存成功!",function(){
										 window.location="/member/baseinfo.html";
									 });
								} else {
									 ygAlert(data.message);
								}
							} else {
								 ygAlert("保存失败!");
							}
						},error:function(){
							 ygAlert("保存失败!");
						}
					});
				});
			</script>
 </div>
</body>
</html>
