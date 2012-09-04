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
			<c:when test="${projectForm.project.id == null}">
				<spring:message code="label.projectnew" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.projectedit" /> : ${projectForm.project.name}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveproject.html"
			commandName="projectForm">
			<form:hidden path="project.id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<tr>
					<td><form:label path="project.name">
							<spring:message code="label.projectname" />
						</form:label></td>
					<td><form:input id="txt" path="project.name" /></td>

					<td><form:errors path="project.name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="project.description">
							<spring:message code="label.description" />
						</form:label></td>
					<td><form:textarea id="textarea" path="project.description"
							rows="10" cols="60" /></td>
					<td><form:errors path="project.description" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="managerIds">
							<spring:message code="label.managers" />
						</form:label></td>
					<td><form:select path="managerIds" multiple="true"
							items="${projectForm.managers}" itemLabel="email" itemValue="id"
							size="4" /></td>
					<td><form:errors path="managerIds" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="userIds">
							<spring:message code="label.users" />
						</form:label></td>
					<td><form:select path="userIds" multiple="true"
							items="${projectForm.users}" itemLabel="email" itemValue="id"
							size="4" /></td>
					<td><form:errors path="userIds" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="observerIds">
							<spring:message code="label.observers" />
						</form:label></td>
					<td><form:select path="observerIds" multiple="true"
							items="${projectForm.observers}" itemLabel="email" itemValue="id"
							size="4" /></td>
					<td><form:errors path="observerIds" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><c:choose>
							<c:when test="${projectForm.project.id == null}">
								<input type="submit" value="<spring:message code="label.add"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.update"/>" />
							</c:otherwise>
						</c:choose>
						<form>
							<input type="button" value="<spring:message code="label.back" />"
								onClick="parent.location='project.html'" />
						</form></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
