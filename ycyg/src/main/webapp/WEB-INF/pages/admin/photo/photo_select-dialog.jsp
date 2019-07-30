
<%--选择图片弹出框 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal fade" tabindex="-1" role="dialog" id="pf-modal-select-photo">
 <div class="modal-dialog" role="document">
  <div class="modal-content" style="width: 680px;">
   <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
     <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">选择图片</h4>
   </div>
   <div class="modal-body" style="padding: 1px">
    <div class="wz-panel">
     <iframe style="width: 675px; height: 420px" id="iframe_select_photo" frameborder="0" src="/admin/photo/select?singleSelect=${singleSelect}"></iframe>
    </div>
   </div>
   <div class="modal-footer">
    <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
    <button type="button" class="am-btn hl-btn-green am-btn-sm ladda-button" data-style="expand-left" id="btn-select-photo">
     <span class="ladda-label">选择图片</span>
     <span class="ladda-spinner"></span>
    </button>
   </div>
  </div>
 </div>
</div>
<script type="text/javascript">
var singleSelect=true;
<c:if test="${singleSelect eq 'false'}">
singleSelect=false;
</c:if>
	/** 注册弹出选择图片窗口 */
	$(".btn-dialog-photo").click(function() {
		$("#pf-modal-select-photo").modal('show');
		//返回id
		$("#btn-select-photo").attr("tid", $(this).attr("tid"));
	});
	/** 注册选择图片窗口 */
	$("#btn-select-photo").click(
		function() {
			var photos = $("#iframe_select_photo")[0].contentWindow.PhotoObject
					.getSelectedPhotos();
			if (photos.length == 0) {
				showPopup("请选择图片!");
				return false;
			} 
            if (singleSelect && photos.length > 1) {
				showPopup("只能选择一张图片!");
				return false;
			}
			$("#" + $(this).attr("tid")).val(photos[0].photoPath);
			$("#" + $(this).attr("tid")).change();
			$("#pf-modal-select-photo").modal('hide');
		});
</script>