package com.sonartrading.fxclient.model;

import java.io.Serializable;

public class TradeTicker extends Trade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TRADE_AT_HIGH_PRICE_UP_TICK = "Trade At High Price => UP";
	public static final String TRADE_AT_LOW_PRICE_UP_TICK = "Trade At LOW Price => DOWN";
	public static final String TRADE_AT_SAME_PRICE_ZERO_TICK = "Trade At LOW Price => ZERO";

	private Trade imaginaryTrade;
	private String tradeRemarkOnTicker;

	public Trade getImaginaryTrade() {
		return imaginaryTrade;
	}

	public void setImaginaryTrade(Trade imaginaryTrade) {
		this.imaginaryTrade = imaginaryTrade;
	}

	public String getTradeRemarkOnTicker() {
		return tradeRemarkOnTicker;
	}

	public void setTradeRemarkOnTicker(String tradeRemarkOnTicker) {
		this.tradeRemarkOnTicker = tradeRemarkOnTicker;
	}

	public void populateImaginaryTrade() {
		this.setBook(this.getImaginaryTrade().getBook());
		this.setId(this.getImaginaryTrade().getId());
		this.setCreateDateTime(this.getImaginaryTrade().getCreateDateTime());
		this.setAmount(this.getImaginaryTrade().getAmount());
		this.setMakerSide(this.getImaginaryTrade().getMakerSide());
		this.setPrice(this.getImaginaryTrade().getPrice());
		this.setSequence(this.getImaginaryTrade().getSequence());
	}
}
