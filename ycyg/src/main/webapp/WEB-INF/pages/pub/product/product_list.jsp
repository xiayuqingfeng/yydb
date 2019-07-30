<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>全部商品_${comName }</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<body>
<c:set var="nav" value="product"/>
<%@ include file="../index/index_head.jsp"%>
 <link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
 <div class="product-top menu-show container">
  <div class="product-weizhi">
   <a href="/">首页</a>
   <font class='st'> > </font>
   <a href="/product/"><em>一元云购</em></a>
  </div>
  <dl class="product-top1">
   <dt>
    <big><a href="/product/">全部商品</a></big>
    （共
    <span>${page.totalItems }</span>
    件商品）
   </dt>
   <dd>
    <a href="/product/" <c:if test="${0==cateId }">class="hover"</c:if>>
     <i class="iconfontYC">&#xe60a;</i>
     <span>全部商品</span>
    </a>
     <c:forEach items="${pcates}" var="pcate">
    <a href="/product/list/${pcate.id}-0-1-1.html"  <c:if test="${pcate.id==cateId }">class="hover"</c:if>>
     <i class="iconfontYC">${pcate.icons }</i>
     <span>${pcate.name }</span>
    </a>
    </c:forEach>
   </dd>
  </dl>
  <div class="product-list">
   <a href="/product/" class="product-list1">全部商品</a>
   <h6>排序：</h6>
   <ul class="m-list-sortList">
    <li <c:if test="${orderType==1 }">class="selected"</c:if>>
     <a href="/product/list/${cateId}-0-1-1.html">人气奖品</a>
    </li>
    <li <c:if test="${orderType==2 }">class="selected"</c:if>>
     <a href="/product/list/${cateId}-0-2-1.html">剩余人次</a>
    </li>
    <li <c:if test="${orderType==3 }">class="selected"</c:if>>
     <a href="/product/list/${cateId}-0-3-1.html">最新商品</a>
    </li>
    <li <c:if test="${orderType==4 }">class="selected"</c:if>>
     <a href="/product/list/${cateId}-0-4-1.html">
      总需人次
      <i class="ico ico-arrow-sort ico-arrow-sort-gray-down" title="降序"></i>
     </a>
    </li>
    <li <c:if test="${orderType==5 }">class="selected"</c:if>>
     <a href="/product/list/${cateId}-0-5-1.html">
      总需人次
      <i class="ico ico-arrow-sort ico-arrow-sort-gray-up" title="升序"></i>
     </a>
    </li>
   </ul>
  </div>
 </div>
 <div class="main-pd1">
  <div class="container">
   <ul class="ten box-sizing">   
    <c:forEach items="${page.result }" var="item">
    <li>
   <!--   <img class="sale_db" src="/style/images/sale_db.png" style="right: 5px; top: 5px;" /> -->
     <div class="ten1 index1-img">
      <em class="scrollLoadingDiv">
       <a href="${base }/product/${item.id}.html" target="_blank" title="${item.name}">
        <img alt="${item.name}" class="scrollLoading" src="${ygBase}/style/images/pix.gif" data-url="${item.logoPath }" id="buy_img_${item.id}" />
       </a>
      </em>
      <p class="banner-title">
       <a href="${base }/product/${item.id}.html" target="_blank" title="${item.name}">
        <span class="color01">(第${item.period}期)</span>
        ${item.name}
       </a>
      </p>
      <p class="banner-money">总需：${item.totalNum } 人次</p>
      <div class="progressBar">
       <p class="progressBar-wrap">
        <span style="width: <fmt:formatNumber value="${item.usedNum/item.totalNum*100}" pattern="0.0#"/>%"></span>
       </p>
       <div class="progressBar-txt">
        <div class="txt-l">
         <p>
          <b>${item.usedNum }</b>
         </p>
         <p>已参与人次</p>
        </div>
        <div class="txt-r">
         <p>
          <b>${item.leaveNum }</b>
         </p>
         <p>剩余人次</p>
        </div>
       </div>
      </div>
     </div>
     <div class="ten2">
      <span>我要参与：</span>
      <div class="ten2-1">
       <input type="button" value="-" class="ten-jian ten-i w-number-btn-minus" />
       <input type="text" class="ten-text w-number-input" id="qty_${item.id}" data-max="${item.leaveNum}" value="10" />
       <input type="button" value="+" class="ten-jian ten-i w-number-btn-plus" />
      </div>
      <span>人次</span>
      <p>
       <a  href="javascript:void(0)" onclick="addToCart('${item.id}','buy')" class="ten-a">立即云购</a>
       <a  href="javascript:void(0)" onclick="addToCart('${item.id}','',this)" class="ten-a1">加入购物车</a>
      </p>
     </div>
    </li>
    </c:forEach>
   </ul>
  </div>
  <script type="text/javascript">
        $(function(){
        	//我要参与数量
            $(".w-number-btn-plus").click(function(){
                var max = $(this).parent().children('input.w-number-input').attr('data-max');
                var qty = $(this).parent().children('input.w-number-input').val()*1;
                if(qty < max){
                    $(this).parent().children('input.w-number-input').val(qty+1);
                }
            });
            $(".w-number-btn-minus").click(function(){
                var qty = $(this).parent().children('input.w-number-input').val()*1;
                if(qty>1){
                    $(this).parent().children('input.w-number-input').val(qty-1);
                }
            });
            $(".w-number-input").blur(function(){
                var max = $(this).attr('data-max')*1;
                if($(this).val()>max){
                    $(this).val(max);
                }
            });
        });
    </script>
 </div>
 
  <div style="width:100%;text-align:center">
   <%@ include file="../commons/page.jsp"%>
  <script type="text/javascript">
   $(".w-pager button").click(function() {
     window.location="/product/list/${cateId}-0-1-"+$(this).attr("pageNo")+".html";
   });
  </script>
  </div>

 <div class="index3" style="display:none">
  <div class="container">
   <dl class="index-top">
    <dt class="index-shuaxin">
     <a href="javascript:;" onclick="randLove()">换一批</a>
    </dt>
    <dd>
     <span>
      <img src="/style/theme_01/css/images/love.png">
     </span>
     <big>猜你喜欢</big>
    </dd>
   </dl>
   <ul class="index2 box-sizing love" id="yunbuy_love"></ul>
  </div>
 </div>

 <%@ include file="../index/index_foot.jsp"%>
</body>
</html>