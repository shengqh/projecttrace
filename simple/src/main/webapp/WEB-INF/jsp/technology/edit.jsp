<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />

	<p>
	<h1 align="center">
		<c:choose>
			<c:when test="${technology.id == null}">
				New Technology
			</c:when>
			<c:otherwise>
				Edit Technology : ${technology.name}
			</c:otherwise>
		</c:choose>
	</h1>
	<p>
		<form:form method="post" action="savetechnology.html"
			commandName="technology">
			<form:hidden path="id" />
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table id="box-table-a">
				<tr>
					<td><form:label path="name">
							Name
						</form:label></td>
					<td><form:input id="txt" path="name" cssClass="txt" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="description">
							<spring:message code="label.description" />
						</form:label></td>
					<td><form:textarea id="textarea" path="description" rows="10"
							cols="60" /></td>
					<td><form:errors path="description" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><c:choose>
							<c:when test="${technology.id == null}">
								<input type="submit" value="<spring:message code="label.add"/>" />
							</c:when>
							<c:otherwise>
								<input type="submit"
									value="<spring:message code="label.update"/>" />
							</c:otherwise>
						</c:choose> <input type="button" value="<spring:message code="label.back" />"
						onClick="parent.location='technology.html'" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	<p class="message">${message}</p>
</body>
</html>
