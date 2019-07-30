<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<div class="m-user-duobaoRecord">
 <div class="detail">
  <div class="goods">
   <div class="pic">
    <a title="" href="/product/${item.ygProduct.id}.html"><img src="${item.ygProduct.logoPath }" width="64" height="48" alt=""></a>
   </div>
   <div class="info info-simple">
    <div class="title">
     <a title="${item.ygProduct.name}" href="/product/${item.ygProduct.id}.html"><span class="txt-impt">(第${item.period }期) </span>${item.ygProduct.name}</a>
    </div>
    <div class="opt" style="width: 60px; height: 20px;">
     <a href="/product/${item.ygProduct.id}.html" class="w-button w-button-s"><span>跟买</span></a>
    </div>
   </div>
  </div>
  <div class="summary">
   本期奖品该用户总共参与了${item.buyNum }人次，拥有${item.buyNum }个云购号码 <a href="/user/${userId }" class="js-back back">&lt;&lt;返回列表</a>
  </div>
  <div id="dvContainer">
   <table class="w-table">
    <thead>
     <tr>
      <th class="col1">云购时间</th>
      <th class="col2">参与人次</th>
      <th class="col3">云购号码</th>
     </tr>
    </thead>
    <tbody>
     <tr>
      <td class="col1" valign="top"><fmt:formatDate value="${item.buyDate}" pattern="yyyy-MM-dd HH:mm:ss.S" /></td>
      <td class="col2" valign="top">${item.buyNum }人次</td>
      <td class="col3" valign="top">
       <ul class="codeList">
        <c:forEach items="${fn:split(item.buyNos, ',')}" var="buyNo" varStatus="v">
         <li class="item ">${buyNo }</li>
        </c:forEach>
       </ul>
      </td>
     </tr>
    </tbody>
   </table>
  </div>
  <div class="summary summary-bottom">
   <a href="/user/${userId }" class="js-back back">&lt;&lt;返回列表</a>
  </div>
 </div>
</div>