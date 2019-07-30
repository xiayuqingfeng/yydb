var userAccountDetailObject = {
	init : function() {
		// 初始化应用表格
		this._init_app_table();
		// 充值
		this._reg_amount_btn_event();
	},
	// 充值
	_reg_amount_btn_event : function() {
		Ladda.bind('#pf-btn-amount');
		$("#pf-btn-amount").click(function() {
			// 充值
			var amount = $("#amount").val();
			if(amount==undefined||amount==''){
				showAlertDialog("金额不能为空", "异常");
				return false;
			}
			$.ajax({
				data : {
					amount : amount,
					userId : userId
				},
				url : "/admin/userAccountDetail/amount.json",
				cache : false,
				dataType : "json",
				async : false,// 异步参数 默认true (false为同步执行)
				success : function(data) {
					Ladda.stopAll();
					if (data.isSuccess == true) {
						// 刷新
						$("#pf-table-payRecord").bootstrapTable("refresh");
						showAlertDialog("充值成功", "充值成功");
					} else {
						showAlertDialog(data.message, "异常");
					}
				}
			});
		});
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-payRecord").bootstrapTable({
			method : 'get',
			url : "/admin/userAccountDetail/all.json?userId=" + userId,
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
				// 初始化表格中
			},
			columns : [ {
				field : 'id',
				title : '#id',
				align : 'center',
				valign : 'middle',
				visible : false,
				sortable : false

			}, {
				field : 'userId',
				title : '会员ID',
				align : 'left',
				valign : 'middle',
				visible : true,
				sortable : false
			}, {
				field : 'amount',
				title : '金额',
				align : 'left',
				valign : 'middle',
				visible : true,
				sortable : false
			}, {
				field : 'direction',
				title : '状态',
				align : 'left',
				valign : 'middle',
				visible : false,
				sortable : false
			}, {
				field : 'remark',
				title : '备注',
				align : 'left',
				valign : 'middle',
				visible : true,
				sortable : false
			} ]
		});
	}
}
userAccountDetailObject.init();
