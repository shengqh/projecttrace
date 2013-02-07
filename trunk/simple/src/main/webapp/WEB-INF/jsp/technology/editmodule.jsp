<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">
		${module.technology.name } |
		<c:choose>
			<c:when test="${module.id == null}">
				New Module
			</c:when>
			<c:otherwise>
				Edit Module : ${module.name}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="savemodule.html" commandName="module">
			<form:hidden path="technology.id" />
			<form:hidden path="id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<tr>
					<td>Index</td>
					<td><form:input path="moduleIndex" cssClass="txt" /></td>
					<td><form:errors path="moduleIndex" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Name</td>
					<td><form:input path="name" cssClass="txt" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Price per project</td>
					<td><form:input path="pricePerProject" cssClass="txt" /></td>
					<td><form:errors path="pricePerProject" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Price per unit</td>
					<td><form:input path="pricePerUnit" cssClass="txt" /></td>
					<td><form:errors path="pricePerUnit" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Notes</td>
					<td><form:textarea path="description" rows="10" cols="60" /></td>
					<td><form:errors path="description" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Module type</td>
					<td><form:select path="moduleType"
							items="${module.moduleTypeMap}" /></td>
					<td><form:errors path="moduleType" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><c:choose>
							<c:when test="${module.id == null}">
								<input type="submit" value="<spring:message code="label.add"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.update"/>" />
							</c:otherwise>
						</c:choose> <input type="button" value="<spring:message code="label.back" />"
						onClick="parent.location='showtechnology.html?id=${module.technology.id}'" />
					</td>
					<td></td>
				</tr>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
