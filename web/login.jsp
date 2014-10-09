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
.STYLE112 {font-weight: bold;color: #FF0000;font-size: larger}
-->
</style>
<!-- InstanceEndEditable -->
</head>
<body>
<%@ include file="/WEB-INF/view/_header.jsp" %>
<div style="margin: 10pt;"> <!-- InstanceBeginEditable name="EditRegion2" -->
  <c:if test="${param.denied}"> <span class="STYLE112">访问当前模块，请先登录！</span></c:if>
  	<p><font size="4">注意：您正在使用本地的登录服务！</font> </p>
  <form method="POST" action="j_spring_security_check">
    <p>
      用户名：<input type="text" name="j_username" size="20" value="kermit"></p>
	<p>
      密码：<input type="password" name="j_password" size="20" value="kermit"></p>
	<p>
      可用的账号：kermit（经理）、gonzo（销售员工）、fozzie（工程师）</p>
    <p>
      <input type="submit" value="提交" name="B1">
      <input type="reset" value="重置" name="B2">
    </p>
    <hr>
    <p>采用统一认证服务器登录：<a href="./oauth/sparklr" class="btn btn-primary">连接PMIS平台认证服务器</a>
	<a href="./oauth/gmail" class="btn btn-warning">连接Gmail服务器</a>
	<a href="./oauth/facebook" class="btn btn-danger">连接Facebook服务器</a>
	</p>
    </form>
  <!-- InstanceEndEditable --> </div>
</body>
<!-- InstanceEnd -->
</html>