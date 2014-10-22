<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<style type="text/css">
<!--
.STYLE1 {font-size: 9px}
-->
</style>
 
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/workflow/listAssignedTasks.action">待办任务<span class="STYLE1">(${tools.processTool.assignedTasksCount})</span></a></li>
				<li><a href="${pageContext.request.contextPath}/workflow/listTaskQueue.action">待签收任务<span class="STYLE1">(${tools.processTool.taskQueueCount})</span></a></li>
      </ul>
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">我要启动流程<span class="STYLE1">(${tools.processTool.processDefsCount})</span> <span class="caret"></a>
          <ul class="dropdown-menu" role="menu">
          	<c:forEach items="${tools.processTool.processDefs}" var="processDef">
            <li><a href="${pageContext.request.contextPath}/workflow/startProcessForm.action?processDefId=${processDef.id}">${processDef.name}</a></li>
            </c:forEach>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">所有流程列表 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${pageContext.request.contextPath}/workflow/listActiveProcesses.action">运行中流程<span class="STYLE1">(${tools.processTool.activeProcessesCount})</span></a></li>
			<li><a href="${pageContext.request.contextPath}/workflow/listHistoricProcesses.action">已完成流程<span class="STYLE1">(${tools.processTool.historicProcessesCount})</span></a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">我的流程列表 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${pageContext.request.contextPath}/workflow/listMyActiveProcesses.action">运行中流程<span class="STYLE1">(${tools.processTool.myActiveProcessesCount})</span></a></li>
			<li><a href="${pageContext.request.contextPath}/workflow/listMyHistoricProcesses.action">已完成流程<span class="STYLE1">(${tools.processTool.myHistoricProcessesCount})</span></a></li>
          </ul>
        </li>		
      </ul>
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">查看流程定义<span class="STYLE1">(${tools.processTool.processDefsCount})</span> <span class="caret"></a>
          <ul class="dropdown-menu" role="menu">
          	<c:forEach items="${tools.processTool.processDefs}" var="processDef">
            <li><a href="${pageContext.request.contextPath}/diagram-viewer/index.html?processDefinitionId=${processDef.id}">${processDef.name}</a></li>
            </c:forEach>
          </ul>
        </li>
      </ul>
	  <ul class="nav navbar-nav navbar-right">
	  	<c:if test="${pageContext.request.userPrincipal.name!=null}">
        <li><a>${pageContext.request.userPrincipal.name}</a></li>
        <li><a href="${pageContext.request.contextPath}/j_spring_security_logout">退出</a></li>
		</c:if>
	  	<c:if test="${pageContext.request.userPrincipal.name==null}">
        <li><a href="${pageContext.request.contextPath}/login.jsp">我要登录</a></li>
		</c:if>
      </ul>
    	<p>　</div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

