<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../../wap/commons/commons_resource_head.jsp"%>
 <title>最新揭晓_${comName}</title>
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
 <script src="${wapBase}/js/lefttime_jx.js"></script>
 <style type="text/css">
.count {
	background: #f60;
	font-size: 14px;
	color: #fff;
	display: inline-block;
	padding: 2px 10px;
}

.count b {
	color: #fff;
}

.icon-count:after {
	color: #fff;
}
</style>
 <div class="container">
  <!--  公共头部 -->
  <c:set var="headType" value="zxjx" />
  <%@ include file="../index/index_head.jsp"%>
  <!--  公共头部 -->
  <div class="clear"></div>
  <div class="pro-view shareList">
   <ul class="goodList" id="win-list">
    <li class="item ui-clr item-db"></li>
   </ul>
  </div>
  <a class="loading getMore">点击获取更多...</a>
 </div>
 <input type="hidden" name="ids" id="ids" value="" />
 <!--  公共底部 -->
 <c:set var="footType" value="win" />
 <%@ include file="../index/index_foot.jsp"%>
 <!--  公共底部 -->
</body>
</html>
<script src="${wapBase}/style/mobile/js/jquery.more.js"></script>
<script type="text/javascript">
	$(function() {
		$('.goodList').more({
			'address' : '/win',
			'amount' : 5
		})
	});
</script>
