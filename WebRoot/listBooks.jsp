<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/header.jsp" %>
	<a href="${pageContext.request.contextPath}">所有分类</a>
	<c:forEach items="${cs }" var="c">
		<a href="${pageContext.request.contextPath}/client/ClientServlet?op=showCategoryBooks&categoryId=${c.id}">${c.name }</a>	
	</c:forEach>
	<table border="10" width="438" align="center" valign="center">
			<br>
			<tr>
				<c:forEach items="${page.records }" var="b">
					<td align="center" valign="center">
						<img src="${pageContext.request.contextPath}/images/${b.path}/${b.filename}" alt="${b.filename}"/><br/>
						书名:${b.name }<br/>
						作者:${b.author }<br/>
						价格:${b.price }<br/>
						<a href="${pageContext.request.contextPath}/client/ClientServlet?op=showBookDetial&bookId=${b.id}">详情展示</a>
					</td>
				</c:forEach>
			</tr>
			
			<!--底部导航栏  -->
			<tr>
				<td colspan="3">
					<%@include file="/common/page.jsp" %>
				</td>
			</tr>
			<br>
	
	</table>
	
	
	
	
</body>
</html>