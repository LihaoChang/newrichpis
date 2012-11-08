package com.newRich.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.backRun.vo.QuartzTriggersForm;
import com.newRich.backRun.vo.QuartzTriggersVO;
import com.newRich.backRun.vo.SelectVO;
import com.newRich.dao.QuartzTriggersDao;
import com.newRich.util.SystemUtil;
import com.opensymphony.xwork2.Action;

public class QuartzAction extends DefaultAction {

	private static final long serialVersionUID = 1023517277357979214L;
	private static Log log = LogFactory.getLog(QuartzAction.class);

	private String jobtype;
	private String action;
	private String triggerGroup;
	private List<QuartzTriggersVO> gridModel;

	private List<SelectVO> quartzTypeList = new ArrayList<SelectVO>();// quartzType的下拉

	public String query() throws Exception {
		log.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("getThis_ogin_user_id :" + getThis_login_user_id());

		// log2.debug("2Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
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
			QuartzTriggersForm formVO = new QuartzTriggersForm();
			if (null != triggerGroup && !triggerGroup.equals("") && !triggerGroup.equals("null")) {
				formVO.setTriggerGroup(triggerGroup);
				countSearchKey++;
			}
			// 取得package：com.newRich.quartz下所有的類別名稱
			quartzTypeList = SystemUtil.getSelectClassName();
			QuartzTriggersDao dao = new QuartzTriggersDao();

			// Count Person
			records = dao.findAllByForm(formVO).size();

			// Get Person by Criteria
			gridModel = dao.findByFromEnd(formVO, from, rows);
			// Set to = max rows
			if (to > records) {
				to = records;
			}
			// System.out.println("..gridModel.." + gridModel);
			// Calculate total Pages
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Exception e) {
			// System.out.println("PersonAction query() Exception:  " + e);
			addActionError("ERROR : " + e.getLocalizedMessage());
			return "error";
		}
		return SUCCESS;
	}

	public String goEditQuartz() throws Exception {
		// 取得package：com.newRich.quartz下所有的類別名稱
		quartzTypeList = SystemUtil.getSelectClassName();
		return Action.SUCCESS;
	}

	public String getJobtype() {
		return jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public List<QuartzTriggersVO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<QuartzTriggersVO> gridModel) {
		this.gridModel = gridModel;
	}

	public List<SelectVO> getQuartzTypeList() {
		return quartzTypeList;
	}

	public void setQuartzTypeList(List<SelectVO> quartzTypeList) {
		this.quartzTypeList = quartzTypeList;
	}

}
