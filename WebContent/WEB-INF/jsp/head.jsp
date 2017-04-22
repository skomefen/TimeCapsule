<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/index.css">

<title>客户关系管理系统</title>
</head>

<body style="text-align: center;">
	<h1>客户关系管理系统</h1>
	<div style="text-align:right;">
	<c:if test="${user!=null }">
		欢迎你：${user.nickname } 
		<a href="${pageContext.request.contextPath}/LogoutServlet" target="_top">注销</a>
	</c:if>
	<c:if test="${user==null }">
		<a href="${pageContext.request.contextPath}/RegisterUIServlet" target="_top">注册</a>
		<a href="${pageContext.request.contextPath }/LoginUIServlet" target="_top">登陆</a>
	</c:if>
	</div>

	<a href="${pageContext.request.contextPath }/AddCustomerServlet"
		target="main">添加客户</a>
	<a href="${pageContext.request.contextPath }/ListCustomerServlet"
		target="main">查看客户</a>
	<a href="${pageContext.request.contextPath }/CapsuleServlet?method=goadd"
		target="main">添加时间胶囊</a>
	<a href="${pageContext.request.contextPath }/CapsuleServlet?method=list"
		target="main">查看时间胶囊</a>
<!--  <a href="${pageContext.request.contextPath }/CapsuleServlet?method=intomailview"
		target="main">设置自动发送邮件</a>-->	
</body>
</html>
