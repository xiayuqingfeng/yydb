<%--相册管理 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" type="text/css" href="${vendorsBase }/bootstrap-loading/ladda-themeless.min.css?v=${version}">
<style>
.red {
	color: red;
}
.img_icon{
    display: none;
    width: 106px;
    height: 117px;
    border: 2px solid #1094fa;
    position: absolute;
    background-repeat: no-repeat;
    background: url("/assets/images/pub/success.png") 62px 74px no-repeat;
    margin-top: -114px;
    cursor: pointer;
}
</style>
</head>
<body>
  <%--begin页面核心内容 --%>
  <div class="admin-content" id="hl-admin-content" style="padding-left:5px">
   <div class="admin-content-info admin-content-padding">
    <div class="col col-md-12">
     <form id="uploadForm" method="post" enctype="multipart/form-data" action="${base}/admin/photo/uploadFile">
     <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active">
       <a href="javascript:void(0);">我的图片</a>
      </li>
      <li role="presentation" style="width:160px;">
        <div style="padding-top:10px;padding-left:10px">
        <div style="float:left">上传：</div>
        <div style="float:left;cursor: pointer"><input type="file" multiple accept="image/*" name="file" id="fileId" style="display:;width:75px;"/></div>
         </div>
      </li>
      <li role="presentation">
       <a href="#" id="btn-delete-photo">删除图片</a>
      </li>
     </ul>
     <div style="width: 100%; padding: 10px">
      <select id="albumId" class="form-control" url="/admin/photo/select?singleSelect=${param.singleSelect }" style="width: 200px;display:inline">
       <option value="">请选择相册</option>
       <c:forEach var="item" items="${ablums}">
          <option <c:if test="${item.id eq param.albumId }">selected</c:if> value="${item.id}">${ item.displayName}</option>
         </c:forEach>
      </select>
     </div>
      </form>
     <div class="wz-panel" style="padding: 10px">
      <%--begin app --%>
      <c:forEach items="${page.result}" var="item">
       <div class="am-inline-block photoItem" style="width: 110px; height: 110px; margin-bottom: 20px;vertical-align: top;">
        <div class="thumbnail am-text-center" style="padding: 1px;cursor: pointer;">
          <img src="${uploadImageBase}/${item.path}/${item.name}" alt="${item.name}" style="height: 75px; width: 75px;display: inline;">
         <div style="padding-top: 15px;overflow: hidden;height: 40px;" title="${item.info}">
           <strong>&nbsp;${item.info}</strong>
         </div>
         <span class="img_icon" show="0" photoId="${item.id}" photoPath="${uploadImageBase}/${item.path}/${item.name}"></span>
        </div>
       </div>
      </c:forEach>
      <%--end app --%>
     </div>
     <!--  <p>
       <a class="am-btn am-btn-default am-btn-sm" href="/useradmin/wz/item#2007">返回</a>
      </p> -->
     <div style="text-align: center">
      <c:set var="pageUrl" value="${base }/admin/photo/select?albumId=${param.albumId }&singleSelect=${param.singleSelect }&pageSize=${page.pageSize}&pageNo=#pageNo#"></c:set>
      <%@ include file="../commons/page.jsp"%>
     </div>
    </div>
   </div>
  </div>
  <%--end页面核心内容 --%> 
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/spin.min.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-loading/ladda.min.js?v=${version}"></script>
 <script src="${vendorsBase}/tree-table/jquery.treetable.js?v=${version}"></script>
 <script type="text/javascript">
 var singleSelect="${param.singleSelect}";
 </script>
 <script src="${mimeBase}/scripts/admin/photo/photo_list.js?v=${version}" type="text/javascript"></script>
</body>
</html>