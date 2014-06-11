<%@ include file="include.jsp"%>

<c:url value="home" var="homeUrl" />
<c:url value="project" var="projectUrl" />
<c:url value="myproject" var="myProjectUrl" />
<c:url value="technology" var="technologyUrl" />
<c:url value="estimateptms" var="estimationUrl" />
<c:url value="showlog" var="logUrl" />
<c:url value="user" var="userUrl" />
<c:url value="role" var="roleUrl" />
<c:url value="changeownpassword" var="passwordUrl" />
<c:url value="logout" var="logoutUrl" />

<div class="menu">
	<ul>
		<li><a href="${homeUrl}">Home</a></li>

		<sec:authorize
			access="hasAnyRole('ROLE_PROJECT_VIEW', 'ROLE_PROJECT_EDIT')">
			<li><a href="${projectUrl}">Project</a></li>
			<li><a href="${myProjectUrl}">MyProject</a></li>
			<li><a href="${technologyUrl}">Technology</a></li>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_ESTIMATION')">
			<li><a href="${estimationUrl}">Estimation</a></li>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_USER_VIEW', 'ROLE_USER_EDIT')">
			<li><a href="${userUrl}">User</a></li>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_USER_EDIT">
			<li><a href="${roleUrl}">Role</a></li>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
			<li><a href="${logUrl}">Log</a></li>
		</sec:authorize>
		<li><a href="${passwordUrl}">Change Password</a></li>
		<li><a href="${logoutUrl}">Logout</a></li>
	</ul>
	<span id="menu-username"><%=SecurityContextHolder.getContext().getAuthentication()
					.getName()%></span> <br style="clear: left" />
</div>