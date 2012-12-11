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
	<p>
	<h1>Project : ${projectDetailForm.project.name}</h1>
	<hr>
	<h2>Basic Information</h2>
	<table id="box-table-a" summary="Project Information">
		<tr>
			<td>Contact date</td>
			<td>${projectDetailForm.project.contactDate}</td>
		</tr>
		<tr>
			<td>Contact name</td>
			<td><c:forEach items="${projectDetailForm.project.contactName}"
					var="user">
						${user}<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Study PI</td>
			<td><c:forEach items="${projectDetailForm.project.studyPIName}"
					var="user">
						${user}<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Quote amount</td>
			<td>${projectDetailForm.project.quoteAmount}</td>
		</tr>
		<tr>
			<td>Assigned to (faculty)</td>
			<td><c:forEach items="${projectDetailForm.project.facultyName}"
					var="user">
						${user}<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Assigned to (staff)</td>
			<td><c:forEach items="${projectDetailForm.project.staffName}"
					var="user">
						${user}<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Work started (date)</td>
			<td>${projectDetailForm.project.workStarted}</td>
		</tr>
		<tr>
			<td>Work completed (date)</td>
			<td>${projectDetailForm.project.workCompleted}</td>
		</tr>
		<tr>
			<td>Cost center to bill</td>
			<td>${projectDetailForm.project.costCenterToBill}</td>
		</tr>
		<tr>
			<td>Request cost center setup in CORES (date)</td>
			<td>${projectDetailForm.project.requestCostCenterSetupInCORES}</td>
		</tr>
		<tr>
			<td>Requested by (name)</td>
			<td>${projectDetailForm.project.requestedBy}</td>
		</tr>
		<tr>
			<td>Billed in CORES (date)</td>
			<td>${projectDetailForm.project.billedInCORES}</td>
		</tr>
		<tr>
			<td>Billed by (name)</td>
			<td>${projectDetailForm.project.billedBy}</td>
		</tr>
		<tr>
			<td>Comments</td>
			<td><pre>${projectDetailForm.project.comments}</pre></td>
		</tr>
	</table>
	<h2>Technologies</h2>
	<table id="box-table-a" summary="Technologies">
		<thead>
			<tr>
				<th scope="col">Technology</th>
				<th scope="col">Modules</th>
				<th scope="col">Platform</th>
				<th scope="col">Sample number</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projectDetailForm.project.technologies}"
				var="tec">
				<tr>
					<td>${tec.technology}</td>
					<td></td>
					<td>${tec.platform}</td>
					<td>${tec.sampleNumber}</td>
				</tr>
				<c:forEach items="${tec.modules}" var="module">
					<tr>
						<td></td>
						<td>${module.name}</td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>

	<p class="message">${message}</p>
</body>
</html>
