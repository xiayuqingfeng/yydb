/**
 * 编辑器外部调用图片管理  图片不加到编辑器中
 */
//闭包
(function($) {
var pageObj = {
	/*初始化事件*/
	imgHtml : "",
	initEvent:function(){
	    $(document).on("change", "#url,#width,#height", function() {
	    	pageObj.addPhoto();
	  	});
		 
		pageObj.initImg();    
	    try{
	    	dialog.onok = pageObj.endOk;
	    }catch(e){}
	    //注册linTab 页签事件
	    pageObj.reg_page_tab_click(); 
	    /**整数*/
        $(document).on("keypress", ".int", function(event) {    
      	  if(event.keyCode < 48 || event.keyCode > 57){
			  return false;
		  }
        }); 
        
        // 触发图片管理事件
        $("#add-photo-tab-head>span").last().trigger("click");
       	
	},      
    /**注册页面Tab 页签事件*/
	reg_page_tab_click:function(){
		$("#add-photo-tab-head>span").each(function(){
			$(this).click(function(){
				var refId=$(this).attr("ref");
				var url=$(this).attr("url");
				//显示当前tab标题
			    $('#add-photo-tab-head>span').removeClass("focus");
			    $(this).addClass("focus");
			  //显示当前tab内容
			    $('#add-photo-tab-body>div.panel').hide();
			    $('#'+refId).show();
			    if(url && url!=""){
			 	   jQuery.post(url,{},function(data){
			 		  $('#'+refId).html(data);
			 	   });
			    }
			});
		});
	},	   
	/**关闭页面事件*/
	 endOk:function(){
	   	var html; 
	   	html=pageObj.getImgHtml();
	   //	editor.execCommand('insertHtml',html,true);
	   	
	    dialog.close();
	},
	/**重新生成图片*/
	addPhoto:function (){
    	var img=$("#preview img");
    	img.remove();
    	var imgHtml= pageObj.getImgHtml();
    	$("#preview").append(imgHtml);
	},
	/**获取图片html -----------------单张图片*/
	getImgHtml_single:function(){
		var width=$("#width").val();
    	var height=$("#height").val();
    	var style="";
    	if(width!="" && !isNaN(width)){
    		style+="width:"+width+"px;";
    	}
    	if(height!="" && !isNaN(height)){
    		style+="height:"+height+"px;";
    	} 
    	return '<img  src="'+$('#url').val()+'" style="'+style+'">';
	},
	
	/**获取图片html-----------------多张图片*/
	getImgHtml:function(){
		var width=$("#width").val();
    	var height=$("#height").val();
    	var style="";
    	if(width!="" && !isNaN(width)){
    		style+="width:"+width+"px;";
    	}
    	if(height!="" && !isNaN(height)){
    		style+="height:"+height+"px;";
    	}
    	$("#layer-photos li").each(function(name, value){
    		if($(this).find("span").attr("show") == "1"){
    			addMorePhoto.imgHtml += '<img class="articleImg" src="'+$(this).find("img").attr("layer-src")+'" style="'+style+'">';
    		}
    	});
    	return addMorePhoto.imgHtml;
	},
	/**把图片显示在指定的div中*/
	setPreview:function (img){
		pageObj.addPhoto();
	},
	/**初始化图片：加载已选中的图片*/
	initImg:function (){
		try{
		     var img = editor.selection.getRange().getClosedNode();
	         if (img) {
	        	 pageObj.setImage(img);
	         }
		}catch(e){}
	},
	setImage:function (img){
        /* 不是正常的图片 */
        if (!img.tagName || img.tagName.toLowerCase() != 'img' && !img.getAttribute("src") || !img.src) return;

        var wordImgFlag = img.getAttribute("word_img"),
            src = wordImgFlag ? wordImgFlag.replace("&amp;", "&") : (img.getAttribute('_src') || img.getAttribute("src", 2).replace("&amp;", "&")),
            align = editor.queryCommandValue("imageFloat");

        /* 防止onchange事件循环调用 */
        if (src !== $G("url").value) $G("url").value = src;
        if(src) {
            /* 设置表单内容 */
            $G("width").value = img.width || '';
            $G("height").value = img.height || ''; 
            //$G("title").value = img.title || img.alt || ''; 
            pageObj.setPreview(img);
        }
	}
};
pageObj.initEvent();
addMorePhoto=pageObj;
})(jQuery);
var addMorePhoto;//闭包，要让外部调用