var areaObject = {
	init : function() {
		/** 省市县切换事件 */
		this._reg_area_select_event();
		/** 加载省份事件 */
		this.getArea("all", $('#pf-select-area1'));
		/** 加载搜索页省份 */
		// this.getArea("all", $('#pf-select-area2'));
		/** 加载搜索页省份 */
		// this.getArea("all", $("#pf-select-area3"));
		// 选中省份
		$('#pf-select-area1').val(provinceId);
		// 加载并选中城市
		this.getArea(provinceId, $('#pf-select-area2'));
		// 加载并选中区域
		this.getArea(cityId, $('#pf-select-area3'));
	},
	// 注册区域事件
	_reg_area_select_event : function() {
		// 添加页省变化
		$('#pf-select-area1').change(function() {
			$('#pf-select-area2').html("");
			$('#pf-select-area3').html("");
			areaObject.getArea($(this).val(), $('#pf-select-area2'));
			if ($("#provinceName") != undefined) {
				$("#provinceName").val($(this).find("option:selected").text());
			}
		});
		// 添加页市变化
		$('#pf-select-area2').change(function() {
			$('#pf-select-area3').html("");
			areaObject.getArea($(this).val(), $('#pf-select-area3'));
			if ($("#cityName") != undefined) {
				$("#cityName").val($(this).find("option:selected").text());
			}
		});
		// 添加区变化
		$('#pf-select-area3').change(function() {
			if ($("#areaName") != undefined) {
				$("#areaName").val($(this).find("option:selected").text());
			}
		});
	},
	/**
	 * 获取区域列表 selected：是否选中
	 */
	getArea : function(parentId, $areaSelect) {
		if (parentId == "") {
			return;
		}
		$areaSelect.empty();
		// 加载省份
		if (parentId == "all") {
			parentId = "";
			$areaSelect.append($("<option>").val("").text("请选择"));
		} else {
			$areaSelect.append($("<option>").val("").text("请选择"));
		}
		$.ajax({
			type : "GET",
			cache : false,
			async : false, // 太关键了，同步和异步的参数
			data : {
				parentId : parentId
			},
			dataType : "json",
			url : "/area/getChilds.json",
			success : function(data) {
				if (data.isSuccess == true) {
					var areas = data.message;
					for ( var i in areas) {
						var area = areas[i];
						var option = $("<option>").val(area.id).text(area.areaName);
						$areaSelect.append(option);
					}
					if ($areaSelect.attr("areaId") != "") {
						$areaSelect.val($areaSelect.attr("areaId"));
					}
				} else {
					alert(data.message);
				}
			}
		});
	},
	exit : function() {

	}
}
areaObject.init();