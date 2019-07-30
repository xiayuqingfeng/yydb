/**
 * 修改头像
 */
// 页面初始化
$(document).ready(function() {
	pageObj.initEvent();
});
var pageObj = {
	alert : function(msg, click) {
		var index = layer.open({
			content : msg,
			time : 2,
			shadeClose : false,
			end : function() {
				layer.close(index);
			}
		});
	},
	showLoading : function(msg) {
		pageObj.index = layer.open({
			content : msg,
			anim : true,
			type : 3,
			shadeClose : false
		});
	},
	hideLoading : function() {
		// $.BzwMessageBox.hide();
	},

	initEvent : function() {
		$(".head_fr a span").click(function() {
			// 切换效果
			if ($(".other_oper").hasClass("hidden")) {
				$(".other_oper").removeClass("hidden");
			} else {
				$(".other_oper").addClass("hidden");
			}
		});

		// 拍照
		$(".take_photo").click(function() {
			// $(".container").addClass("hidden");
			// $(".contentHolder").removeClass("hidden");
			// 切换效果
			if ($(".other_oper").hasClass("hidden")) {
				$(".other_oper").removeClass("hidden");
			} else {
				$(".other_oper").addClass("hidden");
			}
		});
		// 相册
		$(".location_photo").click(function() {
			// $(".contentHolder").addClass("hidden");
			// $(".container").removeClass("hidden");
			// 切换效果
			if ($(".other_oper").hasClass("hidden")) {
				$(".other_oper").removeClass("hidden");
			} else {
				$(".other_oper").addClass("hidden");
			}
		});

		$(window).load(
				function() {
					var options = {
						thumbBox : '.thumbBox',
						spinner : '.spinner',
						imgSrc : ""
					}
					$('#upload-file').on('change', function() {
						var reader = new FileReader();
						reader.onload = function(e) {
							options.imgSrc = e.target.result;
							cropper = $('.imageBox').cropbox(options);
						}
						reader.readAsDataURL(this.files[0]);
						this.files = [];
					})
					$('#btnCrop').on(
							'click',
							function() {
								var img = cropper.getDataURL();
								$('.cropped').html('');
								$('.cropped').append(
										'<img src="' + img
												+ '" align="absmiddle" style="width:1.28rem;margin-top:0.04rem;border-radius:1.28rem;box-shadow:0 0 0.12rem #7E7E7E;">');
							});
					$('#btnZoomIn').on('click', function() {
						cropper.zoomIn();
					})
					$('#btnZoomOut').on('click', function() {
						cropper.zoomOut();
					})
				});

		$("#uploadFrontBtn").click(function() {
			$("#frontFileId").trigger("click");

		});
		// 上传头像事件
		$("#frontFileId").bind(
				"change",
				function() {

					var imageUrl = getObjectURL($(this)[0].files[0]);
					var fileName = $(this).val();
					convertImgToBase64(imageUrl, function(base64Img) {

						// base64Img为转好的base64,放在img src直接前台展示(<img
						// src="data:image/jpg;base64,/9j/4QMZRXh...." />)
						// alert(base64Img);
						$("#baseImg").attr("src", base64Img);
						// base64转图片不需要base64的前缀data:image/jpg;base64
						// alert(base64Img.split(",")[1]);
						// $("#uploadFile").val(base64Img.split(",")[1]);
						if (fileName == "") {
							// $("#logoRemind").text("");
							return;
						} else if (fileName.indexOf(".jpg") > 0 || fileName.indexOf(".jpeg") > 0 || fileName.indexOf(".png") > 0 || fileName.indexOf(".gif") > 0
								|| fileName.indexOf(".JPG") > 0 || fileName.indexOf(".JPEG") > 0 || fileName.indexOf(".PNG") > 0 || fileName.indexOf(".GIF") > 0
								|| fileName.indexOf(".bmp") > 0) {
							// 上传图片
							pageObj.uploadFront();
						} else {
							pageObj.alert("图片格式不正确！");
							return;
						}
					});
					event.preventDefault();
				});

	},

	/* 头像图片上传 */
	uploadFront : function() {
		if (!pageObj.checkImgSize("frontFileId", 2)) {
			return;
		}
		var url = base + "/member/uploadUserImg.htm";
		// 当前图片个数
		$("input[name='indexImgSize']").val($(".pic img").length);
		var $form = $("#uploadFrontForm");
		$form.attr("action", url);
		pageObj.submitForm($form, "图片上传中...");
	},
	/** 校验文件大小 */
	checkImgSize : function(fileId, imgSize) {
		if (!imgSize) {
			imgSize = 2;
		}
		var file = document.getElementById(fileId);
		var totalSize = 0;
		for (var i = 0; i < file.files.length; i++) {
			if (file.files[i].size >= 1024 * 1024 * imgSize) {
				pageObj.alert("图片[" + file.files[i].name + "]不能超过" + imgSize + "M");
				return false;
			}
			totalSize += file.files[i].size;

		}
		if (totalSize >= 1024 * 1024 * 2) {
			pageObj.alert("本次图片上传，不能超过2M");
			return false;
		}
		return true;
	},
	submitForm : function(form, msg) {
		// 打开等待条
		pageObj.showLoading(msg);
		if ($('#iframeAjax')) {
			$('#iframeAjax').remove();
		}
		// 动态创建，解决重复提交
		$(document.body).append('<iframe id="iframeAjax" name="iframeAjax" style="display: none;"></iframe>');
		var base64 = $("#baseImg").attr("src");
		var formData = new FormData($("#uploadFrontForm")[0]);
		$.ajax({
			url : '/member/uploadUserImg.htm',
			type : 'POST',
			data : {
				base64Code : base64,
				indexImgSize : 1
			},
			cache : false,
			/*
			 * async : false, contentType : false, processData : false,
			 */
			success : function(data) {
				layer.close(pageObj.index);
				if (data.isSuccess == true) {
					// 刷新页面
					window.location = window.location.href;
				} else {
					pageObj.alert("提交失败");
				}
			},
			error : function(returndata) {
				layer.close(pageObj.index);
				if (returndata.statusText == 'OK') {
					// pageObj.alert("提交成功");
					// 刷新页面
					window.location = window.location.href;
				} else {
					pageObj.alert("提交失败");
				}
			}
		});
		// form.submit();
	},
	/* 头像图片上传结果回调：iframe页面调用 */
	uploadFrontResult : function(obj) {
		if ($('#iframeAjax')) {
			$('#iframeAjax').remove();
		}
		// 关闭等待条
		pageObj.hideLoading();
		if (obj.isSuccess) {
			for (var i = 0; i < obj.message.length; i++) {
				pageObj.addFrontPhoto(obj.message[i]);
			}
		} else {
			pageObj.alert(obj.message);
		}

	},
	/* 上传后，页面添加个人图片 */
	addFrontPhoto : function(photo) {
		// 刷新页面
		window.location = window.location.href;
	}
}

// 前端只需要给input file绑定这个change事件即可（下面两个方法不需要修改）获取到图片
$('.testUpload').bind('change', function(event) {
	var imageUrl = getObjectURL($(this)[0].files[0]);
	convertImgToBase64(imageUrl, function(base64Img) {

		// base64Img为转好的base64,放在img src直接前台展示(<img
		// src="data:image/jpg;base64,/9j/4QMZRXh...." />)
		// alert(base64Img);
		$("#base").attr("src", base64Img);
		// base64转图片不需要base64的前缀data:image/jpg;base64
		// alert(base64Img.split(",")[1]);
		$("#uploadFile").val(base64Img.split(",")[1]);
	});
	event.preventDefault();
});

// 生成图片的base64编码
function convertImgToBase64(url, callback, outputFormat) {
	// html5 的convas画布
	var canvas = document.createElement('CANVAS');
	var ctx = canvas.getContext('2d');
	var img = new Image;
	img.crossOrigin = 'Anonymous';
	img.onload = function() {
		var width = img.width;
		var height = img.height;
		// 按比例压缩4倍
		// var rate = (width<height ? width/height : height/width)/4;
		// 原比例生成画布图片
		var rate = 1;
		canvas.width = width * rate;
		canvas.height = height * rate;
		ctx.drawImage(img, 0, 0, width, height, 0, 0, width * rate, height * rate);
		// canvas.toDataURL 返回的是一串Base64编码的URL，当然,浏览器自己肯定支持
		var dataURL = canvas.toDataURL(outputFormat || 'image/png');
		callback.call(this, dataURL);
		canvas = null;
	};
	img.src = url;
}

// createobjecturl()静态方法创建一个包含了DOMString代表参数对象的URL。该url的声明周期是在该窗口中.也就是说创建浏览器创建了一个代表该图片的Url.
function getObjectURL(file) {
	var url = null;
	if (window.createObjectURL != undefined) {
		// basic
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) {
		// mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) {
		// web_kit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}