<%@ page language="java" pageEncoding="UTF-8" import="cn.com.yyg.base.entity.PayRecordEntity"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>购物车_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <div id="breadCrumb">
  <div class="breadCrumb-txt">
   <a href="/">首页</a>
   <font class='st'> > </font>
   <em>购物车</em>
  </div>
 </div>
 <div class="g-body blue">
  <div class="m-header">
   <div class="g-wrap">
    <div id="progress" class="progress-02">
     <ul class="fn-clear">
      <li class="dq">
       <span>1</span>
       <p>提交订单</p>
      </li>
      <li class="dq">
       <span>2</span>
       <p>支付订单</p>
      </li>
      <li>
       <span>3</span>
       <p>支付成功，等待揭晓</p>
      </li>
      <li>
       <span>4</span>
       <p>揭晓幸运码</p>
      </li>
      <li>
       <span>5</span>
       <p>奖品派发</p>
      </li>
     </ul>
    </div>
   </div>
  </div>
  <form action="/member/buy/paySubmit" id="checkoutFrom" method="post" onsubmit="return submitCheck()">
   <input type="hidden" name="payType" id="payType">
    <div class="g-wrap">
     <ul class="m-cart-address">
      <li style="display: none;">
       <b></b>
       <label>
        <input type="radio" style="visibility: hidden">
         <a href="#">使用其它地址</a>
       </label>
      </li>
     </ul>
     <div class="m-cart-order m-cart-order-list">
      <div data-pro="order">
       <table class="w-table">
        <caption>订单奖品</caption>
        <colgroup>
         <col class="w-table-col0">
          <col class="w-table-col1">
           <col class="w-table-col2">
            <col class="w-table-col3">
             <col class="w-table-col5">
        </colgroup>
        <thead>
         <tr>
          <th>奖品名称</th>
          <th class="tc">价值</th>
          <th class="tc">云购价</th>
          <th class="tc">剩余人次</th>
          <th class="tc">参与人次</th>
          <th class="tc">小计</th>
         </tr>
        </thead>
        <tbody>
         <c:forEach items="${results}" var="item">
         <tr>
          <td style="text-align: left;"> 
              <c:if test="${item.success==true }">
                <input type="hidden" value="${item.product.id}" name="ids"/>
             </c:if> 
             <a target="_blank" href="/product/${item.product.id}.html">(第${item.product.period}期)${item.product.name}</a>
             <p class="txt-err">${item.remark}</p>
          </td>
          <td>￥${item.product.totalNum}</td>
          <td>￥${item.product.singlePrice}</td>
          <td>${item.product.leaveNum}</td>
          <td>${item.buyNum}</td>
          <td>
           <em> ￥${item.product.singlePrice*item.buyNum} </em>
          </td>
         </tr>
         </c:forEach>
        </tbody>
       </table>
       <div class="m-cart-order-total">
        <a href="/cart/" style="font-size: 14px;">返回清单修改</a>
        <span class="total txt-gray" style="background: #fff">
         合计：
         <strong>￥${totalPrice}</strong>
        </span>
       </div>
       <div class="m-cart-order-options" style="font-size: 16px;">
        <div class="option" style="display:none">
         <label class="w-checkbox w-checkbox-disabled">
          <input type="checkbox" value="1" id="accountBalancePay"  name="accountBalancePay"  onclick="changePay()" checked>
           <span data-pro="text" style="color: #000; ">
           使用账户余额支付：
            <span class="color01" id="accountBalance">￥${accountBalance }</span>
         </label>
        </div>
        <div class="option">
         <label class="w-checkbox w-checkbox-disabled">
           <span data-pro="text" style="color: #000;display: ">
           请选择支付方式
           </span>
         </label>
        </div>
       </div>
      </div>
      <div class="m-cart-order-pay f-clear" style="padding-bottom: 0;">
       <div class="pay_online">
        <div>
         <div class="w-pay">
          <div class="w-pay-selector">
            <div style="float: left">
            <label class="w-pay-type w-pay-type-3 alipay" payType="<%=PayRecordEntity.PAY_TYPE_YUE%>"> 
              账户余额支付<br/>￥${accountBalance }
            </label>
           </div>
           <div style="float: left;display:">
            <label class="w-pay-type w-pay-type-3 alipay" payType="<%=PayRecordEntity.PAY_TYPE_ALI%>"> 
              <img src="http://mimg.127.net/p/yy/lib/img/bank/0023.v2.png" alt="支付宝">
            </label>
           </div>
           <div style="float: left;display:">
            <label class="w-pay-type w-pay-type-3  wxpay" payType="<%=PayRecordEntity.PAY_TYPE_WX%>">
             <img src="/assets/images/WePayLogo.png" alt="" width="142"/> 
            </label>
           </div>
          </div>
         </div>
        </div>
       </div>
      </div>
     </div>
     <div class="m-cart-submit" data-pro="submit">
      <button type="submit" class="w-button w-button-main w-button-xl" id="pro-view-10">
       <span>立即支付</span>
      </button>
     </div>
     <div class="w-payTips" style="margin-bottom: 10px;">
      <p class="w-payTips-title">付款遇到问题</p>
      <ol>
       <li>1、系统未对商品购买数量进行锁定，请选择快速的支付方式，否则存在支付时商品数量不足的情况；</li>
       <li>2、如果您有财付通或支付宝账户，可将款项先充入相应账户内，然后使用账户余额进行一次性支付；</li>
       <li>3、如果已经扣款，商品未购买成功，有可能因为网络原因导致，系统将金额退还到账户余额中。</li>
      </ol>
     </div>
    </div>
  </form>
 </div>
 <%@ include file="../index/index_foot.jsp"%>
 <script type="text/javascript"> 
    $(function(){        
        $('.w-pay-selector').each(function(){
            var selector = $(this);
            selector.find('.w-pay-type').bind('click',function(){
                $('.w-pay-selector').find('.w-pay-type').removeClass('w-pay-selected');
                $('.w-pay-selector').find('.w-pay-type').children('input[name=pay_id]').attr('checked',false);
                $(this).addClass('w-pay-selected');
                $(this).children('input[name=pay_id]').attr('checked',true);
               
            });
        });
		
        changePay();

    }); 
	function changePay(){
		var accountBalance=Number($("#lastTotalPrice").attr("accountBalance"));
		var totalPrice=Number($("#lastTotalPrice").attr("totalPrice"));
		//使用余额付
		if($("#accountBalancePay").is(":checked")){
			if(accountBalance>=totalPrice){
				$("#lastTotalPrice").html("￥0");
			}else{
				$("#lastTotalPrice").html("￥"+(totalPrice-accountBalance));
			}
		}else{
			$("#lastTotalPrice").html("￥"+$("#lastTotalPrice").attr("totalPrice"));
		}
	}
    function submitCheck(){
    	var payType=$('.w-pay-selector .w-pay-selected').attr("payType");   
    	if(!payType){
/*             $.layer({
                type: 2,
                fix: true,
                shadeClose: false,
                title: '温馨提示',
                area: ['420px' , '230px']
            }); */
            alert("请选择支付方式");
    		return false;
    	}
		$("#payType").val(payType);
        return true;
    }
</script>
</body>
</html>
