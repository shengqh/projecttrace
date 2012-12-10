<%@ include file="include.jsp"%>
<html>
<head>
<title>CQS Project Trace System</title>
<style type="text/css">
body {
	font-family: sans-serif;
}

.data,.data td {
	border-collapse: collapse;
	width: 100%;
	border: 1px solid #aaa;
	margin: 2px;
	padding: 2px;
}

.data th {
	font-weight: bold;
	background-color: #5C82FF;
	color: white;
}
</style>
</head>
<body>

	<h2>
		<spring:message code="label.pipelinemanager" />
	</h2>

	<c:if test="${pipelineForm.canEdit}">
		<form:form method="post" action="savepipeline.html"
			commandName="pipelineForm.pipeline">
			<form:hidden path="id" />
			<table>
				<tr>
					<td><form:label path="name">
							<spring:message code="label.pipelinename" />
						</form:label></td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td colspan="2"><c:choose>
							<c:when test="${pipelineForm.pipeline.id != null}">
								<input type="submit"
									value="<spring:message code="label.updatepipeline"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.addpipeline"/>" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</form:form>
	</c:if>

	<h3>Pipelines</h3>
	<c:if test="${!empty pipelineForm.pipelineList}">
		<table class="data">
			<tr>
				<th>Name</th>
				<th>CreateDate</th>
				<th>Creator</th>
				<c:if test="${pipelineForm.canEdit}">
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</c:if>
			</tr>
			<c:forEach items="${pipelineForm.pipelineList}" var="pipeline">
				<tr>
					<td><a href="pipelinedetail?id=${pipeline.id}">${pipeline.name}</a></td>
					<td>${pipeline.createDate}</td>
					<td>${pipeline.creator.lastname},
						${pipeline.creator.firstname}</td>
					<c:if test="${pipelineForm.canEdit}">
						<td><a href="editpipeline?id=${pipeline.id}">edit</a></td>
						<td><a href="deletepipeline?id=${pipeline.id}">delete</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>
