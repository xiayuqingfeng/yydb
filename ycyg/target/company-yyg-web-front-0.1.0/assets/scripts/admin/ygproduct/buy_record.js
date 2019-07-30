/** 文章分类对象 */
var ProductObject = {
	init : function() {
		// 初始化应用表格
		this._init_app_table(); 
		// 搜索按钮事件
		this._reg_search_btn_event();
		this._reg_view_buyNos();
	},
	// 搜索按钮事件
	_reg_search_btn_event : function() {
		$("#pf-btn-search").click(function() {
			$("#pf-table-product").bootstrapTable("refresh", {
				url : "/admin/ygproduct/buyRecord.json?id="+$("#ygProductId").val()+"&searchText=" + encodeURIComponent($("#searchbox").val()),
				pageNumber : 1
			});
		});
	}, 
	_reg_view_buyNos:function(){
		/** 弹出窗口 */
		$(document).on("click", ".moreNos", function() {
			var $tr = $(this).closest("tr");
			$("#buyNos_content").html($tr.data("data").buyNos.split(",").join(" "));
			$("#pf-modal-buyNos").modal('show'); 
		}); 
	},
	// 初始化应用表格
	_init_app_table : function() {
		$("#pf-table-product").bootstrapTable(
				{
					method : 'get',
					url : "/admin/ygproduct/buyRecord.json?id="+$("#ygProductId").val(),
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
								field : 'ipAddr',
								title : '所属地区',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'clientIp',
								title : 'IP地址',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'buyUserNickName',
								title : '购买人',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
 
						 
							{
								field : 'buyNum',
								title : '购买份数',
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
								field : 'createTime',
								title : '购买时间',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false
							},
							{
								field : 'buyNos',
								title : '云购号码',
								align : 'left',
								valign : 'middle',
								visible : true,
								sortable : false,
								formatter : function(value, row, index) {
									var html=''
									var nos=value.split(",");
									if(nos.length>5){
										html=nos.slice(0,5)+'&nbsp;'
											+ '<a class="btn btn-primary btn-xs moreNos">更多</a>&nbsp;'
									}else{
										html=value;
									}
						
									return html;
								}
							
							} ]
				});
	}
}
ProductObject.init();