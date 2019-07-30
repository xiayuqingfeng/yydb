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
                <small>公共配置</small>
            </div>
            <div class="admin-content-info admin-content-padding">
                <div class="am-container am-fl">
                    <div class="am-u-lg-9" style="padding-left: 0; padding-right: 0;">
                        <div class="wz-panel">
                            <form action="/admin/config/save/${config.id}" method="POST" id="pf-form-member">
                                <fieldset> 
                                    <div class="form-group">
                                        <label for="initPwd">初始密码</label>
                                        <input type="text" id="initPwd" name="initPwd" maxlength="20" style="display: inline-block; width: 100px"  value="${config.dataMap['initPwd']}" class="form-control" autocomplete="off">
                                      
                                    </div>  
                                    <div class="form-group">
                                        <label for="hotSearch">热门搜索</label>
                                        <input type="text" id="hotSearch" name="hotSearch" maxlength="100" style="display: inline-block; width: 400px"  value="${config.dataMap['hotSearch']}" class="form-control" autocomplete="off">
                                      
                                    </div>  
                                    <div class="form-group">
                                        <label for="linkRemark">邀请好友备注</label>
                                        <input type="text" id="linkRemark" name="linkRemark" maxlength="100" style="display: inline-block; width: 400px"  value="${config.dataMap['linkRemark']}" class="form-control" autocomplete="off">
                                       
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