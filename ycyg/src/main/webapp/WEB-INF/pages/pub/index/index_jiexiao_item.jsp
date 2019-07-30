<%@ page language="java" pageEncoding="UTF-8" import="cn.com.yyg.base.em.YgProductStatusEnum"%>
<%@ include file="../commons/taglibs.jsp"%>
<%
//已开奖
request.setAttribute("end", YgProductStatusEnum.END.getValue());

//开奖倒计时
request.setAttribute("pre", YgProductStatusEnum.PRE.getValue());
//计算中
request.setAttribute("cal", YgProductStatusEnum.DO.getValue());
%>
<c:forEach items="${newJieXiaos}" var="item">
   <li title="${item.name }" <c:if test="${item.status == end}">class="index-new1"</c:if>> 
    <em >
     <a href="/product/${item.id}.html" target="_blank">
      <img src="${item.logoPath }" alt="${item.name }" />
     </a>
     <i></i>
    </em>
    <p class="name">
     <a href="/product/${item.id}.html" target="_blank" title="${item.name }">(第${item.period }期)${item.name }</a>
    </p>
    <small>总需：${item.totalNum } 人次</small>
    <c:choose>
    <c:when test="${item.status eq end}">
       <div class="text">
       <p>
        获得者：
        <a href="/user/${item.winUserId }/" target="_blank" title="${item.winUserNickName }">
         <span>${item.winUserNickName }</span>
        </a>
       </p> 
       <p>
        幸运号码：
        <font>${item.winNo }</font>
       </p>
       <p>
        本期参与：
        <font>${item.winUserBuyNum }</font>
       </p>
       <p>
        揭晓时间：
        <fmt:formatDate value="${item.publishDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
       </p>
      </div>
    </c:when>
    <c:when test="${(item.status eq pre) && item.publishDateLong>0}">
       <div class="index-now">
          <big>揭晓倒计时：</big>
          <div id="leftTimeJx${item.id}" class="index-time" style="font-size: 24px;"></div> 
       </div> 
         <script type="text/javascript">
             if(${item.publishDateLong}>0){
                 onload_leftTime_jx('${item.id}',"${item.publishDateLong}");
             }
           </script>
    </c:when>
    <c:otherwise>
       <div class="index-now jx-ing">
           <big>揭晓倒计时：</big>
           <div>开奖计算中...</div>
       </div>
     </c:otherwise>
    </c:choose>

    
    
   </li>
</c:forEach>