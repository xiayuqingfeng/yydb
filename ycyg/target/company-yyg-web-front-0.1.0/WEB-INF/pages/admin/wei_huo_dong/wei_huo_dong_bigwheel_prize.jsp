<%-- 奖品管理页面 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
</head>
<body>
    <%--顶部 --%>
    <%@ include file="../index/index_top.jsp"%>
    <div class="admin-wrapper">
        <%--左边菜单 --%>
        <%@ include file="../index/index_left.jsp"%>
        <div class="admin-content" id="hl-admin-content">
            <div class="admin-content-title admin-content-padding">
                <strong class="am-text-lg am-padding-left-sm">幸运大转盘</strong>
                /
                <small>奖品管理</small>
            </div>
            <div class="am-cf am-padding-top-sm">
                <div class="am-u-sm-4 am-u-md-6">
                    <div class="am-btn-toolbar am-fl">
                        <div class="am-show-md-up am-padding-left-sm">
                            <a class="am-btn hl-btn-green am-btn-sm" href="/admin/whd/bigwheel/${id }/prize/add">
                                <i class="am-icon-plus"></i>
                                添加奖品
                            </a>
                        </div>
                        <div class="am-show-sm-only">
                            <div class="am-btn-group">
                                <button class="am-btn hl-btn-green am-btn-sm" autocomplete="off">菜单</button>
                                <div class="am-dropdown" data-am-dropdown="">
                                    <button class="am-btn hl-btn-green am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle="" autocomplete="off">
                                        <span class="am-icon-caret-down"></span>
                                    </button>
                                    <ul class="am-dropdown-content">
                                        <li>
                                            <a href="/activity/bigwheel/prize/edit/88">
                                                <i class="am-icon-plus"></i>
                                                添加奖品
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-12" style="padding-bottom: 40px;">
                <table class="am-table am-table-striped am-table-hover table-main" id="pf-table">
                    <thead>
                        <tr>
                            <th>奖项</th>
                            <th>奖品内容</th>
                            <th>奖品数量</th>
                            <th>中奖概率(%)</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <div class="am-cf">
                    <div>
                        <a class="am-btn am-btn-primary am-btn-sm" href="/admin/whd/bigwheel/${id}/edit">上一步</a>
                        <a class="am-btn am-btn-success am-btn-sm" href="/admin/whd/bigwheel/${id}/calibrations">下一步</a>
                        <a class="am-btn am-btn-default am-btn-sm" href="/admin/whd/bigwheel#4001">返回</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../index/index_sm_foot.jsp"%>
    <%@ include file="../commons/commons_resource_foot.jsp"%>
    <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
    <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
    <script type="text/javascript">
					var bigWheelId = "${id}";
					var BigWheelPrizeObject = {
						init : function() {
							//初始化表格
							this._init_table();
						},
						//初始化表格
						_init_table : function() {
							$("#pf-table")
									.bootstrapTable(
											{
												method : 'get',
												url : "/admin/whd/bigwheel/" + bigWheelId + "/prize/all.json",
												cache : false,
												// height : 400,
												sidePagination : 'server', // client or server
												silentSort : false,
												queryParamsType : 'pageSize',
												// search : true,
												striped : true,
												clickToSelect : true,
												pagination : false,
												pageSize : 10,
												pageList : [ 10, 20, 50, 100, 200 ],
												paginationDetail : true,
												paginationHAlign : 'right', // right, left
												showColumns : true,
												showRefresh : true,
												minimumCountColumns : 2,
												checkboxHeader : true,
												
												onLoadSuccess : function() {
													//初始化表格中的下拉菜单
													//初始化表格中的下拉菜单
													setTimeout(function() {
														$(".am-dropdown").dropdown({});
													}, 500);
												},
												columns : [
														{
															field : 'id',
															title : '#id',
															align : 'center',
															valign : 'middle',
															visible : false,
															sortable : false
														
														},
														{
															field : 'prizeName',
															title : '奖项',
															align : 'center',
															valign : 'middle',
															visible : true,
															sortable : false
														},
														{
															field : 'prizeType',
															title : '奖品内容',
															align : 'center',
															valign : 'middle',
															visible : true,
															sortable : false,
															formatter : function(value, row, index) {
																if (value ==0) {
																	return "积分";
																} else {
																	return "实物";
																}
															}
														},
														{
															field : 'prizeContent',
															title : '奖品实物/积分',
															align : 'center',
															valign : 'middle',
															visible : true,
															sortable : false,
															formatter : function(value, row, index) {
																if (value ==0) {
																	return row.prizeScore;
																} else {
																	return row.prizeContent;
																}
															}
														},
														{
															field : 'prizeCount',
															title : '奖品数量',
															align : 'center',
															valign : 'middle',
															visible : true,
															sortable : false,
															formatter : function(value, row, index) {
																if (value == -1) {
																	return "不限";
																} else {
																	return value;
																}
															}
														},
														{
															field : 'prizeRate',
															title : '中奖概率',
															align : 'center',
															valign : 'middle',
															visible : true,
															sortable : false
														},
														
														{
															field : 'oprate',
															title : '操作',
															align : 'left',
															valign : 'middle',
															visible : true,
															sortable : false,
															formatter : function(value, row, index) {
																
																if (row.id == null) {
																	return "";
																} else {
																	
																	var html = '<div class="am-show-md-up">';
																	html = html
																			+ '<a class="am-btn hl-btn-green am-btn-xs" href="/admin/whd/bigwheel/'+bigWheelId+'/prize/'+row.id+'/edit">编辑</a>';
																	html = html + ' <a class="am-btn am-btn-danger am-btn-xs"';
																	html = html + ' href="javascript:showConfirmDialog(function(){ BigWheelPrizeObject.delete_prize(' + row.id
																			+ ')}, \'确定删除? 一旦删除不可恢复，<font color=red>请谨慎！</font>\')">删除</a>';
																	html = html + '</div>';
																	
																	//手机上显示的菜单
																	html = html + ' <div class="am-show-sm-only">';
																	html = html + ' <div class="am-btn-group" style="width: 90px;">';
																	html = html + '<button class="am-btn hl-btn-green am-btn-xs" autocomplete="off">操作</button>';
																	html = html + '<div class="am-dropdown" data-am-dropdown="">';
																	html = html
																			+ ' <button class="am-btn hl-btn-green am-btn-xs am-dropdown-toggle" data-am-dropdown-toggle="" autocomplete="off">';
																	html = html + '<span class="am-icon-caret-down"></span>';
																	html = html + '</button>';
																	html = html + '<ul class="am-dropdown-content">';
																	html = html + ' <li>';
																	html = html + '<a href="/admin/whd/bigwheel/'+bigWheelId+'/prize/'+row.id+'/edit">';
																	html = html + ' <i class="am-icon-edit"></i>';
																	html = html + ' 编辑';
																	html = html + '</a>';
																	html = html + '</li>';
																	html = html + '<li>';
																	html = html + '<a';
																	html = html + ' href="javascript:showConfirmDialog(function(){BigWheelPrizeObject.delete_prize(' + row.id
																			+ ')}, \'确定删除? 一旦删除不可恢复，<font color=red>请谨慎！</font>\')">';
																	html = html + ' <i class="am-icon-minus"></i>';
																	html = html + ' 删除';
																	html = html + ' </a>';
																	html = html + '</li>';
																	html = html + '</ul>';
																	html = html + ' </div>';
																	html = html + '</div>';
																	html = html + '</div>';
																	return html;
																}
															}
														} ]
											});
						},
						//执行删除事件
						delete_prize : function(id) {
							// 异步请求
							$.ajax({
								type : "GET",
								cache : false,
								dataType : "json",
								url : "/admin/whd/bigwheel/" + bigWheelId + "/prize/" + id + "/delete.json",
								success : function(data) {
									if (data != undefined && data.isSuccess == true) {
										// 发送删除请求
										showPopup("删除成功");
										$("#pf-table").bootstrapTable("refresh");
									} else {
										showAlertDialog(data.message, "异常");
									}
								}
							});
						}
					
					}
					BigWheelPrizeObject.init();
				</script>
    <!--  <script type="text/javascript">
					showPopup("保存成功!");
				</script> -->
</body>
</html>