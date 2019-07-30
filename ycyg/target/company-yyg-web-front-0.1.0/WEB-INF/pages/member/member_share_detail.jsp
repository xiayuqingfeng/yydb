<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>Ta的晒单_${comName}</title>
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
     <c:set var="member_nav" value="share" />
     <div class="m-user-frame-colMain  m-user-frame-colMain-noLeft">
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item">
         <a href="/user/${userInfo.id}.html"">我的夺宝</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item">
         <a href="/user/${userInfo.id}/share.html">Ta的晒单</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item w-crumbs-active">晒单详情</li>
       </ul>
       <div id="pro-view-2" module-id="module-3" module-launched="true">
        <div class="m-user-duobao">
         <%@ include file="member_right_head.jsp"%>
         <!-- begin right main  -->
         <div class="m-user-shareDetail-panel">
          <div class="m-user-shareDetail-header">
           <h1 class="title">${share.title }</h1>
           <div class="time">晒单时间：<fmt:formatDate value="${share.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
           <div class="share" id="dvShare">
            <div class="w-shareTo" pro="shareToWarp">
             <span class="w-shareTo-txt">分享到</span>
             <ul class="w-shareTo-list">
              <li>
               <a pro="shareIco" data-share="yixin" class="w-shareTo-ico w-shareTo-yixin" href="javascript:void(0)" title="分享至易信"></a>
              </li>
              <li>
               <a pro="shareIco" data-share="weibo" class="w-shareTo-ico w-shareTo-weibo" href="javascript:void(0)" title="分享至新浪微博"></a>
              </li>
              <li>
               <a pro="shareIco" data-share="qzone" class="w-shareTo-ico w-shareTo-qzone" href="javascript:void(0)" title="分享至QZone"></a>
              </li>
              <li>
               <a pro="shareIco" data-share="tqq" class="w-shareTo-ico w-shareTo-tqq" href="javascript:void(0)" title="分享至腾讯微博"></a>
              </li>
              <li>
               <a pro="shareIco" data-share="weixin" class="w-shareTo-ico w-shareTo-weixin" href="javascript:void(0)" title="分享至微信"></a>
              </li>
             </ul>
            </div>
           </div>
          </div>
          <div class="m-user-shareDetail-winDetail">
           <div class="owner">
            <div class="avatar">
             <a href="/user/${share.ygProduct.winUserId}/" title="${share.ygProduct.winUserNickName }">
              <img width="90" height="90" src="${share.ygProduct.winUserLogoPath}">
             </a>
            </div>
            <div class="info">
             <div class="name">
              获得者：
              <a href="/user/${share.ygProduct.winUserId}/" title="${share.ygProduct.winUserNickName }">${share.ygProduct.winUserNickName }</a>
             </div>
             <div class="total">
              总共参与：
              <strong class="txt-impt">${share.ygProduct.winUserBuyNum }</strong>
              人次
             </div>
             <div class="code">
              幸运号码：
              <strong class="txt-impt">${share.ygProduct.winNo }</strong>
             </div>
             <div class="time">揭晓时间：<fmt:formatDate value="${share.ygProduct.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" /></div>
            </div>
           </div>
           <div class="goods">
            <div class="pic">
             <a href="/product/${share.ygProduct.id}.html" target="_blank"> 
                  <img width="90" height="90" src="${share.ygProduct.logoPath }" alt="${share.ygProduct.name}">
             </a>
            </div>
            <div class="info">
             <div class="name">
              <a href="/product/${share.ygProduct.id}.html" target="_blank" style="color: #808080;">  (第${share.ygProduct.period }期) ${share.ygProduct.name }</a>
             </div> 
             <div class="total">总需：35人次</div>
             <div class="more">
             <c:if test="${not empty newYgProduct.id}">
              <a href="/product/${newYgProduct.id}.html" target="_blank">最新一期正在进行中…</a>
              </c:if>
             </div>
            </div>
           </div>
          </div>
          <div class="m-user-shareDetail-cont">
           <i class="ico ico-quote ico-quote-former"></i>
           <i class="ico ico-quote ico-quote-after"></i>
           <div class="text">${share.content }</div>
          </div>
          <div class="m-user-shareDetail-pics">
          <c:forEach items="${share.photoList }" var="photo">
           <div class="item">
            <img src="${photo }">
           </div>
          </c:forEach>
          </div>
         </div>
         <!-- end right main -->
        </div>
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
