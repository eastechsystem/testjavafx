package com.sonartrading.fxclient.mapper;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;

import com.sonartrading.fxclient.model.Trade;
import com.sonartrading.fxclient.util.JsonUtil;

/**
 * This mapper handles mapping from json to Trade object
 * 
 * @author Jahanzaib Ali
 *
 */
public class TradeMapper {
	private TradeMapper() {

	}

	/**
	 * The tradeFromJson() method is used to create Trade object from given json
	 * detail string
	 * 
	 * @param json
	 *            Specifies the json detail string
	 * @return Return Trade object of given json detail string
	 */
	public static Trade tradeFromJson(String json) {
		System.out.println("tradeFromJson() method starts!");

		Trade trade = new Trade();
		try {
			JsonObject request = Json.createReader(new StringReader(json)).readObject();

			String bookName = JsonUtil.getFieldValue(request, "book");
			trade.setBook(bookName);

			String createAtDateTime = JsonUtil.getFieldValue(request, "created_at");
			trade.setCreateDateTime(createAtDateTime);

			Double amount = Double.parseDouble(JsonUtil.getFieldValue(request, "amount"));
			trade.setAmount(amount);

			String makerSide = JsonUtil.getFieldValue(request, "maker_side");
			trade.setMakerSide(makerSide);

			Double price = Double.parseDouble(JsonUtil.getFieldValue(request, "price"));
			trade.setPrice(price);

			Integer tradeId = JsonUtil.getIntegerFieldValue(request, "tid");
			trade.setId(tradeId);

		} catch (Exception exception) {
			System.out.println(exception);
		}

		System.out.println("tradeFromJson() method ends!");

		return trade;
	}
}
