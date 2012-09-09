<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

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
						<th scope="col"><spring:message
								code="label.projectcreatedate" /></th>
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
							<td><a href="showproject?projectid=${project.id}">${project.name}</a></td>
							<td><pre>${project.description}</pre></td>
							<td>${project.createDate}</td>
							<td>${project.creator}</td>
							<sec:authorize access="hasRole('ROLE_MANAGER')">
								<td>
									<form action="editproject?projectid=${project.id}" method="post">
										<input type="submit" value="<spring:message	code="label.edit" />" />
									</form>
								</td>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<td>
									<form action="deleteproject/${project.id}">
										<input type="submit"
											value="<spring:message	code="label.delete" />"
											onclick="return confirm('Are you sure you want to delete?')" />
									</form>
								</td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	<p class="message">${message}</p>
</body>
</html>
