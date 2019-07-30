/**
 * 广告管理列表
 */
var advObject = { 
	/** 初始化方法 */
	init : function() {
		// 删除事件*/
		this._reg_delete_btn_event(); 
	},
	/** 注册删除事件 */
	_reg_delete_btn_event : function() {

		$(".advItem-delete").each(function(){
			$(this).click(function(){
				var $me=$(this);
				showConfirmDialog(function(){
					$.ajax({
						type : "GET",
						cache : false,
						dataType : "json",
						data : {},
						url : "/admin/adv/deleteItem/"+$me.attr("advItemId"),
						success : function(data) {
							if(data.isSuccess){
								var o=data.message;
								showPopup("删除成功!");
								window.setTimeout(function(){
									window.location.reload();
								},1000);
							}else{
								showPopup("删除失败!");
							}
						}
					});
				}, '确定删除?');
				

			});
		});

	}
}

advObject.init();