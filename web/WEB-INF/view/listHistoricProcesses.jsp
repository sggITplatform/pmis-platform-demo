<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"><!-- InstanceBegin template="/Templates/template.dwt.jsp" codeOutsideHTMLIsLocked="false" -->
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap-3.2.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="${pageContext.request.contextPath}/resources/jquery-1.11.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${pageContext.request.contextPath}/resources/bootstrap-3.2.0/js/bootstrap.min.js"></script>

<!-- InstanceBeginEditable name="head" --><!-- InstanceEndEditable -->
</head>
<body>
<%@ include file="/WEB-INF/view/_header.jsp" %>
<div style="margin: 10pt;">
<!-- InstanceBeginEditable name="EditRegion2" -->
<div>
<table class="table table-condensed">
	<tr>
		<td>ID</td>
		<td>发起人</td>
		<td>启动时间</td>
		<c:if test="${!listActiveProcesses}">
		<td>完成时间</td>
		</c:if>
		<td>业务ID</td>
		<td>流程定义ID</td>
		<td></td>
	</tr>
<c:forEach items="${processes}" var="process">
	<tr>
		<td>${process.id}</td>
		<td>${process.startUserId}</td>
		<td>${process.startTime}</td>
		<c:if test="${!listActiveProcesses}">
		<td>${process.endTime}</td>
		</c:if>
		<td>${process.businessKey}</td>
		<td>${process.processDefinitionId}</td>
		<td>
		<c:if test="${listActiveProcesses}">
		<a target="_blank" href="../diagram-viewer/index.html?processDefinitionId=${process.processDefinitionId}&processInstanceId=${process.id}"  data-target="#modal1_${process.id}" class="btn btn-primary">查看状态图</a>
		</c:if>
		<a data-toggle="modal" href="${pageContext.request.contextPath}/workflow/listProcessVariables.action?processId=${process.id}&historic=${!listActiveProcesses}" data-target="#modal2_${process.id}" class="btn btn-primary">查看变量</a>
		<a href="${pageContext.request.contextPath}/workflow/listHistoricActivities.action?processId=${process.id}" target="_blank" class="btn btn-primary">查看历史活动</a>
<div id="modal1_${process.id}" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="modal2_${process.id}" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->				
		</td>
	</tr>
</c:forEach>
</table>
</div>
<!-- InstanceEndEditable -->
</div>
</body>
<!-- InstanceEnd --></html>