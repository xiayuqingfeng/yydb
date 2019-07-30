<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>晒单分享_${comName }</title>
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
 <h1>晒单分享</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container">
  <div class="clear"></div>
  <div class="shareList">
   <div class="item"></div>
  </div>
  <div class="loading getMore"></div>
 </div>
 <!--  公共底部 -->
 <c:set var="footType" value="share" />
 <%@ include file="../index/index_foot.jsp"%>
 <!--  公共底部 -->
 <iframe name="iframeNews" style="display: none;"></iframe>
 <span id="BIDNUMBER" style="display: none;"></span>
 <script src="${wapBase}/style/scroll/debug.js"></script>
 <script type="text/javascript">
		var logCount = '002654977';
	</script>
 <script src="${wapBase}/style/mobile/js/script.js"></script>
 <div style="display: none"></div>
</body>
</html>
<script src="${wapBase}/style/mobile/js/jquery.more.js"></script>
<script type="text/javascript">
	$(function() {
		$('.shareList').more({
			'address' : '/product/${productId}/shareRecord',
			'amount' : 10
		})
	});
	//     var type='join';
	//     $('#'+type).pageload({
	//         url: '/yunbuy/ajax_'+type,
	//         param: 'id=986',
	//         trigger: '.getMore_'+type
	//     });
</script>