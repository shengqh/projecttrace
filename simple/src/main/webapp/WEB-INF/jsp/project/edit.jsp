<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<c:set var="isStaff" value="${projectForm.userType == utStaff || projectForm.userType == utFaculty}" />
	<c:set var="isFaculty" value="${projectForm.userType == utFaculty}" />
	<c:set var="isAdStaff" value="false" />
	<sec:authorize access="hasAnyRole('ROLE_VANGARD_ADSTAFF','ROLE_ADMIN')">
		<c:set var="isAdStaff" value="true" />
	</sec:authorize>

	<p>
	<h1>
		<c:choose>
			<c:when test="${projectForm.project.id == null}">
				<spring:message code="label.projectnew" />
			</c:when>
			<c:otherwise>
				<spring:message code="label.projectedit" /> : ${projectForm.project.name}
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
						<td>Study name</td>
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
						<td>BioVU project?</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:checkbox path="project.isBioVU" cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.isBioVU" />
								<td>${projectForm.project.isBioVU}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.isBioVU" cssClass="error" /></td>
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
								<td>${projectForm.project.contactDateString}</td>
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
							</c:when>
							<c:otherwise>
								<form:hidden path="project.contact" />
								<td>${projectForm.project.contact}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.contact" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Study PI</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.studyPI"
										cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.studyPI" />
								<td>${projectForm.project.studyPI}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.studyPI" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Quote amount</td>
						<c:choose>
							<c:when test="${isFaculty}">
								<td><form:input id="txt" path="project.quoteAmount"
										cssClass="txt" /></td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.quoteAmount" />
								<td>${projectForm.project.quoteAmount}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
						<td><form:errors path="project.quoteAmount" cssClass="error" /></td>
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
						<td>Work started</td>
						<c:choose>
							<c:when
								test="${isStaff}">
								<td><form:input id="txt" path="project.workStarted"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.workStarted" />
								<td>${projectForm.project.workStartedString}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.workStarted" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Work completed</td>
						<c:choose>
							<c:when
								test="${isStaff}">
								<td><form:input id="txt" path="project.workCompleted"
										cssClass="txt" /></td>
								<td>${dateFormat}</td>
							</c:when>
							<c:otherwise>
								<form:hidden path="project.workCompleted" />
								<td>${projectForm.project.workCompletedString}</td>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td><form:errors path="project.workCompleted"
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
								<td>${projectForm.project.requestCostCenterSetupInCORESString}</td>
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
							</c:when>
							<c:otherwise>
								<form:hidden path="project.requestedBy" />
								<td>${projectForm.project.requestedBy}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
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
								<td>${projectForm.project.billedInCORESString}</td>
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
							</c:when>
							<c:otherwise>
								<form:hidden path="project.billedBy" />
								<td>${projectForm.project.billedBy}</td>
							</c:otherwise>
						</c:choose>
						<td>&nbsp;</td>
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
						<td>Faculty</td>
						<td><c:choose>
								<c:when test="${isFaculty}">
									<form:select path="faculty" multiple="true"
										items="${projectForm.facultyList}" itemLabel="name"
										itemValue="id" size="6" />
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
						<td>Staff</td>
						<td><c:choose>
								<c:when test="${isFaculty}">
									<form:select path="staff" multiple="true"
										items="${projectForm.staffList}" itemLabel="name"
										itemValue="id" size="6" />
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
						<td colspan="4" align="center"><c:choose>
								<c:when test="${projectForm.project.id == null}">
									<input type="submit" value="<spring:message code="label.add"/>" />
								</c:when>
								<c:otherwise>
									<input type="submit"
										value="<spring:message code="label.update"/>" />
								</c:otherwise>
							</c:choose> <input type="button"
							value="<spring:message code="label.back" />"
							onClick="parent.location='showproject?id=${projectForm.project.id}'" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
