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
					<td><form:input path="project.name" /></td>
					<td><form:errors path="project.name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="project.description">
							<spring:message code="label.description" />
						</form:label></td>
					<td><form:textarea path="project.description" /></td>
					<td><form:errors path="project.description" cssClass="error" /></td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${projectForm.project.id == null}">
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
