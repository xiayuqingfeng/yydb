
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加图片</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<%@ include file="../../admin/commons/commons_resource_head.jsp"%>
<script type="text/javascript" src="${vendorsBase}/ueeditor/dialogs/internal.js"></script>
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/pub/photo/photo_add.css?version=${version}" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/pub/photo/photo_select.css?version=${version}" />
</head>
<body>
				<div class="wrapper">
								<div class="tabhead" id="add-photo-tab-head">
												<span class="focus" ref="remote">插入图片</span>
												<c:choose>
																<c:when test="${type eq 1}">
																				<span url="${base}/pub/photo/select?type=2" ref="selectPhoto">图片管理</span>
																</c:when>
																<c:when test="${type eq 2}">
																				<span url="${base}/pub/photo/select?type=1" ref="selectPhoto">图片管理</span>
																</c:when>
																<c:otherwise>
																				<span url="${base}/pub/photo/select" ref="selectPhoto">图片管理</span>
																</c:otherwise>
												</c:choose>
												
								</div>
								<div class="tabbody" id="add-photo-tab-body">
												<div class="panel" id="remote">
																<div class="top">
																				<div class="row">
																								<label>图片地址：</label> <span><input class="text" id="url" type="text" placeholder="请输入http://地址" /></span> <span>宽度<input class="text" type="text" id="width" />px
																								</span> <span>高度<input class="text" type="text" id="height" />px
																								</span>
																				</div>
																</div>
																<div class="left">
																				<div id="preview"></div>
																</div>
												</div>
												<div class="panel" id="selectPhoto" style="display: none"></div>
								</div>
				</div>

				<%@ include file="../../admin/commons/commons_resource_foot.jsp"%>


				<c:choose>
								<c:when test="${type eq 1 }">
												<script type="text/javascript" src="${mimeBase}/scripts/pub/ueditor/photo_add_more.js?v=${version}"></script>
								</c:when>
								<c:when test="${type eq 2 }">
												<script type="text/javascript" src="${mimeBase}/scripts/pub/ueditor/photo_add_out_div.js?v=${version}"></script>
								</c:when>
								<c:otherwise>
												<script type="text/javascript" src="${mimeBase}/scripts/pub/ueditor/photo_add.js?v=${version}"></script>
								</c:otherwise>
				</c:choose>

</body>
</html>