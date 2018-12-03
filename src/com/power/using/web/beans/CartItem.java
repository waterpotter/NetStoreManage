package com.power.using.web.beans;

import java.io.Serializable;

import com.power.using.domain.Book;

public class CartItem implements Serializable {
	
	private Book book;
	private int qunatity;//本项数量
	private float money;//本项小计
	public CartItem(Book book) {
		super();
		this.book = book;
	}
	public int getQunatity() {
		return qunatity;
	}
	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}
	public float getMoney() {
		return book.getPrice()*qunatity;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public Book getBook() {
		return book;
	}
	
	

}
