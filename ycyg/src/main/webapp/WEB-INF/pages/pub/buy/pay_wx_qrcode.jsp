<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>微信扫码支付_${comName}</title>
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
       <p>等待微信扫码支付</p>
      </li>
      <li>
       <span>4</span>
       <p>揭晓幸运码</p>
      </li>
      <li>
       <span>5</span>
       <p>奖品派发</p>
      </li>
     </ul>
    </div>
   </div>
  </div>
  <div class="clear"></div>
  <dl class="pay_success" style="background:url()">
   <img src="/wxPay/resQrcode?content=${qrcodeUrl}" style="float: left;" width="300">
   <div class="txt">
    <h2 class="color01">支付金额：￥${payAmount }</h2>
    <p class="p1">请打开微信扫一扫！</p>   
   </div>
  </dl>
 </div>
 <%@ include file="../index/index_foot.jsp"%>
<script type="text/javascript">
//监听支付
function check() { 
	$.ajax({
		type : "post",
		url : "/wxPay/qrcodePayCheck?payNo=${param.payNo}",
		data : "",
		cache : false,
		dataType : "json", 
		success : function(o) {
	        if(o.isSuccess){
	        	window.location="/wxPay/qrcodePaySuccess?payNo=${param.payNo}";
	        }
		}
	});
}
check();
setInterval('check()', 2 * 1000);//2秒
</script>
</body>
</html>
