<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>我的积分_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
</head>
<body>
 <%@ include file="../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="jifen" />
     <%@ include file="member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item"><a href="/member/">我的夺宝</a><span class="w-crumbs-split">&gt;</span></li>
        <li class="w-crumbs-item w-crumbs-active">我的积分</li>
       </ul>
        <div class="m-user-duobao">
         <%@ include file="member_right_head.jsp"%>
         <c:choose>
         <c:when test="${page.totalItems==0 }">
          <div module="user/win/MyWin" class="m-win m-win-myself" module-id="module-3" module-launched="true">
           <div class="m-user-comm-wraper" id="pro-view-11">
            <div class="m-user-comm-empty" id="pro-view-12">
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
         <!-- begin right main  -->
         <div class="m-user-comm-wraper">
          <div class="m-user-duobao">
           <div class="m-user-comm-cont m-user-duobaoOther" id="pro-view-10" module-id="module-8" module-launched="true">
            <div class="m-user-comm-title">
             <div class="m-user-comm-navLandscape">
              <span class="title">积分记录</span>
             </div>
            </div>
            <div pro="container">
             <div class="listCont" data-name="join" style="display: block;">
              <div id="pro-view-17">
               <table class="w-table m-user-comm-table" pro="listHead">
                <thead>
                 <tr>
                  <th class="col-info">积分来源</th>
                  <th class="col-joinNum">积分</th>
                  <th class="col-info">时间</th>
                  <th class="col-info">备注</th>
                 </tr>
                </thead>
               </table>
               <div pro="duobaoList" class="duobaoList">
                <c:forEach items="${page.result }" var="item">
                 <table class="m-user-comm-table" id="pro-view-18">
                  <tbody>
                   <tr>
                    <td class="col-info">
                     ${item.scoreTypeName }
                    </td>
                    <td class="col-joinNum">
                     ${item.score }</td>
                    <td class="col-info">
                        <fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td> 
                    <td class="col-info">
                       ${item.remark }
                    </td> 
                   </tr>
                  </tbody>
                 </table>
                </c:forEach>
               </div>
                <div style="width:100%;text-align:center">
                 <%@ include file="../pub/commons/page.jsp"%>
                <script type="text/javascript">
                 $(".w-pager button").click(function() {
                   window.location="/member/jifen.html?pageNo="+$(this).attr("pageNo");
                 });
                </script>
                </div>
              </div>
             </div>
            </div>
           </div>
          </div>
         </div>
          <!-- end right main -->
          </c:otherwise>
          </c:choose>
        </div>
      </div>
       <div class="m-user-frame-clear"></div>
    </div>
   </div>
  </div>
  </div>
      <%@ include file="../pub/index/index_foot.jsp"%>
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
       </script>
    </div>
</body>
</html>
