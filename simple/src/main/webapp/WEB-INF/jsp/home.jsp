<%@ include file="include.jsp"%>
<html>
<head>
<%@ include file="include_head.jsp"%>
</head>

<body>
	<jsp:include page="menu.jsp" />
	<h1 id="banner">Home</h1>
	<hr />

	<p>
		Welcome <span id="username"><%=SecurityContextHolder.getContext().getAuthentication()
					.getName()%></span>!
	</p>
	<p>The project trace system is used to monitor the procedure of
		project running at CQS/VUMC.</p>
	<p>Last modified: 2013-05-07.</p>
</body>
</html>