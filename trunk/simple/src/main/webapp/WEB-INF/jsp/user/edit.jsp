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
			<c:when test="${userForm.isContact}">
				<c:set var="targetname" value="Contact" />
			</c:when>
			<c:otherwise>
				<c:set var="targetname" value="User" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${userForm.id == null}">
				New ${targetname}
			</c:when>
			<c:otherwise>
				Edit ${targetname} : ${userForm.email}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveuser.html" commandName="userForm">
			<form:hidden path="id" />
			<form:hidden path="accountNonExpired" />
			<form:hidden path="accountNonDeleted" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<c:choose>
					<c:when test="${userForm.id != null}">
						<form:hidden path="email" />
					</c:when>
					<c:otherwise>
						<tr>
							<td>Email</td>
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
					<td><form:label path="accountNonLocked">
							<spring:message code="label.accountNonlocked" />
						</form:label></td>
					<td><form:checkbox path="accountNonLocked" /></td>
					<td></td>
				</tr>
				<c:choose>
					<c:when test="${userForm.isContact}">
						<form:hidden path="roles" />
					</c:when>
					<c:otherwise>
						<tr>
							<td><form:label path="roles">
									<spring:message code="label.role" />
								</form:label></td>
							<td><form:select path="roles" items="${userForm.roleList}"
									multiple="true" itemLabel="name" itemValue="id" size="6" /></td>
							<td></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td colspan="3" align="center"><c:choose>
							<c:when test="${userForm.id == null}">
								<input type="submit" value="<spring:message code="label.add"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.update"/>" />
							</c:otherwise>

						</c:choose> <input type="button" value="<spring:message code="label.back" />"
						onClick="parent.location='user.html'" /></td>
				</tr>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
