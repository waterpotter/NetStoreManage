<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/manage/header.jsp" %>
	
	<table border="1" width="738" align="center" valign="center">
			<br>
			<tr>
				<th>序号</th>
				<th>图片</th>
				<th>书名</th>
				<th>作者</th>
				<th>单价</th>
				<th>描述</th>
				<th>所属分类</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${page.records}" var="b" varStatus="vs">
			<tr class=${vs.index%2==0?'odd':'even' }>
				<th>${vs.count }</th>
				<th>
					<img src="${pageContext.request.contextPath}/images/${b.path}/${b.filename}" alt="${b.filename}"/>
				</th>
				<th>${b.name }</th>
				<th>${b.author }</th>
				<th>${b.price }</th>
				<th>${b.description }</th>
				<th>${b.category.name}</th>
				<th>
					<a href="#">修改</a>
					<a href="#">删除</a>
				</th>
			</tr>
			</c:forEach>
			<!--底部导航栏  -->
			<tr>
				<td colspan="8">
					<%@include file="/common/page.jsp" %>
				</td>
			</tr>
			<br>
	
	</table>
	
	
	
	
</body>
</html>