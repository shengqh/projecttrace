<%@ include file="../include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>CQS/VUMC Project Trace System</title>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">
		<spring:message code="label.userlist" />
	</h1>
	<p>
		<c:if test="${!empty validUserList}">
			<table id="box-table-a" summary="User list">
				<thead>
					<tr>
						<th scope="col"><spring:message code="label.email" /></th>
						<th scope="col"><spring:message code="label.name" /></th>
						<th scope="col"><spring:message code="label.role" /></th>
						<th scope="col"><spring:message code="label.telephone" /></th>
						<th scope="col"><spring:message code="label.createdate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${validUserList}" var="user">
						<tr>
							<td>${user.email}</td>
							<td>${user.name}</td>
							<td>${user.roleName}</td>
							<td>${user.telephone}</td>
							<td>${user.createDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
</body>
</html>
