
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="admin-content" id="hl-admin-content">
    <style>
.admin-content-list {
	border: 1px solid #e9ecf1;
	margin-top: 0;
	background: #fff;
}

.admin-content-list li {
	border: 1px solid #e9ecf1;
	border-width: 0 1px;
	margin-left: -1px;
}

.admin-content-list li:first-child {
	border-left: none;
}

.admin-content-list li:last-child {
	border-right: none;
}
</style>
    <div class="am-hide-sm">
        <div class="admin-content-title admin-content-padding">
            <strong class="am-text-lg am-padding-left-sm">首页</strong>
            /
            <small>一些常用模块</small>
        </div>
        <div class="admin-content-info admin-content-padding">
            <ul class="am-avg-sm-4 am-margin am-padding am-text-center admin-content-list hidden">
                <li>
                    <a href="#" class="am-text-success">
                        <span class="am-icon-btn am-icon-file-text"></span>
                        <br>
                        新增页面
                        <br>
                        2300
                    </a>
                </li>
                <li>
                    <a href="#" class="am-text-warning">
                        <span class="am-icon-btn am-icon-briefcase"></span>
                        <br>
                        成交订单
                        <br>
                        308
                    </a>
                </li>
                <li>
                    <a href="#" class="am-text-danger">
                        <span class="am-icon-btn am-icon-recycle"></span>
                        <br>
                        昨日访问
                        <br>
                        80082
                    </a>
                </li>
                <li>
                    <a href="#" class="am-text-secondary">
                        <span class="am-icon-btn am-icon-user-md"></span>
                        <br>
                        在线用户
                        <br>
                        3000
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="am-show-sm">
        <div data-am-widget="slider" class="am-slider am-slider-b1 am-no-layout" data-am-slider="{&quot;controlNav&quot;:false}">
            <div class="am-viewport" style="overflow: hidden; position: relative;">
                <ul class="am-slides" style="width: 800%; transition-duration: 0s; transform: translate3d(0px, 0px, 0px);">
                    <li class="clone" aria-hidden="true" style="width: 0px; float: left; display: block;">
                        <img src="${mimeBase }/images/useradmin/slider1_s.png" draggable="false">
                    </li>
                    <%--  
                    <li class="am-active-slide" style="width: 0px; float: left; display: block;">
                        <img src="/assets/images/site/home/slider0_s.jpg" draggable="false">
                    </li> --%>
                </ul>
            </div>
            <ul class="am-direction-nav">
                <li>
                    <a class="am-prev" href="#">Previous</a>
                </li>
                <li>
                    <a class="am-next" href="#">Next</a>
                </li>
            </ul>
        </div>
        <ul data-am-widget="gallery" class="am-gallery am-avg-sm-4 am-gallery-default hl-admin-item am-no-layout">
            <li>
                <div class="am-gallery-item">
                    <a href="/admin/articles?type=1#4001" data="4001">
                        <span class="am-icon-users am-icon-lg"></span>
                        <h3>会员管理</h3>
                    </a>
                </div>
            </li>
            <li class="hidden">
                <div class="am-gallery-item">
                    <a href="/admin/settings">
                        <span class="am-icon-wrench am-icon-lg"></span>
                        <h3>用户设置</h3>
                    </a>
                </div>
            </li>
            <li>
                <div class="am-gallery-item">
                    <a href="/admin/icons#4003" data="4003">
                        <span class="am-icon-area-chart am-icon-lg"></span>
                        <h3>图标库管理</h3>
                    </a>
                </div>
            </li>
            <li>
                <div class="am-gallery-item">
                    <a href="/admin/articles?type=1#4001" data="4001">
                        <span class="am-icon-file-text am-icon-lg"></span>
                        <h3>文库管理</h3>
                    </a>
                </div>
            </li>
            <li class="border-bottom-no">
                <div class="am-gallery-item">
                    <a href="/admin/articles?type=0#4002" data="4002">
                        <span class="am-icon-wechat am-icon-lg"></span>
                        <h3>每日分享管理</h3>
                    </a>
                </div>
            </li>
            <li class="border-bottom-no ">
                <div class="am-gallery-item">
                    <a href="/admin/order?type=1#5001" data="5001"> 
                        <span class="am-icon-tasks am-icon-lg"></span>
                        <h3>订单管理</h3>
                    </a>
                </div>
            </li>
            <li class="border-bottom-no hidden">
                <div class="am-gallery-item">
                    <a href="#">
                        <span class="am-icon-sitemap am-icon-lg"></span>
                        <h3>VIP等级管理</h3>
                    </a>
                </div>
            </li>
            <li class="border-bottom-no hidden">
                <div class="am-gallery-item">
                    <a href="#">
                        <span class="am-icon-shield am-icon-lg"></span>
                        <h3>权限管理</h3>
                    </a>
                </div>
            </li>
            <li class="border-bottom-no hidden">
                <div class="am-gallery-item">
                    <a href="/vip">
                        <span class="am-icon-globe am-icon-lg"></span>
                        <h3>进入微站</h3>
                    </a>
                </div>
            </li>
        </ul>
    </div>
</div>
<%@ include file="index_sm_foot.jsp"%>