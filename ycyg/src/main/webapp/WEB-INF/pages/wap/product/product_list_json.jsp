<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<c:forEach items="${page.result }" var="item">
 <li class="ui-clr">
  <div class="pic">
   <a href="${base }/product/${item.id}" title="${item.name}"><img src="${item.logoPath }" alt="${item.name }" id="buy_img_${item.id }" /></a>
  </div>
  <div class="info">
   <p class="title">
    <a href="${base }/product/${item.id}">(第${item.period}期)${item.name}</a>
   </p>
   <p class="price">总需${item.totalNum }人次</p>
   <div class="progressBar">
    <p class="wrap-bar">
     <span class="bar" style="width: <fmt:formatNumber value="${item.usedNum/item.totalNum*100}" pattern="0.0#"/>%">"></span>
    </p>
    <div class="txt ui-clr">
     <span class="ui-left">${item.usedNum }人已参与</span> <span class="ui-right">剩余${item.leaveNum }</span>
    </div>
   </div>
  </div>
  <button class="add-btn" onclick="addToCart('${item.id}','',this)">
   <span class="ap-icon icon-cart"></span>
  </button>
 </li>
</c:forEach>
