<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/index.css">

<title>时间胶囊列表</title>
</head>

<script type="text/javascript">
	function gotopage(currentpage){
		if(currentpage<1  ||currentpage!=parseInt(currentpage) || currentpage > ${pagebean.totalpage}){
			alert("输入的是无效值");
			document.getElementById("pagenum").value='';
		}else{
			var pagesize = document.getElementById("pagesize").value;
			window.location.href = '${pageContext.request.contextPath}/CapsuleServlet?method=list&currentpage='+currentpage+'&pagesize='+pagesize;
		}
	}
	function changesize(pagesize,oldvalue){
		if(pagesize!=parseInt(pagesize) || pagesize==oldvalue){
			alert("请输入合法值");
			document.getElementById("pagesize").value=oldvalue;
		}else{
			window.location.href = '${pageContext.request.contextPath}/CapsuleServlet?method=list&pagesize='+pagesize;
		}
	}
	function del(id){
		if(window.confirm("你确认删除吗？")){
			window.location.href = '${pageContext.request.contextPath}/CapsuleServlet?method=delete&id='+id;
		}
	}
</script>
<body align="center" style="text-align: center">
	<table frame="border" width="100%" align="center">
		<tr>
			<td>id</td>
			<td>胶囊名</td>
			<td>保存日期</td>
			<td>读取日期</td>
			<td>描述</td>
			<td>所属邮件</td>
			<td>是否读过</td>
			<td>邮件发送次数</td>
			<td>所属用户id</td>
			
		</tr>
		<c:forEach var="capsule" items="${requestScope.pagebean.list }">
			<tr>
				<td>${capsule.id }</td>
				<td>${capsule.capsulename }</td>
				<td>${capsule.savedate }</td>
				<td>${capsule.readdate }</td>
				<td>${capsule.description }</td>
				<td>${capsule.email }</td>
				<td>${capsule.isreaded }</td>
				<td>${capsule.sendnum }</td>
				<td>${capsule.usernameid }</td>
				
				<td>
				<!--  <a href="${pageContext.request.contextPath }/CapsuleServlet?method=goedit&id=${capsule.id}">修改</a> -->
					<a href="javascript:void(0)" onclick="del('${capsule.id}')">删除</a>
				</td>  
				
				
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
