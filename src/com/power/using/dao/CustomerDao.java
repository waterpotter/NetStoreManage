package com.power.using.dao;

import com.power.using.domain.Customer;

public interface CustomerDao {

	/**
	 * 添加用户
	 * @param c
	 */
	void save(Customer c);

	/**
	 * 根据用户Id来查找用户
	 * @param customerId
	 * @return
	 */
	Customer findOne(String customerId);

	/**
	 * 根据用户名和密码来查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	Customer find(String username, String password);
	

}
