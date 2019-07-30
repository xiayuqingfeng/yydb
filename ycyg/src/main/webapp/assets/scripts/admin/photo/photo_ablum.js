var PhotoAblum = {
	init : function() {
		// 注册-添加顶级数据-按钮事件
		this._reg_add_btn_event();
		this._init_table();
		// 修改数据按钮
		this._reg_update_cate_btn();		
		// 添加子数据按钮
		this._reg_add_sub_cate_btn();
		// 删除数据按钮事件
		this._reg_delete_cate_btn();
		this._reg_save_btn_event();
	},
	// 注册添加顶级数据按钮事件
	_reg_add_btn_event : function() {
		$("#pf-btn-add").click(function() {
			$("#pf-form-cate-save").resetForm();
			$("#pf-form-cate-save input[name='parentId']").val(0);
			$("#parentName").text("默认相册"); 
			$("#pf-modal-title").html("添加相册");
			$("#pf-modal-add-cate").modal("show");
		});
	},
	// 添加子数据按钮
	_reg_add_sub_cate_btn : function() {
		$(document).on("click", ".pf-add-sub-cate-btn", function() {
			var $tr = $(this).closest("tr");
			var data = $tr.data("data");
			// 重置form
			$("#pf-form-cate-save").resetForm();
			$("#pf-form-cate-save input[name='parentId']").val(data.id);
			$("#parentName").text(data.name);
			$("#pf-form-cate-save input[name='id']").val("");
			// 显示弹窗
			$("#pf-modal-title").html("添加相册");
			$("#pf-modal-add-cate").modal("show");
		});
	},
	// 初始化应用表格
	_init_table : function() {
		$("#pf-table-cate")
				.bootstrapTable(
						{
							method : 'get',
							url : "/admin/photo/ablum/list.json",
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
								$("#pf-table-cate tbody").hide();
								// 初始化tree-table
								$.treetable.defaults = {
									id_col : 0,// ID td列 {从0开始}
									parent_col : 1,// 父ID td列
									handle_col : 2,// 加上操作，展开操作的 td列
									open_img : "/assets/vendors/tree-table/images/minus.gif",
									close_img : "/assets/vendors/tree-table/images/plus.gif",
									is_open_start : false
								}
								setTimeout(function() {
									// 只能应用于tbody
									$("#tbody").treetable();
									// 隐藏数据列
									$("#tbody tr").find("td:eq(0)").hide();
									$("#tbody tr").find("td:eq(1)").hide();
									$("#pf-table-cate tr:eq(0)").find(
											"th:eq(0)").hide();
									$("#pf-table-cate tr:eq(0)").find(
											"th:eq(1)").hide();
									$("#pf-table-cate tbody").show();
								}, 100);
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
										field : 'parentId',
										title : '#parentId',
										align : 'left',
										valign : 'middle',
										visible : true,
										sortable : false,
										formatter : function(value, row, index) {
											if (row.parentId == null
													|| row.parentId == "") {
												return "";
											} else {
												return row.parentId;
											}
										}
									},
									{
										field : 'name',
										title : '相册名称',
										align : 'left',
										valign : 'middle',
										visible : true,
										sortable : false,
										formatter : function(value, row, index) {
											if (row.icons != null
													&& row.icons != "") {
												return '<i class="iconfontYC">'
														+ row.icons + '</i>'
														+ value;
											}
											return value;
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
											return '<button type="button" class="btn btn-danger btn-xs pf-delete-cate-btn"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;'
													+ '<button type="button" class="btn btn-success btn-xs pf-update-cate-btn"><i class="glyphicon glyphicon-pencil"></i>修改</button>&nbsp;'
													+ '<button type="button" class="btn btn-primary btn-xs pf-add-sub-cate-btn"><i class="glyphicon glyphicon-plus"></i>添加下级</button>&nbsp;';
										}

									} ]
						});
	},
	// 删除数据按钮事件
	_reg_delete_cate_btn : function() {
		$(document).on("click", ".pf-delete-cate-btn", function() {
			var $tr = $(this).closest("tr");
			showConfirmDialog(function() {
				$.ajax({
					type : "GET",
					cache : false,
					data : {
						id : $tr.data("data").id
					},
					dataType : "json",
					url : "/admin/photo/ablum/delete.json",
					success : function(data) {
						if (data != undefined && data.isSuccess == true) {
							// 刷新
							$tr.remove();
						} else {
							showAlertDialog(data.message);
						}
					}
				});
			}, '确定删除数据：' + $tr.data("data").name, "");
		});
	},
	// 修改数据按钮
	_reg_update_cate_btn : function() {
		$(document).on("click", ".pf-update-cate-btn", function() {
			// 重置form
			$("#pf-form-cate-save").resetForm();
			var $tr = $(this).closest("tr");
			var data = $tr.data("data");
			// 显示弹窗
			$("#pf-modal-title").html("修改相册");
			$("#pf-modal-add-cate").modal("show");
			// 设置数据
			$("#pf-form-cate-save input[name='id']").val(data.id);
			$("#pf-form-cate-save input[name='parentId']").val(data.parentId);
			$("#parentName").text(data.parentName);
			$("#pf-form-cate-save input[name='name']").val(data.name);			
		});
	},
	// 保存事件
	_reg_save_btn_event : function() {
		Ladda.bind('#pf-btn-save-cate');
		// class图标
		$("#pf-btn-save-cate").click(function() {
			// 名称
			if (validate($("#name"), "名称不能为空") == false) {
				return;
			}
			ajaxsubmit($("#pf-form-cate-save"));
		});
		/** 异步提交 */
		function ajaxsubmit($form) {
			$form.ajaxSubmit({
				dataType : "json",
				success : function(data) {
					// 关闭加载中
					Ladda.stopAll();
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("保存成功!");
							$("#pf-table-cate").bootstrapTable("refresh");
							$("#pf-modal-add-cate").modal('hide');
						} else {
							showAlertDialog(data.message);
						}
					} else {
						showAlertDialog("保存失败!");
					}
				},
				error : function() {
					// 关闭加载中
					Ladda.stopAll();
				}
			});
		}
		/** 校验方法 */
		function validate($input, errorMsg) {
			var val = $input.val();
			if ($.trim(val) == "") {
				showPopup(errorMsg);
				$input.focus();
				Ladda.stopAll();
				return false;
			} else {
				return true;
			}
		}
	}
}
PhotoAblum.init();