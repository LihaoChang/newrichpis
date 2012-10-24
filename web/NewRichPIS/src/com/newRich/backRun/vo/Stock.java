package com.newRich.backRun.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK", schema = "NEWRICH")
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6225489204178179867L;
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
	public String strategy;// 策略
	public String exDividendDate;// 除權除息日

	public Stock() {
	}

	public Stock(String stockCode) {
		this.stockCode = stockCode;
	}

	public Stock(String stockCode, String title, Double nowPrice, String url, int sharesTraded, String prefixedTicker, Double netIncome,
			Double netIncomeGrowth, Double netMargin, Double debtEquity, Double bookValuePerShare, Double cashPerShare, Double roe, Double roa,
			Double dividend, String reScheduleDate, String createDate, String updateDate, String sector, String strategy, String exDividendDate) {

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
	}

	@Column(name = "TITLE", length = 255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Id()
	@Column(name = "STOCKCODE", unique = true)
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@Column(name = "NOWPRICE")
	public Double getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(Double nowPrice) {
		this.nowPrice = nowPrice;
	}

	@Column(name = "URL", length = 255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "SHARESTRADED")
	public int getSharesTraded() {
		return sharesTraded;
	}

	public void setSharesTraded(int sharesTraded) {
		this.sharesTraded = sharesTraded;
	}

	@Column(name = "PREFIXEDTICKER", length = 100)
	public String getPrefixedTicker() {
		return prefixedTicker;
	}

	public void setPrefixedTicker(String prefixedTicker) {
		this.prefixedTicker = prefixedTicker;
	}

	@Column(name = "NETINCOME")
	public Double getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(Double netIncome) {
		this.netIncome = netIncome;
	}

	@Column(name = "NETINCOMEGROWTH")
	public Double getNetIncomeGrowth() {
		return netIncomeGrowth;
	}

	public void setNetIncomeGrowth(Double netIncomeGrowth) {
		this.netIncomeGrowth = netIncomeGrowth;
	}

	@Column(name = "NETMARGIN")
	public Double getNetMargin() {
		return netMargin;
	}

	public void setNetMargin(Double netMargin) {
		this.netMargin = netMargin;
	}

	@Column(name = "DEBTEQUITY")
	public Double getDebtEquity() {
		return debtEquity;
	}

	public void setDebtEquity(Double debtEquity) {
		this.debtEquity = debtEquity;
	}

	@Column(name = "BOOKVALUEPERSHARE")
	public Double getBookValuePerShare() {
		return bookValuePerShare;
	}

	public void setBookValuePerShare(Double bookValuePerShare) {
		this.bookValuePerShare = bookValuePerShare;
	}

	@Column(name = "CASHPERSHARE")
	public Double getCashPerShare() {
		return cashPerShare;
	}

	public void setCashPerShare(Double cashPerShare) {
		this.cashPerShare = cashPerShare;
	}

	@Column(name = "ROE")
	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	@Column(name = "ROA")
	public Double getRoa() {
		return roa;
	}

	public void setRoa(Double roa) {
		this.roa = roa;
	}

	@Column(name = "DIVIDEND")
	public Double getDividend() {
		return dividend;
	}

	public void setDividend(Double dividend) {
		this.dividend = dividend;
	}

	@Column(name = "RESCHEDULEDATE", length = 50)
	public String getReScheduleDate() {
		return reScheduleDate;
	}

	public void setReScheduleDate(String reScheduleDate) {
		this.reScheduleDate = reScheduleDate;
	}

	@Column(name = "CREATEDATE", length = 50)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATEDATE", length = 50)
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "SECTOR", length = 100)
	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	@Column(name = "STRATEGY", length = 300)
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	@Column(name = "EXDIVIDENDDATE", length = 3)
	public String getExDividendDate() {
		return exDividendDate;
	}

	public void setExDividendDate(String exDividendDate) {
		this.exDividendDate = exDividendDate;
	}

	@Override
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

		builder.append("]");

		return builder.toString();
	}
}
