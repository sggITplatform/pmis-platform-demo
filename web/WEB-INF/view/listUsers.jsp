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
		<td>用户名</td>
		<td>密码</td>
	  </tr>
<c:forEach items="${users}" var="user">
	<tr>
		<td>${user.name}</td>
		<td>${user.password}</td>
		</tr>
</c:forEach>
</table>
</div>
<!-- InstanceEndEditable -->
</div>
</body>
<!-- InstanceEnd --></html>