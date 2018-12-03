<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/manage/header.jsp" %>
	
	<table border="1" width="438" align="center" valign="center">
			<br>
			<tr>
				<th>序号</th>
				<th>分类名称</th>
				<th>分类描述</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${cs}" var="c" varStatus="vs">
			<tr class=${vs.index%2==0?'odd':'even' }>
				<th>${vs.count }</th>
				<th>${c.name }</th>
				<th>${c.description}</th>
				<th>
					<a href="#">修改</a>
					<a href="#">删除</a>
				</th>
			</tr>
			</c:forEach>
			<br>
	
	</table>
	
	
	
	
</body>
</html>