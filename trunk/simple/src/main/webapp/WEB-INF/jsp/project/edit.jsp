<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
<script type="text/javascript">
	function estimatePrice(projectid) {
		//alert(projectid);
		$.ajax({
			type : "POST",
			url : "getEstimatePrice",
			data : "projectid=" + projectid.toString(),
			success : function(response) {
				//alert(response);
				document.forms[0].quoteAmount.value = response;
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
</script>

</head>

<body>
	<jsp:include page="../menu.jsp" />

	<c:set var="isStaff"
		value="${projectForm.userType == utStaff || projectForm.userType == utFaculty || projectForm.userType == utAdmin}" />
	<c:set var="isFaculty"
		value="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}" />
	<c:set var="isAdStaff" value="false" />
	<c:set var="isNewProject" value="${projectForm.project.id == null}" />
	<sec:authorize access="hasAnyRole('ROLE_VANGARD_ADSTAFF','ROLE_ADMIN')">
		<c:set var="isAdStaff" value="true" />
	</sec:authorize>

	<p>
	<h1>
		<c:choose>
			<c:when test="${isNewProject}">
				<spring:message code="label.projectnew" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.projectedit" /> : ${projectForm.project.projectName}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="saveproject.html"
			commandName="projectForm">
			<form:hidden path="project.id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<col width="300">
				<thead>
					<tr>
						<th scope="col">Information</th>
						<th scope="col">Value</th>
						<th scope="col">&nbsp;</th>
						<th scope="col">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Project</td>
						<td>${projectForm.project.projectName}</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Contact date</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.contactDate"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.contactDate" />
								<td><spring:eval
										expression="projectForm.project.contactDate" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.contactDate" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Contact name</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.contact"
										cssClass="txt" /></td>
								<td>${nameFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.contact" />
								<td>${projectForm.project.contact}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.contact" cssClass="error" /></td>
					</tr>
					<tr>
						<td>BioVU sample request?</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:checkbox path="project.isBioVUSampleRequest"
										cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.isBioVUSampleRequest" />
								<td>${projectForm.project.isBioVUSampleRequest}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.isBioVUSampleRequest"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>BioVU data request?</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:checkbox path="project.isBioVUDataRequest"
										cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.isBioVUDataRequest" />
								<td>${projectForm.project.isBioVUDataRequest}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.isBioVUDataRequest"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>VANTAGE project?</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:checkbox path="project.isVantage" cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.isVantage" />
								<td>${projectForm.project.isVantage}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.isVantage" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Outside project?</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:checkbox path="project.isOutside" cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.isOutside" />
								<td>${projectForm.project.isOutside}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.isOutside" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Grant?</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:checkbox path="project.isGranted" cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.isGranted" />
								<td>${projectForm.project.isGranted}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.isGranted" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Study descriptive name</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.name" cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.name" />
								<td>${projectForm.project.name}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.name" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Study PI</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.studyPI"
										cssClass="txt" /></td>
								<td>${nameFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.studyPI" />
								<td>${projectForm.project.studyPI}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.studyPI" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Quote amount</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="quoteAmount" path="project.quoteAmount"
										cssClass="txt" /></td>
								<td><form>
										<input type="button" value="Estimate"
											onclick="estimatePrice(${projectForm.project.id})" />
									</form></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.quoteAmount" />
								<td>${projectForm.project.quoteAmount}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.quoteAmount" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Assigned to (faculty)</td>
						<td><c:choose>
								<c:when test="${isFaculty}">
									<form:select path="faculty" multiple="true"
										items="${projectForm.facultyList}" itemLabel="name"
										itemValue="id" size="10" />
								</c:when>
								<c:otherwise>
									<form:hidden path="faculty" />
									<c:forEach items="${projectForm.project.facultyName}"
										var="user">
									${user}<br>
									</c:forEach>
								</c:otherwise>
							</c:choose></td>
						<td>&nbsp;</td>
						<td><form:errors path="faculty" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Study status</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:select path="project.status"
										items="${projectForm.statusMap}" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.status" />
								<td>${projectForm.project.status}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.status" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Contract date</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.contractDate"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.contractDate" />
								<td><spring:eval
										expression="projectForm.project.contractDate" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.contractDate" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Assigned to (staff)</td>
						<td><c:choose>
								<c:when test="${isFaculty}">
									<form:select path="staff" multiple="true"
										items="${projectForm.staffList}" itemLabel="name"
										itemValue="id" size="10" />
								</c:when>
								<c:otherwise>
									<form:hidden path="staff" />
									<c:forEach items="${projectForm.project.staffName}" var="user">
									${user}<br>
									</c:forEach>
								</c:otherwise>
							</c:choose></td>
						<td>&nbsp;</td>
						<td><form:errors path="staff" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Work started</td>
						<c:choose>
							<c:when test="${isStaff}">
								<td><form:input id="txt" path="project.workStarted"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.workStarted" />
								<td><spring:eval
										expression="projectForm.project.workStarted" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.workStarted" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Work completed</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.workCompleted"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.workCompleted" />
								<td><spring:eval
										expression="projectForm.project.workCompleted" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.workCompleted"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>BioVU project - data delivery to investigator (date)</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt"
										path="project.bioVUDataDeliveryDate" cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.bioVUDataDeliveryDate" />
								<td><spring:eval
										expression="projectForm.project.bioVUDataDeliveryDate" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.bioVUDataDeliveryDate"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>BioVU project - redeposit (date)</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.bioVURedepositDate"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.bioVURedepositDate" />
								<td><spring:eval
										expression="projectForm.project.bioVURedepositDate" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.bioVURedepositDate"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Cost center to bill</td>
						<c:choose>
							<c:when test="${isAdStaff}">
								<td><form:input id="txt" path="project.costCenterToBill"
										cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.costCenterToBill" />
								<td>${projectForm.project.costCenterToBill}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.costCenterToBill"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Request cost center setup in CORES</td>
						<c:choose>
							<c:when test="${isAdStaff}">
								<td><form:input id="txt"
										path="project.requestCostCenterSetupInCORES" cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.requestCostCenterSetupInCORES" />
								<td><spring:eval
										expression="projectForm.project.requestCostCenterSetupInCORES" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.requestCostCenterSetupInCORES"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Requested by (name)</td>
						<c:choose>
							<c:when test="${isAdStaff}">
								<td><form:input id="txt" path="project.requestedBy"
										cssClass="txt" /></td>
								<td>${nameFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.requestedBy" />
								<td>${projectForm.project.requestedBy}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.requestedBy" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Billed in CORES</td>
						<c:choose>
							<c:when test="${isAdStaff}">
								<td><form:input id="txt" path="project.billedInCORES"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.billedInCORES" />
								<td><spring:eval
										expression="projectForm.project.billedInCORES" /></td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.billedInCORES"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Billed by (name)</td>
						<c:choose>
							<c:when test="${isAdStaff}">
								<td><form:input id="txt" path="project.billedBy"
										cssClass="txt" /></td>
								<td>${nameFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.billedBy" />
								<td>${projectForm.project.billedBy}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.billedBy" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Billed amount</td>
						<c:choose>
							<c:when test="${isAdStaff}">
								<td><form:input id="txt" path="project.billedAmount"
										cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.billedAmount" />
								<td>${projectForm.project.billedAmount}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.billedAmount" cssClass="error" /></td>
					</tr>
					<tr>
						<td colspan="4" align="center"><c:choose>
								<c:when test="${isNewProject}">
									<input type="submit" value="<spring:message code="label.add"/>" />
									<input type="button"
										value="<spring:message code="label.back" />"
										onClick="parent.location='project'" />
								</c:when>
								<c:otherwise>
									<input type="submit"
										value="<spring:message code="label.update"/>" />
									<input type="button"
										value="<spring:message code="label.back" />"
										onClick="parent.location='showproject?id=${projectForm.project.id}'" />
								</c:otherwise>
							</c:choose></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
