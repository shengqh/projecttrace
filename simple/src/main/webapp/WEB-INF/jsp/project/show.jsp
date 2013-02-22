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
				alert("Estimate Price = " + response);
				document.forms[0].quoteAmount.value = response;
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}

	function showComments(taskid) {
		//alert(taskid);
		$
				.ajax({
					type : "POST",
					url : "getStatusList.html",
					data : "taskid=" + taskid.toString(),
					success : function(response) {
						//alert(response);
						var tbody = "";
						$
								.each(
										response,
										function(n, value) {
											//alert(n+' '+value);  
											var trs = "<tr class=\"comment\">";
											trs += "<td align=\"right\">log:</td>";
											trs += "<td colspan=\"1\" class=\"comment\" background=\"red\"><pre>"
													+ value.comment
													+ "</pre></td>";
											trs += "<td>" + value.statusString
													+ "</td>";
											trs += "<td>" + value.updateUser
													+ "</td>";
											trs += "<td>"
													+ value.updateDateString
													+ "</td>";
											trs += "<td></td>";
											trs += "<td></td>";
											trs += "<td></td>";
											trs += "</tr>";
											tbody += trs;
										});
						$('#task' + taskid.toString()).html(tbody);
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
		value="${projectForm.userType == utStaff || projectForm.userType == utFaculty}" />
	<c:set var="isFaculty" value="${projectForm.userType == utFaculty}" />
	<c:set var="isAdStaff" value="false" />
	<sec:authorize access="hasAnyRole('ROLE_VANGARD_ADSTAFF','ROLE_ADMIN')">
		<c:set var="isAdStaff" value="true" />
	</sec:authorize>

	<p>
	<h1>Project : ${projectForm.project.projectName}</h1>
	<hr>
	<h2>
		Basic Information
		<c:if test="${isStaff || isAdStaff}">
			| <a href="editproject?id=${projectForm.project.id}">Edit</a>
		</c:if>

	</h2>
	<table id="box-table-a" summary="Project Information">
		<col width="300">
		<col width="300">
		<c:if test="${isStaff || isAdStaff}">
			<col width="50">
		</c:if>
		<tr>
			<td>Contact date</td>
			<td><spring:eval expression="projectForm.project.contactDate" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Contact name</td>
			<td>${projectForm.project.contact}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>BioVU data request?</td>
			<td><tags:yesno
					value="${projectForm.project.isBioVUDataRequest}" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>BioVU sample request?</td>
			<td><tags:yesno
					value="${projectForm.project.isBioVUSampleRequest}" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>VANTAGE project?</td>
			<td><tags:yesno value="${projectForm.project.isVantage}" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Outside project?</td>
			<td><tags:yesno value="${projectForm.project.isOutside}" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Grant?</td>
			<td><tags:yesno value="${projectForm.project.isGranted}" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Study descriptive name</td>
			<td>${projectForm.project.name}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Study PI</td>
			<td>${projectForm.project.studyPI}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Quote amount</td>
			<td>${projectForm.project.quoteAmount}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Contract date</td>
			<td><spring:eval expression="projectForm.project.contractDate" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Assigned to (faculty)</td>
			<td><c:forEach items="${projectForm.project.facultyName}"
					var="user">
						${user}<br>
				</c:forEach></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Study status</td>
			<td>${projectForm.project.status}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Assigned to (staff)</td>
			<td><c:forEach items="${projectForm.project.staffName}"
					var="user">
						${user}<br>
				</c:forEach></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Work started (date)</td>
			<td><spring:eval expression="projectForm.project.workStarted" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Work completed (date)</td>
			<td><spring:eval expression="projectForm.project.workCompleted" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>BioVU data delivery to investigator (date)</td>
			<td><spring:eval
					expression="projectForm.project.bioVUDataDeliveryDate" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>BioVU redeposit (date)</td>
			<td><spring:eval
					expression="projectForm.project.bioVURedepositDate" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Request cost center setup in CORES (date)</td>
			<td><spring:eval
					expression="projectForm.project.requestCostCenterSetupInCORES" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Requested by (name)</td>
			<td>${projectForm.project.requestedBy}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Billed in CORES (date)</td>
			<td><spring:eval expression="projectForm.project.billedInCORES" /></td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Billed by (name)</td>
			<td>${projectForm.project.billedBy}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
		<tr>
			<td>Billed amount</td>
			<td>${projectForm.project.billedAmount}</td>
			<c:if test="${isStaff || isAdStaff}">
				<td />
			</c:if>
		</tr>
	</table>

	<h2>
		Cost Center To Bill
		<c:if test="${isFaculty || isAdStaff}">
			| <a href="editprojectcostcenter.html?id=${projectForm.project.id}">Edit</a>
		</c:if>
	</h2>
	<table id="box-table-a" summary="Cost Center To Bill">
		<thead>
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Percentage</th>
				<th scope="col">Is remaining cost</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projectForm.project.costCenters}" var="pcc">
				<tr>
					<td>${pcc.name}</td>
					<td>${pcc.percentage}%</td>
					<td>${pcc.isRemainingCost}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:choose>
		<c:when test="${isFaculty}">
			<br>
			<table>
				<tr>

					<td><h2>
							<form:form method="post" action="addprojecttechnology.html"
								commandName="projectForm">
								Technologies |
								<form:hidden path="project.id" />
								<form:select path="newTechnology"
									items="${projectForm.technologyList}" itemLabel="name"
									itemValue="id" />
								<input type="submit" value="Add technology" />
							</form:form>
						</h2></td>
					<td><h2>
							<form:form method="post"
								action="assignmoduleprices.html?projectid=${projectForm.project.id}">
							=&gt;<input type="submit" value="Assign prices"
									onclick="return confirm('Are you sure you want to assign current prices to this project? The old price information will be OVERRIDED and CANNOT be recovered!')" />
							</form:form>
						</h2></td>
					<td><h2>
							<form:form method="post"
								action="editptms.html?id=${projectForm.project.id}">
							=&gt;<input type="submit" value="Edit modules" />
							</form:form>
						</h2></td>
					<td><h2>
							<form:form>
								=&gt; <input type="button" value="Estimate"
									onclick="estimatePrice(${projectForm.project.id})" />
							</form:form>
						</h2></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<h2>Technologies</h2>
		</c:otherwise>
	</c:choose>
	<table id="box-table-a" summary="Technologies">
		<thead>
			<tr>
				<th scope="col">Technology</th>
				<th scope="col">Module</th>
				<th scope="col">Platform</th>
				<th scope="col">Sample number</th>
				<th scope="col">Other unit</th>
				<th scope="col">Per project</th>
				<th scope="col">Per unit</th>
				<th scope="col">Module type</th>
				<th scope="col">Project setup fee</th>
				<th scope="col">Unit fee</th>
				<th scope="col">Total fee</th>
				<th scope="col">Notes</th>
				<c:if test="${isFaculty}">
					<th scope="col">&nbsp;</th>
					<th scope="col">&nbsp;</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projectForm.project.technologies}" var="tec">
				<tr>
					<td>${tec.technology}</td>
					<td>&nbsp;</td>
					<td>${tec.platform}</td>
					<td>${tec.sampleNumber}</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>${tec.projectSetupFee}</td>
					<td>${tec.unitFee}</td>
					<td>${tec.totalFee}</td>
					<td>&nbsp;</td>
					<c:if test="${isFaculty}">
						<td>
							<form action="editprojecttechnology?id=${tec.id}" method="post">
								<input type="submit"
									value="<spring:message	code="label.edit" />" />
							</form>
						</td>
						<td>
							<form action="deleteprojecttechnology/${tec.id}">
								<input type="submit"
									value="<spring:message	code="label.delete" />"
									onclick="return confirm('Are you sure you want to delete?')" />
							</form>
						</td>
					</c:if>
				</tr>
				<c:forEach items="${tec.modules}" var="module">
					<tr>
						<td></td>
						<td>${module.name}</td>
						<td></td>
						<td>${module.sampleNumber}</td>
						<td>${module.otherUnit}</td>
						<td>${module.pricePerProject}</td>
						<td>${module.pricePerUnit}</td>
						<td>${module.moduleTypeString}</td>
						<td>${module.projectSetupFee}</td>
						<td>${module.unitFee}</td>
						<td>${module.totalFee}</td>
						<td>${module.description}</td>
						<c:if test="${isFaculty}">
							<td>
								<form action="editptm?id=${module.id}" method="post">
									<input type="submit"
										value="<spring:message	code="label.edit" />" />
								</form>
							</td>
							<td></td>
						</c:if>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>

	<h2>
		Files
		<c:if test="${isFaculty || isAdStaff}">
			| <a href="addfiles?projectid=${projectForm.project.id}">Add</a>
		</c:if>
	</h2>
	<table id="box-table-a" summary="Comments">
		<thead>
			<tr>
				<th scope="col">Create Date</th>
				<th scope="col">Create User</th>
				<th scope="col">File name</th>
				<th scope="col">File size</th>
				<c:if test="${isFaculty || isAdStaff}">
					<th scope="col">&nbsp;</th>
				</c:if>
				<c:if test="${isFaculty}">
					<th scope="col">&nbsp;</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projectForm.project.files}" var="file">
				<tr>
					<td>${file.createDate}</td>
					<td>${file.createUser}</td>
					<td>${file.fileName}</td>
					<td>${file.fileSizeString}</td>
					<c:if test="${isFaculty || isAdStaff}">
						<td>
							<form action="downloadfile/${file.id}" method="post">
								<input type="submit" value="Download" />
							</form>
						</td>
					</c:if>
					<c:if test="${isFaculty || isAdStaff}">
						<td>
							<form action="deletefile/${file.id}" method="post">
								<input type="submit"
									value="<spring:message	code="label.delete" />"
									onclick="return confirm('Are you sure you want to delete file ${file.fileName}?')" />
							</form>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Comments</h2>
	<table id="box-table-a" summary="Comments">
		<thead>
			<tr>
				<th scope="col">Date</th>
				<th scope="col">User/Comment</th>
				<c:if test="${isFaculty}">
					<th scope="col">&nbsp;</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projectForm.project.comments}" var="comment">
				<tr>
					<td>${comment.commentDate}</td>
					<td>${comment.commentUser} wrote : <br> <pre>${comment.comment}</pre>
					</td>
					<c:if test="${isFaculty}">
						<td>
							<form action="deleteprojectcomment/${comment.id}">
								<input type="submit"
									value="<spring:message	code="label.delete" />"
									onclick="return confirm('Are you sure you want to delete?')" />
							</form>
						</td>
					</c:if>
				</tr>
			</c:forEach>
			<form:form method="post" action="saveprojectcomment.html"
				commandName="projectForm">
				<form:hidden path="project.id" />
				<tr>
					<td>New comment</td>
					<c:set var="cols" value="1" />
					<c:if test="${isStaff || isAdStaff}">
						<c:set var="cols" value="2" />
					</c:if>
					<td colspan="${cols}"><form:textarea id="textarea"
							path="comment" rows="10" /></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="${cols}">
						<form action="saveprojectcomment" method="post">
							<input type="submit" value="<spring:message code="label.add"/>" />
						</form>
					</td>
				</tr>
			</form:form>
		</tbody>
	</table>

	<p class="message">${message}</p>
</body>
</html>
