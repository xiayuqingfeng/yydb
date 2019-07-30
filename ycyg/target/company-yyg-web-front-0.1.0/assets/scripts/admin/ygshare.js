/** 晒单对象 */
var ShareObject = {
	init : function() {
		// 注册新晒单按钮事件
		this._reg_add_members_btn_event();
		// 注册或者编辑 晒单，弹出框的保存按钮事件
		this._reg_save_member_btn_event();
		// 初始化应用表格
		this._init_app_table();
		// 注册表格中编辑与删除按钮事件
		this._reg_table_edit_and_delete_btn_event();
		// 注册时间控件
		this._reg_datetime_events();
	},
	// 注册时间控件
	_reg_datetime_events : function() {
		$('.wz-datetime').datetimepicker({
			language : "zh-CN",
			format : "yyyy-mm-dd hh:ii",
			autoclose : true,
			todayBtn : true,
			todayHighlight : true,
			minuteStep : 1
		});
	},
	// 注册表格中编辑与删除按钮事件
	_reg_table_edit_and_delete_btn_event : function() {

		// 删除按钮
		$(document).on("click", ".pf-table-btn-delete", function() {
			var $tr = $(this).closest("tr");
			// 行数据
			var rowData = $tr.data("data");
			showConfirmDialog(function() {
				// 异步请求
				$.ajax({
					type : "GET",
					cache : false,
					dataType : "json",
					url : "/admin/ygshare/delete.json?id=" + rowData.id,
					success : function(data) {
						if (data != undefined && data.isSuccess == true) {
							showPopup("删除成功!");
							$("#pf-table-members").bootstrapTable("refresh");
						} else {
							showAlertDialog(data.message, "异常");
						}
					}
				});
			}, "是否删除改晒单：" + rowData.title, "系统");
		});

		// 编辑按钮
		$(document).on("click", ".pf-table-btn-edit", function() {
			var $tr = $(this).closest("tr");
			// 行数据
			var rowData = $tr.data("data");
			//
			var $modal = $("#pf-modal-share");
			// 输入字段全部置为空
			$modal.find("input").val("");
			$modal.find("img").attr("src", "/assets/images/nophoto.png");
			// 设置字段值
			var $inputs = $modal.find("input");
			$inputs.each(function(i) {
				var name = $(this).attr("name");
				$(this).val(rowData[name]);
			});
			$("#content").val(rowData["content"]);
			var jsonarray = $.parseJSON(rowData.photos);
			// 设置图片
			$("#photos").html("");
			for (var i = 0, k = jsonarray.length; i < k; i++) {
				if (jsonarray[i].indexOf("http") != -1) {
					$("#photos").append('<img src="' + jsonarray[i] + '" alt="" style="height: 80px;">');
				} else {
					$("#photos").append('<img src="' + uploadImageBase + "/" + jsonarray[i] + '" alt="" style="height: 80px;">');
				}
			}
			// 通过 or 禁止
			if (rowData["audit"] == true) {
				$("#passOrForbidden").html("禁止");
			} else {
				$("#passOrForbidden").html("通过");
			}

			$("#pf-modal-share").modal('show');
		});
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-members").bootstrapTable(
				{
					method : 'get',
					url : "/admin/ygshare/all.json",
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
					ct : true,
					onLoadSuccess : function() {
						// 初始化表格中”操作“的checkbox
						setTimeout(function() {
							_reg_recomment_event();
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
								field : 'userLogoPath',
								title : '晒单用户',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									return '<img src="' + row.userLogoPath + '" style="width:30px;height:30px;">' + row.userNickName;
								}
							},
							{
								field : 'createTime',
								title : '晒单时间',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'title',
								title : ' 标题',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'audit',
								title : '审核状态',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									if (row.audit == true) {
										return "通过";
									} else {
										// TODO 查看审核未通过原因
										return "未通过";
									}
								}
							},
							{
								field : 'opration',
								title : '推荐',
								align : 'center',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									if (row.recommend == true) {
										return '<div title="已推荐"> <input type="checkbox" class="switch" data-size="mini" data-on-color="primary" id="' + row.id
												+ '" data-off-color="danger" checked/> </div>';
									} else {
										return '<div> <input type="checkbox" class="switch" data-size="mini" data-on-color="primary" id="' + row.id
												+ '" data-off-color="danger"/> </div>';
									}

								}
							}, {
								field : 'oprate',
								title : '操作',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									var html = '<a class="am-btn hl-btn-green  am-btn-xs pf-table-btn-edit" style="margin-right:5px;">审核</a>';
									// html = html + '<a class="am-btn
									// am-btn-danger am-btn-xs
									// pf-table-btn-delete"
									// style="margin-right:5px;">删除</a>';
									html = html + '<a class="am-btn am-btn-success am-btn-xs " onclick="site_preview(' + row.id + ');">查看</a>';
									return html;
								}

							} ]
				});
		function _reg_recomment_event() {
			$(".switch").bootstrapSwitch();
			$(".switch").on('switchChange.bootstrapSwitch', function(event, state) {
				var $me = $(this);
				$.ajax({
					type : "GET",
					cache : false,
					dataType : "json",
					data : {},
					url : "/admin/ygshare/recomment.json?id=" + $me.attr("id"),
					success : function(data) {
						if (data.isSuccess) {
							showPopup("操作成功!");
						}
					},
					error : function(req, status, err) {
						// $(".switch").bootstrapSwitch('state', !state, true);
						showPopup("发生错误，请重试!");
					}
				});

			});
		}
	},
	// 注册新晒单按钮事件
	_reg_add_members_btn_event : function() {
		$("#pf-add-member-btn").click(function() {
			//
			var $modal = $("#pf-modal-user");
			// 输入字段全部置为空
			$modal.find("input").val("");
			$modal.find("img").attr("src", "/assets/images/nophoto.png");
			$("#pf-modal-user").modal('show');
		});
	},
	// 注册或者编辑 晒单，弹出框的保存按钮事件
	_reg_save_member_btn_event : function() {
		Ladda.bind('#btn-save-user');
		// 保存并使用按钮
		$("#btn-save-user").click(function() {
			$("#saveForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					// 关闭加载中
					Ladda.stopAll();
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("保存成功!");
							$("#pf-table-members").bootstrapTable("refresh");
							$("#pf-modal-share").modal('hide');
						} else {
							showPopup(data.message);
						}
					} else {
						showPopup("保存失败!");
					}
				}
			});

		});
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
ShareObject.init();

var site_preview = function(id) {
	window.open("/share/" + id);
}
