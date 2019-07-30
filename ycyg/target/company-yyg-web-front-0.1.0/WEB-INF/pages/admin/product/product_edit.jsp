<%--商品管理添加 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
     <a>商品管理</a>
    </strong>
    /
    <small>商品编辑 </small>
   </div>
   <div class="admin-content-info admin-content-padding">
    <div class="col col-md-12">
     <form id="uploadForm" method="post" enctype="multipart/form-data" action="/admin/product/uploadXxtkFile">
      <input id="photos" multiple name="photos" type="file" style="display: none;" />
     </form>
     <!-- /tabbable -->
     <form class="form-inline" id="saveForm" action="/admin/product/save.json" method="POST">
      <input type="hidden" name="id" value="${product.id}">
      <textarea id="photosFile" name="photos" style="display: none">${product.photos}</textarea>
      <input type="hidden" name="contentId" value="${product.contentId}">
      <div class="tabbable" style="margin-bottom: 18px;">
       <ul class="nav nav-tabs" id="p-Tabs">
        <li class="active">
         <a href="#tab1" data-toggle="tab">基本信息</a>
        </li>
       </ul>
       <div class="tab-content">
        <div class="tab-pane active" id="tab1">
         <div class="am-form-group">
          <label for="cateId">
           <i class="red">*</i>
           商品分类:
          </label>
          <select id="cateId" name="cateId" class="form-control">
            <option value="">选择分类</option>
           <c:forEach var="item" items="${cateList}">
            <option value="${item.id}" <c:if test="${product.cateId == item.id}"> selected</c:if>>${ item.displayName}</option>
           </c:forEach>
          </select>
          <a href="/admin/sysdicts#8001" target="_blank">商品分类管理</a>
         </div>
         <div class="am-form-group">
          <label> 商品品牌: </label>
          <select name="brandId" class="form-control">
            <option value="">选择品牌</option>
           <c:forEach var="item" items="${brandList}">
            <option value="${item.id}" <c:if test="${product.brandId == item.id}"> selected</c:if>>${ item.brandName}</option>
           </c:forEach>
          </select>
          <a href="/admin/brand?type=1#7030" target="_blank">商品品牌管理</a>
         </div>
         <div class="am-form-group">
          <label for="name">
           <i class="red">*</i>
           商品名称:
          </label>
          <input type="text" id="name" name="name" value="${product.name}" maxlength="200" placeholder="必填(必须包含汉字,不能是纯数字或者字母)" style="width: 500px" class="form-control"
           autocomplete="off">
         </div>
         <div class="am-form-group">
          <label for="title"> 商品副标题: </label>
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
          <p class="help-inline text-info" style="padding-left: 85px;margin-top: 2px;">建议上传等比例150*150px，背景透明或白色的图片</p>
         </div>
         <div class="am-form-group">
          <label style="vertical-align: 120px;"> &nbsp;展示图集: </label> 
          
          <ul class="list-group" id="ul_xjtk" style="max-width: 630px;display: inline-block; cursor: pointer;margin-bottom:2px;">
          </ul>
          <ul id="ul_xjtk_copy" style="display: none">
           <li class="list-group-item">
             <input type="text" maxlength="200" placeholder="选择图片" style="width: 500px" class="form-control logoPath" autocomplete="off">
             <button class="btn btn-danger btn-dialog-photo" tid="logoPath" type="button">选择图片</button>
           </li>
          </ul>
          <p class="help-inline text-info" style="padding-left: 85px;margin-top: 2px;">建议上传宽度小于380px，背景透明或白色的图片</p>
         </div>
         <div class="am-form-group ">
          <label for="origPrice">
           <i class="red">*</i>
           原价(元):
          </label>
          <input type="text" id="origPrice" name="origPrice" value="${product.origPrice}" maxlength="8" placeholder="必填" style="width: 200px" class="form-control"
           autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
         </div>
         <div class="am-form-group ">
          <label for="singlePrice">
           <i class="red">*</i>
           总需份数:
          </label>
          <input type="text" id="totalNum" name="totalNum" value="${product.totalNum}" maxlength="8" placeholder="必填" style="width: 200px" class="form-control"
           autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
           (建议尾数不要带0)
         </div>
         <div class="am-form-group ">
          <label for="singlePrice">
           <i class="red">*</i>
           单笔购买价格(元):
          </label>
          <input type="text" id="singlePrice" name="singlePrice" value="${product.singlePrice}" maxlength="8" placeholder="必填" style="width: 200px" class="form-control"
           autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
         </div>
         <div class="am-form-group ">
          <label for="limitPeriods">
           <i class="red">*</i>
           最大期数:
          </label>
          <input type="text" id="limitPeriods" name="limitPeriods" value="${product.limitPeriods}" maxlength="8" placeholder="必填" style="width: 200px"
           class="form-control" autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') "
           onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
           (0代表不限期数)
         </div>
         <div class="am-form-group">
          <label>
           <i class="red">*</i>
           状态:
          </label>
          <input type="radio" id="status" name="status" value="1" class="form-control" <c:if test="${product.status == 1}">checked</c:if>>
          上架
          <input type="radio" id="status" name="status" value="0" class="form-control" <c:if test="${product.status == 0}">checked</c:if>>
          下架
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
           <i class="red">*</i>
           排序号:
          </label>
          <input type="text" id="seqNo" name="seqNo" maxlength="100" value="${product.seqNo}" placeholder="排序号(数字)" style="width: 110px;" class="form-control"
           autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
           人气（越大越靠前）
         </div>
         <div class="am-form-group">
          <label style="display: block;"> &nbsp;商品详情: </label>
          <script id="editorZrmc" name="content" type="text/plain" style="width: 640px; height: 180px">${ElUtils.unescapeHtml3(product.content)}</script>
         </div>
         <div class="am-form-group hidden">
          <label for="seoTitle">seo标题:</label>
          <input type="text" name="seoTitle" value="${product.seoTitle}" placeholder="seo标题" style="width: 500px" class="form-control" autocomplete="off">
          <p class="help-inline text-info"></p>
         </div>
         <div class="am-form-group hidden">
          <label for="seoDesc">seo描述:</label>
          <input type="text" name="seoDesc" value="${product.seoDesc}" placeholder="seo描述" style="width: 500px" class="form-control" autocomplete="off">
          <p class="help-inline text-info"></p>
         </div>
         <div class="am-form-group hidden">
          <label for="seoKey">seo关键字:</label>
          <input type="text" name="seoKey" value="${product.seoKey}" placeholder="seo关键字" style="width: 500px" class="form-control" autocomplete="off">
          <p class="help-inline text-info"></p>
         </div>
         <!-- end tab1 -->
        </div>
       </div>
      </div>
    </div>
    <div class="form-actions">
     <input type="button" id="btn-save-product" class="btn btn-info" value="保存">
     <input type="button" id="goback" class="btn" value="返回" onclick="window.location='/admin/product/?type=7010'">
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
 <%--编辑器 start--%>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/ueditor.config.js?v=${version}"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/ueditor.all.min.js?v=${version}"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/lang/zh-cn/zh-cn.js?v=${version}"></script>
 <%--编辑器 end--%>
 <script src="${mimeBase}/scripts/admin/product/product_edit.js?v=${version}" type="text/javascript"></script>
 <script type="text/javascript" src="${mimeBase }/scripts/pub/ueditor/addImgDialog.js?v=${version}"></script>
    
  <%--选择图片弹出框 --%>
 <%@ include file="../photo/photo_select-dialog.jsp"%> 
</body>
</html>