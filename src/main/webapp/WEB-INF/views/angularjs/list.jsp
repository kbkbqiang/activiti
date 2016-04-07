<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app>
<head>
<%@ include file="/common/global.jsp"%>
<%-- <script src="${ctx }/js/angularjs/angular.min.js"></script> --%>
<script type="text/javascript" src="https://code.angularjs.org/1.2.5/angular.min.js"></script>
<script type="text/javascript">
	function Hello($scope, $http) {
		var url = 'http://localhost:8080/activiti/main/userlist';
		$http.get(url).success(function(data) {
			$scope.userdetails = data;
		});
	}
</script>
</head>
<body>
	Your name:
	<input type="text" ng-model="yourname" placeholder="World">
	<hr>
	Hello {{yourname || 'World'}}!
	<div ng-app="" ng-controller="Hello">
		<table border="1">
			<tr>
				<th>id</th>
				<th>email</th>
				<th>firstName</th>
				<th>lastName</th>
			</tr>
			<tr ng-repeat="user in userdetails">
				<td>{{ user.id }}</td>
				<td>{{ user.email }}</td>
				<td>{{ user.firstName }}</td>
				<td>{{ user.lastName }}</td>
			</tr>
		</table>
	</div>
</body>
</html>