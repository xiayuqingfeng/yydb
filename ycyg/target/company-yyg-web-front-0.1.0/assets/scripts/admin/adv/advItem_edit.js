/**
 * 首页banner编辑
 */
var advObject = { 
	/** 初始化方法 */
	init : function() {
		// 保存事件*/
		this._reg_save_btn_event(); 
		this._reg_upload_photo();
		// 更换图片
		this._reg_change_photo_event();
	},
	/** 注册保存事件 */
	_reg_save_btn_event : function() {

		// 保存并使用按钮
		$("#btn-save-item-content").click(function() {
			  
			// 用户名
			var title = $("#title").val();
			if(title==""){
				showPopup("标题不能为空!");
				$("#title").focus();
				return false;
			} 
			if($("#seqNo").val()==""){
				showPopup("排序号不能为空!");
				$("#seqNo").focus();
				return false;
			} 
			if($("#photoId").val()==""&&$("#image_preview").css("display")=="none"){
				showPopup("请选择图片!");
				return false;
			}
			 $("#saveForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("保存成功!");
						} else {
							showPopup(data.message);
						}
					} else {
						showPopup("保存失败!");
					}
					window.setTimeout(function(){
						window.location=$('#goback').attr("href");
					},1000);
				}
			});

		});	

	},
	/** 上传图片事件 */
	_reg_upload_photo:function(){
		$("#photoFile").bind("change", function () {
			var fileName = $(this).val();
			if (fileName == "") {
				showPopup("请选择图片");	
				advObject.deleteImg();
				return;
			} else if (fileName.indexOf(".jpg") > 0
				|| fileName.indexOf(".jpeg") > 0
				|| fileName.indexOf(".png") > 0
				|| fileName.indexOf(".gif") > 0
				|| fileName.indexOf(".JPG") > 0
				|| fileName.indexOf(".JPEG") > 0
				|| fileName.indexOf(".PNG") > 0
				|| fileName.indexOf(".GIF") > 0
				|| fileName.indexOf(".bmp") > 0) {
				if (!advObject.checkImgSize("photoFile",5)) {
					$(this).val("");
					return;
				}
				$("#img_preview").html('');
				$("#image_preview").show();
				if (window.FileReader) {
					var fr = new FileReader();
					fr.onloadend = function(e) {
						$("#img_preview").html('<img id="imgId" src="'+e.target.result+'">');
					};
					fr.readAsDataURL(this.files[0]);
				}
			} else {
				$(this).val("");
				showPopup("图片格式不正确！");	
				return;
			}
		});
	},
	/** 更换图片 */
	_reg_change_photo_event:function(){
		$("#btn-change-img").click(function(){
			$("#photoFile").trigger("click");
		});
	},
	deleteImg:function(){
		$("#photoFile").val('');
		$('#photoId').val("");
		$("#img_preview").html('');
		$("#image_preview").hide();
	},
	/** 校验图片大小 */
	checkImgSize:function(fileId,imgSize){
		if(!imgSize){
			imgSize=1;
		}
		var file = document.getElementById(fileId);  
		var totalSize=0;
		for(var i=0;i<file.files.length;i++){
			if(file.files[i].size>=1024*1024*imgSize){
				showPopup("图片["+file.files[i].name+"]不能超过"+imgSize+"M");
				return false;
			}
			totalSize+=file.files[i].size;
			
		}
		if( totalSize>=1024*1024*5){
			showPopup("图片上传，不能超过5M");
			return false;
		}
		return true;
	}
	 
}

advObject.init();