<%@ page language="java" pageEncoding="UTF-8"%>
<div class="friList ui-clr">
 <dl class="item">
  <dt>
   <a href="/user?id=3633"><img src="/upload/images/photo/20160908021501520630846_src.jpg" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3633">1117元</a>
   </p>
   <p class="desc">13小时前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=3628"><img
    src="/upload/images/photo/20160904172756_2013628.jpg?angle=0&amp;zoom=1&amp;x=43&amp;y=1&amp;x1=99&amp;y1=15&amp;w=160&amp;h=160&amp;b=0&amp;c=0&amp;s=0" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3628">13313042***</a>
   </p>
   <p class="desc">25天前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=3"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3">123***</a>
   </p>
   <p class="desc">26天前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=3627"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3627">3730</a>
   </p>
   <p class="desc">26天前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=99"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=99">1</a>
   </p>
   <p class="desc">1月前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=3615"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3615">13666076***</a>
   </p>
   <p class="desc">1月前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=90"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=90">13685853***</a>
   </p>
   <p class="desc">2月前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=95"><img
    src="/upload/images/photo/20160721192102_15595.jpg?angle=0&amp;zoom=1&amp;x=39.5&amp;y=94&amp;x1=70&amp;y1=70&amp;w=160&amp;h=160&amp;b=0&amp;c=0&amp;s=0" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=95">superpig</a>
   </p>
   <p class="desc">6月前</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=162"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=162">test01</a>
   </p>
   <p class="desc">8月前</p>
  </dd>
 </dl>
</div>
<div class="foot-btn"></div>
<script type="text/javascript">
	var load_div = '#load_visit';
	$(document).ready(function() {
		$(load_div).find('.demo').ajaxContent({
			event : 'click', //mouseover
			loaderType : "text",
			loadingMsg : "拼命加载中...",
			target : load_div
		}).bind('click', function() {
			$('html,body').animate({
				scrollTop : '0px'
			}, 500);
		});
	});
</script>