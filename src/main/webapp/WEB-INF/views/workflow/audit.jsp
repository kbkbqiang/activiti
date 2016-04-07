<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${description }</title>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>

<!-- 自动隐藏提示信息 -->
<script type="text/javascript">
    $(function() {
    	$('#startTime,#endTime').datetimepicker({
            stepMinute: 5
        });
    });
    
    function submitStartTask(){
    	var dataJson = simpleConvertFormToJSON('inputForm');
    	var jsonParams =  JSON.stringify(dataJson);
    	alert("===" + jsonParams);
    	$.ajax( {
			"url": "${ctx }/workflow/auto/start",
			"data": jsonParams,
			"success": function(){
				alert("成功！");
			},
			"dataType": "json",
			"contentType": "application/json",
			"type": "POST",
			"cache": false,
			"error": function () {
				alert( "Error detected when sending table data to server" );
			}
		} );
    }
    </script>
</head>
<body>
${description }
<br />
<form name="form1" id="form1" action="${pageContext.request.contextPath}/workflow/auto/task/complete/${taskId}/${processInstanceId}" method="post">
		<div style="margin: 0 auto;">${renderedTaskForm}</div> 
		 <input type="hidden" name="taskId"
			value="${taskId}">
			<input type="hidden" name="processInstanceId"
			value="${processInstanceId}">
		<div>
			 <table style="margin: auto" width="600">
				<tr>			
					<td align="right" >
					<input type="submit"  value="提交" />
					</td>
				</tr>
			</table> 
		</div> 
	</form> 
</body>
</html>