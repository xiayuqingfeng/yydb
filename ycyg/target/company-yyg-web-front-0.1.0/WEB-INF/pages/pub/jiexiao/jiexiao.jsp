<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>最新揭晓_${comName }</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<body>
<c:set var="nav" value="win"/>
 <%@ include file="../index/index_head.jsp"%>
 <link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
  <link rel="stylesheet" href="${ygBase}/style/css/win.css?verson=${version}">
 <div id="breadCrumb">
  <div class="breadCrumb-txt">
   <a href="/">首页</a>
   <font class='st'> > </font>
   <em>最新揭晓</em>
  </div>
 </div>
 <div class="fn-clear win-box">
  <ul class="fn-clear taba">
   <li class="li_db"><a href="/win/">最新揭晓</a></li>
  </ul>
  <div class="load" id="load_db" style="display: block;">
   <div class="win-list" id="win-list">
    <div class="winCount">
     截至目前共揭晓商品
     <b class="color01">${page.totalItems }</b>
     个
    </div>
    <div class="fn-clear">
     <div id="list-djx"></div>
     <div id="list-win">
      <c:forEach items="${page.result }" var="item">
       <div class="item item-db">
        <div class="itemc">
         <div class="itemL scrollLoadingDiv">
          <a href="/product/${item.id}.html"  title="${item.name }" target="_blank">
           <img class="scrollLoading" data-url="${item.logoPath }" alt="${item.name }"  width="150">
          </a>
         </div>
         <div class="itemR">
          <div class="user-info">
           <div class="photo">
            <a href="/user/${item.winUserId}/" title="${item.winUserNickName }" target="_blank">
            <c:choose>
            <c:when test="${empty item.winUserLogoPath }">
               <img class="scrollLoading scrollLoadingDiv"
              data-url="${mimeBase}/images/member/nohead.jpeg" width="60" height="60">       
            </c:when>
            <c:otherwise>
              <img class="scrollLoading scrollLoadingDiv"
              data-url="${item.winUserLogoPath }"" width="60" height="60">
            </c:otherwise>
            </c:choose>

            </a>
           </div>
           <div class="txt">
            <p class="p1">
             获得者：
             <a href="/user/${item.winUserId}/" target="_blank" class="color02" title="${item.winUserNickName }">${item.winUserNickName }</a>
            </p>
			<p>来自：${item.winUserIpAddr}</p>
            <p>
             幸运号码：
             <span class="color01">${item.winNo }</span>
            </p>
            <p>
             本期参与：
             <span class="color01">${item.winUserBuyNum }</span>人次
            </p>
           </div>
          </div>
          <div class="pro-info">
           <p class="tit">
            <a href="/product/${item.id}.html"  title="${item.name }测试1">(第${item.period }期) ${item.name }</a>
           </p>
           <p>商品总需：${item.totalNum }人次</p>
           <p>揭晓时间： <fmt:formatDate value="${item.publishDate }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
           <p class="button">
            <span>
             <a href="/product/${item.id}.html" target="_blank">查看详情</a>
            </span>
           </p>
          </div>
         </div>
        </div>
       </div>
      </c:forEach>
     </div>
    </div>
   </div>
   <div style="width: 100%; text-align: center;margin-top:30px">
    <%@ include file="../commons/page.jsp"%>
    <script type="text/javascript">
		$(".w-pager button").click(function() {
		     window.location="/win/"+$(this).attr("pageNo");
		});
	</script>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>
