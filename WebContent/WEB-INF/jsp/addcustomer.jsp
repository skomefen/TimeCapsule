<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/index.css">

<title>添加用户</title>
</head>
<body>
	<div align="center">
		<form action="${pageContext.request.contextPath}/AddCustomerServlet"
			method="post">
			<br />
			<br />
			<table id="formtable" >
				<tr>
					<td class="td1">登陆账号：</td>
					<td><input class="userinput" type="text" name="username"
						value=${form.username}> <span class="message">${form.errors.username}</span>
					</td>
				</tr>
				<tr>
					<td class="td1">密码</td>
					<td><input class="userinput" type="password" name="password"
						value=${form.password}> <span class="message">${form.errors.password}</span>
					</td>
				</tr>

				<tr>
					<td class="td1">确认密码</td>
					<td><input class="userinput" type="password" name="password2"
						value=${form.password2}> <span class="message">${form.errors.password2}</span>
					</td>
				</tr>

				<tr>
					<td class="td1">电子邮箱</td>
					<td><input class="userinput" type="text" name="email"
						value=${form.email}> <span class="message">${form.errors.email}</span>
					</td>
				</tr>

				<tr>
					<td class="td1">昵称</td>
					<td><input class="userinput" type="text" name="nickname"
						value=${form.nickname}> <span class="message">${form.errors.nickname}</span>
					</td>
				</tr>

			</table>
			<div id="formsubmit">
				<span><input class="btn" type="reset" value="重置"></span>
				&nbsp;&nbsp; <span><input class="btn" type="submit"
					value="添加"></span>

			</div>
		</form>
	</div>
</body>
</html>