package com.newRich.quartz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

public class StockValue2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockValue2DB.class.getName());
	static PlatformTransactionManager transactionManager = null;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = new Date();
		// 進行轉換
		String startDateString = sdf1.format(startDate);
		loger.info("StockValue2DB start -----------" + startDateString);

		transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				HttpGet httpget = null;
				HttpClient httpclient = new DefaultHttpClient();
				try {
					// 呼叫dao
					StockDao dao = new StockDao();

					List<Stock> stockList = dao.findAllToStock();

					loger.info("StockValue2DB stockList.size():" + stockList.size());

					int totalStock = 0;

					// 進行利用股票代號，查詢網頁的股票其產業類別為何
					String financeUrl = "";
					String valueStr = "";
					String nowPriceStr = "";
					String sqlStr = "";
					String exDividendDate = "";
					for (int i = 0; i < stockList.size(); i++) {
						Stock vo = stockList.get(i);
						financeUrl = "";
						financeUrl = "http://finance.yahoo.com/q?s=" + vo.getStockCode() + "&ql=1";
						// financeUrl = "http://finance.yahoo.com/q?s=FAZ&ql=1";
						httpget = new HttpGet(financeUrl);

						if (checkResponseCode(financeUrl)) {
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							String responseBody = httpclient.execute(httpget, responseHandler);

							String updDateString = sdf1.format(new Date());
							vo.setUpdateDate(updDateString);
							// loger.info(responseBody);
							// Return=> Sector:</th><td nowrap class="yfnc_tabledata1"><a
							// href="http://biz.yahoo.com/p/8conameu.html">Technology</a>
							sqlStr = "";
							// if (responseBody.indexOf("Volume:") > -1) {
							if (responseBody.indexOf("Avg Vol <span class=\"small\">(3m)</span>:") > -1) {
								valueStr = "";
								// String firstString =
								// responseBody.substring(responseBody.indexOf("Volume:"));
								String firstString = responseBody.substring(responseBody.indexOf("Avg Vol <span class=\"small\">(3m)</span>:"));
								String secendString = firstString.substring(0, firstString.indexOf("</td>"));
								secendString = secendString.replaceAll("</span>", "");
								valueStr = secendString.substring(secendString.lastIndexOf(">") + 1, secendString.length());
								valueStr = valueStr.replaceAll(",", "");
								vo.setSharesTraded(!"N/A".equals(valueStr) ? Integer.parseInt(valueStr) : 0);
								sqlStr += " , " + valueStr;
							}
							if (responseBody.indexOf("Prev Close:") > -1) {
								nowPriceStr = "";
								String firstString = responseBody.substring(responseBody.indexOf("Prev Close:"));
								String secendString = firstString.substring(0, firstString.indexOf("</td>"));
								nowPriceStr = secendString.substring(secendString.lastIndexOf(">") + 1, secendString.length());
								nowPriceStr = nowPriceStr.replaceAll(",", "");

								if (!nowPriceStr.equals("N/A")) {
									if (sqlStr.length() > 0) {
										sqlStr += " , nowPrice='" + nowPriceStr + "' ";
									} else {
										sqlStr += " nowPrice='" + nowPriceStr + "' ";
									}
									Double thisValue = new Double(0);
									try {
										thisValue = Double.parseDouble(nowPriceStr);
										vo.setNowPrice(thisValue);
									} catch (Exception e) {

									}

								} else {
									if (sqlStr.length() > 0) {
										sqlStr += " , nowPrice='0' ";
									} else {
										sqlStr += " nowPrice='0' ";
									}
								}
							}

							if (responseBody.indexOf("Ex-Dividend Date:") > -1) {
								// Ex-Dividend Date:</th><td class="yfnc_tabledata1">03-Oct-12</td>
								exDividendDate = "";
								String firstString = responseBody.substring(responseBody.indexOf("Ex-Dividend Date:"));
								String secendString = firstString.substring(0, firstString.indexOf("</td>"));
								secendString = secendString.replaceAll("Ex-Dividend Date:", "");
								exDividendDate = secendString.replaceAll("</th>", "");
								exDividendDate = exDividendDate.replaceAll("<td class=\"yfnc_tabledata1\">", "");
								exDividendDate = exDividendDate.replaceAll("</td>", "");
								vo.setExDividendDate(exDividendDate);
								sqlStr += " , " + exDividendDate;
							}

							if (sqlStr.length() > 0) {
								dao.update(vo);
								totalStock++;
							}
						}
					}

					loger.info("StockValue2DB totalStock:" + totalStock);
					loger.info("StockValue2DB end -----------" + sdf1.format(new Date()));
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
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
