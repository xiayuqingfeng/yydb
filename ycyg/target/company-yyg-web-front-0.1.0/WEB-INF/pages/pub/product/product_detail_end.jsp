<%@ page language="java" pageEncoding="UTF-8" import="cn.com.yyg.base.em.YgProductStatusEnum"%><%//进行中request.setAttribute("ing", YgProductStatusEnum.ING.getValue());//开奖倒计时request.setAttribute("pre", YgProductStatusEnum.PRE.getValue());//计算中request.setAttribute("cal", YgProductStatusEnum.DO.getValue());%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>${product.name }</title><%@ include file="../commons/commons_resource_head.jsp"%><meta name="keywords" content="" /><meta name="description" content="" /></head><body> <%@ include file="../index/index_head.jsp"%> <link rel="stylesheet" href="${ygBase}/style/css/yiyuanbao.css?verson=${version}"> <script src="${ygBase}/style/jquery.W3CI.js" type="text/javascript"></script> <script src="${ygBase}/style/jquery.ajaxContent.pack.js" type="text/javascript"></script> <div id="breadCrumb">  <div class="breadCrumb-txt">   <a href="/">首页</a>   <font class='st'> > </font>     <a href='/product/list/${cate.id}-0-1-1.html' style=''>${cate.name }</a>   <font class='st'> > </font>   <em>${product.name }</em>  </div> </div> <div class="g-body">  <div class="m-detail">   <div class="g-wrap g-body-hd f-clear add-css" style="overflow: visible;">    <div class="fn-clear yhp-wq">     <ul>      <c:forEach items="${periods }" var="period" varStatus="status">       <li <c:if test="${period.period ==product.period}">class="dq"</c:if>>        <a <c:if test="${period.status ==ing}">class="color01"</c:if> href="/product/${period.id}.html">         第${period.period }期         <c:if test="${period.status ==ing}">          <img src="${ygBase}/style/images/ing.gif" />         </c:if>        </a>       </li>      </c:forEach>     </ul>    </div>    <div class="g-main">     <div class="g-main-l m-detail-show">      <div class="w-goods-picShow">       <div class="w-goods-picShow-large">        <c:forEach items="${oProduct.photoList}" var="photo">         <div  class="v">          <img src="${photo.photoPath}" />          <span></span>         </div>        </c:forEach>       </div>       <div class="w-goods-picShow-thumbnail">        <ul class="w-goods-picShow-thumbnail-list">         <c:forEach items="${oProduct.photoList}" var="photo" varStatus="status">          <li class="<c:if test="${status.count==1}">selected</c:if> v">           <i class="ico ico-arrow ico-arrow-red ico-arrow-red-up"></i>           <img src="${photo.photoPath}" width="64" height="48">           <span></span>          </li>         </c:forEach>        </ul>       </div>      </div>      <div class="w-shareTo">       <!-- JiaThis Button BEGIN -->       <div class="jiathis_style_24x24">        <a class="jiathis_button_qzone"></a>        <a class="jiathis_button_tqq"></a>        <a class="jiathis_button_tsina"></a>        <a class="jiathis_button_cqq"></a>        <a class="jiathis_button_weixin"></a>        <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>       </div>       <!-- JiaThis Button END -->      </div>     </div>     <script>jQuery(".w-goods-picShow").slide( { titCell:".w-goods-picShow-thumbnail-list  li",mainCell:".w-goods-picShow-large",titOnClassName:"selected" } );</script>     <div class="g-main-m m-detail-main">      <div class="m-detail-main-title">       <h3 title="${product.name }">${product.name }<span style="color: red;" title="${product.title }">${product.title }</span>       </h3>      </div>      <div class="jiexiao" style="height:293px">       <div class="m-detail-main-end-luckyCode">        <c:forEach items="${winNoChars}" var="n">         <b class="num num-${n}">${n}</b>        </c:forEach>       </div>       <dl class="zjxx">        <dt>         <img src="${product.winUserLogoPath}" height="105" width="105" />        </dt>        <dd>         恭喜         <span style="font-size: 12px;">          <a href="/user/${product.winUserId}">           <i>${product.winUserNickName }</i>          </a>          (${product.winUserIpAddr })         </span>         获得了本奖品         <br />         用户ID：         <em>${product.winUserId} (ID为用户唯一不变标识)</em>         <br />         揭晓时间：         <fmt:formatDate value="${product.publishDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />         <br />         云购时间：         <fmt:formatDate value="${product.winUserBuyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />         <br />         本期参与：         <b class="txt-red">${myTotalBuyNums }</b>         人次        </dd>       </dl>      </div>      <div class="fn-clear add-tab" style="margin-bottom: 10px;">       <ul class="add-tab-tt fn-clear">        <li class="dq">中奖者云购记录</li>        <li class="f60" style="width: 305px;">什么是一元云购？</li>       </ul>       <div class="add-tab-box">        <div class="add-nr01" style="display: block;">         <c:choose>          <c:when test="${empty user.id }">           <div class="add-dl">            <p>看不到？是不是没登录或是没注册？ 登录后看看</p>            <a href="${base }/login" class="add-dla">登录</a>            <a href="${base }/regist" class="add-zca">注册</a>           </div>          </c:when>          <c:otherwise>           <div class="m-detail-main-winner-codes" style="margin: 0px; padding: 0px; background: #fff; border: 0px;">            <c:choose>             <c:when test="${myTotalBuyNums==0}">              <div class="empty">               <p class="status-empty">                <i class="littleU littleU-cry"></i>                &nbsp;&nbsp;您还没有参与本次云购哦               </p>              </div>             </c:when>             <c:otherwise>              <table cellpadding="0" cellspacing="0">               <tbody>                <tr>                 <td colspan="2" style="padding: 15px 0;">                  <p style="margin-bottom: 5px; color: #3c3c3c">                   您本期总共参与了                   <b class="txt-red">${myTotalBuyNums }</b>                   人次                  </p>                 </td>                </tr>                <tr>                 <th style="line-height: 3em;">您的号码:</th>                 <td class="m-detail-main-codes-list" style="line-height: 3em;">                  <c:forEach items="${mBuyNos}" var="myBuyNo">                   <span <c:if test="${myBuyNo  eq product.winNo}">style="color:#E54048; font-weight:bold;"</c:if>>${myBuyNo }</span>                  </c:forEach>                  <c:if test="${myTotalBuyNums>9 }">                  ……                  <p>                    <a class="m-detail-main-codes-viewWinnerCodesBtn" href="javascript:void(0)">您的所有号码&gt;&gt;</a>                   </p>                  </c:if>                 </td>                </tr>               </tbody>              </table>              <div id="pro-view-8" class="w-msgbox m-detail-codesDetail" style="z-index: 99999; top: 331.5px; left: 418.5px; display: none;">               <a data-pro="close" href="javascript:void(0);" class="w-msgbox-close">×</a>               <div class="w-msgbox-hd" data-pro="header"></div>               <div class="w-msgbox-bd" data-pro="entry">                <div class="m-detail-codesDetail-bd">                 <h3>                  您本期总共参与了                  <span class="txt-red">                   <b class="txt-red">${myTotalBuyNums }</b>                  </span>                  人次                 </h3>                 <div class="m-detail-codesDetail-wrap">                  <c:forEach items="${myBuyRecords}" var="myBuyRecord">                   <dl class="m-detail-codesDetail-list f-clear">                    <dt>                     <fmt:formatDate value="${myBuyRecord.buyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />                    </dt>                    <c:forEach items="${fn:split(myBuyRecord.buyNos, ',')}" var="buyNo">                     <dd <c:if test="${buyNo eq product.winNo}">class="txt-red selected"</c:if>>${buyNo }</dd>                    </c:forEach>                   </dl>                  </c:forEach>                 </div>                </div>               </div>              </div>              <script type="text/javascript">                 //查看号码窗口关闭                 $('.w-msgbox-close').click(function(){                     $('#pro-view-8,#pro-view-15').hide();                 });                 //查看号码                 $('.m-detail-main-codes-viewWinnerCodesBtn').click(function(){                     var wnd = $(window), doc = $(document);                     var left = doc.scrollLeft();                     var top = doc.scrollTop();                                      left += (wnd.width() - $(".w-msgbox").width())/2;                     top = (wnd.height() - $(".w-msgbox").height())/2;                                      $('.w-msgbox').css("top",top);                     $('.w-msgbox').css("left",left);                     $('#pro-view-8,#pro-view-15').show();                 })                 </script>             </c:otherwise>            </c:choose>           </div>          </c:otherwise>         </c:choose>        </div>        <div class="add-nr01" style="display: none;">         <div class="add-txt1">          <p style="text-indent: 21.0000pt; text-align: justify; vertical-align:; background: #FFFFFF;">           <span style="font-size: 10.5pt; font-family: 微软雅黑;">一元云购，就是指</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">每件商品被平分成若干“等份”，每份</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">1元，同时获得对应一个号码。当一件商品所有等份即相应号码被分配完成后，</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">根据预先制定的规则计算出一个幸运号码，持有该号码的</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">会员</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">，直接获得该</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">商品</span>           <span style="font-size: 10.5pt; font-family: 微软雅黑;">。</span>          </p>          <a href="/help" target="_blank" class="add-color1">查看详情&gt;&gt;</a>         </div>        </div>       </div>      </div>     </div>     <link rel="stylesheet" href="${ygBase}/style/css/addcss.css">     <div class="clear"></div>     <div style="width: 870px;">      <div class="m-detail-main-rule">       <ul class="txt">        <li>         <span class="title">计算公式</span>         <div class="formula">          <div class="box red-box">           <b class="txt-red">${product.winNo }</b>           <br>           <b class="txt-red" style="font-size: 12px">本期幸运号码</b>          </div>          <div class="operator">=</div>          <div class="box gray-box">           <b class="txt-red">${buyDateTotals}</b>           <br>           50个时间求和           <div class="more-box">            <i class="ico ico-arrow ico-arrow-yellow"></i>            <div class="yellow-box">             奖品的最后一个号码分配完毕，公示该分配时间点前本站全部奖品的             <span class="txt-red">最后50个参与时间</span>             ，并求和。            </div>           </div>          </div>          <div class="operator" title="相加" style="display:none">+</div>          <div class="box gray-box" style="display:none">           <b class="txt-red">${product.llscNo}</b>           <br>           “老时时彩”开奖号码           <div class="more-box">            <i class="ico ico-arrow ico-arrow-yellow"></i>            <div class="yellow-box f-breakword">             取最近一期“老时时彩” (第             <span class="txt-red">${product.llscPeriodNo}</span>             期) 开奖结果。             <a href="${ygBase}/../caipiao.163.com/award/ssc/20160817.html" target="_blank">开奖查询</a>            </div>           </div>          </div>          <div class="operator" title="取余">%</div>          <div class="box">           <b class="txt-red">${product.totalNum}</b>           <br>           该奖品总需人次          </div>          <div class="operator" title="相加">+</div>          <div class="box">           <b class="txt-red">10000001</b>           <br>           原始数          </div>         </div>        </li>       </ul>      </div>     </div>     <script type="text/javascript">    $(function(){        $('.gray-box').hover(function(){            $(this).addClass("box-hover yellow-box");        },function(){            $(this).removeClass("box-hover yellow-box");        });    });</script>    </div>    <div class="g-side">     <div class="m-detail-period">      <div class="m-detail-period-title">       <h4>最新一期</h4>      </div>      <div class="m-detail-period-title-ft"></div>      <div class="m-detail-period-main" style="padding-bottom: 0px;">       <div class="m-detail-period-main-wrap">        <div class="m-detail-period-newest">         <h6>第${newYgProduct.period }期</h6>         <div class="w-goods w-goods-ing m-detail-period-ing">          <div class="w-goods-pic" style="text-align: center; height: 120px;">           <a href="/product/${newYgProduct.id}" title="" target="_blank">            <img src="${newYgProduct.logoPath}">           </a>          </div>          <div class="w-progressBar" title="0.00%">           <p class="w-progressBar-wrap">            <span class="w-progressBar-bar" style="width: 0.00%;"></span>           </p>           <ul class="w-progressBar-txt f-clear">            <li class="w-progressBar-txt-l" style="width: 50%;">             <p>              <b>${newYgProduct.usedNum }</b>              已参与人次             </p>            </li>            <li class="w-progressBar-txt-r" style="width: 50%;">             <p style="padding-left: 0px;">              <b>${newYgProduct.leaveNum }</b>              剩余人次             </p>            </li>           </ul>          </div>          <div class="w-goods-opr">           <a class="w-button w-button-main w-button-l" href="/product/${newYgProduct.id}" style="width: 96px;" target="_blank">立即云购</a>          </div>         </div>        </div>       </div>      </div>      <div class="m-detail-period-main" id="qishu"></div>     </div>    </div>   </div>   <a name="tab"></a>   <div class="g-wrap g-body-bd f-clear">    <div class="w-tabs w-tabs-main m-detail-mainTab">     <div class="w-tabs-tab">      <div id="introTab" class="w-tabs-tab-item w-tabs-tab-item-selected">计算结果</div>      <div id="recordTab" class="w-tabs-tab-item">所有参与记录</div>      <div id="shareTab" class="w-tabs-tab-item">晒单</div>     </div>     <div class="w-tabs-panel">      <div id="resultPanel" class="w-tabs-panel-item" style="display: block;">       <div class="m-detail-mainTab-calcRule" style="height: 112px">        <h4>         <i class="ico ico-text"></i>         <br>         幸运号码         <br>         计算规则        </h4>        <div class="ruleWrap" style="height: 80px;">         <ol class="ruleList">          <li>           <span class="index">1</span>           奖品的最后一个号码分配完毕后，将公示该分配时间点前本站全部奖品的最后50个参与时间；          </li>          <li>           <span class="index">2</span>           将这50个时间的数值进行求和（得出数值A）（每个时间按时、分、秒、毫秒的顺序组合，如20:15:25.362则为201525362）；          </li>          <li style="display:none">           <span class="index">3</span>           为保证公平公正公开，系统还会等待一小段时间，取最近下一期中国福利彩票“老时时彩”的开奖结果（一个五位数值B）；          </li>          <li>           <span class="index">3</span>           数值A除以该奖品总需人次得到的余数           <i class="ico ico-questionMark" data-func="remainder" style="margin-top: -3px;"></i>           + 原始数&nbsp;10000001，得到最终幸运号码，拥有该幸运号码者，直接获得该奖品。          </li>          <li class="txt-red"i style="display:none">           注：最后一个号码分配时间距离“老时时彩”最近下一期开奖大于24小时或无法读取老时时彩下一期开奖结果时，默认“老时时彩”开奖结果为00000。           <a href="http://chart.cp.360.cn/kaijiang/ssccq" target="_blank">了解更多“老时时彩”信息</a>          </li>         </ol>        </div>       </div>       <table cellpadding="0" cellspacing="0" class="m-detail-mainTab-resultList">        <thead>         <tr>          <th class="time" colspan="2">云购时间</th>          <th>会员帐号</th>          <th>奖品名称</th>          <th width="70">参与人次</th>         </tr>        </thead>        <tr class="startRow">         <td colspan="5">          截止该奖品最后云购时间【          <fmt:formatDate value="${product.lastUserBuyDate }" pattern="yyyy-MM-dd HH:mm:ss.SSS" />          】最后50条全站参与记录         </td>        </tr>        <c:forEach items="${buyRecords}" var="buyRecord" end="49">         <tr class="calcRow">          <td class="day">           <fmt:formatDate value="${buyRecord.buyDate }" pattern="yyyy-MM-dd" />          </td>          <td class="time">           <fmt:formatDate value="${buyRecord.buyDate }" pattern="HH:mm:ss.SSS" />           <i class="ico ico-arrow-transfer"></i>           <b class="txt-red">            <fmt:formatDate value="${buyRecord.buyDate }" pattern="HHmmssSSS" />           </b>          </td>          <td class="user">           <div class="f-txtabb">            <a href="${base}/user/${buyRecord.buyUserId }.html" target="_blank" title="">${buyRecord.buyUserNickName }</a>           </div>          </td>          <td class="gname">           <a href="${base}/product/${buyRecord.ygProductId }.html" target="_blank">${buyRecord.productName }</a>          </td>          <td>${buyRecord.buyNum}人次</td>         </tr>        </c:forEach>        <tr class="resultRow">         <td colspan="5">          <h4>           计算结果           <a name="calcResult"></a>          </h4>          <ol>           <li>            <span class="index">1、</span>            求和：${buyDateTotals } (上面50条参与记录的时间取值相加)           </li>           <li style="display:none">            <span class="index">2、</span>            第 ${product.llscPeriodNo}期 “老时时彩”开奖号码：            <c:forEach begin="0" end="${fn:length(product.llscNo.toString())-1}" var="index">             <b class="ball">${product.llscNo.toString().charAt(index)}</b>            </c:forEach>            <a href="http://caipiao.163.com/award/cqssc/${nowDate}.html" style="margin-left: 10px; color: #FFE000; font-family: simsun;" target="_blank">开奖查询&gt;&gt;</a>           </li>           <li>            <span class="index">2、</span>            求余：(${buyDateTotals }     <%--         +            <c:forEach begin="0" end="${fn:length(product.llscNo.toString())-1}" var="index">             <b class="ball">${product.llscNo.toString().charAt(index)}</b>            </c:forEach> --%>            ) % ${product.totalNum} (奖品所需人次) =            <c:set var="yushu" value="${((buyDateTotals+product.llscNo)%product.totalNum).toString()}" />            <c:forEach begin="0" end="${fn:length(yushu)-1}" var="index">             <b class="square">${yushu.charAt(index)}</b>            </c:forEach>            (余数)            <i class="ico ico-questionMark" data-func="remainder"></i>           </li>           <li>            <span class="index">3、</span>            <c:forEach begin="0" end="${fn:length(yushu)-1}" var="index">             <b class="square">${yushu.charAt(index)}</b>            </c:forEach>            (余数) + 10000001 =            <c:set var="winno" value="${(yushu+10000001).toString()}" />            <c:forEach begin="0" end="${fn:length(winno)-1}" var="index">             <b class="square">${winno.charAt(index)}</b>            </c:forEach>           </li>          </ol>          <span class="resultCode">幸运号码：${winno }</span>         </td>        </tr>        <c:forEach items="${buyRecords}" var="buyRecord" begin="50">         <tr class="calcRow">          <td class="day">           <fmt:formatDate value="${buyRecord.buyDate }" pattern="yyyy-MM-dd" />          </td>          <td class="time">           <fmt:formatDate value="${buyRecord.buyDate }" pattern="HH:mm:ss.SSS" />           <i class="ico ico-arrow-transfer"></i>           <b class="txt-red">            <fmt:formatDate value="${buyRecord.buyDate }" pattern="HHmmssSSS" />           </b>          </td>          <td class="user">           <div class="f-txtabb">            <a href="${base}/user/${buyRecord.buyUserId }.html" target="_blank" title="">${buyRecord.buyUserNickName }</a>           </div>          </td>          <td class="gname">           <a href="${base}/product/${buyRecord.ygProductId }.html" target="_blank">${buyRecord.productName }</a>          </td>          <td>${buyRecord.buyNum}人次</td>         </tr>        </c:forEach>       </table>      </div>      <div id="recordPanel" class="w-tabs-panel-item m-detail-mainTab-record" style="display: none;"></div>      <div id="sharePanel" class="w-tabs-panel-item m-detail-mainTab-share" style="display: none;"></div>     </div>    </div>   </div>  </div> </div> <%@ include file="../index/index_foot.jsp"%> <script type="text/javascript">function loadBuyRecord(pageNo){	jQuery("#recordPanel").load("/product/${product.id}/buyRecord?pageNo="+pageNo);}function loadShareRecord(pageNo){	jQuery("#sharePanel").load("/product/${product.id}/shareRecord?pageNo="+pageNo);}function loadPeriodPage(pageNo){	jQuery("#qishu").load("/product/${product.id}/periodPage?pageNo="+pageNo);}</script> <script type="text/javascript" src="${mimeBase}/scripts/pub/product/product_detail_end.js"></script> <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script></body></html>