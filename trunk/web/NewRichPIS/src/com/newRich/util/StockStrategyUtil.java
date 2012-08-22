package com.newRich.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class StockStrategyUtil {
	public static String STRATEGY_TYPE_PB = "PB";
	public static String STRATEGY_TYPE[] = new String[] { "PB" };
	
	private static String pbUrl = "http://finviz.com/screener.ashx?v=111&f=ta_candlestick_mw,ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&t=";
	
	
	public static String getPBStrategyUrl(String stockCode) {
		String rtString = "";
		if (StringUtils.isNotEmpty(stockCode))
			rtString = pbUrl + stockCode;
		return rtString;
	}
	
	/**
	 * 傳入欲查詢的佈局類型及欲查詢的股票代碼，驗證是否符合佈局類型的條件
	 * 符合回傳true,否則false
	 * @param strategyType(佈局類型)
	 * @param stockCode(股票代碼)
	 * @return
	 */
	public static boolean checkStockStrategy(String strategyType, String stockCode) {
		boolean isCompliance = false;
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		String url = "", responseBody = "";
		try {
			if (STRATEGY_TYPE_PB.equals(strategyType)) {
				url = pbUrl + stockCode;
			}
			if(checkResponseCode(url)){
				httpget = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpclient.execute(httpget, responseHandler);
				//查詢的條件查不到就會出現字串"<b>Total: </b>0"
				if (responseBody.indexOf("<b>Total: </b>0") > -1) {
					isCompliance = false;
				} else {
					isCompliance = true;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return isCompliance;
	}
	
	/**
	 * 驗證該url是否為正常有效的url
	 * 
	 * @param url
	 * @return
	 */
	public static boolean checkResponseCode(String url) {
		boolean is200 = false;
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpContext context = null;
		HttpResponse response = null;
		try {
			httpget = new HttpGet(url);
			context = new BasicHttpContext();
			response = httpclient.execute(httpget, context);
			if (response.getStatusLine().getStatusCode() == 200)
				is200 = true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return is200;
	}
}
