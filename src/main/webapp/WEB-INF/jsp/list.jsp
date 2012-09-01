<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>CQS Project Trace System</title>
<style type="text/css">
body {
	font-family: sans-serif;
}

.data,.data td {
	border-collapse: collapse;
	width: 100%;
	border: 1px solid #aaa;
	margin: 2px;
	padding: 2px;
}

.data th {
	font-weight: bold;
	background-color: #5C82FF;
	color: white;
}
</style>
</head>
<body>

	<h2>Welcome to CQS Project Trace System!</h2>

	<table>
		<tr>
			<th><spring:message code="label.action" /></th>
		</tr>
		<tr>
			<td><a href="user"><spring:message code="label.usermanager" /></a></td>
		</tr>
		<tr>
			<td><a href="project"><spring:message
						code="label.projectmanager" /></a></td>
		</tr>
		<tr>
			<td><a href="pipeline"><spring:message
						code="label.pipelinemanager" /></a></td>
		</tr>
	</table>
</body>
</html>
