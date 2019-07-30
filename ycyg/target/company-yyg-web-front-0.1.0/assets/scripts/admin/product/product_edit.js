/**
 * 商品编辑
 */
var ProductAddObject = {
	/** 初始化方法 */
	init : function() { 
		// 保存事件*/
		this._reg_save_btn_event(); 
		// 注册checkbox
		this._reg_icheckbox();
		// 初始化编辑器
		this._init_editor();
		// 初始化上传商品图片
		this._init_uploadFile();
	},
	/** 注册多选框 */
	_reg_icheckbox : function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-orange',
			radioClass : 'iradio_square-orange',
			increaseArea : '20%' // optional
		});
	}, 
	/** 注册保存事件 */
	_reg_save_btn_event : function() {
		// 保存并使用按钮
		$("#btn-save-product,#btn-publish-product").click(function() {
			// 标题
			var cateId = $("#cateId").val();
			if (cateId == "") {
				$('#p-Tabs a:first').tab('show');
				showPopup("请选择分类!"); 
				return false;
			}
			// 标题
			var name = $("#name").val();
			if (name == "") {
				$('#p-Tabs a:first').tab('show');
				showPopup("名称不能为空!");
				$("#name").focus();
				return false;
			}
			// 排序号
			var seqNo = $("#seqNo").val();
			if (seqNo == "") {
				$("#seqNo").val(50);
				return false;
			}
			// 商品图片
			var xxtkDatas = [];
			$("#ul_xjtk li").each(function() {
				var txt = $(this).find(".logoPath");
				if(txt.val()!=""){
					xxtkDatas.push({
						"photoPath" : txt.val(),
						"remark" : ""
					})
				}
			});
			$("#photosFile").val(JSON.stringify(xxtkDatas));
			// alert($("#saveForm").serialize());return false;
			// $("#content").val(UE.getEditor('editorZrmc').getContent());
			// $("#tbalContent").val(UE.getEditor('editorTbal').getContent());
			$("#saveForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("保存成功!");
							window.setTimeout(function() {
								$('#goback').click();
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

	}, 
	/** 初始化编辑器 */
	_init_editor : function() {
		// 商品详情
		UE.getEditor('editorZrmc', {
			allowDivTransToP : false,
			autoHeightEnabled : false
		});
	},
	/** 初始化上传商品图片 */
	_init_uploadFile : function() {
		// 点击选择图片
		$('#btn-choose-xxtk').click(function() {
			// UE.getEditor('editorZrmc').fireEvent("addImgMoreDialog");
			var dialog = UE.getEditor('editorZrmc').ui._dialogs["addImgOutDivDialog"];
			dialog.render();
			dialog.open();
			
		});
		// 点击上传
		$('#btn-add-xxtk').click(function() {
			$("#photos").trigger("click");
		});
		// 文件提交
		$("#photos").bind("change", function() {
			if (!ProductAddObject.checkFileSize()) {
				return;
			}
			$("#uploadForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("上传成功!");
							ProductAddObject.loadXxtkFiles(data.message);
						} else {
							showPopup(data.message);
						}
					} else {
						showPopup("上传失败!");
					}

				}
			});
		});
		// 加载文件
		if ($('#photosFile').val() != "") {
			var photos = JSON.parse($('#photosFile').val());
			this.loadXxtkFiles(photos);
		}else{
			this.loadXxtkFiles([]);
		}

	},
	/** 加载商品图片文件 */
	loadXxtkFiles : function(data) {
		for (var i = 0; i < data.length; i++) {
			var obj = data[i];
			var txt = $('#ul_xjtk_copy .logoPath');
			txt.attr("id","logoPath_"+i);
			txt.attr("value", obj.photoPath);
			
			var selectPhoto = $('#ul_xjtk_copy .btn-dialog-photo');
			selectPhoto.attr("tid",txt.attr("id"));
			var li = $('#ul_xjtk_copy').html();
			$('#ul_xjtk').append(li);
			txt.attr("id","");
		}
		//至少3行
		if(data.length<3){
			for (var i = data.length; i < 3; i++) { 
				var txt = $('#ul_xjtk_copy .logoPath');
				txt.attr("id","logoPath_"+i);
				txt.attr("value","");
				
				var selectPhoto = $('#ul_xjtk_copy .btn-dialog-photo');
				selectPhoto.attr("tid",txt.attr("id"));
				var li = $('#ul_xjtk_copy').html();
				$('#ul_xjtk').append(li);
				txt.attr("id","");
			}
		}
	},
	// 编辑商品图片
	editXxtk : function(o) {
		$(o).hide();
		$(o).parent().find(".ok").show();
		var info = $(o).parent().find(".info");
		info.show();
		var link = $(o).parent().find(".link");
		link.hide();
		info.bind('keyup', function() {
			link.text(info.val());
		});
	},
	// 确定编辑商品图片
	okEditXxtk : function(o) {
		$(o).hide();
		$(o).parent().find(".edit").show();
		$(o).parent().find(".info").hide();
		$(o).parent().find(".link").show();
	},
	// 确定编辑商品图片
	deleteXxtk : function(o) {
		showConfirmDialog(function() {
			$(o).parent().remove();
		}, "确定要删除吗");
	},
	/** 验证文件大小 */
	checkFileSize : function() {
		var file = document.getElementById("photos");
		var totalSize = 0;
		var fileSize = 10;// 10M
		for (var i = 0; i < file.files.length; i++) {
			var fileName = file.files[i].name;
			if (fileName.indexOf(".jpg") > 0 || fileName.indexOf(".jpeg") > 0 || fileName.indexOf(".png") > 0 || fileName.indexOf(".gif") > 0
					|| fileName.indexOf(".JPG") > 0 || fileName.indexOf(".JPEG") > 0 || fileName.indexOf(".PNG") > 0 || fileName.indexOf(".GIF") > 0
					|| fileName.indexOf(".bmp") > 0) {
				if (file.files[i].size >= 1024 * 1024 * fileSize) {
					showPopup("文件[" + file.files[i].name + "]不能超过" + fileSize + "M");
					return false;
				}
				totalSize += file.files[i].size;
			} else {
				showPopup("图片格式不正确！");
				return false;
			}
		}
		if (totalSize >= 1024 * 1024 * 20) {
			showPopup("本次累计上传文件，不能超过20M");
			return false;
		}
		return true;
	}

}

ProductAddObject.init();