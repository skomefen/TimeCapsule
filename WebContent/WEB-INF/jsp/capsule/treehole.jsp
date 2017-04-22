<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>树洞</title>
<link rel="stylesheet" type="text/css" href="css/treehole.css">
</head>
<body>
	<div id=container>
		<div id=head>
			<h1>这是一个树洞</h1>
			<div id="item">
				<c:if test="${sessionScope.user!=null }">
					昵称：${user.nickname }
					<a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>			
				</c:if>
			
				<c:if test="${sessionScope.user==null }">
					<a href="${pageContext.request.contextPath}/RegisterUIServlet">注册</a>
					<a href="${pageContext.request.contextPath }/LoginUIServlet">登陆</a>
				</c:if>
			</div>
		</div>
		<div class="clear"></div>
		<div id=main>
			<a href="${pageContext.request.contextPath}/AddCapsuleServlet">埋下时间胶囊</a><br>
			<a href="${pageContext.request.contextPath}/FindCapsuleServlet">挖出时间胶囊</a><br> 
		<!--	<a href=#>已经挖出的时间胶囊</a><br>-->
	
		</div>
	</div>
</body>
</html>