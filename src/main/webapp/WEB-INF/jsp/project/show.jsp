<%@ include file="../include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>CQS/VUMC Project Trace System</title>
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript">
	function showComments(taskid) {
		alert(taskid);
		$.ajax({
	        type: "POST",
    	    url: "getStatusList3.html",
    	    data: "taskid=" + taskid.toString(),
        	success: function(response){
        		$('#info').html(response);
           	},
        	error: function(e){
        		alert('Error: ' + e);
        	}
        });
	}
</script>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">
		Project : ${projectDetailForm.project.name}
		<c:if test="${projectDetailForm.canManage}">
							| <a
				href="addprojecttask?projectid=${projectDetailForm.project.id}"><spring:message
					code="label.tasknew" /></a>
		</c:if>
	</h1>
	<p>
	<h2 align="center">Total people time :
		${projectDetailForm.project.peopleTime} ; Total machine time :
		${projectDetailForm.project.machineTime}</h2>
	<p>

		<c:if test="${!empty projectDetailForm.project.tasks}">
			<table id="box-table-a" summary="Project Task">
				<thead>
					<tr>
						<th scope="col"><spring:message code="label.taskindex" /></th>
						<th scope="col"><spring:message code="label.taskname" /></th>
						<th scope="col"><spring:message code="label.taskpeopletime" /></th>
						<th scope="col"><spring:message code="label.taskmachinetime" /></th>
						<th scope="col"><spring:message code="label.taskstatus" /></th>
						<th scope="col"><spring:message code="label.taskupdateuser" /></th>
						<th scope="col"><spring:message code="label.taskupdatedate" /></th>
						<c:if test="${projectDetailForm.canEdit}">
							<th scope="col">&nbsp;</th>
						</c:if>
						<c:if test="${projectDetailForm.canManage}">
							<th scope="col">&nbsp;</th>
						</c:if>
						<th scope="col">comments</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projectDetailForm.project.tasks}" var="task">
						<c:choose>
							<c:when test="${task.status == 2}">
								<tr bordercolor="red">
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
						<td>${task.taskIndex}</td>
						<td>${task.name}</td>
						<td>${task.peopleTime}</td>
						<td>${task.machineTime}</td>
						<td>${task.statusString}</td>
						<c:choose>
							<c:when test="${task.status == 0}">
								<td></td>
								<td></td>
							</c:when>
							<c:otherwise>
								<td>${task.updateUser}</td>
								<td>${task.updateDate}</td>
							</c:otherwise>
						</c:choose>
						<c:if test="${projectDetailForm.canEdit}">
							<td>
								<form action="editprojecttask?taskid=${task.id}" method="post">
									<input type="submit"
										value="<spring:message	code="label.edit" />" />
								</form>

							</td>
						</c:if>
						<c:if test="${projectDetailForm.canManage}">
							<td>
								<form action="deleteprojecttask/${task.id}">
									<input type="submit"
										value="<spring:message	code="label.delete" />"
										onclick="return confirm('Are you sure you want to delete task ${task.name} ?')" />
								</form>
							</td>
						</c:if>
						<td><input type="submit" value="Comments"
							onclick="showComments(${task.id})" /></td>
						</tr>
					</c:forEach>
					<tr><td colspan="7"><div id="info" style="color: green;"></div></td></tr>
				</tbody>
			</table>
		</c:if>
	<c:if test="${!empty projectDetailForm.comments}">
		<table id="box-table-a" summary="Project Task Comments">
			<thead>
				<tr>
					<th scope="col"><spring:message code="label.taskstatus" /></th>
					<th scope="col"><spring:message code="label.taskupdateuser" /></th>
					<th scope="col"><spring:message code="label.taskupdatedate" /></th>
					<th scope="col"><spring:message code="label.comment" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projectDetailForm.comments}" var="comment">
					<tr>
						<td>${comment.statusString}</td>
						<td>${comment.updateUser}</td>
						<td>${comment.updateDate}</td>
						<td>${comment.comment}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<p class="message">${message}</p>
</body>
</html>
