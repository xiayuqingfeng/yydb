<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>幸运记录_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
 <link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
 <link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
 <link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_share.css" />
</head>
<body>
 <%@ include file="../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="share"/>
     <%@ include file="member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <div class="m-user-frame-content" pro="userFrameWraper">
        <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item"><a href="/member/">我的夺宝</a><span class="w-crumbs-split">&gt;</span></li>
        <li class="w-crumbs-item w-crumbs-active">我的晒单</li>
       </ul>
        <div class="m-user-duobao">         
         <!-- begin right main  -->
         <div class="m-user-share-panel">
          <c:choose>
          <c:when test="${total ==0 }">
           <div class="m-user-share-blank" style="display:">
            <span class="m-user-share-blank-text">只有签收了幸运商品才可以晒单哦!</span>
            <br/>
            <a href="/member/win/">立即查看</a>
           </div>
          </c:when>
           <c:otherwise>           
            <div class="m-user-share-totalRecords" style="display: block;">
             <span style="font-weight: bold; font-size: 18px; margin-right: 20px;">晒单</span>
             共有
             <span class="m-user-share-totalCnt">${total }</span>
             条晒单
            </div>
           </c:otherwise>
          </c:choose>
          <div class="m-user-share-list" style="display: block;">
           <div class="m-shareList  infinite-scroll" id="wall">
           </div>
            <div id="loadmore" style="display: none;">
            <a href="/member/share_ajax/2">加载更多</a>
           </div>
          </div> 
         </div>
         <!-- end right main -->
        </div>
      </div>
     </div>
     <div class="m-user-frame-clear"></div>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../pub/index/index_foot.jsp"%>
 
<script type="text/javascript">
    var _url = '/member/share_ajax/1';
</script>
 <script type="text/javascript" src="${ygBase}/style/gk/js/jquery.masonry.min.js"></script>
<script type="text/javascript" src="${ygBase}/style/gk/js/jquery.infinitescroll.min.js"></script>
 <script type="text/javascript" src="${mimeBase}/scripts/pub/share/share.js"></script>
</body>
</html>
