<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>申请待办任务列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>
    <style type="text/css">
    /* block ui */
	.blockOverlay {
		z-index: 1004 !important;
	}
	.blockMsg {
		z-index: 1005 !important;
	}
    </style>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/oa/leave/leave-todo.js" type="text/javascript"></script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">${message}</div>
	</c:if>
	<table width="100%" class="need-border">
		<thead>
			<tr>
				<th>流程名称</th>
				<th>当前节点</th>
				<th>任务创建时间</th>
				<!-- <th>流程记录</th> -->
				<th>流程状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${results}" var="data">
				<c:set var="task" value="${data['task']}" />
				<c:set var="pi" value="${data['processInstance'] }" />
				<c:set var="pd" value="${data['processDefinition'] }" />
				<tr id="${task.id }">
					<td>${pd.description }</td>
					<td>
						<%-- <a class="trace" href='#' pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a> --%>
						<a class="trace" pid="${pi.id }" pdid="${pi.processDefinitionId}" href="${ctx }/workflow/process/trace/auto/${pi.id }" title="点击查看流程图">${task.name }</a>
					</td>
					
					<td>${task.createTime }</td>
					
					<td>${pi.suspended ? "已挂起" : "正常" }；<b title='流程版本号'>V: ${data["processDefinition"].processDefinition.version }</b></td>
					<td>
						<c:if test="${empty task.assignee && task.name !='更新业务'}">
							<a class="claim" href="${ctx }/workflow/auto/task/claim/${task.id}">签收</a>
						</c:if>
						<c:if test="${task.name eq '更新业务'}">
							<a class="claim" href="${ctx }/oa/leave/complete/${task.id}">更新</a>
						</c:if>								
						<c:if test="${not empty task.assignee && task.name !='更新业务'}">
							<!-- 此处用tkey记录当前节点的名称 -->
							<%-- <a class="handle" tkey='${task.taskDefinitionKey }' tname='${task.name }' href="#">办理</a> --%>
							<a class="handle" href="${ctx }/workflow/auto/get-form/task/${task.id }" tkey='${task.taskDefinitionKey }' tname='${task.name }' href="#">办理</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 下面是每个节点的模板，用来定义每个节点显示的内容 -->
	<!-- 使用DIV包裹，每个DIV的ID以节点名称命名，如果不同的流程版本需要使用同一个可以自己扩展（例如：在DIV添加属性，标记支持的版本） -->


</body>
</html>
