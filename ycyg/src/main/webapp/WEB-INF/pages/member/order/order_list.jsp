<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../pub/commons/commons_resource_head.jsp"%>
<title>幸运记录_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<style type="text/css">
.hy-btn2,.hy-btn1{     text-decoration: none;background:#e54048; height:22px; border:none; font-size:14px; color:#fff; cursor:pointer; -moz-border-radius:5px;    -webkit-border-radius:5px; border-radius:5px; padding:0 15px; font-family: Microsoft Yahei, Arial, Verdana, sans-serif }

</style>
</head>
<body>
 <%@ include file="../../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="order" />
     <%@ include file="../member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item"><a href="/member/">我的夺宝</a><span class="w-crumbs-split">&gt;</span></li>
        <li class="w-crumbs-item w-crumbs-active">我的订单</li>
       </ul>
        <div class="m-user-duobao">

         <!-- begin right main  -->
         <div class="m-user-comm-wraper">
          <div class="m-user-duobao">
           <div class="m-user-comm-cont m-user-duobaoOther" id="pro-view-10" module-id="module-8" module-launched="true">
             <div class="m-user-comm-title">
              <div class="m-user-comm-navLandscape">
               <a class="i-item <c:if test="${empty param.status }">i-item-active</c:if>" href="/member/order.html">
                所有订单
                <span pro="tagNum_join"></span>
               </a>
               <span class="i-sptln">|</span>
               <a class="i-item <c:if test="${ param.status ==1}">i-item-active</c:if>" data-name="multi" href="/member/order.html?status=1">
                待发货
                <span class="txt-impt" pro="tagNum_multi"></span>
               </a>
               <span class="i-sptln">|</span>
               <a class="i-item <c:if test="${ param.status ==2}">i-item-active</c:if>" data-name="willReveal" href="/member/order.html?status=2">
                待收货
                <span class="txt-impt" pro="tagNum_willReveal"></span>
               </a>
               <span class="i-sptln">|</span>
               <a class="i-item <c:if test="${ param.status ==3}">i-item-active</c:if>" data-name="periodRevealed" href="/member/order.html?status=3">
                完成
                <span class="txt-impt" pro="tagNum_periodRevealed"></span>
               </a>
              </div>
             </div>
           <c:choose>
          <c:when test="${page.totalItems==0 }">
           <div class="m-win m-win-myself" module-id="module-3" module-launched="true">
            <div>
             <div class="m-user-comm-empty">
              <b class="ico ico-face-sad"></b>
              <div class="i-desc">您还没有此记录哦~</div>
              <div class="i-opt">
               <a href="/" class="w-button w-button-main w-button-xl">马上试试运气</a>
              </div>
             </div>
            </div>
           </div>
          </c:when>
          <c:otherwise>
             <div pro="container">
             <div class="listCont" data-name="join" style="display: block;">
              <div id="pro-view-17">
               <table class="w-table m-user-comm-table" pro="listHead">
                <thead>
                 <tr>
                  <th class="col-info">订单信息</th>
                  <th class="col-info">商品信息</th>
                  <th class="col-info">收货信息</th> 
                  <th class="col-opt">操作</th>
                 </tr>
                </thead>
               </table>
               <div pro="duobaoList" class="duobaoList">
                <c:forEach items="${page.result }" var="item">
                 <table class="m-user-comm-table" id="pro-view-18">
                  <tbody>
                   <tr>
                    <td class="col-info">
                    订单号：${item.orderId }<Br/>
                    下单时间：<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td class="col-info">
                     <div class="w-goods w-goods-l w-goods-hasLeftPic">
                      <div class="w-goods-pic">
                       <a target="_blank" href="/product/${item.ygProduct.id}.html">
                        <img src="${item.ygProduct.logoPath }" alt="${item.ygProduct.name}" width="74" height="74">
                       </a>
                      </div>
                      <p class="w-goods-title f-txtabb" style="    padding-top: 15px;">
                       <a title="${item.ygProduct.name}" target="_blank" href="/product/${item.ygProduct.id}.html">(第${item.ygProduct.period }期) ${item.ygProduct.name}</a>
                      </p>
                      <p class="w-goods-price">金额：￥${item.ygProduct.totalNum } </p> 
                     </div>
                    </td>
                    <td class="col-info">
                    ${item.address.provinceName } ${item.address.cityName } ${item.address.areaInfo }&nbsp;&nbsp;${item.address.trueName }&nbsp;&nbsp;${item.address.mobile }
                      <c:if test="${not empty item.expNo }">
                        <br/>
                        顺丰快递单号: 2222<br/>
                       <a class="color02" href="${item.expressCom.url}?mscomnu=${item.expNo}" target="_blank">查看物流</a>
                       </c:if>
                     </td>
                    <td class="col-opt"> 
                      <span class="color01">${item.statusName }</span>
                      <c:if test="${item.status == 2}">
                      <br/><a href="javascript:;" onclick="accept_confirm('您确认已经收到该商品？',${item.id})" class="hy-btn2">确认收货</a>
                      </c:if>
                      <c:if test="${(item.status == 2 || item.status == 3) && empty item.shareId}">
                      <br/>
                         <a href="/member/share/pre_save/${item.id}"   class="hy-btn2">晒单</a>
                      </c:if>
                      <c:if test="${not empty item.shareId}">
                      <br/>
                         <a href="/share/${item.shareId}.html"   class="hy-btn2">查看晒单</a>
                      </c:if>
                    </td>
                   </tr>
                  </tbody>
                 </table>
                </c:forEach>
               </div>
                <div style="width:100%;text-align:center">
                 <%@ include file="../../pub/commons/page.jsp"%>
                <script type="text/javascript">
                 $(".w-pager button").click(function() {
                   window.location="/member/win.html?pageNo="+$(this).attr("pageNo");
                 });
                </script>
                </div>
              </div>
             </div>
            </div>            
            </c:otherwise>
            </c:choose>
           </div>
          </div>
         </div>
          <!-- end right main -->
        </div>
      </div>
       <div class="m-user-frame-clear"></div>
    </div>
   </div>
  </div>
  </div>
      <%@ include file="../../pub/index/index_foot.jsp"%>
       <script type="text/javascript">
              function viewBuyRecord(userId,ygProductId){
            	    jQuery.post("/member/viewBuyRecord.html", {"ygProductId" : ygProductId}, function(data) {
                        $(document.body).append(data);
                        var wnd = $(window), doc = $(document);
                        var left = doc.scrollLeft();
                        var top = doc.scrollTop();
                        var dialog=$("#db-view-dialog");
                        left += (wnd.width() - dialog.width())/2;
                        top = (wnd.height() -dialog.height())/2;                    
                        dialog.css("top",top);
                        dialog.css("left",left); 
                        
                        //查看号码窗口关闭
                        $('.w-msgbox-close').click(function(){
                            $('#db-view-mask,#db-view-dialog').remove();
                        });
                    });
              }
              function accept_confirm(title,id){
            	  if(confirm(title)){
            		// 异步请求
      				$.ajax({
      					type : "GET",
      					cache : false,
      					dataType : "json",
      					url : "/member/order/sureAccept.json?orderId=" +id,
      					success : function(data) {
      						 window.location= window.location.href;
      					}
      				});
            	  }
              }
       </script>
    </div>
</body>
</html>
