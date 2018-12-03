package com.power.using.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.power.using.constant.Constants;
import com.power.using.domain.Book;
import com.power.using.domain.Category;
import com.power.using.domain.Customer;
import com.power.using.domain.Order;
import com.power.using.domain.OrderItem;
import com.power.using.service.BusinessService;
import com.power.using.service.commons.Page;
import com.power.using.service.impl.BusinessServiceImpl;
import com.power.using.util.IdGenertor;
import com.power.using.util.WebUtil;
import com.power.using.web.beans.Cart;
import com.power.using.web.beans.CartItem;

public class ClientServlet extends HttpServlet {

	private BusinessService s = new BusinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("showIndex".equals(op)) {
			showIndex(request, response);
		} else if ("showCategoryBooks".equals(op)) {
			showCategoryBooks(request, response);
		} else if ("showBookDetial".equals(op)) {
			showBookDetial(request, response);
		} else if ("buy".equals(op)) {
			buy(request, response);
		} else if ("changeNum".equals(op)) {
			changeNum(request, response);
		} else if ("delOneItem".equals(op)) {
			delOneItem(request, response);
		} else if ("delAllItem".equals(op)) {
			delAllItem(request, response);
		} else if ("registeCustomer".equals(op)) {
			registeCustomer(request, response);
		} else if ("customerLogin".equals(op)) {
			customerLogin(request, response);
		} else if ("customerLogout".equals(op)) {
			customerLogout(request, response);
		} else if ("genOrder".equals(op)) {
			genOrder(request, response);
		} else if ("showCustomerOrders".equals(op)) {
			showCustomerOrders(request, response);
		}
	}

	/**
	 * 显示用户订单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showCustomerOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 验证用户登录
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute(Constants.CUSTOMER_LOGIN_FLAG);
		// 没有登录则去登录
		if (c == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		//登录:
		List<Order> os = s.findCustomerOrders(c);
		request.setAttribute("os", os);
		request.getRequestDispatcher("/showCustomerOrders.jsp").forward(request, response);

	}

	/**
	 * 用户注销
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void customerLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute(Constants.CUSTOMER_LOGIN_FLAG);
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void customerLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Customer c = s.customerLogin(username, password);

		request.getSession().setAttribute(Constants.CUSTOMER_LOGIN_FLAG, c);
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void registeCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Customer c = WebUtil.fillBean(request, Customer.class);
		s.addCustomer(c);
		response.getWriter().write("注册成功!2秒后转向主页");
		response.setHeader("Refresh", "2;URL=" + request.getContextPath());
	}

	/**
	 * 生成订单 结算购物车
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void genOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 验证用户登录
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute(Constants.CUSTOMER_LOGIN_FLAG);
		// 没有登录则去登录
		if (c == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		// 已经登录
		// 取出购物车的信息
		Cart cart = (Cart) session.getAttribute(Constants.HTTPSESSION_CART);
		if (cart == null) {
			response.getWriter().write("会话超时!");
			return;
		}
		Order order = new Order();
		order.setOrdernum(IdGenertor.genOrdernum());
		order.setQuantity(cart.getTotalQuantity());
		order.setMoney(cart.getTotalMoney());
		order.setCustomer(c);
		// 搞定单项
		List<OrderItem> oItems = new ArrayList<OrderItem>();
		for (Map.Entry<String, CartItem> me : cart.getItems().entrySet()) {
			CartItem cItem = me.getValue();// 购物车中的购物项
			OrderItem oItem = new OrderItem();
			oItem.setId(IdGenertor.genGUID());
			oItem.setBook(cItem.getBook());// 忘记会造成DAO异常
			oItem.setPrice(cItem.getMoney());
			oItem.setQuantity(cItem.getQunatity());
			oItems.add(oItem);
		}

		// 简历订单项和订单的关系
		order.setItems(oItems);
		// 保存订单:跳转到在线支付页面
		s.genOrder(order);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/pay.jsp").forward(request, response);

	}

	/**
	 * 删除购物车中的所有购买享
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delAllItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute(Constants.HTTPSESSION_CART);
		response.sendRedirect(request.getContextPath() + "/showCart.jsp");
	}

	/**
	 * 根据bookId删除对应的书籍
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delOneItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookId = request.getParameter("bookId");
		Cart cart = (Cart) request.getSession().getAttribute(Constants.HTTPSESSION_CART);
		cart.getItems().remove(bookId);
		response.sendRedirect(request.getContextPath() + "/showCart.jsp");

	}

	/**
	 * 修改购物项的数量
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void changeNum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String num = request.getParameter("num");
		String bookId = request.getParameter("bookId");
		System.out.println(num);
		// 取出购物车
		Cart cart = (Cart) request.getSession().getAttribute(Constants.HTTPSESSION_CART);
		CartItem item = cart.getItems().get(bookId);
		item.setQunatity(Integer.parseInt(num));
		response.sendRedirect(request.getContextPath() + "/showCart.jsp");
	}

	/**
	 * 买书,购物车添加书籍 书籍放入购物车
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);

		// 第一次调用时(==第一次访问主页时)session 的时候创建回话
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.HTTPSESSION_CART);
		// 第一次的时候
		if (cart == null) {
			cart = new Cart();
			session.setAttribute(Constants.HTTPSESSION_CART, cart);
		}

		// 此处必定有值
		cart.addBook(book);
		// 暂时先不用异步返回结果
		// 按照返回主页处理
		response.getWriter().write("购买成功!２秒后转向主页");
		response.setHeader("Refresh", "2;URL=" + request.getContextPath());

	}

	/**
	 * 根据bookId来寻找书籍详情
	 * 
	 * @param request
	 * @param response
	 */
	private void showBookDetial(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/bookDetial.jsp").forward(request, response);

	}

	/**
	 * 按照分类查询分页信息
	 * 
	 * @param request
	 * @param response
	 */
	private void showCategoryBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		String num = request.getParameter("num");
		String categoryId = request.getParameter("categoryId");
		Page page = s.findBookPageRecords(num, categoryId);
		page.setUrl("/client/ClientServlet?op=showCategoryBooks&categoryId=" + categoryId);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/listBooks.jsp").forward(request, response);

	}

	/**
	 * 展现主页
	 * 
	 * @param request
	 * @param response
	 */
	private void showIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查询所有分类
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		// 查询所有书籍:分页
		String num = request.getParameter("num");
		Page page = s.findBookPageRecords(num);
		page.setUrl("/client/ClientServlet?op=showIndex");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
