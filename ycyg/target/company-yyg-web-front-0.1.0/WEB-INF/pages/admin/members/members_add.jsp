<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<link rel="stylesheet" type="text/css" href="${vendorsBase }/bootstrap-loading/ladda-themeless.min.css?v=${version}">
<link rel="stylesheet" media="screen" href="${vendorsBase }/boostrap-datetime-picker/css/bootstrap-datetimepicker.min.css?v=${version}">
<%@ include file="../commons/commons_resource_head.jsp"%>
<style>
.red {
	color: red;
}
</style>
</head>
<body>
 <%--顶部 --%>
 <%@ include file="../index/index_top.jsp"%>
  <div class="admin-wrapper">
   <%--左边菜单 --%>
   <%@ include file="../index/index_left.jsp"%>
   <%--begin页面核心内容 --%>
   <div class="admin-content" id="hl-admin-content">
    <div class="admin-content-title admin-content-padding">
     <strong class="am-text-lg am-padding-left-sm"> <a>会员管理</a>
     </strong> / <small> 信息编辑 </small>
    </div>
    <div class="admin-content-info admin-content-padding">
     <div class="col col-md-12">
      <form id="saveForm" action="/admin/members/save" method="POST">
       <input type="hidden" name="id" value="${user.id }">
       <div class="form-group ">
        <label for="phone">用户名</label> <input type="text" id="userName" name="userName" value="${user.userName }" placeholder="用户名" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="phone">昵称</label> <input type="text" id="nickName" name="nickName" value="${user.nickName }" placeholder="昵称" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="user">真实姓名</label> <input type="text" id="trueName" name="trueName" value="${user.trueName }" placeholder="真实姓名" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="password" id="lbl_password">修改密码</label> <input type="text" id="password" name="password" value="" placeholder="密码"
         class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="phone">手机</label> <input type="text" id="mobile" name="mobile" value="${user.mobile }" placeholder="手机" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="email">邮箱</label> <input type="text" id="email" name="email" value="${user.email }" placeholder="邮箱" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="qq">QQ</label> <input type="text" id="qq" name="qq" value="${user.qq }" placeholder="QQ" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="cardNo">身份证号</label> <input id="cardNo" name="cardNo" placeholder="身份证号码" value="${user.cardNo }" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-actions">
        <input type="button" id="btn-save-user" class="am-btn hl-btn-green am-btn-sm" value="保存" autocomplete="off"> <a id="goback"
         class="am-btn am-btn-default am-btn-sm" href="/admin/members">返回</a>
       </div>
       </fieldset>
      </form>
     </div>
    </div>
   </div>
   <%--end页面核心内容 --%>
  </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <%--编辑器 start--%>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/spin.min.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/ladda.min.js?v=${version}"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/bootstrap-datetimepicker.min.js?v=${version}" type="text/javascript"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/locales/bootstrap-datetimepicker.zh-CN.js?v=${version}" type="text/javascript"></script>
 <%--     <script src="${vendorsBase }/bootstrap-switch/bootstrap-switch.min.js" type="text/javascript"></script> --%>
 <%--编辑器 end--%>
 <script type="text/javascript">
		var type = "${param.type}";
		var provinceId = "${user.provinceId}";
		var cityId = "${user.cityId}";
		var areaId = "${user.areaId}";
		var initPwd = "${sysConfig.dataMap['initPwd']}";
	</script>
 <script src="${mimeBase}/scripts/admin/members_add.js?v=${version}" type="text/javascript"></script>
</body>
</html>