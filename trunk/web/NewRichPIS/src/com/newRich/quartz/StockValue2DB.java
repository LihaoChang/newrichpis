package com.newRich.quartz;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.newRich.util.PoiUtil;

public class StockValue2DB extends QuartzBaseDao implements Job {
	static Logger loger = Logger.getLogger(StockValue2DB.class.getName());
	static PlatformTransactionManager transactionManager = null;
	final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	final static SimpleDateFormat exDividendDateSdf = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	public static void main(String[] args) {
		// run();
		// getOptions();
		// getWeeklyOptions();
	}

	public static void run() {
		getOptions();
		getWeeklyOptions();

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
								if (exDividendDate.length() > 7) {
									// Jan 1,Feb 2,Mar 3,Apr 4,May 5,Jun 6
									// Jul 7,Aug 8,Sep 9,Oct 10,Nov 11,Dec 12

									try {
										if (exDividendDate.split("-").length == 3) {
											Date thisExDividendDate = exDividendDateSdf.parse(exDividendDate);
											exDividendDate = new SimpleDateFormat("yyyy/MM/dd").format(thisExDividendDate);
											vo.setExDividendDate(exDividendDate);
										} else {
											vo.setExDividendDate(null);
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									vo.setExDividendDate(null);
								}
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

	public static void getOptions() {
		loger.info("StockValue2DB getOptions start -----------" + sdf1.format(new Date()));
		StockDao dao = new StockDao();
		String excelUrl = "";
		// http://www.cboe.com/publish/ScheduledTask/MktData/cboesymboldir2.csv
		// http://www.cboe.com/publish/weelkysmf/weeklysmf.xls
		excelUrl = "http://www.cboe.com/publish/ScheduledTask/MktData/cboesymboldir2.csv";

		String saveToFile = "D:/" + new Date().getTime() + "_1.csv";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(excelUrl);
		try {
			// send get request
			HttpResponse response = client.execute(get);
			// get http response stream and prepare the fileouputstream
			InputStream in = response.getEntity().getContent();
			BufferedInputStream bin = new BufferedInputStream(in);
			OutputStream os = new FileOutputStream(saveToFile);
			Long time1 = System.currentTimeMillis();
			// seems quicker when file is big, commons-io needed.
			IOUtils.copy(bin, os); // quicker
			Long time2 = System.currentTimeMillis();
			// System.out.println("Time spent: " + (double) (time2 - time1) / 1000 + " seconds.");
			bin.close();
			in.close();
			os.close();
			client.getConnectionManager().shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// csv change to xls start
		// new xls
		String newXlsFile = "D:/" + new Date().getTime() + "_2.xls";
		ArrayList<ArrayList<String>> arList = new ArrayList<ArrayList<String>>();
		ArrayList<String> al = null;
		String thisLine;
		try {
			DataInputStream myInput = new DataInputStream(new FileInputStream(saveToFile));
			while ((thisLine = myInput.readLine()) != null) {
				al = new ArrayList<String>();
				String strar[] = thisLine.split(",");
				for (int j = 0; j < strar.length; j++) {
					// My Attempt (BELOW)
					String edit = strar[j].replace('\n', ' ');
					al.add(edit);
				}
				arList.add(al);
				// System.out.println();
			}
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList<String> ardata = (ArrayList<String>) arList.get(k);
				HSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					// System.out.print(ardata.get(p));
					HSSFCell cell = row.createCell((short) p);
					cell.setCellValue(ardata.get(p).toString());
				}
			}
			FileOutputStream fileOut = new FileOutputStream(newXlsFile);
			hwb.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// del saveToFile
		File delFile1 = new File(saveToFile);
		if (delFile1.isFile()) {
			delFile1.delete();
		}
		// csv change to xls end

		// set to poi excel
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(newXlsFile);
			// POIFSFileSystem fs = new POIFSFileSystem(fileIn);
			HSSFWorkbook wb = new HSSFWorkbook(fileIn);
			Sheet sheet = wb.getSheetAt(0);
			int row_num = sheet.getLastRowNum();
			System.out.println("row_num:" + row_num);
			short row = 0;
			Row r = sheet.getRow(row);
			row = 2;

			// 先將db options 設成空,在update

			dao.updateAllOptionsEmpty();
			String updDateString = sdf1.format(new Date());

			for (; row <= row_num; row++) { // Excel每筆record
				r = sheet.getRow(row);
				if (PoiUtil.isRowBlank(r))
					continue;
				String str0 = PoiUtil.getCellString(r.getCell(0));
				String stockCode = PoiUtil.getCellString(r.getCell(1));
				System.out.println("Options  stock :" + stockCode);
				if (row == 250) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(30000);
				}
				if (row == 500) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(30000);
				}
				if (row == 750) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(30000);
				}
				if (row == 1000) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(60000);
				}
				if (row == 1500) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(30000);
				}
				if (row == 1750) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(30000);
				}
				if (row == 2000) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(60000);
				}
				if (row == 2500) {
					// System.out.println("Options  row :" + row);
					Thread.sleep(30000);
				}
				Stock findStock = dao.findByStockCodeToStock(stockCode);
				if (null == findStock) {
					Stock newStock = new Stock();
					newStock.setStockCode(stockCode);
					newStock.setUpdateDate(updDateString);
					newStock.setTitle(str0);
					dao.insert(newStock);
					// System.out.println("Options insert stock :" + stockCode);
				}

				Stock thisStock = new Stock();
				thisStock.setStockCode(stockCode);
				thisStock.setUpdateDate(updDateString);
				thisStock.setOptions("O");
				dao.updateOptions(thisStock);
				// String str2 = PoiUtil.getCellString(r.getCell(2));
				// String str3 = PoiUtil.getCellString(r.getCell(3));
				// String str4 = PoiUtil.getCellString(r.getCell(4));
				// String str5 = PoiUtil.getCellString(r.getCell(5));
				// String str6 = PoiUtil.getCellString(r.getCell(6));
				// String str7 = PoiUtil.getCellString(r.getCell(7));
				// String str8 = PoiUtil.getCellString(r.getCell(8));
				// System.out.println("stockCode:" + stockCode);
			}
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// del newXlsFile
		File delFile2 = new File(newXlsFile);
		if (delFile2.isFile()) {
			delFile2.delete();
		}
		loger.info("StockValue2DB getOptions end -----------" + sdf1.format(new Date()));
	}

	public static void getWeeklyOptions() {
		loger.info("StockValue2DB getWeeklyOptions start -----------" + sdf1.format(new Date()));
		String excelUrl = "";
		excelUrl = "http://www.cboe.com/publish/weelkysmf/weeklysmf.xls";

		String saveToFile = "D:/" + new Date().getTime() + "_1.xls";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(excelUrl);
		try {
			// send get request
			HttpResponse response = client.execute(get);
			// get http response stream and prepare the fileouputstream
			InputStream in = response.getEntity().getContent();
			BufferedInputStream bin = new BufferedInputStream(in);
			OutputStream os = new FileOutputStream(saveToFile);
			Long time1 = System.currentTimeMillis();
			// seems quicker when file is big, commons-io needed.
			IOUtils.copy(bin, os); // quicker
			Long time2 = System.currentTimeMillis();
			// System.out.println("Time spent: " + (double) (time2 - time1) / 1000 + " seconds.");
			bin.close();
			in.close();
			os.close();
			client.getConnectionManager().shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// set to poi excel
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(saveToFile);
			// POIFSFileSystem fs = new POIFSFileSystem(fileIn);
			HSSFWorkbook wb = new HSSFWorkbook(fileIn);
			Sheet sheet = wb.getSheetAt(0);
			int row_num = sheet.getLastRowNum();
			System.out.println("getWeeklyOptions row_num:" + row_num);
			short row = 0;
			Row r = sheet.getRow(row);
			row = 2;

			// 先將db options 設成空,在update
			StockDao dao = new StockDao();
			dao.updateAllWeeklyoptionsEmpty();
			String updDateString = sdf1.format(new Date());
			for (; row <= row_num; row++) { // Excel每筆record
				r = sheet.getRow(row);
				if (PoiUtil.isRowBlank(r))
					continue;
				String stockCode = PoiUtil.getCellString(r.getCell(0));
				if (row == 100) {
					Thread.sleep(30000);
				}
				if (row == 200) {
					Thread.sleep(30000);
				}
				if (row == 300) {
					Thread.sleep(30000);
				}

				// String str1 = PoiUtil.getCellString(r.getCell(1));
				// String str2 = PoiUtil.getCellString(r.getCell(2));
				// String str3 = PoiUtil.getCellString(r.getCell(3));
				// String str4 = PoiUtil.getCellString(r.getCell(4));
				// String str5 = PoiUtil.getCellString(r.getCell(5));
				// String str6 = PoiUtil.getCellString(r.getCell(6));
				// String str7 = PoiUtil.getCellString(r.getCell(7));
				// String str8 = PoiUtil.getCellString(r.getCell(8));
				if (stockCode.length() < 6) {
					stockCode = stockCode.replaceAll("\\*", "");
					System.out.println("stockCode:" + stockCode);
					if (null == dao.findByStockCodeToStock(stockCode)) {
						Stock newStock = new Stock();
						newStock.setStockCode(stockCode);
						newStock.setUpdateDate(updDateString);
						dao.insert(newStock);
						System.out.println("Weeklyoptions insert stock :" + stockCode);
					}

					Stock thisStock = new Stock();
					thisStock.setStockCode(stockCode);
					thisStock.setUpdateDate(updDateString);
					thisStock.setWeeklyoptions("W");
					dao.updateWeeklyoptions(thisStock);
				}

			}
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// del saveToFile
		File delFile2 = new File(saveToFile);
		if (delFile2.isFile()) {
			delFile2.delete();
		}
		loger.info("StockValue2DB getWeeklyOptions end -----------" + sdf1.format(new Date()));
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
