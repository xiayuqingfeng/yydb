
//添加插入图片按钮
UE.registerUI('addImgDialog',function(editor,uiName){
    //创建dialog
    var dialog = new UE.ui.Dialog({
        //指定弹出层中页面的路径，这里只能支持页面,因为跟addCustomizeDialog.js相同目录，所以无需加路径
        //iframeUrl:base+'/user/article/manage/goods_list',
        iframeUrl:base+'/pub/photo/addPhotoPre',
        //需要指定当前的编辑器实例
        editor:editor,
        //指定dialog的名字
        name:uiName,
        //dialog的标题
        title:"插入单张图片",

        //指定dialog的外围样式
        cssRules:"width:700px;height:480px;",

        //如果给出了buttons就代表dialog有确定和取消
        buttons:[
            {
                className:'edui-okbutton',
                label:'确定',
                onclick:function (a,b) {
                    dialog.close(true);
                }
            },
            {
                className:'edui-cancelbutton',
                label:'取消',
                onclick:function () {
                    dialog.close(false);
                }
            }
        ]});
    //UE.getEditor('container').ui._dialogs[uiName]=dialog;
  
    //增加插入图片按钮
    var btn = new UE.ui.Button({
        name:'btn_' + uiName,
        title:'插入单张图片',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -380px 0;',
        onclick:function () {
            //渲染dialog
            dialog.render();
            dialog.open();
        }
    });
    return btn;   
	
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);


//添加插入图片按钮
UE.registerUI('addImgMoreDialog',function(editor,uiName){
    //创建dialog
    var dialog = new UE.ui.Dialog({
        //指定弹出层中页面的路径，这里只能支持页面,因为跟addCustomizeDialog.js相同目录，所以无需加路径
        //iframeUrl:base+'/user/article/manage/goods_list',
        iframeUrl:base+'/pub/photo/addPhotoPre?type=1',
        //需要指定当前的编辑器实例
        editor:editor,
        //指定dialog的名字
        name:uiName,
        //dialog的标题
        title:"插入多张图片",

        //指定dialog的外围样式
        cssRules:"width:700px;height:480px;",

        //如果给出了buttons就代表dialog有确定和取消
        buttons:[
            {
                className:'edui-okbutton',
                label:'确定',
                onclick:function (a,b) {
                    dialog.close(true);
                }
            },
            {
                className:'edui-cancelbutton',
                label:'取消',
                onclick:function () {
                    dialog.close(false);
                }
            }
        ]});
   // editor.ui._dialogs[uiName]=dialog;
  
    //增加插入图片按钮
    var btn = new UE.ui.Button({
        name:'btn_' + uiName,
        title:'插入多张图片',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -726px 43px;',
        onclick:function () {
            //渲染dialog
            dialog.render();
            dialog.open();
        }
    });
    return btn;   
	
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);






//添加插入图片按钮
UE.registerUI('addImgOutDivDialog',function(editor,uiName){
  //创建dialog
  var dialog = new UE.ui.Dialog({
      //指定弹出层中页面的路径，这里只能支持页面,因为跟addCustomizeDialog.js相同目录，所以无需加路径
      //iframeUrl:base+'/user/article/manage/goods_list',
      iframeUrl:base+'/pub/photo/addPhotoPre?type=2',
      //需要指定当前的编辑器实例
      editor:editor,
      //指定dialog的名字
      name:uiName,
      //dialog的标题
      title:"图片管理",

      //指定dialog的外围样式
      cssRules:"width:700px;height:480px;",

      //如果给出了buttons就代表dialog有确定和取消
      buttons:[
          {
              className:'edui-okbutton',
              label:'确定',
              onclick:function (a,b) {
                  dialog.close(true);
              }
          },
          {
              className:'edui-cancelbutton',
              label:'取消',
              onclick:function () {
                  dialog.close(false);
              }
          }
      ]});
  editor.ui._dialogs[uiName]=dialog;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);







