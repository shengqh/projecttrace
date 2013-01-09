<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">Log Trace</h1>

	<p>
	<p class="message">${message}</p>
	<c:if test="${!empty logs}">
		<c:set var="haspassed" value="0" />
		<table id="box-table-a" summary="Log List">
			<thead>
				<tr>
					<th scope="col">Level</th>
					<th scope="col">Date</th>
					<th scope="col">User</th>
					<th scope="col">Action</th>
					<th scope="col">IP Address</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${logs}" var="log">
					<tr>
						<td>${log.levelString}</td>
						<td>${log.logDateString}</td>
						<td>${log.user}</td>
						<td>${log.action}</td>
						<td>${log.ipaddress}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>
