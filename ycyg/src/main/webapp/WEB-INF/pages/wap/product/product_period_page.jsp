<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<c:set var="nowPageNo" value="${page.pageNo}" />
<%--设置总页数 --%>
<c:set var="totalPage" value="${page.totalPages}"></c:set>
<div class="m-detail-period-list f-clear">
 <ul class="list f-clear" id="ul_periods">
  <c:forEach items="${page.result }" var="period" varStatus="status">
   <li <c:if test="${period.period ==product.period}">class="curr"</c:if>>
    <a href="/product/${period.id}.html">第${period.period}期</a>
   </li>
  </c:forEach>
 </ul>
</div>
<div id="pro-view-3" class="w-pager w-pager-simple">
 <a class="w-button w-pager-prev w-button-aside<c:if test="${nowPageNo==1}"> w-button-disabled</c:if>" pageNo="${nowPageNo-1}" id="bt_page_pre">上一页</a>
 <a class="w-button w-pager-next w-button-aside<c:if test="${nowPageNo==totalPage}"> w-button-disabled</c:if>" pageNo="${nowPageNo+1}" id="bt_page_next">下一页</a>
</div>
<script type="text/javascript">
	$("#pro-view-3 .w-button").click(function() {
		if($(this).attr("class").indexOf("w-button-disabled")<0){
			loadPeriodPage($(this).attr("pageNo"));
		}
	});
</script>