<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1>
		Edit : ${ptmForm.name}
	</h1>
	<p>
		<form:form method="post" action="saveptm.html"
			commandName="ptmForm">
			<form:hidden path="id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
			  	<col width="200">
				<thead>
					<tr>
						<th scope="col">Information</th>
						<th scope="col">Value</th>
						<th scope="col">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Sample number</td>
								<td><form:input id="txt" path="sampleNumber" cssClass="txt" /></td>
						<td><form:errors path="sampleNumber" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Other unit</td>
								<td><form:input id="txt" path="otherUnit" cssClass="txt" /></td>
						<td><form:errors path="otherUnit" cssClass="error" /></td>
					</tr>
					<tr>
						<td colspan="3" align="center">
									<input type="submit"
										value="<spring:message code="label.update"/>" />
							<input type="button"
							value="<spring:message code="label.back" />"
							onClick="parent.location='showproject?id=${ptmForm.technology.project.id}'" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
