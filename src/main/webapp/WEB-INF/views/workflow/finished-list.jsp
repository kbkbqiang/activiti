<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>已结束列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>
</head>

<body>
	<table>
		<tr>
			<th>流程ID</th>
			<th>流程定义ID</th>
			<!-- <th>流程名称</th> -->
			<th>流程启动时间</th>
			<th>流程结束时间</th>
			<th>流程结束原因</th>
		</tr>

		<c:forEach items="${page}" var="hpi">
		<tr>
			<td>${hpi.id }</td>
			<td>${hpi.processDefinitionId }</td>
			<%-- <td>${hpi.name }</td> --%>
			<td><fmt:formatDate value="${hpi.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${hpi.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${empty hpi.deleteReason ? "正常结束" : hpi.deleteReason}</td>
		</tr>
		</c:forEach>
	</table>
	
	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>

</body>
</html>
