//添加模板按钮
UE.registerUI('addTemplateBtn',function(editor,uiName){
    
  
    //增加插入模板按钮
    var btn = new UE.ui.Button({
        name:'btn_' + uiName,
        title:'选择模板',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -340px 80px;',
        onclick:function () {
          //显示隐藏模板
        	$('#style-operate-area').toggle();
        }
    });
    return btn;   
	
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);