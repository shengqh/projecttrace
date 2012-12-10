<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">
		<c:choose>
			<c:when test="${projectForm.project.id == null}">
				<spring:message code="label.projectnew" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.projectedit" /> : ${projectForm.project.studyName}
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
					<td>Study name</td>
					<td><form:input id="txt" path="project.studyName" cssClass="txt" /></td>
					<td><form:errors path="project.studyName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Comments</td>
					<td><form:textarea id="textarea" path="project.comments"
							rows="10" cols="60" /></td>
					<td><form:errors path="project.comments" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Contact</td>
					<td><form:select path="contact" multiple="true"
							items="${projectForm.users}" itemLabel="name" itemValue="id"
							size="4" /></td>
					<td><form:errors path="contact" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Study PI</td>
					<td><form:select path="studyPI" multiple="true"
							items="${projectForm.users}" itemLabel="name" itemValue="id"
							size="4" /></td>
					<td><form:errors path="studyPI" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Faculty</td>
					<td><form:select path="faculty" multiple="true"
							items="${projectForm.users}" itemLabel="name" itemValue="id"
							size="4" /></td>
					<td><form:errors path="faculty" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Staff</td>
					<td><form:select path="staff" multiple="true"
							items="${projectForm.users}" itemLabel="name" itemValue="id"
							size="4" /></td>
					<td><form:errors path="staff" cssClass="error" /></td>
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
