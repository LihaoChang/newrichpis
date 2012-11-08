package com.newRich.backRun.vo;

import java.util.Iterator;

import com.newRich.json.JSONObject;

public class ResultSetDetail {
	public String totalResultsReturned;
	public String Result;

	public ResultSetDetail() {
	}

	public ResultSetDetail(JSONObject jsonObject) {
		try {

			Iterator<String> myIter = jsonObject.keys();
			// System.out.println("myIter:"+myIter);
			while (myIter.hasNext()) {
				String nextStr = myIter.next();
				// System.out.println("nextStr:"+nextStr);
				if ("totalResultsReturned".equals(nextStr)) {
					if (null != jsonObject.get("totalResultsReturned") && !jsonObject.get("totalResultsReturned").toString().equals("null")) {
						this.setTotalResultsReturned((String) jsonObject.get("totalResultsReturned"));
					}
				}
				if ("Result".equals(nextStr)) {
					if (null != jsonObject.get("Result") && !jsonObject.get("Result").toString().equals("null")) {
						this.setTotalResultsReturned((String) jsonObject.get("Result"));
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

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

}
