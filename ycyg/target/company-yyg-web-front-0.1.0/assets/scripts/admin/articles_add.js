/**
 * 栏目内容编辑
 */
var AticlesAddObject = {
	/** 初始化方法 */
	init : function() {
		// 保存事件*/
		this._reg_save_btn_event();
		// 初始化编辑器
		this._init_editor();
	},
	/** 初始化编辑器 */
	_init_editor : function() {
		// 商品详情
		UE.getEditor('contentEditor', {
			allowDivTransToP : false,
			autoHeightEnabled : false
		});
	},
	/** 注册保存事件 */
	_reg_save_btn_event : function() {
		// 保存并使用按钮
		$("#btn-save-item-content").click(function() {
	
			// 分类
			var articleCateId = $("#articleCateId").val();
			if (articleCateId == "") {
				showPopup("分类不能为空!");
				$("#articleCateId").focus();
				return false;
			} 
			// 标题
			var title = $("#title").val();
			if (title == "") {
				showPopup("标题不能为空!");
				$("#title").focus();
				return false;
			}
			//拼音
	/*		var pinyin = $("#pinyin").val();
			if (pinyin == "") {
				showPopup("拼音不能为空!");
				$("#pinyin").focus();
				return false;
			} */
			//排序号
			var seqNo = $("#seqNo").val();
			if (seqNo == "") {
				showPopup("排序号不能为空!");
				$("#seqNo").focus();
				return false;
			} 
			$("#saveForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("保存成功!");
							window.setTimeout(function() {
								window.location = $('#goback').attr("href");
							}, 1000);
						} else {
							showPopup(data.message);
						}
					} else {
						showPopup("保存失败!");
					}

				}
			});

		});

	}

}

AticlesAddObject.init();