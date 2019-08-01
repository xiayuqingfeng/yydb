<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
 <!-- begin foot -->
 <div class="container">
   <ul class="f-promise" style="height:65px">
    <li class="li1">
     <a href="/help/bztx.html">
      <em></em>
      <span>100%公平公正</span>
      <br />
      参与过程公开透明，用户可随时查看
     </a>
    </li>
    <li class="li2">
     <a href="/help/bztx.html">
      <em></em>
      <span>100%正品保证</span>
      <br />
      精心挑选优质商家，100%品牌正品
     </a>
    </li>
    <li class="li3" style="height:62px">
     <a href="/help/bztx.html">
      <em></em>
      <span>全国免运费</span>
      <br />
      全场商品全国包邮<br />(港澳台以及偏远地区除外)
     </a>
    </li>
   </ul>
   <div class="foot">
    <ul class="foot1 box-sizing">
     <li>
      <big>
       <a href="javascript:;">新手指南</a>
      </big>
      <p>
       <a href="${base}/help/what.html">了解${comName }</a>
       <br />
       <a href="/help/agreement.html">服务协议</a>
       <br />
       <a href="/help/question.html">常见问题</a>
       <br />
       <a href="/help/score.html">会员福分</a>
      </p>
     </li>
     <li>
      <big>
       <a href="javascript:;">云购保障</a>
      </big>
      <p>
       <a href="/help/bztx.html">保障体系</a>
       <br />
       <a href="/help/zpbz.html">正品保障</a>
       <br />
       <a href="/help/pay.html">安全支付</a>
       <br />
      </p>
     </li>
     <li>
      <big>
       <a href="javascript:;">商品配送</a>
      </big>
      <p>
       <a href="/help/fee.html">商品配送</a>
       <br />
       <a href="/help/check.html">商品验货与签收</a>
       <br />
       <a href="/help/wsd.html">长时间未收到商品</a>
       <br />
      </p>
     </li>
     <li>
      <big>
       <a href="javascript:;">关于云购</a>
      </big>
      <p>
       <a href="/help/aboutus.html">关于我们</a>
       <br />
       <a href="/help/connect.html">联系我们</a>
       <br />
      </p>
     </li>
    </ul>
    <dl class="foot2">
     <dt>
      <em>
            <img src="${ygBase}/style/images/qrcode2.jpg" width="120" />
      </em>
      <p>
       <span>官方微信</span>
      </p>
     </dt>
     <dd>
      <h2>${serviceTel }</h2>
      <small>
       周一到周日 8:00-18:00
       <br />
       (全国免费热线)
      </small>
      <strong>服务器时间</strong>
      <big id="clock">载入中...</big>
      <script type="text/javascript">
                    tick();
                    function showLocale(obj){
                        var str;
                        var hh = obj.getHours();
                        if(hh<10) hh = '0' + hh;
                        var mm = obj.getMinutes();
                        if(mm<10) mm = '0' + mm;
                        var ss = obj.getSeconds();
                        if(ss<10) ss = '0' + ss;
                        str = "<b>"+hh + "</b><span>:</span><b>" + mm + "</b><span>:</span><b>" + ss + "</b>";
                        return(str);
                    }
                    function tick(){
                        var today = new Date();
                        document.getElementById("clock").innerHTML = showLocale(today);
                        window.setTimeout("tick()", 1000);
                    }
                </script>
     </dd>
    </dl>
   </div>
  </div>
  <footer>
  <dl class="container">
   <dt>
    <img src="${ygBase}/style/theme_01/css/images/foot-2.png">
     <img src="${ygBase}/style/theme_01/css/images/foot-3.png">
      <img src="${ygBase}/style/theme_01/css/images/foot-4.png">
       <a target="_blank" href="http://www.miitbeian.gov.cn"><img src="${ygBase}/style/theme_01/css/images/foot-5.png"></a>
   </dt>
   <dd>Copyright©2016 ALL RIGHTS RESERVED. ICP经营许可证：<a target="_blank" href="http://www.miitbeian.gov.cn" style="color:#818181">闽ICP备16018459号-2</a>

    <p>
    ${comName }平台由郑州中原视界网络科技有限公司研发打造 全国统一热线:${comTel }

    </p>
   </dd>
  </dl>
  </footer>
  <ul class="bodyright box-sizing">
   <li onclick="location.href='/cart/'">
    <em class="cartNum">0</em>
    <p>购物车</p>
   </li>
   <li class="br-3">
    <em></em>
    <p>
     <img src="${ygBase}/style/images/qrcode2.jpg" width="120"/>
     <span>官方微信</span>
    </p>
   </li>
   <li class="br-6">
    <em></em>
    <p>返回顶部</p>
   </li>
  </ul>
 <!-- end foot -->
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 