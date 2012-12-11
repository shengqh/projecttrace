<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1>
		Technology List
		<sec:authorize access="hasRole('ROLE_MODULE_EDIT')"> | <a
				href="addtechnology">New Technology</a>
		</sec:authorize>
	</h1>
	<p>
		<c:if test="${!empty technologyList}">
			<table id="box-table-a" summary="Technology list">
				<thead>
					<tr>
						<th scope="col"><spring:message code="label.name" /></th>
						<th scope="col"><spring:message code="label.description" /></th>
						<sec:authorize access="hasRole('ROLE_MODULE_EDIT')">
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${technologyList}" var="tec">
						<c:choose>
							<c:when test="${tec.enabled}">
								<c:set var="bc" value="" />
							</c:when>
							<c:otherwise>
								<c:set var="bc" value="class=\"failed\"" />
							</c:otherwise>
						</c:choose>
						<tr ${bc}>
							<td><a href="showtechnology?id=${tec.id}">${tec.name}</a></td>
							<td><pre>${tec.description}</pre></td>
							<sec:authorize access="hasRole('ROLE_MODULE_EDIT')">
								<td>
									<form action="edittechnology?id=${tec.id}" method="post">
										<input type="submit"
											value="<spring:message	code="label.edit" />" />
									</form>
								</td>
								<td><c:choose>
										<c:when test="${tec.enabled}">
											<form action="disabletechnology/${tec.id}">
												<input type="submit"
													value="<spring:message	code="label.disable" />" />
											</form>
										</c:when>
										<c:otherwise>
											<form action="enabletechnology/${tec.id}">
												<input type="submit"
													value="<spring:message	code="label.enable" />" />
											</form>
										</c:otherwise>
									</c:choose></td>
								<td>
									<form action="deletetechnology/${tec.id}">
										<input type="submit"
											value="<spring:message	code="label.delete" />"
											onclick="return confirm('Are you sure you want to delete?')" />
									</form>
								</td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	<p class="message">${message}</p>
</body>
</html>
