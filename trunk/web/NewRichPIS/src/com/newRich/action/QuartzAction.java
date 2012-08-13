package com.newRich.action;

import java.util.List;
import java.util.Map;

import com.newRich.dao.QuartzTriggersDao;
import com.opensymphony.xwork2.Action;
public class QuartzAction extends DefaultAction {

	private static final long serialVersionUID = 1023517277357979214L;
	
	private String jobtype;
	private String action;
	private String quartzName;
	private List<Map<String, Object>> list;
	
	public String query() throws Exception {
		QuartzTriggersDao dao = new QuartzTriggersDao();
		list = dao.getQrtzTriggers();
		return Action.SUCCESS;
	}
	
	public String goEditQuartz() throws Exception {
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

}
