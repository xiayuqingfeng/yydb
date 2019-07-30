<%--修改密码首页 --%>
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
                <strong class="am-text-lg am-padding-left-sm">用户设置</strong>
                /
                <small>基本信息</small>
            </div>
            <div class="admin-content-info admin-content-padding">
                <div class="am-container am-fl">
                    <div class="am-u-lg-9" style="padding-left: 0; padding-right: 0;">
                        <div class="wz-panel">
                            <form action="/admin/setting/save.json" method="POST" id="pf-form-member">
                                <fieldset>
                                    <div class="form-group ">
                                        <label for="email">邮箱</label>
                                        <input type="text" id="email" name="email" value="${user_admin.email }" placeholder="Email" class="form-control" autocomplete="off">
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="fullname">姓名</label>
                                        <input type="text" id="trueName" name="trueName" value="${user_admin.trueName }" placeholder="姓名" class="form-control"
                                            autocomplete="off">
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="phone">手机</label>
                                        <input type="text" id="mobile" name="mobile" value="${user_admin.mobile }" placeholder="手机号" class="form-control" autocomplete="off">
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="oldPassword">旧密码</label>
                                        <input type="password" id="oldPassword" name="oldPassword" placeholder="旧密码" class="form-control" autocomplete="off">
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="inputPassword">密码</label>
                                        <input type="password" id="password" name="password" placeholder="密码" class="form-control" autocomplete="off">
                                        <p class="help-inline text-info">若不更改密码请留空!</p>
                                    </div>
                                    <div id="#passwordStrengthDiv" class="is0"></div>
                                    <div class="form-group ">
                                        <label for="confirmPassword">确认密码</label>
                                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="重复密码" class="form-control" autocomplete="off">
                                        <p class="help-inline text-info"></p>
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