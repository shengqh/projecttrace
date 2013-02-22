<%@ include file="../include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
		<form:form method="post" id="costForm" name="costForm"
			action="saveprojectcostcenter.html" commandName="costForm">
			<form:hidden path="projectId" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<col width="200">
				<thead>
					<tr>
						<th scope="col">Name</th>
						<th scope="col">Percentage</th>
						<th scope="col">Is remaining cost</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${costForm.costCenters}" var="cc"
						varStatus="status">
						<tr>
							<form:hidden path="costCenters[${status.index}].id" />
							<td><form:input path="costCenters[${status.index}].name"
									id="name${status.index}" /></td>
							<td><form:input
									path="costCenters[${status.index}].percentage"
									id="percentage${status.index}" /></td>
							<td><form:checkbox
									path="costCenters[${status.index}].isRemainingCost"
									id="isRemainingCost${status.index}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<input type="submit" value="Save" id="submit" />
			<input type="button" value="<spring:message code="label.back" />"
				onClick="parent.location='showproject?id=${costForm.projectId}'" />
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
