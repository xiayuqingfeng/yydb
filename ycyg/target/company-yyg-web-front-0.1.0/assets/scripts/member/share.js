/**晒单分享 */
var PhotoObject = {
	init : function() { 
		// 上传图片控件的点击事件
		this._reg_files_event();
		// 上传图片
		this._reg_upload_event(); 
	}, 
	//注册上传
	_reg_upload_event:function(){  
		$(".btn-upload-photo").click(function(){
			$(this).parent().find("input[type=file]").trigger("click");
		});
	}, 
	// 上传图片控件的点击事件
	_reg_files_event : function() {
		$("input[type=file]").change(function(event){
			var files = event.target.files, file;	
			if (files && files.length > 0) { 
				// 获取目前上传的文件 
				file = files[0]; 
			}
			 if(file.size > 1024 * 1024 * 2) { 
				 ygAlert('图片大小不能超过 2MB!'); 
				 return false; 
			}
			 var fileName = $(this).val();
			 if (fileName.indexOf(".jpg") > 0
						|| fileName.indexOf(".jpeg") > 0
						|| fileName.indexOf(".png") > 0
						|| fileName.indexOf(".gif") > 0
						|| fileName.indexOf(".JPG") > 0
						|| fileName.indexOf(".JPEG") > 0
						|| fileName.indexOf(".PNG") > 0
						|| fileName.indexOf(".GIF") > 0
						|| fileName.indexOf(".bmp") > 0) {
				 var URL = window.URL || window.webkitURL;
			      // 通过 file 生成目标 url
			      var imgURL = URL.createObjectURL(file);
			      addImg($(this).parent().parent().find(".viewImg"),imgURL); 
			 }else{
				 $(this).val(""); 
				 ygAlert('图片格式不正确！'); 	
				 return false; 
			 }
	
	
		      
		});  
		function addImg(a,url){
			a.html('<img src="'+url+'" width="100" height="100" />');
			a.attr("target","_blank");
			a.attr("href",url);
		}
	}, 
	/**保存校验*/
	check:function(){
	    var title=$("#title");
	    if(title.val()==""){
			   	ygAlert('请输入标题！',function(){
				   	title.focus();
			   	});
			   	 return false;
	    }

		if($(".viewImg img").length<=0){
			ygAlert('至少上传一张图片！');
		   	return false;
		}
	    
		return true;
	}
	

}
PhotoObject.init();

