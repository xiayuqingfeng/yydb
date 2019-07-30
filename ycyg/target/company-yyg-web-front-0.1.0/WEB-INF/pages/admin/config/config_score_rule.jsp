<%--修改积分规则配置 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" type="text/css" href="${vendorsBase }/bootstrap-loading/ladda-themeless.min.css?v=${version}">
</head>
<body>
    <%--顶部 --%>
    <%@ include file="../index/index_top.jsp"%>
    <div class="admin-wrapper">
        <%--左边菜单 --%>
        <%@ include file="../index/index_left.jsp"%>
        <div class="admin-content" id="hl-admin-content">
            <div class="admin-content-title admin-content-padding">
                <strong class="am-text-lg am-padding-left-sm">系统配置</strong>
                /
                <small>积分规则配置</small>
            </div>
            <div class="admin-content-info admin-content-padding">
                <div class="am-container am-fl">
                    <div class="am-u-lg-9" style="padding-left: 0; padding-right: 0;">
                        <div class="wz-panel">
                            <form action="/admin/config/saveScoreRule" method="POST" id="pf-form-member">
                                <fieldset> 
                                    <div class="form-group">
                                        <label for="registScore">新会员注册</label>
                                        <input type="text" id="registScore" name="registScore" style="display: inline-block; width: 100px"  value="${scoreRule.registScore}" class="form-control" autocomplete="off">
                                        成为新会员一次多少积分
                                    </div>  
                                    <div class="form-group">
                                        <label for="qianDaoScore">每日签到</label>
                                        <input type="text" id="qianDaoScore" name="qianDaoScore" style="display: inline-block; width: 100px"  value="${scoreRule.qianDaoScore}" class="form-control" autocomplete="off">
                                        签到一次多少积分，一天只算一次,连续签到积分翻盘
                                    </div> 
                                    <div class="form-group">
                                        <label for="linkScore">转发链接</label>
                                        <input type="text" id="linkScore" name="linkScore" style="display: inline-block; width: 100px"  value="${scoreRule.linkScore}" class="form-control" autocomplete="off">
                                        转发到微信或QQ一次多少积分
                                    </div>  
                                    <div class="form-group">
                                        <label for="yaoqingScore">邀请好友</label>
                                        <input type="text" id="yaoqingScore" name="yaoqingScore" style="display: inline-block; width: 100px"  value="${scoreRule.yaoqingScore}" class="form-control" autocomplete="off">
                                        邀请好友注册成功一个多少积分
                                    </div>    
                                    <div class="form-group ">
                                        <label for="shareScore">晒单分享</label>
                                        <input type="text" id="shareScore" name="shareScore" style="display: inline-block; width: 100px"  value="${scoreRule.shareScore}" class="form-control" autocomplete="off">
                                        晒单分享一次多少积分
                                    </div>   
                                    <div class="form-group ">
                                        <label for="buyScore">参与夺宝</label>
                                        <input type="text" id="buyScore" name="buyScore" style="display: inline-block; width: 100px"  value="${scoreRule.buyScore}" class="form-control" autocomplete="off">
                                        购买商品一元多少积分
                                    </div>   
                                    <div class="form-group ">
                                        <label for="bigWheelJoinScore">参与抽奖<br>(大转盘)</label>
                                        <input type="text" id="bigWheelJoinScore" name="bigWheelJoinScore" style="display: inline-block; width: 100px"  value="${scoreRule.bigWheelJoinScore}" class="form-control" autocomplete="off">
                                        参与抽奖一次扣除多少积分
                                    </div>      
                                </fieldset>
                                <div class="form-actions">
                                    <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="pf-btn-save">
                                        <span class="ladda-label">保存</span>
                                        <span class="ladda-spinner"></span>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../index/index_sm_foot.jsp"%>
    <%@ include file="../commons/commons_resource_foot.jsp"%>
    <script src="${vendorsBase}/bootstrap-loading/spin.min.js?v=${version}"></script>
    <script src="${vendorsBase}/bootstrap-loading/ladda.min.js?v=${version}"></script>
    <script type="text/javascript">
     Ladda.bind('#pf-btn-save');
     $("#pf-btn-save").click(function() {
      $("#pf-form-member").ajaxSubmit({
       dataType : "json",
       success : function(data) {
        // 关闭加载中
        Ladda.stopAll();
        if (data != undefined) {
         if (data.isSuccess == true) {
          showPopup("保存成功!");
         } else {
          showAlertDialog(data.message);
         }
        } else {
         showAlertDialog("保存失败!");
        }
       }
      });
     });
    </script>
</body>
</html>