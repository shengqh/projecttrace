<%@ include file="../include.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href="resources/css/style.css"/>
	<title>Login</title>
</head>

<body>
	<form class="login-form" action="j_spring_security_check" method="post" >
		<fieldset>
			<legend>Login Here</legend>
			
			<p>
			<label for="j_username">Username</label>:
			<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
			</p>
			
			<p>
			<label for="j_password">Password</label>:
			<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
			</p>
			
			<p><input type="submit" value="Login"/></p>
		</fieldset>
	</form>
	<p class="message">${message}</p>
</body>
</html>