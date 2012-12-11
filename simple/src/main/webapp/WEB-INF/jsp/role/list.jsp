<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1>Role List</h1>
	<p>
	<table id="box-table-a" summary="Role list">
		<thead>
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Permission</th>
				<sec:authorize access="hasRole('ROLE_USER_EDIT')">
					<th scope="col">&nbsp;</th>
				</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${roles}" var="role">
				<tr>
					<td>${role.name}</td>
					<td><c:forEach items="${role.permissions}" var="p">
									${p.permission.name}<br>
						</c:forEach></td>
					<sec:authorize access="hasRole('ROLE_USER_EDIT')">
						<td>
							<form action="editrole?id=${role.id}" method="post">
								<input type="submit"
									value="<spring:message	code="label.edit" />" />
							</form>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form>
		<form:errors path="*" cssClass="errorblock" element="div" />
	</form>
</body>
</html>
