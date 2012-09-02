<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<c:url value="/" var="homeUrl"/>
<c:url value="project" var="projectUrl"/>
<c:url value="user" var="userUrl"/>
<c:url value="projectmanage" var="projectAdminUrl"/>
<c:url value="usermanage" var="userAdminUrl"/>
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
		
		<sec:authorize access="hasRole('ROLE_MANAGER')">
		<li><a href="${projectAdminUrl}">Project manage</a></li>
		</sec:authorize>
		
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="${userAdminUrl}">User manage</a></li>
		</sec:authorize>

		<li><a href="${logoutUrl}">Logout</a></li>
	</ul>
	<span id="menu-username"><%=SecurityContextHolder.getContext().getAuthentication().getName()%></span>
	<br style="clear:left"/>
</div>