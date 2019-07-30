<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>支付失败_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <style type="text/css">
.g-body {
	border: 1px solid #ccc;
	width: 1150px;
	margin: 20px auto 0;
	padding: 60px 0 70px;
}

#progress {
	float: none;
	margin: 0 auto;
	display: block;
}

.pay_success {
	width: 1000px;
	min-height: 300px;
	margin: 30px auto 0;
	background: #f6f6f6 url('${ygBase}/style/images/yes.gif') 50px center
		no-repeat;
}

.pay_success h2 {
	font-size: 50px;
}

.pay_success .txt {
	padding: 70px 0 70px 320px;
}

.pay_success .p1 {
	font-weight: bold;
	font-size: 30px;
}

.pay_success .p2 {
	font-size: 18px;
}

.share_box {
	width: 360px;
	margin: 10px 0 0;
	padding: 10px;
	background: #ececec;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
}
</style>
 <div class="g-body blue">
  <div class="m-header">
   <div class="g-wrap">
    <div id="progress" class="progress-03">
     <ul class="fn-clear">
      <li class="dq">
       <span>1</span>
       <p>提交订单</p>
      </li>
      <li class="dq">
       <span>2</span>
       <p>支付订单</p>
      </li>
      <li class="dq">
       <span>3</span>
       <p>支付提示</p>
      </li> 
     </ul>
    </div>
   </div>
  </div>
  <div class="clear"></div>
  <dl class="pay_success">
   <div class="txt">
    <h2 class="color01">对不起，您支付失败！</h2>
    <p class="p1">${message }</p>
    <p class="p2">
     您现在可以
     <a href="${base}/member/duobao.html" class="color01">查看夺宝记录</a>
     或
     <a href="${base}/product/" class="color01">继续云购</a>
    </p>
   </div>
  </dl>
 </div>
 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>
