<%@ include file="include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="resources/css/style.css" />
<title>CQS/VUMC Project Trace System</title>
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
</body>
</html>