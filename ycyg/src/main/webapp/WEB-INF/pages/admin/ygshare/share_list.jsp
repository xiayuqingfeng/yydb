<%--晒单管理首页 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" type="text/css" href="${vendorsBase }/bootstrap-loading/ladda-themeless.min.css?v=${version}">
<link rel="stylesheet" media="screen" href="${vendorsBase }/bootstrap-switch/bootstrap-switch.min.css?v=${version}">
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
    <strong class="am-text-lg am-padding-left-sm">晒单管理</strong>
   </div>
   <div class="am-cf am-padding-top-sm hidden">
    <div class="am-u-sm-4 am-u-md-6">
     <div class="am-btn-toolbar am-fl am-padding-left-sm">
      <a class="am-btn hl-btn-green am-btn-sm" id="pf-add-member-btn">注册新晒单</a>
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
 <%--审核晒单弹出框 --%>
 <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-share">
  <div class="modal-dialog" role="document">
   <div class="modal-content">
    <input type="hidden" name="id" value="">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-label="Close">
      <span aria-hidden="true">&times;</span>
     </button>
     <h4 class="modal-title" id="myModalLabel">审核晒单</h4>
    </div>
    <div class="modal-body">
     <div class="wz-panel">
      <form action="/admin/ygshare/save.json" method="GET" id="saveForm">
       <input type="hidden" name="id" id="id" value="">
       <div class="form-group ">
        <label for="title">标题</label> <input id="title" name="title" " placeholder="" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group ">
        <label for="content">内容</label>
        <textarea rows="" cols="" id="content" name="content" placeholder="内容" class="form-control"></textarea>
        <p class="help-inline text-info"></p>
       </div>
       <div>
        <a id="photos"> </a>
       </div>
      </form>
     </div>
    </div>
    <div class="modal-footer">
     <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
     <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="btn-save-user">
      <span class="ladda-label" id="passOrForbidden">通过</span> <span class="ladda-spinner"></span>
     </button>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/spin.min.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/ladda.min.js?v=${version}"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/bootstrap-datetimepicker.min.js?v=${version}" type="text/javascript"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/locales/bootstrap-datetimepicker.zh-CN.js?v=${version}" type="text/javascript"></script>
 <script src="${vendorsBase }/bootstrap-switch/bootstrap-switch.min.js" type="text/javascript"></script>
 <script src="${mimeBase}/scripts/admin/ygshare.js?v=${version}" type="text/javascript"></script>
</body>
</html>