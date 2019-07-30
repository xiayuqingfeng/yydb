<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<div id="content" class="container">
 <nav class="nav">
  <ul class="ui-clr">
   <li><a href="/"><span>首页</span></a></li>
   <li <c:if test="${not empty headType and headType eq 'product' }">class="selected" </c:if>><a href="/product"><span>全部商品</span></a></li>
   <li <c:if test="${not empty headType and headType eq 'zxjx' }">class="selected" </c:if>><a href="/win"><span>最新揭晓</span></a></li>
   <li <c:if test="${not empty headType and headType eq 'share' }">class="selected" </c:if>><a href="/share"><span>晒单</span></a></li>
  </ul>
 </nav>
 <div class="clear"></div>