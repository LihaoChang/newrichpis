package com.newRich.model;


public class StockHistory implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String stockCode; // 股票代碼
	private String createDate;// 日期
	private String open;// 開盤
	private String high;// 最高
	private String low;// 最低
	private String close;// 收盤
	private String volume;// 成交量
	private String adjClose;// 開市

	public StockHistory() {
	}

	public StockHistory(String id, String createDate) {
		this.id = id;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(String adjClose) {
		this.adjClose = adjClose;
	}

}
