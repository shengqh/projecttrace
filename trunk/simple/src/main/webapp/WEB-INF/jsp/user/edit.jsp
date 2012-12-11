<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

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
			<form:hidden path="user.accountNonExpired" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<c:choose>
					<c:when test="${userForm.user.id != null}">
						<form:hidden path="user.email" />
					</c:when>
					<c:otherwise>
						<tr>
							<td>Email</td>
							<td><form:input path="user.email" cssClass="txt" /></td>
							<td><form:errors path="user.email" cssClass="error" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td><form:label path="user.firstname">
							<spring:message code="label.firstname" />
						</form:label></td>
					<td><form:input path="user.firstname" cssClass="txt" /></td>
					<td><form:errors path="user.firstname" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="user.lastname">
							<spring:message code="label.lastname" />
						</form:label></td>
					<td><form:input path="user.lastname" cssClass="txt" /></td>
					<td><form:errors path="user.lastname" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="user.telephone">
							<spring:message code="label.telephone" />
						</form:label></td>
					<td><form:input path="user.telephone" cssClass="txt" /></td>
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
					<td><form:label path="user.accountNonLocked">
							<spring:message code="label.locked" />
						</form:label></td>
					<td><form:checkbox path="user.accountNonLocked" /></td>
					<td></td>
				</tr>
				<tr>
					<td><form:label path="roles">
							<spring:message code="label.role" />
						</form:label></td>
					<td><form:select path="roles" items="${userForm.roleList}"
							multiple="true" itemLabel="name" itemValue="id" /></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3" align="center"><c:choose>
							<c:when test="${userForm.user.id == null}">
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
