<%@ page language="java" pageEncoding="UTF-8"%>
<div class="m-user-frame-colNav">
 <h3>
  <a href="/member/" <c:if test="${member_nav=='home' }">style="color: #db3652; font-weight: bold"</c:if>>我的夺宝</a>
 </h3>
 <hr>
  <ul pro="userFrameNav">
   <li>
    <a href="/member/duobao.html" pro="userDuobao"  <c:if test="${member_nav=='duobao' }">style="color: #db3652; font-weight: bold"</c:if>>
     夺宝记录
     <strong pro="userDuobao_num" class="txt-impt"></strong>
    </a>
   </li>
   <li>
    <a href="/member/win.html" pro="userWin"  <c:if test="${member_nav=='win' }">style="color: #db3652; font-weight: bold"</c:if>>
     幸运记录
     <strong pro="userWin_num" class="txt-impt"></strong>
    </a>
   </li> 
   <li>
       <hr>
   </li>
    <li>
    <a href="/member/order.html"  <c:if test="${member_nav=='order' }">style="color: #db3652; font-weight: bold"</c:if>>
     我的订单
     <strong class="txt-impt"></strong>
    </a>
   </li> 
   <li>
       <hr>
   </li>
   <li>
    <a href="/member/yq.html"  <c:if test="${member_nav=='yq' }">style="color: #db3652; font-weight: bold"</c:if>>
     我的邀请
     <strong pro="userShare_num" class="txt-impt"></strong>
    </a>
   </li>
      <li>
    <a href="/member/jifen.html"  <c:if test="${member_nav=='jifen' }">style="color: #db3652; font-weight: bold"</c:if>>
     我的积分
     <strong pro="userShare_num" class="txt-impt"></strong>
    </a>
   </li>
   <li>
    <a href="/member/amountDetail.html"  <c:if test="${member_nav=='amountDetail' }">style="color: #db3652; font-weight: bold"</c:if>>
     金额明细
     <strong pro="userShare_num" class="txt-impt"></strong>
    </a>
   </li>
   <li>
    <a href="/member/share.html"  <c:if test="${member_nav=='share' }">style="color: #db3652; font-weight: bold"</c:if>>
     我的晒单
     <strong pro="userShare_num" class="txt-impt"></strong>
    </a>
   </li>
    <li>
       <hr>
   </li>
   <li>
    <a href="/member/baseinfo.html"  <c:if test="${member_nav=='baseinfo' }">style="color: #db3652; font-weight: bold"</c:if>>
    个人资料
     <strong pro="userShare_num" class="txt-impt"></strong>
    </a>
   </li>
   <li>
    <a href="/member/addr.html"  <c:if test="${member_nav=='addr' }">style="color: #db3652; font-weight: bold"</c:if>>
     收货地址
     <strong pro="userShare_num" class="txt-impt"></strong>
    </a>
   </li>
  </ul>
</div>