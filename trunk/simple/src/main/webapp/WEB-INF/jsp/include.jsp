<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="edu.vanderbilt.cqs.UserType"%>
<c:set var="utAdmin" value="<%=UserType.ADMIN%>" />
<c:set var="utContact" value="<%=UserType.CONTACT%>" />
<c:set var="utStudyPI" value="<%=UserType.STUDYPI%>" />
<c:set var="utStaff" value="<%=UserType.VANGARD_STAFF%>" />
<c:set var="utFaculty" value="<%=UserType.VANGARD_FACULTY%>" />