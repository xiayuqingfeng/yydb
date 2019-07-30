<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<c:if test="${page.totalItems>0}">
 <c:set var="pageNoTemplate" value="#pageNo#"></c:set>
 <c:set var="nowPageNo" value="${page.pageNo}" />
 <%-- 没有页码的请求路径--%>
 <%--设置总页数 --%>
 <c:set var="totalPage" value="${page.totalPages}"></c:set>
 <c:if test="${totalPage>100}">
  <c:set var="totalPage" value="100"></c:set>
 </c:if>
 <div class="page">
  <div class="page_btn_bar clearfix">
   <ul class="page_list  clearfix">
    <c:if test="${nowPageNo!=1}">
     <a class="demo" href="${pageUrl.replaceAll(pageNoTemplate,nowPageNo-1)}${urlHash}" title="上一页">&lt; 上一页</a>
    </c:if>
    <c:if test="${nowPageNo!=totalPage}">
     <a class="demo" href="${pageUrl.replaceAll(pageNoTemplate,nowPageNo+1)}${urlHash}" title="下一页">下一页 &gt; </a>
    </c:if>
  </div>
</c:if>
