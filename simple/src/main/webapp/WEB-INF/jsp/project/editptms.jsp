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
			<form:hidden path="estimateOnly" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<h2 align="center">Estimated price = ${ptmForm.estimatedPrice}</h2>
			<table id="box-table-a">
				<col width="200">
				<col width="300">
				<col width="300">
				<thead>
					<tr>
						<th scope="col">Technology</th>
						<th scope="col">Module name</th>
						<th scope="col">Module type</th>
						<th scope="col">Description</th>
						<th scope="col">Sample number</th>
						<th scope="col">Other unit</th>
						<th scope="col">Per project</th>
						<th scope="col">Per unit</th>
						<th scope="col">Project setup fee</th>
						<th scope="col">Unit fee</th>
						<th scope="col">Total fee</th>
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
						<form:hidden path="modules[${status.index}].description" />
						<tr>
							<c:choose>
								<c:when test="${status.index == 0}">
									<td>${module.technology.technology}</td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when
											test="${lastTecName == module.technology.technology}">
											<td></td>
										</c:when>
										<c:otherwise>
											<td>${module.technology.technology}</td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<c:set var="lastTecName"
								value="${module.technology.technology}" />
							<td>${module.name}</td>
							<td>${module.moduleTypeString}</td>
							<td>${module.description}</td>
							<td><input name="modules[${status.index}].sampleNumber"
								value="${module.sampleNumber}" /></td>

							<c:choose>
								<c:when test="${1 == module.moduleTypeValue}">
									<form:hidden path="modules[${status.index}].otherUnit" />
									<td></td>
								</c:when>
								<c:otherwise>
									<td><input name="modules[${status.index}].otherUnit"
										value="${module.otherUnit}" /></td>
								</c:otherwise>
							</c:choose>

							<td>${module.pricePerProject}</td>
							<td>${module.pricePerUnit}</td>
							<td>${module.projectSetupFee}</td>
							<td>${module.unitFee}</td>
							<td>${module.totalFee}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="11" align="center"><c:if
								test="${!ptmForm.estimateOnly}">
								<input type="submit"
									value="<spring:message code="label.update" />" onclick="save()" />
							</c:if> <input type="submit" value="Estimate" onclick="estimate()" /> <c:if
								test="${!ptmForm.estimateOnly}">
								<input type="button"
									value="<spring:message code="label.back" />"
									onClick="parent.location='showproject?id=${ptmForm.projectId}'" />
							</c:if></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
