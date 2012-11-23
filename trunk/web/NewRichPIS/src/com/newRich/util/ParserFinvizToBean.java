package com.newRich.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.newRich.backRun.vo.FinvizStockVO;

public class ParserFinvizToBean {
	//抓取類型
	public static String PARSER_TYPE_SP500 = "SP500";
	
	//抓取類型的url
	private static String SP500_URL = "http://finviz.com/screener.ashx?v=111&f=idx_sp500";
	private static String test_URL = "http://finviz.com/screener.ashx?v=111&f=idx_sp500&t=aa";
	
	//拆解網頁資料用start
	private static String recordStartString = "</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=";
	private static String recordStockEndString = "'><br>&nbsp;<b>";
	private static String recordDataObjStartString = "class=\"body-table-nw\">";
	private static String recordDataObjEndString = "</td>";
	private static String recordEndString = "</td></tr><tr valign=\"top\"";
	private static String pbPageUrl = "&r=";
	
	public static void main(String[] args) {
		try {
			List<FinvizStockVO> list = getStockInfoListByType(PARSER_TYPE_SP500);
			System.out.println("list.size():" + list.size());
			System.out.println("list:" + list.toString());
		} catch (Exception d) {
			d.printStackTrace();
		}
	}
	
	public static List<FinvizStockVO> getStockInfoListByType(String type) {
		List<FinvizStockVO> list = new ArrayList<FinvizStockVO>();
		int start = 1, pageOfRecords = 20;
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		String url = "", responseBody = "", pageMaxNo = "";
		try {
			if (PARSER_TYPE_SP500.equals(type)) {
				url = SP500_URL;
//				url = test_URL;
			}
			
			if(checkResponseCode(url)){//判斷網站是否正常
				httpget = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpclient.execute(httpget, responseHandler);
				String stock = "";
				//查詢的條件查不到就會出現字串"<b>Total: </b>0"
				if (responseBody.indexOf("<b>Total: </b>0") > -1) {
					list = null;
				} else {
					//判斷是否有分頁
					pageMaxNo = checkMaxPageNo(responseBody);
					System.out.println("pageMaxNo:" + pageMaxNo);
					
					//有資料開始處理，取得的第一頁先處理
					for (int i = start; i <= pageOfRecords; i++) {
						//System.out.println("i:" + i);
						if (responseBody.indexOf(String.valueOf(i) + recordStartString) > 0) {
							stock = responseBody.substring(responseBody.indexOf(String.valueOf(i) + recordStartString) + recordStartString.length() + String.valueOf(i).length(), responseBody.indexOf(recordStockEndString));
							responseBody = responseBody.substring(responseBody.indexOf(stock+"</a></td>"));//ticker之後
							//responseBody = responseBody.substring(0, responseBody.indexOf(recordDataEndString));//資料區
							String datas[] = responseBody.split("<td height=\"10\"");
							
							FinvizStockVO vo = new FinvizStockVO();
							String end = "</span></td>";
							for (int j = 0; j < datas.length; j++) {
								//System.out.println("datas:" + j + " value:" + datas[j]);
								String str  = datas[j];
								if (j == 0) {
									str = str.substring(0, str.indexOf("</a></td>"));
									vo.setTicker(str);
									//System.out.println("stock:" + str);
								}
								if (j == 1) {
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setCompany(str);
									//System.out.println("company:" + str);
								}
								if (j == 2) {
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setSector(str);
									//System.out.println("sector:" + str);
								}
								if (j == 3) {
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setIndustry(str);
									//System.out.println("industry:" + str);
								}
								if (j == 4) {
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setCountry(str);
									//System.out.println("country:" + str);
								}
								if (j == 5) {
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setMarketCap(str);
									//System.out.println("marketCap:" + str);
								}
								if (j == 6) {
									//<span style="color:#008800;">綠
									//<span style="color:#aa0000;">紅
									if(str.indexOf("<span style=\"color:#008800;\">") > 0){
										str = str.substring(str.indexOf("<span style=\"color:#008800;\">") + "<span style=\"color:#008800;\">".length(), str.indexOf(end));
										vo.setPe(str);
										//System.out.println("pe:" + str);
									}else if(str.indexOf("<span style=\"color:#aa0000;\">") > 0){
										str = str.substring(str.indexOf("<span style=\"color:#aa0000;\">") + "<span style=\"color:#aa0000;\">".length(), str.indexOf(end));
										vo.setPe(str);
										//System.out.println("pe:" + str);
									}else{
										str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
										vo.setPe(str);
										//System.out.println("pe:" + str);
									}
									
								}
								if (j == 7) {
									//<span style="color:#008800;">xx.XX</span></td>
									//<span style="color:#aa0000;">
									if(str.indexOf("<span style=\"color:#008800;\">") > 0){
										str = str.substring(str.indexOf("<span style=\"color:#008800;\">") + "<span style=\"color:#008800;\">".length(), str.indexOf(end));
										vo.setPrice(Double.valueOf(str));
										//System.out.println("Price:" + str);
									}else if(str.indexOf("<span style=\"color:#aa0000;\">") > 0){
										str = str.substring(str.indexOf("<span style=\"color:#aa0000;\">") + "<span style=\"color:#aa0000;\">".length(), str.indexOf(end));
										vo.setPrice(Double.valueOf(str));
										//System.out.println("Price:" + str);
									}else{
										str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
										vo.setPrice(Double.valueOf(str));
										//System.out.println("Price:" + str);
									}
									
								}
								if (j == 8) {
									if(str.indexOf("<span style=\"color:#008800;\">") > 0){
										str = str.substring(str.indexOf("<span style=\"color:#008800;\">") + "<span style=\"color:#008800;\">".length(), str.indexOf(end));
										str = str.replace("%", "");
										vo.setChangePer(Double.valueOf(str));
										//System.out.println("changePer:" + str);
									}else if(str.indexOf("<span style=\"color:#aa0000;\">") > 0){
										str = str.substring(str.indexOf("<span style=\"color:#aa0000;\">") + "<span style=\"color:#aa0000;\">".length(), str.indexOf(end));
										str = str.replace("%", "");
										vo.setChangePer(Double.valueOf(str));
										//System.out.println("changePer:" + str);
									}else{
										str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
										str = str.replace("%", "");
										vo.setChangePer(Double.valueOf(str));
										//System.out.println("changePer:" + str);
									}
								}
								if (j == 9) {
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									str = str.replaceAll(",", "");
									vo.setVolume(Double.valueOf(str));
									//System.out.println("volume:" + str);
								}

							}
							
							list.add(vo);
							responseBody = responseBody.substring(responseBody.indexOf(recordEndString) + recordEndString.length(), responseBody.length());
						}
					}
					
					//如果有其他的分頁，就再各別的分頁去解析
					if(!"".equals(pageMaxNo)){
						int maxNo = Integer.valueOf(pageMaxNo);
						for (int i = 2; i <= maxNo; i++) {
							parsetOtherPage(url, list, String.valueOf(i));
						}
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return list;
	}
	
	/**
	 * 共用的方法，若有第二頁以上的符合策略的話，再進行同樣的解析
	 * @param url 佈局類型的url
	 * @param list 第一頁塞進的資料list
	 * @param pageNo 要查第幾頁
	 * @return
	 */
	public static List<FinvizStockVO> parsetOtherPage(String url, List<FinvizStockVO> list, String pageNo){
		int start = 1, pageOfRecords = 20, goPageNO = 21;
		HttpGet httpget = null;
		HttpClient httpclient = new DefaultHttpClient();
		String responseBody = "";
		if (!"".equals(pageNo)) {
			int pageno = Integer.valueOf(pageNo);
			if(pageno > 2){
				goPageNO = (pageno - 1) * 20 + 1;
			}
			start = ((Integer.valueOf(pageNo) - 1) * pageOfRecords) + 1;
			pageOfRecords = pageOfRecords * Integer.valueOf(pageNo);
			//System.out.println("goPageNO:"+goPageNO+", pageNo:" + pageNo + ", start:" + start + ", pageOfRecords:" + pageOfRecords);
		}
		try {
			url = url + pbPageUrl + String.valueOf(goPageNO);
			//System.out.println("url:"+url);
			if(checkResponseCode(url)){//判斷網站是否正常
				httpget = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpclient.execute(httpget, responseHandler);
				String stock = "";
				//有資料開始處理，取得的第一頁先處理
				for (int i = start; i <= pageOfRecords; i++) {
					//System.out.println("i:" + i);
					if (responseBody.indexOf(String.valueOf(i) + recordStartString) > 0) {
						stock = responseBody.substring(responseBody.indexOf(String.valueOf(i) + recordStartString) + recordStartString.length() + String.valueOf(i).length(), responseBody.indexOf(recordStockEndString));
						responseBody = responseBody.substring(responseBody.indexOf(stock+"</a></td>"));//ticker之後
						//responseBody = responseBody.substring(0, responseBody.indexOf(recordDataEndString));//資料區
						String datas[] = responseBody.split("<td height=\"10\"");
						
						FinvizStockVO vo = new FinvizStockVO();
						String end = "</span></td>";
						for (int j = 0; j < datas.length; j++) {
							//System.out.println("datas:" + j + " value:" + datas[j]);
							String str  = datas[j];
							if (j == 0) {
								str = str.substring(0, str.indexOf("</a></td>"));
								vo.setTicker(str);
								//System.out.println("stock:" + str);
							}
							if (j == 1) {
								str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
								vo.setCompany(str);
								//System.out.println("company:" + str);
							}
							if (j == 2) {
								str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
								vo.setSector(str);
								//System.out.println("sector:" + str);
							}
							if (j == 3) {
								str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
								vo.setIndustry(str);
								//System.out.println("industry:" + str);
							}
							if (j == 4) {
								str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
								vo.setCountry(str);
								//System.out.println("country:" + str);
							}
							if (j == 5) {
								str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
								vo.setMarketCap(str);
								//System.out.println("marketCap:" + str);
							}
							if (j == 6) {
								//<span style="color:#008800;">綠
								//<span style="color:#aa0000;">紅
								if(str.indexOf("<span style=\"color:#008800;\">") > 0){
									str = str.substring(str.indexOf("<span style=\"color:#008800;\">") + "<span style=\"color:#008800;\">".length(), str.indexOf(end));
									vo.setPe(str);
									//System.out.println("pe:" + str);
								}else if(str.indexOf("<span style=\"color:#aa0000;\">") > 0){
									str = str.substring(str.indexOf("<span style=\"color:#aa0000;\">") + "<span style=\"color:#aa0000;\">".length(), str.indexOf(end));
									vo.setPe(str);
									//System.out.println("pe:" + str);
								}else{
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setPe(str);
									//System.out.println("pe:" + str);
								}
								
							}
							if (j == 7) {
								//<span style="color:#008800;">xx.XX</span></td>
								//<span style="color:#aa0000;">
								if(str.indexOf("<span style=\"color:#008800;\">") > 0){
									str = str.substring(str.indexOf("<span style=\"color:#008800;\">") + "<span style=\"color:#008800;\">".length(), str.indexOf(end));
									vo.setPrice(Double.valueOf(str));
									//System.out.println("Price:" + str);
								}else if(str.indexOf("<span style=\"color:#aa0000;\">") > 0){
									str = str.substring(str.indexOf("<span style=\"color:#aa0000;\">") + "<span style=\"color:#aa0000;\">".length(), str.indexOf(end));
									vo.setPrice(Double.valueOf(str));
									//System.out.println("Price:" + str);
								}else{
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									vo.setPrice(Double.valueOf(str));
									//System.out.println("Price:" + str);
								}
								
							}
							if (j == 8) {
								if(str.indexOf("<span style=\"color:#008800;\">") > 0){
									str = str.substring(str.indexOf("<span style=\"color:#008800;\">") + "<span style=\"color:#008800;\">".length(), str.indexOf(end));
									str = str.replace("%", "");
									vo.setChangePer(Double.valueOf(str));
									//System.out.println("changePer:" + str);
								}else if(str.indexOf("<span style=\"color:#aa0000;\">") > 0){
									str = str.substring(str.indexOf("<span style=\"color:#aa0000;\">") + "<span style=\"color:#aa0000;\">".length(), str.indexOf(end));
									str = str.replace("%", "");
									vo.setChangePer(Double.valueOf(str));
									//System.out.println("changePer:" + str);
								}else{
									str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
									str = str.replace("%", "");
									vo.setChangePer(Double.valueOf(str));
									//System.out.println("changePer:" + str);
								}
							}
							if (j == 9) {
								str = str.substring(str.indexOf(recordDataObjStartString) + recordDataObjStartString.length(), str.indexOf(recordDataObjEndString));
								str = str.replaceAll(",", "");
								vo.setVolume(Double.valueOf(str));
								//System.out.println("volume:" + str);
							}

						}
						
						list.add(vo);
						responseBody = responseBody.substring(responseBody.indexOf(recordEndString) + recordEndString.length(), responseBody.length());
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return list; 
	}
	
	/**
	 * 判斷是否有分頁，有則返回分頁最大值
	 * @param bodyString
	 * @return
	 */
	public static String checkMaxPageNo(String responseBody){
		String pageMaxNo = "";
		if(StringUtils.isNotEmpty(responseBody)){
			//判斷是否有分頁
			responseBody.indexOf("<b>next</b>");
			//取得分頁最大值
			int lastPageHeader = responseBody.lastIndexOf("class=\"screener-pages\">");
			if(lastPageHeader > -1){
				//System.out.println("lastPageHeader:"+lastPageHeader);
				pageMaxNo = responseBody.substring(lastPageHeader+"class=\"screener-pages\">".length(), responseBody.lastIndexOf("</a> <a"));
				//System.out.println("maxPage:"+maxPage);	
			}
		}
		return pageMaxNo;
	}
	
	/**
	 * 驗證該url是否為正常有效的url
	 * 
	 * @param url
	 * @return
	 */
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return is200;
	}
	
}
