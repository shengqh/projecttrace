<%@ include file="include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>CQS/VUMC Project Trace System</title>
</head>

<body>
	<jsp:include page="menu.jsp" />

	<p>
	<h1 align="center">
		<c:choose>
			<c:when test="${projectTaskForm.task.id == null}">
				<spring:message code="label.tasknew" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.taskedit" /> : ${projectTaskForm.task.name}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveprojecttask.html"
			commandName="projectTaskForm">
			<form:hidden path="projectId" />
			<form:hidden path="task.id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<tr>
					<td><form:label path="task.taskIndex">
							<spring:message code="label.taskindex" />
						</form:label></td>
					<td><form:input path="task.taskIndex" /></td>
					<td><form:errors path="task.taskIndex" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="task.name">
							<spring:message code="label.taskname" />
						</form:label></td>
					<td><form:input path="task.name" /></td>
					<td><form:errors path="task.name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="task.peopleTime">
							<spring:message code="label.taskpeopletime" />
						</form:label></td>
					<td><form:input path="task.peopleTime" /></td>
					<td><form:errors path="task.peopleTime" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="task.machineTime">
							<spring:message code="label.taskmachinetime" />
						</form:label></td>
					<td><form:input path="task.machineTime" /></td>
					<td><form:errors path="task.machineTime" cssClass="error" /></td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${projectTaskForm.task.id == null}">
							<td colspan="3"><input type="submit"
								value="<spring:message code="label.add"/>" /></td>
						</c:when>
						<c:otherwise>
							<td colspan="3"><input type="submit"
								value="<spring:message code="label.update"/>" /></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</form:form>
</body>
</html>
