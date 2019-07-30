<%@ page language="java" pageEncoding="UTF-8" import="cn.com.yyg.base.em.YgProductStatusEnum"%>
<%
	//进行中
	request.setAttribute("ing", YgProductStatusEnum.ING.getValue());

	//开奖倒计时
	request.setAttribute("pre", YgProductStatusEnum.PRE.getValue());
	//计算中
	request.setAttribute("cal", YgProductStatusEnum.DO.getValue());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${product.name }</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}">
<script src="${ygBase}/style/jquery.W3CI.js" type="text/javascript"></script>
<script src="${ygBase}/style/jquery.ajaxContent.pack.js" type="text/javascript"></script>
</head>
<body>
 <%@ include file="../index/index_head.jsp"%>
 <input type="hidden" value="${product.id }" id="ygProductId"></input>
 <div id="breadCrumb">
  <div class="breadCrumb-txt">
   <a href="/">首页</a>
   <font class='st'> > </font>
    <a href='/product/list/${cate.id}-0-1-1.html' style=''>${cate.name }</a>
   <font class='st'> > </font>
   <em>${product.name }</em>
  </div>
 </div>
 <div class="g-body">
  <div class="m-detail">
   <div class="g-wrap g-body-hd f-clear add-css" style="overflow: visible;">
    <div class="fn-clear yhp-wq" style="  <c:if test="${fn:length(period)>29}">height:98px;</c:if>overflow: hidden;">
     <c:if test="${fn:length(period)>29}">
      <div class="period_Open">
       <a href="javascript:void(0);">展开 &gt;&gt;</a>
      </div>
     </c:if>
     <ul>
      <c:forEach items="${periods }" var="period" varStatus="status">
       <li <c:if test="${period.period ==product.period}">class="dq"</c:if>>
        <a <c:if test="${period.status ==ing}">class="color01"</c:if> href="/product/${period.id}.html">
         第${period.period }期
         <c:if test="${period.status ==ing}">
          <img src="${ygBase}/style/images/ing.gif" />
         </c:if>
        </a>
       </li>
      </c:forEach>
     </ul>
    </div>
    <div class="g-main">
     <div class="g-main-l m-detail-show">
      <div class="w-goods-picShow">
       <div class="w-goods-picShow-large">
        <c:forEach items="${oProduct.photoList}" var="photo">
         <div  class="v">
          <img src="${photo.photoPath}"  id="buy_img_${product.id}"/>
          <span></span>
         </div>
        </c:forEach>
       </div>
       <div class="w-goods-picShow-thumbnail">
        <ul class="w-goods-picShow-thumbnail-list">
         <c:forEach items="${oProduct.photoList}" var="photo" varStatus="status">
          <li class="<c:if test="${status.count==1}">selected</c:if> v">
           <i class="ico ico-arrow ico-arrow-red ico-arrow-red-up"></i>
           <img src="${photo.photoPath}" width="64" height="48">
           <span></span>
          </li>
         </c:forEach>
        </ul>
       </div>
      </div>
      <div class="w-shareTo">
       <!-- JiaThis Button BEGIN -->
       <div class="jiathis_style_24x24">
        <a class="jiathis_button_qzone"></a>
        <a class="jiathis_button_tqq"></a>
        <a class="jiathis_button_tsina"></a>
        <a class="jiathis_button_cqq"></a>
        <a class="jiathis_button_weixin"></a>
        <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
       </div>
       <!-- JiaThis Button END -->
      </div>
     </div>
     <script>jQuery(".w-goods-picShow").slide( { titCell:".w-goods-picShow-thumbnail-list  li",mainCell:".w-goods-picShow-large",titOnClassName:"selected" } );</script>
     <div class="g-main-m m-detail-main">
      <div class="m-detail-main-title">
       <h3 title="${product.name }">${product.name }<span style="color: red;" title="${product.title }">${product.title }</span>
       </h3>
      </div>
      <div class="w-goods-xxl m-detail-main-wrap m-detail-main-ing">
       <p class="m-detail-main-price">
        <span style="color: black; font-size: 18px;">一元云购&nbsp;&nbsp;第${product.period }期</span>
        <span style="color: #999; font-size: 14px;">&nbsp;&nbsp;每满总需人次，即抽取1人获得该商品</span>
       </p>
       <div class="w-progressBar" title="<fmt:formatNumber value="${product.usedNum/product.totalNum*100}" pattern="0.0#"/>%">
        <p class="w-progressBar-wrap">
         <span class="w-progressBar-bar" style="width: <fmt:formatNumber value="${product.usedNum/product.totalNum*100}" pattern="0.0#"/>%;"></span>
        </p>
        <ul class="w-progressBar-txt f-clear">
         <li class="w-progressBar-txt-l">
          <p>
           <b class="color02">${product.usedNum}</b>
          </p>
          <p>已参与人次</p>
         </li>
         <li class="w-progressBar-txt-l" style="width: 30%; text-align: center;">
          <p>
           <b>${product.totalNum}</b>
          </p>
          <p>总需人次</p>
         </li>
         <li class="w-progressBar-txt-r">
          <p>
           <b class="color04">${product.leaveNum}</b>
          </p>
          <p>剩余人次</p>
         </li>
        </ul>
       </div>
       <p class="m-detail-main-progress">
        已完成
        <b style="color: #2db06e">
         <fmt:formatNumber value="${product.usedNum/product.totalNum*100}" pattern="0.0#" />
        </b>
        %，赶快去参加吧
       </p>
       <div class="m-detail-main-count">
        <span>参与人次：</span>
        <div class="m-detail-main-count-number">
         <div id="pro-view-2" class="w-number w-number-inline">
          <input class="w-number-input" id="qty_${product.id}" data-max="${product.leaveNum}" style="color: black" value="10">
          <a class="w-number-btn w-number-btn-plus" data-pro="plus" href="javascript:void(0);">+</a>
          <a class="w-number-btn w-number-btn-minus" data-pro="minus" href="javascript:void(0);">-</a>
         </div>
        </div>
        <span class="m-detail-main-buyHint">
         <i class="ico ico-arrow ico-arrow-grayBubbleArr"></i>
         <c:choose>
          <c:when test="${product.singlePrice==1}">
           <b>加大参与人次，云购在望！</b>
          </c:when>
          <c:otherwise>
           <b>1人次=${product.singlePrice}夺宝币</b>
          </c:otherwise>
         </c:choose>
        </span>
       </div>
       <div class="m-detail-main-buyGoods">
       <c:choose>
       <c:when test="${product.leaveNum<=0}">
        <a  class="w-button w-button-xl w-button-main" href="javascript:void(0)"  style="width: 105px;background: #dddddd !important;">已抢光</a>
       </c:when>
       <c:otherwise>
        <a id="quickBuy" class="w-button w-button-xl w-button-main" href="javascript:void(0)" onclick="addToCart(${product.id},'buy')" style="width: 105px">立即云购</a>
        <a id="addToCart" class="w-button w-button-xl" href="javascript:void(0)" onclick="addToCart(${product.id},'',this)" style="width: 112px">
         <i class="ico ico-miniCart"></i>
         加入购物车
        </a>
       </c:otherwise>
       </c:choose>
       </div>
      </div>
      <div class="m-detail-main-state">
       <ul class="f-clear">
        <li>
         <i class="ico ico-state ico-state-1"></i>
         <a href="/help/bztx.html" target="_blank">公平公正</a>
        </li>
        <li>
         <i class="ico ico-state ico-state-2"></i>
         <a href="/help/bztx.html" target="_blank">正品保证</a>
        </li>
        <li>
         <i class="ico ico-state ico-state-3"></i>
         <a href="/help/bztx.html" target="_blank">权益保障</a>
        </li>
       </ul>
      </div>
      <div class="fn-clear add-tab">
       <ul class="add-tab-tt fn-clear">
        <li class="dq">我的云购记录</li>
        <li class="f60" style="width: 305px;">什么是一元云购？</li>
       </ul>
       <div class="add-tab-box">
        <div class="add-nr01" style="display: block;">
         <c:choose>
          <c:when test="${empty user.id }">
           <div class="add-dl">
            <p>看不到？是不是没登录或是没注册？ 登录后看看</p>
            <a href="${base }/login" class="add-dla">登录</a>
            <a href="${base }/regist" class="add-zca">注册</a>
           </div>
          </c:when>
          <c:otherwise>
           <div class="m-detail-main-winner-codes" style="margin: 0px; padding: 0px; background: #fff; border: 0px;">
            <c:choose>
             <c:when test="${myTotalBuyNums==0}">
              <div class="empty">
               <p class="status-empty">
                <i class="littleU littleU-cry"></i>
                &nbsp;&nbsp;您还没有参与本次云购哦
               </p>
              </div>
             </c:when>
             <c:otherwise>
              <table cellpadding="0" cellspacing="0">
               <tbody>
                <tr>
                 <td colspan="2" style="padding: 15px 0;">
                  <p style="margin-bottom: 5px; color: #3c3c3c">
                   您本期总共参与了
                   <b class="txt-red">${myTotalBuyNums }</b>
                   人次
                  </p>
                 </td>
                </tr>
                <tr>
                 <th style="line-height: 3em;">您的号码:</th>
                 <td class="m-detail-main-codes-list" style="line-height: 3em;">
                  <c:forEach items="${mBuyNos}" var="myBuyNo">
                         <span>${myBuyNo }</span> 
                  </c:forEach> 
                  <c:if test="${myTotalBuyNums>9 }">
                  ……
                  <p>
                   <a class="m-detail-main-codes-viewWinnerCodesBtn" href="javascript:void(0)">您的所有号码&gt;&gt;</a>
                  </p>
                  </c:if>
                 </td>
                </tr>
               </tbody>
              </table>
              <div id="pro-view-8" class="w-msgbox m-detail-codesDetail" style="z-index: 99999; top: 331.5px; left: 418.5px; display: none;">
               <a data-pro="close" href="javascript:void(0);" class="w-msgbox-close">×</a>
               <div class="w-msgbox-hd" data-pro="header"></div>
               <div class="w-msgbox-bd" data-pro="entry">
                <div class="m-detail-codesDetail-bd">
                 <h3>
                  您本期总共参与了
                  <span class="txt-red">
                   <b class="txt-red">${myTotalBuyNums }</b>
                   </span>
                  人次
                 </h3>
                 <div class="m-detail-codesDetail-wrap">
                  <c:forEach items="${myBuyRecords}" var="myBuyRecord">
                  <dl class="m-detail-codesDetail-list f-clear">
                   <dt><fmt:formatDate value="${myBuyRecord.buyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" /></dt>
                   <c:forEach items="${fn:split(myBuyRecord.buyNos, ',')}" var="buyNo">
                   <dd>${buyNo }</dd>
                   </c:forEach>
                  </dl>
                  </c:forEach>
                 </div>
                </div>
               </div>
              </div>
              <script type="text/javascript">
                 //查看号码窗口关闭
                 $('.w-msgbox-close').click(function(){
                     $('#pro-view-8,#pro-view-15').hide();
                 });
                 //查看号码
                 $('.m-detail-main-codes-viewWinnerCodesBtn').click(function(){
                     var wnd = $(window), doc = $(document);
                     var left = doc.scrollLeft();
                     var top = doc.scrollTop();
                 
                     left += (wnd.width() - $(".w-msgbox").width())/2;
                     top = (wnd.height() - $(".w-msgbox").height())/2;
                 
                     $('.w-msgbox').css("top",top);
                     $('.w-msgbox').css("left",left);
                     $('#pro-view-8,#pro-view-15').show();
                 })
                 </script>
             </c:otherwise>
            </c:choose>
           </div>
          </c:otherwise>
         </c:choose>
        </div>
        <div class="add-nr01" style="display: none;">
         <div class="add-txt1">
          <p style="text-indent: 21.0000pt; text-align: justify; vertical-align:; background: #FFFFFF;">
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">一元云购，就是指</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">每件商品被平分成若干“等份”，每份</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">1元，同时获得对应一个号码。当一件商品所有等份即相应号码被分配完成后，</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">根据预先制定的规则计算出一个幸运号码，持有该号码的</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">会员</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">，直接获得该</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">商品</span>
           <span style="font-size: 10.5pt; font-family: 微软雅黑;">。</span>
          </p>
          <a href="/help" target="_blank" class="add-color1">查看详情&gt;&gt;</a>
         </div>
        </div>
       </div>
      </div>
     </div>
     <link rel="stylesheet" href="${ygBase}/style/css/addcss.css">
     <div class="clear"></div>
    </div>
    <div class="g-side">
     <div class="m-detail-period">
      <c:choose>
       <c:when test="${product.period==1 }">
        <img src="${ygBase}/style/images/xye.png" width="255">
       </c:when>
       <c:otherwise>
        <div class="m-detail-period-title">
         <h4>上一期获得者</h4>
        </div>
        <div class="m-detail-period-title-ft"></div>
        <div class="m-detail-period-main" style="padding-bottom: 0px;">
         <div class="m-detail-period-main-wrap">
          <div class="m-detail-period-newest">
           <div style="padding: 10px;">
            <div style="text-align: center; padding-bottom: 5px;">
             <a href="/user/${perYgProduct.winUserId}" target="_blank">
              <img width="150" height="150" src="${perYgProduct.winUserLogoPath}" alt="">
             </a>
            </div>
            <div>
             <p>
              恭喜
              <span style="font-size: 12px;">
               <a style="color: #E54048;" href="/user/${perYgProduct.winUserId}" target="_blank">${perYgProduct.winUserNickName }</a>
               (北京市)
              </span>
             </p>
             <p>
              揭晓时间：
              <fmt:formatDate value="${perYgProduct.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
             </p>
             <p>
              云购时间：
              <fmt:formatDate value="${perYgProduct.winUserBuyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
             </p>
             <p>
              幸运云购码：
              <strong class="add-color1">${perYgProduct.winNo}</strong>
             </p>
            </div>
           </div>
          </div>
         </div>
        </div>
       </c:otherwise>
      </c:choose>
      <div class="m-detail-period-main" id="qishu"></div>
     </div>
    </div>
   </div>
   <a name="tab"></a>
   <div class="g-wrap g-body-bd f-clear">
    <div class="w-tabs w-tabs-main m-detail-mainTab">
     <div class="w-tabs-tab">
      <div id="introTab" class="w-tabs-tab-item w-tabs-tab-item-selected">商品详情</div>
      <div id="recordTab" class="w-tabs-tab-item">所有参与记录</div>
      <div id="shareTab" class="w-tabs-tab-item">晒单</div>
     </div>
     <div class="w-tabs-panel">
      <div id="resultPanel" class="w-tabs-panel-item" style="display: block;text-align: center;">${oProduct.content}</div>
      <div id="recordPanel" class="w-tabs-panel-item m-detail-mainTab-record" style="display: none;"></div>
      <div id="sharePanel" class="w-tabs-panel-item m-detail-mainTab-share" style="display: none;"></div>
     </div>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_foot.jsp"%> 
<script type="text/javascript">
function loadBuyRecord(pageNo){
	jQuery("#recordPanel").load("/product/${product.id}/buyRecord?pageNo="+pageNo);
}
function loadShareRecord(pageNo){
	jQuery("#sharePanel").load("/product/${product.id}/shareRecord?pageNo="+pageNo);
}
function loadPeriodPage(pageNo){
	jQuery("#qishu").load("/product/${product.id}/periodPage?pageNo="+pageNo);
}
</script>
 <script type="text/javascript" src="${mimeBase}/scripts/pub/product/product_detail_ing.js"></script>
 <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
</body>
</html>
