<%--会员管理首页 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" type="text/css" href="${vendorsBase }/bootstrap-loading/ladda-themeless.min.css?v=${version}">
<link rel="stylesheet" media="screen" href="${vendorsBase }/boostrap-datetime-picker/css/bootstrap-datetimepicker.min.css?v=${version}">
</head>
<body>
 <%--顶部 --%>
 <%@ include file="../index/index_top.jsp"%>
 <div class="admin-wrapper">
  <%--左边菜单 --%>
  <%@ include file="../index/index_left.jsp"%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">会员管理</strong>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-4 am-u-md-6">
     <div class="am-btn-toolbar am-fl am-padding-left-sm">
      <!--       <a class="am-btn hl-btn-green am-btn-sm" id="pf-add-member-btn">注册新会员</a> -->
      <a class="am-btn hl-btn-green  am-btn-sm" href="/admin/members/add">注册新会员</a>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="am-u-sm-12">
     <table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-members">
      <thead>
      </thead>
      <tbody id="tbody">
      </tbody>
     </table>
    </div>
   </div>
  </div>
 </div>
 <%--添加用户弹出框 --%>
 <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-user">
  <div class="modal-dialog" role="document">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-label="Close">
      <span aria-hidden="true">&times;</span>
     </button>
     <!--      <h4 class="modal-title" id="myModalLabel">注册新会员</h4> -->
     <a class="am-btn hl-btn-green  am-btn-sm" href="/admin/members/add">注册新会员</a>
    </div>
    <div class="modal-body">
     <div class="wz-panel">
      <form id="saveForm" action="/admin/members/save" method="POST" enctype="multipart/form-data">
       <input type="hidden" name="id" id="id" value="">
       <div class="form-group ">
        <label for="phone">用户名</label> <input type="text" id="userName" name="userName" value="" placeholder="用户名" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="user">昵称</label> <input type="text" id="nickName" name="nickName" value="" placeholder="昵称" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="user">真实姓名</label> <input type="text" id="trueName" name="trueName" value="" placeholder="真实姓名" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="password" id="lbl_password">修改密码</label> <input type="text" id="password" name="password" value="" placeholder="密码" class="form-control"
         autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="phone">手机号</label> <input type="text" id="mobile" name="mobile" value="" placeholder="手机号" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="email">邮箱</label> <input type="text" id="email" name="email" value="" placeholder="邮箱" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="qq">QQ</label> <input type="text" id="qq" name="qq" value="" placeholder="QQ" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="address">所在地区</label>
       </div>
       <div class="form-group ">
        <label for="address">地址</label> <input type="text" id="address" name="address" value="" placeholder="地址" class="form-control" autocomplete="off">
       </div>
       <div class="form-group ">
        <label for="accountBalance">账户余额（元）</label> <input type="text" id="accountBalance" name="accountBalance" value="0" placeholder="账户余额" class="form-control"
         autocomplete="off">
       </div>
       <div class="form-group ">
        <label>是否内部</label> <input type="radio" name="innerUser" value="false">否 <input type="radio" name="innerUser" value="true">是
       </div>
      </form>
     </div>
    </div>
    <div class="modal-footer">
     <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
     <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="btn-save-user">
      <span class="ladda-label">保存</span> <span class="ladda-spinner"></span>
     </button>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
  <%@ include file="../userAccountDetail/userAccountDetail_select-dialog.jsp"%>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/spin.min.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/ladda.min.js?v=${version}"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/bootstrap-datetimepicker.min.js?v=${version}" type="text/javascript"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/locales/bootstrap-datetimepicker.zh-CN.js?v=${version}" type="text/javascript"></script>
 <script src="${mimeBase}/scripts/admin/members.js?v=${version}" type="text/javascript"></script>
</body>
</html>