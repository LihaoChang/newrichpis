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

public class StockSector2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockSector2DB.class.getName());
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
		loger.info("StockSector2DB start -----------" + startDateString);

		transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				HttpGet httpget = null;
				HttpClient httpclient = new DefaultHttpClient();
				int totalStock = 0;
				try {
					// 呼叫dao
					StockDao dao = new StockDao();
					List<Stock> stockList = dao.findAllToStock();

					loger.info("StockSector2DB stockList.size():" + stockList.size());

					// 進行利用股票代號，查詢網頁的股票其產業類別為何
					for (int i = 0; i < stockList.size(); i++) {
						Stock vo = stockList.get(i);
						// loger.info(vo.getStockCode());
						// if (i == 0) {http://finance.yahoo.com/q/in?s=AACOU
						String financeUrl = "http://finance.yahoo.com/q/in?s=" + vo.getStockCode();
						// String financeUrl = "http://finance.yahoo.com/q/in?s="+"AAII";
						httpget = new HttpGet(financeUrl);
						// loger.info("httpget:"+httpget);

						if (checkResponseCode(financeUrl)) {
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							String responseBody = httpclient.execute(httpget, responseHandler);

							// loger.info(responseBody);
							// Return=> Sector:</th><td nowrap class="yfnc_tabledata1"><a href="http://biz.yahoo.com/p/8conameu.html">Technology</a>

							String sector = "";
							if (responseBody.indexOf("Sector:") > -1) {
								Date updDate = new Date();
								// 進行轉換
								String updDateString = sdf1.format(updDate);

								String firstString = responseBody.substring(responseBody.indexOf("Sector:"));
								// loger.info("firstString=="+firstString);
								String secendString = firstString.substring(0, firstString.indexOf("</a>"));
								// loger.info("secendString=="+secendString);
								sector = secendString.substring(secendString.lastIndexOf(">") + 1, secendString.length());
								// loger.info("Stock:"+vo.getStockCode()+" ,Sector:"+sector);

								Stock setvo = new Stock();
								setvo.setSector(sector);
								setvo.setUpdateDate(updDateString);
								setvo.setStockCode(vo.getStockCode());
								dao.updateSector(setvo);
								totalStock++;
							}
						}
					}
				} catch (ClientProtocolException e) {
					loger.info("Exception:" + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					loger.info("Exception:" + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				}
				loger.info("StockSector2DB totalStock:" + totalStock);
				loger.info("StockSector2DB end -----------" + sdf1.format(new Date()));
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
			loger.info("url:" + url + " ,Exception:" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			loger.info("url:" + url + " ,Exception:" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is200;
	}
}
