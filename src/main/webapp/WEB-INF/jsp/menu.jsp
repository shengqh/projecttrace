<%@ include file="include.jsp"%>

<c:url value="/" var="homeUrl"/>
<c:url value="project" var="projectUrl"/>
<c:url value="user" var="userUrl"/>
<c:url value="logout" var="logoutUrl"/>

<div class="menu">
	<ul>
		<li><a href="${homeUrl}">Home</a></li>
		
		<sec:authorize access="hasRole('ROLE_OBSERVER')">
		<li><a href="${projectUrl}">Project</a></li>
		</sec:authorize>
		
		<sec:authorize access="hasRole('ROLE_USER')">
		<li><a href="${userUrl}">User</a></li>
		</sec:authorize>
		
		<li><a href="${logoutUrl}">Logout</a></li>
	</ul>
	<span id="menu-username"><%=SecurityContextHolder.getContext().getAuthentication().getName()%></span>
	<br style="clear:left"/>
</div>