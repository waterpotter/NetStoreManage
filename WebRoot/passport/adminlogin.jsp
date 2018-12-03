<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>

</head>
<body>
	<h1>管理登录</h1>
	<form action="${pageContext.request.contextPath}/servlet/PrivilegeServlet" method="post">
		<table border="1" width="438" align="center">
			
			<tr>
				<td>用户名:</td>
				<td>
					<input name="username">
				</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td>
					<input type="password" name="password">
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<input type="submit" value="登录">
				</td>
			</tr>
		
		</table>
	
	
	
	</form>
	

</body>
</html>