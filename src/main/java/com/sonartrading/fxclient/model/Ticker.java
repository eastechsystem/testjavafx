package com.sonartrading.fxclient.model;

import java.io.Serializable;
import java.util.Date;

public class Ticker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Double high;
	private Double last;
	private Date createDateTime;
	private String book;
	private Double volume;
	private Double vwap;
	private Double low;
	private Double ask;
	private Double bid;
	private Integer countUpTicks = 0;
	private Integer countDownTicks = 0;
	private Integer countZeroTicks = 0;
	
	
	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLast() {
		return last;
	}

	public void setLast(Double last) {
		this.last = last;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getVwap() {
		return vwap;
	}

	public void setVwap(Double vwap) {
		this.vwap = vwap;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Integer getCountUpTicks() {
		return countUpTicks;
	}

	public void setCountUpTicks(Integer countUpTicks) {
		this.countUpTicks = countUpTicks;
	}

	public Integer getCountDownTicks() {
		return countDownTicks;
	}

	public void setCountDownTicks(Integer countDownTicks) {
		this.countDownTicks = countDownTicks;
	}

	public Integer getCountZeroTicks() {
		return countZeroTicks;
	}

	public void setCountZeroTicks(Integer countZeroTicks) {
		this.countZeroTicks = countZeroTicks;
	}

}
