package com.sonartrading.fxclient.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

public class RestClient {
	private String webserviceUrl;
	private String username;
	private String password;
	private Integer readTimeout;

	public RestClient(String webserviceUrl) {
		this(webserviceUrl, null, null);
	}

	public RestClient(String webserviceUrl, Integer readTimeout) {
		this(webserviceUrl);
		this.readTimeout = readTimeout;
	}

	public RestClient(String webserviceUrl, String username, String password) {
		this.webserviceUrl = webserviceUrl;
		this.username = username;
		this.password = password;
	}

	public RestClient(String webserviceUrl, String username, String password,
			Integer readTimeout) {
		this(webserviceUrl, username, password);
		this.readTimeout = readTimeout;
	}

	public String sendPostRequest(String methodName, String jsonRequest) {
		StringBuilder responseJson = new StringBuilder();
		try {

			String requestUrl = webserviceUrl + "/" + methodName;

			System.out.println("Request Sending to " + requestUrl);

			URL url = new URL(requestUrl);
			URLConnection urlConnection;

			urlConnection = url.openConnection();

			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

			httpURLConnection.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);

			if (this.readTimeout != null && this.readTimeout > 0) {
				httpURLConnection.setReadTimeout(this.readTimeout);
			}

			if (ApplicationUtil.isNotNullAndEmpty(username)
					&& ApplicationUtil.isNotNullAndEmpty(password)) {
				String encodedCredentials = getEncodedCredentials(username,
						password);
				httpURLConnection.setRequestProperty("Authorization", "Basic "
						+ encodedCredentials);
			}

			OutputStream out = httpURLConnection.getOutputStream();
			byte[] bytes = jsonRequest.getBytes();

			out.write(bytes);
			out.close();

			InputStream inputStream = httpURLConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String inputLine;

			while ((inputLine = bufferedReader.readLine()) != null) {
				responseJson.append(inputLine);
			}

			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();

			String responseJsonString = responseJson.toString();

			System.out.println("Response Received from " + requestUrl);
			System.out.println("Json Response: " + responseJsonString);

			return responseJsonString;

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public static String sendGetRequest(String requestUrl) {

		StringBuilder responseJson = new StringBuilder();
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				responseJson.append(inputLine);
			}

			conn.disconnect();

			return responseJson.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getEncodedCredentials(String username, String password) {

		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String encodedCredentials = new String(authEncBytes);

		return encodedCredentials;
	}
	
	public static String sendRequestForGetMethod(String requestedURL) throws Exception {
		StringBuilder responseJson = new StringBuilder();
		try {
			URL url = new URL(requestedURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ connection.getResponseCode());
			}

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader((connection.getInputStream())));

			System.out.println("Output from Server .... \n");
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				responseJson.append(inputLine);
			}

			connection.disconnect();

			return responseJson.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
