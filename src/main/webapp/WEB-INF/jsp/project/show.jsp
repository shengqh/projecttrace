<%@ include file="../include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>CQS/VUMC Project Trace System</title>
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
						<th scope="col">&nbsp;</th>
						<th scope="col">&nbsp;</th>
						<th scope="col">logs</th>
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
						<c:choose>
							<c:when test="${projectDetailForm.canEdit}">
								<td>
									<form action="editprojecttask?taskid=${task.id}" method="post">
										<input type="submit"
											value="<spring:message	code="label.edit" />" />
									</form>

								</td>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${projectDetailForm.canManage}">
								<td>
									<form action="deleteprojecttask/${task.id}">
										<input type="submit"
											value="<spring:message	code="label.delete" />"
											onclick="return confirm('Are you sure you want to delete task ${task.name} ?')" />
									</form>
								</td>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td>
							<form
								action="showproject?projectid=${projectDetailForm.project.id}&taskid=${task.id}"
								method="post">
								<input type="submit"
									value="+" />
							</form>
						</td>
						<c:if test="${task.id == projectDetailForm.taskId || task.status==2}">
							<c:if test="${!empty task.statuses}">
								<c:forEach items="${task.statuses}" var="comment">
									<tr class="yellow">
										<td align="right">log:</td>
										<td colspan="3" class="comment" background="red">${comment.comment}</td>
										<td>${comment.statusString}</td>
										<td>${comment.updateUser}</td>
										<td>${comment.updateDate}</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</c:forEach>
							</c:if>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	<p class="message">${message}</p>
</body>
</html>
