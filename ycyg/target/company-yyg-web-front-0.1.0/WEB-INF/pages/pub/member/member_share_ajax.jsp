<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<c:forEach items="${page.result }" var="share">
<div class="item">
 <div class="pic">
  <table>
   <tbody>
    <tr>
     <td valign="middle" align="center">
        <a href="/share/${share.id}.html" target="_blank" title="${share.title}">
        <img alt="${share.title}" src="${share.photoList[0]}"  width="270"/>
       </a>
     </td>
    </tr>
   </tbody>
  </table>
 </div>
 <div class="name">
    <a href="/product/${share.ygProduct.id }.html" target="_blank">
   (第${share.ygProduct.period }期) ${share.ygProduct.name } 
   </a>
 </div>
 <div class="code">
  幸运号码：
  <strong class="txt-impt">${share.ygProduct.winNo }</strong>
 </div>
 <div class="post">
  <div class="title">
   <a href="/share/${share.id}.html" target="_blank">
    <strong>${share.title }</strong>
   </a>
  </div>
  <div class="time"><fmt:formatDate value="${share.createTime }" pattern="MM-dd HH:mm"/></div>
  <div class="abbr">
   <p>${share.content}</p>
  </div>
 </div>
</div>
</c:forEach>