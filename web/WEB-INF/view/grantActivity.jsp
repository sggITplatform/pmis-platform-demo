<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html class="no-js">
<!-- InstanceBegin template="/Templates/template.dwt.jsp" codeOutsideHTMLIsLocked="false" -->
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap-3.2.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="${pageContext.request.contextPath}/resources/jquery-1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${pageContext.request.contextPath}/resources/bootstrap-3.2.0/js/bootstrap.min.js"></script>
<!-- InstanceBeginEditable name="head" -->
<style type="text/css">
<!--
.STYLE100 {
	color: #FF0000;
	font-weight: bold;
	font-size: 24pt;
}
-->
</style>
<!-- InstanceEndEditable -->
</head>
<body>
<%@ include file="/WEB-INF/view/_header.jsp" %>
<div style="margin: 10pt;"> <!-- InstanceBeginEditable name="EditRegion2" -->
  <p class="STYLE100">设置活动的执行权限，注意对已创建的任务无效~~</p>
  <form method="POST" action="${pageContext.request.contextPath}/doGrantActivity.action">
    <p> <span lang="zh-cn">所属流程：</span>
      <input type="text" name="processDefId" size="24" value="${processDefinition.id}">
      ${processDefinition.name}<br>
      <span lang="zh-cn">ID：</span>
      <input type="text" name="activityId" size="24" value="${activity.id}">
      <br>
      <span lang="zh-cn">NAME： ${activity.getProperty("name")}</span><br>
      <span lang="zh-cn">TYPE： ${activity.getProperty("type")}</span></p>
    <p><span lang="zh-cn">直接指定用户：</span>
      <input type="text" name="assigneeExpression" size="24" value="${assignee}">
      <br>
      <span lang="zh-cn">可分配到组：</span>
      <input type="text" name="candidateGroupIdExpressions" size="24" value="${candidateGroupIds}">
      （多个组ID之间采用分号分隔）<br>
      <span lang="zh-cn">可分配到用户：</span>
      <input type="text" name="candidateUserIdExpressions" size="24" value="${candidateUserIds}">
    （多个组用户D之间采用分号分隔）</p>
    <p>
      <input type="submit" value="提交" name="B1">
      <input type="reset" value="重置" name="B2">
    </p>
  </form>
  <!-- InstanceEndEditable --></div>
</body>
<!-- InstanceEnd -->
</html>
