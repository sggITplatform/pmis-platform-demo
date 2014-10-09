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

<p>${processDef.name}</p>
<p>${processDef.description}</p>
<form method="POST" action="${pageContext.request.contextPath}//workflow/doStartProcess.action">
	<p>
	ID<span lang="zh-cn">：</span><input type="text" name="processDefId" size="24" value="${processDef.id}"></p>
	<p>
	申请人：<input type="text" name="var_employeeName" size="14" value="${pageContext.request.userPrincipal.name}"></p>
	<p>
	请假天数：<input type="text" name="var_numberOfDays" size="14"></p>
	<p>原因：<textarea rows="3" name="var_vacationMotivation" cols="20"></textarea></p>
	<p><input type="submit" value="提交" name="B1"><input type="reset" value="重置" name="B2"></p>
</form>

<!-- InstanceEndEditable -->
</div>
</body>
<!-- InstanceEnd --></html>