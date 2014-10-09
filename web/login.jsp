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
  <form method="POST" action="doLogin.action">
    <p>
      <input type="radio" value="kermit" name="R1" checked>
      kermit（经理）</p>
    <p>
      <input type="radio" value="gonzo" name="R1">
      gonzo（销售员工）</p>
    <p>
      <input type="radio" value="fozzie" name="R1">
      fozzie（工程师）</p>
    <p>
      <input type="submit" value="提交" name="B1">
      <input type="reset" value="重置" name="B2">
    </p>
  </form>
  <!-- InstanceEndEditable --> </div>
</body>
<!-- InstanceEnd -->
</html>
