package com.power.using.web.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.power.using.domain.Book;

//购物车  必须有一个集合
public class Cart implements Serializable {
	
	//书的id    CartItem购物项,一次购物过程对应一本书
	private Map<String,CartItem> items=new HashMap<String, CartItem>();
	
	private int totalQuantity;
	private float totalMoney;
	public int getTotalQuantity() {
		totalQuantity=0;
		for (Map.Entry<String, CartItem> en : items.entrySet()) {
			totalQuantity+=en.getValue().getQunatity();
		}
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public float getTotalMoney() {
		//必须得初始化为0,因为数据保存在内存中,不然,每刷新一次,就会调用一次get方法
		totalMoney=0;
		//将CartItem中的小计依次想加
		for (Map.Entry<String, CartItem> en : items.entrySet()) {
			totalMoney+=en.getValue().getMoney();
		}
		
		return totalMoney;
	}
	public void setTotalMoney(float totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Map<String, CartItem> getItems() {
		return items;
	}
	
	public void addBook(Book book){
		//包含  数量+1
		if(items.containsKey(book.getId())){
			CartItem item = items.get(book.getId());
			item.setQunatity(item.getQunatity()+1);
		}else{
			//不包含  生成该购物项
			CartItem cartItem = new CartItem(book);
			cartItem.setQunatity(1);
			items.put(book.getId(), cartItem);
		}
		
		
	}
	
	
	
	
	
}
