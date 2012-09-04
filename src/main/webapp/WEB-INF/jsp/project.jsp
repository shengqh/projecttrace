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
		<spring:message code="label.projectlist" />
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		| <a href="addproject"><spring:message code="label.projectnew" /></a>
		</sec:authorize>
	</h1>
	<p>

		<c:if test="${!empty projectList}">
			<table id="box-table-a" summary="Project list">
				<thead>
					<tr>
						<th scope="col"><spring:message code="label.name" /></th>
						<th scope="col"><spring:message code="label.description" /></th>
						<th scope="col"><spring:message code="label.projectcreatedate" /></th>
						<th scope="col"><spring:message code="label.projectcreator" /></th>
						<sec:authorize access="hasRole('ROLE_MANAGER')">
							<th scope="col">&nbsp;</th>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projectList}" var="project">
						<tr>
							<td><a href="showproject?id=${project.id}">${project.name}</a></td>
							<td>${project.description}</td>
							<td>${project.createDate}</td>
							<td>${project.creator.email}</td>
							<sec:authorize access="hasRole('ROLE_MANAGER')">
								<td><a href="editproject?id=${project.id}"><spring:message
											code="label.edit" /></a></td>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<td><a href="deleteproject?id=${project.id}"><spring:message
											code="label.delete" /></a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
</body>
</html>
