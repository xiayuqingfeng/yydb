<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
 <%@ include file="../commons/commons_resource_head.jsp"%>
 <title>${comName}</title>
 <meta charset="utf-8">
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=2,user-scalable=no">
   <link rel="stylesheet" href="${wapBase}/style/mobile/css/common.css">
    <link rel="stylesheet" href="${wapBase}/style/mobile/css/font/iconfont.css">
     <link href="${wapBase}/style/mobile/css/lottery.css" rel="stylesheet">
      <script src="${wapBase}/style/jquery-1.8.3.min.js"></script>
      <script type="text/javascript" src="${wapBase}/style/layer/layer.min.js"></script>
      <script type="text/javascript" src="${wapBase}/style/common.js"></script>
</head>
<body>
 <header id="header2">
 <h1>计算结果</h1>
 <div class="back">
  <a class="ap-icon" href="javascript:history.back();"></a>
 </div>
 <div class="menu">
  <a class="icon-home ap-icon" href="/"></a>
 </div>
 </header>
 <div id="content" class="container detail">
  <section id="calResult" class="z-minheight">
  <div class="infoResult">
   <dl>
    截止该奖品最后云购时间【
    <fmt:formatDate value="${product.lastUserBuyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
    】
    <em>最后50条全站参与记录</em>
   </dl>
   <ul class="result2">
    <c:forEach items="${buyRecords}" var="buyRecord" end="49">
     <li><span><fmt:formatDate value="${buyRecord.buyDate }" pattern="yyyy-MM-dd" />
       <dd>
        <fmt:formatDate value="${buyRecord.buyDate }" pattern="HH:mm:ss.SSS" />
       </dd> </span> <span><fmt:formatDate value="${buyRecord.buyDate }" pattern="HHmmssSSS" /></span> <span>${buyRecord.buyUserNickName }</span>
      <p>
       <b class="z-arrow"></b>
      </p></li>
    </c:forEach>
   </ul>
  </div>
  <div class="infoCount">
   <div class="infoCount2">
    <ul>
     <li style="border: 0;">取以上数值结果得：</li>
     <li>1、求和：${buyDateTotals } <em>(上面50条参与记录的时间取值相加)</em>
     </li>
     <li>
      <p>
       2、取余：${buyDateTotals } <em>(50条时间记录之和)+
       <c:forEach begin="0" end="${fn:length(product.llscNo.toString())-1}" var="index">
         ${product.llscNo.toString().charAt(index)}
        </c:forEach>(${product.llscPeriodNo}期时时彩开奖结果)
       </em> <br>%${product.totalNum} <em>(本商品总需参与人次)</em>=
       <c:set var="yushu" value="${((buyDateTotals+product.llscNo)%product.totalNum).toString()}" /> 
       <c:forEach begin="0" end="${fn:length(yushu)-1}"  var="index">
         ${yushu.charAt(index)}
         </c:forEach>
       <em>(余数)</em>
      </p>
     </li>
     <li>3、计算结果：${yushu}<em>(余数)</em>+10000001= <span><c:set var="winno" value="${(yushu+10000001).toString()}" /> 
     <c:forEach begin="0" end="${fn:length(winno)-1}" var="index">${winno.charAt(index)} </c:forEach></span>
         <p></p>
     </li>
    </ul>
   </div>
  </div>
  </section>
 </div>
 <!--  公共底部 -->
 <c:set var="footType" value="product" />
 <%@ include file="../index/index_foot.jsp"%>
 <!--  公共底部 -->
 <script src="${wapBase}/style/mobile/js/jquery-pageload.js"></script>
 <script src="${wapBase}/style/mobile/js/jquery.more.js"></script>
</body>
</html>