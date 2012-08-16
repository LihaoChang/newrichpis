package com.newRich.quartz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.newRich.backRun.vo.Stock;
import com.newRich.dao.QuartzBaseDao;
import com.newRich.dao.StockDao;
import com.newRich.model.StockHistory;

public class StockIchart2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockIchart2DB.class.getName());
	static PlatformTransactionManager transactionManager = null;
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("HH");
	public static SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");
	public static SimpleDateFormat sdfMM = new SimpleDateFormat("MM");
	public static SimpleDateFormat sdfDD = new SimpleDateFormat("dd");

	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		
		final Date thisDate = new Date();
		String startDateString = sdf1.format(thisDate);
		loger.info("StockIchart2DB start -----------" + startDateString);
		
		transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				HttpGet httpget = null;
				HttpClient httpclient = new DefaultHttpClient();
				String yyyyStr = sdfYYYY.format(thisDate);
				String mmStr = sdfMM.format(thisDate);
				mmStr = (Integer.parseInt(mmStr) - 1) + "";
				String ddStr = sdfDD.format(thisDate);
				String lastYear = (Integer.parseInt(yyyyStr) - 1) + "";
				int totalStock = 0;
				
				// 呼叫dao
				StockDao dao = new StockDao();
				// StockHistoryDao hisdao = new StockHistoryDao();
				
				
				String[] loopStr1 = { "A", "B" };
				String[] loopStr2 = { "C", "D" };
				String[] loopStr3 = { "E", "F", "G" };
				String[] loopStr4 = { "H", "I", "J", "K" };
				String[] loopStr5 = { "L", "M", "N" };
				String[] loopStr6 = { "O", "P", "Q", "R" };
				String[] loopStr7 = { "S", "T", "U", };
				String[] loopStr8 = { "V", "W", "X", "Y", "Z" };
		
				String HHStr = sdf4.format(new Date());
				int HHInt = Integer.parseInt(HHStr);
		
				List<Stock> allList = new ArrayList<Stock>();
				String[] loopStr = null;
				if (HHInt >= 9 && HHInt <= 16) {
					if ((HHInt % 8) == 1) {
						loopStr = loopStr1;
						loger.info("StockIchart2DB loopStr1");
					} else if ((HHInt % 8) == 2) {
						loopStr = loopStr2;
						loger.info("StockIchart2DB loopStr2");
					} else if ((HHInt % 8) == 3) {
						loopStr = loopStr3;
						loger.info("StockIchart2DB loopStr3");
					} else if ((HHInt % 8) == 4) {
						loopStr = loopStr4;
						loger.info("StockIchart2DB loopStr4");
					} else if ((HHInt % 8) == 5) {
						loopStr = loopStr5;
						loger.info("StockIchart2DB loopStr5");
					} else if ((HHInt % 8) == 6) {
						loopStr = loopStr6;
						loger.info("StockIchart2DB loopStr6");
					} else if ((HHInt % 8) == 7) {
						loopStr = loopStr7;
						loger.info("StockIchart2DB loopStr7");
					} else if ((HHInt % 8) == 0) {
						loopStr = loopStr8;
						loger.info("StockIchart2DB loopStr8");
					}
		
					allList = new ArrayList<Stock>();
					String sqlStr = "";
					for (int i = 0; i < loopStr.length; i++) {
						String checkStr = loopStr[i];
						if (i == 0) {
							sqlStr = " STOCKCODE LIKE '" + checkStr + "%'";
						} else {
							sqlStr = sqlStr + " OR STOCKCODE LIKE '" + checkStr + "%'";
						}
					}
		
					allList = dao.FindStockFindGood2DBAll(sqlStr);
					System.out.println("allList.size():" + allList.size());
				} else {
					loger.info("StockIchart2DB do nothing");
				}
				
				try {
					String responseBody = "";
					String valueStr = "";
		
					String dateStr = "";
					String openStr = "";
					String highStr = "";
					String lowStr = "";
					String closeStr = "";
					String volumeStr = "";
					String adjCloseStr = "";
					StockHistory stockHistory = null;
					if (null != allList && allList.size() > 0) {
						/* 取得資料庫連線 */
						// Transaction tx = sess.beginTransaction();
						for (Stock stockBean : allList) {
							String financeUrl = "";
		
							// http://ichart.yahoo.com/table.csv?s=string&a=int&b=int&c=int&d=int&e=int&f=int&g=d&ignore=.csv
							// 參數
							// s - 股票名稱
							// a - 起始時間，月
							// b - 起始時間，日
							// c - 起始時間，年
							// d - 結束時間，月
							// e - 結束時間，日
							// f - 結束時間，年
							// g - 時間週期。Example: g=w, 表示週期是'週'。d->'日'(day),
							// w->'週'(week)，m->'月'(mouth)，v->'dividends only'
							// 一定注意月份參數，其值比真實數據-1。如需要9月數據，則寫為08。
							financeUrl = "http://ichart.yahoo.com/table.csv?s=" + stockBean.getStockCode() + "&a=" + mmStr + "&b=" + ddStr + "&c=" + lastYear
									+ "&d=" + mmStr + "&e=" + ddStr + "&f=" + yyyyStr + "&g=d";
							// System.out.println("financeUrl:" + financeUrl);
							httpget = new HttpGet(financeUrl);
		
							if (checkResponseCode(financeUrl)) {
								responseBody = "";
								ResponseHandler<String> responseHandler = new BasicResponseHandler();
								// 取得回傳的資料
								try {
									responseBody = httpclient.execute(httpget, responseHandler);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// System.out.println("responseBody:" + responseBody);
								if (!StringUtils.isBlank(responseBody)) {
									totalStock++;
									String[] allArray = responseBody.split("\n");
									System.out.println("allArray:" + allArray.length);
									if (null != allArray && allArray.length > 1) {
										// for (int i = 0; i < allArray.length; i++) {
		
										// 刪除一年以前的資料
										String lastDate = lastYear + "/" + mmStr + "/" + ddStr;
										//
										StockHistory hisvo = new StockHistory();
										hisvo.setStockCode(stockBean.getStockCode());
										hisvo.setCreateDate(lastDate);
										// 刪除資料
										// hisdao.delete(hisvo);
										for (int i = 2; i < allArray.length; i++) {
											valueStr = "";
											valueStr = allArray[i];
											String[] valueArray = valueStr.split(",");
											// System.out.println("valueArray:" + i + " :" +
											// valueArray.length);
											if (null != valueArray && valueArray.length == 7) {
												dateStr = "";
												dateStr = valueArray[0];
												dateStr = dateStr.replaceAll("-", "/");
												Date date = sdf2.parse(dateStr);
												String dateString = sdf3.format(date);
												// System.out.println("dateStr:" + i + " :" + dateStr);
												openStr = "";
												openStr = valueArray[1];
												highStr = "";
												highStr = valueArray[2];
												lowStr = "";
												lowStr = valueArray[3];
												closeStr = "";
												closeStr = valueArray[4];
												volumeStr = "";
												volumeStr = valueArray[5];
												adjCloseStr = "";
												adjCloseStr = valueArray[6];
		
												stockHistory = new StockHistory();
												stockHistory.setId(dateString + "_" + stockBean.getStockCode());
												stockHistory.setStockCode(stockBean.getStockCode());
												stockHistory.setCreateDate(dateStr);
												stockHistory.setOpen(openStr);
												stockHistory.setHigh(highStr);
												stockHistory.setLow(lowStr);
												stockHistory.setClose(closeStr);
												stockHistory.setVolume(volumeStr);
												stockHistory.setAdjClose(adjCloseStr);
												// System.out.println("dateStr:" + dateStr);
												// 新增資料
												// hisdao.insert(stockHistory);
											}
										}
									}
								}
							}
						}
					}
		
					loger.info("StockIchart2DB totalStock:" + totalStock);
					loger.info("StockIchart2DB end -----------" + sdf1.format(new Date()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is200;
	}
}
