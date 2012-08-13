package com.newRich.util;

import org.apache.commons.lang.StringUtils;

import com.newRich.backRun.vo.StockVO;
import com.newRich.model.StockBean;

public class StockBeanCopy {
	public static StockBean copy(StockVO stock) {
		StockBean stockBean = new StockBean();

		if (!StringUtils.isBlank(stock.getTitle())) {
			stockBean.setTitle(stock.getTitle());
		}
		if (!StringUtils.isBlank(stock.getStockCode())) {
			stockBean.setStockCode(stock.getStockCode());
		}
		if (!StringUtils.isBlank(stock.getSector())) {
			stockBean.setSector(stock.getSector());
		}
		if (null != stock.getNowPrice()) {
			stockBean.setNowPrice(stock.getNowPrice().toString());
		}
		if (!StringUtils.isBlank(stock.getUrl())) {
			stockBean.setUrl(stock.getUrl());
		}
		if (stock.getSharesTraded() >= 0) {
			stockBean.setSharesTraded(stock.getSharesTraded() + "");
		}
		if (!StringUtils.isBlank(stock.getPrefixedTicker())) {
			stockBean.setPrefixedTicker(stock.getPrefixedTicker());
		}
		if (null != stock.getNetIncome()) {
			stockBean.setNetIncome(stock.getNetIncome().toString());
		}
		if (null != stock.getNetIncomeGrowth()) {
			stockBean.setNetIncomeGrowth(stock.getNetIncomeGrowth().toString());
		}
		if (null != stock.getNetMargin()) {
			stockBean.setNetMargin(stock.getNetMargin().toString());
		}
		if (null != stock.getDebtEquity()) {
			stockBean.setDebtEquity(stock.getDebtEquity().toString());
		}
		if (null != stock.getBookValuePerShare()) {
			stockBean.setBookValuePerShare(stock.getBookValuePerShare().toString());
		}
		if (null != stock.getCashPerShare()) {
			stockBean.setCashPerShare(stock.getCashPerShare().toString());
		}
		if (null != stock.getRoe()) {
			stockBean.setRoe(stock.getRoe().toString());
		}
		if (null != stock.getRoa()) {
			stockBean.setRoa(stock.getRoa().toString());
		}
		if (null != stock.getDividend()) {
			stockBean.setDividend(stock.getDividend().toString());
		}
		if (!StringUtils.isBlank(stock.getReScheduleDate())) {
			stockBean.setReScheduleDate(stock.getReScheduleDate());
		}
		if (!StringUtils.isBlank(stock.getCreateDate())) {
			stockBean.setCreateDate(stock.getCreateDate());
		}
		if (!StringUtils.isBlank(stock.getUpdateDate())) {
			stockBean.setUpdateDate(stock.getUpdateDate());
		}

		return stockBean;
	}
}
