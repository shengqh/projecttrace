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
			<c:when test="${projectTaskForm.id == null}">
				<spring:message code="label.tasknew" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.taskedit" /> : ${projectTaskForm.name}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveprojecttask.html"
			commandName="projectTaskForm">
			<form:hidden path="projectId" />
			<form:hidden path="id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<tr>
					<td><form:label path="taskIndex">
							<spring:message code="label.taskindex" />
						</form:label></td>
					<td><form:input id="txt" path="taskIndex" /></td>
					<td><form:errors path="taskIndex" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="name">
							<spring:message code="label.taskname" />
						</form:label></td>
					<td><form:input id="txt" path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="peopleTime">
							<spring:message code="label.taskpeopletime" />
						</form:label></td>
					<td><form:input id="txt" path="peopleTime" /></td>
					<td><form:errors path="peopleTime" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="machineTime">
							<spring:message code="label.taskmachinetime" />
						</form:label></td>
					<td><form:input id="txt" path="machineTime" /></td>
					<td><form:errors path="machineTime" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="status">
							<spring:message code="label.taskstatus" />
						</form:label></td>
					<td><form:select path="status"
							items="${projectTaskForm.statusMap}" /></td>
					<td><form:errors path="status" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><c:choose>
							<c:when test="${projectTaskForm.id == null}">
								<input type="submit" value="<spring:message code="label.add"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.update"/>" />
							</c:otherwise>
						</c:choose>
						<form>
							<input type="button" value="<spring:message code="label.back" />"
								onClick="parent.location='showproject?projectid=${projectTaskForm.projectId}'" />
						</form></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
