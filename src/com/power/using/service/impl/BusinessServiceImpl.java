package com.power.using.service.impl;

import java.util.List;

import javax.print.attribute.standard.PDLOverrideSupported;

import com.power.using.dao.BookDao;
import com.power.using.dao.CategoryDao;
import com.power.using.dao.CustomerDao;
import com.power.using.dao.OrderDao;
import com.power.using.dao.PrivilegeDao;
import com.power.using.dao.impl.BookDaoImpl;
import com.power.using.dao.impl.CategoryDaoImpl;
import com.power.using.dao.impl.OrderDaoImpl;
import com.power.using.dao.impl.PrivilegeDaoImpl;
import com.power.using.domain.Book;
import com.power.using.domain.Category;
import com.power.using.domain.Customer;
import com.power.using.domain.Function;
import com.power.using.domain.Order;
import com.power.using.domain.OrderItem;
import com.power.using.domain.Role;
import com.power.using.domain.User;
import com.power.using.service.BusinessService;
import com.power.using.service.commons.Page;
import com.power.using.util.IdGenertor;

public class BusinessServiceImpl implements BusinessService {
	
	private CategoryDao categoryDao=new CategoryDaoImpl();
	
	private BookDao bookDao=new BookDaoImpl();
	
	private CustomerDao customerDao=new CustomerImpl();
	
	private OrderDao orderDao=new OrderDaoImpl();
	
	private PrivilegeDao pDao=new PrivilegeDaoImpl();
	
	
	@Override
	public void addCategory(Category c) {
		c.setId(IdGenertor.genGUID());
		categoryDao.save(c);
	}

	@Override
	public List<Category> findAllCategories() {

		return categoryDao.findAll();
	}

	@Override
	public Category findCategoryById(String categoryId) {

		return categoryDao.findById(categoryId);
	}

	@Override
	public void addBook(Book book) {
		if(book==null){
			throw new IllegalArgumentException("the book canot be null");
		}
		if(book.getCategory()==null){
			throw new IllegalArgumentException("the book's category canot be null");
		}
		book.setId(IdGenertor.genGUID());
		bookDao.save(book);
		
		
	}

	@Override
	public Book findBookById(String bookId) {
		
		return bookDao.findBookById(bookId);
	}

	@Override
	public Page findBookPageRecords(String num) {
		
		int pageNum=1;
		if(num!=null&&!num.equals("")){
			pageNum=Integer.parseInt(num);
		}
		
		int totalRecordsNum=bookDao.getToatalRecordsNum();
		Page page = new Page(pageNum, totalRecordsNum);
		List records=bookDao.findPageRecoeds(page.getStartIndex(),page.getPageSize());
		page.setRecords(records);
		return page;
	}

	@Override
	public Page findBookPageRecords(String num, String categoryId) {
		
		int pageNum=1;
		if(num!=null&&!num.equals("")){
			pageNum=Integer.parseInt(num);
		}
		
		int totalRecordsNum=bookDao.getToatalRecordsNum(categoryId);
		Page page = new Page(pageNum, totalRecordsNum);
		List records=bookDao.findPageRecoeds(page.getStartIndex(),page.getPageSize(),categoryId);
		page.setRecords(records);
		return page;
	}

	@Override
	public void addCustomer(Customer c) {
		c.setId(IdGenertor.genGUID());
		customerDao.save(c);
	}

	@Override
	public Customer findCustomer(String customerId) {
		
		return customerDao.findOne(customerId);
	}

	@Override
	public Customer customerLogin(String username, String password) {
		
		return customerDao.find(username,password);
	}

	@Override
	public void genOrder(Order o) {
		if(o==null){
			throw new IllegalArgumentException("不存在订单所属客户信息");
		}
		if(o.getItems()==null&&o.getItems().size()==0){
			throw new IllegalArgumentException("订单中没有订单项,不生成订单");
		}
		
		orderDao.save(o);
		
	}

	@Override
	public Order findOrderByNum(String OrderNum) {
		
		return orderDao.findByNum(OrderNum);
	}

	@Override
	public List<Order> findCustomerOrders(Customer c) {
		
		return orderDao.findByCustomer(c.getId());
	}

	@Override
	public void changeOrderStatus(Order order) {
		
		orderDao.updateStatus(order);
		
	}

	@Override
	public User login(String username, String password) {
		
		return pDao.find(username,password);
	}

	@Override
	public List<Role> findRolesByUser(User user) {
		
		return pDao.findRolesByUser(user);
	}

	@Override
	public List<Function> findFunctionsByRole(Role role) {
		
		return pDao.findFunctuonsByRole(role);
	}

}
