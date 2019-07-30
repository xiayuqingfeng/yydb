<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<c:choose>
 <c:when test="${empty resultMap}">
  <div class="empty">
   <p class="status-empty">
    <i class="littleU littleU-cry"></i>
    &nbsp;&nbsp;暂时还没有参与记录！
   </p>
  </div>
 </c:when>
 <c:otherwise>
  <div class="content" style="">
   <div class="m-detail-recordList-start">
    <i class="ico ico-clock"></i>
   </div>
   <c:forEach items="${resultMap}" var="map">
    <div class="m-detail-recordList-timeSeperate">
     ${map.key }
     <i class="ico ico-recordDot ico-recordDot-solid"></i>
    </div>
    <ul class="m-detail-recordList m-detail-recordList-0">
     <c:forEach items="${map.value}" var="val">
      <li class="f-clear">
       <span class="time">
        <fmt:formatDate value="${val.buyDate }" pattern="HH:mm:ss.SSS" />
       </span>
       <i class="ico ico-recordDot ico-recordDot-hollow"></i>
       <div class="m-detail-recordList-userInfo">
        <div class="inner">
         <p>
          <span class="avatar">
          <c:if test="${not empty val.buyUserLogoPath}">
           <img height="20" src="${val.buyUserLogoPath}" width="20">
           </c:if>
          </span>
          <a href="/user/${val.buyUserId}" target="_blank" class="color02">${val.buyUserNickName }</a>
          (${val.ipAddr } IP：${val.clientIp }) 参与了
          <b class="times txt-red">${val.buyNum }人次</b>
          <a class="w-button w-button-simple btn-checkCodes" data-func="code" data-rid="1027" href="javascript:void(0)" style="display: none;">
           所有云购号码
           <i class="ico ico-arrow-gray ico-arrow-gray-down"></i>
          </a>
         </p>
         <a class="btn-close" href="javascript:void(0)" style="display: none;">x</a>
         <p class="codes" style="display: none;">
          <c:set value="${ fn:split(val.buyNos,',') }" var="buyNos" />
          <c:forEach items="${buyNos}" var="buyNo">
           <b <c:if test="${buyNo eq val.winNo}">style="color:#E54048; font-weight:bold;"</c:if>>${buyNo }</b>
          </c:forEach>
         </p>
        </div>
       </div>
      </li>
     </c:forEach>
    </ul>
   </c:forEach>
  </div>
  <%@ include file="../commons/page.jsp"%>
  <script type="text/javascript">
    //查看购买号码
    $(".btn-checkCodes").click(function(){
  	  $(this).closest(".f-clear").addClass("m-detail-recordList-userInfo-detail");
    });
    $(".btn-close").click(function(){
  	  $(this).closest(".f-clear").removeClass("m-detail-recordList-userInfo-detail");
    });
    $(".w-pager button").click(function() {
    	loadBuyRecord($(this).attr("pageNo"));
    });
  </script>
 </c:otherwise>
</c:choose>