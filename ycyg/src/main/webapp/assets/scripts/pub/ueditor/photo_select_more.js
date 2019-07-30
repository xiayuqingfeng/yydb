/**
 * 选择图片
 */

// 闭包
(function($) {
	var pageObj = {
		initEvent : function() {

			$('#uploadBtn').click(function() {
				$("#fileId").trigger("click");
			});

			// 选图--多图模式
			$("#layer-photos li").on("click", function() {
				var icon = $(this).find("span");
				var img = $(this).find("img")
				if ($(icon).css("display") == "none") {
					$(icon).css("display", "block");
					$(icon).attr("show", "1");
				} else {
					$(icon).attr("show", "0");
					$(icon).css("display", "none");
				}
			});

			// 上传封面事件
			$("#fileId").bind(
					"change",
					function() {
						var fileName = $(this).val();
						if (fileName == "") {
							// $("#logoRemind").text("");
							return;
						} else if (fileName.indexOf(".jpg") > 0 || fileName.indexOf(".jpeg") > 0 || fileName.indexOf(".png") > 0 || fileName.indexOf(".gif") > 0
								|| fileName.indexOf(".JPG") > 0 || fileName.indexOf(".JPEG") > 0 || fileName.indexOf(".PNG") > 0 || fileName.indexOf(".GIF") > 0
								|| fileName.indexOf(".bmp") > 0) {
							// 上传图片
							pageObj.uploadPhoto();
						} else {
							pageObj.alert("图片格式不正确！");
							return;
						}
					});

		},
		alert : function(msg) {
			showPopup(msg);
		},
		/* 使用图片 */
		usePhoto : function(img) {
			$('#url').val($(img).attr("src"));
			$('#add-photo-tab-head>span[ref="remote"]').trigger("click");
			addPhoto.addPhoto();
		},
		/* 图片上传 */
		uploadPhoto : function() {
			if (!pageObj.checkImgSize("fileId", 2)) {
				return;
			}
			$("#uploadForm").ajaxSubmit({
				dataType : "text",
				success : function(data) {
					showPopup(data);
					window.setTimeout(function() {
						$('#add-photo-tab-head>span[ref="selectPhoto"]').trigger("click");
					}, 1000);
				}
			});
		},
		/** 校验文件大小 */
		checkImgSize : function(fileId, imgSize) {
			if (!imgSize) {
				imgSize = 2;
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
			if (totalSize >= 1024 * 1024 * 20) {
				pageObj.alert("本次图片上传，不能超过20M");
				return false;
			}
			return true;
		}
	};
	pageObj.initEvent();
	selectPhoto = pageObj;
})(jQuery);
var selectPhoto;// 闭包，要让外部调用
