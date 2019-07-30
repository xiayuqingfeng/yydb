<%--商品管理添加 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="cn.com.yyg.base.entity.YgProductEntity"%>
<%
request.setAttribute("initNo", YgProductEntity.INIT_BUY_NO);
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link href="${vendorsBase }/icheck-1.0.2/skins/square/orange.css" rel="stylesheet">
<style type="text/css">
.red {
	color: red;
	line-height: 35px;
}

.duty_box {
	background: #e7f2f8;
	padding: 5px 20px 20px;
	margin: 5px 0 15px;
	position: relative;
}

.cd-popup-close {
	background:
		url(http://pres.vobao.com/Images/V3/jihuashu/pc/close_icon.png)
		no-repeat;
	background-size: 20px;
	width: 30px;
	height: 30px;
	color: transparent;
	position: absolute;
	right: 5px;
	top: 10px;
}

label {
	font-weight: 100;
	min-width: 80px;
}

.tab-content .tab-pane {
	padding-top: 10px;
}

.tauto_xiala {
	background-color: #e7f2f8;
	background-image: none;
	/* 	margin-left: 5px; */
	border: 1px solid #ccc;
	border-top: none;
	overflow: auto;
	width: 500px;
	max-height: 500px;
	padding: 6px 12px;
	font-size: 14px;
	display: inline-block;
	font-size: 14px;
	line-height: 1.428571429;
	color: #555;
	position: absolute;
}

.tauto_xiala_1 {
	
}

.tauto_xiala_1 hover {
	
}
</style>
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
    <strong class="am-text-lg am-padding-left-sm">
     <a>云购管理</a>
    </strong>
    /
    <small>商品编辑 </small>
   </div>
   <div class="admin-content-info admin-content-padding">
    <div class="col col-md-12">
     <form id="uploadForm" method="post" enctype="multipart/form-data" action="/admin/ygproduct/uploadXxtkFile">
      <input id="photos" multiple name="photos" type="file" style="display: none;" />
     </form>
     <!-- /tabbable -->
     <form class="form-inline" id="saveForm" action="/admin/ygproduct/save.json" method="POST">
      <input type="hidden" name="id" value="${product.id}">
      <textarea id="photosFile" name="photos" style="display: none"></textarea>
      <input type="hidden" name="contentId" value="">
      <div class="tabbable" style="margin-bottom: 18px;">
       <ul class="nav nav-tabs" id="p-Tabs">
        <li class="active">
         <a href="#tab1" data-toggle="tab">基本信息</a>
        </li>
       </ul>
       <div class="tab-content">
        <div class="tab-pane active" id="tab1">
         <div class="am-form-group">
          <label for="name">
           <i class="red">*</i>
           商品名称:
          </label>
          <input type="text" id="name" name="name" value="${product.name}" maxlength="200" placeholder="必填" style="width: 500px" class="form-control" autocomplete="off">
         </div> 
        <div class="am-form-group">
         <label for="name"> 商品副标题: </label>
         <input type="text" id="title" name="title" value="${product.title}" maxlength="200" placeholder="选填" style="width: 500px" class="form-control" autocomplete="off">
        </div>
        <div class="am-form-group">
          <label for="logoPath">
           <i class="red">*</i>
           缩略图:
          </label>
          <input type="text" id="logoPath" name="logoPath" value="${product.logoPath}" maxlength="200" placeholder="必填" style="width: 500px" class="form-control"
           autocomplete="off">
           <button class="btn btn-danger btn-dialog-photo" tid="logoPath" type="button">选择图片</button>
         </div> 
          <div class="am-form-group">
          <label>
           <i class="red">*</i>
           是否推荐:
          </label>
          <input type="radio" name="recommend" value="true" class="form-control" <c:if test="${product.recommend == true}">checked</c:if>>
          推荐
          <input type="radio" id="recommend" name="recommend" value="false" class="form-control" <c:if test="${product.recommend == false}">checked</c:if>>
          不推荐
         </div>
         <div class="am-form-group">
          <label> 
           指定中奖号码:
          </label> <input type="text"  name="winNo" maxlength="10" value="${product.winNo}" placeholder="排序号(数字)" style="width: 110px;" class="form-control"
           autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
         建议指定小的号码,在${initNo+1}到${initNo+product.totalNum}之间
         </div>
         <div class="am-form-group">
          <label>
           <i class="red">*</i>
           排序号:
          </label> <input type="text" id="seqNo" name="seqNo" maxlength="100" value="${product.seqNo}" placeholder="排序号(数字)" style="width: 110px;" class="form-control"
           autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
           人气（越大越靠前）
         </div>
        <!-- end tab1 -->
       </div>
      </div>
    </div>
   </div>
   <div class="form-actions">
    <input type="button" id="btn-save-product" class="btn btn-info" value="保存">
    <input type="button" id="goback" class="btn" value="返回" onclick="window.location='/admin/ygproduct/?type=7010'">
   </div>
   </form>
  </div>
 </div>
 </div>
 <%--end页面核心内容 --%>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script src="${vendorsBase }/icheck-1.0.2/icheck.js"></script>
 <script src="${mimeBase}/scripts/admin/ygproduct/product_edit.js?v=${version}" type="text/javascript"></script>
  <%--选择图片弹出框 --%>
 <%@ include file="../photo/photo_select-dialog.jsp"%> 
</body>
</html>