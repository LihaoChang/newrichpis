package com.newRich.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.backRun.vo.SelectVO;
import com.newRich.dao.MemberDao;
import com.newRich.util.RtMember;

public class MemberAction extends DefaultAction {

	private static final long serialVersionUID = -6178361490067849233L;

	private static Log log = LogFactory.getLog(MemberAction.class);
	// Your result List
	private List<RtMember> gridModel;
	
	private MemberDao memberDao = new MemberDao();
	private String memberId;
	private String nickname;
	private String realname;
	private String email;
	private String scale;
	private String memberIdHidden;
	
	private String modify_id;
	
	private List<SelectVO> scaleTypeList = new ArrayList<SelectVO>();// 大於小於的下拉
	
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
			queryScaleTypeList();
			// Handle Search
			int countSearchKey = 0;

			// System.out.println("..name..[" + name + "]");
			// System.out.println("..password..[" + password + "]");

			RtMember formVO = new RtMember();
			if (null != memberId && !memberId.equals("") && !memberId.equals("null")) {
				// criteria.add(checkSQL("eq", "item_type", (new String(item_type.getBytes("ISO8859_1"), "UTF-8"))));
				System.out.println("====memberId:"+memberId);
				formVO.setMemberId(memberId);
				countSearchKey++;
			}
			if (null != nickname && !nickname.equals("") && !nickname.equals("null")) {
				formVO.setNickname(nickname);
				// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
				countSearchKey++;
			}
			if (null != realname && !realname.equals("") && !realname.equals("null")) {
				formVO.setRealname(realname);
				// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
				countSearchKey++;
			}
			if (null != email && !email.equals("") && !email.equals("null")) {
				formVO.setEmail(email);
				// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
				countSearchKey++;
			}
			if (null != scale && !scale.equals("") && !scale.equals("null")) {
				formVO.setScale(scale);
				// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
				countSearchKey++;
			}

			// Count Person
			records = memberDao.findAllByForm(formVO).size();

			// Get Person by Criteria
			gridModel = memberDao.findAllByFormForPage(formVO, from, rows);
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
	
	public String save() throws Exception {
		try {
			System.out.println("====memberIdHidden:"+memberIdHidden);
			if (null == memberIdHidden || memberIdHidden.equals("")) {
				log.debug("Add member");
				RtMember formVO = new RtMember();
				if (null != memberId && !memberId.equals("") && !memberId.equals("null")) {
					formVO.setMemberId(memberId);
				}
				if (null != nickname && !nickname.equals("") && !nickname.equals("null")) {
					formVO.setNickname(nickname);
				}
				if (null != realname && !realname.equals("") && !realname.equals("null")) {
					formVO.setRealname(realname);
				}
				if (null != email && !email.equals("") && !email.equals("null")) {
					formVO.setEmail(email);
				}
				if (null != scale && !scale.equals("") && !scale.equals("null")) {
					if("Y".equals(formVO.getScale())){
						scale = "2";
					}
					if("N".equals(formVO.getScale())){
						scale = "1";
					}
					formVO.setScale(scale);
				}
				// PersonDao.save(Person);
				memberDao.insert(formVO);
			}

		} catch (Exception e) {
			addActionError("ERROR : " + e.getLocalizedMessage());
			addActionError("Is Database in read/write modus?");
			return "error";
		}
		return SUCCESS;
	}

	public String delete() throws Exception {
		try {
			// System.out.println("..delete.." + modify_id);
			if (null != modify_id) {
				log.debug("Delete member " + modify_id);
				memberDao.delete(modify_id);
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
		// System.out.println("..modify.." + modify_id);
		RtMember member;
		if (null != modify_id) {
			log.debug("Delete Person " + modify_id);
			member = memberDao.findById(modify_id);
			if (null != member) {
				memberId = member.getMemberId();
				nickname = member.getNickname();
				realname = member.getRealname();
				email = member.getEmail();
				scale = member.getScale();
				//updateDate = member.getUpdateDate();
			}
		}
		query();
		return SUCCESS;
	}
	
	/**
	 * 產生『是否已付費』下拉
	 * 
	 * @return
	 */
	public List<SelectVO> queryScaleTypeList() {
		List<SelectVO> types = new ArrayList<SelectVO>();

		SelectVO selectVO1 = new SelectVO();
		selectVO1.setString("...");
		selectVO1.setValue("");
		types.add(selectVO1);

		SelectVO selectVO2 = new SelectVO();
		selectVO2.setString("Y");
		selectVO2.setValue("2");
		types.add(selectVO2);

		SelectVO selectVO3 = new SelectVO();
		selectVO3.setString("N");
		selectVO3.setValue("1");
		types.add(selectVO3);

		// System.out.println("typeList :" + typeList);
		scaleTypeList = types;
		return types;
	}
	

	public List<RtMember> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<RtMember> gridModel) {
		this.gridModel = gridModel;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getModify_id() {
		return modify_id;
	}

	public void setModify_id(String modifyId) {
		modify_id = modifyId;
	}

	public List<SelectVO> getScaleTypeList() {
		return scaleTypeList;
	}

	public void setScaleTypeList(List<SelectVO> scaleTypeList) {
		this.scaleTypeList = scaleTypeList;
	}

	public String getMemberIdHidden() {
		return memberIdHidden;
	}

	public void setMemberIdHidden(String memberIdHidden) {
		this.memberIdHidden = memberIdHidden;
	}

}
