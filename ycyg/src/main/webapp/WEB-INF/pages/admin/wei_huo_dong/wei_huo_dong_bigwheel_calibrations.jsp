<%--转盘刻度管理页面 --%>
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
                <strong class="am-text-lg am-padding-left-sm">幸运大转盘</strong>
                /
                <small>转盘维护</small>
            </div>
            <div class="admin-content-info admin-content-padding">
                <div class="am-container am-fl">
                    <div class="am-u-lg-9" style="padding-left: 0; padding-right: 0;">
                        <div class="wz-panel">
                            <form action="/admin/whd/bigwheel/${bigWheelId}/calibrations/save" method="POST">
                                <fieldset>
                                    <div class="form-group ">
                                        <label for="prize1">转盘1</label>
                                        <select id="prize1" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[0]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize2">转盘2</label>
                                        <select id="prize2" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[1]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize3">转盘3</label>
                                        <select id="prize3" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[2]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize4">转盘4</label>
                                        <select id="prize4" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[3]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize5">转盘5</label>
                                        <select id="prize5" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[4]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize6">转盘6</label>
                                        <select id="prize6" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[5]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize7">转盘7</label>
                                        <select id="prize7" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[6]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                    <div class="form-group ">
                                        <label for="prize8">转盘8</label>
                                        <select id="prize8" name="ids" class="form-control" autocomplete="off">
                                            <option value="-1" selected="selected">谢谢参与</option>
                                            <c:forEach var="item" items="${bigWheelPrizes }">
                                                <option value="${item.id }" <c:if test="${wheelCalibrations[7]==item.id }">selected="selected"</c:if>>${item.prizeName }</option>
                                            </c:forEach>
                                        </select>
                                        <p class="help-inline text-info"></p>
                                    </div>
                                </fieldset>
                                <div class="form-actions">
                                    <input type="submit" class="am-btn hl-btn-green am-btn-sm" value="保存" autocomplete="off">
                                    <a class="am-btn am-btn-primary am-btn-sm" href="/admin/whd/bigwheel/${bigWheelId }/prize">上一步</a>
                                    <a class="am-btn am-btn-default am-btn-sm" href="/admin/whd/bigwheel#4001">返回</a>
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
</body>
</html>