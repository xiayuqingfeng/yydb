<%@ page language="java" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="X-UA-Compatible" content="IE=Edge"> <%@ include file="../wap/commons/commons_resource_head.jsp"%> <title>会员中心_${comName}</title> <meta charset="utf-8">  <meta name="keywords" content="" />  <meta name="description" content="" />  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=2,user-scalable=no">   <link rel="stylesheet" href="${wapBase}/style/mobile/css/common.css">    <link rel="stylesheet" href="${wapBase}/style/mobile/css/font/iconfont.css">     <script src="${wapBase}/style/jquery-1.8.3.min.js"></script>     <script type="text/javascript" src="${wapBase}/style/layer/layer.min.js"></script>     <script type="text/javascript" src="${wapBase}/style/common.js"></script></head><body> <header id="header2"> <h1>云购记录</h1> <div class="back">  <a class="ap-icon" href="javascript:history.back();"></a> </div> <div class="menu">  <a class="icon-home ap-icon" href="/"></a> </div> </header> <link rel="stylesheet" href="${wapBase}/style/mobile/css/member.css">  <div id="content" class="container main">   <!--    <div class="tab-m-a" style="padding: 15px 40px; border-bottom-width: 1px;"> -->   <!--     <ul class="ui-clr"> -->   <!--      <li class="on"><a href="/member/buyRecord.html">参与成功</a></li> -->   <!--      <li class=" last"><a href="/member/buyRecord.html">未付款</a></li> -->   <!--     </ul> -->   <!--    </div> -->   <!--    <div class="tips-m" style="padding: 10px; text-align: center; color: #333;"> -->   <!--     即将揭晓商品<a href="db@type=1" class="color01"><b>（0）</b></a> 进行中的商品<a href="db@type=2.htm" class="color01"><b class="color01">（0）</b></a> 已揭晓商品<a href="db@type=3.htm" -->   <!--      class="color01"><b class="color01">（0）</b></a> -->   <!--    </div> -->   <!--    <ul class="tab-cell tab-cell-5 ui-clr"> -->   <!--     <li class="on"><a href="/member/buyRecord.html">全部</a></li> -->   <!--     <li><a href="/member/buyRecord.html?time=1">今天</a></li> -->   <!--     <li><a href="/member/buyRecord.html?time=2">本周</a></li> -->   <!--     <li><a href="/member/buyRecord.html?time=3">本月</a></li> -->   <!--     <li class="last "><a href="/member/buyRecord.html?time=3">最近三月</a></li> -->   <!--    </ul> -->   <div class="list01 list-db">    <dl class="item ui-clr"></dl>   </div>   <div class="loading getMore" style="background: #fff;"></div>  </div> <!--  公共底部 --> <c:set var="footType" value="member" />   <%@ include file="../wap/index/index_foot.jsp"%> <!--  公共底部 --></body></html><script src="${wapBase}/style/mobile/js/jquery.more.js"></script><script type="text/javascript">	$(function() {		$('.list-db').more({			'address' : '/member/buyRecordAjax',			'amount' : 8		})	});	function moreCode(obj) {		$(obj).parents('td').find('.moreCode').toggle();		if ($(obj).html() == '查看更多&gt;&gt;')			$(obj).html('收起');		else			$(obj).html('查看更多&gt;&gt;');	}</script>