package com.newRich.quartz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import com.newRich.util.StockStrategyUtil;

public class StockIsSP5002DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockIsSP5002DB.class.getName());
	static PlatformTransactionManager transactionManager = null;
	public static SimpleDateFormat sdfMM = new SimpleDateFormat("MM");

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
		loger.info("StockIsSP5002DB start -----------" + startDateString);

		transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				
				HttpClient httpclient = new DefaultHttpClient();
				Date thisDate = new Date();
				String startDateString = sdf1.format(thisDate);

				String mmStr = sdfMM.format(thisDate);
				mmStr = (Integer.parseInt(mmStr) - 1) + "";
				int totalStock = 0, sucessStotalStock = 0;

				StockDao dao = new StockDao();

				loger.info("StockIsSP5002DB start -----------" + startDateString);
				String noStock = "";
				try {
					String sp500 = "";
					List<Stock> newStockList = new ArrayList<Stock>();
					// 清除目前DB中所有佈局的字串
					dao.clearIsSP500();
					
					List<String> sp500_list = StockStrategyUtil.getStrategyListByStrategyType(StockStrategyUtil.TYPE_SP500);
					loger.info("StockIsSP5002DB sp500_list.size() -----------" + sp500_list.size());
					
					if (null != sp500_list) {
						for (int i = 0; i < sp500_list.size(); i++) {
							String StockCode = sp500_list.get(i);
							//查詢db裡是否有該成分股
							Stock vo = dao.findByStockCodeToStock(StockCode);
							// System.out.println("vo:"+vo);
							Date updDate = new Date();
							// 進行轉換
							String updDateString = sdf1.format(updDate);
							if (null != vo) {
								sp500 += StockCode + "','";
								sucessStotalStock++;
							} else {
								noStock += StockCode + ", ";
								//新增物件準備新增至資料庫
								Stock newvo = new Stock();
								newvo.setStockCode(StockCode);
								newvo.setIsSp500("Y");
								newStockList.add(newvo);
							}
							totalStock++;
						}// end for
					}
					
					if (null != sp500 && sp500.length() > 0) {
						sp500 = sp500.substring(0, sp500.length() - 3);
					}
					if (null != noStock && noStock.length() > 2) {
						noStock = noStock.substring(0, noStock.length() - 2);
					}
					loger.info("StockIsSP5002DB sp500 DB have -----------" + sp500);
					//一次更新為sp500
					dao.updateIsSP500(sp500);
					//將資料庫無個股資料的部分，新增至資料庫
					for (int i = 0; i < newStockList.size(); i++) {
						Stock vo = newStockList.get(i);
						dao.insert(vo);
					}
					loger.info("StockIsSP5002DB end -----------" + sdf1.format(new Date()));

				} catch (Exception e) {
					loger.info("Exception:" + e.getMessage());
					e.printStackTrace();
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				}
				loger.info("StockIsSP5002DB totalStock:" + totalStock + ", stock no datas:" + noStock);
				loger.info("StockIsSP5002DB end -----------" + sdf1.format(new Date()));
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
