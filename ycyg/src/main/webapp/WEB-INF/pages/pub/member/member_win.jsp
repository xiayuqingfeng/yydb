<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>幸运记录_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_win.css" />
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="win" />
     <%@ include file="member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <div class="m-user-frame-content" pro="userFrameWraper">
       <div class="m-user-duobao">
        <%@ include file="member_right_head.jsp"%>
        <c:choose>
         <c:when test="${page.totalItems==0 }">
          <div module="user/win/MyWin" class="m-win m-win-myself" module-id="module-3" module-launched="true">
           <div class="m-user-comm-wraper" id="pro-view-11">
            <div class="m-user-comm-empty" id="pro-view-12">
             <b class="ico ico-face-sad"></b>
             <div class="i-desc">您还没有此记录哦~</div>
             <div class="i-opt">
              <a href="/" class="w-button w-button-main w-button-xl">马上试试运气</a>
             </div>
            </div>
           </div>
          </div>
         </c:when>
         <c:otherwise>
          <!-- begin right main  -->
          <div module="user/win/Win" class="m-win m-win-user" module-id="module-3" module-launched="true">
           <div class="m-user-comm-wraper">
            <div class="m-user-comm-title m-user-comm-titleHasBdr">
             <h2 class="title">
              幸运记录
              <span class="desc txt-gray">
               共有
               <strong pro="total">${page.totalItems }</strong>
               条幸运记录~
              </span>
             </h2>
            </div>
            <c:forEach items="${page.result }" var="item">
             <div class="w-goods">
              <div class="w-goods-pic">
               <a title="${item.ygProduct.name}" target="_blank" href="/product/${item.ygProduct.id}.html">
                <img src="${item.ygProduct.logoPath }" alt="${item.ygProduct.name}">
               </a>
              </div>
              <div class="w-goods-content">
               <p class="w-goods-title">
                <a title="${item.ygProduct.name}" target="_blank" href="/product/${item.ygProduct.id}.html">(第${item.ygProduct.period }期) ${item.ygProduct.name}</a>
               </p>
               <p class="w-goods-price">总需：${item.ygProduct.totalNum } 人次</p>
               <p class="w-goods-info">
                幸运号码：
                <strong class="txt-impt">${item.ygProduct.winNo }</strong>
               </p>
               <p class="w-goods-info">
                总共参与：
                <strong class="txt-dark">${item.ygProduct.winUserBuyNum }</strong>
                人次
               </p>
               <p class="w-goods-info">
                揭晓时间：
                <fmt:formatDate value="${item.ygProduct.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
               </p>
              </div>
             </div>
            </c:forEach>
            <div style="width: 100%; text-align: center">
             <%@ include file="../commons/page.jsp"%>
             <script type="text/javascript">
														$(".w-pager button")
																.click(
																		function() {
																			window.location = "/user/${userInfo.id}/win.html?pageNo="
																					+ $(
																							this)
																							.attr(
																									"pageNo");
																		});
													</script>
            </div>
           </div>
          </div>
          <!-- end right main -->
         </c:otherwise>
        </c:choose>
       </div>
      </div>
     </div>
     <div class="m-user-frame-clear"></div>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>
