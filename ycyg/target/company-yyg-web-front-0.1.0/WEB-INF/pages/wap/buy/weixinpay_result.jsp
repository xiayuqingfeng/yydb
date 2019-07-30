<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../commons/commons_resource_head.jsp"%>
 <title>微信支付_${comName}</title>
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
 <h1></h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <style type="text/css">
.pay_success {
	padding: 30px;
	font-size: 12px;
}

.pay_success h2 {
	font-size: 16px;
}

.pay_success .p1 {
	font-weight: bold;
	font-size: 12px;
}

.pay_success .p2 {
	font-size: 12px;
}

.share_box {
	margin: 10px 0 0;
	padding: 10px;
	background: #ececec;
	border-radius: 5px;
}
</style>
 <div id="content" class="container main">
  <div class="pay_success">
   <h2 class="color01">正在支付......</h2>
  </div>
 </div>
</body>
<%@ include file="../commons/commons_resource_foot.jsp"%>
<script>
	function onBridgeReady() {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : "${appId}", //公众号名称，由商户传入     
			"timeStamp" : "${timeStamp}", //时间戳，自1970年以来的秒数     
			"nonceStr" : "${nonce_str}", //随机串     
			"package" : "prepay_id=${prepay_id}",
			"signType" : "MD5", //微信签名方式:     
			"paySign" : "${paySign}" //微信签名 
		}, function(res) {
			//alert(res.err_msg); // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返
			if (res.err_msg.indexOf("ok")!=-1) {
				window.location.replace("/member/buy/payResult");
			}else{
				window.location.replace("/member/buy/payError");
			}
		});
	}
	function pay() {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			onBridgeReady();
		}
	}
	//支付
	pay();
</script>
</html>
