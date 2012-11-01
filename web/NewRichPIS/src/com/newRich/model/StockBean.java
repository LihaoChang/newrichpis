package com.newRich.model;

import java.io.Serializable;

public class StockBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6225489204178179867L;
	public String title;
	public String stockCode; // 股票代碼
	public String nowPrice;// 成交
	public String url;
	public String sharesTraded;// 成交量
	public String prefixedTicker;
	public String netIncome;// 淨獲利 Net Income <small>&#9658;</small></a></td><td id="kmValue-148"
							// class="keyMetricValue">73.5M</td>
	public String netIncomeGrowth;// 淨獲利成長 Net Income Growth <small>&#9658;</small></a></td><td
									// id="kmValue-15" class="keyMetricValue">42.5%</td>
	public String netMargin;// 淨毛利 Net Margin <small>&#9658;</small></a></td><td id="kmValue-13"
							// class="keyMetricValue">4%</td>
	public String debtEquity;// 負債/資產比例 //Debt / Equity <small>&#9658;</small></a></td><td
								// id="kmValue-391" class="keyMetricValue">1.11</td>
	public String bookValuePerShare;// 帳面股價 //Book Value Per Share
									// <small>&#9658;</small></a></td><td id="kmValue-20"
									// class="keyMetricValue">21.4</td><td id="keyMetricRating-20"
									// class="keyMetricRating">&nbsp;</td></tr>
	public String cashPerShare;// 現金配股 //Cash Per Share <small>&#9658;</small></a></td><td
								// id="kmValue-21" class="keyMetricValue">0.692</td><td
								// id="keyMetricRating-21" class="keyMetricRating">&nbsp;</td></tr>
	public String roe;// 淨值報酬率 //Return on Equity (ROE) <small>&#9658;</small></a></td><td
						// id="kmValue-18" class="keyMetricValue">8.9%</td><td
						// id="keyMetricRating-18" class="keyMetricRating">&nbsp;</td></tr>
	public String roa;// 總資產報酬率 //Return on Assets (ROA) <small>&#9658;</small></a></td><td
						// id="kmValue-17" class="keyMetricValue">6.2%</td><td
						// id="keyMetricRating-17" class="keyMetricRating">&nbsp;</td></tr>
	public String dividend;// 股息百分比 //Dividend Yield</a></td><td id="kmValue-19"
							// class="keyMetricValue">1.4%</td><td id="keyMetricRating-19"
							// class="keyMetricRating">&nbsp;</td></tr>

	public String reScheduleDate;// 下次重跑的時間
	public String createDate;// 建立時間
	public String updateDate;// 修改時間
	public String sector;// 產業別
	public String strategy;// 策略
	public String exDividendDate;// 除權除息日
	public String options;// 有無選擇權
	public String weeklyoptions;// 有無weekly選擇權

	public StockBean() {
	}

	public StockBean(String stockCode) {
		this.stockCode = stockCode;
	}

	public StockBean(String stockCode, String title, String nowPrice, String url, String sharesTraded, String prefixedTicker, String netIncome,
			String netIncomeGrowth, String netMargin, String debtEquity, String bookValuePerShare, String cashPerShare, String roe, String roa,
			String dividend, String reScheduleDate, String createDate, String updateDate, String sector, String strategy, String exDividendDate,
			String options, String weeklyoptions) {

		this.stockCode = stockCode;
		this.title = title;
		this.nowPrice = nowPrice;
		this.url = url;
		this.sharesTraded = sharesTraded;
		this.prefixedTicker = prefixedTicker;
		this.netIncome = netIncome;
		this.netIncomeGrowth = netIncomeGrowth;
		this.netMargin = netMargin;
		this.debtEquity = debtEquity;
		this.bookValuePerShare = bookValuePerShare;
		this.cashPerShare = cashPerShare;
		this.roe = roe;
		this.roa = roa;
		this.dividend = dividend;
		this.reScheduleDate = reScheduleDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.sector = sector;
		this.strategy = strategy;
		this.exDividendDate = exDividendDate;
		this.options = options;
		this.weeklyoptions = weeklyoptions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSharesTraded() {
		return sharesTraded;
	}

	public void setSharesTraded(String sharesTraded) {
		this.sharesTraded = sharesTraded;
	}

	public String getPrefixedTicker() {
		return prefixedTicker;
	}

	public void setPrefixedTicker(String prefixedTicker) {
		this.prefixedTicker = prefixedTicker;
	}

	public String getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}

	public String getNetIncomeGrowth() {
		return netIncomeGrowth;
	}

	public void setNetIncomeGrowth(String netIncomeGrowth) {
		this.netIncomeGrowth = netIncomeGrowth;
	}

	public String getNetMargin() {
		return netMargin;
	}

	public void setNetMargin(String netMargin) {
		this.netMargin = netMargin;
	}

	public String getDebtEquity() {
		return debtEquity;
	}

	public void setDebtEquity(String debtEquity) {
		this.debtEquity = debtEquity;
	}

	public String getBookValuePerShare() {
		return bookValuePerShare;
	}

	public void setBookValuePerShare(String bookValuePerShare) {
		this.bookValuePerShare = bookValuePerShare;
	}

	public String getCashPerShare() {
		return cashPerShare;
	}

	public void setCashPerShare(String cashPerShare) {
		this.cashPerShare = cashPerShare;
	}

	public String getRoe() {
		return roe;
	}

	public void setRoe(String roe) {
		this.roe = roe;
	}

	public String getRoa() {
		return roa;
	}

	public void setRoa(String roa) {
		this.roa = roa;
	}

	public String getDividend() {
		return dividend;
	}

	public void setDividend(String dividend) {
		this.dividend = dividend;
	}

	public String getReScheduleDate() {
		return reScheduleDate;
	}

	public void setReScheduleDate(String reScheduleDate) {
		this.reScheduleDate = reScheduleDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getExDividendDate() {
		return exDividendDate;
	}

	public void setExDividendDate(String exDividendDate) {
		this.exDividendDate = exDividendDate;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getWeeklyoptions() {
		return weeklyoptions;
	}

	public void setWeeklyoptions(String weeklyoptions) {
		this.weeklyoptions = weeklyoptions;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(stockCode);
		builder.append(", title=");
		builder.append(title);
		builder.append(", nowPrice=");
		builder.append(nowPrice);
		builder.append(", url=");
		builder.append(url);
		builder.append(", sharesTraded=");
		builder.append(sharesTraded);
		builder.append(", prefixedTicker=");
		builder.append(prefixedTicker);
		builder.append(", netIncome=");
		builder.append(netIncome);
		builder.append(", netIncomeGrowth=");
		builder.append(netIncomeGrowth);
		builder.append(", netMargin=");
		builder.append(netMargin);
		builder.append(", debtEquity=");
		builder.append(debtEquity);
		builder.append(", bookValuePerShare=");
		builder.append(bookValuePerShare);
		builder.append(", cashPerShare=");
		builder.append(cashPerShare);
		builder.append(", roe=");
		builder.append(roe);
		builder.append(", roa=");
		builder.append(roa);
		builder.append(", dividend=");
		builder.append(dividend);
		builder.append(", reScheduleDate=");
		builder.append(reScheduleDate);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", sector=");
		builder.append(sector);
		builder.append(", strategy=");
		builder.append(strategy);
		builder.append(", exDividendDate=");
		builder.append(exDividendDate);
		builder.append(", options=");
		builder.append(options);
		builder.append(", weeklyoptions=");
		builder.append(weeklyoptions);
		
		builder.append("]");

		return builder.toString();
	}
}
