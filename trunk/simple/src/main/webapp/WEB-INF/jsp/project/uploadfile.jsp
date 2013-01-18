<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ include file="../include.jsp"%>
<html>
<head>
<%@ include file="../include_head.jsp"%>
</head>

<body>
	<jsp:include page="../menu.jsp" />
	<form:form method="post" action="savefiles.html"
		modelAttribute="uploadForm" enctype="multipart/form-data">
		<form:hidden path="projectId" />
		<p class="message">${message}</p>
		<p>Select files to upload, file size is limited to 20M</p>
		<table id="fileTable">
			<tr>
				<td><input name="files[0]" type="file" /></td>
			</tr>
			<tr>
				<td><input name="files[1]" type="file" /></td>
			</tr>
			<tr>
				<td><input name="files[2]" type="file" /></td>
			</tr>
			<tr>
				<td><input name="files[3]" type="file" /></td>
			</tr>
			<tr>
				<td><input name="files[4]" type="file" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Upload" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>