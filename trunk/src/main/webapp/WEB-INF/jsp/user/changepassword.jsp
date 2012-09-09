<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">
		<spring:message code="label.changepassword" />
		${targetUser.email}
	</h1>
	<p>
		<form:form method="post" action="savepassword.html"
			commandName="changePasswordForm">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<form:hidden path="targetUser.id" />
			<table id="box-table-a">
				<tr>
					<td><form:label path="currentPassword">
							<spring:message code="label.currentpassword" />
						</form:label></td>
					<td><form:password path="currentPassword"   /></td>
					<td><form:errors path="currentPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="newPassword">
							<spring:message code="label.newpassword" />
						</form:label></td>
					<td><form:password path="newPassword" /></td>
					<td><form:errors path="newPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="confirmPassword">
							<spring:message code="label.confirmpassword" />
						</form:label></td>
					<td><form:password path="confirmPassword" /></td>
					<td><form:errors path="confirmPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit"
						value="<spring:message code="label.update"/>" /><input
						type="reset" value="<spring:message code="label.reset"/>" /></td>
				</tr>
			</table>
		</form:form>
</body>
</html>
