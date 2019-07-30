<%--订单管理首页 --%>
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
    <strong class="am-text-lg am-padding-left-sm">订单管理</strong>
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
 <%--发货弹出框 --%>
 <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-share">
  <div class="modal-dialog" role="document">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-label="Close">
      <span aria-hidden="true">&times;</span>
     </button>
     <h4 class="modal-title" id="myModalLabel">发货</h4>
    </div>
    <div class="modal-body">
     <div class="wz-panel">
      <form action="/admin/ygOrder/sureSend.json" method="GET" id="saveForm">
       <input type="hidden" name="id" id="id" value="">
       <div class="form-group ">
        <label for="expComId">快递公司</label> 
        <select id="expComId" name="expComId" class="form-control">
            <option value="">选择快递公司</option>
            <option value="0">虚拟物品</option>
           <c:forEach var="item" items="${expressComs}">
            <option value="${item.id}">${ item.name}</option>
           </c:forEach>
          </select>
       </div>
       <div class="form-group ">
        <label for="expNo">快递单号</label> <input id="expNo" name="expNo" placeholder="" class="form-control" autocomplete="off">
  
       </div>
       <div class="form-group ">
        <label for="sendRemark">发货备注</label>
        <input id="sendRemark" name="sendRemark" " placeholder="" class="form-control" autocomplete="off">
       </div>
      </form>
     </div>
    </div>
    <div class="modal-footer">
     <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
     <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="btn-send">
      <span class="ladda-label">发货</span> <span class="ladda-spinner"></span>
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
 <script src="${mimeBase}/scripts/admin/ygOrder.js?v=${version}" type="text/javascript"></script>
</body>
</html>