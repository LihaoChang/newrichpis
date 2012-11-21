package com.newRich.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.newRich.backRun.vo.SelectVO;
import com.newRich.backRun.vo.StockForm;
import com.newRich.backRun.vo.StockVO;
import com.newRich.dao.StockDao;
import com.newRich.model.StockBean;
import com.newRich.util.StockBeanCopy;
import com.newRich.util.StockStrategyUtil;

public class StockAction extends DefaultAction {

	private static final long serialVersionUID = -3001143410723349703L;
	private static Log log = LogFactory.getLog(StockAction.class);
	private static Logger log2 = Logger.getLogger(StockAction.class);
	private DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0");
	// Your result List
	private static List<StockBean> gridModel;

	private static StockDao StockDao = new StockDao();
	private String stockCodeStr;
	private String netIncomeStr;
	private String netIncomeGrowthStr;
	private String netMarginStr;
	private String debtEquityStr;
	private String bookValuePerShareStr;
	private String cashPerShareStr;
	private String roeStr;
	private String roaStr;
	private String dividendStr;
	private String sectorStr;
	private String sharesTradedStr;// 成交量
	private String strategyStr;// 策略

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
	private String sharesTradedType;// 成交量
	private String isSp500;
	private List<SelectVO> sectorList = new ArrayList<SelectVO>();// sector的下拉
	private List<SelectVO> typeList = new ArrayList<SelectVO>();// 大於小於的下拉
	private List<SelectVO> strategyList = new ArrayList<SelectVO>();// 大於小於的下拉
	private List<SelectVO> isSP500TypeList = new ArrayList<SelectVO>();//下拉option

	public String query() throws Exception {

		log.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("getThis_ogin_user_id :" + getThis_login_user_id());

		// log2.debug("2Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() +
		// " Index Row :" + getSidx());
		// log2.debug("2Search :" + searchField + " " + searchOper + " " + searchString);
		rows = 10;
		if (page == 0) {
			page = 1;
		}

		// Calcalate until rows ware selected
		int to = (rows * page);

		// Calculate the first row to read
		int from = to - rows;
		try {
			// Handle Search
			int countSearchKey = 0;
			// System.out.println("..stockCodeStr..[" + stockCodeStr + "]");
			// System.out.println("..netIncomeStr..[" + netIncomeStr + "]");
			// System.out.println("..netIncomeGrowthStr..[" + netIncomeGrowthStr + "]");
			// System.out.println("..netMarginStr..[" + netMarginStr + "]");
			// System.out.println("..debtEquityStr..[" + debtEquityStr + "]");
			// System.out.println("..bookValuePerShareStr..[" + bookValuePerShareStr + "]");
			// System.out.println("..cashPerShareStr..[" + cashPerShareStr + "]");
			// System.out.println("..roeStr..[" + roeStr + "]");
			// System.out.println("..roaStr..[" + roaStr + "]");
			// System.out.println("..dividendStr..[" + dividendStr + "]");
			// System.out.println("..sectorStr..[" + sectorStr + "]");
			// System.out.println("..sharesTradedStr..[" + sharesTradedStr + "]");
			// System.out.println("..strategyStr..[" + strategyStr + "]");
			// add sector select
			querySectorList();
			queryTypeList();
			queryStrategyList();
			queryIsSP500TypeList();

			// set查詢的參數
			StockForm formVO = new StockForm();
			if (null != stockCodeStr && !stockCodeStr.equals("") && !stockCodeStr.equals("null")) {
				formVO.setStockCode(stockCodeStr);
				countSearchKey++;
			}

			if (!StringUtils.isBlank(sharesTradedStr) && !sharesTradedStr.equals("null")) {
				if (!StringUtils.isBlank(sharesTradedType) && !sharesTradedType.equals("null")) {
					int thisInt = Integer.parseInt(sharesTradedStr);
					formVO.setSharesTraded(thisInt);
					formVO.setSharesTradedType(typeChange(sharesTradedType));
					countSearchKey++;
				}
			}

			if (null != sectorStr && !sectorStr.equals("") && !sectorStr.equals("null")) {
				formVO.setSector(sectorStr);
				countSearchKey++;
			}

			if (null != strategyStr && !strategyStr.equals("") && !strategyStr.equals("null")) {
				formVO.setStrategy(strategyStr);
				countSearchKey++;
			}

			if (!StringUtils.isBlank(netIncomeStr) && !netIncomeStr.equals("null")) {
				if (!StringUtils.isBlank(netIncomeType) && !netIncomeType.equals("null")) {
					if (!StringUtils.isBlank(netIncomeType2) && !netIncomeType2.equals("null")) {
						Double netIncomeDouble = new Double(netIncomeStr);
						if (netIncomeType2.equals("K")) {
							netIncomeDouble = netIncomeDouble * 1000;
						} else if (netIncomeType2.equals("M")) {
							netIncomeDouble = netIncomeDouble * 1000000;
						} else if (netIncomeType2.equals("B")) {
							netIncomeDouble = netIncomeDouble * 1000000000;
						}
						formVO.setNetIncome(netIncomeDouble);
						formVO.setNetIncomeType(typeChange(netIncomeType));
						countSearchKey++;
					}
				}
			}

			if (!StringUtils.isBlank(netIncomeGrowthStr) && !netIncomeGrowthStr.equals("null")) {
				if (!StringUtils.isBlank(netIncomeGrowthType) && !netIncomeGrowthType.equals("null")) {
					Double thisDouble = new Double(netIncomeGrowthStr);
					thisDouble = thisDouble / 100;
					formVO.setNetIncomeGrowth(thisDouble);
					formVO.setNetIncomeGrowthType(typeChange(netIncomeGrowthType));
					countSearchKey++;
				}
			}

			if (!StringUtils.isBlank(netMarginStr) && !netMarginStr.equals("null")) {
				if (!StringUtils.isBlank(netMarginType) && !netMarginType.equals("null")) {
					Double thisDouble = new Double(netMarginStr);
					thisDouble = thisDouble / 100;
					formVO.setNetMargin(thisDouble);
					formVO.setNetMarginType(typeChange(netMarginType));
					countSearchKey++;
				}
			}

			if (!StringUtils.isBlank(debtEquityStr) && !debtEquityStr.equals("null")) {
				if (!StringUtils.isBlank(debtEquityType) && !debtEquityType.equals("null")) {
					Double thisDouble = new Double(debtEquityStr);
					formVO.setDebtEquity(thisDouble);
					formVO.setDebtEquityType(typeChange(debtEquityType));
					countSearchKey++;
				}
			}
			if (!StringUtils.isBlank(bookValuePerShareStr) && !bookValuePerShareStr.equals("null")) {
				if (!StringUtils.isBlank(bookValuePerShareType) && !bookValuePerShareType.equals("null")) {
					Double thisDouble = new Double(bookValuePerShareStr);
					formVO.setBookValuePerShare(thisDouble);
					formVO.setBookValuePerShareType(typeChange(bookValuePerShareType));
					countSearchKey++;
				}
			}

			if (!StringUtils.isBlank(cashPerShareStr) && !cashPerShareStr.equals("null")) {
				if (!StringUtils.isBlank(cashPerShareType) && !cashPerShareType.equals("null")) {
					Double thisDouble = new Double(cashPerShareStr);
					formVO.setCashPerShare(thisDouble);
					formVO.setCashPerShareType(typeChange(cashPerShareType));
					countSearchKey++;
				}
			}

			if (!StringUtils.isBlank(roeStr) && !roeStr.equals("null")) {
				if (!StringUtils.isBlank(roeType) && !roeType.equals("null")) {
					Double thisDouble = new Double(roeStr);
					thisDouble = thisDouble / 100;
					formVO.setRoe(thisDouble);
					formVO.setRoeType(typeChange(roeType));
					countSearchKey++;
				}
			}

			if (!StringUtils.isBlank(roaStr) && !roaStr.equals("null")) {
				if (!StringUtils.isBlank(roaType) && !roaType.equals("null")) {
					Double thisDouble = new Double(roaStr);
					thisDouble = thisDouble / 100;
					formVO.setRoa(thisDouble);
					formVO.setRoaType(typeChange(roaType));
					countSearchKey++;
				}
			}

			if (!StringUtils.isBlank(dividendStr) && !dividendStr.equals("null")) {
				if (!StringUtils.isBlank(dividendType) && !dividendType.equals("null")) {
					Double thisDouble = new Double(dividendStr);
					thisDouble = thisDouble / 100;
					formVO.setDividend(thisDouble);
					formVO.setDividendType(typeChange(dividendType));
					countSearchKey++;
				}
			}
			
			if (null != isSp500 && !isSp500.equals("") && !isSp500.equals("null")) {
				formVO.setIsSp500(isSp500);
				countSearchKey++;
			}
			
			// Count Stock
			records = StockDao.findAllByForm(formVO).size();

			// Get Stock by Criteria
			List<StockVO> gridModel0 = StockDao.findAllByPage(formVO, from, rows);

			// gridModel = StockDao.findByCriteria(criteria, from, rows);
			// Set to = max rows
			if (to > records) {
				to = records;
			}

			gridModel = new ArrayList<StockBean>();
			// System.out.println("..gridModel.." + gridModel);
			for (StockVO stock : gridModel0) {
				StockBean stockBean = StockBeanCopy.copy(stock);

				String thisNetIncomeStr = stockBean.getNetIncome();
				String thisNetIncomeGrowthStr = stockBean.getNetIncomeGrowth();
				String thisNetMarginStr = stockBean.getNetMargin();
				String thisRoeStr = stockBean.getRoe();
				String thisRoaStr = stockBean.getRoa();
				String thisDividendStr = stockBean.getDividend();

				if (!StringUtils.isBlank(thisNetIncomeStr)) {
					Double netIncomeDouble = new Double(thisNetIncomeStr);
					stockBean.setNetIncome(reSetNetIncome(netIncomeDouble));
				}

				if (!StringUtils.isBlank(thisNetIncomeGrowthStr)) {
					Double netIncomeGrowthStrDouble = new Double(thisNetIncomeGrowthStr);
					stockBean.setNetIncomeGrowth(re100B(netIncomeGrowthStrDouble));
				}

				if (!StringUtils.isBlank(thisNetMarginStr)) {
					Double netMarginStrDouble = new Double(thisNetMarginStr);
					stockBean.setNetMargin(re100B(netMarginStrDouble));
				}

				if (!StringUtils.isBlank(thisRoeStr)) {
					Double roeStrDouble = new Double(thisRoeStr);
					stockBean.setRoe(re100B(roeStrDouble));
				}
				if (!StringUtils.isBlank(thisRoaStr)) {
					Double roaStrDouble = new Double(thisRoaStr);
					stockBean.setRoa(re100B(roaStrDouble));
				}
				if (!StringUtils.isBlank(thisDividendStr)) {
					Double daividendStrDouble = new Double(thisDividendStr);
					stockBean.setDividend(re100B(daividendStrDouble));
				}
				if (null != stockBean.getSharesTraded()) {
					stockBean.setSharesTraded(fmt.format(Double.parseDouble(stockBean.getSharesTraded())));
				}
				gridModel.add(stockBean);
			}
			// Calculate total Pages
			total = (int) Math.ceil((double) records / (double) rows);
			// System.out.println("..rows.." + rows);
			// System.out.println("..page.." + page);
			// System.out.println("..sord.." + sord);
			// System.out.println("..sidx.." + sidx);
			// System.out.println("..searchField.." + searchField);
			// System.out.println("..searchString.." + searchString);
			// System.out.println("..searchOper.." + searchOper);
			// System.out.println("..total.." + total);
			// System.out.println("..records.." + records);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("000000000000000000000000000:  " + e);
			addActionError("ERROR : " + e.getLocalizedMessage());
			return "error";
		}
		return SUCCESS;

	}

	public String reSetNetIncome(Double netIncomeDouble) {
		String outStr = "";
		// thousand （千，十的三次方），
		// million（百萬，十的六次方），
		// 之後就是billion（十億，十的九次方）、
		// trillion（兆，十的十二次方）
		int K = 1000;
		int M = 1000000;
		int B = 1000000000;
		Double T = new Double("1000000000000");
		boolean checkNegative = false;// 檢查是否為負數
		Double netIncomeDouble2 = new Double(0);
		netIncomeDouble2 = netIncomeDouble;

		if (netIncomeDouble < 0) {
			checkNegative = true;
			netIncomeDouble2 = netIncomeDouble * (-1);
		}

		if ((netIncomeDouble2 / T) >= 1) {
			outStr = (netIncomeDouble2 / T) + "T";
		} else {
			if ((netIncomeDouble2 / B) >= 1) {
				outStr = (netIncomeDouble2 / B) + "B";
			} else {
				if ((netIncomeDouble2 / M) >= 1) {
					outStr = (netIncomeDouble2 / M) + "M";
				} else {
					if ((netIncomeDouble2 / K) >= 1) {
						outStr = (netIncomeDouble2 / K) + "K";
					} else {
						outStr = netIncomeDouble2.toString();
					}
				}
			}
		}

		if (checkNegative) {
			outStr = "-" + outStr;
		}

		return outStr;

	}

	public String re100B(Double double0) {
		String outStr = "";
		if (null != double0) {

			BigDecimal bd = new BigDecimal((double0 * 100));
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);// 小數後面四位, 四捨五入
			outStr = bd.toString() + "%";
		}
		return outStr;
	}

	/**
	 * 產生『產業類別』下拉
	 * 
	 * @return
	 */
	public List<SelectVO> querySectorList() {
		List<SelectVO> sectors = new ArrayList<SelectVO>();
		sectors = StockDao.querySectorList();
		sectorList = sectors;
		// System.out.println("action sectors.size():" + sectors.size());

		return sectors;
	}

	/**
	 * 產生『數值比較』下拉
	 * 
	 * @return
	 */
	public List<SelectVO> queryTypeList() {
		List<SelectVO> types = new ArrayList<SelectVO>();

		SelectVO selectVO1 = new SelectVO();
		selectVO1.setString("...");
		selectVO1.setValue("");
		types.add(selectVO1);

		SelectVO selectVO2 = new SelectVO();
		selectVO2.setString(">=");
		selectVO2.setValue("ge");
		types.add(selectVO2);

		SelectVO selectVO3 = new SelectVO();
		selectVO3.setString("<=");
		selectVO3.setValue("le");
		types.add(selectVO3);

		// System.out.println("typeList :" + typeList);
		typeList = types;
		return types;
	}

	/**
	 * 產生『策略』下拉
	 */
	public void queryStrategyList() {
		List<SelectVO> strategys = new ArrayList<SelectVO>();
		SelectVO selectVO1 = new SelectVO();
		selectVO1.setString("...");
		selectVO1.setValue("");
		strategys.add(selectVO1);
		String strategy[] = StockStrategyUtil.STRATEGY_TYPE;
		for (int i = 0; i < strategy.length; i++) {
			String str = strategy[i];
			SelectVO vo = new SelectVO();
			vo.setString(str);
			vo.setValue(str);
			strategys.add(vo);
		}
		strategyList = strategys;
		// System.out.println("action strategyList.size():" + strategyList.size());
	}

	public String typeChange(String typeCode) {
		String str = "";
		if (null != typeCode && !"".equals(typeCode)) {
			if (typeCode.equals("ge")) {
				str = ">=";
			} else {
				str = "<=";
			}
		}
		return str;
	}

	/**
	 * @return an collection that contains the actual data
	 */
	public List<StockBean> getGridModel() {
		return gridModel;
	}

	
	/**
	 * 產生『』下拉
	 * 
	 * @return
	 */
	public List<SelectVO> queryIsSP500TypeList() {
		List<SelectVO> types = new ArrayList<SelectVO>();

		SelectVO selectVO1 = new SelectVO();
		selectVO1.setString("...");
		selectVO1.setValue("");
		types.add(selectVO1);

		SelectVO selectVO2 = new SelectVO();
		selectVO2.setString("Y");
		selectVO2.setValue("Y");
		types.add(selectVO2);

		SelectVO selectVO3 = new SelectVO();
		selectVO3.setString("N");
		selectVO3.setValue("N");
		types.add(selectVO3);

		// System.out.println("typeList :" + typeList);
		isSP500TypeList = types;
		return types;
	}
	
	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<StockBean> gridModel) {
		this.gridModel = gridModel;
	}

	public String getStockCodeStr() {
		return stockCodeStr;
	}

	public void setStockCodeStr(String stockCodeStr) {
		this.stockCodeStr = stockCodeStr;
	}

	public String getNetIncomeStr() {
		return netIncomeStr;
	}

	public void setNetIncomeStr(String netIncomeStr) {
		this.netIncomeStr = netIncomeStr;
	}

	public String getNetIncomeGrowthStr() {
		return netIncomeGrowthStr;
	}

	public void setNetIncomeGrowthStr(String netIncomeGrowthStr) {
		this.netIncomeGrowthStr = netIncomeGrowthStr;
	}

	public String getNetMarginStr() {
		return netMarginStr;
	}

	public void setNetMarginStr(String netMarginStr) {
		this.netMarginStr = netMarginStr;
	}

	public String getDebtEquityStr() {
		return debtEquityStr;
	}

	public void setDebtEquityStr(String debtEquityStr) {
		this.debtEquityStr = debtEquityStr;
	}

	public String getBookValuePerShareStr() {
		return bookValuePerShareStr;
	}

	public void setBookValuePerShareStr(String bookValuePerShareStr) {
		this.bookValuePerShareStr = bookValuePerShareStr;
	}

	public String getCashPerShareStr() {
		return cashPerShareStr;
	}

	public void setCashPerShareStr(String cashPerShareStr) {
		this.cashPerShareStr = cashPerShareStr;
	}

	public String getRoeStr() {
		return roeStr;
	}

	public void setRoeStr(String roeStr) {
		this.roeStr = roeStr;
	}

	public String getRoaStr() {
		return roaStr;
	}

	public void setRoaStr(String roaStr) {
		this.roaStr = roaStr;
	}

	public String getDividendStr() {
		return dividendStr;
	}

	public void setDividendStr(String dividendStr) {
		this.dividendStr = dividendStr;
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

	public String getSectorStr() {
		return sectorStr;
	}

	public void setSectorStr(String sectorStr) {
		this.sectorStr = sectorStr;
	}

	public List<SelectVO> getSectorList() {
		return sectorList;
	}

	public void setSectorList(List<SelectVO> sectorList) {
		this.sectorList = sectorList;
	}

	public List<SelectVO> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<SelectVO> typeList) {
		this.typeList = typeList;
	}

	public String getSharesTradedStr() {
		return sharesTradedStr;
	}

	public void setSharesTradedStr(String sharesTradedStr) {
		this.sharesTradedStr = sharesTradedStr;
	}

	public String getSharesTradedType() {
		return sharesTradedType;
	}

	public void setSharesTradedType(String sharesTradedType) {
		this.sharesTradedType = sharesTradedType;
	}

	public String getStrategyStr() {
		return strategyStr;
	}

	public void setStrategyStr(String strategyStr) {
		this.strategyStr = strategyStr;
	}

	public List<SelectVO> getStrategyList() {
		return strategyList;
	}

	public void setStrategyList(List<SelectVO> strategyList) {
		this.strategyList = strategyList;
	}

	public String getIsSp500() {
		return isSp500;
	}

	public void setIsSp500(String isSp500) {
		this.isSp500 = isSp500;
	}

	public List<SelectVO> getIsSP500TypeList() {
		return isSP500TypeList;
	}

	public void setIsSP500TypeList(List<SelectVO> isSP500TypeList) {
		this.isSP500TypeList = isSP500TypeList;
	}
	
	
}
