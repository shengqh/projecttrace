<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>CQS Project Trace System</title>
	<style type="text/css">
		body {
			font-family: sans-serif;
		}
		.data, .data td {
			border-collapse: collapse;
			width: 100%;
			border: 1px solid #aaa;
			margin: 2px;
			padding: 2px;
		}
		.data th {
			font-weight: bold;
			background-color: #5C82FF;
			color: white;
		}
	</style>
</head>
<body>

<h2><spring:message code="label.usermanager"/></h2>

<form:form method="post" action="adduser.html" commandName="user">

	<table>
	<tr>
		<td><form:label path="firstname"><spring:message code="label.firstname"/></form:label></td>
		<td><form:input path="firstname" /></td> 
	</tr>
	<tr>
		<td><form:label path="lastname"><spring:message code="label.lastname"/></form:label></td>
		<td><form:input path="lastname" /></td>
	</tr>
	<tr>
		<td><form:label path="email"><spring:message code="label.email"/></form:label></td>
		<td><form:input path="email" /></td>
	</tr>
	<tr>
		<td><form:label path="telephone"><spring:message code="label.telephone"/></form:label></td>
		<td><form:input path="telephone" /></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="<spring:message code="label.addcontact"/>"/>
		</td>
	</tr>
</table>	
</form:form>

	
<h3>Users</h3>
<c:if  test="${!empty userList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>Email</th>
	<th>Telephone</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${userList}" var="user">
	<tr>
		<td>${user.lastname}, ${user.firstname} </td>
		<td>${user.email}</td>
		<td>${user.telephone}</td>
		<td><a href="deleteuser/${user.id}">delete</a></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
