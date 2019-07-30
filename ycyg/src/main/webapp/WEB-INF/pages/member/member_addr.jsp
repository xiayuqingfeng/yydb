<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>幸运记录_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_addr.css" />
</head>
<body>
 <%@ include file="../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="addr" />
     <%@ include file="member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <!-- begin right main  -->
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item">
         <a href="/member/">我的夺宝</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item w-crumbs-active">收货地址</li>
       </ul>
       <div class="m-user-address" id="pro-view-2" module-id="module-3" module-launched="true">

        <div class="m-user-address-title m-user-address-title-list f-clear" style="display: block;">
         <b>我的地址</b>   
         <p class="m-user-address-subtitle">
          已保存了
          <strong pro="nowAmount">${fn:length(addressList) }</strong>
          条地址，还可以保存
          <strong pro="leftAmount">${5-fn:length(addressList) }</strong>
          条哦~
         </p>     
        </div>
        <div pro="addresslist" class="m-user-address-list">
         <div class="w-address-bar" id="pro-view-21">
          <div class="address-bar-title" style="display:">
           <span class="title-txt">已保存的有效收货地址</span>
           <a class="title-btn" href="#addr_edit_title" id="a_add">新增收货地址</a>
          </div>
          <ul pro="addressbar" class="bar-view">
           <li class="bar-header f-clear">
            <div class="bar-item receiver">收货人</div>
            <div class="bar-item address">收货地址</div>
            <div class="bar-item mobile">联系电话</div>
            <div class="bar-item operation">操作</div>
           </li>
           <c:forEach items="${addressList}" var="addr">
           <li class="address-bar f-clear">
            <div class="bar-item receiver" style="margin-left: 16px;">${addr.trueName}</div>
            <div class="bar-item address">${addr.provinceName}&nbsp;${addr.cityName}&nbsp;${addr.areaName}&nbsp;${addr.areaInfo}</div>
            <div class="bar-item mobile">${addr.mobile}</div>
            <div class="bar-item operation">
             <c:choose>
             <c:when test="${addr.isDefaultAddress==1}">
             <span class="address-status default-status">默认地址</span>
             </c:when>
             <c:otherwise>
       <!--       <a pro="setdefault" href="javascript:void(0)" class="address-status set-default">设为默认地址</a> -->
             </c:otherwise>
             </c:choose>
             &nbsp;
             <a  href="/member/addr/${addr.id}#addr_edit_title">修改</a>
             &nbsp;|&nbsp;
             <a href="javascript:void(0)" target="iframeNews" url="/member/addr_del/${addr.id}" class="a_del">删除</a>
            </div>
            <i class="selected-ico"></i>
           </li>
            </c:forEach>
          </ul>
         </div>
        </div>
        <div id="addr_edit_title" class="m-user-address-title m-user-address-title-add"  <c:if test="${ empty address}">style="display: none;"</c:if> >
         <b>编辑收货地址</b>
        </div>
        <div id="addr_edit_content"  class="m-user-address-edit" <c:if test="${ empty address}">style="display: none;"</c:if> >
         <form  action="/member/addr/save" target="iframeNews" method="post" > 
          <input type="hidden" value="${fromUrl }" name="fromUrl"/>
          <input type="hidden" value="${address.id }" name="id"/>
          <div pro="area" class="w-address-form-item">
           <div class="w-address-form-name">
            <span class="w-txt-impt">*&nbsp;</span>
            配送地区
           </div>
            <input type="hidden"  id="provinceName" name="provinceName" value="${addr.provinceName}"/>
            <input type="hidden"  id="cityName" name="cityName" value="${addr.cityName}" />
             <input type="hidden" id="areaName" name="areaName" value="${addr.areaName}" /> 
            <select id="pf-select-area1" ref="pf-select-area2" name="provinceId" areaId="${address.provinceId}" style="max-width: 6rem;    height: 30px;"></select>
            </select>
            <select id="pf-select-area2" ref="pf-select-area3" name="cityId" areaId="${address.cityId}" style="max-width: 6rem;    height: 30px;"></select>
            <select id="pf-select-area3" name="areaId" areaId="${address.areaId}" style="max-width: 6rem;    height: 30px;"></select>
           </div> 
          <div pro="address" class="w-address-form-item">
           <div class="w-address-form-name address">
            <span class="w-txt-impt">*&nbsp;</span>
            详情地址
           </div>
           <div class="w-input w-input-textarea w-address-area" >
            <textarea class="w-input-input"  name="areaInfo" placeholder="不需要重复填写省/市/区">${address.areaInfo}</textarea>
           </div>
          </div>
          <div pro="receiver" class="w-address-form-item">
           <div class="w-address-form-name">
            <span class="w-txt-impt">*&nbsp;</span>
            收 货 人
           </div>
           <div class="w-input">
            <input class="w-input-input" value="${address.trueName}" name="trueName"  type="text" placeholder="请使用真实姓名，长度不超过8个字" style="width: 482px;">
           </div>
          </div>
          <div pro="mobile" class="w-address-form-item">
           <div class="w-address-form-name">
            <span class="w-txt-impt">*&nbsp;</span>
            手机号码
           </div>
           <div class="w-input">
            <input class="w-input-input" value="${address.mobile}"  name="mobile"  type="text" maxlength="11" placeholder="手机号码必须填" style="width: 482px;">
           </div>
          </div>
          
          <div pro="zipcode" class="w-address-form-item">
           <div class="w-address-form-name">邮政编码</div>
           <div class="w-input">
            <input class="w-input-input" value="${address.zip}"  name="zip"  type="text" maxlength="6" style="width: 482px;">
           </div>
          </div>
          <div pro="default" class="w-address-form-item">
           <div class="w-address-form-name">&nbsp;</div>
           <label class="w-checkbox">
            <input type="checkbox" name="isDefaultAddress" <c:if test="${address.isDefaultAddress==1}">checked</c:if> value="1">
             <span>设置为默认收货地址</span>
           </label>
          </div>
          <div class="w-address-form-item">
           <div class="w-address-form-name">&nbsp;</div>
           <button class="w-button w-button-main" type="submit"  style="width: 140px; height: 45px; font-size: 18px; font-weight: normal;">
            <span>保存地址</span>
           </button>
          </div>
          </form>
         </div>
        </div>
       </div>
      </div>
      <div class="m-user-frame-clear"></div>
       <!-- end right main -->
     </div>
    </div>
   </div>
  </div>
  <iframe id="iframeNews" name="iframeNews" style="display: none;"></iframe>
 <script type="text/javascript" src="${ygBase}/style/layer/layer.min.js"></script>
 <%@ include file="../pub/index/index_foot.jsp"%>
  <script type="text/javascript">
      	var provinceId = "${address.provinceId}";
      	var cityId = "${address.cityId}";
      	var areaId = "${address.areaId}";
      	//点击新增
		$("#a_add").click(function(){
			$("#addr_edit_title b").text("新增收货地址");
			$("#addr_edit_title").show();
			$("#addr_edit_content").show();
			
		});
      	//删除
      	$(".a_del").click(function(){
			if(confirm("确定要删除吗")){
				$("#iframeNews").attr("src",$(this).attr("url"));
			}
			
		});
      	if(${fn:length(addressList)}==0){
      		$("#a_add").click();
      	}
  </script>
  <script type="text/javascript" src="${mimeBase}/scripts/pub/common/area.js"></script>
 </div>
</body>
</html>
