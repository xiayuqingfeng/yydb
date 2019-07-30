<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../commons/commons_resource_head.jsp"%>
<title>${article.title}_${comName }</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<body>
 <c:set var="nav" value="product" />
 <%@ include file="../index/index_head.jsp"%>
 <link rel="stylesheet" href="${ygBase}/style/css/member.css?verson=${version}">
  <div id="breadCrumb">
   <div class="breadCrumb-txt">
    <a href="/">首页</a>
    &gt;
    <a href="/news/${article.id}.html">${article.parentCateName }</a>
    &gt;
    <span class="end">
     <a href="/news/${article.id}.html">${article.title }</a>
    </span>
   </div>
  </div>
  <div id="container">
   <div class="fn-clear mt20 pb20">
    <div class="fn-left hy-l ffbz">
     <h4>系统公告</h4>
     <div class="hy-lnav">
      <ul class="fn-clear">
       <c:forEach items="${articles}" var="t">
       <li <c:if test="${article.id==t.id}">class="dq"</c:if>>
        <a href="${base}/news/${t.id}.html">${t.title }</a>
       </li>
       </c:forEach>
      </ul>
     </div>
    </div>
    <div class="fn-right hy-r">
     <div class="fn-clear hy-tjxh">
      <div class="title">
       <h2>${article.title }</h2>
      </div>
      <div class="hy-box">
       <div class="hy-qalist">
        ${article.content }
       </div>
      </div>
     </div>
    </div>
   </div>
  </div><%@ include file="../index/index_foot.jsp"%>
</body>
</html>