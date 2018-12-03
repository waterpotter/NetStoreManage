package com.power.using.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.power.using.domain.User;
import com.power.using.service.BusinessService;
import com.power.using.service.impl.BusinessServiceImpl;

public class PrivilegeServlet extends HttpServlet {

	private BusinessService s=new BusinessServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = s.login(username, password);
		if(user==null){
			response.getWriter().write("用户名或密码错误,2秒后转向登录页面");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/passport/adminlogin.jsp");
			return;
		}
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/manage/index.jsp");
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
