<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<c:choose>
 <c:when test="${page.totalItems==0}">
  <div class="prompt">该用户还没有晒单记录！</div>
 </c:when>
 <c:otherwise>
  <div class="shareA container" style="padding: 10px 15px; text-align: left">
   <a href="/member/order"><img src="${wapBase}/style/images/shareB.png" /></a>
  </div>
  <div class="shareList" style="margin-top: 0;">
   <c:forEach items="${page.result }" var="share">
    <div class="item">
     <p class="title">
      <a href="/product/${share.ygProduct.id }.html">${share.ygProduct.name } <span class="color03"> (第${share.ygProduct.period }期) </span></a>
     </p>
     <div class="cont ui-clr">
      <div class="pic">
       <a href="/share/${share.id}.html"><img src="${share.photoList[0]}" alt="${share.title }" /></a>
      </div>
      <div class="txt">
       <p class="mb5">
        <b style="color: #000">${share.title }</b>
       </p>
       <a href="/share/${share.id}.html">${share.content }</a>
      </div>
     </div>
     <div class="author ui-clr">
      <a href="/user/${share.ygProduct.winUserId}/" class="color04">${share.ygProduct.winUserNickName }</a>
      <time datetime="<fmt:formatDate value="${share.ygProduct.publishDate }" pattern="MM-dd HH:mm:ss.SSS" />">
       <fmt:formatDate value="${share.ygProduct.publishDate }" pattern="MM-dd HH:mm:ss.SSS" />
      </time>
     </div>
    </div>
   </c:forEach>
  </div>
  <div class="foot-btn">
   <!--分页按钮 End-->
   <c:set var="pageUrl" value="/user/load_share/${userId }/#pageNo##share" />
   <%@ include file="page.jsp"%>
  </div>
 </c:otherwise>
</c:choose>
<script type="text/javascript">
	var load_div = '#load_share';
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
</script>