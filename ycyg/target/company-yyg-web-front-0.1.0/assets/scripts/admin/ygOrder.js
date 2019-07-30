/** 中奖订单对象 */
var OrderObject = {
	init : function() { 
		// 注册进入发货
		this._reg_init_send_btn_event();
		// 注册发货
		this._reg_send_btn_event();
		// 初始化应用表格
		this._init_app_table(); 
	}, 
	_reg_init_send_btn_event:function(){
		//进入发货
		$(document).on("click", ".pf-table-btn-edit", function() {
			var $tr = $(this).closest("tr");
			// 行数据
			var rowData = $tr.data("data");
			//
			var $modal = $("#pf-modal-share");
			// 输入字段全部置为空
			$modal.find("input").val("");
			$("#id").val(rowData.id);
			$("#pf-modal-share").modal('show');
		});
	},
	_reg_send_btn_event:function(){
		$(document).on("click", "#btn-send", function() {
			if($("#expComId").val()==""){
				showPopup("请选择快递公司!");
				return false;
			}
			if($("#expNo").val()==""){
				showPopup("请选择快递单号!");
				$("#expNo").focus();
				return false;
			}
			// 发货按钮
			Ladda.bind('#btn-send');
			$("#saveForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					// 关闭加载中
					Ladda.stopAll();
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("发货成功!");
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
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-members").bootstrapTable(
				{
					method : 'get',
					url : "/admin/ygOrder/all.json",
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
						// 初始化表格中
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
								field : 'userId',
								title : '会员ID',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'orderId',
								title : '订单号',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'createTime',
								title : '下单时间',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'ygProduct',
								title : ' 商品信息',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									var html =  '<a href="/product/'+value.id+'.html" target="_blank"><img src="' + value.logoPath + '" style="width:40px;height:40px;">'+'(第'+value.period+'期)'+value.name;
									html+='&nbsp;&nbsp;￥'+(value.totalNum*value.singlePrice)+"</a>";
									return html;
								}
							},
							{
								field : 'address',
								title : ' 收货人信息',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									var html = value.provinceName+' '+value.cityName+' '+value.areaInfo+'<br/>'+value.trueName+' '+value.mobile;
							
									if(row.expNo && row.expressCom){
										html+='<br/>'+row.expressCom.name+'['+row.expNo+']'
									}
									return html;
								}
							}, {
								field : 'oprate',
								title : '状态与操作',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									var html = row.statusName;
									if(row.status==1){
										html+='<br/><a class="am-btn hl-btn-green  am-btn-xs pf-table-btn-edit" style="margin-right:5px;">发货</a>';
									}
									if(row.expNo && row.expressCom){
										html+='<br/><a href="'+row.expressCom.url+'?mscomnu='+row.expNo+'" target="_blank" class="am-btn am-btn-success am-btn-xs" style="margin-right:5px;">查看物流</a>';
									}
									return html;
								}

							} ]
				}); 
	}
}
OrderObject.init();

var site_preview = function(id) { 
	window.open("/share/" + id);
}
