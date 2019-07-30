<%--商品管理-参看云购记录 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
</head>
<body>
 <%--顶部 --%>
 <%@ include file="../index/index_top.jsp"%>
 <div class="admin-wrapper">
  <%--左边菜单 --%>
  <%@ include file="../index/index_left.jsp"%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">云购管理</strong>
    /
    <small>购买记录:(第${ygProduct.period }期)${ygProduct.name }</small>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-12 am-u-md-3">
     <div class="am-btn-toolbar am-fl am-padding-left-sm">
      <a class="am-btn hl-btn-green  am-btn-sm" href="javascript:history.go(-1)">返回</a>
     </div>
    </div>
    <div class="am-u-sm-12 am-u-md-9 am-u-lg-6 hide">
     <div class="am-fr">
      <form class="am-form-inline am-hide-sm"> 
      <input type="hidden" id="ygProductId" value="${ygProduct.id }"/>
       <div class="am-form-group">
        <div class="am-input-group am-input-group-sm">
         <input type="text" id="searchbox" name="searchText" value="${param.searchText }" placeholder="姓名、地区" class="am-form-field" autocomplete="off">
         <span class="am-input-group-btn">
          <!-- <input type="submit" id="searchsubmit" value="过滤"   class="btn btn-success">-->
          <button class="am-btn am-btn-success" type="button" autocomplete="off" id="pf-btn-search">
           <span class="glyphicon glyphicon-search"></span>
          </button>
         </span>
        </div>
       </div>
      </form>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-md-6 am-cf">
     <div class="am-fr">
      <div class="am-input-group am-input-group-sm">
       <form action="/admin/ygproduct" method="GET" class="form-inline" role="form"></form>
      </div>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-sm-12">
     <table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-product">
      <thead>
      </thead>
      <tbody id="tbody">
      </tbody>
     </table>
    </div>
   </div>
  </div>
 </div>
 
 <div class="modal fade" tabindex="-1" role="dialog" id="pf-modal-buyNos">
 <div class="modal-dialog" role="document">
  <div class="modal-content">
   <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
     <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">查看号码</h4>
   </div>
   <div class="modal-body" style="padding: 1px;height:400px; overflow-y: scroll;">
    <div class="wz-panel"  style="padding: 5px" id="buyNos_content">
    
    </div>
   </div>
   <div class="modal-footer">
    <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>

   </div>
  </div>
 </div>
</div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script type="text/javascript" src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script type="text/javascript" src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script type="text/javascript">
					var type = "${param.type}";
				</script>
 <script src="${mimeBase}/scripts/admin/ygproduct/buy_record.js?v=${version}" type="text/javascript"></script>
</body>
</html>