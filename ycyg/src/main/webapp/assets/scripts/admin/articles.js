/** 文章分类对象 */
var ArticleObject = {
	init : function() {
		// 初始化应用表格
		this._init_app_table();
		// 删除分类按钮事件
		this._reg_delete_cate_btn();
		// 分类下拉框事件
		this._reg_select_cate_event();
		// 搜索按钮事件
		this._reg_search_btn_event();
	},
	// 搜索按钮事件
	_reg_search_btn_event : function() {
		$("#pf-btn-search").click(function() {
			$("#pf-table-article").bootstrapTable("refresh", {
				url : "/admin/articles/all.json?type=" + type + "&cateId=" + $("#articleCateId").val() + "&searchText=" + encodeURIComponent($("#searchbox").val()),
				pageNumber : 1
			});
		});
	},
	// 分类下拉框事件
	_reg_select_cate_event : function() {
		$("#articleCateId").change(function() {
			$("#pf-table-article").bootstrapTable("refresh", {
				url : "/admin/articles/all.json?type=" + type + "&cateId=" + $("#articleCateId").val() + "&searchText=" + encodeURIComponent($("#searchbox").val())
			});
		});
	},
	
	// 删除分类按钮事件
	_reg_delete_cate_btn : function() {
		$(document).on("click", ".pf-btn-delete-article", function() {
			var $tr = $(this).closest("tr");
			showConfirmDialog(function() {
				$.ajax({
					type : "GET",
					cache : false,
					data : {
						id : $tr.data("data").id
					},
					dataType : "json",
					url : "/admin/articles/delete.json",
					success : function(data) {
						if (data != undefined && data.isSuccess == true) {
							// 刷新
							$tr.remove();
						} else {
							showAlertDialog(data.message);
						}
					}
				});
			}, '确定删除文章：' + $tr.data("data").title, "");
		});
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-article").bootstrapTable(
				{
					method : 'get',
					url : "/admin/articles/all.json?type=" + type,
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
					showColumns : true,
					showRefresh : true,
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
								field : 'parentCateName',
								title : '分类',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'title',
								title : '标题',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'createTime',
								title : '发布时间',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'pinyin',
								title : '拼音',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'seqNo',
								title : '排序号',
								align : 'left',
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
									var html="";
									//html+='<button type="button" class="btn btn-danger btn-xs pf-btn-delete-article"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;';
									return html
											+ '<a href="/admin/articles/edit?id=' + row.id
											+ '" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-pencil"></i>编辑</a>&nbsp;';
								}
							
							} ]
				});
	}
}
ArticleObject.init();