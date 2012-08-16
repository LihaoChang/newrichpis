package com.newRich.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
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

import com.newRich.backRun.util.DataUtil00;
import com.newRich.backRun.vo.Stock;
import com.newRich.dao.QuartzBaseDao;
import com.newRich.dao.StockDao;

public class StockFindGood2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockFindGood2DB.class.getName());
	static PlatformTransactionManager transactionManager = null;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy_MM_dd");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("HH");
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	/**
	 * @param args
	 */
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
					StockDao dao = new StockDao();
					
					Date thisDate = new Date();
		
					// 進行轉換
					String dateString = sdf.format(thisDate);
					String thisDateStr = sdf1.format(thisDate);
					loger.info("StockFindGood2DB start -----------" + thisDateStr);
		
					String searchUrl = "/Data/Key_Metrics";
		
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
		
					int totalStock = 0;
					List<Stock> allList = new ArrayList();
					String[] loopStr = null;
					if (HHInt >= 1 && HHInt <= 8) {
						if ((HHInt % 8) == 1) {
							loopStr = loopStr1;
							loger.info("StockFindGood2DB loopStr1");
						} else if ((HHInt % 8) == 2) {
							loopStr = loopStr2;
							loger.info("StockFindGood2DB loopStr2");
						} else if ((HHInt % 8) == 3) {
							loopStr = loopStr3;
							loger.info("StockFindGood2DB loopStr3");
						} else if ((HHInt % 8) == 4) {
							loopStr = loopStr4;
							loger.info("StockFindGood2DB loopStr4");
						} else if ((HHInt % 8) == 5) {
							loopStr = loopStr5;
							loger.info("StockFindGood2DB loopStr5");
						} else if ((HHInt % 8) == 6) {
							loopStr = loopStr6;
							loger.info("StockFindGood2DB loopStr6");
						} else if ((HHInt % 8) == 7) {
							loopStr = loopStr7;
							loger.info("StockFindGood2DB loopStr7");
						} else if ((HHInt % 8) == 0) {
							loopStr = loopStr8;
							loger.info("StockFindGood2DB loopStr8");
						}
		
						allList = new ArrayList();
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
						loger.info("StockFindGood2DB do nothing");
					}
		
					if (null != allList && allList.size() > 0) {
						for (Stock stockBean : allList) {
							DataUtil00.dataWork(stockBean, searchUrl);
							String sqlStr = "";
							if (!StringUtils.isBlank(stockBean.getStockCode())) {
								if (null != stockBean.getNetIncome()) {
									sqlStr += ", NetIncome='" + stockBean.getNetIncome() + "'";
								}
								if (null != stockBean.getNetIncomeGrowth()) {
									sqlStr += ", NetIncomeGrowth='" + stockBean.getNetIncomeGrowth() + "'";
								}
								if (null != stockBean.getNetMargin()) {
									sqlStr += ", NetMargin='" + stockBean.getNetMargin() + "'";
								}
								if (null != stockBean.getDebtEquity()) {
									sqlStr += ", DebtEquity='" + stockBean.getDebtEquity() + "'";
								}
								if (null != stockBean.getBookValuePerShare()) {
									sqlStr += ", BookValuePerShare='" + stockBean.getBookValuePerShare() + "'";
								}
								if (null != stockBean.getCashPerShare()) {
									sqlStr += ", CashPerShare='" + stockBean.getCashPerShare() + "'";
								}
								if (null != stockBean.getRoe()) {
									sqlStr += ", Roe='" + stockBean.getRoe() + "'";
								}
								if (null != stockBean.getRoa()) {
									sqlStr += ", Roa='" + stockBean.getRoa() + "'";
								}
								if (null != stockBean.getDividend()) {
									sqlStr += ", Dividend='" + stockBean.getDividend() + "'";
								}
								String sql = "update Stock set updateDate='" + thisDateStr + "' " + sqlStr + "  where stockCode='" + stockBean.getStockCode()
										+ "'";
								System.out.println("sql:" + sql);
								dao.updateStockFindGood2DB(sql);
								totalStock++;
							}
						}
		
					}
					loger.info("StockFindGood2DB totalStock:" + totalStock);
					loger.info("StockFindGood2DB end -----------" + sdf1.format(new Date()));
		
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
