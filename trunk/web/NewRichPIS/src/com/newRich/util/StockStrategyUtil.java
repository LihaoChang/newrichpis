package com.newRich.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	public static String STRATEGY_TYPE[] = new String[] { "PB" };
	public static String STRATEGY_TYPE_PB = "PB";
	public static String STRATEGY_TYPE_PC = "PC";
	
	//各種佈局策略的url -- start
	private static String PB_STRATEGY_URL = "http://finviz.com/screener.ashx?v=111&f=ta_candlestick_mw,ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3";
	private static String PC_STRATEGY_URL = "";
	
	//各種佈局策略的url -- end
	
	
	//拆解網頁資料用start
	private static String recordStartString = "</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=";
	private static String recordStockEndString = "'><br>&nbsp;<b>";
	private static String recordEndString = "</td></tr><tr valign=\"top\"";
	private static String pbPageUrl = "&r=";
	//拆解網頁資料用end

	private static String pbByStockCodeUrl = "http://finviz.com/screener.ashx?v=111&f=ta_candlestick_mw,ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&t=";
	static String testUtl = "http://finviz.com/screener.ashx?v=111&f=ta_change_u2,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3";
	
	
	public static void main(String[] args) {
		try {
			List<String> list = getStrategyListByStrategyType(STRATEGY_TYPE_PB);
			System.out.println("list.size():" + list.size());
		} catch (Exception d) {
			d.printStackTrace();
		}
	}
	
	public static List<String> getStrategyListByStrategyType(String strategyType) {
		List<String> list = new ArrayList<String>();
		int start = 1, pageOfRecords = 20;
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		String url = "", responseBody = "", pageMaxNo = "";
		try {
			if (STRATEGY_TYPE_PB.equals(strategyType)) {
				url = PB_STRATEGY_URL;
				//url = testUtl;
			}else if (STRATEGY_TYPE_PC.equals(strategyType)){
				url = PC_STRATEGY_URL;
			}
			if(checkResponseCode(url)){//判斷網站是否正常
				httpget = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpclient.execute(httpget, responseHandler);
				String stock = "";
				//查詢的條件查不到就會出現字串"<b>Total: </b>0"
				if (responseBody.indexOf("<b>Total: </b>0") > -1) {
					list = null;
				} else {
					//判斷是否有分頁
					pageMaxNo = checkMaxPageNo(responseBody);
					//System.out.println("responseBody:" + responseBody);
					//有資料開始處理，取得的第一頁先處理
					for (int i = start; i <= pageOfRecords; i++) {
						if (responseBody.indexOf(String.valueOf(i) + recordStartString) > 0) {
							stock = responseBody.substring(responseBody.indexOf(String.valueOf(i) + recordStartString) + recordStartString.length() + String.valueOf(i).length(), responseBody.indexOf(recordStockEndString));
							list.add(stock);
							//System.out.println("stock:" + stock);
							responseBody = responseBody.substring(responseBody.indexOf(recordEndString) + recordEndString.length(), responseBody.length());
							//System.out.println("responseBody:" + responseBody);
						}
					}
					
					//如果有其他的分頁，就再各別的分頁去解析
					if(!"".equals(pageMaxNo)){
						int maxNo = Integer.valueOf(pageMaxNo);
						for (int i = 2; i <= maxNo; i++) {
							parsetOtherPage(url, list, String.valueOf(i));
						}
					}
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
		return list;
	}
	
	/**
	 * 共用的方法，若有第二頁以上的符合策略的話，再進行同樣的解析
	 * @param url 佈局類型的url
	 * @param list 第一頁塞進的資料list
	 * @param pageNo 要查第幾頁
	 * @return
	 */
	public static List<String> parsetOtherPage(String url, List<String> list, String pageNo){
		int start = 1, pageOfRecords = 20, goPageNO = 21;
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		String responseBody = "";
		if (!"".equals(pageNo)) {
			int pageno = Integer.valueOf(pageNo);
			if(pageno > 2){
				goPageNO = (pageno - 1) * 20 + 1;
			}
			start = ((Integer.valueOf(pageNo) - 1) * pageOfRecords) + 1;
			pageOfRecords = pageOfRecords * Integer.valueOf(pageNo);
			//System.out.println("goPageNO:"+goPageNO+", pageNo:" + pageNo + ", start:" + start + ", pageOfRecords:" + pageOfRecords);
		}
		try {
			url = url + pbPageUrl + String.valueOf(goPageNO);
			//System.out.println("url:"+url);
			if(checkResponseCode(url)){//判斷網站是否正常
				httpget = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpclient.execute(httpget, responseHandler);
				String stock = "";
				//有資料開始處理，取得的第一頁先處理
				for (int i = start; i <= pageOfRecords; i++) {
					if (responseBody.indexOf(String.valueOf(i) + recordStartString) > 0) {
						//System.out.println("start:" + i);
						stock = responseBody.substring(responseBody.indexOf(String.valueOf(i) + recordStartString) + recordStartString.length() + String.valueOf(i).length(), responseBody.indexOf(recordStockEndString));
						list.add(stock);
						//System.out.println("stock:" + stock);
						responseBody = responseBody.substring(responseBody.indexOf(recordEndString) + recordEndString.length(), responseBody.length());
					}
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
		return list; 
	}
	
	/**
	 * 判斷是否有分頁，有則返回分頁最大值
	 * @param bodyString
	 * @return
	 */
	public static String checkMaxPageNo(String responseBody){
		String pageMaxNo = "";
		if(StringUtils.isNotEmpty(responseBody)){
			//判斷是否有分頁
			responseBody.indexOf("<b>next</b>");
			//取得分頁最大值
			int lastPageHeader = responseBody.lastIndexOf("class=\"screener-pages\">");
			if(lastPageHeader > -1){
				//System.out.println("lastPageHeader:"+lastPageHeader);
				pageMaxNo = responseBody.substring(lastPageHeader+"class=\"screener-pages\">".length(), responseBody.lastIndexOf("</a> <a"));
				//System.out.println("maxPage:"+maxPage);	
			}
		}
		return pageMaxNo;
	}
	
	/**
	 * 傳入股票代號，驗證是否符合PB佈局
	 * @param stockCode
	 * @return
	 */
	public static String getPBStrategyUrl(String stockCode) {
		String rtString = "";
		if (StringUtils.isNotEmpty(stockCode))
			rtString = pbByStockCodeUrl + stockCode;
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
				url = pbByStockCodeUrl + stockCode;
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
