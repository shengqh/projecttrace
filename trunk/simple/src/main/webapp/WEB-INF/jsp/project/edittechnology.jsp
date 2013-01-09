<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1>
		<c:choose>
			<c:when test="${technologyForm.technology.id == null}">
				New Technology : ${technologyForm.reference.name}
			</c:when>
			<c:otherwise>
				Edit Technology : ${technologyForm.technology.technology}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveprojecttechnology.html"
			commandName="technologyForm">
			<form:hidden path="projectId" />
			<form:hidden path="technology.id" />
			<form:hidden path="technology.technologyId" />
			<form:hidden path="technology.technology" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
			  	<col width="200">
				<tbody>
					<tr>
						<td>Sample number</td>
						<td><form:input id="txt" path="technology.sampleNumber"
								cssClass="txt" /></td>
						<td><form:errors path="technology.sampleNumber"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Platform</td>
						<td><form:select path="technology.platformId"
								items="${technologyForm.reference.platforms}" itemLabel="name"
								itemValue="id" size="6" /></td>
						<td><form:errors path="technology.platformId"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Modules</td>
						<td><form:select path="modules" multiple="true"
								items="${technologyForm.reference.modules}" itemLabel="name"
								itemValue="id" size="20" /></td>
						<td><form:errors path="modules" cssClass="error" /></td>
					</tr>
					<tr>
						<td colspan="3" align="center"><c:choose>
								<c:when test="${technologyForm.technology.id == null}">
									<input type="submit" value="<spring:message code="label.add"/>" />
								</c:when>
								<c:otherwise>
									<input type="submit"
										value="<spring:message code="label.update"/>" />
								</c:otherwise>
							</c:choose> <input type="button"
							value="<spring:message code="label.back" />"
							onClick="parent.location='showproject?id=${technologyForm.technology.project.id}'" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
