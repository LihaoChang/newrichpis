package com.newRich.action;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.backRun.vo.CountLogin;
import com.newRich.backRun.vo.RtMember;
import com.newRich.dao.CountLoginDao;

public class LoginCountAction extends DefaultAction {

	private static final long serialVersionUID = -244142998227074556L;
	
	private static Log log = LogFactory.getLog(LoginCountAction.class);
	private DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0");
	// Your result List
	private List<CountLogin> gridModel;
	private static CountLoginDao countLoginDao = new CountLoginDao();
	private String loginDateStart;//登入日開始
	private String loginDateEnd;//登入日結束
	private int loginTotal;
	
	public String query() throws Exception {

		log.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("getThis_ogin_user_id :" + getThis_login_user_id());

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
			 System.out.println("..loginDateStart..[" + loginDateStart + "]");
			 System.out.println("..loginDateEnd..[" + loginDateEnd + "]");
			// add sector select

			// set查詢的參數
			CountLogin formVO = new CountLogin();

			if (!StringUtils.isBlank(loginDateStart) && !loginDateStart.equals("null")) {
				if (!StringUtils.isBlank(loginDateEnd) && !loginDateEnd.equals("null")) {
					String loginDateStartStr = loginDateStart.replaceAll("-", "/");
					String loginDateEndStr = loginDateEnd.replaceAll("-", "/");
					formVO.setDateStart(loginDateStartStr);
					formVO.setDateEnd(loginDateEndStr);
					countSearchKey++;
				}
				//reset date
				this.setLoginDateStart(loginDateStart.replaceAll("/", "-"));
				this.setLoginDateEnd(loginDateEnd.replaceAll("/", "-"));
			}

			// Count Stock
			records = countLoginDao.findAllByForm(formVO).size();

			gridModel = countLoginDao.findAllByFormForPage(formVO, from, rows);
			
			loginTotal = gridModel.size();
			// gridModel = StockDao.findByCriteria(criteria, from, rows);
			// Set to = max rows
			if (to > records) {
				to = records;
			}

			//gridModel = new ArrayList<CountLogin>();
			// Calculate total Pages
			total = (int) Math.ceil((double) records / (double) rows);
//			System.out.println("..rows.." + rows);
//			System.out.println("..page.." + page);
//			System.out.println("..sord.." + sord);
//			System.out.println("..total.." + total);
//			System.out.println("..records.." + records);
//			System.out.println("..loginTotal.." + loginTotal);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("000000000000000000000000000:  " + e);
			addActionError("ERROR : " + e.getLocalizedMessage());
			return "error";
		}
		return SUCCESS;
	}

	public String getLoginDateStart() {
		return loginDateStart;
	}

	public void setLoginDateStart(String loginDateStart) {
		this.loginDateStart = loginDateStart;
	}

	public String getLoginDateEnd() {
		return loginDateEnd;
	}

	public void setLoginDateEnd(String loginDateEnd) {
		this.loginDateEnd = loginDateEnd;
	}

	public int getLoginTotal() {
		return loginTotal;
	}

	public void setLoginTotal(int loginTotal) {
		this.loginTotal = loginTotal;
	}

	public List<CountLogin> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<CountLogin> gridModel) {
		this.gridModel = gridModel;
	}

}
