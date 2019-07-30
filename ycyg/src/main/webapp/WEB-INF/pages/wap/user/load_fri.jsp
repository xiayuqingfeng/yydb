<%@ page language="java" pageEncoding="UTF-8"%>
<div class="friList ui-clr">
 <dl class="item">
  <dt>
   <a href="/user?id=3590"><img src="/upload/default.gif" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3590">tes***</a>
   </p>
   <p class="desc">经验值：313</p>
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
   <p class="desc">经验值：46227</p>
  </dd>
 </dl>
 <dl class="item">
  <dt>
   <a href="/user?id=3587"><img
    src="/upload/images/photo/20160928141637_9213587.jpg?angle=0&amp;zoom=1&amp;x=1&amp;y=38.5&amp;x1=70&amp;y1=70&amp;w=160&amp;h=160&amp;b=0&amp;c=0&amp;s=0" /></a>
  </dt>
  <dd>
   <p class="name">
    <a href="/user?id=3587">随天</a>
   </p>
   <p class="desc">经验值：1180</p>
  </dd>
 </dl>
</div>
<div class="foot-btn"></div>
<script type="text/javascript">
	var load_div = '#load_fri';
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