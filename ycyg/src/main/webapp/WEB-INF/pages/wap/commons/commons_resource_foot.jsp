<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var logCount = '${totalBuyNums}';
</script>
<script type="text/javascript" src="${mimeBase}/scripts/wap/index/common.js"></script>
<c:if test="${isWeixinBrower eq true and isWeixinBrowerisWeixinBrower eq true}">
 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
 <script type="text/javascript">
		//  alert("appId!${appId}");
		//  alert("timestamp!${timestamp}");
		//  alert("nonceStr!${nonceStr}");
		//  alert("signature!${signature}");
		var WeixinObject = {
			init : function() {
				wx.config({
					debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId : '${appId}', // 必填，公众号的唯一标识
					timestamp : '${timestamp}', // 必填，生成签名的时间戳
					nonceStr : '${nonceStr}', // 必填，生成签名的随机串
					signature : '${signature}',// 必填，签名，见附录1
					jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo' ]
				// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				wx.ready(function() {
					wx.onMenuShareTimeline({
						title : shareTitle(), // 分享标题
						link : addUrlPara(), // 分享链接
						imgUrl : '', // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							//alert("success");
							shareSuccess(1);
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
							//alert("fail");
						}
					});
					wx.onMenuShareAppMessage({
						title : shareTitle(), // 分享标题
						desc : shareDescription(), // 分享描述
						link : addUrlPara(), // 分享链接
						imgUrl : '', // 分享图标
						type : '', // 分享类型,music、video或link，不填默认为link
						dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
						success : function() {
							// 用户确认分享后执行的回调函数
							shareSuccess(2);
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
						}
					});

					wx.onMenuShareQQ({
						title : shareTitle(), // 分享标题
						desc : shareDescription(), // 分享描述
						link : addUrlPara(), // 分享链接
						imgUrl : '', // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							shareSuccess(3);
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
						}
					});

					wx.onMenuShareWeibo({
						title : shareTitle(), // 分享标题
						desc : shareDescription(), // 分享描述
						link : addUrlPara(), // 分享链接
						imgUrl : '', // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							shareSuccess(4);
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
						}
					});

					wx.onMenuShareQZone({
						title : shareTitle(), // 分享标题
						desc : shareDescription(), // 分享描述
						link : addUrlPara(), // 分享链接
						imgUrl : '', // 分享图标
						success : function() {
							// 用户确认分享后执行的回调函数
							shareSuccess(5);
						},
						cancel : function() {
							// 用户取消分享后执行的回调函数
						}
					});

				});

			},
			end : function() {
			}
		}
		//addUrlPara("yaoqingUserId","${currentUserId}");
		WeixinObject.init();

		function addUrlPara() {
			var name = "yaoqingUserId";
			var value = "${currentUserId}";
			var currentUrl = window.location.href.split('#')[0];
			if (/\?/g.test(currentUrl)) {
				if (/name=[-\w]{4,25}/g.test(currentUrl)) {
					currentUrl = currentUrl.replace(/name=[-\w]{4,25}/g, name + "=" + value);
				} else {
					currentUrl += "&" + name + "=" + value;
				}
			} else {
				currentUrl += "?" + name + "=" + value;
			}
			if (window.location.href.split('#')[1]) {
				return currentUrl + '#' + window.location.href.split('#')[1];
			} else {
				return currentUrl;
			}
		}

		function shareSuccess(type) {
			$.post('/member/weixin/share/' + type, {}, function(data) {
				if (data.error == 1) {
					var params = ' ';
					layer.alert(data.msg, 8, params);
				} else {
					layer.alert(data.msg, 1, function() {
						location.reload();
					});
				}
			}, 'json');
		}
		
		function shareDescription() {
			var m = $("meta[name='description']");
			return m.attr("content");
		}

		function shareTitle() {
			return $("title").html();
		}
	</script>
</c:if>
