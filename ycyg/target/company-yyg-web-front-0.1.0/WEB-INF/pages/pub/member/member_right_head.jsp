<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<div class="m-user-comm-infoBox f-clear" id="pro-view-15" module-id="module-11" module-launched="true">
<c:set var="logo" value="${mimeBase}/images/member/nohead.jpeg"/>
<c:if test="${not empty userInfo.headPhotoPath }">
<c:set var="logo" value="${userInfo.headPhotoPath}"/>
</c:if>
 <img class="m-user-comm-infoBox-face" src="${logo}" width="160" height="160">
 <div class="m-user-comm-infoBox-cont">
  <ul>
   <li class="item nickname">
    <span class="txt">${userInfo.nickName }</span>
   </li>
   <li class="item">
    <span class="txt">
     ID：
     <strong>${userInfo.id }</strong>
    </span>
   </li>
  </ul>
 </div>
</div>