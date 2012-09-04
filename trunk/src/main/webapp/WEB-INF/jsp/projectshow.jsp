<%@ include file="include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>CQS/VUMC Project Trace System</title>
</head>

<body>
	<jsp:include page="menu.jsp" />

	<p>
	<h1 align="center">
		Project : ${projectDetailForm.project.name}
		<c:if test="${projectDetailForm.canManage}">
							| <a href="addprojecttask?id=${projectDetailForm.project.id}"><spring:message
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
						<th scope="col"><spring:message
								code="label.taskpeopletime" /></th>
						<th scope="col"><spring:message
								code="label.taskmachinetime" /></th>
						<th scope="col"><spring:message
								code="label.taskstatus" /></th>
						<th scope="col"><spring:message
								code="label.taskupdateuser" /></th>
						<th scope="col"><spring:message
								code="label.taskupdatedate" /></th>
						<c:if test="${projectDetailForm.canEdit}">
							<th scope="col">&nbsp;</th>
						</c:if>
						<c:if test="${projectDetailForm.canManage}">
							<th scope="col">&nbsp;</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projectDetailForm.project.tasks}" var="task">
						<tr>
							<td>${task.taskIndex}</td>
							<td>${task.name}</td>
							<td>${task.peopleTime}</td>
							<td>${task.machineTime}</td>
							<td>${task.status}</td>
							<td>${task.updateUser}</td>
							<td>${task.updateDate}</td>
							<c:if test="${projectDetailForm.canEdit}">
								<td><a href="editprojecttask?id=${task.id}"><spring:message
											code="label.edit" /></a></td>
							</c:if>
							<c:if test="${projectDetailForm.canManage}">
								<td><a href="deleteprojecttask?id=${task.id}"><spring:message
											code="label.delete" /></a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
</body>
</html>
