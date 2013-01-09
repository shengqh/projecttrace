<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
<script type="text/javascript">
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
	<c:set var="canEdit" value="${projectForm.userType == utFaculty}" />
	<p>
	<h1>Project : ${projectForm.project.name}</h1>
	<hr>
	<h2>
		Basic Information
		<c:if test="${canEdit}">
			| <a href="editproject?id=${projectForm.project.id}">Edit</a>
		</c:if>

	</h2>
	<table id="box-table-a" summary="Project Information">
	  	<col width="300">
	  	<col width="300">
		<tr>
			<td>Contact date</td>
			<td>${projectForm.project.contactDateString}</td>
		</tr>
		<tr>
			<td>Contact name</td>
			<td>${projectForm.project.contact}</td>
		</tr>
		<tr>
			<td>Study PI</td>
			<td>${projectForm.project.studyPI}</td>
		</tr>
		<tr>
			<td>Quote amount</td>
			<td>${projectForm.project.quoteAmount}</td>
		</tr>
		<tr>
			<td>Assigned to (faculty)</td>
			<td><c:forEach items="${projectForm.project.facultyName}"
					var="user">
						${user}<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Assigned to (staff)</td>
			<td><c:forEach items="${projectForm.project.staffName}"
					var="user">
						${user}<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Work started (date)</td>
			<td>${projectForm.project.workStartedString}</td>
		</tr>
		<tr>
			<td>Work completed (date)</td>
			<td>${projectForm.project.workCompletedString}</td>
		</tr>
		<tr>
			<td>Cost center to bill</td>
			<td>${projectForm.project.costCenterToBill}</td>
		</tr>
		<tr>
			<td>Request cost center setup in CORES (date)</td>
			<td>${projectForm.project.requestCostCenterSetupInCORESString}</td>
		</tr>
		<tr>
			<td>Requested by (name)</td>
			<td>${projectForm.project.requestedBy}</td>
		</tr>
		<tr>
			<td>Billed in CORES (date)</td>
			<td>${projectForm.project.billedInCORESString}</td>
		</tr>
		<tr>
			<td>Billed by (name)</td>
			<td>${projectForm.project.billedBy}</td>
		</tr>
	</table>
	<c:choose>
		<c:when test="${canEdit}">
			<form:form method="post" action="addprojecttechnology.html"
				commandName="projectForm">
				<h2>
					Technologies |
					<form:hidden path="project.id" />
					<form:select path="newTechnology"
						items="${projectForm.technologyList}" itemLabel="name"
						itemValue="id" />
					<input type="submit" value="Add technology" />
				</h2>
			</form:form>
		</c:when>
		<c:otherwise>
			<h2>Technologies</h2>
		</c:otherwise>
	</c:choose>
	<table id="box-table-a" summary="Technologies">
		<thead>
			<tr>
				<th scope="col">Technology</th>
				<th scope="col">Modules</th>
				<th scope="col">Platform</th>
				<th scope="col">Sample number</th>
				<th scope="col">Other unit</th>
				<c:if test="${canEdit}">
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
					<c:if test="${canEdit}">
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
						<c:if test="${canEdit}">
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

	<h2>Comments</h2>
	<table id="box-table-a" summary="Comments">
		<thead>
			<tr>
				<th scope="col">Date</th>
				<th scope="col">User/Comment</th>
				<c:if test="${canEdit}">
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
					<c:if test="${canEdit}">
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
					<c:if test="${canEdit}">
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
