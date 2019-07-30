http://bootstrap-table.wenzhixin.net.cn/documentation/
$("#pf-members-table")
					.bootstrapTable(
							{
								method : 'get',
								url : "/manage/system/member/get/all",
								cache : false,
								// height : 400,
								sidePagination : 'server', // client or server
								queryParamsType : 'pageSize',
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
								checkboxHeader : false,
								clickToSelect : true,
								columns : [
										{
											field : 'id',
											title : '#',
											align : 'center',
											valign : 'middle',
											visible : false,
											sortable : false
										},
										{
											field : 'loginname',
											title : '登录名',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : true
										},
										{
											field : 'realname',
											title : '姓名',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : false
										},
										{
											field : 'tel',
											title : '电话',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : false
										},
										{
											field : 'isAdmin',
											title : '是否管理员',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : false,
											formatter : function(value, row, index) {
												if (value == true) {
													return '是';
												} else {
													return '否';
												}
											}
										},
										{
											field : 'statusString',
											title : '状态',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : false
										},
										{
											field : 'email',
											title : '邮件',
											align : 'left',
											valign : 'middle',
											visible : false,
											sortable : false
										},
										{
											field : 'lastLoginTime',
											title : '最后登录时间',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : true
										},
										{
											field : 'option',
											title : '操作',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : false,
											formatter : function(value, row, index) {
												return '  <button type="button" class="btn btn-primary btn-xs pf-delete-memeber-btn"><i class="glyphicon glyphicon-trash"></i>删除</button>&nbsp;'
														+ '<button type="button" class="btn btn-primary btn-xs pf-update-memeber-btn"><i class="glyphicon glyphicon-pencil"></i>修改</button>';
											}
										}, {
											field : 'loginIp',
											title : '最后登录ip',
											align : 'center',
											valign : 'middle',
											visible : false,
											sortable : false
										}, {
											field : 'mark',
											title : '备注',
											align : 'center',
											valign : 'middle',
											visible : true,
											sortable : false
										} ]
							});