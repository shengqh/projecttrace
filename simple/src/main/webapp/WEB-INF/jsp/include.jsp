<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="edu.vanderbilt.cqs.UserType"%>
<c:set var="utAdmin" value="<%=UserType.ADMIN%>" />
<c:set var="utContact" value="<%=UserType.CONTACT%>" />
<c:set var="utStudyPI" value="<%=UserType.STUDYPI%>" />
<c:set var="utStaff" value="<%=UserType.VANGARD_STAFF%>" />
<c:set var="utFaculty" value="<%=UserType.VANGARD_FACULTY%>" />
<c:set var="dateFormat" value="Format: yyyy-mm-dd" />
<c:set var="nameFormat" value="Format: firstname lastname" />

