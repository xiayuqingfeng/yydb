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
   (第${product.period }期) ${product.name }<span style="color: red;" title=""></span>
   </p>
   <div class="info ">
    <p class="title">
     一元云购 <span>&nbsp;&nbsp;只需要1元就有机会获得商品！</span>
    </p>
    <p class="price">
     <span style="float: right">总需${product.totalNum}人次</span> 总需：<b class="color01">${product.totalNum}</b>人次
    </p>
    <div class="progressBar">
     <p class="wrap-bar">
      <span class="bar" style="width: <fmt:formatNumber value="${product.usedNum/product.totalNum*100}" pattern="0.0#"/>%;"></span>
     </p>
     <div class="txt ui-clr">
      <span class="ui-left">${product.usedNum}人已参与</span> <span class="ui-right">剩余${product.leaveNum}人次</span>
     </div>
     <div class="bottom">
      参与人次：
      <div class="number" style="margin-right: 5px;">
       <input class="num-input" type="text" id="qty_874" data-max="${product.totalNum}" value="1" /> <a class="num-btn btn-plus" id="qty_plus" href="javascript:void(0);">+</a>
       <a class="num-btn btn-minus" id="qty_minus" href="javascript:void(0);">-</a>
      </div>
      <span style="color: #EB3D60">获得机率 <font id="w-number-point">100</font>%
      </span>
      <script type="text/javascript">
							var need_num = '${product.totalNum}';
							function add() {
								var div = $(".num-input");
								var num = div.val();
								num = parseInt(num);
								num += 1;
								if (num > div.attr('data-max'))
									return false;
								div.val(num);
								getWinPoint(num, need_num);
							}
							function reduce() {
								var div = $(".num-input");
								var num = div.val();
								num = parseInt(num);
								num -= 1;
								if (num < 1)
									return false;
								div.val(num);
								getWinPoint(num, need_num);
							}
							function getWinPoint($num, $need_num) {
								$('#w-number-point').html(($num / $need_num * 100).toFixed(3));
							};
							window.onload = function(e) {
								//鼠标按钮时刻，抬起时刻
								var firstTime, lastTime;
								//定义计时器(判断2s后)
								var time1, time2;
								//周期性计时器;
								var flag = false;
								document.getElementById("qty_plus").onmousedown = function(e) {
									firstTime = new Date().getTime();
									var time1 = setTimeout(function() {
										flag = true;
										clearTimeout(time1);
									}, 100);
									time2 = setInterval(function() {
										if (flag == true) {
											add();
										}
									}, 100);
								}
								document.getElementById("qty_minus").onmousedown = function(e) {
									firstTime = new Date().getTime();
									var time1 = setTimeout(function() {
										flag = true;
										clearTimeout(time1);
									}, 100);
									time2 = setInterval(function() {
										if (flag == true) {
											reduce();
										}
									}, 100);
								}
								document.onmouseup = function(e) {
									lastTime = new Date().getTime();
									var someTime = lastTime - firstTime;
									someTime = someTime / 1000;
									if (someTime < 2) {
										if (e.target.id == "qty_plus") {
											add();
											flag = false;
											clearInterval(time2);
										} else if (e.target.id == "qty_minus") {
											reduce();
											flag = false;
											clearInterval(time2);
										}
									} else {
										flag = false;
										clearTimeout(time2);
									}
								}
								$(".num-input").blur(function() {
									var max = $(this).attr('data-max') * 1;
									if ($(this).val() > max) {
										$(this).val(max);
									}
									getWinPoint($(this).val(), need_num);
								});
								getWinPoint($(".num-input").val(), need_num);
							}
						</script>
     </div>
     <div class="bottom button-box ui-clr">
      <span class="btn-db"><a href="javascript:void(0)" onclick="addToCart('${product.id}','buy')">立即云购</a></span> <span class="btn-db btn-cart"><a
       href="javascript:void(0)" onclick="addToCart('${product.id}','',this)">加入购物车</a></span>
     </div>
    </div>
   </div>
   <div class="detail-userCodes ">
    <c:choose>
     <c:when test="${empty user.id }">
      <p>
       看不到？是不是没登录或是没注册？ <a href="/login?back=/product/${product.id }" class="color02">登录</a> <a href="/regist?back=/product/${product.id }" class="color02">注册</a>
      </p>
     </c:when>
     <c:when test="${myTotalBuyNums==0}">
      <p class="detail-userCodes-blank">您还没有参与本次云购哦！</p>
     </c:when>
     <c:when test="${myTotalBuyNums<10}">
      <p>
       您本期总共参与了<b class="color01">${myTotalBuyNums }</b>人次
      </p>
      <p class="codes">
       您的号码: <span class="m-detail-code"> <c:forEach items="${myBuyRecords}" var="myBuyRecord">
         <c:forEach items="${fn:split(myBuyRecord.buyNos, ',')}" var="buyNo">
          <b>${buyNo }</b>
         </c:forEach>
        </c:forEach>
       </span>
      </p>
     </c:when>
     <c:otherwise>
     </c:otherwise>
    </c:choose>
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
      <li class="fl"><a href="/user/${perYgProduct.winUserId}.html"><s></s><img src="${perYgProduct.winUserLogoPath}" /></a></li>
      <li class="fr"><b class="z-arrow"></b></li>
      <li class="getInfo">
      <a href="/user/${perYgProduct.winUserId}.html">
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
       </a>
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
