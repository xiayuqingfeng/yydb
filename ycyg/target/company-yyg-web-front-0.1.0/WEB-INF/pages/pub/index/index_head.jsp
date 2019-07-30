<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<style type="text/css">
.site-nav .weixin {
	position: relative;
	cursor: pointer;
}

.site-nav .weixin i {
	width: 16px;
	height: 16px;
	display: inline-block;
	background: url("${mimeBase}/images/pub/wx_favicon.ico") no-repeat;
}

.site-nav .weixin img {
	position: absolute;
	top: 25px;
	left: 0;
	height: 120px;
	width: 120px;
	max-width: none;
	z-index: 9;
}
</style>
<div class="full headbg">
 <div class="site-nav">
  <div class="container site-nav-bd">
   <div class="site-nav-bd-l ui-left">欢迎来到${comName}！</div>
   <div class="site-nav-bd-r ui-right">
    <c:choose>
     <c:when test="${empty user }">
      <%--       <a href="/weixinLogin" class="color"><img src="${ygBase}/style/images/wechat.png" style="vertical-align: middle; margin-bottom: 3px;"> 微信登录</a> --%>
      <!--       <span></span> -->
      <a href="/login" class="color">请登录</a>
      <span> | </span>
      <a href="/regist"> <i class="color" style="font-style: normal">免费注册</i>
      </a>
     </c:when>
     <c:otherwise>
      <a href="/member" class="color">${user.nickName }</a>
       [
       <a href="/logout">退出</a>
       ]
       <span></span>
      <a href="/member/" class="color abg">会员中心</a>
     </c:otherwise>
    </c:choose>
    <span> | </span> <a href="/help/connect.html">联系客服</a> <span> | </span> <a class="weixin"> <i></i> 官方微信 <img src="${ygBase}/style/images/qrcode2.jpg"
     style="display: none;">
    </a> <span> | </span> <a href="/help/">帮助中心</a>
   </div>
  </div>
 </div>
 <div class="container head">
  <h1 class="logo-bd ui-left">
   <a href="/" target="_self" style="text-indent: 0;"> <img src="${ygBase}/style/images/logo.png" />
   </a>
  </h1>
  <dl class="logo1">
   <dd>
    <form onsubmit="return searchWord();">
     <c:set var="keyword" value="${param.keyword }" />
     <c:if test="${empty keyword }">
      <c:set var="keyword" value="${sysConfig.dataMap['hotSearch']}" />
     </c:if>
     <input class="logo1_i1" id="SEARCHQ" name="q" value="${keyword }" onfocus="if(this.value=='${keyword }'){ this.value=''; }"
      onblur="if(this.value==''){ this.value='${keyword }'; }" />
     <button type="submit" class="logo1_i2">搜索</button>
    </form>
    <script type="text/javascript">
					function searchWord() {
						location.href = '/product/search?keyword=' + encodeURIComponent($('#SEARCHQ').val());
						return false;
					}
				</script>
   </dd>
   <dt class="load_cart">
    <a href="/cart/" class="logo1_1"> <span>购物车</span> <em class="cartNumber" id="cartNum">0</em>
    </a>
    <div class="cart-box" id="cart_info">
     <div class="w-miniCart-empty">您的清单中还没有任何物品</div>
    </div>
   </dt>
  </dl>
  <span class="buyers" onclick="location.href='/buyCount.html'"> <label>累计参与</label> <span id="BIDNUMBER"></span> <label class="rc"> 人次 <b> <s></s>
   </b>
  </label>
 </div>
</div>
<div class="header">
 <div class="nav-wrap">
  <div class="container nav">
   <div class="nav-title ui-left">
    <a href="/product/" class="nav-a">云购分类</a>
    <div class="screen-left nav-topabs ui-left <c:if test="${nav!='home' }">head_nofix</c:if>">
     <ul class="nav-sub">
      <c:forEach items="${pcates}" var="pcate">
       <li><a href="/product/list/${pcate.id }"> <em class="iconfontYC">${pcate.icons }</em> <span>${pcate.name }</span>
       </a></li>
      </c:forEach>
     </ul>
    </div>
   </div>
   <ul class="nav-main ui-left">
    <li <c:if test="${nav=='home' }">class="on"</c:if>><a href="/">首页</a></li>
    <li <c:if test="${nav=='product' }">class="on"</c:if>><a href="/product/">一元云购</a></li>
    </li>
    <li <c:if test="${nav=='win' }">class="on"</c:if>><a href="/win/">最新揭晓</a></li>
    <li <c:if test="${nav=='share' }">class="on"</c:if>><a href="/share/">晒单分享</a></li>
    <li <c:if test="${nav=='yaoqin' }">class="on"</c:if>><a href="/member/yq/">邀请 <img src="${ygBase}/style/images/t-hot.gif" />
    </a></li>
    <li <c:if test="${nav=='bigwheel' }">class="on"</c:if>><a href="/member/bigwheel/">大转盘 <img src="${ygBase}/style/images/t-hot.gif" />
    </a></li>
    <li <c:if test="${nav=='qiandao' }">class="on"</c:if>><a href="/member/qiandao/">每日签到</a></li>
   </ul>
  </div>
 </div>
</div>
