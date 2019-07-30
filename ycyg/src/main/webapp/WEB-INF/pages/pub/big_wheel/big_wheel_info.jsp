
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<dl class="cols-1">
 <dt></dt>
 <dd>
  <table cellpadding="0" cellspacing="0">
   <thead>
    <tr>
     <td>用户名</td>
     <td>奖品</td>
     <td>时间</td>
    </tr>
   </thead>
   <tbody>
    <tr class="od">
     <c:forEach var="item" items="${prizesPage}" varStatus="v">
      <tr <c:if test="${v.count%2!=0}" >class="od"</c:if>>
       <td class="username">${item.name }</td>
       <td>${item.prizeName }</td>
       <td><fmt:formatDate value="${item.createTime}" pattern="MM-dd" /></td>
      </tr>
     </c:forEach>
    <tr>
   </tbody>
  </table>
 </dd>
</dl>
<div class="cols-2">
 <dl class="cols-2-row-1">
  <dt>
   <c:choose>
    <c:when test="${empty user.headPhotoPath }">
     <img onclick="location.href='/user/${user.id}'" style="cursor: pointer;" src="${mimeBase}/images/member/nohead.jpeg" height="105" width="105" />
    </c:when>
    <c:otherwise>
     <img onclick="location.href='/user/${user.id}'" style="cursor: pointer;" src="${user.headPhotoPath}" height="105" width="105" />
    </c:otherwise>
   </c:choose>
  </dt>
  <dd>
   <h3 onclick="location.href='/user/${user.id}'" style="cursor: pointer;">${ not empty user.nickName? user.nickName:暂无昵称}</h3>
   <h5 onclick="location.href='/member/jifen/'" style="cursor: pointer;">总积分： ${scores }</h5>
   <h5 style="display: inline-block;">剩余次数：</h5>
   <h5 id="lottery_count" style="display: inline-block;">
    <c:choose>
     <c:when test="${not empty checkPhoneDto}">
                    ${checkPhoneDto.lastTimes }
                    </c:when>
     <c:otherwise>
           0
            </c:otherwise>
    </c:choose>
   </h5>
   <p>一次抽奖需${bigWheelJoinScore }积分</p>
  </dd>
  <div class="clear"></div>
 </dl>
 <dl class="cols-2-row-2">
  <dt>活动规则</dt>
  <dd>${bigWheelInfo.activeRule }</dd>
 </dl>
</div>
<dl class="cols-3">
 <dt></dt>
 <dd>
  <ul>
   <c:forEach var="item" items="${bigWheelPrizePage}" varStatus="v">
    <li class="li-1"><b>${v.index+1 }</b><span>${item.prizeLevelName}：${item.prizeName }</span></li>
   </c:forEach>
  </ul>
 </dd>
</dl>
<div class="clear" style="height: 40px;"></div>