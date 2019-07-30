<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
</head>
<style>
.admin-content {
	padding-left: 0px!important;
}
</style>
<body>
 <%--顶部 --%>
 <div class="admin-wrapper">
  <%--左边菜单 --%>
  <%--   <%@ include file="../index/index_left.jsp"%> --%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">充值管理</strong>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-12 am-u-md-3">
     <div class="am-btn-toolbar am-fl am-padding-left-sm">
      <%--       <a class="am-btn hl-btn-green  am-btn-sm" href="/admin/product/add?type=${param.type }"">新建商品</a> --%>
     </div>
     <div class="am-form-group">
      <div class="am-input-group am-input-group-sm">
       <input type="text" id="amount" name="amount" value="${param.amount }" placeholder="给${user.userName }充值" class="am-form-field" autocomplete="off"
        style="min-width: 150px;"> <span class="am-input-group-btn">
        <button class="am-btn am-btn-success" type="button" autocomplete="off" id="pf-btn-amount">
         <span class="glyphicon glyphicon-yen" aria-hidden="true">确认</span>
        </button>
       </span>
      </div>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-md-6 am-cf">
     <div class="am-fr">
      <div class="am-input-group am-input-group-sm">
       <form action="/admin/payRecord" method="GET" class="form-inline" role="form"></form>
      </div>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-sm-12">
     <table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-payRecord">
      <thead>
      </thead>
      <tbody id="tbody">
      </tbody>
     </table>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script type="text/javascript" src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script type="text/javascript" src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script type="text/javascript">
		var userId = "${param.userId}";
	</script>
 <script src="${vendorsBase}/bootstrap-loading/ladda.min.js?v=${version}"></script>
 <script src="${mimeBase}/scripts/admin/userAccountDetail.js?v=${version}" type="text/javascript"></script>
</body>
</html>