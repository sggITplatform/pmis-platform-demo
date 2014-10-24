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
    <li>如何使用OpenWebFlow（基于Activiti扩展形成的Web工作流引擎，系本平台原创）和Spring MVC处理业务流程，OpenWebFlow演示如下功能：
      <ul>
        <li><b>完全非侵入式开发方式：</b>开发者可以像往常一样编写Controller，如果确实需要使用OpenWebFlow的功能，则可以在方法参数中通过注解的方式得到ContextTool，参见<a href="https://github.com/bluejoe2008/openwebflow/blob/master/src/test/java/demo/mvc/MyExtController.java" target="_blank" class="btn-primary">demo.mvc.MyExtController</a></li>
        <li><b>事件模式：</b>由OpenWebFlow负责流转，客户程序只需要编写指定类型的事件处理器（如：StartProcessEvent），参见<a href="https://github.com/bluejoe2008/openwebflow/blob/master/src/test/java/demo/mvc/MyEventHandler.java" target="_blank" class="btn-primary">demo.mvc.MyEventHandler</a>；</li>
        <li>此外，OpenWebFlow还负责BPMN模型（如：<a href="https://github.com/sggITplatform/pmis-platform-demo/blob/master/web/WEB-INF/models/vacation.png" target="_blank" class="btn-primary">请假流程模型</a>）的自动
		部署，所有的BPMN文件放置在<a href="https://github.com/sggITplatform/pmis-platform-demo/tree/master/web/WEB-INF/models" target="_blank" class="btn-primary">/WEB-INF/models</a>目录下；</li>
      </ul>
    </li>
    <li>如何使用Spring Security进行访问控制（参见<a href="https://github.com/sggITplatform/pmis-platform-demo/blob/master/web/WEB-INF/security.xml" target="_blank" class="btn-primary">security.xml</a>）； </li>
    <li>如何连接远程OAuth服务进行登录控制（参见<a href="https://github.com/sggITplatform/pmis-platform-demo/blob/master/web/WEB-INF/oauth-servlet.xml" target="_blank" class="btn-primary">oauth.xml</a>）；</li>
    <li>如何使用甘特图组件；</li>
    <li>如何使用图表组件；</li>
  </ul>
  <p>PMIS-platform 0.9包括如下组件：</p>
  <ul>
    <li>工作流OpenWebFlow <a href="https://github.com/bluejoe2008/openwebflow/tree/master/src/main/java/org/openwebflow" target="_blank" class="btn-primary">查看源码</a> <a href="./docs/openwebflow-javadoc/index.html" target="_blank" class="btn-primary">查看Java API</a> </li>
    <li>commons组件 <a href="https://github.com/sggITplatform/pmis-platform/tree/master/src/main/java/pmis/commons" target="_blank" class="btn-primary">查看源码</a> <a href="docs/platform-javadoc/index.html" target="_blank" class="btn-primary">查看Java API</a> </li>
    <li>图表charts组件 <a href="./bar.html" target="_blank" class="btn-warning">柱状图示例</a> <a href="./pie.html" target="_blank" class="btn-warning">饼图示例</a> <a href="http://echarts.baidu.com/doc/example.html" target="_blank" class="btn-warning">更多示例</a></li>
    <li>报表组件 <a href="./report?type=pdf" target="_blank" class="btn-warning">查看PDF输出示例</a> <a href="./report?type=doc" target="_blank" class="btn-warning">查看Word输出示例</a></li>
    <li>校验码生成组件 <a href="./kaptcha.jpg" target="_blank" class="btn-warning">查看示例</a></li>
    <li>甘特图组件 <a href="./gantt.htm" target="_blank" class="btn-warning">查看示例</a></li>
    <li>MVC+Hibernate框架 <a href="./listUsers.action" target="_blank" class="btn-warning">查看示例</a></li>
  </ul>
  <p>&nbsp;</p>
  <p>OpenWebFlow的功能演示需要登录，点击此处登录--&gt;<a class="btn btn-danger" href="login.jsp">现在登录！</a></p>
</div>
<!-- InstanceEndEditable -->
</div>
</body>
<!-- InstanceEnd --></html>