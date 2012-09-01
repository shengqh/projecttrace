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

	<h2>
		<spring:message code="label.pipelinemanager" />
	</h2>

	<form:form method="post"
		action="savepipelinetask.html?pipelineid=${pipeline.id}"
		commandName="pipelinetask">
		<table>
			<form:hidden path="id" />
			<tr>
				<td><form:label path="taskIndex">
						<spring:message code="label.pipelinetaskindex" />
					</form:label></td>
				<td><form:input path="taskIndex" /></td>
			</tr>
			<tr>
				<td><form:label path="name">
						<spring:message code="label.pipelinetaskname" />
					</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="peopleTime">
						<spring:message code="label.pipelinetaskpeopletime" />
					</form:label></td>
				<td><form:input path="peopleTime" /></td>
			</tr>
			<tr>
				<td><form:label path="machineTime">
						<spring:message code="label.pipelinetaskmachinetime" />
					</form:label></td>
				<td><form:input path="machineTime" /></td>
			</tr>
			<tr>
				<td colspan="2"><c:choose>
						<c:when test="${pipelinetask.id != null}">
							<input type="submit"
								value="<spring:message code="label.updatepipelinetask"/>" />
						</c:when>
						<c:otherwise>
							<input type="submit"
								value="<spring:message code="label.addpipelinetask"/>" />
						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
	</form:form>

	<h3>Pipeline : ${pipeline.name} ; Total people time : ${pipeline.peopleTime} ; Total machine time : ${pipeline.machineTime}}</h3>

	<c:if test="${!empty pipeline.tasks}">
		<table class="data">
			<tr>
				<th>Index</th>
				<th>Name</th>
				<th>People Time</th>
				<th>Machine Time</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${pipeline.tasks}" var="task">
				<tr>
					<td>${task.taskIndex}</td>
					<td>${task.name}</td>
					<td>${task.peopleTime}</td>
					<td>${task.machineTime}</td>
					<td><a href="editpipelinetask?id=${task.id}">edit</a></td>
					<td><a href="deletepipelinetask?id=${task.id}">delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>
