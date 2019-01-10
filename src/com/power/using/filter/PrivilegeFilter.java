package com.power.using.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.power.using.domain.Function;
import com.power.using.domain.Role;
import com.power.using.domain.User;
import com.power.using.service.BusinessService;
import com.power.using.service.impl.BusinessServiceImpl;

/**
 * 只拦截后台资源的访问
 * @author q1203
 *
 */
public class PrivilegeFilter implements Filter {

	private BusinessService s=new BusinessServiceImpl();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request;
		HttpServletResponse response;
		try{
			request=(HttpServletRequest) req;
			response=(HttpServletResponse) res;
		}catch (Exception e) {
			throw new RuntimeException("no-http request or reqponse");
		}
		
		
		//检查用户是否登录
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		//没有登陆:转向登录页面
		if(user==null){
			request.getRequestDispatcher("/passport/adminlogin.jsp").forward(request, response);;
			return;
		}
		//登录
			//把当前登录用户访问的功能存起来
		Set<Function> funs=new HashSet<Function>();
			//查询对应的角色
		List<Role> roles = s.findRolesByUser(user);
			//遍历角色,查询功能
		for (Role role : roles) {
			List<Function> functions = s.findFunctionsByRole(role);
			funs.addAll(functions);
		}
		//得到用户当前访问的uri地址
		///23-002-netstore/manage/addcategory.jsp
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		if(queryString!=null){
			uri=uri+"?"+queryString;
		}
		//   /manage/addcategory.jsp
		uri.replace(request.getContextPath(), "");
			//对比是否在权限访问之内
		boolean hasPermission=false;//是否有权限
		for (Function f : funs) {
			if(uri.equals(f.getUri())){
				hasPermission=true;
				break;
			}
		}
				//不在:提示没权限
		if(!hasPermission){
			response.getWriter().write("您没有权限");
			return;
		}
		
		//不管有无权限都需要放行
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	 
	}

}
