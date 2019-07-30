<%--系统数据字典首页 --%>
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
.font-box {
 min-height: 60px;
 list-style: none;
 padding-left: 3.9rem;
 margin-top: 0;
}

.font-box li {
 float: left;
}

.font-box li label {
 float: left;
 font-size: 20px;
 color: #000;
 text-align: center;
 width: 35px;
 height: 35px;
 line-height: 35px;
 border: 1px solid #ccc;
 margin: 0 2px 2px 0;
 cursor: pointer;
}

.font-box li label input {
 display: none;
}

.font-box li.hover label,.font-box li:hover label {
 border-color: red;
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
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">数据字典管理 </strong>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-12 am-u-md-3">
     <div class="am-btn-toolbar am-fl am-padding-left-sm">
      <a class="am-btn hl-btn-green  am-btn-sm" id="pf-btn-add-top-cate">新建顶级数据</a>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-sm-12">
     <table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-cate">
      <thead>
      </thead>
      <tbody id="tbody">
      </tbody>
     </table>
    </div>
   </div>
  </div>
 </div>
 <%--添加数据 modal --%>
 <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-add-cate">
  <div class="modal-dialog ">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal"></button>
     <a id="pf-modal-title">添加顶级数据</a>
    </div>
    <div class="modal-body">
     <form id="pf-form-cate-save" action="/admin/sysdicts/save.json" method="post">
      <input type="hidden" class="form-control" placeholder="id" name="id">
      <input type="hidden" class="form-control" placeholder="parentId" name="parentId">
      <input type="hidden" class="form-control" placeholder="id" name="type" value="${param.type }">
      <div class="form-group ">
       <label>
        <span class="red">*</span>
        名称:
       </label>
       <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="名称" id="name" name="name">
      </div>
      <div class="form-group ">
       <label>
        <span class="red">*</span>
        排序:
       </label>
       <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="排序" id="seqNo" name="seqNo">
      </div>
       <div class="form-group ">
       <label> 
        &nbsp;&nbsp;图标:
       </label>
       <input type="hidden" id="icons" name="icons">
        <ul class="font-box">
         <li title="2">
          <label> 
           <i class="iconfontYC">&#xe601;</i>
          </label>
         </li>
         <li title="3">
          <label> 
           <i class="iconfontYC">&#xe602;</i>
          </label>
         </li>
         <li title="4">
          <label>
           <i class="iconfontYC">&#xe603;</i>
          </label>
         </li>
         <li title="5">
          <label>
           <i class="iconfontYC">&#xe604;</i>
          </label>
         </li>
         <li title="6">
          <label>
           <i class="iconfontYC">&#xe605;</i>
          </label>
         </li>
         <li title="7">
          <label>
           <i class="iconfontYC">&#xe606;</i>
          </label>
         </li>
         <li title="8">
          <label>
           <i class="iconfontYC">&#xe607;</i>
          </label>
         </li>
         <li title="9">
          <label>
           <i class="iconfontYC">&#xe608;</i>
          </label>
         </li>
         <li title="10">
          <label>
           <i class="iconfontYC">&#xe609;</i>
          </label>
         </li>
         <li title="11">
          <label>
           <i class="iconfontYC">&#xe60a;</i>
          </label>
         </li>
         <li title="12">
          <label>
           <i class="iconfontYC">&#xe60b;</i>
          </label>
         </li>
         <li title="13">
          <label>
           <i class="iconfontYC">&#xe60c;</i>
          </label>
         </li>
         <li title="14">
          <label>
           <i class="iconfontYC">&#xe60d;</i>
          </label>
         </li>
         <li title="15">
          <label>
           <i class="iconfontYC">&#xe60e;</i>
          </label>
         </li>
         <li title="16">
          <label>
           <i class="iconfontYC">&#xe60f;</i>
          </label>
         </li>
         <li title="17">
          <label>
           <i class="iconfontYC">&#xe610;</i>
          </label>
         </li>
         <li title="18">
          <label>
           <i class="iconfontYC">&#xe611;</i>
          </label>
         </li>
         <li title="19">
          <label>
           <i class="iconfontYC">&#xe612;</i>
          </label>
         </li>
         <li title="20">
          <label>
           <i class="iconfontYC">&#xe613;</i>
          </label>
         </li>
         <li title="21">
          <label>
           <i class="iconfontYC">&#xe614;</i>
          </label>
         </li>
         <li title="22">
          <label>
           <i class="iconfontYC">&#xe615;</i>
          </label>
         </li>
         <li title="23">
          <label>
           <i class="iconfontYC">&#xe616;</i>
          </label>
         </li>
         <li title="24">
          <label>
           <i class="iconfontYC">&#xe617;</i>
          </label>
         </li>
         <li title="25">
          <label>
           <i class="iconfontYC">&#xe618;</i>
          </label>
         </li>
         <li title="26">
          <label>
           <i class="iconfontYC">&#xe619;</i>
          </label>
         </li>
         <li title="27">
          <label>
           <i class="iconfontYC">&#xe61a;</i>
          </label>
         </li>
        </ul>
      </div>
     </form>
    </div>
    <div class="modal-footer">
     <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
     <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="pf-btn-save-cate">
      <span class="ladda-label">保存</span>
      <span class="ladda-spinner"></span>
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
 <script src="${vendorsBase}/tree-table/jquery.treetable.js?v=${version}"></script>
 <script type="text/javascript">
					var type = "${param.type}";
				</script>
 <script src="${mimeBase}/scripts/admin/sys_dicts.js?v=${version}" type="text/javascript"></script>
</body>
</html>