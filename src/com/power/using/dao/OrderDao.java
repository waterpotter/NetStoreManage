package com.power.using.dao;

import java.util.List;

import com.power.using.domain.Customer;
import com.power.using.domain.Order;

public interface OrderDao {

	/**
	 * 保存订单的基本信息,还有
	 * 保存订单关联的订单项信息
	 * @param o
	 */
	void save(Order o);

	/**
	 * 根据订单号查询订单
	 * @param orderNum
	 * @return
	 */
	Order findByNum(String orderNum);

	/**
	 * 根据用户,查询用户所有的订单
	 * @param c
	 * @return
	 */
	List<Order> findByCustomer(String customerId);

	/**
	 * 根据订单修改订单状态
	 * @param order
	 */
	void updateStatus(Order order);

}
