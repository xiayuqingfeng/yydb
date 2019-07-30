<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../pub/commons/commons_resource_head.jsp"%>
<title>确认订单_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_addr.css" />
</head>
<body>
 <%@ include file="../../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="addr" />
     <%@ include file="../member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <!-- begin right main  -->
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item">
         <a href="/member/">我的夺宝</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item">
         <a href="/member/win/">幸运记录</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item w-crumbs-active">确认订单</li>
       </ul>
       <div class="m-user-address" id="pro-view-2" module-id="module-3" module-launched="true">
        <form action="/member/order/buy/save">
        <input type="hidden" name="userYgId" value="${ygRecord.id}"/>
        <div class="m-user-address-title m-user-address-title-list f-clear" style="display: block;">
         <b>寄送至</b> 
          &nbsp;&nbsp;&nbsp;&nbsp;<a class="title-btn" href="/member/addr/" id="a_add">管理收货地址</a>
        </div>
        <div pro="addresslist" class="m-user-address-list">
         <div class="w-address-bar" id="pro-view-21">
          <div class="address-bar-title" style="display: none;">
           <span class="title-txt">已保存的有效收货地址</span>
           <a class="title-btn" href="javascript:void(0)" pro="addAddress">新增收货地址</a>
          </div>
          <ul pro="addressbar" class="bar-view">
           <li class="bar-header f-clear">
            <div class="bar-item" style="width:60px">选择</div>
            <div class="bar-item receiver">收货人</div>
            <div class="bar-item address">收货地址</div>
            <div class="bar-item mobile">联系电话</div>
           </li>
           <c:forEach items="${addressList}" var="addr" varStatus="status">
           <li class="address-bar f-clear">
           <div class="bar-item" style="width:60px"> 
             &nbsp; <input type="radio" name="addrId" value="${addr.id}" <c:if test="${status.count==1}">checked</c:if>></input>
            </div>
            <div class="bar-item receiver" style="margin-left: 16px;">${addr.trueName}</div>
            <div class="bar-item address">${addr.provinceName}&nbsp;${addr.cityName}&nbsp;${addr.areaName}&nbsp;${addr.areaInfo}</div>
            <div class="bar-item mobile">${addr.mobile}</div>
             
           </li>
            </c:forEach>
          </ul>
         </div>
        </div> 
        <div pro="addressedit" class="m-user-address-edit"> 
          <div pro="zipcode" class="w-address-form-item">
           <div class="w-address-form-name">商品：</div>
           <div class="w-input">
          <a href="/product/${ygRecord.ygProductId }.html" target="_blank"> (第${ygRecord.period }期) ${ygRecord.productName } ￥${ygRecord.totalNum }</a>
           </div>
          </div>
          <div pro="receiver" class="w-address-form-item">
           <div class="w-address-form-name">
            订单备注：
           </div>
           <div class="w-input" >
            <input class="w-input-input" type="text" id="remark" name="remark" style="width: 482px;">
           </div>
          </div>
          
          <div class="w-address-form-item">
           <div class="w-address-form-name">&nbsp;</div>
           <button class="w-button w-button-main" type="submit" id="pro-view-11" style="width: 140px; height: 45px; font-size: 18px; font-weight: normal;">
            <span>确认领奖</span>
           </button>
          </div> 
        </div>
        </form>
       </div>
      </div>
      <div class="m-user-frame-clear"></div>
       <!-- end right main -->
     </div>
    </div>
   </div>
  </div>
 <%@ include file="../../pub/index/index_foot.jsp"%>
 </div>
</body>
</html>
