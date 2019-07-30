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
      <a pro="UC_goRecharge" href="/member/qiandao/" class="w-button w-button-s"><span>每日签到</span></a>
      <c:if test="${todayQian ==false }">
       <span style="color:red">您今天还没签到哦！</span>
       </c:if>
       连续签到积分翻倍
       <c:if test="${qiandaos>1 }">
      (已连续${qiandaos}天)
      </c:if>
    
   </li>
   <li class="item">
    <span class="txt">
     账户余额：<strong class="txt-impt" pro="userCoin" style="cursor: pointer; "  onclick="location.href='/member/amountDetail.html'">${userInfo.accountBalance }</strong>
     夺宝币
    </span>
   </li>
    <li class="item">
    <span class="txt">
   我的积分：<strong class="txt-impt" pro="userCoin"><a href="/member/jifen/">${scores }</a></strong>
     分
    </span>
   </li>
  </ul>
 </div>
</div>