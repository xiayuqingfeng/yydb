<%@ page language="java" pageEncoding="UTF-8"%>
<div class="m-user-frame-colNav">
 <h3>
  <a href="/user/${userInfo.id}.html">Ta的夺宝</a>
 </h3>
 <hr>
  <ul pro="userFrameNav">
   <li>
    <a href="/user/${userInfo.id}/duobao.html" pro="userDuobao"  <c:if test="${member_nav=='duobao' }">style="color: #db3652; font-weight: bold"</c:if>>
     夺宝记录
     <strong pro="userDuobao_num" data-pos="userNav" class="txt-impt"></strong>
    </a>
   </li>
   <li>
    <a href="/user/${userInfo.id}/win.html" pro="userWin"  <c:if test="${member_nav=='win' }">style="color: #db3652; font-weight: bold"</c:if>>
     幸运记录
     <strong pro="userWin_num" data-pos="userNav" class="txt-impt"></strong>
    </a>
   </li> 
   <li>
    <a href="/user/${userInfo.id}/share.html" pro="userShare"  <c:if test="${member_nav=='share' }">style="color: #db3652; font-weight: bold"</c:if>>
     Ta的晒单
     <strong pro="userShare_num" data-pos="userNav" class="txt-impt"></strong>
    </a>
   </li>
  </ul>
</div>