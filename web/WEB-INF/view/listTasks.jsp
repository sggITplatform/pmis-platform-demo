<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html><html class="no-js"><!-- InstanceBegin template="/Templates/template.dwt.jsp" codeOutsideHTMLIsLocked="false" -->
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
		<td>名称</td>
		<td>流程ID</td>
		<td>指定办理人</td>
		<td>发起时间</td>
		<td>摘要内容</td>
		<td>操作</td>
	</tr>
<c:forEach items="${tasks}" var="task">
	<tr>
		<td>${task.id}</td>
		<td>${task.name}</td>
		<td>${task.processInstanceId}</td>
		<td>${task.assignee}</td>
		<td>${task.createTime}</td>
		<td>${task.description}</td>
		<td>
		<c:if test="${empty task.assignee}"><a class="btn btn-warning" href="claimTask.action?taskId=${task.id}">签收</a></c:if>
		<c:if test="${not empty task.assignee}"><a class="btn btn-warning" href="completeTaskForm.action?taskId=${task.id}">办理</a></c:if>
		</td>
	</tr>
</c:forEach>
</table>
</div>
<!-- InstanceEndEditable -->
</div>
</body>
<!-- InstanceEnd --></html>