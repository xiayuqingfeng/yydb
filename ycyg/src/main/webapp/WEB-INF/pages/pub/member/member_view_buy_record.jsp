<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<div class="w-mask" id="db-view-mask"></div>
<div class="w-msgbox w-msgbox-moduleCode" tabindex="0" id="db-view-dialog">
 <a pro="close" href="javascript:void(0);" class="w-msgbox-close"></a>
 <div class="w-msgbox-hd" pro="header">Ta的夺宝号码</div>
 <div class="w-msgbox-bd" pro="entry">
  <div id="pro-view-43">
   <div class="w-duobaoCodeList" pro="codeWarper">
    <div class="w-duobaoCodeList-hd" pro="codeTitle">Ta本期总共参与了${myTotalBuyNums }人次</div>
     <div pro="list" class="w-duobaoCodeList-list">
    <c:forEach items="${myBuyRecords}" var="myBuyRecord">
      <dl class="iItem f-clear" id="pro-view-44">
       <dt class="iItemTime">
        <fmt:formatDate value="${myBuyRecord.buyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
       </dt>
       <dt>
        <c:forEach items="${fn:split(myBuyRecord.buyNos, ',')}" var="buyNo">
         <span class="iCodeItem">${buyNo }</span>
        </c:forEach>
       </dt>
      </dl>
    </c:forEach>
     </div>
   </div>
  </div>
 </div>
</div>
