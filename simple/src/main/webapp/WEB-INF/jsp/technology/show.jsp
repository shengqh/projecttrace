<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />
	<p>
	<h1>Technology : ${technology.name}</h1>
	<p>
	<h3>Description</h3>
	<pre>${technology.description}</pre>
	<p>
	<h3>
		Platforms
		<sec:authorize access="hasRole('ROLE_MODULE_EDIT')"> | <a
				href="addplatform?technologyid=${technology.id}">New Platform</a>
		</sec:authorize>
	</h3>
	<c:if test="${!empty technology.platforms}">
		<div>
			<table id="box-table-a" summary="Technology Platforms">
				<thead>
					<tr>
						<th scope="col">Name</th>
						<th scope="col">Description</th>
						<sec:authorize access="hasRole('ROLE_MODULE_EDIT')">
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${technology.platforms}" var="platform">
						<tr>
							<td>${platform.name}</td>
							<td>${platform.description}</td>
							<sec:authorize access="hasRole('ROLE_MODULE_EDIT')">
								<td>
									<form action="editplatform?id=${platform.id}" method="post">
										<input type="submit"
											value="<spring:message	code="label.edit" />" />
									</form>
								</td>
								<td>
									<form action="deleteplatform/${platform.id}" method="post">
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
		</div>
	</c:if>
	<h3>
		Modules
		<sec:authorize access="hasRole('ROLE_MODULE_EDIT')"> | <a
				href="addmodule?technologyid=${technology.id}">New Module</a>
		</sec:authorize>
	</h3>
	<c:if test="${!empty technology.modules}">
		<div>
			<table id="box-table-a" summary="Technology Modules">
				<thead>
					<tr>
						<th scope="col">Module Index</th>
						<th scope="col">Name</th>
						<th scope="col">Price per project</th>
						<th scope="col">Price per unit</th>
						<th scope="col">Description</th>
						<sec:authorize access="hasRole('ROLE_MODULE_EDIT')">
							<th scope="col">&nbsp;</th>
							<th scope="col">&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${technology.modules}" var="module">
						<tr>
							<td>${module.moduleIndex}</td>
							<td>${module.name}</td>
							<td>${module.pricePerProject}</td>
							<td>${module.pricePerUnit}</td>
							<td>${module.description}</td>
							<sec:authorize access="hasRole('ROLE_MODULE_EDIT')">
								<td>
									<form action="editmodule?id=${module.id}" method="post">
										<input type="submit"
											value="<spring:message	code="label.edit" />" />
									</form>
								</td>
								<td>
									<form action="deletemodule/${module.id}" method="post">
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
		</div>
	</c:if>
	<p class="message">${message}</p>
</body>
</html>
