<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../commons/commons_resource_head.jsp"%>
 <title>${product.name }</title>
 <meta charset="utf-8">
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=2,user-scalable=no">
   <link rel="stylesheet" href="${wapBase}/style/mobile/css/common.css">
    <link rel="stylesheet" href="${wapBase}/style/mobile/css/font/iconfont.css">
     <script src="${wapBase}/style/jquery-1.8.3.min.js"></script>
     <script type="text/javascript" src="${wapBase}/style/layer/layer.min.js"></script>
     <script type="text/javascript" src="${wapBase}/style/common.js"></script>
</head>
<body>
 <header id="header2">
 <h1>云购详情</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <link rel="stylesheet" href="${wapBase}/style/mobile/css/goods.css">
  <div id="content" class="container detail">
   <div style="clear: both; height: 1px; width: 100%;"></div>
   <div class="pProcess">
    <div class="pResults">
     <div class="pResultsL" onclick="location.href='/user/${product.winUserId}#dbCod'">
      <a> <img src="${product.winUserLogoPath}"> <span>${product.winUserNickName }</span></a> <s></s>
     </div>
     <div class="pResultsR">
      <div class="g-snav">
       <div class="g-snav-lst g1">
        参与<br><dd>
          <b class="orange">${product.totalNum }</b><br>人次 
         </dd>
       </div>
       <div class="g-snav-lst">
        揭晓时间<br><dd class="gray9">
          <span> <fmt:formatDate value="${product.publishDate }" pattern="yyyy-MM-dd" /></br>
        <fmt:formatDate value="${product.publishDate }" pattern="HH:mm:ss.SSS" />
        </span>
        </dd>
       </div>
       <div class="g-snav-lst">
        云购时间<br><dd class="gray9">
          <fmt:formatDate value="${product.winUserBuyDate }" pattern="yyyy-MM-dd" /></br>
        <fmt:formatDate value="${product.winUserBuyDate }" pattern="HH:mm:ss.SSS" />
        </dd>
       </div>
      </div>
     </div>
     <p>
      <a href="/product/${product.id }/luck" class="fr">查看计算结果</a>幸运云购码：<b class="orange">${product.winNo}</b>
     </p>
    </div>
   </div>
   <div class="slider" id="single-item" style="position: relative">
    <div class="bd">
     <c:forEach items="${oProduct.photoList}" var="photo">
      <div>
       <img src="${photo.photoPath}" id="buy_img_${product.id }" />
      </div>
     </c:forEach>
    </div>
    <ul class="slick-dots">
     <c:forEach items="${oProduct.photoList}" var="photo">
      <li><button type="button"></button></li>
     </c:forEach>
    </ul>
   </div>
   <p class="title">
    (第${product.period }期)${product.name }<span style="color: red;" title=""></span>
   </p>
   <div class="info ">
    <p class="title">
     一元云购 <span>&nbsp;&nbsp;只需要1元就有机会获得商品！</span>
    </p>
    <p class="price">
     <span style="float: right">总需${product.totalNum}人次</span> 总需：<b class="color01">${product.totalNum}</b>人次
    </p>
   </div>
   <div class="detail-userCodes" style="padding: 10px">
    <div class="pClosed">本云购已揭晓</div>
    <div class="pOngoing">
     <em>第 ${newYgProduct.period } 期 进行中…</em> <a class="fr" href="/product/${newYgProduct.id }.html">查看详情</a>
    </div>
   </div>
   <ul class="yunbuy_other">
    <a href="/product/${product.id}/buyRecord"><b></b>所有云购记录</a>
    <a href="/product/${product.id}/info"><b></b>商品图文详情<label>（建议WIFI下使用）</label></a>
    <a href="/product/${product.id}/shareRecord"><b></b>幸运者晒单</a>
    <a href="/product/${product.id}/win"><b></b>往期揭晓</a>
   </ul>
   <c:if test="${product.period >1 }">
    <div class="joinAndGet">
     <ul id="prevPeriod" class="m-round">
      <li class="fl"><s></s><img src="${perYgProduct.winUserLogoPath}" /></li>
      <li class="fr"><b class="z-arrow"></b></li>
      <li class="getInfo">
       <dd>
        <em class="blue">${perYgProduct.winUserNickName }</em> 
       </dd>
       <dd>
        幸运云购码：<em class="orange arial">${perYgProduct.winNo}</em>
       </dd>
       <dd>
        揭晓时间：
        <fmt:formatDate value="${perYgProduct.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
       </dd>
       <dd>
        云购时间：
        <fmt:formatDate value="${perYgProduct.winUserBuyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
       </dd>
      </li>
     </ul>
    </div>
   </c:if>
  </div>
  <!--  公共底部 -->
  <c:set var="footType" value="product" />
  <%@ include file="../index/index_foot.jsp"%>
  <!--  公共底部 -->
</body>
</html>
<script src="${wapBase}/style/touchslide.1.1.js"></script>
<script src="${wapBase}/style/mobile/js/jquery-pageload.js"></script>
<script src="${wapBase}/style/mobile/js/jquery.more.js"></script>
<script type="text/javascript">
	$(function() {
		TouchSlide({
			slideCell : "#single-item",
			mainCell : ".bd",
			titCell : ".slick-dots li",
			titOnClassName : "slick-active"
		});
		tabs(".tab", ".tab-nav li", ".tab-item", "on", "click");

		$('.m-detail-codes-btn').bind('click', function() {
			$(this).hide();
			$('.m-detail-code').hide();
			$('.m-detail-codes').show();
			$('.m-detail-codes-btn-hide').show();
		})
		$('.m-detail-codes-btn-hide').bind('click', function() {
			$(this).hide();
			$('.m-detail-codes').hide();
			$('.m-detail-code').show();
			$('.m-detail-codes-btn').show();
		})
	});

	var isLoad = {
		"join" : false,
		"win" : false,
		"share" : false
	};

	function mLoad(type) {
		if (isLoad[type] == false) {
			if (type == 'share') {
				$('.shareList').more({
					'address' : '../../content/share_ajax/all@goods_id=34&type=db',
					'amount' : 10
				})
			} else {
				$('#' + type).pageload({
					url : '../ajax_' + type,
					param : 'id=874',
					trigger : '.getMore_' + type
				});
			}
			isLoad[type] = true;
		}
	}
</script>
