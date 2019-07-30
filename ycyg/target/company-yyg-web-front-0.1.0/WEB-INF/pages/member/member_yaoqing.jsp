<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../pub/commons/commons_resource_head.jsp"%>
<title>我的邀请_${comName}</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member_common.css" />
<link rel="stylesheet" type="text/css" href="${mimeBase}/styles/member/member.css" />
<style type="text/css">
.hy-tjxh {
	border: 1px #ededed solid;
}

.title {
	background: #f7f7f7;
	height: 38px;
	overflow: hidden;
	padding-left: 20px;
	border-bottom: 1px solid #ededed;
}

.title h2 {
	float: left;
	display: inline;
	height: 37px;
	line-height: 37px;
	font-size: 16px;
	border-bottom: 1px #e54048 solid;
	color: #000;
	font-weight: normal;
}

.title span {
	float: right;
	line-height: 37px;
	padding-right: 20px;
	font-size: 14px;
}
/*发送邀请链接*/
.hy-box {
	width: 894px;
	margin: 0 auto;
	padding: 20px 0 20px;
	color: #555;
	font-size: 14px;
	line-height: 1.8;
}

.regMytiv {
	line-height: 2;
	font-size: 16px;
}

.regMytiv textarea {
	width: 400px;
	height: 60px;
	border: 1px solid #ccc;
	font-size: 12px;
	padding: 5px;
}
</style>
</head>
<body>
 <c:set var="nav" value="yaoqin" />
 <%@ include file="../pub/index/index_head.jsp"%>
 <div class="g-body">
  <div class="m-user" id="pro-view-0" module-id="module-1" module-launched="true">
   <div class="g-wrap">
    <div class="m-user-frame-wraper">
     <c:set var="member_nav" value="yq" />
     <%@ include file="member_left.jsp"%>
     <div class="m-user-frame-colMain ">
      <div class="m-user-frame-content" pro="userFrameWraper">
       <ul class="w-crumbs f-clear">
        <li class="w-crumbs-item">当前位置：</li>
        <li class="w-crumbs-item"><a href="/member/">我的夺宝</a> <span class="w-crumbs-split">&gt;</span></li>
        <li class="w-crumbs-item w-crumbs-active">我的邀请</li>
       </ul>
       <div class="m-user-duobao">
        <!-- begin right main  -->
        <div class="m-user-comm-wraper">
         <div class="m-user-duobao">
          <div class="fn-clear hy-tjxh">
           <div class="title">
            <h2>邀请好友注册</h2>
           </div>
           <div class="hy-box" style="padding-bottom: 0;">
            <div class="regMytiv" style="width: 450px; float: left;">
             <p class="p1 color01">复制邀请链接：</p>
             <p class="p2">
              <textarea id="text" onfocus="this.style.borderColor='#777';this.select();" onblur="this.style.borderColor='#ccc'">${sysConfig.dataMap['linkRemark']} 
${base}/regist/${user.id }
              </textarea>
             </p>
             <p class="p5">
              <a class="hy-btn2 copybtn">复制链接</a> <span style="margin-left: 2px; font-size: 12px;">建议您写分享感受，获得最好邀请效果（注册1个得100积分）</span>
             </p>
            </div>
            <div style="float: left; width: 420px;">
             <img src="/wxPay/resQrcode?content=${base}/regist/${user.id }" style="float: left;" width="140">
              <div style="float: left; width: 260px; margin-left: 5px; margin-top: 15px;">
               我的专属二维码，分享给好友， <br> 无论是微信、微博、QQ群、QQ空间……<br> 只要你乐于分享，<br>就有收获，三重好礼等你来拿！ 
              </div>
            </div>
            <div style="clear: both; height: 15px;"></div>
            <script type="text/javascript" src="${ygBase}/style/ZeroClipboard.js"></script>
            <script type="text/javascript">
													$(document).ready(function() {
														$(".copybtn").zclip({
															path : '${ygBase}/style/ZeroClipboard.swf',
															copy : $('#text').html()
														});
													});
												</script>
           </div>
          </div>
          <div class="fn-clear hy-tjxh hy-noborder">
           <div class="title" id="ivt_list">
            <h2>
             已邀请注册<b style="color: #f30; font-weight: bold;">${fn:length(yaoqingUsers) }</b>人
            </h2>
           </div>
           <c:choose>
            <c:when test="${fn:length(yaoqingUsers)==0}">
             <div module="user/win/MyWin" class="m-win m-win-myself" module-id="module-3" module-launched="true">
              <div class="m-user-comm-wraper" id="pro-view-11">
               <div class="m-user-comm-empty" style="padding: 20px 0 58px;">
                <div class="i-desc">您还没有邀请好友注册哦~</div>
                <div class="i-opt">
                 <a href="javascript:void()" class="w-button w-button-main w-button-xl copybtn">马上复制链接发出邀请</a>
                </div>
               </div>
              </div>
             </div>
            </c:when>
            <c:otherwise>
             <div class="m-user-comm-cont m-user-duobaoOther" id="pro-view-10" module-id="module-8" module-launched="true">
              <div pro="container">
               <div class="listCont" data-name="join" style="display: block;">
                <div>
                 <table class="w-table m-user-comm-table" style="width: 800px">
                  <thead>
                   <tr>
                    <th style="width: 60px">会员ID</th>
                    <th style="width: 150px">注册会员</th>
                    <th style="width: 150px">手机号</th>
                    <th style="width: 150px">注册时间</th>
                   </tr>
                  </thead>
                 </table>
                 <div pro="duobaoList" class="duobaoList">
                  <c:forEach items="${yaoqingUsers }" var="yq">
                   <table class="m-user-comm-table" style="width: 800px">
                    <tbody>
                     <tr>
                      <td style="width: 60px">${yq.id }</td>
                      <td style="width: 150px">${yq.nickName }</td>
                      <td style="width: 150px">${yq.mobile }</td>
                      <td style="width: 150px"><fmt:formatDate value="${yq.createTime }" pattern="yyyy-MM-dd" /></td>
                     </tr>
                    </tbody>
                   </table>
                  </c:forEach>
                 </div>
                 <div style="width: 100%; text-align: center">
                  <%@ include file="../pub/commons/page.jsp"%>
                  <script type="text/javascript">
																			$(".w-pager button").click(function() {
																				window.location = "/member/win.html?pageNo=" + $(this).attr("pageNo");
																			});
																		</script>
                 </div>
                </div>
               </div>
              </div>
             </div>
            </c:otherwise>
           </c:choose>
          </div>
         </div>
        </div>
        <!-- end right main -->
       </div>
      </div>
      <div class="m-user-frame-clear"></div>
     </div>
    </div>
   </div>
  </div>
  <%@ include file="../pub/index/index_foot.jsp"%>
  <script type="text/javascript">
			function viewBuyRecord(userId, ygProductId) {
				jQuery.post("/member/viewBuyRecord.html", {
					"ygProductId" : ygProductId
				}, function(data) {
					$(document.body).append(data);
					var wnd = $(window), doc = $(document);
					var left = doc.scrollLeft();
					var top = doc.scrollTop();
					var dialog = $("#db-view-dialog");
					left += (wnd.width() - dialog.width()) / 2;
					top = (wnd.height() - dialog.height()) / 2;
					dialog.css("top", top);
					dialog.css("left", left);

					//查看号码窗口关闭
					$('.w-msgbox-close').click(function() {
						$('#db-view-mask,#db-view-dialog').remove();
					});
				});
			}
		</script>
 </div>
</body>
</html>
