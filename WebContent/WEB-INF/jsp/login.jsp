<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登陆界面</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css">

<script language="javascript" type="text/javascript">
	function register(){
		window.location.href = '${pageContext.request.contextPath }/RegisterServlet';
	}
</script>

</head>

<body>
	<div id="container">
		<div id="login">
			<div id="form">
				<form action="${pageContext.request.contextPath }/LoginServlet"
					method="post">
					<div id="input">
						<div>
							用户：<input type="text" name="username" /><br />
						</div>
						<div>
							密码：<input type="password" name="password" /><br />
						</div>
					</div>
					<div id="button">
						<input type="button" value="注册" onclick="register()"/>
						<input type="submit"value="登陆" />		
					</div>
				</form>
			</div>
		</div>
	<!--
	<div style="text-align: right;">
			<a href="${pageContext.request.contextPath }/ManageUIServlet">管理员登陆</a>
	</div>	 -->
	</div>

</body>

</html>