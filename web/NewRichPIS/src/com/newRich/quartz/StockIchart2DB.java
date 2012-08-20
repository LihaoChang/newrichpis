package com.newRich.quartz;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.newRich.backRun.vo.Stock;
import com.newRich.dao.StockDao;
import com.newRich.model.StockHistory;

public class StockIchart2DB implements Job {
	static Logger loger = Logger.getLogger(StockIchart2DB.class.getName());
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
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		Date thisDate = new Date();
		String startDateString = sdf1.format(thisDate);

		String yyyyStr = sdfYYYY.format(thisDate);
		String mmStr = sdfMM.format(thisDate);
		mmStr = (Integer.parseInt(mmStr) - 1) + "";
		String ddStr = sdfDD.format(thisDate);
		String lastYear = (Integer.parseInt(yyyyStr) - 1) + "";
		String financeUrl = "";
		String emaUrl = "";
		int totalStock = 0;

		boolean lastTrade_PassThrough_20EMA = false;// 股價上漲穿過20EMA
		boolean lastTrade_PassThrough_50EMA = false;// 股價在50EMA之上
		boolean EMA20_Above_EMA50 = false;// 20EMA在50EMA之上
		boolean lastTrade_Above_Last10 = false;// 股價在10天前股價之上
		boolean Last10_Above_Last20 = false;// 10天前股價在20天前股價之上
		int goUp = 0;
		BigDecimal lastTrade = new BigDecimal("0");
		BigDecimal dayEMA_5 = new BigDecimal("0");
		BigDecimal dayEMA_13 = new BigDecimal("0");
		BigDecimal dayEMA_20 = new BigDecimal("0");
		BigDecimal dayEMA_50 = new BigDecimal("0");
		ArrayList<StockHistory> stockHistoryList = new ArrayList<StockHistory>();

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

		StockDao dao = new StockDao();

		List<Stock> allList = new ArrayList();
		String[] loopStr = null;
		String thisLoopStr = "";
		if (HHInt >= 9 && HHInt <= 16) {
			if ((HHInt % 8) == 1) {
				loopStr = loopStr1;
				thisLoopStr = "loopStr1";
			} else if ((HHInt % 8) == 2) {
				loopStr = loopStr2;
				thisLoopStr = "loopStr2";
			} else if ((HHInt % 8) == 3) {
				loopStr = loopStr3;
				thisLoopStr = "loopStr3";
			} else if ((HHInt % 8) == 4) {
				loopStr = loopStr4;
				thisLoopStr = "loopStr4";
			} else if ((HHInt % 8) == 5) {
				loopStr = loopStr5;
				thisLoopStr = "loopStr5";
			} else if ((HHInt % 8) == 6) {
				loopStr = loopStr6;
				thisLoopStr = "loopStr6";
			} else if ((HHInt % 8) == 7) {
				loopStr = loopStr7;
				thisLoopStr = "loopStr7";
			} else if ((HHInt % 8) == 0) {
				loopStr = loopStr8;
				thisLoopStr = "loopStr8";
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

			String sql = "SELECT * FROM STOCK WHERE ( " + sqlStr + ") ";
			// System.out.println("sql:" + sql);
			allList = dao.queryStockBySql(sql);
			// System.out.println("allList.size():" + allList.size());
		} else {
			thisLoopStr = "do nothing";
		}
		loger.info("StockIchart2DB " + thisLoopStr + " start -----------" + startDateString);

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

				for (Stock stockBean : allList) {
					// System.out.println(" stockBean.getStockCode():" + stockBean.getStockCode());
					financeUrl = "";
					emaUrl = "";
					lastTrade_PassThrough_20EMA = false;// 股價上漲穿過20EMA
					lastTrade_PassThrough_50EMA = false;// 股價在50EMA之上
					EMA20_Above_EMA50 = false;// 20EMA在50EMA之上
					lastTrade_Above_Last10 = false;// 股價在10天前股價之上
					Last10_Above_Last20 = false;// 10天前股價在20天前股價之上

					lastTrade = new BigDecimal("0");
					dayEMA_5 = new BigDecimal("0");
					dayEMA_13 = new BigDecimal("0");
					dayEMA_20 = new BigDecimal("0");
					dayEMA_50 = new BigDecimal("0");
					goUp = 0;
					responseBody = "";
					stockHistoryList = new ArrayList<StockHistory>();

					if (stockBean.getSharesTraded() > 0) {
						emaUrl = "http://www.stockta.com/cgi-bin/analysis.pl?symb=" + stockBean.getStockCode() + "&table=ema&mode=table";
						httpget = new HttpGet(emaUrl);
						try {

							if (checkResponseCode(emaUrl)) {

								ResponseHandler<String> responseHandler = new BasicResponseHandler();
								responseBody = httpclient.execute(httpget, responseHandler);

								ArrayList<String> checkArrayList = new ArrayList<String>();

								if (responseBody.indexOf("<font size=\"+2\"><b>EMA Analysis</b></font>") > -1) {
									String firstString = responseBody.substring(responseBody.indexOf("<font size=\"+2\"><b>EMA Analysis</b></font>"));
									String secendString = firstString.substring(0, firstString.indexOf("</table>"));

									secendString = secendString.substring(secendString.indexOf("<tr>"), (secendString.length()));
									// System.out.println("secendString:" + secendString);
									String[] checkArray = secendString.split("</tr>");
									for (int i = 0; i < checkArray.length; i++) {
										String checkStr = checkArray[i];
										checkStr = checkStr.replaceAll("<tr>", "");
										checkStr = checkStr.replaceAll("<td class=\"headerTd\">", "");
										checkStr = checkStr.replaceAll("<td class=\"borderTd\">", "");
										checkStr = checkStr.replaceAll("<td class=\"borderTd\"><font color=\"#008000\">", "");
										checkStr = checkStr.replaceAll("<td class=\"borderTd\"class=\"borderTd\">", "");
										checkStr = checkStr.replaceAll("<font color=\"#008000\">", "");
										checkStr = checkStr.replaceAll("</font>", "");
										checkStr = checkStr.replaceAll("<font color=\"#ff0000\">", "");

										// System.out.println("checkStr:" + checkStr);
										if (i != 0) {
											String[] checkArray2 = checkStr.split("</td>");
											for (int z = 0; z < checkArray2.length; z++) {
												if (!StringUtils.isBlank(checkArray2[z])) {
													if (!checkArray2[z].trim().equals("5 day EMA") && !checkArray2[z].trim().equals("13 day EMA")
															&& !checkArray2[z].trim().equals("20 day EMA")) {
														checkArrayList.add(checkArray2[z].trim());
													}
												}
											}
										}
									}

									for (int i = 0; i < checkArrayList.size(); i++) {
										// System.out.println("str:" + checkArrayList.get(i));
										if (i == 0) {
											lastTrade = new BigDecimal(checkArrayList.get(i).trim());
										} else if (i == 1) {
											dayEMA_5 = new BigDecimal(checkArrayList.get(i).trim());
										} else if (i == 2) {
											dayEMA_13 = new BigDecimal(checkArrayList.get(i).trim());
										} else if (i == 3) {
											dayEMA_20 = new BigDecimal(checkArrayList.get(i).trim());
										} else if (i == 4) {
											dayEMA_50 = new BigDecimal(checkArrayList.get(i).trim());
										}
										if (checkArrayList.get(i).equals("5 EMA Above 13 EMA")) {
											goUp++;
										} else if (checkArrayList.get(i).equals("5 EMA Above 20 EMA")) {
											goUp++;
										} else if (checkArrayList.get(i).equals("5 EMA Above 50 EMA")) {
											goUp++;
										} else if (checkArrayList.get(i).equals("20 EMA Above 50 EMA")) {
											// System.out.println("20EMA在50EMA之上");
											EMA20_Above_EMA50 = true;
										}
									}

								} else {

								}
								if (lastTrade.compareTo(dayEMA_20) > 0) {
									// System.out.println("股價上漲穿過20EMA");
									lastTrade_PassThrough_20EMA = true;
								}
								if (lastTrade.compareTo(dayEMA_50) > 0) {
									// System.out.println("股價在50EMA之上");
									lastTrade_PassThrough_50EMA = true;
								}
								if (goUp == 3) {
									// System.out.println("上漲趨勢");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

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
						financeUrl = "http://ichart.yahoo.com/table.csv?s=" + stockBean.getStockCode() + "&a=" + mmStr + "&b=" + ddStr + "&c="
								+ lastYear + "&d=" + mmStr + "&e=" + ddStr + "&f=" + yyyyStr + "&g=d";
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
								// System.out.println("allArray:" + allArray.length);
								if (null != allArray && allArray.length > 1) {
									// for (int i = 0; i < allArray.length; i++) {

									String lastDate = lastYear + "/" + mmStr + "/" + ddStr;

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
											stockHistoryList.add(stockHistory);

										}
									}
								}
							}
						}

						if (null != stockHistoryList && stockHistoryList.size() > 10) {
							StockHistory stockHistory_0 = stockHistoryList.get(0);
							StockHistory stockHistory_10 = stockHistoryList.get(9);
							BigDecimal close_0 = new BigDecimal(stockHistory_0.getClose());
							BigDecimal close_10 = new BigDecimal(stockHistory_10.getClose());

							if (close_0.compareTo(close_10) > 0) {
								// System.out.println("股價在10天前股價之上");
								lastTrade_Above_Last10 = true;
							}
							if (stockHistoryList.size() >= 19) {
								StockHistory stockHistory_20 = stockHistoryList.get(19);
								BigDecimal close_20 = new BigDecimal(stockHistory_20.getClose());
								if (close_10.compareTo(close_20) > 0) {
									// System.out.println("10天前股價在20天前股價之上");
									Last10_Above_Last20 = true;
								}
							}
						}

						String outStr = "";
						if (lastTrade_PassThrough_20EMA) {
							outStr = outStr + "," + "股價上漲穿過20EMA";
						}
						if (lastTrade_PassThrough_50EMA) {
							outStr = outStr + "," + "股價在50EMA之上";
						}
						if (EMA20_Above_EMA50) {
							outStr = outStr + "," + "20EMA在50EMA之上";
						}
						if (lastTrade_Above_Last10) {
							outStr = outStr + "," + "股價在10天前股價之上";
						}
						if (Last10_Above_Last20) {
							outStr = outStr + "," + "10天前股價在20天前股價之上";
						}
						if (!StringUtils.isBlank(outStr) && outStr.length() > 2) {
							if (outStr.substring(0, 1).equals(",")) {
								outStr = outStr.substring(1, outStr.length());
							}
							System.out.println("StockCode:" + stockBean.getStockCode() + " , outStr:" + outStr);

							String updDateString = sdf1.format(new Date());
							stockBean.setUpdateDate(updDateString);
							stockBean.setStrategy(outStr);
							dao.updateIchart(stockBean);

						}
					}// end if
				}// end for
			}// end if

			loger.info("StockIchart2DB totalStock:" + totalStock);
			loger.info("StockIchart2DB " + thisLoopStr + " end -----------" + sdf1.format(new Date()));

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
