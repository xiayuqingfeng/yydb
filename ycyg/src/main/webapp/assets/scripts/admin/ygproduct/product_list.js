/** 文章分类对象 */
var ProductObject = {
	init : function() {
		// 初始化应用表格
		this._init_app_table();
		// 删除分类按钮事件
		this._reg_delete_btn();
		// 分类下拉框事件
		this._reg_select_org_event();
		// 搜索按钮事件
		this._reg_search_btn_event();
	},
	// 搜索按钮事件
	_reg_search_btn_event : function() {
		$("#pf-btn-search").click(function() {
			$("#pf-table-product").bootstrapTable("refresh", {
				url : "/admin/ygproduct/search.json?cateId=" + $("#cateId").val() + "&searchText=" + encodeURIComponent($("#searchbox").val()),
				pageNumber : 1
			});
		});
	},
	// 分类下拉框事件
	_reg_select_org_event : function() {
		$("#cateId").change(function() {
			$("#pf-btn-search").click();
		});
	},
	
	// 删除分类按钮事件
	_reg_delete_btn : function() {
		$(document).on("click", ".pf-btn-delete-product", function() {
			var $tr = $(this).closest("tr");
			showConfirmDialog(function() {
				$.ajax({
					type : "GET",
					cache : false,
					data : {
						id : $tr.data("data").id
					},
					dataType : "json",
					url : "/admin/ygproduct/delete.json",
					success : function(data) {
						if (data != undefined && data.isSuccess == true) {
							// 刷新
							$tr.remove();
						} else {
							showAlertDialog(data.message);
						}
					}
				});
			}, '确定删除?');
		});
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-product").bootstrapTable(
				{
					method : 'get',
					url : "/admin/ygproduct/search.json",
					cache : false,
					// height : 400,
					sidePagination : 'server', // client or server
					queryParamsType : 'pageSize',
					search : false,
					striped : true,
					clickToSelect : true,
					pagination : true,
					pageSize : 20,
					pageList : [ 10, 20, 50, 100, 200 ],
					paginationDetail : true,
					paginationHAlign : 'right', // right, left
					showColumns : false,
					showRefresh : false,
					minimumCountColumns : 2,
					checkboxHeader : true,
					ct : true,
					onLoadSuccess : function() {
					},
					columns : [
							{
								field : 'id',
								title : '#id',
								align : 'center',
								valign : 'middle',
								visible : true,
								sortable : false
							
							},
							{
								field : 'name',
								title : '商品名称',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'origPrice',
								title : '原价格',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'singlePrice',
								title : '单笔价格',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'totalNum',
								title : '总份数',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'leaveNum',
								title : '剩余份数',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'period',
								title : '当前期数',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									return "第"+value+"期";
								}
							},
							{
								field : 'statusName',
								title : '状态',
								align : 'center',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'recommend',
								title : '是否推荐',
								align : 'center',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									return value?"是":"否";
								}
							},
							{
								field : 'oprate',
								title : '操作',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									var html='';
									if(row.status!=4){
										html+='<a href="/product/' + row.id
										+ '.html" target="_blank" class="btn btn-primary btn-xs">查看商品</a>';
									}
									html+='&nbsp;<a href="/admin/ygproduct/buyRecord/' + row.id
									+ '.html" class="btn btn-primary btn-xs">购买记录</a>';
									//已结束
									if(row.status!=0){
										return html;
									}
									html+='&nbsp;<a href="/admin/ygproduct/edit/' + row.id
									+ '" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-pencil"></i>编辑</a>';
									html+='&nbsp;<a class="btn btn-danger btn-xs pf-btn-delete-product"><i class="glyphicon glyphicon-trash"></i>删除</a>'
									return html;
								}
							
							} ]
				});
	}
}
ProductObject.init();