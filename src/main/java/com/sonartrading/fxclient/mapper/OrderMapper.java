package com.sonartrading.fxclient.mapper;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;

import com.sonartrading.fxclient.model.Order;
import com.sonartrading.fxclient.util.JsonUtil;

/**
 * This mapper handles mapping from json to Order object
 * 
 * @author Jahanzaib Ali
 *
 */
public class OrderMapper {
	private OrderMapper() {
	}

	/**
	 * The orderFromJson() method is used to create Order object from given json
	 * detail string
	 * 
	 * @param json
	 *            Specifies the json detail string
	 * @return Return Order object of given json detail string
	 */
	public static Order orderFromJson(String json) {
		System.out.println("orderFromJson() method starts!");

		Order order = new Order();
		try {
			JsonObject request = Json.createReader(new StringReader(json)).readObject();
			// validate profile name
			String bookName = JsonUtil.getFieldValue(request, "book");
			order.setBook(bookName);

			Double price = Double.parseDouble(JsonUtil.getFieldValue(request, "price"));
			order.setPrice(price);

			Double amount = Double.parseDouble(JsonUtil.getFieldValue(request, "amount"));
			order.setAmount(amount);

		} catch (Exception exception) {
			System.out.println(exception);
		}

		System.out.println("orderFromJson() method ends!");

		return order;
	}
}
