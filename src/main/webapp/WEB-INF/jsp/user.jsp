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
	</h1>
	<p>
		<c:if test="${!empty userList}">
			<table id="box-table-a" summary="User list">
				<thead>
					<tr>
						<th scope="col">Name</th>
						<th scope="col">Email</th>
						<th scope="col">Telephone</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userList}" var="user">
						<tr>
							<td>${user.name}</td>
							<td>${user.email}</td>
							<td>${user.telephone}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
</body>
</html>
