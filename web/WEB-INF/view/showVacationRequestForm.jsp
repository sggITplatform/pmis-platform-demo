<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=utf-8" %>

<table class="table table-condensed">
  <tr>
    <td>表单号</td>
    <td>${bean.ID}</td>
  </tr>
  <tr>
    <td>申请天数</td>
    <td>${bean.DAYS}</td>
  </tr>
  <tr>
    <td>事由</td>
    <td>${bean.MOTIVATION}</td>
  </tr>
</table>
