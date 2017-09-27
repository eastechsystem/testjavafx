package com.sonartrading.fxclient.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Order> asks;
	private List<Order> bids;
	private Date updateDateTime;
	private Long sequence;

	public List<Order> getAsks() {
		return asks;
	}

	public void setAsks(List<Order> asks) {
		this.asks = asks;
	}

	public List<Order> getBids() {
		return bids;
	}

	public void setBids(List<Order> bids) {
		this.bids = bids;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
}
