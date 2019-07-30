<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<c:if test="${page.totalItems>0}">
 <c:set var="pageNoTemplate" value="#pageNo#"></c:set>
 <c:set var="nowPageNo" value="${page.pageNo}" />
 <%-- 没有页码的请求路径--%>
 <%--设置总页数 --%>
 <c:set var="totalPage" value="${page.totalPages}"></c:set>
 <c:if test="${totalPage>100}">
  <c:set var="totalPage" value="100"></c:set>
 </c:if>
 <div class="pager">
  <div class="w-pager">
   <c:if test="${totalPage>=1}">
    <button pageNo="${nowPageNo-1}" class="w-button w-button-aside <c:if test="${nowPageNo==1}">w-button-disabled</c:if>" type="button"
     <c:if test="${nowPageNo==1}">disabled="disabled"</c:if>>
     <span>上一页</span>
    </button>
   </c:if>
   <%--小于七页 --%>
   <c:if test="${totalPage<=6 && totalPage>=1}">
    <c:forEach begin="1" end="${totalPage}" varStatus="index">
     <c:choose>
      <c:when test="${nowPageNo==index.index}">
       <button pageNo="${index.index }" class="w-button w-button-main" type="button">
        <span>${index.index }</span>
       </button>
      </c:when>
      <c:otherwise>
       <button pageNo="${index.index }" class="w-button w-button-aside" type="button">
        <span>${index.index }</span>
       </button>
      </c:otherwise>
     </c:choose>
    </c:forEach>
   </c:if>
   <%--大于六页 --%>
   <c:if test="${totalPage>6}">
    <c:choose>
     <%--页号小于四 --%>
     <c:when test="${nowPageNo<=4}">
      <c:forEach begin="1" end="6" varStatus="index">
       <c:choose>
        <c:when test="${nowPageNo==index.index}">
         <button pageNo="${index.index }" class="w-button w-button-main" type="button">
          <span>${index.index }</span>
         </button>
        </c:when>
        <c:otherwise>
         <button pageNo="${index.index }" class="w-button w-button-aside" type="button">
          <span>${index.index }</span>
         </button>
        </c:otherwise>
       </c:choose>
      </c:forEach>
      <span class="w-pager-ellipsis">...</span>
      <button pageNo="${totalPage}" class="w-button w-button-aside" type="button">
       <span>${totalPage}</span>
      </button>
     </c:when>
     <%--页号大于四 --%>
     <c:when test="${nowPageNo>4 && nowPageNo<=totalPage-5}">
      <%-- 页号大于四  --%>
      <button pageNo="1" class="w-button w-button-aside" type="button">
       <span>1</span>
      </button>
      <span class="w-pager-ellipsis">...</span>
      <button pageNo="${nowPageNo-2}" class="w-button w-button-aside" type="button">
       <span>${nowPageNo-2}</span>
      </button>
      <button pageNo="${nowPageNo-1}" class="w-button w-button-aside" type="button">
       <span>${nowPageNo-1}</span>
      </button>
      <button pageNo="${nowPageNo}" class="w-button w-button-main" type="button">
       <span>${nowPageNo}</span>
      </button>
      <button pageNo="${nowPageNo+1}" class="w-button w-button-aside" type="button">
       <span>${nowPageNo+1}</span>
      </button>
      <button pageNo="${nowPageNo+2}" class="w-button w-button-aside" type="button">
       <span>${nowPageNo+2}</span>
      </button>
      <button pageNo="${nowPageNo+3}" class="w-button w-button-aside" type="button">
       <span>${nowPageNo+3}</span>
      </button>
      <span class="w-pager-ellipsis">...</span>
      <button pageNo="${totalPage}" class="w-button w-button-aside" type="button">
       <span>${totalPage}</span>
      </button>
     </c:when>
     <c:when test="${nowPageNo > totalPage-5}">
      <%-- 页号大于最后五个  --%>
      <button pageNo="1" class="w-button w-button-aside" type="button">
       <span>1</span>
      </button>
      <span class="w-pager-ellipsis">...</span>
      <c:forEach begin="${totalPage-5}" end="${totalPage}" varStatus="index">
       <c:choose>
        <c:when test="${nowPageNo==index.index}">
         <button pageNo="${index.index }" class="w-button w-button-main" type="button">
          <span>${index.index }</span>
         </button>
        </c:when>
        <c:otherwise>
         <button pageNo="${index.index }" class="w-button w-button-aside" type="button">
          <span>${index.index }</span>
         </button>
        </c:otherwise>
       </c:choose>
      </c:forEach>
     </c:when>
    </c:choose>
   </c:if>
   <c:if test="${totalPage>=1}">
    <button pageNo="${nowPageNo+1 }" class="w-button w-button-aside <c:if test="${nowPageNo==totalPage}">w-button-disabled</c:if>" type="button"
     <c:if test="${nowPageNo==totalPage}">disabled="disabled"</c:if>>
     <span>下一页</span>
    </button>
   </c:if>
  </div>
 </div>
</c:if>
