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
		<c:choose>
			<c:when test="${userForm.id == null}">
				<spring:message code="label.newuser" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.edituser" /> : ${userForm.email}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveuser.html" commandName="userForm">
			<form:hidden path="id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<c:choose>
					<c:when test="${userForm.id != null}">
						<form:hidden path="email" />
					</c:when>
					<c:otherwise>
						<tr>
							<td><form:label path="email">
									<spring:message code="label.email" />
								</form:label></td>
							<td><form:input path="email" cssClass="txt" /></td>
							<td><form:errors path="email" cssClass="error" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td><form:label path="firstname">
							<spring:message code="label.firstname" />
						</form:label></td>
					<td><form:input path="firstname" cssClass="txt" /></td>
					<td><form:errors path="firstname" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="lastname">
							<spring:message code="label.lastname" />
						</form:label></td>
					<td><form:input path="lastname" cssClass="txt" /></td>
					<td><form:errors path="lastname" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="telephone">
							<spring:message code="label.telephone" />
						</form:label></td>
					<td><form:input path="telephone" cssClass="txt" /></td>
					<td><form:errors path="telephone" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="enabled">
							<spring:message code="label.enabled" />
						</form:label></td>
					<td><form:checkbox path="enabled" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="locked">
							<spring:message code="label.locked" />
						</form:label></td>
					<td><form:checkbox path="locked" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="expired">
							<spring:message code="label.expired" />
						</form:label></td>
					<td><form:checkbox path="expired" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="role">
							<spring:message code="label.role" />
						</form:label></td>
					<td><form:select path="role" items="${userForm.roles}" /></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3" align="center"><c:choose>
							<c:when test="${userForm.id == null}">
								<input type="submit" value="<spring:message code="label.add"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.update"/>" />
							</c:otherwise>

						</c:choose>
						<form>
							<input type="button" value="<spring:message code="label.back" />"
								onClick="parent.location='alluser.html'" />
						</form></td>
				</tr>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
