<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<header class="am-topbar am-topbar-admin am-topbar-fixed-top">
    <h2 class="am-topbar-brand">
        <a href="/admin">${comName }管理后台</a>
        <i class="am-icon-bars am-icom-sm am-hide-sm-only" id="hl-topbar-brand-bars"></i>
    </h2>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm hl-btn-white am-show-sm-only" data-am-collapse="{target: '#doc-topbar-collapse'}" autocomplete="off">
        <span class="am-sr-only">导航切换</span>
        <span class="am-icon-bars"></span>
    </button>
    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <div class="am-topbar-right">
            <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
                <li class="am-dropdown  am-dropdown-flip" data-am-dropdown="">
                    <a class="am-dropdown-toggle" data-am-dropdown-toggle="" href="javascript:;">
                        <span class="am-icon-users"></span>
                        ${user_admin.trueName }
                        <span class="am-icon-caret-down"></span>
                    </a>
                    <ul class="am-dropdown-content">
                        <li>
                            <a href="/admin/setting">
                                <span class="am-icon-cog"></span>
                                设置
                            </a>
                        </li>
                        <li>
                            <a href="javascript:showConfirmDialog(function(){window.location.href='/admin/login/logout'}, '确定退出系统?')">
                                <span class="am-icon-power-off"></span>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</header>