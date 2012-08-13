package com.newRich.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.backRun.vo.StockToolForm;
import com.newRich.dao.StockToolDao;
import com.newRich.model.StockTool;

public class StockToolAction extends DefaultAction {

	private static final long serialVersionUID = -3001143410723349703L;
	private static Log log = LogFactory.getLog(StockToolAction.class);
	// Your result List
	private List<StockTool> gridModel;

	private StockToolDao stockToolDao = new StockToolDao();
	private String id;
	private String userid;
	private String name;
	private String url;
	private String remark;

	private String modify_id;

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

		// Handle Search
		int countSearchKey = 0;
		try {

			System.out.println("..name..[" + name + "]");
			StockToolForm formVO = new StockToolForm();
			if (null != name && !name.equals("") && !name.equals("null")) {
				// criteria.add(checkSQL("eq", "item_type", (new
				// String(item_type.getBytes("ISO8859_1"), "UTF-8"))));
				formVO.setName(name);
				countSearchKey++;
			}

			// Count StockTool
			records = stockToolDao.findAll().size();

			// System.out.println("..criteria.." + criteria);
			// Get StockTool by Criteria
			gridModel = stockToolDao.findByFromEnd(formVO, from, rows);
			// Set to = max rows
			if (to > records) {
				to = records;
			}
			// System.out.println("..gridModel.." + gridModel);
			// Calculate total Pages
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Exception e) {
			System.out.println("000000000000000000000000000:  " + e);
			addActionError("ERROR : " + e.getLocalizedMessage());
			return "error";
		}
		return SUCCESS;

	}

	public String save() throws Exception {
		try {
			System.out.println("..id..[" + id + "]");
			System.out.println("..userid.." + userid);
			System.out.println("..name.." + name);
			System.out.println("..url.." + url);
			System.out.println("..remark.." + remark);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date thisDate = new Date();
			String thisDateStr = sdf1.format(thisDate);
			// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
			// password = (new String(password.getBytes("ISO8859_1"), "UTF-8"));
			StockTool stockTool;
			if (null == id || id.equals("")) {
				log.debug("Add StockTool");
				stockTool = new StockTool();
				String nextid = getUUID();
				log.debug("Id for ne StockTool is " + nextid);
				stockTool.setId(nextid);
				stockTool.setUserid(getThis_login_user_id());
				stockTool.setName(name);
				stockTool.setUrl(url);
				stockTool.setRemark(remark);
				stockTool.setUpdateDate(thisDateStr);
				log.info("StockTool:" + stockTool);
				// stockToolDao.save(StockTool);
				stockToolDao.insert(stockTool);

			} else {
				log.debug("Edit StockTool");

				stockTool = stockToolDao.findById(id);
				stockTool.setUserid(getThis_login_user_id());
				stockTool.setName(name);
				stockTool.setUrl(url);
				stockTool.setRemark(remark);
				stockTool.setUpdateDate(thisDateStr);
				log.info("StockTool:" + stockTool);
				stockToolDao.update(stockTool);
			}
			// query();

		} catch (Exception e) {
			addActionError("ERROR : " + e.getLocalizedMessage());
			addActionError("Is Database in read/write modus?");
			return "error";
		}
		return SUCCESS;
	}

	public String delete() throws Exception {
		try {
			System.out.println("..delete.." + modify_id);
			if (null != modify_id) {
				log.debug("Delete StockTool " + modify_id);
				stockToolDao.delete(modify_id);
			}
			query();
		} catch (Exception e) {
			addActionError("ERROR : " + e.getLocalizedMessage());
			addActionError("Is Database in read/write modus?");
			return "error";
		}
		return SUCCESS;

	}

	public String modify() throws Exception {
		try {
			System.out.println("..modify.." + modify_id);
			System.out.println("..userid.." + userid);
			System.out.println("..name.." + name);
			System.out.println("..url.." + url);
			System.out.println("..remark.." + remark);
			StockTool StockTool;
			if (null != modify_id) {
				log.debug("Delete StockTool " + modify_id);
				StockTool = stockToolDao.findById(modify_id);
				System.out.println("modify StockTool:" + StockTool);
				if (null != StockTool) {
					id = StockTool.getId();
					userid = StockTool.getUserid();
					name = StockTool.getName();
					url = StockTool.getUrl();
					remark = StockTool.getRemark();

				}
			}
			query();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModify_id() {
		return modify_id;
	}

	public void setModify_id(String modify_id) {
		this.modify_id = modify_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<StockTool> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<StockTool> gridModel) {
		this.gridModel = gridModel;
	}

}
