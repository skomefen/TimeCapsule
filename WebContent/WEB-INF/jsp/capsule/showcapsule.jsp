<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>挖出胶囊</title>
<script type="text/javascript">
	function findid() {
		var id =  document.getElementById("id").value;
		window.location.href='${pageContext.request.contextPath }/ShowCapsuleServlet?id='+id;
	}
</script>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
<h1>埋藏点</h1>
		
	<div align="right" >
		<c:if test="${sessionScope.user!=null }">
			昵称：${user.nickname }
			<a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>	
		</c:if>
		<c:if test="${sessionScope.user==null }">
			<a href="${pageContext.request.contextPath}/RegisterUIServlet">注册</a>
			<a href="${pageContext.request.contextPath }/LoginUIServlet">登陆</a>
		</c:if>
	</div>
	
	<div id="main" >
	<div align="center" style="text-align:center" >
	<a href="${pageContext.request.contextPath }/TreeHoleServlet">返回树洞</a><br>	
			输入发送给邮箱的唯一编码，找寻埋藏的时间胶囊<br>
			<input id="id" type="text" name="id" style="width:50%;color:#6D727D;text-align:center;" ><br>
			<a href="javascript:void(0)" onclick="findid()">挖掘</a>
	</div>
	<div align="right" style="text-align:center">
		<c:if test="${capsule!=null }">
			这是你的时间胶囊<br>
			${ capsule.capsulename}<br>
			<area style=" border-bottom-color:#6D727D;border-bottom-width: 3px; ">${ capsule.description}</area><br>
			<c:forEach var="entry" items="${files }">
				<c:url var="url" value="/DownLoadServlet">
    				<c:param name="filename" value="${entry.key}"></c:param>
    			</c:url>
				${entry.value} <a href="${url }">观看</a><br>
			</c:forEach>
		</c:if>
	</div>
	</div>
</body>
</html>