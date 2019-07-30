<%--修改密码首页 --%>
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
                <strong class="am-text-lg am-padding-left-sm">
                    <c:if test="${param.type==0 }">每日分享</c:if>
                    文库分类管理
                </strong>
            </div>
            <div class="am-cf am-padding-top-sm">
                <div class="am-u-sm-12 am-u-md-3">
                    <div class="am-btn-toolbar am-fl am-padding-left-sm">
                        <a class="am-btn hl-btn-green  am-btn-sm" id="pf-btn-add-top-cate">新建顶级分类</a>
                        <a class="am-btn am-btn-default am-btn-sm" href="/admin/articles#8003">返回</a>
                    </div>
                </div>
            </div>
            <div class="am-g">
                <div class="col-md-6 am-cf">
                    <div class="am-fr">
                        <div class="am-input-group am-input-group-sm">
                            <form action="/admin/daily/article/list" method="GET" class="form-inline" role="form"></form>
                        </div>
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
    <%--添加分类 modal --%>
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" id="pf-modal-add-cate">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"></button>
                    <a id="pf-modal-title">添加顶级分类</a>
                </div>
                <div class="modal-body">
                    <form id="pf-form-cate-save" action="/admin/articles/cate/save.json" method="post" enctype="multipart/form-data">
                        <input type="hidden" class="form-control" placeholder="id" name="id">
                        <input type="hidden" class="form-control" placeholder="parentId" name="parentId">
                        <input type="hidden" class="form-control" placeholder="id" name="type" value="${param.type }">
                        <div class="form-group ">
                            <label>
                                <span class="red">*</span>
                                分类名称:
                            </label>
                            <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="分类名称" id="name" name="name">
                        </div>
                        <div class="form-group ">
                            <label>
                                <span class="red">*</span>
                                分类排序:
                            </label>
                            <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="分类排序" id="seqNo" name="seqNo">
                        </div>
                        <div class="form-group ">
                            <label>
                                拼音:
                            </label>
                            <input type="text" class="form-control" style="display: inline-block; width: 50%"  id="pinyin"  name="pinyin">
                        </div>
                        <div class="pf-row">
                            <label> 备注: </label>
                            <input type="text" class="form-control" style="display: inline-block; width: 50%" placeholder="备注" id="remark" name=remark>
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
    <script src="${mimeBase}/scripts/admin/articles_cate.js?v=${version}" type="text/javascript"></script>
</body>
</html>