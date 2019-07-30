<%--充值弹出框 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal fade" tabindex="-1" role="dialog" id="pf-modal-select-photo">
 <div class="modal-dialog" role="document">
  <div class="modal-content" style="width: 850px;">
   <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
     <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">充值</h4>
   </div>
   <div class="modal-body" style="padding: 1px">
    <div class="wz-panel">
     <iframe style="width: 845px; height: 420px" id="iframe_select_photo" frameborder="0" src=""></iframe>
    </div>
   </div>
   <div class="modal-footer">
    <button type="button" class="am-btn am-btn-sm" data-dismiss="modal">关闭</button>
   </div>
  </div>
 </div>
</div>
<script type="text/javascript">
	/** 注册弹出选择图片窗口 */
	$(document).on("click", ".newAccountAmount", function() {
		$("#iframe_select_photo").attr("src", $(this).attr("_href"));
		$("#pf-modal-select-photo").modal('show');
	});
	$("#pf-modal-select-photo").on("hidden.bs.modal", function() {
		$("#pf-table-members").bootstrapTable("refresh");
	});
</script>