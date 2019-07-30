http://layer.layui.com/

var index =layer.msg('确定删除成员：' + $tr.data("data").loginname, {
					time : 0, 
					icon : 3,//0-6，0：警告（感叹号），1：成功（打勾），2：错误（打叉），3：问号，4：锁头，5：错误（表情），6：成功（笑脸）
					shade : 0.3, //背景透明度
					btn : [ '确定', '取消' ],
					yes : function(index) {
						layer.close(index);
						alert("确定");
					}
				});
				
				
//关闭弹窗
layer.close(index);