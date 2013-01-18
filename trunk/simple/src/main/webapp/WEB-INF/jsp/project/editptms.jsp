<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
<script type="text/javascript">
	function estimate() {
		//alert("Estimate");
		ptmForm.action = "estimateptms";
		ptmForm.submit();
	}
	function save() {
		//alert("Save");
		ptmForm.action = "saveptms";
		ptmForm.submit();
	}
</script>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
		<form:form method="post" id="ptmForm" name="ptmForm"
			commandName="ptmForm">
			<form:hidden path="projectId" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<h2 align="center">Estimated price = ${ptmForm.estimatedPrice}</h2>
			<table id="box-table-a">
				<col width="200">
				<thead>
					<tr>
						<th scope="col">Technology</th>
						<th scope="col">Module name</th>
						<th scope="col">Sample number</th>
						<th scope="col">Other unit</th>
						<th scope="col">Per project</th>
						<th scope="col">Per unit</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ptmForm.modules}" var="module"
						varStatus="status">
						<form:hidden path="modules[${status.index}].id" />
						<form:hidden path="modules[${status.index}].moduleId" />
						<form:hidden path="modules[${status.index}].name" />
						<form:hidden path="modules[${status.index}].technology.technology" />
						<form:hidden path="modules[${status.index}].pricePerProject" />
						<form:hidden path="modules[${status.index}].pricePerUnit" />
						<form:hidden path="modules[${status.index}].moduleType" />
						<tr>
							<td>${module.technology.technology}</td>
							<td>${module.name}</td>
							<td><input name="modules[${status.index}].sampleNumber"
								value="${module.sampleNumber}" /></td>
							<td><input name="modules[${status.index}].otherUnit"
								value="${module.otherUnit}" /></td>
							<td>${module.pricePerProject}</td>
							<td>${module.pricePerUnit}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" align="center"><input type="submit"
							value="<spring:message code="label.update" />" onclick="save()" />
							<input type="submit" value="Estimate" onclick="estimate()" /><input
							type="button" value="<spring:message code="label.back" />"
							onClick="parent.location='showproject?id=${ptmForm.projectId}'" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
