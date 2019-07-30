<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<%-- pageUrl:  /test/test/#pageNo#> --%>
<c:if test="${page.totalItems>0}">
    <c:set var="pageNoTemplate" value="#pageNo#"></c:set>
    <div class="am-cf hl-table-bottom">
        <div class="am-fl" style="width: 100px">共[${page.totalItems}]条记录</div>
        <div class="am-fr">
            <ul class="pager" style="margin: 0px;">
                <c:choose>
                    <c:when test="${page.pageNo==1 }">
                        <li class="disabled">
                            <a>上一页</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href='${ pageUrl.replaceAll(pageNoTemplate,page.pageNo-1)}'>上一页</a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <span>${page.pageNo }/${page.totalPages }</span>
                <c:choose>
                    <c:when test="${page.pageNo==page.totalPages }">
                        <li class="disabled">
                            <a>上一页</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="${ pageUrl.replaceAll(pageNoTemplate,page.pageNo+1)}">下一页 </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</c:if>