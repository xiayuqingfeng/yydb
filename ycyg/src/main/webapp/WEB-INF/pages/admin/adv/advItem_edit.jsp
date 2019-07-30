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
  <%--begin页面核心内容 --%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">广告管理</strong>
    /${adv.name }
   </div>
   <div class="admin-content-info admin-content-padding">
    <div class="col col-md-12">
     <form id="saveForm" action="/admin/adv/saveItem" method="POST">
      <input type="hidden" name="id" value="${advItem.id}">   
      <input type="hidden" name="advId" value="${adv.id}">
      <fieldset>
       <div class="form-group">
        <label for="title">标题</label>
        <input type="text" id="title" name="title" value="${advItem.title}" placeholder="标题" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group">
        <label for="imageFile" >图片</label>
        <input type="file" id="photoFile" name="photoFile" placeholder="图片" class="form-control" accept="image/*" autocomplete="off" style="display:none">
         <input type="text" id="photoPath" name="photoPath" value="${advItem.photoPath}" maxlength="200" placeholder="必填" style="width: 500px;" class="form-control"
           autocomplete="off">
           <button class="btn btn-danger btn-dialog-photo" tid="photoPath" type="button">选择图片</button>
        <p class="help-inline text-info">建议尺寸：1920宽 X 424高</p>
       </div>
       <div class="row" id="image_preview" <c:if test="${ empty advItem.photoPath}">style="display:none"</c:if>>
        <div class="col-sm-3">
         <div class="thumbnail ov">
          <a href='javascript:' id="img_preview">
           <img id="imgId" src="${advItem.photoPath}" alt="">
          </a>
          <button type="button" class="btn btn-danger btn-xs" id="btn-change-img" autocomplete="off" style="display:none">更换</button>
         </div>
        </div>
       </div>
       <div class="form-group">
        <label>链接</label>
        <input type="text" name="linkUrl" value="${advItem.linkUrl}" placeholder="链接" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-group">
        <label>排序号</label>
        <input type="text" id="seqNo" name="seqNo" value="${advItem.seqNo}" placeholder="排序号" class="form-control" autocomplete="off">
        <p class="help-inline text-info"></p>
       </div>
       <div class="form-actions">
        <input type="button" id="btn-save-item-content" class="am-btn hl-btn-green am-btn-sm" value="保存" autocomplete="off">
        <a id="goback" class="am-btn am-btn-default am-btn-sm" href="/admin/adv/${adv.id}#7006">返回</a>
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
 <script src="${mimeBase}/scripts/admin/adv/advItem_edit.js?v=${version}" type="text/javascript"></script>  
 <%--选择图片弹出框 --%>
 <c:set var="singleSelect" value="true"></c:set>
 <%@ include file="../photo/photo_select-dialog.jsp"%> 
 <script type="text/javascript">
	$("#photoPath").change(function() {
		$("#img_preview").html('<img id="imgId" src="'+$(this).val()+'">');
	});
	$("#img_preview").click(function() {
		$(".btn-dialog-photo").click();
	});

 </script>
</body>
</html>
