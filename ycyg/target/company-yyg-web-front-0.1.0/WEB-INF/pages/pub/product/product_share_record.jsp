<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<c:choose>
 <c:when test="${empty page.result}">
  <div class="empty">
   <p class="status-empty">
    <i class="littleU littleU-cry"></i>
    &nbsp;&nbsp;暂时还没有任何晒单
   </p>
  </div>
 </c:when>
 <c:otherwise>
  <div class="content" style="">
   <ul class="m-detail-shareList-list">
    <c:forEach items="${page.result }" var="item">
     <li>
      <div class="m-detail-shareList-author">
       <a class="avatar" href="/user/${item.userId}" target="_blank">
        <img height="90" src="${item.userLogoPath }" width="90">
       </a>
       <a class="nickname f-txtabb" href="/user/${item.userId}" target="_blank" title="BianZX">${item.userNickName}</a>
      </div>
      <div class="m-detail-shareList-detail">
       <i class="ico ico-arrow ico-arrow-grayShareArr"></i>
       <div class="titleWrap">
        <span class="date">
         <fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
        </span>
        <span class="title">
         第${item.period }期晒单
         <a href="/share/${item.id }.html" target="_blank">
          <b>${item.title }</b>
         </a>
        </span>
       </div>
       <div class="contentWrap">
        <a href="/share/${item.id }.html" target="_blank"> ${item.content } </a>
       </div>
       <div class="imgWrap">
        <c:forEach items="${item.photoList }" var="photo">
         <a href="/share/${item.id }.html" target="_blank">
          <div style="display: table-cell; writing-mode: tb-rl; height: 140px; line-height: 140px; vertical-align: middle">
           <img src="${photo}" width="140">
          </div>
         </a>
        </c:forEach>
       </div>
      </div>
     </li>
    </c:forEach>
   </ul>
  </div>
  <%@ include file="../commons/page.jsp"%>
  <script type="text/javascript">
			$(".w-pager button").click(function() {
				loadShareRecord($(this).attr("pageNo"));
			});
		</script>
 </c:otherwise>
</c:choose>