<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1>
		<spring:message code="label.userlist" />
		<sec:authorize access="hasRole('ROLE_USER_EDIT')"> | <a
				href="adduser"><spring:message code="label.newuser" /></a>
		</sec:authorize>
	</h1>
	<p>
		<c:if test="${!empty validUserList}">
			<table id="box-table-a" summary="Valid User list">
				<thead>
					<tr>
						<th scope="col"><spring:message code="label.email" /></th>
						<th scope="col"><spring:message code="label.name" /></th>
						<th scope="col"><spring:message code="label.role" /></th>
						<th scope="col"><spring:message code="label.telephone" /></th>
						<th scope="col"><spring:message code="label.createdate" /></th>
						<sec:authorize access="hasRole('ROLE_USER_EDIT')">
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${validUserList}" var="user">
						<tr>
							<td>${user.email}</td>
							<td>${user.name}</td>
							<td><c:forEach items="${user.roles}" var="role">
									${role.role.name}<br>
								</c:forEach></td>
							<td>${user.telephone}</td>
							<td>${user.createDateString}</td>
							<sec:authorize access="hasRole('ROLE_USER_EDIT')">
								<td>
									<form action="disableuser/${user.id}">
										<input type="submit"
											value="<spring:message	code="label.disable" />" />
									</form>
								</td>
								<td>
									<form action="lockuser/${user.id}">
										<input type="submit"
											value="<spring:message	code="label.lock" />" />
									</form>
								</td>
								<td>
									<form action="deleteuser/${user.id}">
										<input type="submit"
											value="<spring:message	code="label.delete" />"
											onclick="return confirm('Are you sure you want to delete user ${user.email}?')" />
									</form>
								</td>
								<td>
									<form action="edituser?userid=${user.id}" method="post">
										<input type="submit"
											value="<spring:message	code="label.edit" />" />
									</form>
								</td>
								<td>
									<form action="resetpassword/${user.id}">
										<input type="submit"
											value="<spring:message	code="label.resetpassword" />" />
									</form>
								</td>
							</sec:authorize>
						</tr>
					</c:forEach>
					<c:if test="${!empty invalidUserList}">
						<tr>
							<td align="center" colspan="10"><h3 align="center">
									<spring:message code="label.invaliduserlist" />
								</h3></td>
						</tr>
						<c:forEach items="${invalidUserList}" var="user">
							<tr>
								<td>${user.email}</td>
								<td>${user.name}</td>
								<td><c:forEach items="${user.roles}" var="role">
									${role.role.name}<br>
									</c:forEach></td>
								<td>${user.telephone}</td>
								<td>${user.createDate}</td>
								<sec:authorize access="hasRole('ROLE_USER_EDIT')">
									<td><c:if test="${!user.enabled}">
											<form action="enableuser/${user.id}">
												<input type="submit"
													value="<spring:message	code="label.enable" />" />
											</form>
										</c:if></td>
									<td><c:if test="${!user.accountNonLocked}">
											<form action="unlockuser/${user.id}">
												<input type="submit"
													value="<spring:message	code="label.unlock" />" />
											</form>
										</c:if></td>
									<td><c:if test="${!user.accountNonDeleted}">
											<form action="undeleteuser/${user.id}">
												<input type="submit"
													value="<spring:message	code="label.undelete" />" />
											</form>
										</c:if></td>
									<td>
										<form action="edituser?userid=${user.id}" method="post">
											<input type="submit"
												value="<spring:message	code="label.edit" />" />
										</form>
									</td>
									<td>
										<form action="deleteuserforever/${user.id}">
											<input type="submit"
												value="<spring:message	code="label.deleteforever" />"
												onclick="return confirm('Are you sure you want to delete user ${user.email} forever?')" />
										</form>
									</td>
								</sec:authorize>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</c:if>
	<form>
		<form:errors path="*" cssClass="errorblock" element="div" />
	</form>
</body>
</html>
