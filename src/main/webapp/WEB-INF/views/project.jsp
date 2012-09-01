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
		<spring:message code="label.projectmanager" />
	</h2>

	<c:if test="${projectForm.canEdit}">
		<form:form method="post" action="saveproject.html"
			commandName="projectForm.project">
			<form:hidden path="id" />
			<table>
				<tr>
					<td><form:label path="name">
							<spring:message code="label.projectname" />
						</form:label></td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td colspan="2"><c:choose>
							<c:when test="${projectForm.project.id != null}">
								<input type="submit"
									value="<spring:message code="label.updateproject"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.addproject"/>" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</form:form>
	</c:if>

	<h3>Projects</h3>
	<c:if test="${!empty projectForm.projectList}">
		<table class="data">
			<tr>
				<th>Name</th>
				<th>CreateDate</th>
				<th>Creator</th>
				<c:if test="${projectForm.canEdit}">
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</c:if>
			</tr>
			<c:forEach items="${projectForm.projectList}" var="project">
				<tr>
					<td><a href="projectdetail?id=${project.id}">${project.name}</a></td>
					<td>${project.createDate}</td>
					<td>${project.creator.lastname},
						${project.creator.firstname}</td>
					<c:if test="${projectForm.canEdit}">
						<td><a href="editproject?id=${project.id}">edit</a></td>
						<td><a href="deleteproject?id=${project.id}">delete</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>
