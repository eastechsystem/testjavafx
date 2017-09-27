package com.sonartrading.fxclient.model;

import java.io.Serializable;

public class Trade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 888369812837183927L;

	private Integer id;
	private String book;
	private String createDateTime;
	private Double amount;
	private String makerSide;
	private Double price;
	private Long sequence;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMakerSide() {
		return makerSide;
	}

	public void setMakerSide(String makerSide) {
		this.makerSide = makerSide;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

}
