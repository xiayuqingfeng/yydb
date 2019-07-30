<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../commons/commons_resource_head.jsp"%>
 <title>购物车_${comName}</title>
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
 <h1>云购购物车</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <c:choose>
  <c:when test="${empty results }">
   <div id="content" class="container main tc">
    <img src="${wapBase}/style/images/msg_3.png" style="max-width: 60%; margin: 30px auto 0;">
     <div class="empty">
      你的购物车还是空的<br>赶紧行动吧！ 
     </div> <a href="/product" class="color02" style="margin: 0 5px; font-size: 14px;">马上去购物</a>
   </div>
  </c:when>
  <c:otherwise>
   <form action="/member/buy/paycheck" method="POST" onsubmit="return validateForm()">
    <div id="content" class="container main">
     <div class="cart-list">
      <c:set var="totalPrice" value="0" />
      <c:forEach items="${results}" var="item">
       <div class="item">
        <div class="pic">
         <a href="/product/${item.product.id}.htm"><img alt="(第${item.product.period}期)${item.product.name}" src="${item.product.logoPath}" width="64" /></a>
        </div>
        <div class="info">
         <div class="title to">
          <%--          <c:if test="${item.success==false }">disabled</c:if> --%>
          <input value="${item.product.id}" style="margin-right: 0.6rem; margin-left: 0.4rem;" name="ids" type="checkbox"
           <c:if test="${item.success==false }">disabled</c:if> <c:if test="${item.success==true }">checked</c:if> /> <a href="/product/${item.product.id}.htm">(第${item.product.period}期)${item.product.name}</a>
         </div>
         <p>
          总需 ${item.product.totalNum} 人次，剩余 <em>${item.product.leaveNum}</em> 人次
         </p>
         <div class="bottom">
          参与人次：
          <div class="number">
           <c:if test="${item.success==true }">
            <c:set var="totalPrice" value="${totalPrice+item.buyNum*item.product.singlePrice}" />
           </c:if>
           <input class="num-input" type="text" data-id="${item.product.id}" data-max="${item.product.leaveNum}" value="${item.buyNum}" style="color: #DA3752;" /> <a
            class="num-btn btn-plus" data-pro="plus" href="javascript:void(0);">+</a> <a class="num-btn btn-minus" data-pro="minus" href="javascript:void(0);">-</a>
          </div>
         </div>
         <p class="txt-err" style="color: #DA3752; margin: 0px 6.2rem;">${item.remark}</p>
         <a class="del" href="javascript:;" onclick="delCart('${item.product.id}',1)"><i class="ap-icon icon-del"></i></a>
        </div>
       </div>
      </c:forEach>
     </div>
    </div>
    <footer class="foot-view">
    <div class="text">
     共参与${results.size()}件产品，总计：<em class="color">￥<i id="total">${totalPrice}</i></em>
    </div>
    <div class="btn">
     <button class="ap-button w-button-xl " type="submit" >提交</button>
    </div>
    </footer>
   </form>
  </c:otherwise>
 </c:choose>
 <script type="text/javascript">
		function validateForm() {
			if ($("#total").html() == 0) {
				layer.alert("请选择商品", 0);
				return false;
			} else {
				return true;
			}
		}
		$(function() {
			//参与人数
			$(".btn-plus").click(function() {
				var id = $(this).parent().children('input.num-input').attr('data-id');
				var max = $(this).parent().children('input.num-input').attr('data-max');
				var qty = $(this).parent().children('input.num-input').val() * 1;
				if (qty < max) {
					$(this).parent().children('input.num-input').val(qty + 1);
				}
				updatecart(id, $(this).parent().children('input.num-input').val(), '');
			});
			$(".btn-minus").click(function() {
				var id = $(this).parent().children('input.num-input').attr('data-id');
				var qty = $(this).parent().children('input.num-input').val() * 1;
				if (qty > 1) {
					$(this).parent().children('input.num-input').val(qty - 1);
				}
				updatecart(id, $(this).parent().children('input.num-input').val(), '');
			});
			$("input.num-input").blur(function() {
				var id = $(this).attr('data-id');
				var max = $(this).attr('data-max') * 1;
				if ($(this).val() > max) {
					$(this).val(max);
				}
				if ($(this).val() <= 0)
					$(this).val(1);
				updatecart(id, $(this).val(), '');
			});
			function updatecart(id, qty, multi) {
				var ids = '';
				var i = 0;
				$("input[name='ids']").each(function() {
					if ($(this).is(':checked')) {
						i++;
						ids += i == $("input[name='ids']:checked").length ? $(this).val() : $(this).val() + ',';
					}
				});
				$.post('/cart/updateCartNum.json', {
					id : id,
					buyNum : qty,
				}, function(data) {
					//$('#total').html(data.total);
					show_total();
				}, 'json');
			}
			$("input[name='ids']").click(function() {
				show_total();
			});
			function show_total() {
				var ids = '';
				var i = 0;
				var arr = new Array();
				$("input[name='ids']").each(function() {
					if ($(this).is(':checked')) {
						i++;
						arr.push($(this).val());
						//ids += i == $("input[name='ids']:checked").length ? $(this).val() : $(this).val() + ',';
					}
				});
				$.post('/cart/total.json', {
					u : 1
				}, function(data) {
					if (data.isSuccess) {
						var total = 0;
						for (var i = 0; i < data.message.length; i++) {
							var o = data.message[i];
							//如果选中
							for ( var j in arr) {
								if (arr[j] == o.product.id) {
									total += o.product.singlePrice * o.buyNum;
								}
							}
						}
						$('#total').html(total);
						if (total == 0) {
							$('.w-button-xl').addClass('w-button-disabled').attr("disabled", true);
						} else {
							$('.w-button-xl').removeClass('w-button-disabled').removeAttr("disabled");
						}
					} else {
						//统计错误
					}

				});
			}
		});
	</script>
</body>
</html>
