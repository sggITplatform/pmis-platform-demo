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
<div class="hero-unit">
  <h1>PMIS-platform 0.9 </h1>
  <p>本应用演示PMIS-platform 0.9的相关功能，其中内容包括：</p>
  <ul>
    <li>如何使用OpenWebFlow（基于Activiti扩展形成的Web工作流引擎，系本平台原创）和Spring MVC处理业务流程，使用OpenWebFlow可以采取两种方式实现自定义业务的逻辑：
      <ul>
        <li>编写完全独立的Controller（OpenWebFlow提供Helper），参见demo.mvc.MyController；</li>
        <li>编写指定类型的事件处理器（OpenWebFlow处理好外围事件），参见demo.mvc.MyEventHandler；</li>
      </ul>
    </li>
    <li>如何使用Spring Security进行访问控制； </li>
    <li>如何连接远程OAuth服务进行登录；</li>
    <li>如何使用甘特图组件；</li>
    <li>如何使用图表组件；</li>
  </ul>
  <p>PMIS-platform 0.9包括如下组件：</p>
  <ul>
    <li>工作流OpenWebFlow <a href="./docs/javadoc/index.html?pmis/webflow/mvc/ext/EventContextHolder.html" target="_blank" class="btn-primary">查看Java API</a> </li>
    <li>commons组件 <a href="docs/javadoc/index.html" target="_blank" class="btn-primary">查看Java API</a> </li>
    <li>图表charts组件 <a href="./bar.html" target="_blank" class="btn-warning">柱状图示例</a> <a href="./pie.html" target="_blank" class="btn-warning">饼图示例</a> <a href="http://echarts.baidu.com/doc/example.html" target="_blank" class="btn-warning">更多示例</a></li>
    <li>报表组件 <a href="./report?name=testDoc&type=pdf" target="_blank" class="btn-warning">查看示例1</a> <a href="./report?name=testDoc&type=doc" target="_blank" class="btn-warning">查看示例2</a> <a href="docs/javadoc/index.html?pmis/commons/reports/ReportUtils.html" target="_blank" class="btn-primary">查看Java API</a></li>
    <li>校验码生成组件 <a href="./kaptcha.jpg" target="_blank" class="btn-warning">查看示例</a></li>
    <li>甘特图组件 <a href="./gantt.htm" target="_blank" class="btn-warning">查看示例</a></li>
  </ul>
  <p>&nbsp;</p>
  <p>OpenWebFlow的部分功能演示需要登录，点击此处登录--&gt;<a class="btn btn-danger" href="login.jsp">现在登录！</a></p>
</div>
<!-- InstanceEndEditable -->
</div>
</body>
<!-- InstanceEnd --></html>