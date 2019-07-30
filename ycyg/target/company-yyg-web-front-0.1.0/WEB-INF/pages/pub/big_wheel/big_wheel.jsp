<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>大转盘_${comName }</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<style>
#breadCrumb {
	margin-bottom: 0px !important;
	height: 20px !important;
}

.banner .turnplate {
	margin-top: 256px;
}

.banner {
	height: 100% !important;
	width: 430px !important;
	min-width: 430px !important;
	margin-top: 0px !important;
}
</style>
<script type="text/javascript" src="${wapBase}/style/mobile/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${wapBase}/style/mobile/js/awardRotate.js"></script>
<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${vendorsBase }/scripts/jquery.rotate.min.js"></script>
<script src="${vendorsBase }/jquery-form/jquery.form.js?v=${version}" type="text/javascript"></script>
<link href="${wapBase}/style/mobile/css/style.css" rel="stylesheet" type="text/css">
 <body>
  <c:set var="nav" value="bigwheel" />
  <%@ include file="../index/index_head.jsp"%>
  <link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
   <link rel="stylesheet" href="${ygBase}/style/css/plate.css?verson=${version}">
    <div id="breadCrumb"></div>
    <div id="content" class="container main"></div>
    <img src="${mimeBase }/images/1.png" id="shan-img" style="display: none;" /> <img src="${mimeBase }/images/2.png" id="sorry-img" style="display: none;" />
    <div class="plate_bg">
     <div class="banner  choujiang">
      <div class="turnplate" style="background-image: url(${mimeBase }/images/turnplate-bg.png); background-size: 100% 100%;">
       <canvas class="item" id="wheelcanvas" width="422px" height="422px"></canvas>
       <img class="pointer" src="${mimeBase }/images/turnplate-pointer.png" />
      </div>
      <div id="result" class="result">
       <a class="resultClose"></a>
       <p id="resultTxt" class="resultTxt"></p>
       <a id="resultBtn" class="resultBtn" href="javascript:">再转一次</a>
      </div>
      <div id="tips" class="result">
       <a class="resultClose"></a>
       <p class="resultTxt">
        <b></b><span></span>
       </p>
       <a class="resultBtn" href="javascript:;"></a>
      </div>
     </div>
    </div>
    <div class="am-g">
     <div class="wz-flash-info toast" id="form_error_info" style="display: none;"></div>
    </div>
    <div class="resultBg"></div>
    <div class="plate_rows container">
     <%@ include file="big_wheel_info.jsp"%>
    </div>
    <%@ include file="../index/index_foot.jsp"%>
 </body>
</html>
<script type="text/javascript">
	<c:if test="${not empty error }">
	alert('${error}');
	if (errorTelTimeOut) {
		clearTimeout(errorTelTimeOut);
	}
	var errorTelTimeOut = setTimeout(function() {
		$("#form_error_info").hide();
		window.location.href = "/member/info";
	}, 2.5 * 1000);
	</c:if>
	function showTelError(err) {
		 rotateFunc(0, 150, '<img src="${mimeBase}/images/bigwheel/dzp-wzj.jpg" /><span>'+err+'</span>');
	}
</script>
<script type="text/javascript">
   //不能一直抽奖，这个是等待标志，正在抽奖是，会置为true
   var waiting = false;
   //大转盘id
   var bigWheelId = "${bigWheelInfo.id}";
   //是否抽奖前收集用户信息
   //是否已登录 
   var isLogin = true;
   var isErroring=false;
   //是否已完善良用户信息
   //剩余抽奖 次数
   var lottery_count = 0;
   
   function loadNewInfo() { 
	    jQuery.post("/member/bigwheel/1/loadNewInfo.html", {}, function(data) {
	            $(".plate_rows").html(data);
	     });
	}
   
	var rotateFunc = function (awards, angle, text) {  //awards:奖项，angle:奖项对应的角度
			$('#resultTxt').html(text);
            $('#result').show();
           $('.resultBg').show();
	};

	$('.resultBtn').click(function () {
		$('#result').hide();
   	 	$('.resultBg').hide();
   		//location.reload();
   	 	loadNewInfo();
   	 	if(isErroring==false){
   	 		$('.pointer').click();
   	 	}
	});

	$('.resultClose').click(function () {
		$('#result').hide();
   	 	$('.resultBg').hide();
   		//location.reload();
   	 	loadNewInfo();
	});

     // 显示校验出错弹出信息
     function showFormError(err) {
		 rotateFunc(0, 150, '<img src="${mimeBase}/images/bigwheel/dzp-wzj.jpg" /><span>'+err+'</span>');
	}

     // modal
        function alertModal(message) {
         rotateFunc(0, 150, '<img src="${mimeBase}/images/bigwheel/dzp-wzj.jpg" /><span>'+message+'</span>');
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
     //设置状态正常
     isErroring=false;
    //当前的积分数 
    lottery_count = prizeResultDto.lastTimes;
    if (lottery_count == -1) {
     $("#lottery_count").html("无限次");
    } else {
     $("#lottery_count").html(lottery_count);
     $("#allScore").html(prizeResultDto.allScore);
    }
    if (prizeResultDto.isPrized == true) {
     	rotateFunc(3, 330, '<img src="${mimeBase}/images/bigwheel/dzp-zj.jpg" /><span>恭喜您获得<em>'+prizeResultDto.prizeName+'</em></span>');
    } else {
    	 rotateFunc(0, 150, '<img src="${mimeBase}/images/bigwheel/dzp-wzj.jpg" /><span>很遗憾，<em>未中奖</em>，谢谢参与！</span>');
    }
    prizeResultDtoModel=prizeResultDto;
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
    	isErroring=true;
    	$("#resultBtn").html("知道了");
     	showFormError("您的抽奖机会用完了！");
    }
   } else {
	   isErroring=true;
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
