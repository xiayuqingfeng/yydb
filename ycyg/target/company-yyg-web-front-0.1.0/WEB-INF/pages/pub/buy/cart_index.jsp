<%@ page language="java" pageEncoding="UTF-8"%>
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
  
  <style type="text/css">
    .msg{ width:1200px; margin: 30px auto 20px; border:1px solid #e3e3e3; }
    .msg_content{ margin: 50px 20px 70px 120px; }
    .msg_content h2{ font-size: 32px; padding:30px 0 10px 0; line-height: 1.2; font-weight: normal; }
    .msg_content h2 b{ font-size: 50px; }
    .msg_link{ padding-top:10px;}
    .msg_link p{ margin-bottom: 10px; margin-right: 10px; float: left; }
    .msg_link a{ display:inline-block; background: #33cc99; padding: 0 20px; line-height: 40px; color: #fff; font-size: 24px; font-weight: bold; }
  </style>
  <c:choose>
  <c:when test="${empty results }">
    <div class="msg msg_3">
    <div class="msg_content">
     <table>
      <tbody>
       <tr>
        <td width="10" style="padding-right: 80px;">
         <img src="${ygBase }/style/images/msg_3.png">
        </td>
        <td>
         <h2 class="color01">
          你的购物车还是空的
          <br>赶紧行动吧！
         </h2>
         <div class="msg_link">
          <p>
           <a href="/product/">马上去购物</a>
          </p>
         </div>
        </td>
       </tr>
      </tbody>
     </table>
    </div>
   </div>
  </c:when>
  <c:otherwise>
 
  <div class="g-body mb15">
   <div class="m-user">
    <div class="g-wrap">
     <div class="g-body">
      <form action="/member/buy/paycheck" method="post">
       <div class="m-header">
        <div class="g-wrap">
         <div id="progress" class="progress-01">
          <ul class="fn-clear">
           <li class="dq">
            <span>1</span>
            <p>提交订单</p>
           </li>
           <li>
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
       <div class="m-cart-order" data-pro="order">
        <table id="pro-view-5" class="w-table ">
         <colgroup>
          <col class="w-table-col0">
           <col class="w-table-col1">
            <col class="w-table-col2">
             <col class="w-table-col3">
              <col class="w-table-col4">
               <col class="w-table-col5">
                <col class="w-table-col6">
                 <col class="w-table-col7">
         </colgroup>
         <thead>
          <tr>
           <th class="w-table-chk " style="padding: 8px 15px;">
            <label id="pro-view-6" class="w-checkbox">
             <input class="selectall" type="checkbox" checked>
            </label>
           </th>
           <th style="text-align: center;">云购商品</th>
           <th style="text-align: center;">名称</th>
           <th style="text-align: center;">价值</th>
           <th style="text-align: center;">云购价(元/人次)</th>
           <th style="text-align: center;">参与人次</th>
           <th style="text-align: center;"></th>
           <th style="text-align: center;">小计</th>
           <th class="w-table-opt" style="">操作</th>
          </tr>
         </thead>
         <c:forEach items="${results}" var="item">
         <tr>
          <td class="w-table-chk" style="">
           <input value="${item.product.id}" name="ids" type="checkbox" <c:if test="${item.success==false }">disabled</c:if> />
          </td>
          <td style="">
           <img alt="(第${item.product.period}期)${item.product.name}" height="48" src="${item.product.logoPath}" width="64">
          </td>
          <td style="">
           <p>
            <a href="/product/${item.product.id}.html" target="_blank">(第${item.product.period}期)${item.product.name}</a>
           </p>
           <p>
            总需
            <b class="color02">${item.product.totalNum}</b>
            人次参与，还剩${item.product.leaveNum}人次
           </p>
          </td>
          <td style="text-align: center;">￥${item.product.origPrice}</td>
          <td style="text-align: center;">${item.product.singlePrice}</td>
          <td style="text-align: center;">
           <div id="pro-view-9" class="w-number w-number-inline">
            <input class="w-number-input" value="${item.buyNum}" id="cartNum_${item.product.id}"  data-id="${item.product.id}" data-max="${item.product.leaveNum}">
             <a class="w-number-btn w-number-btn-plus" data-pro="plus" href="javascript:void(0);">+</a>
             <a class="w-number-btn w-number-btn-minus" data-pro="minus" href="javascript:void(0);">-</a>
           </div>
           <p class="txt-err">${item.remark}</p>
          </td>
          <td style="text-align: center;"></td>
          <td style="text-align: center;">
           <em>
            ￥
            <span id="subtotal_${item.product.id}" singlePrice="${item.product.singlePrice}">${item.product.singlePrice*item.buyNum}</span>
           </em>
          </td>
          <td class="w-table-opt">
          
           <a data-pro="del" onclick="delCart('${item.id}',1)" class="w-button w-button-main" style="color: #fff;">删除</a>
          </td>
         </tr>
         </c:forEach>
        </table>
        <div class="total"> 
        <input class="selectall" type="checkbox" >
        <a href="javascript:void(0)" onclick="deleteSelected()">删除选中</a>
        总计：<strong id="totalPrice">￥41</strong></div>
       </div>
       <div class="m-cart-submit" data-pro="submit">
        <a id="pro-view-1" class="w-button w-button-aside w-button-xl" href="/">
         <span>返回首页</span>
        </a>
        <input type="hidden" name="Submit" value="1" />
        <button class="w-button w-button-main w-button-xl " onclick="submit()">
         <span>同意以下协议，去结算</span>
        </button>
       </div>
       <input type="hidden" name="address_id" value="" />
      </form>
      <div class="m-helpcenter-detail-bd" style="height: auto;">
       <h4 style="text-align: left;"></h4>
       <h4 style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">一</span>
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">、配送及费用</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </h4>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">${comName}将会把产品送到您所指定的送货地址。全国免费配送（港澳台以及偏远地区地区除外）。</span>
       </p>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">请清楚准确地填写您的真实姓名、送货地址及联系方式。因如下情况造成配送延迟或无法配送等，本站将不承担责任：</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </p>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">1、客户提供错误信息和不详细的地址；</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </p>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">2、货物送达无人签收，由此造成的重复配送所产生的费用及相关的后果。</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </p>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">3、不可抗力，例如：自然灾害、交通戒严、突发战争等。</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </p>
       <h4 style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">二</span>
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">、商品缺货规则</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </h4>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">由于市场变化及各种以合理商业努力难以控制的因素的影响，${comName}网无法承诺用户所获得的商品都会有货；用户获得的商品或服务如果发生缺货，协议双方均无权取消该交易，${comName}网将通过有效方式通知用户进行换货，用户可选择换购本商城同等价位的商品（一件或多件），或选择补差价换购高价位商品。</span>
        <span style="font-size: 10.5pt; font-family: 'sans serif';"></span>
       </p>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';">${comName}网可对即将上市的商品或服务进行预售登记，${comName}网会在商品或者服务正式上市之后尽最大努力在最快时间内给商品获得者安排发货，预售登记并不做交易处理，不构成要约。</span>
       </p>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';"> </span>
       </p>
       <h4 style="text-indent: 21.0000pt; text-align: justify; vertical-align:;">
        <span style="color: #333333; font-size: 14px; font-family: 'Microsoft YaHei';">三、责任限制</span>
       </h4>
       <p class="p15" style="text-align: left; text-indent: 21pt;">
        <span style="font-size: 14px; font-family: 'Microsoft YaHei';"> </span>
       </p>
       <div>
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;">&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span>
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;">1、用户理解并同意，在使用${comName}网服务的过程中，可能会遇到不可抗力等风险因素使${comName}网服务发生中断。不可抗力是指不能预见、不能克服并不能避免且对一方或双方造成重大影响的客观事件，包括但不限于自然灾害如洪水、地震、瘟疫流行和风暴等以及社会事件如战争、动乱、政府行为等。出现上述情况时，${comName}网将努力在第一时间与相关单位配合，及时进行修复，但是由此给用户造成的损失，${comName}网将在法律允许的范围内免责。</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp; &nbsp;
         &nbsp;&nbsp;2、用户理解并同意，${comName}网不能随时预见和防范法律、技术以及其他不可控的风险，对以下情形之一导致的服务中断或受阻，${comName}网不承担责任：</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp;&nbsp;（1）大规模病毒、木马或其他恶意程序、黑客攻击的破坏；</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp;&nbsp;（2）用户或${comName}网的电脑软件、系统、硬件和通信线路出现故障；</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp; （3）用户操作不当；</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp;&nbsp;（4）用户通过非${comName}网授权的方式使用服务；</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp;&nbsp;（5）政府管制等原因可能导致的服务中断、数据丢失以及其他的损失和风险。</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp;&nbsp;（6）其他${comName}网无法控制或合理预见的情形。</span>
        <br />
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px;"> &nbsp; &nbsp; &nbsp;
         &nbsp;&nbsp;3、在法律法规所允许的限度内，因使用${comName}服务而引起的任何损害或经济损失，${comName}承担的全部责任均不超过用户所购买的与该索赔有关的商品价格。这些责任限制条款将在法律所允许的最大限度内适用，并在用户资格被撤销或终止后仍继续有效。&nbsp;</span>
       </div>
       <br />
       <p>
        <br />
       </p>
       <div style="text-align: left;">
        <span style="font-family: 'Microsoft YaHei'; font-size: 14px; background-color: #E53333;"></span>
       </div>
      </div>
      <div class="m-cart-tuijian">
       <div class="w-hd">
        <h3 class="w-hd-title">推荐云购</h3>
        <a class="w-hd-more" href="/product/">想看更多精彩，逛一下吧！</a>
       </div>
       <div class="m-cart-tuijian-bd">
        <ul class="w-goodsList f-clear">
         <c:forEach items="${recomments}" var="recomment" end="4">
         <li class="w-goodsList-item row-first">
          <div class="w-goods w-goods-brief">
           <div class="w-goods-pic">
            <a  href="/product/${recomment.id }.html" title="${recomment.name }" target="_blank">
             <img alt="${recomment.name }" src="${recomment.logoPath }">
            </a>
           </div>
           <p class="w-goods-title f-txtabb">
            <a href="/product/${recomment.id }.html" target="_blank" title="${recomment.name }"> (第${recomment.period}期)${recomment.name }</a>
           </p>
           <p class="w-goods-price">总需：${recomment.totalNum }人次</p>
          </div>
         </li>
         </c:forEach>
        </ul>
       </div>
      </div>
     </div>
    </div>
   </div>
  </div>
   
  </c:otherwise>
  </c:choose>
   <%@ include file="../index/index_foot.jsp"%>
  <script type="text/javascript">
        //参与人次
        $(".w-number-btn-plus").click(function(){
            var id = $(this).parent().children('input.w-number-input').attr('data-id');
            var max = $(this).parent().children('input.w-number-input').attr('data-max');
            var qty = $(this).parent().children('input.w-number-input').val()*1;
            if(qty < max){
                $(this).parent().children('input.w-number-input').val(qty+1);
            }
            updatecart(id,$(this).parent().children('input.w-number-input').val(),'');
        });
        $(".w-number-btn-minus").click(function(){
            var id = $(this).parent().children('input.w-number-input').attr('data-id');
            var qty = $(this).parent().children('input.w-number-input').val()*1;
            if(qty>1){
                $(this).parent().children('input.w-number-input').val(qty-1);
            }
            updatecart(id,$(this).parent().children('input.w-number-input').val(),'');
        });
        $(".w-number-input").blur(function(){
            var id = $(this).attr('data-id');
            var max = $(this).attr('data-max')*1;
            if($(this).val()>max){
                $(this).val(max);
            }
            if($(this).val()<=0) $(this).val(1);
            updatecart(id,$(this).val(),'');
        });
        //更新数量
       function updatecart(id,qty,multi){
        
            $.post('/cart/updateCartNum.json',{"id":id,"buyNum":qty },function(data){
                $('#subtotal_'+id).html(qty*Number($('#subtotal_'+id).attr("singlePrice")));
                show_total();
            },'json');
        }
       
        $('.selectall').click(function(){
            $("input[name='ids']").not(':disabled').prop("checked",$(this).is(':checked'));
            $('.selectall').prop('checked',$(this).is(':checked'));
            show_total();
        });
        $("input[name='ids']").click(function(){
            show_total();
        });
        //统计总价
        function show_total(){
            var ids = '';
            var i=0;
            var totals=0;
            $("input[name='ids']").each(function(){
                if($(this).is(':checked')){
                    i++;
                    ids += i==$("input[name='ids']:checked").length ? $(this).val() :$(this).val()+',';
                    var num=Number($('#cartNum_'+$(this).val()).val());
                    var singlePrice=Number($('#subtotal_'+$(this).val()).attr("singlePrice"));
                    totals+=num*singlePrice;
                }
            });
            $('#totalPrice').html("￥"+totals);
            if(totals==0){
                $('.w-button-xl').addClass('w-button-disabled').attr("disabled",true);
            }else{
                $('.w-button-xl').removeClass('w-button-disabled').removeAttr("disabled");
            }
            //购物车统计刷新
        	loadCart();
        }
		//删除选择的商品
        function deleteSelected(){
        	var ids = '';
            var i = 0;
            $("input[name='ids']").each(function(){
                if($(this).is(':checked')){
                    i++;
                    ids += i==$("input[name='ids']:checked").length ? $(this).val() :$(this).val()+',';
                }
            });
            if(ids!=""){
                delCart(ids,1);
            }
        }
        $('.selectall').click();
</script>
 
</body>
</html> 