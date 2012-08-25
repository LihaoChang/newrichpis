package com.newRich.backRun.vo;

public class StockForm {
	public String title;
	public String stockCode; // 股票代碼
	public Double nowPrice;// 成交
	public String url;
	public int sharesTraded;// 成交量
	public String prefixedTicker;
	public Double netIncome;// 淨獲利 Net Income <small>&#9658;</small></a></td><td id="kmValue-148"
							// class="keyMetricValue">73.5M</td>
	public Double netIncomeGrowth;// 淨獲利成長 Net Income Growth <small>&#9658;</small></a></td><td
									// id="kmValue-15" class="keyMetricValue">42.5%</td>
	public Double netMargin;// 淨毛利 Net Margin <small>&#9658;</small></a></td><td id="kmValue-13"
							// class="keyMetricValue">4%</td>
	public Double debtEquity;// 負債/資產比例 //Debt / Equity <small>&#9658;</small></a></td><td
								// id="kmValue-391" class="keyMetricValue">1.11</td>
	public Double bookValuePerShare;// 帳面股價 //Book Value Per Share
									// <small>&#9658;</small></a></td><td id="kmValue-20"
									// class="keyMetricValue">21.4</td><td id="keyMetricRating-20"
									// class="keyMetricRating">&nbsp;</td></tr>
	public Double cashPerShare;// 現金配股 //Cash Per Share <small>&#9658;</small></a></td><td
								// id="kmValue-21" class="keyMetricValue">0.692</td><td
								// id="keyMetricRating-21" class="keyMetricRating">&nbsp;</td></tr>
	public Double roe;// 淨值報酬率 //Return on Equity (ROE) <small>&#9658;</small></a></td><td
						// id="kmValue-18" class="keyMetricValue">8.9%</td><td
						// id="keyMetricRating-18" class="keyMetricRating">&nbsp;</td></tr>
	public Double roa;// 總資產報酬率 //Return on Assets (ROA) <small>&#9658;</small></a></td><td
						// id="kmValue-17" class="keyMetricValue">6.2%</td><td
						// id="keyMetricRating-17" class="keyMetricRating">&nbsp;</td></tr>
	public Double dividend;// 股息百分比 //Dividend Yield</a></td><td id="kmValue-19"
							// class="keyMetricValue">1.4%</td><td id="keyMetricRating-19"
							// class="keyMetricRating">&nbsp;</td></tr>

	public String reScheduleDate;// 下次重跑的時間
	public String createDate;// 建立時間
	public String updateDate;// 修改時間

	public String sector;// 產業別
	public String strategy;

	private String netIncomeType;
	private String netIncomeType2;
	private String netIncomeGrowthType;
	private String netMarginType;
	private String debtEquityType;
	private String bookValuePerShareType;
	private String cashPerShareType;
	private String roeType;
	private String roaType;
	private String dividendType;
	private String sharesTradedType;
	private String sord;

	public StockForm() {

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

	public Double getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(Double nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSharesTraded() {
		return sharesTraded;
	}

	public void setSharesTraded(int sharesTraded) {
		this.sharesTraded = sharesTraded;
	}

	public String getPrefixedTicker() {
		return prefixedTicker;
	}

	public void setPrefixedTicker(String prefixedTicker) {
		this.prefixedTicker = prefixedTicker;
	}

	public Double getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(Double netIncome) {
		this.netIncome = netIncome;
	}

	public Double getNetIncomeGrowth() {
		return netIncomeGrowth;
	}

	public void setNetIncomeGrowth(Double netIncomeGrowth) {
		this.netIncomeGrowth = netIncomeGrowth;
	}

	public Double getNetMargin() {
		return netMargin;
	}

	public void setNetMargin(Double netMargin) {
		this.netMargin = netMargin;
	}

	public Double getDebtEquity() {
		return debtEquity;
	}

	public void setDebtEquity(Double debtEquity) {
		this.debtEquity = debtEquity;
	}

	public Double getBookValuePerShare() {
		return bookValuePerShare;
	}

	public void setBookValuePerShare(Double bookValuePerShare) {
		this.bookValuePerShare = bookValuePerShare;
	}

	public Double getCashPerShare() {
		return cashPerShare;
	}

	public void setCashPerShare(Double cashPerShare) {
		this.cashPerShare = cashPerShare;
	}

	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getRoa() {
		return roa;
	}

	public void setRoa(Double roa) {
		this.roa = roa;
	}

	public Double getDividend() {
		return dividend;
	}

	public void setDividend(Double dividend) {
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

	public String getNetIncomeType() {
		return netIncomeType;
	}

	public void setNetIncomeType(String netIncomeType) {
		this.netIncomeType = netIncomeType;
	}

	public String getNetIncomeType2() {
		return netIncomeType2;
	}

	public void setNetIncomeType2(String netIncomeType2) {
		this.netIncomeType2 = netIncomeType2;
	}

	public String getNetIncomeGrowthType() {
		return netIncomeGrowthType;
	}

	public void setNetIncomeGrowthType(String netIncomeGrowthType) {
		this.netIncomeGrowthType = netIncomeGrowthType;
	}

	public String getNetMarginType() {
		return netMarginType;
	}

	public void setNetMarginType(String netMarginType) {
		this.netMarginType = netMarginType;
	}

	public String getDebtEquityType() {
		return debtEquityType;
	}

	public void setDebtEquityType(String debtEquityType) {
		this.debtEquityType = debtEquityType;
	}

	public String getBookValuePerShareType() {
		return bookValuePerShareType;
	}

	public void setBookValuePerShareType(String bookValuePerShareType) {
		this.bookValuePerShareType = bookValuePerShareType;
	}

	public String getCashPerShareType() {
		return cashPerShareType;
	}

	public void setCashPerShareType(String cashPerShareType) {
		this.cashPerShareType = cashPerShareType;
	}

	public String getRoeType() {
		return roeType;
	}

	public void setRoeType(String roeType) {
		this.roeType = roeType;
	}

	public String getRoaType() {
		return roaType;
	}

	public void setRoaType(String roaType) {
		this.roaType = roaType;
	}

	public String getDividendType() {
		return dividendType;
	}

	public void setDividendType(String dividendType) {
		this.dividendType = dividendType;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSharesTradedType() {
		return sharesTradedType;
	}

	public void setSharesTradedType(String sharesTradedType) {
		this.sharesTradedType = sharesTradedType;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

}
