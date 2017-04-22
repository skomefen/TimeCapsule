<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加时间胶囊</title>
<script language="javascript" type="text/javascript"
	src="${pageContext.request.contextPath }/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function deffocus(id,defvalue){
		if(document.getElementById(id).value==defvalue){document.getElementById(id).value="";};
	}
	function defblur(id,defvalue){
		if(document.getElementById(id).value==""){document.getElementById(id).value=defvalue;};
	}
	function addfile(){
		var files = document.getElementById("files");
		
		var input = document.createElement("input");
		input.type="file";
		input.name='file';
		
		var btn = document.createElement("input");
		btn.type="button";
		btn.value="删除";
		btn.onclick = function del(){
			this.parentNode.parentNode.removeChild(this.parentNode);
		}
		
		var div = document.createElement("div");
		div.appendChild(input);
		div.appendChild(btn);
		
		files.appendChild(div);
	}
	function del(id){
		if(window.confirm("你确认删除吗？")){
			window.location.href = '${pageContext.request.contextPath}/CapsuleServlet?method=deletefile&id='+id;
		}
	}
</script>

<link rel="stylesheet" type="text/css" href="css/addcapsule.css">

</head>
<body>
<div id="container">
	<div id="main">
	<form action="${pageContext.request.contextPath }/CapsuleServlet?method=add"
		enctype="multipart/form-data" method="post">

		<div align="center" style="margin-top: 100px">
			<table width="50%" frame="border" border="1" 
				border-style="solid" style="text-align: left">
				<tr>
					<td>时间胶囊名字</td>
					<td class="tableinput">
					<input class="input" type="text" name="capsulename" value="${form.capsulename }"></td>${form.errors.capsulename }
				</tr>
				<c:if test="${form==null }">
				<tr>
					<td>存放时间</td>
					<td class="tableinput">
						<input class="input" type="text"
						onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy年MM月dd日 HH时mm分ss秒',vel:'readdate'})"
						value="存放日期不能少于一个月" onfocus="deffocus(this.id,'存放日期不能少于一个月')"
						onblur="defblur(this.id,'存放日期不能少于一个月')" /> <input id="readdate"
						type="hidden" name="readdate"></td>
				</tr>
				<tr>
					<td>你要告诉将来的谁取出胶囊</td>
					<td class="tableinput">
						<input class="input" id="email" type="text" name="email"
						value="请输入将来挖取胶囊的人的email"
						onfocus="deffocus(this.id,'请输入将来挖取胶囊的人的email')"
						onblur="defblur(this.id,'请输入将来挖取胶囊的人的email')"></td>
				</tr>
				</c:if>
				<c:if test="${form!=null }">
				<tr>
					<td>挖取胶囊时间</td>
					<td class="tableinput">
						<input class="input"
						 type="text"
						onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy年MM月dd日 HH时mm分ss秒',vel:'readdate'})"
						value="存放日期不能少于一个月"  /> ${form.errors.readdate }
						<input id="readdate"type="hidden" name="readdate"></td>
				</tr>
				<tr>
					<td>取出胶囊的邮件</td>
					<td class="tableinput">
						<input class="input" id="email" type="text" name="email"
						value="${form.email }">${form.errors.email }</td>
				</tr>
				</c:if>
				<tr>
					<td>描述时间胶囊的故事(500字以内)</td>
					<td><textarea class="area" name="description">${form.description }</textarea>${form.errors.description }</td>
				</tr>
				<tr>
					<td style="font-size: 15px">存放在胶囊里的物件<br>（仅支持"jpg","gif","txt","png"）
					</td>
					<td class="tableinput">
						<input style="width: 60%"" type="button" value="添加你要存放的物件" onclick="addfile()">${form.errors.upfiles}
					</td>
				</tr>
				<tr>
					<td></td>
					<td id="fileinput">
						<div id="files"></div>
					</td>
				</tr>

			</table>
			<input type="hidden" name="usernameid" value="${user.id }"> <input
				type="submit" value="埋下你的时间胶囊">
		</div>
	</form>
	</div>
</div>
</body>
</html>