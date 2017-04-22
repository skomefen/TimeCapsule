<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/index.css">

<title>管理界面</title>
</head>

<frameset rows="25%,*" frameborder="yes" border="1" framespacing="0">
	<frame noresize="noresize" scrolling="no" name="head"
		src="${pageContext.request.contextPath}/HeadUIServlet">
	<frame name="main" src="${pageContext.request.contextPath }/ListCustomerServlet">
</frameset>
</html>
