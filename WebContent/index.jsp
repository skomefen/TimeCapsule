<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body style="text-align:center;">
	<h1>时间胶囊网站</h1>
	<div style="text-align:right;">
	<c:if test="${user!=null }">
		欢迎你：${user.nickname } 
		<a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>
	</c:if>
	<c:if test="${user==null }">
		<a href="${pageContext.request.contextPath}/RegisterUIServlet" >注册</a>
		<a href="${pageContext.request.contextPath }/LoginUIServlet">登陆</a>
	</c:if>
	</div>
	<div style="text-align:center">
		你是否有什么东西想存做回忆<br>
		你是否有什么话想说<br>
		告诉未来的自己<br>
		告诉那个你想告诉的人<br>
		那就把这些东西存在时间胶囊里，埋入树洞吧<br>
		进入
		<a href="${pageContext.request.contextPath }/TreeHoleServlet">树洞</a>
	</div>
</body>
</html>