<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-cn" xml:lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="../commons/taglibs.jsp"%>
<title>${userInfo.userName}_${comName}</title>
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
  <h1>个人空间</h1>
  <div class="back">
   <a class="ap-icon" href="javascript:history.back();"></a>
  </div>
  <div class="menu">
   <!--<a class="icon-cart ap-icon" href="/yunbuy/cart"><em class="cartNum">0</em></a>-->
   <a class="icon-home ap-icon" href="/"></a>
  </div>
 </header>
 <script src="${wapBase}/style/jquery.ajaxContent.pack.js" type="text/javascript"></script>
 <link rel="stylesheet" href="${wapBase}/style/mobile/css/member.css">
 <link rel="stylesheet" href="${wapBase}/style/mobile/css/minfo.css">
 <div id="content" class="container main space">
  <div class="shead">
   <div class="mpic">
    <img src="${userInfo.headPhotoPath}" width="100" height="100" /><i></i>
   </div>
   <dl class="sinfo">
    <dt>${userInfo.userName}</dt>
    <dd>
     <p>${userInfo.remark}</p>
     <p>
      <b>ID：${userInfo.id}</b>
     </p>
     <p>${base}/user/${userInfo.id}</p>
     <p>
      <b style="font-size: 1.8rem;">积分：<span>${scores }</span>
      </b>
     </p>
    </dd>
   </dl>
   <div class="shr" style="display: none">
    <div class="sbtn">
     <a href="javascript:;" onclick="addFri('${userInfo.id}')" class="ab sbtn_fri" id="Fri"><span>加好友</span></a> <span></span> <a href="/member/message?mid=${userInfo.id}"
      class="ab sbtn_msg"><span>发私信</span></a>
    </div>
   </div>
  </div>
  <div class="snav">
   <ul class="ui-clr">
    <li class="li_db hover" onclick="mLoad('db')">云购记录</li>
    <li class="li_dbCod" onclick="mLoad('dbCod')">云购中奖</li>
    <li class="li_share" onclick="mLoad('share')">晒单</li>
    <!--     <li class="li_fri" onclick="mLoad('fri')">好友</li> -->
    <!--     <li class="li_visit" onclick="mLoad('visit')">访客</li> -->
   </ul>
  </div>
  <div class="load" id="load_db"></div>
  <div class="load" id="load_dbCod"></div>
  <div class="load" id="load_auc"></div>
  <div class="load" id="load_aucCod"></div>
  <div class="load" id="load_share"></div>
  <div class="load" id="load_fri"></div>
  <div class="load" id="load_visit"></div>
  <div class="load" id="load_db_vid"></div>
  <div class="load" id="load_share_vid"></div>
 </div>
 <script type="text/javascript">
		var hash = 'db';
		var mid = "${userInfo.id}";
		var isLoad = {
			"db" : false,
			"dbCod" : false,
			"auc" : false,
			"aucCod" : false,
			"share" : false,
			"fri" : false,
			"visit" : false
		};

		if (location.hash) {
			hash = location.hash.substr(1);
		}
		mLoad(hash);

		function mLoad(hash, load) {
			var arr = hash.split("#");
			var type = arr[0];

			var loadDiv = '#load_' + type;

			$('.snav li').removeClass('hover');
			$('.li_' + type).addClass('hover');
			$('.load').hide();

			var info = '';
			if (arr[1] != undefined) {
				var arr2 = arr[1].split("=");
				info = '?' + arr[1];
				loadDiv += '_' + arr2[0];
			}
			$(loadDiv).show();

			load = load ? load : false;
			if ('#load_' + type != loadDiv || isLoad[type] == false || load == true) {
				$(loadDiv).html("拼命加载中...").load('/user/load_' + type + '/' + mid + info);
				if ('#load_' + type == loadDiv)
					isLoad[type] = true;
			}

			location.href = '/user/' + mid + '#' + hash;
		}

		//添加/解除好友
		function addFri(id) {
			var D = {
				id : id
			};
			$.post('/user/addFri', D, function(data) {
				if (data.error <= 0) {
					location.reload();
				} else {
					var params = ' ';
					if (data.url) {
						params = function() {
							location.href = data.url;
						};
					}
					if (data.msg) {
						layer.alert(data.msg, 8, params);
					}
				}
			}, "json");
		}
	</script>