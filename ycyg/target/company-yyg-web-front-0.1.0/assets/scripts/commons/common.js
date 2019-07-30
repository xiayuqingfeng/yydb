function ygAlert(title,funback){
	 layer.alert(title,0,'提示',function(index){
		   	layer.close(index);
		   	if(funback){
		   		funback();
		   	}
	  }); 
}
//文件大小
function getFileSize(fileId) {
    var size = 0;
    try {
       
    	var file = document.getElementById(fileId);
		var totalSize = 0;
	
		if(file.files){
			for (var i = 0; i < file.files.length; i++) { 
				totalSize += file.files[i].size;
				
			}
		}else{ 
			totalSize += file.size;
		}
    } catch (e) {
        //alert("错误：" + e);
    } 
    return size;
}