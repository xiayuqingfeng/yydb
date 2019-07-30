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
                <strong class="am-text-lg am-padding-left-sm">广告管理</strong>
                /${adv.name }
            </div>
            <div class="am-cf am-padding-top-sm">
                <div class="am-u-sm-4 am-u-md-6">
                    <div class="am-btn-toolbar am-fl am-padding-left-sm">
                        <a class="am-btn hl-btn-green am-btn-sm" href="/admin/adv/addItem/${adv.id }#7006">添加</a>
                        <a class="am-btn am-btn-default am-btn-sm" href="/admin/adv/#7006">返回</a>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-12">
                <table class="am-table am-table-striped am-table-hover table-main">
                    <thead>
                        <tr>
                            <th width="60">图片</th>
                            <th>标题</th>
                            <th>
                                <div class="am-hide-sm">链接</div>
                            </th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.result}" var="item">
                        <tr>
                            <td>
                                <img src='${item.photoPath}' alt="" class="img-responsive" style="height: 25px;">
                            </td>
                            <td>${item.title }</td>
                            <td>
                                <div class="am-hide-sm">${item.linkUrl }</div>
                            </td>
                            <td>
                                          <a class="am-btn hl-btn-green  am-btn-xs" href="/admin/adv/editItem/${adv.id }/${item.id}#7006">编辑</a>
                                    <a class="am-btn am-btn-danger  am-btn-xs advItem-delete"
                                        href="javascript:void()"  advItemId="${item.id}">删除</a>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
               <c:set var="pageUrl" value="${base }/admin/adv/${adv.id }?pageSize=${page.pageSize}&pageNo=#pageNo#"></c:set>
              <%@ include file="../commons/page.jsp"%> 
            </div>
        </div>
    </div>
    <%@ include file="../index/index_sm_foot.jsp"%>
    <%@ include file="../commons/commons_resource_foot.jsp"%>
    <script src="${mimeBase}/scripts/admin/adv/advItem.js?v=${version}" type="text/javascript"></script>
</body>
</html>
