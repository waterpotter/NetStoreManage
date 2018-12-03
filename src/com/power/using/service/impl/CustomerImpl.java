package com.power.using.service.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.power.using.dao.CustomerDao;
import com.power.using.domain.Customer;
import com.power.using.util.DBCPUtil;

public class CustomerImpl implements CustomerDao {

	private QueryRunner qr=new QueryRunner(DBCPUtil.getDataSource());
	
	@Override
	public void save(Customer c) {
		try{
			qr.update("insert into customer(id,username,password,nickname,phonenum,address,email) values(?,?,?,?,?,?,?)",
						c.getId(),c.getUsername(),c.getPassword(),c.getNickname(),c.getPhonenum(),c.getAddress(),c.getEmail());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public Customer findOne(String customerId) {
		try{
			Customer c = qr.query("select * from customer where id=?",new BeanHandler<Customer>(Customer.class),customerId);
			return c;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Customer find(String username, String password) {
		try{
			Customer c = qr.query("select * from customer where username=? and password=? ",new BeanHandler<Customer>(Customer.class),username,password);
			return c;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
