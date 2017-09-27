package com.sonartrading.fxclient.helper;

import java.io.StringReader;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;





import com.sonartrading.fxclient.mapper.OrderMapper;
import com.sonartrading.fxclient.mapper.TickerMapper;
import com.sonartrading.fxclient.mapper.TradeMapper;
import com.sonartrading.fxclient.model.Order;
import com.sonartrading.fxclient.model.OrderBook;
import com.sonartrading.fxclient.model.Ticker;
import com.sonartrading.fxclient.model.Trade;
import com.sonartrading.fxclient.model.TradeTicker;
import com.sonartrading.fxclient.util.JsonUtil;
import com.sonartrading.fxclient.util.ApplicationUtil;
import com.sonartrading.fxclient.util.RestClient;

final public class BitsoHelper {
	private static final String BITSO_BASE_URL = "https://dev.bitso.com";

	private OrderBook orderBook = new OrderBook();
	private static BitsoHelper instance = null;
	private List<Trade> trades = new ArrayList<Trade>(0);
	private Ticker ticker = new Ticker();
	private List<TradeTicker> tradeTickers = new ArrayList<TradeTicker>(0);

	private static Long localSequence = 0L;

	/**
	 * private constructor for making this class singleton
	 */
	private BitsoHelper() {

	}

	/**
	 * The getInstance() method use to get getInstance instance. Create new
	 * instance if existing is NULL otherwise return same instance.
	 * 
	 * @return instance of OrderBookHelper
	 */
	public static BitsoHelper getInstance() {
		if (instance == null) {
			instance = new BitsoHelper();
		}
		return instance;
	}

	/**
	 * @return OrderBook
	 */
	public OrderBook getOrderBook() {
		return orderBook;
	}

	/**
	 * @param orderBook
	 *            the orderBook to set
	 */
	public void setOrderBook(OrderBook orderBook) {
		this.orderBook = orderBook;
	}

	/**
	 * @return the trades
	 */
	public List<Trade> getTrades() {
		return trades;
	}

	/**
	 * @param trades
	 *            the trades to set
	 */
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	/**
	 * @return the ticker
	 */
	public Ticker getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}

	/**
	 * @return the tradeTickers
	 */
	public List<TradeTicker> getTradeTickers() {
		return tradeTickers;
	}

	/**
	 * @param tradeTickers
	 *            the tradeTickers to set
	 */
	public void setTradeTickers(List<TradeTicker> tradeTickers) {
		this.tradeTickers = tradeTickers;
	}

	/**
	 * sendRequestToBitsoToGetOrderBookData() method is use to send request to
	 * get latest OrderBook details
	 * 
	 */
	public void sendRequestToBitsoToGetOrderBookData() {
		try {
			String responseJson = getOrderBookFromBitso("btc_mxn", true);
			populateOrderBookFromJsonResponse(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getOrderBookFromBitso(String book, boolean... aggregate) throws Exception {
		String request = "/api/v3/order_book/?book=" + book;
		String response = "";

		if (aggregate != null && aggregate.length == 1) {
			if (aggregate[0]) {
				request += "&aggregate=true";
			} else {
				request += "&aggregate=false";
			}
		}

		response = RestClient.sendRequestForGetMethod(BITSO_BASE_URL + request);

		return response;
	}

	private void populateOrderBookFromJsonResponse(String json) {

		try {
			JsonObject request = Json.createReader(new StringReader(json)).readObject();
			Boolean status = request.getBoolean("success");

			if (Boolean.TRUE.equals(status) && request.containsKey("payload")) {
				JsonObject payLoadJsonObject = request.getJsonObject("payload");

				// sequence
				Long sequence = Long.parseLong(JsonUtil.getFieldValue(payLoadJsonObject, "sequence"));

				// Diff-Orders, Update Order Book If you see a sequence number that is more than one value that the previous
				if (localSequence < sequence) {
					localSequence = sequence;

					// Bids
					List<Order> bids = new ArrayList<Order>(0);
					JsonArray bidsJsonArray = JsonUtil.getJsonArrayFromJsonObject(payLoadJsonObject, "bids");
					bidsJsonArray.forEach(jsonObject -> {
						Order bid = OrderMapper.orderFromJson(jsonObject.toString());
						bids.add(bid);
					});

					// Asks
					List<Order> asks = new ArrayList<Order>(0);
					JsonArray asksJsonArray = JsonUtil.getJsonArrayFromJsonObject(payLoadJsonObject, "asks");
					asksJsonArray.forEach(jsonObject -> {
						Order ask = OrderMapper.orderFromJson(jsonObject.toString());
						asks.add(ask);
					});

					// update_at
					String updateAtDateTime = JsonUtil.getFieldValue(payLoadJsonObject, "updated_at");
					Date updateDateTime = null;
					if (ApplicationUtil.isNotNullAndEmpty(updateAtDateTime)) {
						try {
							OffsetDateTime odt = OffsetDateTime.parse(updateAtDateTime);
							updateAtDateTime = odt.toLocalDate().toString() + " " + odt.toLocalTime();
							// i.e Sat Sep 23 19:49:43 PKT 2017
							updateDateTime = ApplicationUtil.parseDate("yyyy-MM-dd HH:mm:ss", updateAtDateTime);
						} catch (ParseException e) {
							System.out.println(e);
						}
					}

					orderBook.setBids(bids);
					orderBook.setAsks(asks);
					orderBook.setUpdateDateTime(updateDateTime);
					orderBook.setSequence(sequence);
				}
			} else {
				System.out.println("#################### Invalid Order Book Json format ####################");
			}
		} catch (Exception exception) {
			System.out.println(exception);
		}

	}

	/**
	 * sendRequestToBitsoToGetTradesData() method is use to send request to get
	 * latest trades information
	 * 
	 */
	public void sendRequestToBitsoToGetTradesData() {
		try {
			String responseJson = getTradeJsonResonseFromBitso("btc_mxn");
			populateTradeFromJsonResponse(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getTradeJsonResonseFromBitso(String book) throws Exception {
		String request = "/api/v3/trades/?book=" + book;
		String response = "";
		response = RestClient.sendRequestForGetMethod(BITSO_BASE_URL + request);
		return response;
	}

	private void populateTradeFromJsonResponse(String json) {

		try {
			JsonObject request = Json.createReader(new StringReader(json)).readObject();
			Boolean status = request.getBoolean("success");

			if (Boolean.TRUE.equals(status) && request.containsKey("payload")) {
				JsonArray payLoadJsonArray = JsonUtil.getJsonArrayFromJsonObject(request, "payload");

				// populate latest trades from responded json
				List<Trade> trades = new ArrayList<Trade>(0);
				payLoadJsonArray.forEach(tradeJsonObject -> {
					Trade trade = TradeMapper.tradeFromJson(tradeJsonObject.toString());
					trades.add(trade);
				});

				this.setTrades(trades);
			} else {
				System.out.println("#################### Invalid Trade Json format ####################");
			}
		} catch (Exception exception) {
			System.out.println(exception);
		}

	}

	/**
	 * sendRequestToBitsoToGetTicker() method is use to send request to get
	 * ticker information
	 * 
	 */
	public void sendRequestToBitsoToGetTickerData() {
		try {
			String responseJson = getTickerJsonResonseFromBitso("btc_mxn");
			populateTickerFromJsonResponse(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getTickerJsonResonseFromBitso(String book) throws Exception {
		String request = "/api/v3/ticker/?book=" + book;
		String response = "";
		response = RestClient.sendRequestForGetMethod(BITSO_BASE_URL + request);
		return response;
	}

	private void populateTickerFromJsonResponse(String json) {

		try {
			JsonObject request = Json.createReader(new StringReader(json)).readObject();
			Boolean status = request.getBoolean("success");

			if (Boolean.TRUE.equals(status) && request.containsKey("payload")) {
				JsonObject payLoadJsonObject = request.getJsonObject("payload");
				Ticker ticker = TickerMapper.tickerFromJson(payLoadJsonObject.toString());
				populateImaginarTradeUpTickDownTickAndZeroTick(ticker);
				this.setTicker(ticker);
			} else {
				System.out.println("#################### Invalid Ticker Json format ####################");
			}
		} catch (Exception exception) {
			System.out.println(exception);
		}

	}

	private void populateImaginarTradeUpTickDownTickAndZeroTick(Ticker ticker) {
		if (this.getTrades() != null && this.getTrades().size() > 0) {
			this.getTrades().forEach(trade -> {
				TradeTicker tradeTicker = new TradeTicker();

				if (trade.getPrice().doubleValue() > ticker.getLow().doubleValue() && trade.getPrice().intValue() <= ticker.getHigh().doubleValue()) {
					ticker.setCountUpTicks(ticker.getCountUpTicks().intValue() + 1);
					tradeTicker.setImaginaryTrade(trade);
					tradeTicker.setTradeRemarkOnTicker(TradeTicker.TRADE_AT_HIGH_PRICE_UP_TICK);
				} else if (trade.getPrice().doubleValue() <= ticker.getLow().doubleValue()) {
					ticker.setCountDownTicks(ticker.getCountDownTicks().intValue() + 1);
					tradeTicker.setImaginaryTrade(trade);
					tradeTicker.setTradeRemarkOnTicker(TradeTicker.TRADE_AT_LOW_PRICE_UP_TICK);
				} else {
					ticker.setCountZeroTicks(ticker.getCountZeroTicks().intValue() + 1);
					tradeTicker.setImaginaryTrade(trade);
					tradeTicker.setTradeRemarkOnTicker(TradeTicker.TRADE_AT_SAME_PRICE_ZERO_TICK);
				}

				tradeTicker.populateImaginaryTrade();
				tradeTickers.add(tradeTicker);
			});
		}
	}
}
