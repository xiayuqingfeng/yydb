<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<c:choose>
 <c:when test="${page.totalItems==0}">
  <div class="prompt">该用户还没有云购中奖记录！</div>
 </c:when>
 <c:otherwise>
  <div class="list01 list-luck">
   <c:forEach items="${page.result }" var="item">
    <dl class="item ui-clr">
     <dt class="ui-clr">
      <a href="/product/${item.ygProduct.id}.html" class="color00"><span class="color01">(第${item.ygProduct.period }期) </span> ${item.ygProduct.name}</a>
     </dt>
     <dd>
      <div class="db-img">
       <a href="/product/${item.ygProduct.id}.html" ><img src="${item.ygProduct.logoPath }" alt="${item.ygProduct.name}" /></a>
      </div>
      <div class="db-txt color03">
       <p>总需：${item.ygProduct.totalNum } 人次</p>
       <p>
        幸运号码：<strong class="color01">${item.ygProduct.winNo }</strong>
       </p>
       <p>总共参与：${item.ygProduct.winUserBuyNum }人次</p>
       <p>
        揭晓时间：
        <fmt:formatDate value="${item.ygProduct.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
       </p>
      </div>
     </dd>
    </dl>
   </c:forEach>
  </div>
  <div class="foot-btn">
   <!--分页按钮 End-->
   <c:set var="pageUrl" value="/user/load_dbCod/${userId }/#pageNo##dbCod" />
   <%@ include file="page.jsp"%>
  </div>
 </c:otherwise>
</c:choose>
<script type="text/javascript">
	var load_div = '#load_dbCod';
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
	})
</script>