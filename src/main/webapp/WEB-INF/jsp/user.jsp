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
		<spring:message code="label.userlist" />
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		| <a href="adduser"><spring:message code="label.newuser" /></a>
		</sec:authorize>
	</h1>
	<p>
		<c:if test="${!empty userList}">
			<table id="box-table-a" summary="User list">
				<thead>
					<tr>
						<th scope="col"><spring:message code="label.email" /></th>
						<th scope="col"><spring:message code="label.name" /></th>
						<th scope="col"><spring:message code="label.role" /></th>
						<th scope="col"><spring:message code="label.telephone" /></th>
						<th scope="col"><spring:message code="label.createdate" /></th>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<th scope="col"><spring:message code="label.enabled" /></th>
							<th scope="col"><spring:message code="label.locked" /></th>
							<th scope="col"><spring:message code="label.expired" /></th>
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userList}" var="user">
						<tr>
							<td>${user.email}</td>
							<td>${user.name}</td>
							<td>${user.roleName}</td>
							<td>${user.telephone}</td>
							<td>${user.createDate}</td>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<td>${user.enabled}</td>
								<td>${user.locked}</td>
								<td>${user.expired}</td>
								<td><a href="edituser?id=${user.id}">edit</a></td>
								<td><a href="deleteuser?id=${user.id}">delete</a></td>
								<td><a href="resetpassword?id=${user.id}">reset
										password</a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
</body>
</html>
