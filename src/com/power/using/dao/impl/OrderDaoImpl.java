package com.power.using.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.power.using.dao.OrderDao;
import com.power.using.domain.Customer;
import com.power.using.domain.Order;
import com.power.using.domain.OrderItem;
import com.power.using.util.DBCPUtil;

public class OrderDaoImpl implements OrderDao {

	private QueryRunner qr=new QueryRunner(DBCPUtil.getDataSource());
	
	@Override
	public void save(Order o) {
		try {
			qr.update("insert into orders(ordernum,quantity,money,status,customerId) values(?,?,?,?,?)"
					,o.getOrdernum(),o.getQuantity(),o.getMoney(),o.getStatus(),o.getCustomer().getId());
			//保存订单信息
			List<OrderItem> items = o.getItems();
			for (OrderItem item : items) {
				qr.update("insert into orderitems(id,quantity,price,bookId,ordernum) values(?,?,?,?,?)"
						,item.getId(),item.getQuantity(),item.getPrice(),item.getBook().getId(),o.getOrdernum());
				
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	@Override
	public Order findByNum(String orderNum) {
		try {
			Order order = qr.query("select * from orders where ordernum=?",new BeanHandler<Order>(Order.class),orderNum);
			return order;
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	@Override
	public List<Order> findByCustomer(String customerId) {
		try {
			List<Order> list = qr.query("select * from orders where customerId=? order by ordernum desc",new BeanListHandler<Order>(Order.class),customerId);
			return list;
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	@Override
	public void updateStatus(Order order) {
		try {
			qr.update("update orders set status=? where ordernum=?",order.getStatus(),order.getOrdernum());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
