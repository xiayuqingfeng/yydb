<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>个人资料_${comName}</title>
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
        <li class="w-crumbs-item w-crumbs-active">个人资料</li>
       </ul>
       <div class="m-user-address" id="pro-view-2" module-id="module-3" module-launched="true">
  
        <div pro="addressedit" class="m-user-address-edit">
         <div pro="addressform" class="m-address-form">
         <form  action="/member/uploadHead" method="post" enctype="multipart/form-data"> 
          <div pro="address" class="w-address-form-item" style="margin-bottom: 0px;">
           <div class="w-address-form-name address">
            头像
           </div>
           <div class="w-input w-input-textarea w-address-area" >
           <c:set var="headPhoto" value="/assets/images/member/nohead.jpeg"/>
           <c:if test="${not empty userInfo.headPhotoPath }">
           <c:set var="headPhoto" value="${userInfo.headPhotoPath}"/>
           </c:if>
            <img class="m-user-comm-infoBox-face" id="imgHeadPhoto" src="${headPhoto }" width="160" height="160"/>
            <span class="w-user-avatarEdit-tips" id="changeHeadPhoto">点击更换头像</span>
           </div>
          </div>
          <input type="file" accept="image/*" id="headPhoto" style="margin-left:118px;width:75px" name="headPhoto"/>
          <input type="submit" id="btn-upload" style="display:none" value="上传"/>
         </form>
         <form  action="/member/save" method="post" id="saveForm"> 
          <div class="w-address-form-item">
           <div class="w-address-form-name">  <span class="w-txt-impt">*&nbsp;</span>昵称</div>
           <div class="w-input" >
            <input class="w-input-input" name="nickName" type="text" value="${userInfo.nickName }" maxlength="6" style="width: 300px;">
           </div>
          </div>
          <div pro="receiver" class="w-address-form-item">
           <div class="w-address-form-name">
            <span class="w-txt-impt">*&nbsp;</span>
            手机号码
           </div>
           <div class="w-input">
            <input class="w-input-input" name="mobile" type="text" value="${userInfo.mobile }" placeholder="" style="width: 300px;">
           </div>
          </div>
          <div pro="receiver" class="w-address-form-item">
           <div class="w-address-form-name">
            电子邮箱
           </div>
           <div class="w-input">
            <input class="w-input-input" name="email" type="text" value="${userInfo.email }" placeholder="" style="width: 300px;">
           </div>
          </div>
          <div pro="receiver" class="w-address-form-item">
           <div class="w-address-form-name">
            真实姓名
           </div>
           <div class="w-input">
            <input class="w-input-input" name="trueName" type="text" value="${userInfo.trueName }" placeholder="" style="width: 300px;">
           </div>
          </div>
          <div pro="identity" class="w-address-form-item">
           <div class="w-address-form-name">身份证号码</div>
           <div class="w-input">
            <input class="w-input-input" name="cardNo" type="text" value="${userInfo.cardNo }" maxlength="18" style="width: 300px;">
           </div>
          </div> 
          <div class="w-address-form-item">
           <div class="w-address-form-name">&nbsp;</div>
           <button class="w-button w-button-main" type="button" id="btn-save"  style="width: 140px; height: 45px; font-size: 18px; font-weight: normal;">
            <span>保存</span>
           </button>
            <button class="w-button w-button-main" type="button" id="btn-change-pwd"  style="width: 140px; height: 45px; font-size: 18px; font-weight: normal;">
            <span>修改密码</span>
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
				if($.browser.msie){
					$("#headPhoto").show();
				}else{
					$("#headPhoto").hide();
	   				//非ie切换有效果
					$(".m-user-comm-infoBox-face,#changeHeadPhoto").hover(function() {
						$("#changeHeadPhoto").show();
					}, function() {
						$("#changeHeadPhoto").hide();
					});
					$("#changeHeadPhoto").click(function(){
						$("#headPhoto").trigger("click");
					});
				}
				$("#headPhoto").change(function(event){
					var filesize=getFileSize("headPhoto");
					 if(filesize> 1024 * 1024 * 1) { 
						 ygAlert('图片大小不能超过 1MB!'); 
						 return false; 
					}
					$("#btn-upload").click();
				      
				});  
   				//提交保存
				$("#btn-save").click(function() {
					var $nickname = $("#saveForm input[name='nickName']");
					var $mobile = $("#saveForm input[name='mobile']");
					if ($nickname.val() == "") {
						ygAlert("请输入昵称", function() {
							$nickname.focus();
						});
						return false;
					}
					if ($mobile.val() == "") {
						ygAlert("请输入手机号码", function() {
							$mobile.focus();
						});
						return false;
					}
					$("#saveForm").ajaxSubmit({
						dataType : "json",
						success : function(data) {
							if (data != undefined) {
								if (data.isSuccess == true) {
									 ygAlert("保存成功!");
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

				$("#btn-change-pwd").click(function() {
					window.location="/member/changePwd.html";
				});
			</script>
 </div>
</body>
</html>
