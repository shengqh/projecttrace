<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
<script type="text/javascript">
	function showComments(taskid) {
		//alert(taskid);
		$.ajax({
	        type: "POST",
    	    url: "getStatusList.html",
    	    data: "taskid=" + taskid.toString(),
        	success: function(response){
        		//alert(response);
        		var tbody = "";
        		$.each(response,function(n,value) {   
        	    	//alert(n+' '+value);  
        	       	var trs = "<tr class=\"comment\">";
        	       	trs += "<td align=\"right\">log:</td>";
        	       	trs += "<td colspan=\"3\" class=\"comment\" background=\"red\"><pre>" + value.comment + "</pre></td>";
        	       	trs += "<td>" + value.statusString + "</td>";
        	       	trs += "<td>" + value.updateUser + "</td>";
        	       	trs += "<td>" + value.updateDateString + "</td>";
        	       	trs += "<td></td>";
        	    	trs += "<td></td>";
        	    	trs += "<td></td>";
        	    	trs += "</tr>";
        	        tbody += trs;         
        	    });  
        		$('#task' + taskid.toString()).html(tbody); 
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
			<div>
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
							<c:choose>
								<c:when test="${projectDetailForm.canEdit}">
									<th scope="col">&nbsp;</th>
								</c:when>
								<c:otherwise>
									<th></th>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${projectDetailForm.canManage}">
									<th scope="col">&nbsp;</th>
								</c:when>
								<c:otherwise>
									<th></th>
								</c:otherwise>
							</c:choose>
							<th scope="col">comments</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${projectDetailForm.project.tasks}" var="task">
							<c:choose>
								<c:when test="${task.status == -1}">
									<c:set var="bc" value="class=\"failed\"" />
								</c:when>
								<c:otherwise>
									<c:set var="bc" value="" />
								</c:otherwise>
							</c:choose>
							<tr ${bc}>
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
											<form action="editprojecttask?taskid=${task.id}"
												method="post">
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
								<td><form>
										<input type="button" value="+"
											onclick="showComments(${task.id})" />
									</form></td>
							</tr>
							<tbody id="task${task.id}"></tbody>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	<p class="message">${message}</p>
</body>
</html>
