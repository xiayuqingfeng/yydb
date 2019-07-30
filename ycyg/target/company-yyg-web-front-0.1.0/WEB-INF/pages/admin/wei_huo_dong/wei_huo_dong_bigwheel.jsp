<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<style>
.am-show-md-up>a {
	margin-right: 5px;
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
    <strong class="am-text-lg am-padding-left-sm">幸运大转盘</strong> / <small>Big Wheel</small>
   </div>
   <div class="am-cf am-padding-top-sm">
    <div class="am-u-sm-4 am-u-md-6">
     <div class="am-btn-toolbar am-fl">
      <div class="am-show-md-up am-padding-left-sm">
       <!--        <a class="am-btn hl-btn-green am-btn-sm" href="/admin/whd/bigwheel/add#4001"> <i class="am-icon-plus"></i> 新建活动 -->
       <!--        </a> -->
      </div>
      <div class="am-show-sm-only">
       <div class="am-btn-group">
        <button class="am-btn hl-btn-green am-btn-sm" autocomplete="off">菜单</button>
        <div class="am-dropdown" data-am-dropdown="">
         <button class="am-btn hl-btn-green am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle="" autocomplete="off">
          <span class="am-icon-caret-down"></span>
         </button>
         <ul class="am-dropdown-content">
          <!--           <li><a href="/activity/bigwheel/edit"> <i class="am-icon-plus"></i> 新建 -->
          </a>
          </li>
         </ul>
        </div>
       </div>
      </div>
     </div>
    </div>
   </div>
   <div class="am-u-sm-12">
    <table class="am-table am-table-striped am-table-hover table-main" id="pf-table">
     <thead>
      <tr>
       <th>活动名称</th>
       <th>操作</th>
      </tr>
     </thead>
     <tbody>
     </tbody>
    </table>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/bootstrap-table.js?v=${version}"></script>
 <script src="${vendorsBase}/bootstrap-table-1.9.1/locale/bootstrap-table-zh-CN.js?v=${version}"></script>
 <script type="text/javascript">
		function site_preview(url) {
			if ($.support.leadingWhitespace) {
				alert("不支持在IE浏览器下预览，建议使用谷歌浏览器或者360极速浏览器或者直接在微信上预览。");
				return;
			}
			var left = ($(window).width() - 450) / 2;
			window.open("/wap/bigwheel/" + url, "我的微官网", "height=650,width=450,top=0,left=" + left
					+ ",toolbar=yes,menubar=yes,scrollbars=no, resizable=no,location=no, status=no");
		}
		var BigWheelObject = {
			init : function() {
				//初始化表格
				this._init_table();
			},
			//初始化表格
			_init_table : function() {
				$("#pf-table").bootstrapTable(
						{
							method : 'get',
							url : "/admin/whd/bigwheel/all.json",
							cache : false,
							// height : 400,
							sidePagination : 'server', // client or server
							queryParamsType : 'pageSize',
							// search : true,
							striped : true,
							clickToSelect : true,
							pagination : true,
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
										field : 'title',
										title : '活动名称',
										align : 'center',
										valign : 'middle',
										width: '60%',
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
											var html = '<div class="am-show-md-up">';
											//if (row.status == 0) {
											html = html + '<a class="am-btn hl-btn-green am-btn-xs" href="/admin/whd/bigwheel/'+row.id+'/edit">编辑</a>';
// 											html = html + '<a class="am-btn am-btn-success am-btn-xs" target="blank" _href="/wap/bigwheel/' + row.id
// 													+ '" onclick="site_preview(' + row.id + ')";" " >进入</a>';
											
												html = html + '<a class="am-btn am-btn-success am-btn-xs" href="/admin/whd/bigwheel/'+row.id+'/prize">奖品</a>';
											
											
											
											//}
											html = html + '<a class="am-btn am-btn-danger am-btn-xs" '
													+ 'href="javascript:showConfirmDialog(function(){BigWheelObject.delete_bigwheel(' + row.id
													+ ')}, \'确定删除? 将删除活动及其相关内容，一旦删除不可恢复，<font color=red>请谨慎！</font>\')">删除</a>'
													+ '<a class="am-btn am-btn-primary am-btn-xs" href="/admin/whd/bigwheel/'+ row.id+'/joinuser#4001">统计信息</a></div>';
											//手机显示菜单
											html = html + '<div class="am-show-sm-only">';
											html = html + '<div class="am-btn-group" style="width:90px;">';
											html = html + '<button class="am-btn hl-btn-green am-btn-xs" autocomplete="off">操作</button>';
											html = html + '<div class="am-dropdown" data-am-dropdown="">';
											html = html
													+ '<button class="am-btn hl-btn-green am-btn-xs am-dropdown-toggle" data-am-dropdown-toggle="" autocomplete="off">';
											html = html + '<span class="am-icon-caret-down"></span>';
											html = html + '</button>';
											html = html + '<ul class="am-dropdown-content">';
											//if (row.status == 0) {
											html = html + '<li><a  href="/admin/whd/bigwheel/'+row.id+'/edit"><i class="am-icon-edit"></i> 编辑</a></li>';
											html = html + '<li><a  href="/bigwheel/'+row.id+'"><i class="am-icon-edit"></i> 进入</a></li>';
											//}

											html = html + '<li><a href="javascript:showConfirmDialog(function(){BigWheelObject.delete_bigwheel(' + row.id
													+ ')}, \'确定删除? 将删除活动及其相关内容，一旦删除不可恢复，<font color=red>请谨慎！</font>\')">';
											html = html + '<i class="am-icon-minus"></i> 删除</a></li>			';
											html = html + '<li><a href="/admin/whd/bigwheel/'+ row.id+'/joinuser#4001"><i class="am-icon-trophy"></i> 统计信息</a></li>';
											html = html + '</ul>';
											html = html + '</div>	  ';
											html = html + '</div>';
											html = html + '</div>';
											return html;
										}
									} ]
						});
			},
			//执行删除事件
			delete_bigwheel : function(id) {
				// 异步请求
				$.ajax({
					type : "GET",
					cache : false,
					dataType : "json",
					url : "/admin/whd/bigwheel/" + id + "/delete.json",
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
		BigWheelObject.init();
	</script>
</body>
</html>