<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="../commons/taglibs.jsp"%>
 <title>全部商品_${comName }</title>
 <meta charset="utf-8">
  <%@ include file="../commons/commons_resource_head.jsp"%>
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
 <div class="f-search">
  <table cellpadding="0" cellspacing="0">
   <tr>
    <td>
     <div class="f-search-input">
      <table cellpadding="0" cellspacing="0">
       <tr>
        <td class="f-search-img"><img src="${wapBase}/style/mobile/images/search1.png" /></td>
        <td><input id="SEARCHQ" value="${param.keyword }" placeholder="搜索您需要的商品" /></td>
       </tr>
      </table>
     </div>
    </td>
    <td class="f-search-btn"><a id="search-btn">搜索</a> <input type="hidden" id="zq" value="" /></td>
   </tr>
  </table>
  <script type="text/javascript">
			$("#search-btn").click(function() {
				location.href = '${base}/product/search?keyword=' + encodeURIComponent($('#SEARCHQ').val());
				return false;
			});
		</script>
 </div>
 <!--  公共头部 -->
 <c:set var="headType" value="product" />
 <%@ include file="../index/index_head.jsp"%>
 <!--  公共头部 -->
 <div class="sort">
  <ul class="ui-clr">
   <li id="li_cate" class="DESC">
   <c:set var="cateName" value="全部云购"/>
   <c:forEach items="${pcates}" var="pcate">
    <c:if test="${cateId eq pcate.id }"><c:set var="cateName" value="${pcate.name }"/><c:set var="hasCateName" value="1"/></c:if>
   </c:forEach>
   <a>${cateName }<i class="ap-icon icon-sort"><em></em></i></a></li>
   <li class="li_sort<c:if test="${orderType==1 }"> on </c:if>"><a href="/product/list/${cateId}-0-1-1.html">人气</a></li>
   <li class="li_sort<c:if test="${orderType==3 }"> on </c:if>"><a href="/product/list/${cateId}-0-3-1.html">最新</a></li>
   <li class="li_sort<c:if test="${orderType==2 }"> on </c:if>"><a href="/product/list/${cateId}-0-2-1.html">剩余人次</a></li>
   <c:choose>
    <c:when test="${orderType==4}">
     <li class="li_sort on ASC"><a href="/product/list/${cateId}-0-5-1.html"> 总需人次 <i class="ap-icon icon-sort"><em></em></i>
     </a></li>
    </c:when>
    <c:when test="${orderType==5}">
     <li class="li_sort on DESC"><a href="/product/list/${cateId}-0-4-1.html"> 总需人次 <i class="ap-icon icon-sort"><em></em></i>
     </a></li>
    </c:when>
    <c:otherwise>
     <li class="li_sort"><a href="/product/list/${cateId}-0-4-1.html"> 总需人次 <i class="ap-icon icon-sort"><em></em></i>
     </a></li>
    </c:otherwise>
   </c:choose>
  </ul>
 </div>
 <div class="pro-view">
  <div class="cate-bg"></div>
  <div class="cate">
   <ul class="ui-clr">
   <li>
   <a href="/product" <c:if test="${empty hasCateName }">class="on"</c:if> ><i class="ap-icon d0"></i>全部云购</a></li>
   <c:forEach items="${pcates}" var="pcate">
         <li><a href="/product/list/${pcate.id }" <c:if test="${cateId eq pcate.id }">class="on"</c:if>><i class="ap-icon d2"></i>${pcate.name }</a></li>
   </c:forEach>
   </ul>
  </div>
  <ul class="paiList list">
   <c:forEach items="${page.result }" var="item">
    <li class="ui-clr">
     <div class="pic">
      <a href="${base }/product/${item.id}"  title="${item.name}"><img src="${item.logoPath }" alt="${item.name }" id="buy_img_${item.id }" /></a>
     </div>
     <div class="info">
      <p class="title">
       <a href="${base }/product/${item.id}" >(第${item.period}期)${item.name}</a>
      </p>
      <p class="price">总需${item.totalNum }人次</p>
      <div class="progressBar">
       <p class="wrap-bar">
        <span class="bar" style="width: <fmt:formatNumber value="${item.usedNum/item.totalNum*100}" pattern="0.0#"/>%">"></span>
       </p>
       <div class="txt ui-clr">
        <span class="ui-left">${item.usedNum }人已参与</span> <span class="ui-right">剩余${item.leaveNum }</span>
       </div>
      </div>
     </div>
     <button class="add-btn" onclick="addToCart('${item.id}','',this)">
      <span class="ap-icon icon-cart"></span>
     </button>
    </li>
   </c:forEach>
  </ul>
 </div>
 <script type="text/javascript">
		var order = "";
		var sort = "";
		var url = "yunbuy/index";
		var param = "yunbuy@cid=0&order=" + order + "&sort=" + sort + "&type=1&zq=1&q=&load";

		$(function() {
			$('.sort li.li_sort').removeClass('on DESC ASC');
			$('.sort #li_' + order).addClass('on ' + sort);

			$('#li_cate').bind('click', function() {
				if ($(this).hasClass('DESC')) {
					$(this).removeClass('DESC').addClass('ASC');
				} else {
					$(this).removeClass('ASC').addClass('DESC');
				}
				$('.cate-bg,.cate').toggle();
			})
		});

		function mLoad(type) {
// 			if (type == 'need_num') {
// 				sort = (sort == 'ASC') ? 'DESC' : 'ASC';
// 			} else if (type == 'end_num') {
// 				sort = 'ASC';
// 			} else {
// 				sort = 'DESC';
// 			}
// 			location.replace(url + (url.indexOf('?') > -1 ? '&' : '?') + 'cid=0&order=' + type + '&sort=' + sort + "&type=1&zq=1&q=");
		}

		var ExtendOptions = {
			path : function(index) {
				return "${base}/product/listAjax/${cateId}-0-${orderType}-" + index;
			}
		};
	</script>
 <div class="loading getMore">
  <a class="next" href="#"></a>
 </div>
 <div class="load"></div>
 <script src="${wapBase}/style/scroll/debug.js"></script>
 <script src="${wapBase}/style/scroll/jquery.infinitescroll.js"></script>
 <script type="text/javascript">
		var options = {
			loading : {
				msgText : "",
				img : "${wapBase}/style/scroll/loading.gif",
				finishedMsg : '没有更多了哦...',
				//loading选择器
				selector : '.load'
			},
			animate : false,
			//触发方式
			event : "scroll",
			//导航的选择器，会被隐藏
			navSelector : ".getMore",
			//包含下一页链接的选择器
			nextSelector : ".next",
			//你将要取回的选项(内容块)
			itemSelector : "li",
			//启用调试信息，若启用必须引入debug.js
			debug : false,
			//格式要和itemSelector保持一致
			dataType : 'html',
			//最大加载的页数
			maxPage : "${page.totalItems/page.pageSize+1}",
			//当有新数据加载进来的时候，页面是否有动画效果，默认没有
			//animate: true,
			//滚动条距离底部多少像素的时候开始加载，默认150
			extraScrollPx : 150,
			//载入信息的显示时间，时间越大，载入信息显示时间越短
			//bufferPx: 40,
			//加载完数据后的回调函数
			errorCallback : function() {

			},
			//获取下一页方法
			path : function(index) {
				return "${base}/product/listAjax/${cateId}-0-${orderType}-" + index + ".html";
			}
		}
		if (typeof (ExtendOptions) != "undefined") {
			options = $.extend(options, ExtendOptions);
		}
		$('.list').infinitescroll(options, function(newElements, data, url) { //回调函数
			//console.log(data);
			//alert(url);
		});
	</script>
 </div>
 <!--  公共底部 -->
 <c:set var="footType" value="product" />
 <%@ include file="../index/index_foot.jsp"%>
 <!--  公共底部 -->
 <iframe name="iframeNews" style="display: none;"></iframe>
 <span id="BIDNUMBER" style="display: none;"></span>
 <script type="text/javascript">
		var logCount = '002654977';
	</script>
 <script src="${wapBase}/style/mobile/js/script.js"></script>
 <div style="display: none"></div>
 <script type="text/javascript">
		/*var tjtag = document.createElement('script');
		tjtag.src = 'admin/js/statistic.js';
		tjtag.type = 'text/javascript';
		document.body.appendChild(tjtag);*/
	</script>
</body>
</html>
