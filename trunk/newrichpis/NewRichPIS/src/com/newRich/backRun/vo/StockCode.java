package com.newRich.backRun.vo;

import java.io.Serializable;

public class StockCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String stockCode;

	public StockCode() {
	}

	public StockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

}
