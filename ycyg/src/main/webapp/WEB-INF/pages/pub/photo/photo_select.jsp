
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../admin/commons/commons_resource_head.jsp"%>

<div class="select_photo_tool">
	<!-- <label>个人相册：</label> -->
  <form id="uploadForm" method="post" enctype="multipart/form-data" action="${base}/pub/photo/uploadFile">
 
     <select id="albumId" class="form-control"  onchange="reLoadIconListPage(1)"  style="width: 200px;">
       <option value="">默认相册</option>
       <c:forEach var="item" items="${ablums}">
          <option <c:if test="${item.id eq param.albumId }">selected</c:if> value="${item.id}">${ item.displayName}</option>
         </c:forEach>
      </select>

        <div style="float:left">上传：</div>
        <div style="float:left"><input type="file" multiple accept="image/*" name="file" id="fileId" style="display:;width:73px"/></div>
 </form>
</div>
<div class="select_photo_content">
	<c:if test="${empty page.result}">
			<div class="goods_infr"><div style="color:red;font-size:16px;margin:10px">没找到相关记录！</div>	</div>
	</c:if>
	
	<ul class="hover-ul-a" id="layer-photos" style="padding-left:1em">						
		<c:forEach items="${page.result}" var="item">	
					<li> 
							<img onclick="selectPhoto.usePhoto(this)" layer-src="${uploadImageBase}/${item.path}/${item.name}" src="${uploadImageBase}/${item.path}/${item.name}" title="图片"> 
					</li>
		</c:forEach> 
	</ul>					
</div>
<div style="clear: both;"></div>
<br>
<div class="am-cf" style="margin-bottom: 10px;">
    <div class="am-fl">&nbsp;共${page.totalItems }张图片</div>
    <div class="am-fr">
        <ul class="pager" style="margin: 0px;">
            <c:choose>
                <c:when test="${page.pageNo>1}">
                    <li>
                        <a href="javascript:reLoadIconListPage(${page.prePage})">上一页</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="disabled">
                        <a>上一页</a>
                    </li>
                </c:otherwise>
            </c:choose>
            <span>${page.pageNo}/${page.totalPages }</span>
            <c:choose>
                <c:when test="${page.pageNo<page.totalPages}">
                    <li>
                        <a href="javascript:reLoadIconListPage(${page.nextPage})">下一页</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="disabled">
                        <a>下一页</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<script type="text/javascript" src="${mimeBase}/scripts/pub/ueditor/photo_select.js?v=${version}"></script> 
<script type="text/javascript">
function reLoadIconListPage(pageNo) {
     jQuery.post("/pub/photo/select",{"pageNo": pageNo,"albumId": $("#albumId").val()}, function(data) {
        $("#selectPhoto").html(data);
    });
}
</script>