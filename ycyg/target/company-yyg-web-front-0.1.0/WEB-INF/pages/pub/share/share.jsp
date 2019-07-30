<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>晒单分享_${comName }</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<body>
<c:set var="nav" value="share"/>
 <%@ include file="../index/index_head.jsp"%>
 <link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
  <style type="text/css">
.shareM {
	float: left;
	line-height: 36px;
	font-size: 14px;
	color: #999;
}

.shareM b {
	font-size: 16px;
}

.shareA {
	float: right;
}

.shareA a {
	float: right;
	margin-left: 15px;
}

.shareA a.a {
	display: block;
	height: 34px;
	font-size: 16px;
	background: #fff;
	border: 1px solid #fe685c;
	border-radius: 18px;
	padding: 0 20px;
	line-height: 36px;
	color: #fe685c;
	text-align: center;
	font-weight: bold;
}

.shareA a.on {
	background: #fe685c;
	color: #fff;
}
.m-shareList .item .pic img{
    max-width: 255px;
}

</style>
  <div class="g-gg" style="height: 200px; background: url('${ygBase}/style/images/share_bg.jpg') no-repeat center bottom">
   <span></span>
  </div>
  <div id="stage">
   <div class="container fn-clear" style="padding-top: 20px;">
    <div class="shareA">
    </div>
    <div class="shareM">
     共
     <b class="color01">${total }</b>
     条晒单
    </div>
   </div>
   <div class="m-shareList g-wrap fn-clear transitions-enabled infinite-scroll" id="wall"></div>
   <div id="loadmore" style="display: none;">
    <a href="/share/share_ajax/2">加载更多</a>
   </div>
  </div>
 
 <%@ include file="../index/index_foot.jsp"%>
<script type="text/javascript">
    var _url = '/share/share_ajax/1';
</script>
<link type="text/css" rel="stylesheet" href="${ygBase}/style/gk/css/style.css" />
<script type="text/javascript" src="${ygBase}/style/gk/js/jquery.masonry.min.js"></script>
<script type="text/javascript" src="${ygBase}/style/gk/js/jquery.infinitescroll.min.js"></script>
 <script type="text/javascript" src="${mimeBase}/scripts/pub/share/share.js"></script>
</body>
</html>