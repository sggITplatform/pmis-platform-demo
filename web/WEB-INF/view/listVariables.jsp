<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=utf-8" %>

<table class="table table-condensed">
  <tr>
    <td>名称</td>
    <td>值</td>
  </tr>
  <c:forEach items="${vars.entrySet()}" var="var">
    <tr>
      <td>${var.key}</td>
      <td>${var.value}</td>
    </tr>
  </c:forEach>
</table>
