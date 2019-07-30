<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../commons/commons_resource_head.jsp"%>
 <title>大转盘_${comName}</title>
 <meta charset="utf-8">
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=2,user-scalable=no">
   <link rel="stylesheet" href="${wapBase}/style/mobile/css/common.css">
    <link rel="stylesheet" href="${wapBase}/style/mobile/css/font/iconfont.css">
     <script src="${wapBase}/style/jquery-1.8.3.min.js"></script>
     <script type="text/javascript" src="${wapBase}/style/layer/layer.min.js"></script>
     <script type="text/javascript" src="${wapBase}/style/common.js"></script>
     <link href="${wapBase}/style/mobile/css/style.css" rel="stylesheet" type="text/css">
      <script type="text/javascript" src="${wapBase}/style/mobile/js/jquery-1.10.2.js"></script>
      <script type="text/javascript" src="${wapBase}/style/mobile/js/awardRotate.js"></script>
      <link rel="stylesheet" media="screen" href="http://cdn.amazeui.org/amazeui/2.3.0/css/amazeui.min.css">
       <link rel="stylesheet" media="screen" href="${mimeBase }/styles/wap/lottery.css">
        <script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
        <script src="http://cdn.amazeui.org/amazeui/2.2.1/js/amazeui.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${vendorsBase }/scripts/jquery.rotate.min.js"></script>
        <script src="${vendorsBase }/jquery-form/jquery.form.js?v=${version}" type="text/javascript"></script>
        <script type="text/javascript">
   //不能一直抽奖，这个是等待标志，正在抽奖是，会置为true
   var waiting = false;
   //大转盘id
   var bigWheelId = "${bigWheelInfo.id}";
   //是否抽奖前收集用户信息
   //是否已登录 
   var isLogin = true;
   //是否已完善良用户信息
   //剩余抽奖 次数
   var lottery_count = 0;
  </script>
        <style>
.foot-fix {
	right: 2rem !important;
}

p,hr,ul,ol,dl,blockquote,pre,address,fieldset,figure {
	margin: 0 0 0 0 !important;
}

ul,ol {
	padding-left: 0 !important;
}
</style>
        <script type="text/javascript">
     // 显示校验出错弹出信息
        function showFormError(err) {
        	$("#form_error_info").html(err);
        	$("#form_error_info").show();
        	if (errorTimeOut) {
        		clearTimeout(errorTimeOut);
        	}
        	var errorTimeOut = setTimeout(function() {
        		$("#form_error_info").hide();
        	}, 2.5 * 1000);
        }

     // modal
        function alertModal(message) {
        	$("#dialog_info_message").html(message);
        	$("#dialog_info").modal("open");
        }
     
		var turnplate = {
			restaraunts : [], //大转盘奖品名称
			prizeIds : [], //大转盘奖品id
			colors : [], //大转盘奖品区块对应背景颜色
			outsideRadius : 192, //大转盘外圆的半径
			textRadius : 155, //大转盘奖品位置距离圆心的距离
			insideRadius : 68, //大转盘内圆的半径
			startAngle : 0, //开始角度
			bRotate : false
		//false:停止;ture:旋转
		};

		$(document).ready(function() {
			$("#btn-my-prizes").on("click", function() {
				$("#ul_my_prizes").html("");
				$("#wz-modal-my-prizes").modal("open");
				$.ajax({
					type : 'get',
					url : "/member/bigwheel/" + bigWheelId + "/myprize.json",
					// data:{},
					timeout : 1000 * 60,
					async : true,
					success : function(data) {
						if (data.isSuccess) {
							if (data.message && data.message.length > 0) {
								$.each(data.message, function(indes, obj) {
									$("#ul_my_prizes").append('<li><span>' + obj.prizeName + '</span></li>');
								});
							} else {
								$("#ul_my_prizes").html("没有中奖记录!");
							}
						} else {
							showFormError(json.msg);
						}
					},
					error : function() {
						showFormError("系统繁忙，请稍后重试！");
					}
				});
			});
			
			
			//动态添加大转盘的奖品与奖品区域背景颜色
			turnplate.restaraunts = [<c:forEach var="item" items="${bigWheelPrizes}" varStatus="v">"${item.prizeName }"<c:if test="${v.index+1 != bigWheelPrizes.size() }">,</c:if>
			</c:forEach> ];
			turnplate.prizeIds = [<c:forEach var="item" items="${bigWheelPrizes}" varStatus="v">${item.id }<c:if test="${v.index+1 != bigWheelPrizes.size() }">,</c:if>
			</c:forEach>];
			turnplate.colors = [ "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF" ];
			
			var prizeResultDtoModel;
			
			var rotateTimeOut = function() {
				$('#wheelcanvas').rotate({
					angle : 0,
					animateTo : 2160,
					duration : 8000,
					callback : function() {
						showFormError('网络超时，请检查您的网络设置！');
						//alert('网络超时，请检查您的网络设置！');
					}
				});
			};

			//旋转转盘 item:奖品位置; txt：提示语;
			var rotateFn = function(item, txt, prizeResultDto) {
				var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length * 2));
				if (angles < 270) {
					angles = 270 - angles;
				} else {
					angles = 360 - angles + 270;
				}
				$('#wheelcanvas').stopRotate();
				$('#wheelcanvas').rotate({
					angle : 0,
					animateTo : angles + 1800,
					duration : 8000,
					callback : function() {
						//alert(txt);
						
				//当前的积分数	
				lottery_count = prizeResultDto.lastTimes;
				if (lottery_count == -1) {
					$("#lottery_count").html("无限次");
				} else {
					$("#lottery_count").html(lottery_count);
					$("#allScore").html(prizeResultDto.allScore);
				}
						
				if (prizeResultDto.isPrized == true) {
					$("#btn_result").html("查看我的奖品");
				} else {
					$("#btn_result").html("继续抽奖");
				}
				prizeResultDtoModel=prizeResultDto;
				$("#wz-modal-result").modal({
					closeViaDimmer : 0,
					onConfirm : function() {
						// 是否中奖
						if (prizeResultDtoModel.isPrized == true) {
							//TODO 
							//如果不是积分类型的奖品
							//showFormError("领奖申请已提交,请确认您的个人信息中手机号码已填写!");
							$("#btn-my-prizes").click();
						} else {
							$('.pointer').click();
						}
					}
				});
					//turnplate.bRotate = !turnplate.bRotate;
					}
				});
			};

			$('.pointer').click(function() {
				//if (turnplate.bRotate)
					//return;
				//turnplate.bRotate = !turnplate.bRotate;
				//获取随机数(奖品个数范围内)
				
			if (!waiting) {
				if ($.trim($("#lottery_count").html()) != 0) {
					// 还有抽奖次数
					$.ajax({
						type : 'get',
						url : "/member/bigwheel/" + bigWheelId + "/prize.json",
						// data:{},
						timeout : 1000 * 60,
						cache : false,
						async : true,
						beforeSend : function(req) {
							waiting = true;
						},
						success : function(data) {
							var message = data.message;
							if (data.isSuccess) {
								//var item = rnd(1, turnplate.restaraunts.length);
								//奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
							    // 遍历是否在数组中
							    var turbPlateNum=0;
							    for(var i=0,k=turnplate.prizeIds.length;i<k;i++){
							        if(message.prizedId==turnplate.prizeIds[i]){
							        	turbPlateNum=i+1;
							        }
							    }
								rotateFn(turbPlateNum, turnplate.restaraunts[message.lastTimes - 1],message);
							} else {
								// alertModal(json.msg);
								showFormError(data.message);
							}
						},
						error : function() {
							showFormError("系统繁忙，请稍后重试！");
						},
						complete : function(req, status) {
							if (waitingTimeOut) {
								clearTimeout(waitingTimeOut);
							}
							var waitingTimeOut = setTimeout(function() {
								waiting = false;
							}, 2.5 * 1000);
						}
					});
				} else {
					showFormError("您的抽奖机会用完了！");
				}
			} else {
				showFormError("您抽奖频率太快了, 休息一下吧!");
			}
			});
		});

		function rnd(n, m) {
			var random = Math.floor(Math.random() * (m - n + 1) + n);
			return random;
		}

		//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
		window.onload = function() {
			drawRouletteWheel();
		};

		function drawRouletteWheel() {
			var canvas = document.getElementById("wheelcanvas");
			if (canvas.getContext) {
				//根据奖品个数计算圆周角度
				var arc = Math.PI / (turnplate.restaraunts.length / 2);
				var ctx = canvas.getContext("2d");
				//在给定矩形内清空一个矩形
				ctx.clearRect(0, 0, 422, 422);
				//strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
				ctx.strokeStyle = "#FFBE04";
				//font 属性设置或返回画布上文本内容的当前字体属性
				ctx.font = '16px Microsoft YaHei';
				for (var i = 0; i < turnplate.restaraunts.length; i++) {
					var angle = turnplate.startAngle + i * arc;
					ctx.fillStyle = turnplate.colors[i];
					ctx.beginPath();
					//arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
					ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);
					ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
					ctx.stroke();
					ctx.fill();
					//锁画布(为了保存之前的画布状态)
					ctx.save();

					//----绘制奖品开始----
					ctx.fillStyle = "#E5302F";
					var text = turnplate.restaraunts[i];
					var line_height = 17;
					//translate方法重新映射画布上的 (0,0) 位置
					ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);

					//rotate方法旋转当前的绘图
					ctx.rotate(angle + arc / 2 + Math.PI / 2);

					/** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
					if (text.indexOf("M") > 0) {//流量包
						var texts = text.split("M");
						for (var j = 0; j < texts.length; j++) {
							ctx.font = j == 0 ? 'bold 20px Microsoft YaHei' : '16px Microsoft YaHei';
							if (j == 0) {
								ctx.fillText(texts[j] + "M", -ctx.measureText(texts[j] + "M").width / 2, j * line_height);
							} else {
								ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
							}
						}
					} else if (text.indexOf("M") == -1 && text.length > 6) {//奖品名称长度超过一定范围 
						text = text.substring(0, 6) + "||" + text.substring(6);
						var texts = text.split("||");
						for (var j = 0; j < texts.length; j++) {
							ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
						}
					} else {
						//在画布上绘制填色的文本。文本的默认颜色是黑色
						//measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
						ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
					}

					//添加对应图标
					if (text.indexOf("积分") > 0) {
						var img = document.getElementById("shan-img");
						img.onload = function() {
							ctx.drawImage(img, -15, 10);
						};
						ctx.drawImage(img, -15, 10);
					} else if (text.indexOf("谢谢参与") >= 0) {
						var img = document.getElementById("sorry-img");
						img.onload = function() {
							ctx.drawImage(img, -15, 10);
						};
						ctx.drawImage(img, -15, 10);
					}
					//把当前画布返回（调整）到上一个save()状态之前 
					ctx.restore();
					//----绘制奖品结束----
				}
			}
		}
		
	</script>
</head>
<body style="background: #e54048; overflow-x: hidden;">
 <header id="header2">
 <h1>大转盘</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <link rel="stylesheet" href="${wapBase}/style/mobile/css/member.css">
  <div id="content" class="container main"></div>
  <img src="${mimeBase }/images/1.png" id="shan-img" style="display: none;" /> <img src="${mimeBase }/images/2.png" id="sorry-img" style="display: none;" />
  <div class="banner">
   <div class="turnplate" style="background-image: url(${mimeBase }/images/turnplate-bg.png); background-size: 100% 100%;">
    <canvas class="item" id="wheelcanvas" width="422px" height="422px"></canvas>
    <img class="pointer" src="${mimeBase }/images/turnplate-pointer.png" />
   </div>
  </div>
  <div class="am-g">
   <div class="wz-flash-info toast" id="form_error_info" style="display: none;"></div>
  </div>
  <%-- 转盘 --%>
  <div id="div_lottery" style="display: none">
   <img src="${mimeBase }/images/ly-plate-c.gif" width="300" height="300">
    <div class="plate">
     <c:forEach var="item" items="${bigWheelPrizes }" varStatus="s">
      <div index="${s.index+1}" class="proTitle${s.index+1} proTitle prizeId${item.id}">${item.prizeName }</div>
     </c:forEach>
     <!--      <a id="plateBtn" href="javascript:" title="开始抽奖">开始抽奖</a> -->
    </div>
  </div>
  <div class="lottery_info">
   <div class="count">
    <strong>总积分：</strong> <span id="allScore"> ${scores } </span> <br /> <strong>抽奖所需积分：</strong> <span id="needScore"> ${bigWheelJoinScore } </span> <br /> <strong>剩余抽奖次数：</strong>
    <span id="lottery_count"> <c:choose>
      <c:when test="${not empty checkPhoneDto}">
                    ${checkPhoneDto.lastTimes }
                    </c:when>
      <c:otherwise>
            0
            </c:otherwise>
     </c:choose>
    </span>
   </div>
   <button id="btn-my-prizes" class="am-btn am-btn-danger am-btn-sm">
    <i class="am-icon-trophy"></i> 我的奖品
   </button>
   <div class="prize">
    <div class="head">
     <span>奖品列表</span>
    </div>
    <ul>
     <c:forEach var="item" items="${bigWheelPrizesList }">
      <c:if test="${item.id!=-1 }">
       <li><strong>${item.prizeName }：</strong> ${item.prizeContent } (${item.prizeCount }名)</li>
      </c:if>
     </c:forEach>
    </ul>
    <div class="head">
     <span>抽奖规则</span>
    </div>
    <div class="rule">${bigWheelInfo.activeRule }</div>
   </div>
  </div>
  <div class="am-modal am-modal-no-btn" tabindex="-1" id="wz-modal-pending">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-bd">亲，来早了，活动还未开始哦！</div>
   </div>
  </div>
  <div class="am-modal am-modal-no-btn" tabindex="-1" id="wz-modal-finish">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-bd">亲来晚了，活动已经结束，敬请关注下期活动！</div>
   </div>
  </div>
  <%--中奖记录 --%>
  <div class="am-modal am-modal-no-btn my-prize" tabindex="-1" id="wz-modal-my-prizes">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-hd">
     <i class="am-icon-trophy"></i> 我的奖品 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
     <ul id="ul_my_prizes"></ul>
    </div>
   </div>
  </div>
  <%--异常框，将无法在页面上操作 --%>
  <div class="am-modal am-modal-alert" tabindex="-1" id="dialog_error">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-bd" id="dialog_error_message"></div>
    <div class="am-modal-footer">
     <span class="am-modal-btn"> <B>确认</B>
    </div>
   </div>
  </div>
  <%-- 提示框 --%>
  <div class="am-modal am-modal-alert" tabindex="-1" id="dialog_info">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-bd" id="dialog_info_message"></div>
    <div class="am-modal-footer">
     <span class="am-modal-btn"> <B>知道了</B>
     </span>
    </div>
   </div>
  </div>
  <div class="am-modal" tabindex="-1" id="wz-modal-result">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-hd no">
     <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="dialog-info-result"></div>
    <div class="am-modal-footer">
     <span class="am-modal-btn" data-am-modal-confirm> <B id="btn_result">关闭</B>
     </span>
    </div>
   </div>
  </div>
  <div class="am-modal" tabindex="-1" id="wz-modal-result1">
   <div class="am-modal-dialog" style="border-radius: 8px;">
    <div class="am-modal-hd">
     <span id="wz-modal-result1-title">请记录以下内容, 关闭页面后无法找回</span> <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="wz-modal-result1-info"></div>
    <div class="am-modal-footer">
     <span class="am-modal-btn" data-am-modal-confirm> <B>关闭</B>
     </span>
    </div>
   </div>
  </div>
<%--    <textarea rows="" cols="">${url}******${jsapi_ticket}******${nonceStr}******${timestamp}******${signature}</textarea> --%>
  <%--   <script src="${mimeBase }/scripts/wap/big_wheel.js?v=${version}" type="text/javascript"></script> --%>
  <script type="text/javascript">
			<c:if test="${not empty error }">
			showTelError('${error}');
			</c:if>
			 function showTelError(err) {
		        	$("#form_error_info").html(err);
		        	$("#form_error_info").show();
		        	if (errorTelTimeOut) {
		        		clearTimeout(errorTelTimeOut);
		        	}
		        	var errorTelTimeOut = setTimeout(function() {
		        		$("#form_error_info").hide();
		        		window.location.replace("/member/info");
		        	}, 2.5 * 1000);
		        }
		</script>
  <!--  公共底部 -->
  <c:set var="footType" value="member" />
  <%@ include file="../index/index_foot.jsp"%>
  <!--  公共底部 -->
</body>
</html>