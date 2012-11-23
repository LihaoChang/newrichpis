package com.newRich.backRun.vo;

public class FinvizStockVO implements java.io.Serializable {

	private static final long serialVersionUID = -1345139627918976357L;
	private String ticker;
	private String company;
	private String sector;
	private String industry;
	private String country;
	private String marketCap;
	private String pe;
	private Double price;
	private Double changePer;
	private Double volume;
	private String updateDate;

	public FinvizStockVO() {
	}

	public FinvizStockVO(String ticker) {
		this.ticker = ticker;
	}

	public FinvizStockVO(String ticker, String company, String sector,
			String industry, String country, String marketCap, String pe, 
			Double price, Double changePer, Double volume, String updateDate) {
		this.ticker = ticker;
		this.company = company;
		this.sector = sector;
		this.industry = industry;
		this.country = country;
		this.marketCap = marketCap;
		this.pe = pe;
		this.price = price;
		this.changePer = changePer;
		this.volume = volume;
		this.updateDate = updateDate;
	}
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}

	public String getPe() {
		return pe;
	}

	public void setPe(String pe) {
		this.pe = pe;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getChangePer() {
		return changePer;
	}

	public void setChangePer(Double changePer) {
		this.changePer = changePer;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
