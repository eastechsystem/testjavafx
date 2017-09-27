package com.sonartrading.fxclient.mapper;

import java.io.StringReader;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;

import com.sonartrading.fxclient.model.Ticker;
import com.sonartrading.fxclient.util.JsonUtil;
import com.sonartrading.fxclient.util.ApplicationUtil;

/**
 * This mapper handles mapping from json to Trade object
 * 
 * @author Jahanzaib Ali
 *
 */
public class TickerMapper {
	private TickerMapper() {

	}

	/**
	 * The tickerFromJson() method is used to create Ticker object from given
	 * json detail string
	 * 
	 * @param json
	 *            Specifies the json detail string
	 * @return Return Ticker object of given json detail string
	 */
	public static Ticker tickerFromJson(String json) {
		System.out.println("tickerFromJson() method starts!");

		Ticker ticker = new Ticker();
		try {
			JsonObject request = Json.createReader(new StringReader(json)).readObject();

			Double high = Double.parseDouble(JsonUtil.getFieldValue(request, "high"));
			ticker.setHigh(high);

			Double last = Double.parseDouble(JsonUtil.getFieldValue(request, "last"));
			ticker.setLast(last);

			String createdAtDateTime = JsonUtil.getFieldValue(request, "created_at");
			Date createDateTime = null;
			if (ApplicationUtil.isNotNullAndEmpty(createdAtDateTime)) {
				try {
					OffsetDateTime odt = OffsetDateTime.parse(createdAtDateTime);
					createdAtDateTime = odt.toLocalDate().toString() + " " + odt.toLocalTime();
					// i.e Sat Sep 23 19:49:43 PKT 2017
					createDateTime = ApplicationUtil.parseDate("yyyy-MM-dd HH:mm:ss", createdAtDateTime);
				} catch (ParseException e) {
					System.out.println(e);
				}
			}
			ticker.setCreateDateTime(createDateTime);

			String bookName = JsonUtil.getFieldValue(request, "book");
			ticker.setBook(bookName);

			Double volume = Double.parseDouble(JsonUtil.getFieldValue(request, "volume"));
			ticker.setVolume(volume);

			Double vwap = Double.parseDouble(JsonUtil.getFieldValue(request, "vwap"));
			ticker.setVwap(vwap);

			Double low = Double.parseDouble(JsonUtil.getFieldValue(request, "low"));
			ticker.setLow(low);

			Double ask = Double.parseDouble(JsonUtil.getFieldValue(request, "ask"));
			ticker.setAsk(ask);

			Double bid = Double.parseDouble(JsonUtil.getFieldValue(request, "ask"));
			ticker.setBid(bid);

		} catch (Exception exception) {
			System.out.println(exception);
		}

		System.out.println("tickerFromJson() method ends!");

		return ticker;
	}
}
