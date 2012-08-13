package com.newRich.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.util.SystemUtil;

public class HomeAction extends DefaultAction {

	private static final long serialVersionUID = 4755898582114584196L;
	private static final Log log = LogFactory.getLog(HomeAction.class);
	SystemUtil systemUtil = new SystemUtil();

	public String execute() {
		System.out.println("---------------------------------------------home---------------");
		java.util.Date date_Ut = new java.util.Date();
		java.sql.Timestamp date_ts = new java.sql.Timestamp(date_Ut.getTime());
		String thismonthdate = date_ts.toString().substring(0, 7);
		String thismonth_1 = thismonthdate + "-01";
		String thismonth_31 = thismonthdate + "-31";
		System.out.println("===thismonth_1=" + thismonth_1);
		System.out.println("===thismonth_31=" + thismonth_31);
		try {
		} catch (Exception e) {
			System.out.println("  error !! : " + e.getMessage());
		}
		return SUCCESS;
	}

}