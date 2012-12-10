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
		<sec:authorize access="hasRole('ROLE_VANGARD_USER')">
		| <a href="addproject"><spring:message code="label.projectnew" /></a>
		</sec:authorize>
	</h1>
	<p>

		<c:if test="${!empty projectList}">
			<table id="box-table-a" summary="Project list">
				<thead>
					<tr>
						<th scope="col">Study name</th>
						<th scope="col">Contact date</th>
						<th scope="col">Contact name</th>
						<th scope="col">Study PI</th>
						<th scope="col">Technology</th>
						<th scope="col">Sample number</th>
						<th scope="col">Faculty</th>
						<th scope="col">Staff</th>
						<th scope="col">Work started</th>
						<th scope="col">Work completed</th>
						<sec:authorize access="hasRole('ROLE_VANGARD_USER')">
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
							<c:when test="${project.status == -1}">
								<c:set var="bc" value="class=\"failed\"" />
							</c:when>
							<c:otherwise>
								<c:set var="bc" value="" />
							</c:otherwise>
						</c:choose>
						<tr ${bc}>
							<td><a href="showproject?projectid=${project.id}">${project.studyName}</a></td>
							<td>${project.contactDate}</td>
							<td>
								<c:forEach items="${project.contactName}" var="user">
									${user}<br>
								</c:forEach>
							</td>
							<td>							
								<c:forEach items="${project.studyPIName}" var="user">
									${user}<br>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${project.technologies}" var="tec">
									${tec.technology}<br>
								</c:forEach>
							</td>
							<td>${project.sampleNumber}</td>
							<td>							
								<c:forEach items="${project.facultyName}" var="user">
									${user}<br>
								</c:forEach>
							</td>
							<td>							
								<c:forEach items="${project.staffName}" var="user">
									${user}<br>
								</c:forEach>
							</td>
							<td>${project.workStarted}</td>
							<td>${project.workCompleted}</td>
							<sec:authorize access="hasRole('ROLE_VANGARD_USER')">
								<td>
									<form action="editproject?projectid=${project.id}"
										method="post">
										<input type="submit"
											value="<spring:message	code="label.edit" />" />
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
