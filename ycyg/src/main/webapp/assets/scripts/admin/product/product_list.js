/** 文章分类对象 */
var ProductObject = {
	init : function() {
		// 初始化应用表格
		this._init_app_table();
		// 删除分类按钮事件
		this._reg_delete_btn();
		// 分类下拉框事件
		this._reg_select_cate_event();
		// 搜索按钮事件
		this._reg_search_btn_event();
	},
	// 搜索按钮事件
	_reg_search_btn_event : function() {
		$("#pf-btn-search").click(function() {
			$("#pf-table-product").bootstrapTable("refresh", {
				url : "/admin/product/search.json?cateId=" + $("#cateId").val() + "&searchText=" + encodeURIComponent($("#searchbox").val()),
				pageNumber : 1
			});
		});
	},
	// 分类下拉框事件
	_reg_select_cate_event : function() {
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
					url : "/admin/product/delete.json",
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
					url : "/admin/product/search.json",
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
								field : 'totalNum',
								title : '总份数',
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
								field : 'period',
								title : '当前期数',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'createTime',
								title : '创建时间',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
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
									var html='&nbsp;'
										+ '<a href="/admin/product/edit/' + row.id
										+ '" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-pencil"></i>编辑</a>&nbsp;'
									return html;
								}
							
							} ]
				});
	}
}
ProductObject.init();