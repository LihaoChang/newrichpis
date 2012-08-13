package com.newRich.backRun.util;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.newRich.backRun.vo.Stock;

public class DataUtil00 {
	public static String indexNetIncome = "Net Income <small>";
	public static String indexNetIncomeGrowth = "Net Income Growth <small>";
	public static String indexNetMargin = "Net Margin <small>";
	public static String indexDebt_equity = "Debt / Equity <small>";
	public static String indexBookValuePerShare = "Book Value Per Share <small>";
	public static String indexCashPerShare = "Cash Per Share <small>";
	public static String indexRoe = "Return on Equity (ROE) <small>";
	public static String indexRoA = "Return on Assets (ROA) <small>";
	public static String indexDividend = "Dividend Yield";
	public static int subStringInt = 200;

	// public static String[] loopStr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
	// "l",
	// "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	// public static String[] loopStr = { "a", "b", "c", "d" };

	// public static String[] loopStr = { "e", "f", "g" };

	// public static String[] loopStr = { "h", "i", "j", };

	// public static String[] loopStr = { "k", "l", "m", };

	// public static String[] loopStr = { "n", "o", "p", };

	// public static String[] loopStr = { "q", "r", "s" };

	// public static String[] loopStr = { "t", "u", "v", };

	public static String[] loopStr = { "w", "x", "y", "z" };

	public static void checkValue(ArrayList<Stock> newStockBeanList, ArrayList<String> searchStockList) {
//	public static void checkValue(ArrayList<StockVO> newStockBeanList, ArrayList<String> searchStockList) {
		try {
			if (null != searchStockList) {
				// nowPriceUrl = "http://finance.yahoo.com/d/quotes.csv?s=" +
				// stockBean.getStockCode() + ".US&f=l1v";
				System.out.println("checkValue searchStockList:" + searchStockList.size());

				int inSize = searchStockList.size();
				int a = inSize / 100;
				int b = inSize % 100;
				// System.out.println("a:" + a);
				// System.out.println("b:" + b);
				ArrayList<String> priceList = new ArrayList();
				ArrayList<String> valueList = new ArrayList();
				if (b > 0) {
					a = a + 1;
				}
				int startnum = 0;
				int endnum = 100;
				for (int i = 1; i <= a; i++) {
					if (i == a) {
						startnum = (100 * (i - 1));
						endnum = searchStockList.size();
						checkValue2(startnum, endnum, priceList, valueList, searchStockList);
					} else if (i == 1) {
						startnum = 0;
						endnum = i * 100;
						checkValue2(startnum, endnum, priceList, valueList, searchStockList);
					} else {
						startnum = (100 * (i - 1));
						endnum = i * 100;
						checkValue2(startnum, endnum, priceList, valueList, searchStockList);
					}
				}

				// System.out.println("priceList:" + priceList.size());
				// System.out.println("valueList:" + valueList.size());

				for (int y = 0; y < newStockBeanList.size(); y++) {
					Stock stockBean = newStockBeanList.get(y);
//					StockVO stockBean = newStockBeanList.get(y);
					if (!StringUtils.isBlank(priceList.get(y))) {
						stockBean.setNowPrice(new Double(priceList.get(y)));
					} else {
						stockBean.setNowPrice(new Double(0));
					}
					if (!StringUtils.isBlank(valueList.get(y)) && !valueList.get(y).trim().equals("N/A")) {
						stockBean.setSharesTraded(Integer.parseInt(valueList.get(y).trim()));
					} else {
						stockBean.setSharesTraded(new Integer(0));
					}

				}
			}
		} catch (Exception e) {
			System.out.println("checkValue error:" + e.getMessage());
			e.printStackTrace();
		} finally {
		}
		// return responseBody2;

	}

	public static void checkValue2(int startnum, int endnum, ArrayList<String> priceList, ArrayList<String> valueList,
			ArrayList<String> searchStockList) {
		String searchStock = "";
		// System.out.println("startnum:" + startnum);
		// System.out.println("endnum:" + endnum);
		for (int j = startnum; j < endnum; j++) {
			String stockName = searchStockList.get(j);
			if (j == 0) {
				searchStock = stockName + ".US,";
			} else if (j == (endnum)) {
				searchStock = searchStock + stockName + ".US";
			} else {
				searchStock = searchStock + stockName + ".US,";
			}
		}
		HttpClient httpclient = new DefaultHttpClient();
		String nowPriceUrl = "http://finance.yahoo.com/d/quotes.csv?s=searchStockStr&f=l1v";
		nowPriceUrl = nowPriceUrl.replaceAll("searchStockStr", searchStock);
		String responseBody2 = "";
		try {
			HttpGet httpget = null;
			System.out.println("checkValue2 nowPriceUrl:" + nowPriceUrl);
			httpget = new HttpGet(nowPriceUrl);
			ResponseHandler<String> responseHandler2 = new BasicResponseHandler();
			responseBody2 = httpclient.execute(httpget, responseHandler2);

			// System.out.println("responseBody2:" + responseBody2);
			String[] valueStr = responseBody2.split("\n");
			if (valueStr.length > 0) {
				for (int z = 0; z < valueStr.length; z++) {
					String str = valueStr[z];
					String[] splitStr = str.split(",");
					priceList.add(splitStr[0]);
					valueList.add(splitStr[1]);
				}
			}
		} catch (Exception e) {
			System.out.println("checkValue2 error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static Stock dataWork(Stock stockBean, String searchUrl) {
//	public static StockVO dataWork(StockVO stockBean, String searchUrl) {

		String netIncome = "";
		String netIncomeGrowth = "";
		String netMargin = "";
		String debt_equity = "";
		String bookValuePerShare = "";
		String cashPerShare = "";
		String roe = "";
		String roa = "";
		String dividend = "";

		String checkType = "";
		String checkNum = "";
		// String nowPriceUrl = "";
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = null;
			String dataCentralUrl = stockBean.getUrl() + searchUrl;
			System.out.println("dataWork dataCentralUrl:" + dataCentralUrl);
			httpget = new HttpGet(dataCentralUrl);
			ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
			String responseBody1 = httpclient.execute(httpget, responseHandler1);

			int startNum = responseBody1.indexOf("<table");
			String str = responseBody1.substring(startNum, responseBody1.length());

			if (str.indexOf(indexNetIncome) >= 0) {
				netIncome = str.substring(str.indexOf(indexNetIncome), str.indexOf(indexNetIncome) + subStringInt);
				String[] strArray = netIncome.split(">");
				if (null != strArray && strArray.length > 0) {
					netIncome = strArray[5];
					netIncome = netIncome.substring(0, netIncome.indexOf("<"));
				}
			}
			if (str.indexOf(indexNetIncomeGrowth) >= 0) {
				netIncomeGrowth = str.substring(str.indexOf(indexNetIncomeGrowth), str.indexOf(indexNetIncomeGrowth) + subStringInt);
				String[] strArray = netIncomeGrowth.split(">");
				if (null != strArray && strArray.length > 0) {
					netIncomeGrowth = strArray[5];
					netIncomeGrowth = netIncomeGrowth.substring(0, netIncomeGrowth.indexOf("<"));
				}
			}
			if (str.indexOf(indexNetMargin) >= 0) {
				netMargin = str.substring(str.indexOf(indexNetMargin), str.indexOf(indexNetMargin) + subStringInt);
				String[] strArray = netMargin.split(">");
				if (null != strArray && strArray.length > 0) {
					netMargin = strArray[5];
					netMargin = netMargin.substring(0, netMargin.indexOf("<"));
				}
			}
			if (str.indexOf(indexDebt_equity) >= 0) {
				debt_equity = str.substring(str.indexOf(indexDebt_equity), str.indexOf(indexDebt_equity) + subStringInt);
				String[] strArray = debt_equity.split(">");
				if (null != strArray && strArray.length > 0) {
					debt_equity = strArray[5];
					debt_equity = debt_equity.substring(0, debt_equity.indexOf("<"));
				}
			}
			if (str.indexOf(indexBookValuePerShare) >= 0) {
				bookValuePerShare = str.substring(str.indexOf(indexBookValuePerShare), str.indexOf(indexBookValuePerShare) + subStringInt);
				String[] strArray = bookValuePerShare.split(">");
				if (null != strArray && strArray.length > 0) {
					bookValuePerShare = strArray[5];
					bookValuePerShare = bookValuePerShare.substring(0, bookValuePerShare.indexOf("<"));
				}
			}
			if (str.indexOf(indexCashPerShare) >= 0) {
				cashPerShare = str.substring(str.indexOf(indexCashPerShare), str.indexOf(indexCashPerShare) + subStringInt);
				String[] strArray = cashPerShare.split(">");
				if (null != strArray && strArray.length > 0) {
					cashPerShare = strArray[5];
					cashPerShare = cashPerShare.substring(0, cashPerShare.indexOf("<"));
				}
			}
			if (str.indexOf(indexRoe) >= 0) {
				roe = str.substring(str.indexOf(indexRoe), str.indexOf(indexRoe) + subStringInt);
				String[] strArray = roe.split(">");
				if (null != strArray && strArray.length > 0) {
					roe = strArray[5];
					roe = roe.substring(0, roe.indexOf("<"));
				}
			}
			if (str.indexOf(indexRoA) >= 0) {
				roa = str.substring(str.indexOf(indexRoA), str.indexOf(indexRoA) + subStringInt);
				String[] strArray = roa.split(">");
				if (null != strArray && strArray.length > 0) {
					roa = strArray[5];
					roa = roa.substring(0, roa.indexOf("<"));
				}
			}
			if (str.indexOf(indexDividend) >= 0) {
				dividend = str.substring(str.indexOf(indexDividend), str.indexOf(indexDividend) + subStringInt);
				String[] strArray = dividend.split(">");
				if (null != strArray && strArray.length > 0) {
					dividend = strArray[3];
					dividend = dividend.substring(0, dividend.indexOf("<"));
				}
			}

			// System.out.println("netIncome:" + netIncome);
			// System.out.println("netIncomeGrowth:" + netIncomeGrowth);
			// System.out.println("netMargin:" + netMargin);
			// System.out.println("debt_equity:" + debt_equity);
			// System.out.println("bookValuePerShare:" + bookValuePerShare);
			// System.out.println("cashPerShare:" + cashPerShare);
			// System.out.println("roe:" + roe);
			// System.out.println("roa:" + roa);
			// System.out.println("dividend:" + dividend);

			if (!StringUtils.isBlank(netIncome) && netIncome.length() > 1) {
				checkType = "";
				checkNum = "";
				checkType = netIncome.substring((netIncome.length() - 1), netIncome.length());
				checkNum = netIncome.substring(0, (netIncome.length() - 1));
				Double checkDouble = new Double(checkNum);
				checkDouble = checkNetIncome(checkType, checkDouble);
				stockBean.setNetIncome(checkDouble);
			} else {
				stockBean.setNetIncome(new Double(0));
			}
			if (!StringUtils.isBlank(netIncomeGrowth) && netIncomeGrowth.length() > 1) {
				checkType = "";
				checkNum = "";
				checkType = netIncomeGrowth.substring((netIncomeGrowth.length() - 1), netIncomeGrowth.length());
				checkNum = netIncomeGrowth.substring(0, (netIncomeGrowth.length() - 1));
				Double checkDouble = new Double(checkNum);
				checkDouble = check100B(checkType, checkDouble);
				stockBean.setNetIncomeGrowth(checkDouble);
			} else {
				stockBean.setNetIncomeGrowth(new Double(0));
			}

			if (!StringUtils.isBlank(netMargin) && netMargin.length() > 1) {
				checkType = "";
				checkNum = "";
				checkType = netMargin.substring((netMargin.length() - 1), netMargin.length());
				checkNum = netMargin.substring(0, (netMargin.length() - 1));
				Double checkDouble = new Double(checkNum);
				checkDouble = check100B(checkType, checkDouble);
				stockBean.setNetMargin(checkDouble);
			} else {
				stockBean.setNetMargin(new Double(0));
			}

			if (!StringUtils.isBlank(debt_equity) && debt_equity.length() > 1) {
				Double checkDouble = new Double(debt_equity);
				stockBean.setDebtEquity(checkDouble);
			} else {
				stockBean.setDebtEquity(new Double(0));
			}

			if (!StringUtils.isBlank(bookValuePerShare) && bookValuePerShare.length() > 1) {
				Double checkDouble = new Double(bookValuePerShare);
				stockBean.setBookValuePerShare(checkDouble);
			} else {
				stockBean.setBookValuePerShare(new Double(0));
			}

			if (!StringUtils.isBlank(cashPerShare) && cashPerShare.length() > 1) {
				Double checkDouble = new Double(cashPerShare);
				stockBean.setCashPerShare(checkDouble);
			} else {
				stockBean.setCashPerShare(new Double(0));
			}

			if (!StringUtils.isBlank(roe) && roe.length() > 1) {
				checkType = "";
				checkNum = "";
				checkType = roe.substring((roe.length() - 1), roe.length());
				checkNum = roe.substring(0, (roe.length() - 1));
				Double checkDouble = new Double(checkNum);
				checkDouble = check100B(checkType, checkDouble);
				stockBean.setRoe(checkDouble);
			} else {
				stockBean.setRoe(new Double(0));
			}

			if (!StringUtils.isBlank(roa) && roa.length() > 1) {
				checkType = "";
				checkNum = "";
				checkType = roa.substring((roa.length() - 1), roa.length());
				checkNum = roa.substring(0, (roa.length() - 1));
				Double checkDouble = new Double(checkNum);
				checkDouble = check100B(checkType, checkDouble);
				stockBean.setRoa(checkDouble);
			} else {
				stockBean.setRoa(new Double(0));
			}

			if (!StringUtils.isBlank(dividend) && dividend.length() > 1) {
				checkType = "";
				checkNum = "";
				checkType = dividend.substring((dividend.length() - 1), dividend.length());
				checkNum = dividend.substring(0, (dividend.length() - 1));
				Double checkDouble = new Double(checkNum);
				checkDouble = check100B(checkType, checkDouble);
				stockBean.setDividend(checkDouble);
			} else {
				stockBean.setDividend(new Double(0));
			}

		} catch (Exception e) {
			System.out.println("getStock error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return stockBean;
	}

	public static Double checkNetIncome(String checkType, Double checkDouble) {
		Double outDouble = new Double(0);
		if (!StringUtils.isBlank(checkType) && null != checkDouble) {
			// thousand （千，十的三次方），
			// million（百萬，十的六次方），
			// 之後就是billion（十億，十的九次方）、
			// trillion（兆，十的十二次方）
			if (checkType.equals("K")) {
				outDouble = checkDouble * 1000;
			} else if (checkType.equals("M")) {
				outDouble = checkDouble * 1000000;
			} else if (checkType.equals("B")) {
				outDouble = checkDouble * 1000000000;
			} else if (checkType.equals("T")) {
				outDouble = checkDouble * new Double("1000000000000");
			}

		}
		return outDouble;
	}

	public static Double check100B(String checkType, Double checkDouble) {
		Double outDouble = new Double(0);
		if (!StringUtils.isBlank(checkType) && null != checkDouble) {
			if (checkType.equals("%")) {
				outDouble = checkDouble / 100;
			}
		}
		return outDouble;
	}
}
