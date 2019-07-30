<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>测试联想电脑_${comName}</title>
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
 <h1>往期揭晓</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container detail">
    <ul class="win-list" id="win"></ul>
    <div class="loading getMore_win"></div>
</div>
 <!--  公共底部 -->
 <c:set var="footType" value="product" />
 <%@ include file="../index/index_foot.jsp"%>
 <!--  公共底部 -->
 <script src="${wapBase}/style/mobile/js/jquery-pageload.js"></script>
 <script src="${wapBase}/style/mobile/js/jquery.more.js"></script>
 <script type="text/javascript">
		var type = 'win';
		$('#' + type).pageload({
			url : '/product/${productId}/' + type,
			param : 'id=${productId}',
			trigger : '.getMore_' + type
		});
	</script>
</body>
</html>