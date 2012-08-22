package com.newRich.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
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

import com.newRich.backRun.vo.Stock;
import com.newRich.dao.QuartzBaseDao;
import com.newRich.dao.StockDao;
import com.newRich.util.StockStrategyUtil;

public class StockStrategy2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockIchart2DB.class.getName());
	static PlatformTransactionManager transactionManager = null;
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdfMM = new SimpleDateFormat("MM");

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
				Date thisDate = new Date();
				String startDateString = sdf1.format(thisDate);
		
				String mmStr = sdfMM.format(thisDate);
				mmStr = (Integer.parseInt(mmStr) - 1) + "";
				int totalStock = 0;
		
				StockDao dao = new StockDao();
		
				List<Stock> allList = new ArrayList<Stock>();
				loger.info("StockStrategy2DB start -----------" + startDateString);
				String sql = "SELECT * FROM STOCK ";
				// System.out.println("sql:" + sql);
				allList = dao.queryStockBySql(sql);
				totalStock = allList.size();
				try {
					String strategys = "";
					if (null != allList && allList.size() > 0) {
						//取得目前所有的佈局類型
						String strategyType[] = StockStrategyUtil.STRATEGY_TYPE;
						for (Stock stockBean : allList) {
							strategys = "";
							Stock vo = new Stock();
							System.out.println("stockBean.getStockCode():" + stockBean.getStockCode());
							for (String type : strategyType) {
								if(StockStrategyUtil.checkStockStrategy(type, stockBean.getStockCode()))
									strategys += type + ", ";
							}
							if(!"".equals(strategys))
								strategys = strategys.substring(0, strategys.length() - 2);
							
							vo.setStockCode(stockBean.getStockCode());
							Date updDate = new Date();
							// 進行轉換
							String updDateString = sdf1.format(updDate);
							vo.setUpdateDate(updDateString);
							//更新該筆資料的欄位
							vo.setStrategy(strategys);
							dao.updateIchart(vo);
						}// end for
						
					}// end if
		
					loger.info("StockStrategy2DB totalStock:" + totalStock);
					loger.info("StockStrategy2DB end -----------" + sdf1.format(new Date()));
		
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				}
			}//end Spring doInTransactionWithoutResult
		});//end Spring transactionTemplate
	}
			
}
