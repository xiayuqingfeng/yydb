<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
<link rel="stylesheet" media="screen" href="${vendorsBase }/boostrap-datetime-picker/css/bootstrap-datetimepicker.min.css?v=${version}">
</head>
<body>
 <%--顶部 --%>
 <%@ include file="../index/index_top.jsp"%>
 <div class="admin-wrapper">
  <%--左边菜单 --%>
  <%@ include file="../index/index_left.jsp"%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">幸运大转盘</strong> / <small>Big Wheel</small>
   </div>
   <div class="admin-content-info admin-content-padding">
    <div class="am-container am-fl">
     <div class="am-u-lg-9" style="padding-left: 0; padding-right: 0;">
      <div class="wz-panel">
       <form action="/admin/whd/bigwheel/save" method="POST" enctype="multipart/form-data">
        <fieldset>
         <input type="hidden" value="${bigWheelInfo.id }" name="id">
         <c:if test="${not empty error }">
          <div class="form-group ">
           <p class="help-inline text-info"></p>
           <p class="label label-danger wz-flash-info">${error }</p>
          </div>
         </c:if>
         <div class="form-group ">
          <label for="title">活动标题</label> <input type="text" id="title" name="title" value="${bigWheelInfo.title }" placeholder="活动标题" class="form-control"
           autocomplete="off">
          <p class="help-inline text-info"></p>
         </div>
         <div class="form-group ">
          <label for="description">活动说明</label>
          <textarea id="description" name="description" placeholder="活动说明" class="form-control" autocomplete="off">${bigWheelInfo.description }</textarea>
          <p class="help-inline text-info"></p>
         </div>
         <div class="form-group ">
          <label for="imageFile">活动图片</label> <input type="file" id="imageFile" name="imageFile" accept="image/*" autocomplete="off">
          <p class="help-inline text-info">建议尺寸：720宽 X 400高</p>
         </div>
         <div>
          <a href='javascript:$("#imageFile").click();' id="image_preview"> <c:choose>
            <c:when test="${not empty bigWheelInfo.photoPath }">
             <img src="${uploadImageBase }/${bigWheelInfo.photoPath }" alt="" class="thumbnail" style="height: 100px;">
            </c:when>
            <c:otherwise>
             <img src="${mimeBase }/images/bigwheel.png" alt="" class="thumbnail" style="height: 100px;">
            </c:otherwise>
           </c:choose>
          </a>
         </div>
         <div class="form-group ">
          <label for="rule">活动规则</label>
          <script id="rule" name="activeRule" type="text/plain" style="width: 640px; height: 180px">${ElUtils.unescapeHtml3(bigWheelInfo.activeRule)}</script>
          <%--           <textarea id="rule" name="activeRule" placeholder="抽奖活动的游戏规则，玩法等。" class="form-control" autocomplete="off">${bigWheelInfo.activeRule }</textarea> --%>
         </div>
         <div class="form-actions">
          <input type="submit" class="am-btn hl-btn-green am-btn-sm" value="下一步" autocomplete="off"> <a class="am-btn am-btn-default am-btn-sm"
           href="/admin/whd/bigwheel#4001">返回</a>
         </div>
        </fieldset>
       </form>
      </div>
     </div>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/bootstrap-datetimepicker.min.js?v=${version}" type="text/javascript"></script>
 <script src="${vendorsBase }/boostrap-datetime-picker/js/locales/bootstrap-datetimepicker.zh-CN.js?v=${version}" type="text/javascript"></script>
 <%--编辑器 start--%>
 <script src="${vendorsBase }/icheck-1.0.2/icheck.js"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/ueditor.config.js?v=${version}"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/ueditor.all.min.js?v=${version}"></script>
 <script type="text/javascript" charset="utf-8" src="${vendorsBase }/ueeditor/lang/zh-cn/zh-cn.js?v=${version}"></script>
 <%--编辑器 end--%>
 <%--  <script src="${mimeBase}/scripts/admin/product/product_edit.js?v=${version}" type="text/javascript"></script> --%>
 <script type="text/javascript" src="${mimeBase }/scripts/pub/ueditor/addImgDialog.js?v=${version}">
      var editorObj="1";'
    </script>
 <%--选择图片弹出框 --%>
 <%@ include file="../photo/photo_select-dialog.jsp"%>
 <script type="text/javascript">
	UE.getEditor('rule', {
		allowDivTransToP : false,
		autoHeightEnabled : false
	});
		var BigWheelAddObject = {
			init : function() {
				//注册时间控件
				this._reg_datetime_events();
				//注册图片选择事件
				this._reg_file_input_event();
				//注册参与频次选择事件
				this._reg_join_frequency_type_select_event();
				//不限参与人数checkbox事件
				this._reg_user_un_limit_checkbox_event();
			},
			//注册时间控件
			_reg_datetime_events : function() {
				$('.wz-datetime').datetimepicker({
					language : "zh-CN",
					format : "yyyy-mm-dd hh:ii",
					autoclose : true,
					todayBtn : true,
					todayHighlight : true,
					minuteStep : 1
				});
			},
			//注册图片选择事件
			_reg_file_input_event : function() {
				$("#imageFile").change(function() {
					$("#image_preview").html('');
					var filesize = this.files.length;
					if (filesize > 1) {
						alert("只能选择一个图片!");
						$("#imageFile").prop('outerHTML', $("#imageFile").prop('outerHTML'));
						$("#imageFile").change();
					} else {
						var file = this.files[0];
						if (file) {
							if (512000 < file.size) {
								alert("文件大小不能超过: 500KB");
							} else {
								if (window.FileReader) {
									var fr = new FileReader();
									fr.onloadend = function(e) {
										$("#image_preview").append('<img src="'+e.target.result+'"  alt="" class="thumbnail" style="height: 100px;">');
									};
									fr.readAsDataURL(this.files[0]);
								}
							}
						}
					}
				});
			},
			//注册参与频次选择事件
			_reg_join_frequency_type_select_event : function() {
				//参与次数
				$("#joinFrequencyType").on("change", function() {
					if (this.value && this.value == 2) {
						$("#joinTimesPerPerson").parent().hide();
					} else {
						$("#joinTimesPerPerson").parent().show();
					}
				});
				$("#joinFrequencyType").change();
			},
			//不限参与人数checkbox事件
			_reg_user_un_limit_checkbox_event : function() {
				//不限参与人数checkbox事件
				$("#isUserUnLimit").on("change", function() {
					if ($(this).is(":checked")) {
						$("#userLimit").hide();
					} else {
						$("#userLimit").show();
					}
				});
			}

		};
		BigWheelAddObject.init();
	</script>
</body>
</html>