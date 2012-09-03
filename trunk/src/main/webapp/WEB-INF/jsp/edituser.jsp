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
		<c:choose>
			<c:when test="${userForm.user.id == null}">
				<spring:message code="label.newuser" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.edituser" /> : ${userForm.user.email}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveuser.html" commandName="userForm">
			<form:hidden path="user.id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<c:choose>
					<c:when test="${userForm.user.id != null}">
						<form:hidden path="user.email" />
					</c:when>
					<c:otherwise>
						<tr>
							<td><form:label path="user.email">
									<spring:message code="label.email" />
								</form:label></td>
							<td><form:input path="user.email" /></td>
							<td><form:errors path="user.email" cssClass="error" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td><form:label path="user.firstname">
							<spring:message code="label.firstname" />
						</form:label></td>
					<td><form:input path="user.firstname" /></td>
					<td><form:errors path="user.firstname" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="user.lastname">
							<spring:message code="label.lastname" />
						</form:label></td>
					<td><form:input path="user.lastname" /></td>
					<td><form:errors path="user.lastname" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="user.telephone">
							<spring:message code="label.telephone" />
						</form:label></td>
					<td><form:input path="user.telephone" /></td>
					<td><form:errors path="user.telephone" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="user.enabled">
							<spring:message code="label.enabled" />
						</form:label></td>
					<td><form:checkbox path="user.enabled" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="user.locked">
							<spring:message code="label.locked" />
						</form:label></td>
					<td><form:checkbox path="user.locked" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="user.expired">
							<spring:message code="label.expired" />
						</form:label></td>
					<td><form:checkbox path="user.expired" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="user.role">
							<spring:message code="label.role" />
						</form:label></td>
					<td><form:select path="user.role" items="${userForm.roles}" /></td>
					<td></td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${userForm.user.id == null}">
							<td colspan="3"><input type="submit"
								value="<spring:message code="label.add"/>" /></td>
						</c:when>
						<c:otherwise>
							<td colspan="3"><input type="submit"
								value="<spring:message code="label.update"/>" /></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</form:form>
</body>
</html>
