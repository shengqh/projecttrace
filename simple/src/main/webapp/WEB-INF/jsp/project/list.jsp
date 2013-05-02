<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1>
		<spring:message code="label.projectlist" />
		<sec:authorize access="hasRole('ROLE_PROJECT_EDIT')">
		| <a href="addproject"><spring:message code="label.projectnew" />
			</a>
		</sec:authorize>
		<sec:authorize
			access="hasAnyRole('ROLE_VANGARD_ADSTAFF','ROLE_ADMIN')">
			| <a href="export">Export</a>
		</sec:authorize>
	</h1>
	<p>
		<c:if test="${!empty projectList}">
			<table id="box-table-a" summary="Project list">
				<thead>
					<tr>
						<th scope="col">ID</th>
						<th scope="col">Study</th>
						<th scope="col">Study PI</th>
						<th scope="col">Faculty</th>
						<th scope="col">Staff</th>
						<th scope="col">Started</th>
						<th scope="col">Completed</th>
						<th scope="col">BV-data?</th>
						<th scope="col">BV-data to PI</th>
						<th scope="col">BV-sample?</th>
						<th scope="col">BV-redeposit</th>
						<th scope="col">Grant?</th>
						<th scope="col">Billed</th>
						<th scope="col">Status</th>
						<sec:authorize
							access="hasAnyRole('ROLE_VANGARD_ADSTAFF','ROLE_ADMIN')">
							<th scope="col">&nbsp;</th>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projectList}" var="project">
						<c:choose>
							<c:when test="${project.status == 'Cancelled'}">
								<c:set var="bc" value="class=\"failed\"" />
							</c:when>
							<c:otherwise>
								<c:set var="bc" value="" />
							</c:otherwise>
						</c:choose>
						<tr ${bc}>
							<td><a href="showproject?id=${project.id}">${project.projectName}</a></td>
							<td>${project.name}</td>
							<td>${project.studyPI}</td>
							<td><c:forEach items="${project.facultyName}" var="user">
									${user}<br>
								</c:forEach></td>
							<td><c:forEach items="${project.staffName}" var="user">
									${user}<br>
								</c:forEach></td>
							<td><spring:eval expression="project.workStarted" /></td>
							<td><spring:eval expression="project.workCompleted" /></td>
							<td><tags:yesno value="${project.isBioVUDataRequest}" /></td>
							<td><spring:eval expression="project.bioVUDataDeliveryDate" /></td>
							<td><tags:yesno value="${project.isBioVUSampleRequest}" /></td>
							<td><spring:eval expression="project.bioVURedepositDate" /></td>
							<td><tags:yesno value="${project.isGranted}" /></td>
							<td><spring:eval expression="project.billedInCORES" /></td>
							<td>${project.status}</td>
							<sec:authorize
								access="hasAnyRole('ROLE_VANGARD_ADSTAFF','ROLE_ADMIN')">
								<td>
									<form action="exportproject?id=${project.id}" method="post">
										<input type="submit" value="Export" />
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
