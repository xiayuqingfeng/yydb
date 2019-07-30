<%-- 奖品管理页面 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../commons/taglibs.jsp"%>
<title>${htmlTitleText}</title>
<%@ include file="../commons/commons_resource_head.jsp"%>
</head>
<body>
 <%--顶部 --%>
 <%@ include file="../index/index_top.jsp"%>
 <div class="admin-wrapper">
  <%--左边菜单 --%>
  <%@ include file="../index/index_left.jsp"%>
  <div class="admin-content" id="hl-admin-content">
   <div class="admin-content-title admin-content-padding">
    <strong class="am-text-lg am-padding-left-sm">幸运大转盘</strong> / <small>奖品管理</small>
   </div>
   <div class="admin-content-info admin-content-padding">
    <div class="am-container am-fl">
     <div class="am-u-lg-9" style="padding-left: 0; padding-right: 0;">
      <div class="wz-panel">
       <form action="/admin/whd/bigwheel/${bigWheelId}/prize/save" method="POST" enctype="multipart/form-data">
        <fieldset>
         <input type="hidden" value="${bigWheelPrize.id }" name="id">
         <c:if test="${not empty error }">
          <div class="form-group ">
           <p class="help-inline text-info"></p>
           <p class="label label-danger wz-flash-info">${error }</p>
          </div>
         </c:if>
         <div class="form-group ">
          <label for="grade">奖项</label> <select id="grade" name="prizeLevel" class="form-control" autocomplete="off">
           <option value="1" <c:if test="${bigWheelPrize.prizeLevel==1}" >selected="selected"</c:if>>一等奖</option>
           <option value="2" <c:if test="${bigWheelPrize.prizeLevel==2}" >selected="selected"</c:if>>二等奖</option>
           <option value="3" <c:if test="${bigWheelPrize.prizeLevel==3}" >selected="selected"</c:if>>三等奖</option>
           <option value="4" <c:if test="${bigWheelPrize.prizeLevel==4}" >selected="selected"</c:if>>四等奖</option>
           <option value="5" <c:if test="${bigWheelPrize.prizeLevel==5}" >selected="selected"</c:if>>五等奖</option>
           <option value="6" <c:if test="${bigWheelPrize.prizeLevel==6}" >selected="selected"</c:if>>六等奖</option>
           <option value="7" <c:if test="${bigWheelPrize.prizeLevel==7}" >selected="selected"</c:if>>七等奖</option>
          </select>
          <p class="help-inline text-info"></p>
         </div>
         <div class="form-group ">
          <label for="name">自定义奖项名称(不超过4个字)</label> <input type="text" id="name" name="prizeName" value="${bigWheelPrize.prizeName }" placeholder="自定义奖项名称，4个字以内，如：鼓励奖、幸运奖等"
           class="form-control" autocomplete="off">
          <p class="help-inline text-info"></p>
         </div>
         <div id="valid_list" class="form-group">
          <label for="text_valid_list">奖品类型</label> <select id="text_valid_list" name="prizeType" placeholder="" class="form-control" autocomplete="off">
           <option value="0" <c:if test="${bigWheelPrize.prizeType==0 }">selected</c:if>>积分</option>
           <option value="1" <c:if test="${bigWheelPrize.prizeType!=0 }">selected</c:if>>实物</option>
          </select>
          <p class="help-inline text-info"></p>
         </div>
         <div class="form-group <c:if test="${bigWheelPrize.prizeType==0 }">hide</c:if> prizeContentSelect">
          <label for="content">奖品内容</label> <input type="text" id="content" name="prizeContent" value="${bigWheelPrize.prizeContent }" placeholder="奖品内容，如：iPhone6"
           class="form-control hasPrize" autocomplete="off">
          <p class="help-inline text-info"></p>
         </div>
         <div class="form-group <c:if test="${bigWheelPrize.prizeType!=0 }">hide</c:if> prizeScoreSelect">
          <label for="content">积分</label> <input type="text" id="prizeScore" name="prizeScore" value="${bigWheelPrize.prizeScore }" placeholder="积分数值"
           class="form-control hasPrize" autocomplete="off" onkeyup="value=value.replace(/[^\d]/g,'') "
           onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
          <p class="help-inline text-info"></p>
         </div>
         <div class="form-group ">
          <label for="imageFile">奖品图片</label> <input type="file" id="imageFile" name="imageFile" accept="image/*" autocomplete="off">
          <p class="help-inline text-info">建议尺寸：200宽 X 200高</p>
         </div>
         <div>
          <a href='javascript:$("#imageFile").click();' id="image_preview"> <c:choose>
            <c:when test="${not empty bigWheelPrize.photoPath }">
             <img src="${uploadImageBase }/${bigWheelPrize.photoPath }" alt="" class="thumbnail" style="height: 100px;">
            </c:when>
            <c:otherwise>
             <img src="${mimeBase }/images/nophoto.png" alt="" class="thumbnail" style="height: 100px;">
            </c:otherwise>
           </c:choose>
          </a>
         </div>
         <div class="form-group ">
          <label for="count">奖品数量</label> <input type="text" id="count" name="prizeCount" value="${bigWheelPrize.prizeCount}" placeholder="奖品数量"
           class="form-control hasPrize" autocomplete="off">
          <p class="help-inline text-info">大于零的整数</p>
         </div>
         <div class="form-group ">
          <label for="chance">中奖概率(%)</label> <input type="text" id="chance" name="prizeRate" value="${bigWheelPrize.prizeRate}" placeholder="中奖概率，百分比"
           class="form-control hasPrize" autocomplete="off">
          <p class="help-inline text-info">1-100的任意整数，所有奖项概率之和不能超过100(一等奖可以设置为0 不可抽中)</p>
         </div>
        </fieldset>
        <div class="form-actions">
         <input type="submit" class="am-btn hl-btn-green am-btn-sm" value="保存" autocomplete="off"> <a class="am-btn am-btn-default am-btn-sm"
          href="javascript:history.go(-1)">返回</a>
        </div>
       </form>
      </div>
     </div>
    </div>
   </div>
  </div>
 </div>
 <%@ include file="../index/index_sm_foot.jsp"%>
 <%@ include file="../commons/commons_resource_foot.jsp"%>
 <script type="text/javascript">
		$("#text_valid_list").change(function() {
			// 3、获取当前选中项的value
			if ($("#text_valid_list").val() != 0) {
				$(".prizeScoreSelect").addClass("hide");
				$(".prizeContentSelect").removeClass("hide");
			} else {
				$(".prizeContentSelect").addClass("hide");
				$(".prizeScoreSelect").removeClass("hide");
			}
		});
		if ($("#grade").val() == 0) {
			$("#grid .hasPrize").parent().hide();
		} else {
			$("#grid .hasPrize").parent().show();
		}
		var gradeChange = function() {
			if ($("#grade").val() == 0) {
				$("#name").val("谢谢参与");
				$("#grid .hasPrize").parent().hide();
				$("#count").val(-1);
			} else {
				$("#name").val($("#grade").find("option:selected").text());
				$("#grid .hasPrize").parent().show();
				$("#fetchType").change();
				if ($("#count").val() == -1) {
					$("#count").val(1);
				}
			}
		}
		$("#grade").on("change", function() {
			gradeChange();
		});
		if (!$("#name").val()) {
			$("#grade").change();
		}
		//领奖方式事件
		var fetchTypeChange = function() {
			if ($("#fetchType").val() == "1") {
				$("#fetchLink").parent().hide();
				$("#pickupAddress").parent().hide();
				$("#coupon").parent().show();
			} else if ($("#fetchType").val() == "2") {
				$("#fetchLink").parent().show();
				$("#pickupAddress").parent().hide();
				$("#coupon").parent().hide();
			} else if ($("#fetchType").val() == "3") {
				$("#fetchLink").parent().hide();
				$("#pickupAddress").parent().show();
				$("#coupon").parent().hide();
			} else {
				$("#fetchLink").parent().hide();
				$("#coupon").parent().hide();
				$("#pickupAddress").parent().hide();
			}
		}
		$("#fetchType").on("change", function() {
			fetchTypeChange();
		});
		$("#fetchType").change();
		//上传文件大小控制
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
								$("#image_preview").append('<img src="'+e.target.result+'"  alt="" class="thumbnail" style="height: 80px;">');
							};
							fr.readAsDataURL(this.files[0]);
						}
					}
				}
			}
		});
	</script>
</body>
</html>