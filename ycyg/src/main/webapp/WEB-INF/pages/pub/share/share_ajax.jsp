<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<c:forEach items="${page.result }" var="share">
<div class="item">
 <div class="pic">
  <a href="/share/${share.id}.html" target="_blank" title="${share.title}">
   <img alt="${share.title}" src="${share.photoList[0]}" />
  </a>
 </div>
 <div class="name">
   <a href="/product/${share.ygProduct.id }.html" target="_blank">
   (第${share.ygProduct.period }期) ${share.ygProduct.name }
   <span class="color01" style="white-space: nowrap"></span>
  </a>
 </div>
 <div class="code">
  幸运号码：
  <strong class="txt-impt">${share.ygProduct.winNo }</strong>
 </div>
 <div class="post">
  <div class="title">
   <a href="/share/${share.id}.html" target="_blank">
    <strong>${share.title }</strong>
   </a>
  </div>
  <div class="author">
   <a href="/user/${share.userId}/" target="_blank" title="${share.userNickName}">${share.userNickName}</a>
   <span class="time"><fmt:formatDate value="${share.createTime }" pattern="MM-dd HH:mm"/></span>
  </div>
  <div class="abbr">${share.content}</div>
 </div>
</div>
</c:forEach>