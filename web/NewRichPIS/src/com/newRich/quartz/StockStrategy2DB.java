package com.newRich.quartz;

import java.text.SimpleDateFormat;
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
	static Logger loger = Logger.getLogger(StockStrategy2DB.class.getName());
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
				int totalStock = 0, sucessStotalStock = 0;

				StockDao dao = new StockDao();

				loger.info("StockStrategy2DB start -----------" + startDateString);
				try {
					String strategys = "";
					// 取得目前所有的佈局類型
					// String strategyType[] = StockStrategyUtil.STRATEGY_TYPE;
					// for (String type : strategyType) {
					// allList = StockStrategyUtil.getStrategyListByStrategyType(type);
					// }
					// 清除目前DB中所有佈局的字串
					dao.clearStrategy();
					// ===========PB佈局 Start===========================
					List<String> pb_list = StockStrategyUtil.getStrategyListByStrategyType(StockStrategyUtil.STRATEGY_TYPE_PB);
					String noStock = "";
					if (null != pb_list) {
						for (int i = 0; i < pb_list.size(); i++) {
							String StockCode = pb_list.get(i);
							strategys = StockStrategyUtil.STRATEGY_TYPE_PB;
							// System.out.println("StockCode:"+StockCode);
							Stock vo = dao.findByStockCodeToStock(StockCode);
							// System.out.println("vo:"+vo);
							Date updDate = new Date();
							// 進行轉換
							String updDateString = sdf1.format(updDate);
							if (null != vo) {
								String strategy = vo.getStrategy() == null ? "" : vo.getStrategy();
								// 同一天才將原有的strategy字串相加，不同天就讓它塞符合的策略字串
								if (vo.getUpdateDate().substring(0, 10).equals(updDateString.substring(0, 10)) && strategy.indexOf(StockStrategyUtil.STRATEGY_TYPE_PB) == -1) {
									if (!"".equals(strategy)) {
										strategys += ", " + strategy;
									}
								}

								vo.setUpdateDate(updDateString);
								// 更新該筆資料的欄位
								vo.setStrategy(strategys);
								dao.updateIchart(vo);
								sucessStotalStock++;
							} else {
								noStock += StockCode + ", ";
							}
							totalStock++;
							// if (!"".equals(noStock)) {
							// System.out.println("noStock:[" + noStock + "]");
							// noStock = noStock.substring(0, noStock.length() - 2);
							// }
						}// end for
					}
					// ===========PB佈局 End===========================

					// ===========PC佈局 Start===========================
					List<String> pc_list = StockStrategyUtil.getStrategyListByStrategyType(StockStrategyUtil.STRATEGY_TYPE_PC);
					if (null != pc_list) {
						for (int i = 0; i < pc_list.size(); i++) {
							String StockCode = pc_list.get(i);
							strategys = StockStrategyUtil.STRATEGY_TYPE_PC;
							// System.out.println("StockCode:"+StockCode);
							Stock vo = dao.findByStockCodeToStock(StockCode);
							// System.out.println("vo:"+vo);
							Date updDate = new Date();
							// 進行轉換
							String updDateString = sdf1.format(updDate);
							if (null != vo) {
								String strategy = vo.getStrategy() == null ? "" : vo.getStrategy();
								// 同一天才將原有的strategy字串相加，不同天就讓它塞符合的策略字串
								if (vo.getUpdateDate().substring(0, 10).equals(updDateString.substring(0, 10)) && strategy.indexOf(StockStrategyUtil.STRATEGY_TYPE_PC) == -1) {
									if (!"".equals(strategy)) {
										strategys += ", " + strategy;
									}
								}

								vo.setUpdateDate(updDateString);
								// 更新該筆資料的欄位
								vo.setStrategy(strategys);
								dao.updateIchart(vo);
								sucessStotalStock++;
							} else {
								noStock += StockCode + ", ";
							}
							totalStock++;
							if (!"".equals(noStock)) {
								System.out.println("noStock:[" + noStock + "]");
								// noStock = noStock.substring(0, noStock.length() - 2);
							}
						}// end for
					}
					// ===========PC佈局 End===========================
					if (null != noStock && noStock.length() > 2) {
						noStock = noStock.substring(0, noStock.length() - 2);
					}
					loger.info("StockStrategy2DB totalStock:" + totalStock + ", No Stock code in DB:" + (totalStock - sucessStotalStock) + " , no Stock cdoes:" + noStock);
					loger.info("StockStrategy2DB end -----------" + sdf1.format(new Date()));

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				}
			}// end Spring doInTransactionWithoutResult
		});// end Spring transactionTemplate
	}

}
