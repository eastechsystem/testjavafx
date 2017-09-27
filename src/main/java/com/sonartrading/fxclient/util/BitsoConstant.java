package com.sonartrading.fxclient.util;

import java.util.HashMap;
import java.util.Map;

public final class BitsoConstant {
	private static Map<String, String> tableColumnTitlesForOrderBook = new HashMap<String, String>(0);
	private static Map<String, String> tableColumnTitlesForTrade = new HashMap<String, String>(0);
	private static Map<String, String> tableColumnTitlesForImaginaryTrade = new HashMap<String, String>(0);
	
	static {
		try{
			loadTableColumnTitlesForOrderBook();
			loadTableColumnTitlesForTrade();
			loadTableColumnTitlesForImaginaryTrade();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}
	
	public static Map<String, String> getTableColumnTitlesForOrderBook() {
		if (tableColumnTitlesForOrderBook == null && tableColumnTitlesForOrderBook.size() < 1) {
			loadTableColumnTitlesForOrderBook();
		}
		return tableColumnTitlesForOrderBook;
	}
	
	public static Map<String, String> getTableColumnTitlesForTrade() {
		if (tableColumnTitlesForTrade == null && tableColumnTitlesForTrade.size() < 1) {
			loadTableColumnTitlesForTrade();
		}
		return tableColumnTitlesForTrade;
	}
	
	public static Map<String, String> getTableColumnTitlesForImaginaryTrade() {
		if (tableColumnTitlesForImaginaryTrade == null && tableColumnTitlesForImaginaryTrade.size() < 1) {
			loadTableColumnTitlesForImaginaryTrade();
		}
		return tableColumnTitlesForImaginaryTrade;
	}
	
	private static void loadTableColumnTitlesForOrderBook(){
		tableColumnTitlesForOrderBook.put("book", "Book");
		tableColumnTitlesForOrderBook.put("price", "Price (MXN)");
		tableColumnTitlesForOrderBook.put("amount", "Amount (BTC)");
	}
	
	private static void loadTableColumnTitlesForTrade(){
		tableColumnTitlesForTrade.put("id", "Trade Id");
		tableColumnTitlesForTrade.put("book", "Book");
		tableColumnTitlesForTrade.put("createDateTime", "Create At");
		tableColumnTitlesForTrade.put("amount", "Amount (BTC)");
		tableColumnTitlesForTrade.put("makerSide", "Maker Side");
		tableColumnTitlesForTrade.put("price", "Price (MXN)");
	}
	
	private static void loadTableColumnTitlesForImaginaryTrade(){
		tableColumnTitlesForImaginaryTrade.put("id", "Trade Id");
		tableColumnTitlesForImaginaryTrade.put("book", "Book");
		tableColumnTitlesForImaginaryTrade.put("createDateTime", "Create At");
		tableColumnTitlesForImaginaryTrade.put("amount", "Amount (BTC)");
		tableColumnTitlesForImaginaryTrade.put("makerSide", "Maker Side");
		tableColumnTitlesForImaginaryTrade.put("price", "Price (MXN)");
		tableColumnTitlesForImaginaryTrade.put("tradeRemarkOnTicker", "Trade Remarks On Ticker");
	}
	
}
