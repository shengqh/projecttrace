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
						<td><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.name" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.name}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.name" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Contact date</td>
						<td><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.contactDate" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.contactDateString}
						</c:otherwise>
							</c:choose></td>
						<td>Format: mm/dd/yyyy</td>
						<td><form:errors path="project.name" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Quote amount</td>
						<td><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.quoteAmount" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.quoteAmount}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.quoteAmount" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Study status</td>
						<td><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:select path="project.status"
										items="${projectForm.statusMap}" />
								</c:when>
								<c:otherwise>
							${project.status}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.status" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Work started</td>
						<td><c:choose>
								<c:when
									test="${projectForm.userType == utStaff || projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.workStarted" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.workStartedString}
						</c:otherwise>
							</c:choose></td>
						<td>Format: mm/dd/yyyy</td>
						<td><form:errors path="project.workStarted" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Work completed</td>
						<td><c:choose>
								<c:when
									test="${projectForm.userType == utStaff || projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.workCompleted"
										cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.workCompletedString}
						</c:otherwise>
							</c:choose></td>
						<td>Format: mm/dd/yyyy</td>
						<td><form:errors path="project.workCompleted"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Cost center to bill</td>
						<td><c:choose>
								<c:when test="${projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.costCenterToBill"
										cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.costCenterToBill}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.costCenterToBill"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Request cost center setup in CORES</td>
						<td><c:choose>
								<c:when test="${projectForm.userType == utAdmin}">
									<form:input id="txt"
										path="project.requestCostCenterSetupInCORES" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.requestCostCenterSetupInCORES}
						</c:otherwise>
							</c:choose></td>
						<td>Format: mm/dd/yyyy</td>
						<td><form:errors path="project.requestCostCenterSetupInCORES"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Requested by (name)</td>
						<td><c:choose>
								<c:when test="${projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.requestedBy" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.requestedBy}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.requestedBy" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Billed in CORES</td>
						<td><c:choose>
								<c:when test="${projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.billedInCORES"
										cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.billedInCORES}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.billedInCORES"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>Billed by (name)</td>
						<td><c:choose>
								<c:when test="${projectForm.userType == utAdmin}">
									<form:input id="txt" path="project.billedBy" cssClass="txt" />
								</c:when>
								<c:otherwise>
							${project.billedBy}
						</c:otherwise>
							</c:choose></td>
						<td></td>
						<td><form:errors path="project.billedBy" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Contact</td>
						<td colspan="2"><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:select path="contact" multiple="true"
										items="${projectForm.contactList}" itemLabel="name"
										itemValue="id" size="6" />
								</c:when>
								<c:otherwise>
									<c:forEach items="${project.contactName}" var="user">
									${user}<br>
									</c:forEach>
								</c:otherwise>
							</c:choose></td>
						<td><form:errors path="contact" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Study PI</td>
						<td colspan="2"><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:select path="studyPI" multiple="true"
										items="${projectForm.studyPIList}" itemLabel="name"
										itemValue="id" size="6" />
								</c:when>
								<c:otherwise>
									<c:forEach items="${project.studyPIName}" var="user">
									${user}<br>
									</c:forEach>
								</c:otherwise>
							</c:choose></td>
						<td><form:errors path="studyPI" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Faculty</td>
						<td colspan="2"><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:select path="faculty" multiple="true"
										items="${projectForm.facultyList}" itemLabel="name"
										itemValue="id" size="6" />
								</c:when>
								<c:otherwise>
									<c:forEach items="${project.facultyName}" var="user">
									${user}<br>
									</c:forEach>
								</c:otherwise>
							</c:choose></td>
						<td><form:errors path="faculty" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Staff</td>
						<td colspan="2"><c:choose>
								<c:when
									test="${projectForm.userType == utFaculty || projectForm.userType == utAdmin}">
									<form:select path="staff" multiple="true"
										items="${projectForm.staffList}" itemLabel="name"
										itemValue="id" size="6" />
								</c:when>
								<c:otherwise>
									<c:forEach items="${project.staffName}" var="user">
									${user}<br>
									</c:forEach>
								</c:otherwise>
							</c:choose></td>
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
							onClick="parent.location='project.html'" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
