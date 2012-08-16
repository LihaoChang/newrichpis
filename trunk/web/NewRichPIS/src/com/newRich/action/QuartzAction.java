package com.newRich.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.newRich.backRun.vo.SelectVO;
import com.newRich.dao.QuartzTriggersDao;
import com.newRich.util.SystemUtil;
import com.opensymphony.xwork2.Action;
public class QuartzAction extends DefaultAction {

	private static final long serialVersionUID = 1023517277357979214L;
	
	private String jobtype;
	private String action;
	private String quartzName;
	private List<Map<String, Object>> list;
	private List<SelectVO> quartzTypeList = new ArrayList<SelectVO>();// quartzType的下拉
	
	public String query() throws Exception {
		QuartzTriggersDao dao = new QuartzTriggersDao();
		list = dao.getQrtzTriggers();
		return Action.SUCCESS;
	}
	
	public String goEditQuartz() throws Exception {
		//取得package：com.newRich.quartz下所有的類別名稱
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

	public String getQuartzName() {
		return quartzName;
	}

	public void setQuartzName(String quartzName) {
		this.quartzName = quartzName;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public List<SelectVO> getQuartzTypeList() {
		return quartzTypeList;
	}

	public void setQuartzTypeList(List<SelectVO> quartzTypeList) {
		this.quartzTypeList = quartzTypeList;
	}

}
