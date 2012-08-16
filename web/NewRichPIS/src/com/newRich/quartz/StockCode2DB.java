package com.newRich.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.newRich.backRun.util.ExcelUtil;
import com.newRich.backRun.vo.ResultSetHeader;
import com.newRich.backRun.vo.Stock;
import com.newRich.backRun.vo.StockCode;
import com.newRich.dao.QuartzBaseDao;
import com.newRich.dao.StockCodeDao;
import com.newRich.dao.StockDao;
import com.newRich.json.JSONObject;

public class StockCode2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockCode2DB.class.getName());
	static PlatformTransactionManager transactionManager = null;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy_MM_dd");
	public static String[] loopStr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };

	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				HttpClient httpclient = new DefaultHttpClient();
		
				try {
					ExcelUtil excelUtil = new ExcelUtil();
					Date thisDate = new Date();
					
					StockCodeDao dao = new StockCodeDao();
					StockDao sdao = new StockDao();
					
					// 進行轉換
					String dateString = sdf.format(thisDate);
					String thisDateStr = sdf1.format(thisDate);
					loger.info("StockCode2DB start -----------" + thisDateStr);
					String searchUrl = "/Data/Key_Metrics";
		
					String urlStr = "http://www.wikinvest.com/wikinvest/livesearch/?q=stockLoop&limit=10000&timestamp=0000000000000&format=json&referrers=0&type=regular&_xhr=1";
					HttpGet httpget = null;
					int totalStock = 0;
		
		
					ArrayList<StockCode> allList = new ArrayList();
					ArrayList<Stock> allList2 = new ArrayList();
					for (int i = 0; i < loopStr.length; i++) {
						String newUrlStr = urlStr.replace("stockLoop", loopStr[i]);
						// System.out.println("newUrlStr:" + newUrlStr);
						httpget = new HttpGet(newUrlStr);
						// Create a response handler
						ResponseHandler<String> responseHandler = new BasicResponseHandler();
						String responseBody = httpclient.execute(httpget, responseHandler);
		
						JSONObject jsonObjectMsg = new JSONObject(responseBody);
						ResultSetHeader resultSet = new ResultSetHeader(jsonObjectMsg);
						System.out.println(loopStr[i].toUpperCase() + " :[" + resultSet.getTotalResultsReturned() + "]");
		
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
								} else {
									// stockCodeStr = stock.getTitle();
									// int indexInt = 0;
									// if (stockCodeStr.indexOf("/") > 0) {
									// indexInt = stockCodeStr.indexOf("/");
									// } else {
									// indexInt = stockCodeStr.length() - 1;
									// }
									// stockCodeStr = stockCodeStr.substring(0, indexInt);
									// if (stockCodeStr.indexOf("(") > 0) {
									// stockCodeStr = stockCodeStr.substring(stockCodeStr.indexOf("("), stockCodeStr.length());
									// stockCodeStr = stockCodeStr.replace("(", "");
									// stockCodeStr = stockCodeStr.replace(")", "");
									//
									// } else {
									// stockCodeStr = null;
									// }
								}
								// System.out.println("stockCodeStr:" + stockCodeStr);
								if (!StringUtils.isBlank(stockCodeStr)) {
									totalStock++;
									try {
										int s = Integer.parseInt(stockCodeStr);
									} catch (Exception e) {
										
										if (null == dao.findByStockCode(stockCodeStr)) {
											stockCode.setStockCode(stockCodeStr.trim());
											allList.add(stockCode);
											// sess.save(stockCode);
										}
										
										if (null == sdao.findByStockCodeToStock(stockCodeStr)) {
											stock.setStockCode(stockCodeStr.trim());
											stock.setCreateDate(thisDateStr);
											allList2.add(stock);
										}
									}
								}
							}
						}
					}
		
					for (int i = 0; i < allList.size() - 1; i++) {
						for (int j = allList.size() - 1; j > i; j--) {
							if (allList.get(j).getStockCode().equals(allList.get(i).getStockCode())) {
								allList.remove(j);
							}
						}
					}
		
					for (int i = 0; i < allList2.size() - 1; i++) {
						for (int j = allList2.size() - 1; j > i; j--) {
							if (allList2.get(j).getStockCode().equals(allList2.get(i).getStockCode())) {
								allList2.remove(j);
							}
						}
					}
		
					for (StockCode stockCode : allList) {
						if (null == dao.findByStockCode(stockCode.getStockCode())) {
							dao.insert(stockCode);
						}else{
							dao.update(stockCode);
						}
					}
		
					for (Stock stock : allList2) {
						if (null == sdao.findByStockCodeToStock(stock.getStockCode())) {
							sdao.insert(stock);
						}else{
							sdao.update(stock);
						}
					}
		
					System.out.println("allList.size():" + allList.size());
					System.out.println("allList2.size():" + allList2.size());
		
					System.out.println("StockCode2DB end -----------" + sdf1.format(new Date()));
					loger.info("StockCode2DB end -----------" + sdf1.format(new Date()));
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
		});
	}
}
