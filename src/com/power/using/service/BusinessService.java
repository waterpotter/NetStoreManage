package com.power.using.service;

import java.util.List;

import com.power.using.domain.Book;
import com.power.using.domain.Category;
import com.power.using.domain.Customer;
import com.power.using.domain.Function;
import com.power.using.domain.Order;
import com.power.using.domain.OrderItem;
import com.power.using.domain.Role;
import com.power.using.domain.User;
import com.power.using.service.commons.Page;

public interface BusinessService {
	
	/**
	 * 添加分类
	 * @param c
	 */
	void addCategory(Category c);
	
	/**
	 * 查询所有的分类
	 * @return
	 */
	List<Category> findAllCategories();
	
	/**
	 * 根据id查询分类
	 * @param categoryId
	 * @return 没有找到,返回null
	 */
	Category findCategoryById(String categoryId);
	
	/**
	 * 添加书籍
	 * @param book
	 * 如果book关联的Category为null,要跑出参数错误异常
	 */
	void addBook(Book book);
	
	/**
	 * 根据bookId,查询Book
	 * @param bookId
	 * @return
	 * 一个分类对应着多本书
	 * 要求  ,返回的是Book还有分类
	 */
	Book findBookById(String bookId);
	
	/**
	 * 根据用户查看的页码,返回封装了所有与分页有关的数据(Page对象)
	 * @param num 要看的页码,如果为null或"",则默认为1
	 * @return
	 */
	Page findBookPageRecords(String num);

	/**
	 * 根据用户查看的页码和分类的id,返回封装了所有与分页有关的数据(Page对象)
	 * @param num
	 * @param categoryId
	 * @return
	 */
	Page findBookPageRecords(String num, String categoryId);
	
	/**
	 * 添加用户
	 * @param c
	 */
	void addCustomer(Customer c);
	
	/**
	 * 根据id查找用户
	 * @param customerId
	 * @return
	 */
	Customer findCustomer(String customerId);
	
	/**
	 * 根据用户名密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	Customer customerLogin(String username,String password);
	
	/**
	 * 生成订单,必须有订单项,必须有关联的客户信息
	 * @param o
	 * @param items
	 * @param c
	 */
	void genOrder(Order o);
	
	/**
	 * 根据订单号查询订单
	 * @param OrderNum
	 * @return
	 */
	Order findOrderByNum(String OrderNum);
	
	/**
	 * 查询客户的订单
	 * @param c
	 */
	List<Order> findCustomerOrders(Customer c);

	/**
	 * 更改订单状态
	 * @param order
	 */
	void changeOrderStatus(Order order);
	
	/**
	 * 根据用户名密码登录后台管理
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username,String password);
	
	/**
	 * 根据用户信息来查询用户的角色
	 * @param user
	 * @return
	 */
	List<Role> findRolesByUser(User user);
	
	/**
	 * 根据角色来查询可以使用的有哪些功能
	 * @param role
	 * @return
	 */
	List<Function> findFunctionsByRole(Role role);
	
}
