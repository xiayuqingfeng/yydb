<%--系统品牌字典首页 --%>
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
</style>
<style type="text/css">
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
    <strong class="am-text-lg am-padding-left-sm">品牌管理 </strong>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-12 am-u-md-3">
     <div class="am-btn-toolbar am-fl am-padding-left-sm">
      <a class="am-btn hl-btn-green  am-btn-sm" id="pf-btn-add-top-brand">添加品牌</a>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-sm-12">
     <table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-brand">
      <thead>
      </thead>
      <tbody id="tbody">
      </tbody>
     </table>
    </div>
   </div>
  </div>
 </div>
 <%--添加品牌 modal --%>
 <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-add-brand">
  <div class="modal-dialog ">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal"></button>
     <a id="pf-modal-title">添加品牌</a>
    </div>
    <div class="modal-body">
     <form id="pf-form-brand-save" action="/admin/brand/save.json" method="post">
      <input type="hidden" class="form-control" placeholder="id" name="id">
      <input type="hidden" class="form-control" placeholder="parentId" name="parentId">
      <input type="hidden" class="form-control" placeholder="id" name="type" value="${param.type }">
      <div class="form-group ">
       <label>
        <span class="red">*</span>
        名称:
       </label>
       <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="名称" id="brandName" name="brandName">
      </div>
      <div class="form-group ">
       <label>
        <span class="red">*</span>
        排序:
       </label>
       <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="排序(数字)" id="seqNo" name="seqNo"
        onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
      </div>
      <div class="form-group ">
       <label>
        <span class="red">*</span>
        图标:
       </label>
       <input type="hidden" class="form-control" style="display: inline-block; width: 50%" placeholder="" id="iconFrontCode" name="iconFrontCode">
       <td class="td-input">
        <ul class="font-box">
         <!-- 																																				<li  title="1"><label><input type="radio" name="post[iconfont]" value="1" /> <i class="iconfontYC">&#xe600;</i></label></li> -->
         <li title="2">
          <label>
           <input type="radio" name="post[iconfont]" value="2" />
           <i class="iconfontYC">&#xe601;</i>
          </label>
         </li>
         <li title="3">
          <label>
           <input type="radio" name="post[iconfont]" value="3" />
           <i class="iconfontYC">&#xe602;</i>
          </label>
         </li>
         <li title="4">
          <label>
           <input type="radio" name="post[iconfont]" value="4" />
           <i class="iconfontYC">&#xe603;</i>
          </label>
         </li>
         <li title="5">
          <label>
           <input type="radio" name="post[iconfont]" value="5" />
           <i class="iconfontYC">&#xe604;</i>
          </label>
         </li>
         <li title="6">
          <label>
           <input type="radio" name="post[iconfont]" value="6" />
           <i class="iconfontYC">&#xe605;</i>
          </label>
         </li>
         <li title="7">
          <label>
           <input type="radio" name="post[iconfont]" value="7" />
           <i class="iconfontYC">&#xe606;</i>
          </label>
         </li>
         <li title="8">
          <label>
           <input type="radio" name="post[iconfont]" value="8" />
           <i class="iconfontYC">&#xe607;</i>
          </label>
         </li>
         <li title="9">
          <label>
           <input type="radio" name="post[iconfont]" value="9" />
           <i class="iconfontYC">&#xe608;</i>
          </label>
         </li>
         <li title="10">
          <label>
           <input type="radio" name="post[iconfont]" value="10" />
           <i class="iconfontYC">&#xe609;</i>
          </label>
         </li>
         <li title="11">
          <label>
           <input type="radio" name="post[iconfont]" value="11" />
           <i class="iconfontYC">&#xe60a;</i>
          </label>
         </li>
         <li title="12">
          <label>
           <input type="radio" name="post[iconfont]" value="12" />
           <i class="iconfontYC">&#xe60b;</i>
          </label>
         </li>
         <li title="13">
          <label>
           <input type="radio" name="post[iconfont]" value="13" />
           <i class="iconfontYC">&#xe60c;</i>
          </label>
         </li>
         <li title="14">
          <label>
           <input type="radio" name="post[iconfont]" value="14" />
           <i class="iconfontYC">&#xe60d;</i>
          </label>
         </li>
         <li title="15">
          <label>
           <input type="radio" name="post[iconfont]" value="15" />
           <i class="iconfontYC">&#xe60e;</i>
          </label>
         </li>
         <li title="16">
          <label>
           <input type="radio" name="post[iconfont]" value="16" />
           <i class="iconfontYC">&#xe60f;</i>
          </label>
         </li>
         <li title="17">
          <label>
           <input type="radio" name="post[iconfont]" value="17" />
           <i class="iconfontYC">&#xe610;</i>
          </label>
         </li>
         <li title="18">
          <label>
           <input type="radio" name="post[iconfont]" value="18" />
           <i class="iconfontYC">&#xe611;</i>
          </label>
         </li>
         <li title="19">
          <label>
           <input type="radio" name="post[iconfont]" value="19" />
           <i class="iconfontYC">&#xe612;</i>
          </label>
         </li>
         <li title="20">
          <label>
           <input type="radio" name="post[iconfont]" value="20" />
           <i class="iconfontYC">&#xe613;</i>
          </label>
         </li>
         <li title="21">
          <label>
           <input type="radio" name="post[iconfont]" value="21" />
           <i class="iconfontYC">&#xe614;</i>
          </label>
         </li>
         <li title="22">
          <label>
           <input type="radio" name="post[iconfont]" value="22" />
           <i class="iconfontYC">&#xe615;</i>
          </label>
         </li>
         <li title="23">
          <label>
           <input type="radio" name="post[iconfont]" value="23" />
           <i class="iconfontYC">&#xe616;</i>
          </label>
         </li>
         <li title="24">
          <label>
           <input type="radio" name="post[iconfont]" value="24" />
           <i class="iconfontYC">&#xe617;</i>
          </label>
         </li>
         <li title="25">
          <label>
           <input type="radio" name="post[iconfont]" value="25" />
           <i class="iconfontYC">&#xe618;</i>
          </label>
         </li>
         <li title="26">
          <label>
           <input type="radio" name="post[iconfont]" value="26" />
           <i class="iconfontYC">&#xe619;</i>
          </label>
         </li>
         <li title="27">
          <label>
           <input type="radio" name="post[iconfont]" value="27" />
           <i class="iconfontYC">&#xe61a;</i>
          </label>
         </li>
        </ul>
       </td>
      </div>
     </form>
    </div>
    <div class="modal-footer">
     <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
     <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="pf-btn-save-brand">
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
 <script src="${mimeBase}/scripts/admin/brand.js?v=${version}" type="text/javascript"></script>
</body>
</html>