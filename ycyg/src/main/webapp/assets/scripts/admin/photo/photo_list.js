/** 会员对象 */
var PhotoObject = {
	init : function() {
		// 注册切换相册
		this._reg_change_ablum(); 
		// 上传图片控件的点击事件
		this._reg_files_event();
		// 上传图片
		this._reg_upload_event();
		this._reg_select_more();
		this._reg_delete_photo();
		this._reg_change_remark();
	},
	// 注册切换相册
	_reg_change_ablum : function() {
		$('#albumId').change(function(){
			window.location=$(this).attr("url")+"&albumId="+$(this).val()+"#7020";
		});
	},
	//注册上传
	_reg_upload_event:function(){ 
		$("#btn-upload-photo").click(function(){
			$("#fileId").trigger("click");
		});
	},
	//选择图片
	_reg_select_more:function(){
		// 选图--多图模式
		$(".photoItem").on("click", function() {
			var icon = $(this).find("span"); 
			if(singleSelect=="true" || singleSelect==""){
				$(".photoItem span").each(function(){ 
					if(icon.attr("photoId")!=$(this).attr("photoId")){
						$(this).attr("show", "0");
						$(this).css("display", "none");
					}
				});
			}
			if ($(icon).css("display") == "none") {
				$(icon).css("display", "block");
				$(icon).attr("show", "1");
			} else {
				$(icon).attr("show", "0");
				$(icon).css("display", "none");
			}
		});
	
	},
	_reg_delete_photo:function(){
		$("#btn-delete-photo").on("click", function() {
			var photoIds=[];
			$(".photoItem span[show=1]").each(function(){
				photoIds.push($(this).attr("photoId"));
			});
			if(photoIds.length==0){
				showPopup("请选择要删除的图片");
				return;
			}
			showConfirmDialog(function(){
				$.ajax({
					type : "GET",
					cache : false,
					dataType : "json",
					data : {ids:photoIds.join(",")},
					url : "/admin/photo/delete.json",
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
		
	},
	_reg_change_remark:function(){
		$(".changeRemark").on("change", function() {
			var id=$(this).attr("photoId");
			var remark=$(this).val();
			$.ajax({
				type : "GET",
				cache : false,
				dataType : "json",
				data : {id:id,remark:remark},
				url : "/admin/photo/updateRemark.json",
				success : function(data) {
					
				}
			});
		});
		
	},
	// 上传图片控件的点击事件
	_reg_files_event : function() {
		bindFileChange("fileId");
		/** 绑定文件选择后的事件处理 */
		function bindFileChange(fileId) {
			$("#" + fileId).bind(
					"change",
					function() {
						var fileName = $(this).val();
						if (fileName == "") {
							showPopup("请选择图片");
							return;
						} else if (fileName.indexOf(".jpg") > 0 || fileName.indexOf(".jpeg") > 0 || fileName.indexOf(".png") > 0 || fileName.indexOf(".gif") > 0
								|| fileName.indexOf(".JPG") > 0 || fileName.indexOf(".JPEG") > 0 || fileName.indexOf(".PNG") > 0 || fileName.indexOf(".GIF") > 0
								|| fileName.indexOf(".bmp") > 0) {
							if (!checkImgSize(fileId, 2)) {
								$(this).val("");
								return;
							}
							PhotoObject.uploadPhoto();
						} else {
							$(this).val("");
							showPopup("图片格式不正确！");
							return;
						}
					});
		} 
		/** 校验图片大小 */
		function checkImgSize(fileId, imgSize) {
			if (!imgSize) {
				imgSize = 1;
			}
			var file = document.getElementById(fileId);
			var totalSize = 0;
		
			if(file.files){
				for (var i = 0; i < file.files.length; i++) {
					if (file.files[i].size >= 1024 * 1024 * imgSize) {
						showPopup("图片[" + file.files[i].name + "]不能超过" + imgSize + "M");
						return false;
					}
					totalSize += file.files[i].size;
					
				}
			}else{
				if (file.size >= 1024 * 1024 * imgSize) {
					showPopup("图片[" + file.name + "]不能超过" + imgSize + "M");
					return false;
				}
				totalSize += file.size;
			}
			if (totalSize >= 1024 * 1024 * 5) {
				showPopup("图片上传，不能超过5M");
				return false;
			}
			return true;
		}

	},
	/**图片上传*/
	uploadPhoto: function () {
		$("#uploadForm").ajaxSubmit({
			type: "POST",
			dataType : "text",
			success : function(data) {
				showPopup(data);
				window.setTimeout(function(){
					window.location.reload();
				},1000);
				
			},error:function(){
				showPopup("上传失败!");
			}
		});
	},
	/**选择的照片*/
	getSelectedPhotos:function(){
		var photos=[];
		$(".photoItem span[show=1]").each(function(){
			photos.push({"photoId":$(this).attr("photoId"),"photoPath":$(this).attr("photoPath")});
		});
		return photos;
	}
	

}
PhotoObject.init();

