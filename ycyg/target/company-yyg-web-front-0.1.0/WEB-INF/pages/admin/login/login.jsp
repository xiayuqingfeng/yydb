<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" media="screen" href="${mimeBase }/styles/useradmin/login.css">
<style>
*+p,*+hr,*+ul,*+ol,*+dl,*+blockquote,*+pre,*+address,*+fieldset,*+figure
	{
	margin-top: 0;
}

p,hr,ul,ol,dl,blockquote,pre,address,fieldset,figure {
	margin: 0;
}

html,body {
	margin: 0px;
	height: 100%;
}

body {
	min-height: 100%;
	height: 100%;
}

.wz-footer {
	position: absolute;
	font-size: 10pt;
	height: 60px;
	padding: 20px;
	bottom: 0;
	width: 100%;
	border-top: 1px solid #eee;
}
</style>
</head>
<body style="background-color: #f3f3f3; font-size: 1.5rem;">
    <div class="admin-wrapper" style="padding-bottom: 80px; background-color: #f3f3f3;">
        <header data-am-widget="header" class="am-header am-header-default am-header-fixed" style="background-color: #ff6600;">
            <div class="am-header-left am-header-nav">
                <a class="am-topbar-brand" href="/">
                    ${comName }管理后台
                </a>
            </div>
            <div class="am-header-right am-header-nav">
                <a href="###" data-am-widget="navbar">
                    <li data-am-navbar-qrcode style="display: block">
                        <i class="am-icon-qrcode">二维码登录</i>
                    </li>
                </a>
            </div>
        </header>
        <br>
        <div class="am-show-lg-only">
            <br>
        </div>
        <div class="am-container" style="margin-bottom: 60px;">
            <div class="am-u-md-6 am-hide-sm animated bounceInLeft">
                <img src="${mimeBase }/images/login_bg.png" align="right" style="padding-right: 60px;">
            </div>
            <div class="am-u-lg-5 am-u-md-6 am-u-sm-12 am-u-end animated bounceIn">
                <div class="am-panel am-panel-default " style="border-radius: 8px;">
                    <div class="am-panel-bd">
                        <label style="font: 24px 微软雅黑; color: #198764;">管理员登陆</label>
                        <form action="/admin/login/check" method="POST" class="form" onsubmit="return checkForm()">
                            <c:if test="${isCheckSuccess==false }">
                                <p class="error wz-flash-info">
                                    <span class="label label-danger">请输入正确的管理员名及密码!</span>
                                </p>
                            </c:if>
                            <fieldset>
                                <div class="form-group error">
                                    <label for="username">管理员名</label>
                                    <input type="text" id="username" name="username" value="" placeholder="管理员名" class="form-control" autocomplete="off" />
                                    <%-- 
                                    <c:if test="${isCheckSuccess==false }">
                                        <p class="help-inline text-info"></p>
                                        <p class="label label-danger wz-flash-info">您还可以尝试登录3次!</p>
                                    </c:if>
                                    --%>
                                </div>
                                <div class="form-group ">
                                    <label for="password">密码</label>
                                    <input type="password" id="password" name="password" placeholder="密码" class="form-control" />
                                    <p class="help-inline text-info"></p>
                                </div>
                                <div class="form-actions">
                                    <input type="submit" class="am-btn hl-btn-green am-btn-block" value="登录" style="background-color: #ff6600; border-radius: 4px;">
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 页脚 -->
        <footer data-am-widget="footer" class="am-footer am-footer-default" style="background-color: #f3f3f3; bottom: 0;">
            <div class="am-footer-miscs ">
<!--                 <p>CopyRight &copy; 2014-2015 福建价值连诚网络科技有限公司版权所有</p> -->
<!--                 <p>闽ICP备16014896号-2</p> -->
            </div>
        </footer>
    </div>
    <script src="${vendorsBase}/jquery.min.js" type="text/javascript"></script>
    <script src="${vendorsBase}/bootstrap/bootstrap.min.js" type="text/javascript"></script>
    <script src="http://cdn.amazeui.org/amazeui/2.3.0/js/amazeui.min.js" type="text/javascript"></script>
    <script type="text/javascript">
					//提交校验
					$(function() {
						if ('zhaozhiqiang') {
							$('#password').focus();
						} else {
							$('#username').focus();
						}
					});
					function checkForm() {
						if (!$("#username").val()) {
							alert("请填写登陆管理员名");
							$('#username').focus();
							return false;
						} else if (!$("#password").val()) {
							alert("请填写登陆密码");
							$('#password').focus();
							return false;
						}
						return true;
					}
				</script>
    <script type="text/javascript">
					//定时关闭
					if (flashTimeOut) {
						clearTimeout(flashTimeOut);
					}
					var flashTimeOut = setTimeout(function() {
						$(".wz-flash-info").fadeOut();
					}, 5 * 1000);
				</script>
</body>
</html>