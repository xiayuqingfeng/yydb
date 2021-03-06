<%--修改密码首页 --%>
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
        <div class="admin-content" id="hl-admin-content">
            <div class="admin-content-title admin-content-padding">
                <strong class="am-text-lg am-padding-left-sm">帮助与公告</strong>
                /
                <small>内容列表</small>
            </div>
            <div class="am-cf am-padding-top-sm">
                <div class="am-u-sm-12 am-u-md-3">
                    <div class="am-btn-toolbar am-fl am-padding-left-sm">
                        <a class="am-btn hl-btn-green  am-btn-sm" href="/admin/articles/add#8003">新建</a>
                        <a class="am-btn am-btn-primary  am-btn-sm" href="/admin/articles/cate#8003">分类管理</a>
                    </div>
                </div>
                <div class="am-u-sm-12 am-u-md-9 am-u-lg-6">
                    <div class="am-fr">
                        <form class="am-form-inline am-hide-sm">
                            <div class="am-form-group am-input-group-sm">
                                <select id="articleCateId" name="articleCateId" class="form-control" autocomplete="off">
                                    <option value="">-请选择分类-</option>
                                    <c:forEach var="item" items="${cateList}">
                                        <option value="${item.id }" <c:if test="${content.articleCateId == item.id}"> selected</c:if>>${item.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="am-form-group">
                                <div class="am-input-group am-input-group-sm">
                                    <input type="text" id="searchbox" name="searchText" value="" placeholder="关键字" class="am-form-field" autocomplete="off">
                                    <span class="am-input-group-btn">
                                        <!-- <input type="submit" id="searchsubmit" value="过滤"   class="btn btn-success">-->
                                        <button class="am-btn am-btn-success" type="button" autocomplete="off" id="pf-btn-search">
                                            <span class="glyphicon glyphicon-search"></span>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="am-g">
                <div class="col-md-6 am-cf">
                    <div class="am-fr">
                        <div class="am-input-group am-input-group-sm">
                            <form action="/admin/article" method="GET" class="form-inline" role="form"></form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="am-g">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered table-hover table-full-width" id="pf-table-article">
                        <thead>
                        </thead>
                        <tbody id="tbody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../index/index_sm_foot.jsp"%>
    <%@ include file="../commons/commons_resource_foot.jsp"%>
    <script type="text/javascript" src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
    <script type="text/javascript" src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
    <script type="text/javascript">
					var type = "${param.type}";
				</script>
    <script src="${mimeBase}/scripts/admin/articles.js?v=${version}" type="text/javascript"></script>
</body>
</html>