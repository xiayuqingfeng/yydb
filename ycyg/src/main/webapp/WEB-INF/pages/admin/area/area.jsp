<%--系统地区字典首页 --%>
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
</head>
<body>
				<%--顶部 --%>
				<%@ include file="../index/index_top.jsp"%>
				<div class="admin-wrapper">
								<%--左边菜单 --%>
								<%@ include file="../index/index_left.jsp"%>
								<div class="admin-content" id="hl-admin-content">
												<div class="admin-content-title admin-content-padding">
																<strong class="am-text-lg am-padding-left-sm">地区字典管理 </strong>
												</div>
												<div class="am-cf am-padding-top-sm">
																<div class="am-u-sm-12 am-u-md-3">
																				<div class="am-btn-toolbar am-fl am-padding-left-sm">
																								<a class="am-btn hl-btn-green  am-btn-sm" id="pf-btn-add-top-area">新建顶级地区</a>
																				</div>
																</div>
												</div>
												<div class="am-g">
																<div class="col-sm-12">
																				<table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-area">
																								<thead>
																								</thead>
																								<tbody id="tbody">
																								</tbody>
																				</table>
																</div>
												</div>
								</div>
				</div>
				<%--添加地区 modal --%>
				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-add-area">
								<div class="modal-dialog ">
												<div class="modal-content">
																<div class="modal-header">
																				<button type="button" class="close" data-dismiss="modal"></button>
																				<a id="pf-modal-title">添加顶级地区</a>
																</div>
																<div class="modal-body">
																				<form id="pf-form-area-save" action="/admin/area/save.json" method="post" enctype="multipart/form-data">
																								<input type="hidden" class="form-control" placeholder="id" name="id"> <input type="hidden" class="form-control" placeholder="parentId" name="parentId"> <input
																												type="hidden" class="form-control" placeholder="id" name="type" value="${param.type }">
																								<div class="form-group ">
																												<label> <span class="red">*</span> 名称:
																												</label> <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="名称" id="areaName" name="areaName">
																								</div>
																								<div class="form-group ">
																												<label> <span class="red">*</span> 排序:
																												</label> <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="排序" id="seqNo" name="seqNo">
																								</div>
																				</form>
																</div>
																<div class="modal-footer">
																				<button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
																				<button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="pf-btn-save-area">
																								<span class="ladda-label">保存</span> <span class="ladda-spinner"></span>
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
				<script src="${mimeBase}/scripts/admin/area.js?v=${version}" type="text/javascript"></script>
</body>
</html>