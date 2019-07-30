<%--文库管理添加 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" media="screen" href="${vendorsBase }/bootstrap-switch/bootstrap-switch.min.css?v=${version}">
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
  <div class="admin-wrapper">
   <%--左边菜单 --%>
   <%@ include file="../index/index_left.jsp"%>
   <%--begin页面核心内容 --%>
   <div class="admin-content" id="hl-admin-content">
    <div class="admin-content-title admin-content-padding">
     <strong class="am-text-lg am-padding-left-sm">
      <a>帮助与公告</a>
     </strong>
     /
     <small> 内容编辑 </small>
    </div>
    <div class="admin-content-info admin-content-padding">
     <div class="col col-md-12">
      <form id="saveForm" action="/admin/articles/save.json" method="POST">
       <input type="hidden" name="id" value="${content.id}">
       <fieldset>
        <div class="am-form-group am-input-group-sm">
         <label for="title">分类</label>
         <select id="articleCateId" name="articleCateId" class="form-control" autocomplete="off">
          <option value="">-请选择分类-</option>
          <c:forEach var="item" items="${cateList}">
           <option value="${item.id }" <c:if test="${content.articleCateId == item.id}"> selected</c:if>>${item.name }</option>
          </c:forEach>
         </select>
        </div>
        <div class="form-group">
         <label for="title">标题</label>
         <input type="text" id="title" name="title" value="${content.title}" placeholder="标题" class="form-control" autocomplete="off">
         <p class="help-inline text-info"></p>
        </div>
        <div class="form-group ">
         <label for="content"> 内容 </label>
         <script id="contentEditor" name="content" type="text/plain" style="width: 740px; height: 260px">${ElUtils.unescapeHtml3(content.content)}</script>
        </div>
        <div class="form-group">
         <label for="pinyin">拼音</label>
         <input type="text" id="pinyin" name="pinyin" value="${content.pinyin}" placeholder="页面访问需要" class="form-control" autocomplete="off">
        </div>
        <div class="form-group">
         <label for="pinyin">排序号</label>
         <input type="text" id="seqNo" name="seqNo" value="${content.seqNo}" placeholder="排序号" class="form-control" autocomplete="off">
        </div>
        <div class="form-actions">
         <input type="button" id="btn-save-item-content" class="am-btn hl-btn-green am-btn-sm" value="保存" autocomplete="off">
         <a id="goback" class="am-btn am-btn-default am-btn-sm" href="/admin/articles#8003">返回</a>
        </div>
       </fieldset>
      </form>
     </div>
    </div>
   </div>
   <%--end页面核心内容 --%>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <%--编辑器 start--%>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/ueditor.config.js?v=${version}"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/ueditor.all.min.js?v=${version}"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/lang/zh-cn/zh-cn.js?v=${version}"></script>
 <%--编辑器 end--%>
 <script type="text/javascript" src="${mimeBase }/scripts/pub/ueditor/addImgDialog.js?v=${version}"></script>
 <script src="${mimeBase}/scripts/admin/articles_add.js?v=${version}" type="text/javascript"></script>
</body>
</html>