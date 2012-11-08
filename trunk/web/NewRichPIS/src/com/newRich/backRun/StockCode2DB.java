package com.newRich.backRun;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.newRich.backRun.util.ExcelUtil;
import com.newRich.backRun.vo.ResultSetHeader;
import com.newRich.backRun.vo.Stock;
import com.newRich.backRun.vo.StockCode;
import com.newRich.dao.StockCodeDao;
import com.newRich.json.JSONObject;

public class StockCode2DB {
	public static String outPath;
	public static String outPathTW;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy_MM_dd");
	public static String[] loopStr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		try {
			ExcelUtil excelUtil = new ExcelUtil();
			Date thisDate = new Date();

			// 進行轉換
			String dateString = sdf.format(thisDate);
			String thisDateStr = sdf1.format(thisDate);
			// System.out.println("start -----------" + thisDateStr);
			outPath = "d:\\";
			outPath = outPath + "stock" + dateString + ".xls";
			outPathTW = "d:\\";
			outPathTW = outPathTW + "stock" + dateString + "TW.xls";

			String searchUrl = "/Data/Key_Metrics";

			String urlStr = "http://www.wikinvest.com/wikinvest/livesearch/?q=stockLoop&limit=10000&timestamp=0000000000000&format=json&referrers=0&type=regular&_xhr=1";
			HttpGet httpget = null;
			int totalStock = 0;

			ArrayList<ArrayList<Stock>> allList = new ArrayList();
			for (int i = 0; i < loopStr.length; i++) {
				String newUrlStr = urlStr.replace("stockLoop", loopStr[i]);
				// System.out.println("newUrlStr:" + newUrlStr);
				httpget = new HttpGet(newUrlStr);
				// Create a response handler
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String responseBody = httpclient.execute(httpget, responseHandler);

				JSONObject jsonObjectMsg = new JSONObject(responseBody);
				ResultSetHeader resultSet = new ResultSetHeader(jsonObjectMsg);
				// System.out.println(loopStr[i].toUpperCase() + " :[" + resultSet.getTotalResultsReturned() + "]");
				ArrayList<Stock> stockBeanList = resultSet.getStockBeanList();
				if (null != stockBeanList && stockBeanList.size() > 0) {
					for (Stock stock : stockBeanList) {

						StockCode stockCode = new StockCode();
						String stockCodeStr = stock.getPrefixedTicker();
						if (!StringUtils.isBlank(stockCodeStr)) {
							String[] stockCodeArray = stockCodeStr.split(":");
							// System.out.println("1stockCode:" + stockCode);
							// System.out.println("stockCodeArray:" + stockCodeArray.length);
							if (null != stockCodeArray && stockCodeArray.length > 1) {
								stockCodeStr = stockCodeArray[1].trim();
							}
							// System.out.println("2stockCode:" + stockCode);
						}
						// System.out.println("stockCodeStr:" + stockCodeStr);
						if (!StringUtils.isBlank(stockCodeStr)) {
							try {
								int s = Integer.parseInt(stockCodeStr);
							} catch (Exception e) {
								StockCodeDao dao = new StockCodeDao();

								if (null == dao.findByStockCode(stockCodeStr)) {
									stockCode.setStockCode(stockCodeStr);
									dao.insert(stockCode);
								}
							}
						}
					}
				}
			}

			// System.out.println("totalStock:" + totalStock);
		} catch (Exception e) {
			// System.out.println("getStock error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
}
