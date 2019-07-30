<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<c:choose>
 <c:when test="${page.totalItems==0}">
  <div class="prompt">该用户还没有参与过云购！</div>
 </c:when>
 <c:otherwise>
  <div class="list01 list-db">
   <c:forEach items="${page.result }" var="item">
    <dl class="item ui-clr">
     <dt class="ui-clr">
      <div class="db-img">
       <a href="/product/${item.ygProduct.id}.html"><img src="${item.ygProduct.logoPath }" alt="${item.ygProduct.name}" /></a>
      </div>
      <div class="db-txt">
       <p class="p1">
        <a href="/product/${item.ygProduct.id}.html" class="color00"><span class="color01">(第${item.ygProduct.period }期)</span> ${item.ygProduct.name}</a>
       </p>
       <c:choose>
        <c:when test="${item.ygProduct.status==3 }">
         <p class="color03">
          获得者：<a href="/user/${item.ygProduct.winUserId }/">${item.ygProduct.winUserNickName }</a>（本期共参与${item.ygProduct.winUserBuyNum }人次）<br /> 幸运号码：<b class="color01"
           style="font-size: 1.4rem">${item.ygProduct.winNo }</b><br /> 揭晓时间：
          <fmt:formatDate value="${item.ygProduct.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
         </p>
        </c:when>
        <c:otherwise>
         <p class="color03">总需：${item.ygProduct.totalNum }人次</p>
         <p class="db-jdt">
          <span style="width: <fmt:formatNumber value="${item.ygProduct.usedNum*100/item.ygProduct.totalNum}" pattern="0.0#"/>%"></span>
         </p>
         <ul class="db-nums ui-clr color03">
          <li class="tr">剩余<span>${item.ygProduct.leaveNum } </span></li>
          <li class="tl">已参与<span>${item.buyNum }</span></li>
         </ul>
        </c:otherwise>
       </c:choose>
      </div>
     </dt>
     <dd>
      <table cellpadding="0" cellspacing="0">
       <tr>
        <th>云购状态</th>
        <c:choose>
         <c:when test="${item.ygProduct.status==0 }">
          <td><span class="color02">正进行.....</span></td>
         </c:when>
         <c:otherwise>
          <td><span class="color01">已揭晓.....</span></td>
         </c:otherwise>
        </c:choose>
       </tr>
       <tr>
        <th>参与人次</th>
        <td>${item.buyNum }人次</td>
       </tr>
       <tr>
        <th>云购号码</th>
        <td><c:forEach items="${fn:split(item.buyNos, ',')}" var="buyNo" varStatus="v">
          <c:choose>
           <c:when test="${v.index+1<=9}">
      ${buyNo }&nbsp;&nbsp;
       </c:when>
           <c:otherwise>
            <span class="moreCode" style="display: none;"> ${buyNo }&nbsp;&nbsp;</span>
           </c:otherwise>
          </c:choose>
            <c:if test="${v.last}"><c:if test="${v.count>9}"> <a href="javascript:;" class="color02" onclick="moreCode(this)">查看更多&gt;&gt;</a></c:if></c:if>
         </c:forEach> 
        </td>
       </tr>
      </table>
     </dd>
    </dl>
   </c:forEach>
  </div>
  <div class="foot-btn">
   <!--分页按钮 End-->
   <c:set var="pageUrl" value="/user/load_db/${userId }/#pageNo##db" />
   <%@ include file="page.jsp"%>
  </div>
 </c:otherwise>
</c:choose>
<script type="text/javascript">
	var load_div = '#load_db';
	$(document).ready(function() {
		$(load_div).find('.demo').ajaxContent({
			event : 'click', //mouseover
			loaderType : "text",
			loadingMsg : "拼命加载中...",
			target : load_div
		}).bind('click', function() {
			$('html,body').animate({
				scrollTop : '0px'
			}, 500);
		});
	});
	function moreCode(obj) {
		$(obj).parents('td').find('.moreCode').toggle();
		if ($(obj).html() == '查看更多&gt;&gt;')
			$(obj).html('收起');
		else
			$(obj).html('查看更多&gt;&gt;');
	}
</script>