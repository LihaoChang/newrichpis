package com.newRich.backRun.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.newRich.json.JSONArray;
import com.newRich.json.JSONObject;

public class ResultSetHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2184969662827494472L;
	public String totalResultsReturned;
	public JSONArray ResultJSONArray;
	public ArrayList<Stock> stockBeanList;

	public ResultSetHeader() {
	}

	public ResultSetHeader(JSONObject jsonObject) throws Exception {
		try {
			Iterator<String> myIter = jsonObject.keys();
			// System.out.println("myIter:" + myIter);
			while (myIter.hasNext()) {
				String nextStr = myIter.next();
				// System.out.println("nextStr:" + nextStr);
				if ("ResultSet".equals(nextStr)) {
					if (null != jsonObject.get("ResultSet") && !jsonObject.get("ResultSet").toString().equals("null")) {
						JSONObject thisSonObject = (JSONObject) jsonObject.get("ResultSet");
						// System.out.println("1getClass:" + thisSonObject.get("totalResultsReturned"));
						// System.out.println("2getClass:" + thisSonObject.get("Result"));
						// System.out.println("3getClass:" + thisSonObject.get("totalResultsReturned").getClass());
						// System.out.println("4getClass:" + thisSonObject.get("Result").getClass());
						this.setTotalResultsReturned(String.valueOf(thisSonObject.get("totalResultsReturned")));
						this.setResultJSONArray((JSONArray) thisSonObject.get("Result"));

						if (null != this.getResultJSONArray()) {
							ArrayList<Stock> stockBeanList0 = new ArrayList<Stock>();
							for (int i = 0; i < this.getResultJSONArray().length(); i++) {
								JSONObject jsonObjectDetail = (JSONObject) (this.getResultJSONArray().optJSONObject(i));
								Stock stockBean = new Stock();

								String titleStr = null;
								String urlStr = null;
								String prefixedTickerStr = null;
								if (null != jsonObjectDetail.get("title") && !jsonObjectDetail.get("title").toString().equals("null")) {
									titleStr = jsonObjectDetail.get("title") == null ? null : String.valueOf(jsonObjectDetail.get("title"));
								}

								if (null != jsonObjectDetail.get("url") && !jsonObjectDetail.get("url").toString().equals("null")) {
									urlStr = jsonObjectDetail.get("url") == null ? null : String.valueOf(jsonObjectDetail.get("url"));
									// System.out.println("urlStr:"+urlStr);
								}

								if (null != jsonObjectDetail.get("prefixedTicker")
										&& !jsonObjectDetail.get("prefixedTicker").toString().equals("null")) {
									prefixedTickerStr = jsonObjectDetail.get("title") == null ? null : String.valueOf(jsonObjectDetail
											.get("prefixedTicker"));
								}
								stockBean.setTitle(titleStr);
								stockBean.setUrl(urlStr);
								stockBean.setPrefixedTicker(prefixedTickerStr);
								stockBeanList0.add(stockBean);
							}
							this.setStockBeanList(stockBeanList0);

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTotalResultsReturned() {
		return totalResultsReturned;
	}

	public void setTotalResultsReturned(String totalResultsReturned) {
		this.totalResultsReturned = totalResultsReturned;
	}

	public JSONArray getResultJSONArray() {
		return ResultJSONArray;
	}

	public void setResultJSONArray(JSONArray resultJSONArray) {
		ResultJSONArray = resultJSONArray;
	}

	public ArrayList<Stock> getStockBeanList() {
		return stockBeanList;
	}

	public void setStockBeanList(ArrayList<Stock> stockBeanList) {
		this.stockBeanList = stockBeanList;
	}

}
