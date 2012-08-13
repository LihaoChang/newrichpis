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

import com.newRich.backRun.util.DataUtil00;
import com.newRich.backRun.util.ExcelUtil;
import com.newRich.backRun.vo.ResultSetHeader;
import com.newRich.backRun.vo.Stock;
import com.newRich.dao.StockDao;
import com.newRich.json.JSONObject;

public class Pis2DB {
	public static String outPath;
	public static String outPathTW;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy_MM_dd");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		try {
			ExcelUtil excelUtil = new ExcelUtil();
			Date thisDate = new Date();

			// 進行轉換
			String dateString = sdf.format(thisDate);
			String thisDateStr = sdf1.format(thisDate);
			System.out.println("start -----------" + thisDateStr);
			outPath = "d:\\";
			outPath = outPath + "stock" + dateString + ".xls";
			outPathTW = "d:\\";
			outPathTW = outPathTW + "stock" + dateString + "TW.xls";

			String searchUrl = "/Data/Key_Metrics";

			String urlStr = "http://www.wikinvest.com/wikinvest/livesearch/?q=stockLoop&limit=10000&timestamp=0000000000000&format=json&referrers=0&type=regular&_xhr=1";
			HttpGet httpget = null;
			int totalStock = 0;
			ArrayList<ArrayList<Stock>> allList = new ArrayList();
			for (int i = 0; i < DataUtil00.loopStr.length; i++) {
				String newUrlStr = urlStr.replace("stockLoop", DataUtil00.loopStr[i]);
				// System.out.println("newUrlStr:" + newUrlStr);
				httpget = new HttpGet(newUrlStr);
				// Create a response handler
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String responseBody = httpclient.execute(httpget, responseHandler);

				JSONObject jsonObjectMsg = new JSONObject(responseBody);
				ResultSetHeader resultSet = new ResultSetHeader(jsonObjectMsg);
				System.out.println(DataUtil00.loopStr[i].toUpperCase() + " :["
						+ resultSet.getTotalResultsReturned() + "]");
				ArrayList<Stock> stockBeanList = resultSet.getStockBeanList();
				totalStock += Integer.parseInt(resultSet.getTotalResultsReturned());
				allList.add(stockBeanList);
			}
			System.out.println("totalStock:" + totalStock);
			ArrayList<ArrayList<Stock>> newAllList = new ArrayList();
			ArrayList<ArrayList<Stock>> newAllList2 = new ArrayList();

			ArrayList<Stock> newStockBeanList = new ArrayList<Stock>();
			ArrayList<Stock> oldStockBeanList = new ArrayList<Stock>();
			ArrayList<String> searchStockList = new ArrayList();
			Stock stockBean = new Stock();
			String stockCode = "";
			System.out.println("allList.size():" + allList.size());
			if (null != allList && allList.size() > 0) {

				for (int i = 0; i < allList.size(); i++) {
					// for (int i = 0; i < 2; i++) {
					newStockBeanList = new ArrayList<Stock>();
					oldStockBeanList = allList.get(i);
					searchStockList = new ArrayList();

					if (null != oldStockBeanList && oldStockBeanList.size() > 0) {
						for (int ii = 0; ii < oldStockBeanList.size(); ii++) {
							// for (int ii = 0; ii < 2; ii++) {
							stockBean = oldStockBeanList.get(ii);
							// String stockCode = stockBean.getTitle();
							stockCode = stockBean.getPrefixedTicker();
							if (!StringUtils.isBlank(stockCode)) {
								String[] stockCodeArray = stockCode.split(":");
								// System.out.println("1stockCode:" + stockCode);
								// System.out.println("stockCodeArray:" + stockCodeArray.length);
								if (null != stockCodeArray && stockCodeArray.length > 1) {
									stockCode = stockCodeArray[1].trim();
								}
								// System.out.println("2stockCode:" + stockCode);
							}
							stockBean.setStockCode(stockCode);
							searchStockList.add(stockCode);
							newStockBeanList.add(stockBean);
						}
						newAllList.add(newStockBeanList);
					}

					// System.out.println("searchStockList:" + searchStockList);
					DataUtil00.checkValue(newStockBeanList, searchStockList);
					// for (int v = 0; v < newStockBeanList.size(); v++) {
					// StockBean stockBean = newStockBeanList.get(v);
					// System.out.println("1stockCode:" + stockBean.getStockCode());
					// System.out.println("2getNowPrice:" + stockBean.getNowPrice());
					// System.out.println("3getValue:" + stockBean.getValue());
					// System.out.println("");
					// }
				}

				for (int i = 0; i < newAllList.size(); i++) {
					// for (int i = 0; i < 2; i++) {
					newStockBeanList = new ArrayList<Stock>();
					oldStockBeanList = newAllList.get(i);
					if (null != oldStockBeanList && oldStockBeanList.size() > 0) {
						for (int ii = 0; ii < oldStockBeanList.size(); ii++) {
							// for (int ii = 0; ii < 5; ii++) {
							stockBean = oldStockBeanList.get(ii);

							DataUtil00.dataWork(stockBean, searchUrl);
							// System.out.println("1stockCode:" + stockBean.getStockCode());
							// System.out.println("2getNowPrice:" + stockBean.getNowPrice());
							// System.out.println("3getValue:" + stockBean.getValue());
							if(null == stockBean.getNowPrice()){
								
							}
							
							StockDao dao = new StockDao();
							if (null != stockBean && null != stockBean.getStockCode()) {
								if (null == dao.findByStockCodeToStock(stockBean.getStockCode())) {
									stockBean.setCreateDate(thisDateStr);
									dao.insert(stockBean);
								} else {
									stockBean.setUpdateDate(thisDateStr);
									dao.update(stockBean);
								}
							}

							newStockBeanList.add(stockBean);
						}
						newAllList2.add(newStockBeanList);
					}
				}
			}

			// HSSFWorkbook wb = excelUtil.makeUpExcelFile(newAllList2);
			// OutputStream os = new FileOutputStream(new File(outPath));
			// wb.write(os);
			// os.close();

			Date endDate = new Date();
			// 進行轉換
			String endDateString = sdf1.format(endDate);
			System.out.println("end -----------" + endDateString);

		} catch (Exception e) {
			System.out.println("getStock error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
}
