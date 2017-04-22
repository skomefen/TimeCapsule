<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/FilterTest" method="post">
		<input type="text" name="username"></br>
		<textarea rows="" cols="" name="banword">
		</textarea>
		${banword }
		<input type="submit">
	</form>
</body>
</html>