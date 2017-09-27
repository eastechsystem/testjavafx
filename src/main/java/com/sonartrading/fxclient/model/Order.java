package com.sonartrading.fxclient.model;

import java.io.Serializable;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String book;
	private Double price;
	private Double amount;

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double ammount) {
		this.amount = ammount;
	}
}
