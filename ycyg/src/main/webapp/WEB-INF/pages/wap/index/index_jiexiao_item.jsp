<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<c:forEach items="${newJieXiaos}" var="item">
 <li class="item-db"><em><a href="${base }/product/${item.id}.html" ><img width="93" src="${item.logoPath }" alt="${item.name }"></a></em>
  <div class="new-index-2">
   <span><a href="${base }/product/${item.id}.html">${item.name }</a></span>
   <p>
    获得者：<a href="${base }/user/${item.winUserId }">${item.winUserNickName }</a>
   </p>
  </div></li>
</c:forEach>