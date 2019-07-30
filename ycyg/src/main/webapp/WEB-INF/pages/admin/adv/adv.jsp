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
   </div>
   <div class="am-u-sm-12">
    <table class="am-table am-table-striped am-table-hover table-main">
     <thead>
      <tr>
       <th>广告位</th>
       <th>操作</th>
      </tr>
     </thead>
     <tbody>
      <c:forEach items="${advs}" var="item">
       <tr>
        <td>${item.name }</td>
        <td>
         <a class="am-btn hl-btn-green  am-btn-xs" href="/admin/adv/${item.id}#7006">编辑</a>
        </td>
       </tr>
      </c:forEach>
     </tbody>
    </table>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
</body>
</html>
