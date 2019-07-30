<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>我要晒单_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_addr.css" />
<style type="text/css">
.upload_pic ul ul {
	float: left;
}

.upload_pic ul li {
	float: left;
	width: 100px;
	height: 100px;
	border: 1px solid #ddd;
	position: relative;
	margin-right: 28px;
	margin-bottom: 20px;
}

.upload_pic ul li b {
	position: absolute;
	right: -18px;
	top: -6px;
	cursor: pointer;
	background: url(${mimeBase}/images/member/close.png) no-repeat left center;
	width: 13px;
	height: 13px;
}

.upload_pic ul li .fil {
	width: 85px;
	height: 30px;
	background-color: rgba(226, 222, 222, 0.5);
	position: absolute;
	bottom: 0;
	line-height: 30px;
	padding-left: 10px;
	padding-right: 5px;
}
.upload_pic ul li .fil p {
	float: left;
	color: #fff;
	cursor: pointer;
}

 

</style>
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
        <li class="w-crumbs-item">
         <a href="/member/share/">我的晒单</a>
         <span class="w-crumbs-split">&gt;</span>
        </li>
        <li class="w-crumbs-item w-crumbs-active">我要晒单</li>
       </ul>
       <div class="m-user-address" id="pro-view-2" module-id="module-3" module-launched="true">
        <form id="saveForm" method="post" enctype="multipart/form-data" action="/member/share/save" onsubmit="return PhotoObject.check()">      
       
        <input type="hidden" name="orderId" value="${order.id}"/>       
        <div class="m-user-address-title m-user-address-title-list f-clear" style="display: block;">
         <b>晒单</b> 
        </div> 
        <div class="m-user-address-edit"> 
          <div class="w-address-form-item">
           <div class="w-address-form-name">商品：</div>
           <div class="w-input" style=" margin-top: 6px;">
          <a href="/product/${order.ygProduct.id }.html" target="_blank"> (第${order.ygProduct.period }期) ${order.ygProduct.name } ￥${order.ygProduct.totalNum*order.ygProduct.singlePrice }</a>
           </div>
          </div>
          <div class="w-address-form-item">
           <div class="w-address-form-name">
            <span class="w-txt-impt">*&nbsp;</span>
            晒单标题：
           </div>
           <div class="w-input" >
            <input class="w-input-input" type="text" id="title" name="title" style="width: 482px;">
           </div>
          </div>
          <div class="w-address-form-item">
           <div class="w-address-form-name address">晒单内容：</div>
           <div class="w-input w-input-textarea w-address-area">
            <textarea class="w-input-input" id="content" name="content"></textarea>
           </div>
          </div>
          <div class="w-address-form-item">
           <div class="w-address-form-name">上传图片：</div>
           <div class="w-input w-input-textarea"> 
            <div class="clear"></div>
            <div class="upload_pic">
             <ul>
              <li class="shareImgPanel">
               <a class="viewImg">
               <img src="" id="img1" width="100" height="100" />
               </a>
               <div class="fil">   
                 <p style="margin-top:3px"><input type="file" accept="image/*"  imgId="img1"  name="file" style="width:60px;display:"/></p>
               </div>
              </li>
              <li class="shareImgPanel">
               <a class="viewImg">
               <img src="" id="img2" width="100" height="100" />
               </a>
               <div class="fil">
                 <p style="margin-top:3px"><input type="file" accept="image/*"  imgId="img2"  name="file" style="width:60px;display:"/></p>
               </div>
              </li>
              <li class="shareImgPanel">
               <a class="viewImg">
               <img src="" id="img3" width="100" height="100" />
               </a>
               <div class="fil">
                 <p style="margin-top:3px"><input type="file" accept="image/*"  imgId="img3"  name="file" style="width:60px;display:"/></p>
                </a>
               </div>
              </li>
             </ul>
            </div>
           </div>
          </div>
          <div class="w-address-form-item">
           <div class="w-address-form-name">&nbsp;</div>
           <button class="w-button w-button-main" type="submit" id="pro-view-11" style="width: 140px; height: 45px; font-size: 18px; font-weight: normal;">
            <span>提交晒单</span>
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
 <%@ include file="../pub/index/index_foot.jsp"%>  
 <script src="${mimeBase}/vendors/img-upload-preview/img-upload-preview.js?v=${version}" type="text/javascript"></script>
 <script src="${mimeBase}/scripts/member/share.js?v=${version}" type="text/javascript"></script>
 </div>
</body>
</html>
