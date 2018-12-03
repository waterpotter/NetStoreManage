<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
第${page.pageNum }页/共${page.totalPageNum }页&nbsp;&nbsp;
					<a href="${pageContext.request.contextPath }${page.url}&num=${page.prePageNum}">上一页</a>
					<a href="${pageContext.request.contextPath }${page.url}&num=${page.nextPageNum}">下一页</a>