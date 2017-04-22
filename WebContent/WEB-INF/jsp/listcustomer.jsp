<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<link rel="stylesheet" type="text/css" href="css/index.css">

<title>客户列表</title>
</head>

<script type="text/javascript">
	function gotopage(currentpage){
		if(currentpage<1  ||currentpage!=parseInt(currentpage) || currentpage > ${pagebean.totalpage}){
			alert("输入的是无效值");
			document.getElementById("pagenum").value='';
		}else{
			var pagesize = document.getElementById("pagesize").value;
			window.location.href = '${pageContext.request.contextPath}/ListCustomerServlet?currentpage='+currentpage+'&pagesize='+pagesize;
		}
	}
	function changesize(pagesize,oldvalue){
		if(pagesize!=parseInt(pagesize) || pagesize==oldvalue){
			alert("请输入合法值");
			document.getElementById("pagesize").value=oldvalue;
		}else{
			window.location.href = '${pageContext.request.contextPath}/ListCustomerServlet?pagesize='+pagesize;
		}
	}
	function del(id){
		if(window.confirm("你确认删除吗？")){
			window.location.href = '${pageContext.request.contextPath}/DeleteCustomerServlet?id='+id;
		}
	}
</script>
<body align="center" style="text-align: center">
	<table frame="border" width="85%" align="center">
		<tr>
			<td>id</td>
			<td>登陆账号</td>
			<td>密码</td>
			<td>电子邮箱</td>
			<td>昵称</td>
			<td>操作</td>
		</tr>
		<c:forEach var="user" items="${requestScope.pagebean.list }">
			<tr>
				<td>${user.id }</td>
				<td>${user.username }</td>
				<td>${user.password }</td>
				<td>${user.email }</td>
				<td>${user.nickname }</td>
				<td><a
					href="${pageContext.request.contextPath }/EditCustomerServlet?id=${user.id}">修改</a>
					<a href="javascript:void(0)" onclick="del('${user.id}')">删除</a></td>
			</tr>
		</c:forEach>

	</table>
	共${pagebean.totalpage }页， 每页
	<input type="text" id="pagesize" value="${pagebean.pagesize}"
		onchange="changesize(this.value,${pagebean.pagesize })"
		style="width: 30px" maxlength="2">条， 当前[${pagebean.currentpage }]页
		&nbsp;&nbsp;&nbsp; 
		<a href="javascript:void(0)"onclick="gotopage(${pagebean.previouspage})">上一页</a> 
		<c:forEach
			var="pagenum" items="${pagebean.pagebar }">
			<c:if test="${pagenum==pagebean.currentpage }">
				<font color='red'>${pagenum }</font>
			</c:if>
			<c:if test="${pagenum!=pagebean.currentpage }">
				<a href="javascript:void(0)" onclick="gotopage(${pagenum})">${pagenum }</a>
			</c:if>
		</c:forEach> 
		<a href="javascript:void(0)" onclick="gotopage(${pagebean.nextpage})">下一页</a>
		&nbsp;&nbsp;&nbsp; 跳转到
		<input type="text" id="pagenum"style="width: 30px"> 
		<input type="button" value="GO"onclick="gotopage(document.getElementById('pagenum').value)" />
</body>
</html>