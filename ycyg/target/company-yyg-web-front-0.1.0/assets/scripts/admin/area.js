/** 系统地区字典对象 */
var Area = {
	// 地区树
	$areaTree : "",
	// 用于识别当前选中行
	data_index : "",
	init : function() {
		// 注册-添加顶级地区-按钮事件
		this._reg_add_top_area_btn_event();
		// 注册添加框中的保存事件
		this._reg_add_area_panel_save_btn_event();
		// 初始化应用表格
		this._init_app_table();
		// 添加子地区按钮
		this._reg_add_sub_area_btn();
		// 修改地区按钮
		this._reg_update_area_btn();
		// 删除地区按钮事件
		this._reg_delete_area_btn();
	},
	// 删除地区按钮事件
	_reg_delete_area_btn : function() {
		$(document).on("click", ".pf-delete-area-btn", function() {
			var $tr = $(this).closest("tr");
			showConfirmDialog(function() {
				$.ajax({
					type : "GET",
					cache : false,
					data : {
						id : $tr.data("data").id
					},
					dataType : "json",
					url : "/admin/area/delete.json",
					success : function(data) {
						if (data != undefined && data.isSuccess == true) {
							// 刷新
							$tr.remove();
						} else {
							showAlertDialog(data.message);
						}
					}
				});
			}, '确定删除地区：' + $tr.data("data").areaName, "");
		});
	},
	// 修改地区按钮
	_reg_update_area_btn : function() {
		$(document).on("click", ".pf-update-area-btn", function() {
			// 重置form
			$("#pf-form-area-save").resetForm();
			var $tr = $(this).closest("tr");
			var data = $tr.data("data");
			// 显示弹窗
			$("#pf-modal-title").html("修改地区信息");
			$("#pf-modal-add-area").modal("show");
			// 设置地区
			$("#pf-form-area-save input[name='id']").val(data.id);
			$("#pf-form-area-save input[name='paretnId']").val(data.paretnId);
			$("#pf-form-area-save input[name='areaName']").val(data.areaName);
			$("#pf-form-area-save input[name='seqNo']").val(data.seqNo);
		});
	},
	// 添加子地区按钮
	_reg_add_sub_area_btn : function() {
		$(document).on("click", ".pf-add-sub-area-btn", function() {
			var $tr = $(this).closest("tr");
			var data = $tr.data("data");
			//
			Area.data_index = $tr.attr("data-index");
			// 重置form
			$("#pf-form-area-save").resetForm();
			$("#pf-form-area-save input[name='parentId']").val(data.id);
			$("#pf-form-area-save input[name='id']").val("");
			// 显示弹窗
			$("#pf-modal-title").html("添加地区信息，父地区：" + data.areaName);
			$("#pf-modal-add-area").modal("show");
		});
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-area")
				.bootstrapTable(
						{
							method : 'get',
							url : "/admin/area/all.json",
							cache : false,
							// height : 400,
							sidePagination : 'server', // client or server
							queryParamsType : 'pageSize',
							search : true,
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
							onLoadSuccess : function() {
								$("#pf-table-area tbody").hide();
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
									// 隐藏地区列
									$("#tbody tr").find("td:eq(0)").hide();
									$("#tbody tr").find("td:eq(1)").hide();
									$("#pf-table-area tr:eq(0)").find("th:eq(0)").hide();
									$("#pf-table-area tr:eq(0)").find("th:eq(1)").hide();
									$("#pf-table-area tbody").show();
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
											if (row.parentId == null || row.parentId == "") {
												return "";
											} else {
												return row.parentId;
											}
										}
									},
									{
										field : 'areaName',
										title : '地区名称',
										align : 'left',
										valign : 'middle',
										visible : true,
										sortable : false
									},
									{
										field : 'seqNo',
										title : '排序',
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
											return '<button type="button" class="btn btn-danger btn-xs pf-delete-area-btn"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;'
													+ '<button type="button" class="btn btn-success btn-xs pf-update-area-btn"><i class="glyphicon glyphicon-pencil"></i>修改</button>&nbsp;'
													+ '<button type="button" class="btn btn-primary btn-xs pf-add-sub-area-btn"><i class="glyphicon glyphicon-plus"></i>添加子地区</button>&nbsp;';
										}
									} ]
						});
	},
	// 注册添加框中的保存事件
	_reg_add_area_panel_save_btn_event : function() {
		Ladda.bind('#pf-btn-save-area');
		// class图标
		$("#pf-btn-save-area").click(function() {
			// 名称
			if (validate($("#areaName"), "名称不能为空") == false) {
				return;
			}
			// 排序
			if (validate($("#seqNo"), "序号不能为空") == false) {
				return;
			}
			ajaxsubmit($("#pf-form-area-save"));
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
							$("#pf-table-area").bootstrapTable("refresh");
							$("#pf-modal-add-area").modal('hide');
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
	},
	// 注册添加顶级地区按钮事件
	_reg_add_top_area_btn_event : function() {
		$("#pf-btn-add-top-area").click(function() {
			$("#pf-modal-add-area input:not(input[name='type'])").val("");
			$("#pf-modal-add-area").modal("show");
		});
	}
}
Area.init();