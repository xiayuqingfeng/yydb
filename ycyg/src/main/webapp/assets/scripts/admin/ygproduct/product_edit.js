/**
 * 商品编辑
 */
var ProductAddObject = {
	/** 初始化方法 */
	init : function() {
		// 注册签单产品选择下拉框
		this._reg_change_product_event(); 
		// 保存事件*/
		this._reg_save_btn_event();
		// 注册checkbox
		this._reg_icheckbox(); 
	},
	/** 注册多选框 */
	_reg_icheckbox : function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-orange',
			radioClass : 'iradio_square-orange',
			increaseArea : '20%' // optional
		});
	},
	/** 注册签单云购商品产品选择下拉框 */
	_reg_change_product_event : function() {
		// 获取云购商品 对应的id
		$("#search").keyup(function() {
			if ($(this).val() == "" || $(this).val() == undefined) {
				return false;
			}
			var keyWord = $(this).val();
			var $this = $(this);
			var myReg = /^[\u4e00-\u9fa5]+$/; // 判断汉字的正则表达式
			if (myReg.test(keyWord)) { // 输入的是汉字
				// ajax调用
				$.ajax({
					data : {
						name : $this.val()
					},
					url : "/admin/ygproduct/product.json",
					cache : false,
					dataType : "json",
					success : function(data) {
						if (data.isSuccess == true) {
							var product = new Array();
							product = data.message;
							if (product.length > 0) {
								$(".tauto_xiala").html("");
								for (i = 0; i < product.length; i++) {
									// data.result[i].name;
									// data.result[i].id;
									// <div class="tauto_xiala_1
									// hidden">选择所属主险</div>
									var tauto = '<div class="tauto_xiala_1" productId="' + product[i].id + '">' + product[i].name + '</div>';
									$(".tauto_xiala").append(tauto);
								}
								$("#productId").val("");
								$(".tauto_xiala").removeClass("hidden");
							}
						} else {
							alert(data.message);
						}
					}
				});
			}
		});

		// 选择
		$(document).on("click", ".tauto_xiala_1", function() {
			var productId = $(this).attr("productId");
			$("#insuranceTypeId").val($(this).html());
			$("#productId").val(productId);
			$(".tauto_xiala").addClass("hidden");
			// 选择产品--叠加商品数据 使用模板 增加进去
			$.ajax({
				url : "/admin/ygproduct/addProduct/" + productId,
				cache : false,
				dataType : "json",
				success : function(data) {
					// 将商品数据加载到页面
					if (data.isSuccess == true) {
						var product = data.message;
						
					} else {
						alert(data.message);
					}
				}
			});
		});

		// 云购商品id
		$(document).on("mouseenter", ".tauto_xiala_1", function() {
			// $(this).css("background-color", "yellow");
		});
		$(document).on("mouseleave", ".tauto_xiala_1", function() {
			// $(this).css("background-color", "pink");
		});

	}, 
	/** 注册保存事件 */
	_reg_save_btn_event : function() {
		// 保存并使用按钮
		$("#btn-save-product,#btn-publish-product").click(function() {
			// 标题
			var name = $("#name").val();
			if (name == "") {
				$('#p-Tabs a:first').tab('show');
				showPopup("名称不能为空!");
				$("#name").focus();
				return false;
			}
			// 排序号
			var seqNo = $("#seqNo").val();
			if (seqNo == "") {
				$("#seqNo").val(50);
				return false;
			}
			// 商品图片
			var xxtkDatas = [];
			$("#ul_xjtk li").each(function() {
				var a = $(this).find(".link");
				var data = JSON.parse(a.attr("item-data"));
				xxtkDatas.push({
					"name" : data.name,
					"path" : data.path,
					"info" : a.text()
				})
			});
			$("#photosFile").val(JSON.stringify(xxtkDatas));
			// alert($("#saveForm").serialize());return false;
			// $("#content").val(UE.getEditor('editorZrmc').getContent());
			// $("#tbalContent").val(UE.getEditor('editorTbal').getContent());
			$("#saveForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if (data != undefined) {
						if (data.isSuccess == true) {
							showPopup("保存成功!");
							window.setTimeout(function() {
								$('#goback').click();
							}, 1000);
						} else {
							showPopup(data.message);
						}
					} else {
						showPopup("保存失败!");
					}

				}
			});

		});

	} 
}

ProductAddObject.init();