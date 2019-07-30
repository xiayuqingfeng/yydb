<%--大转盘参与用户页面 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<style>
.red {
	color: red;
}
</style>
</head>
<body>
 <%--顶部 --%>
 <%@ include file="../index/index_top.jsp"%>
 <div class="admin-wrapper">
  <%--左边菜单 --%>
  <%@ include file="../index/index_left.jsp"%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">幸运大转盘</strong> / <small>数据统计</small>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-6 am-u-md-3">
     <a class="am-btn hl-btn-green  am-btn-xs" href="/admin/whd/bigwheel#4001">返回</a>
    </div>
    <div class="am-u-sm-6 am-u-md-9 am-u-lg-5">
     <div class="am-fr">
      <form action="/activity/bigwheel/data/93" method="GET" class="am-form-inline">
       <div>
        显示内容 <select data-am-selected="" name="w" autocomplete="off" id="select">
         <option value="0">全部记录</option>
         <option value="1">中奖记录</option>
        </select>
       </div>
      </form>
     </div>
    </div>
   </div>
   <div class="am-g">
    <div class="col-sm-12">
     <table class="am-table am-table-striped am-table-hover table-main" id="pf-table">
      <thead>
       <tr>
        <th>姓名</th>
        <th>手机</th>
        <th>地址</th>
        <th>是否中奖</th>
        <th>奖项</th>
        <th>抽奖时间</th>
       </tr>
      </thead>
      <tbody>
      </tbody>
     </table>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script type="text/javascript">
		var bigWheelId = "${bigWheelId}";
		var JoinUserObject = {
			init : function() {
				//初始化表格
				this._init_table();
				//显示内容 ，全部记录 ，中奖记录；下拉选择事件
				this._reg_select_change_event();
			},
			//显示内容 ，全部记录 ，中奖记录；下拉选择事件
			_reg_select_change_event : function() {
				//参与次数
				$("#select").on("change", function() {
					if (this.value && this.value == 1) {
						$("#pf-table").bootstrapTable("refresh", {
							url : "/admin/whd/bigwheel/" + bigWheelId + "/joinuser/all.json?isWinning= true"
						});
					} else {
						$("#pf-table").bootstrapTable("refresh", {
							url : "/admin/whd/bigwheel/" + bigWheelId + "/joinuser/all.json"
						});
					}
				});
			},
			//初始化表格
			_init_table : function() {
				$("#pf-table").bootstrapTable({
					method : 'get',
					url : "/admin/whd/bigwheel/" + bigWheelId + "/joinuser/all.json",
					cache : false,
					// height : 400,
					sidePagination : 'server', // client or server
					silentSort : false,
					queryParamsType : 'pageSize',
					// search : true,
					striped : true,
					clickToSelect : true,
					pagination : false,
					pageSize : 200,
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
					columns : [ {
						field : 'id',
						title : '#id',
						align : 'center',
						valign : 'middle',
						visible : false,
						sortable : false

					}, {
						field : 'name',
						title : '姓名',
						align : 'center',
						valign : 'middle',
						visible : true,
						sortable : false
					}, {
						field : 'tel',
						title : '手机',
						align : 'center',
						valign : 'middle',
						visible : true,
						sortable : false
					}, {
						field : 'address',
						title : '地址',
						align : 'center',
						valign : 'middle',
						visible : true,
						sortable : false
					}, {
						field : 'winning',
						title : '是否中奖',
						align : 'center',
						valign : 'middle',
						visible : true,
						sortable : false,
						formatter : function(value, row, index) {
							if (value == true) {
								return "<span class='red'>是</span>";
							} else {
								return "否";
							}
						}
					}, {
						field : 'prizeName',
						title : '奖项',
						align : 'center',
						valign : 'middle',
						visible : true,
						sortable : false,
						formatter : function(value, row, index) {
							if (row.winning == true) {
								return "<span class='red'>" + value + "</span>";
							} else {
								return value;
							}
						}
					}, {
						field : 'createTime',
						title : '抽奖时间',
						align : 'center',
						valign : 'middle',
						visible : true,
						sortable : false
					} ]
				});
			}

		}
		JoinUserObject.init();
	</script>
</body>
</html>