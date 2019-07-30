function showConfirmDialog(callback, msg, title){
	$.messager.model = { 
        ok:{ text: "确定", classed: 'btn-danger' },
        cancel: { text: "取消", classed: 'btn-default' }
      };
      $.messager.confirm(title||"提醒", msg||"确定?", function() {
    	callback && callback();
      });
      return false;
}

function showAlertDialog(msg, title){
	$.messager.model = { 
        ok:{ text: "知道了", classed: 'btn-primary' },
      };
	  $.messager.alert(title||"注意", msg||"");
}

function showPopup(message){
	$.messager.popup(message);    
}