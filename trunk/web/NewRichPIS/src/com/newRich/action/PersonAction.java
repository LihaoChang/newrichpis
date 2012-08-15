package com.newRich.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.backRun.vo.PersonForm;
import com.newRich.dao.PersonDao;
import com.newRich.model.Person;

public class PersonAction extends DefaultAction {

	private static final long serialVersionUID = -3001143410723349703L;
	private static Log log = LogFactory.getLog(PersonAction.class);
	// Your result List
	private List<Person> gridModel;

	private PersonDao personDao = new PersonDao();
	private String id;
	private String name;
	private String password;

	private String modify_id;


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

			System.out.println("..name..[" + name + "]");
			System.out.println("..password..[" + password + "]");
			
			PersonForm formVO = new PersonForm();
			if (null != name && !name.equals("") && !name.equals("null")) {
				// criteria.add(checkSQL("eq", "item_type", (new String(item_type.getBytes("ISO8859_1"), "UTF-8"))));
				formVO.setName(name);
				countSearchKey++;
			}
			if (null != password && !password.equals("") && !password.equals("null")) {
				formVO.setPassword(password);
				// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
				countSearchKey++;
			}

			// Count Person
			records = personDao.findAllByForm(formVO).size();

			// Get Person by Criteria
			gridModel = personDao.findByUserPwd(formVO, from, rows);
			// Set to = max rows
			if (to > records) {
				to = records;
			}
			System.out.println("..gridModel.." + gridModel);
			// Calculate total Pages
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Exception e) {
			System.out.println("PersonAction query() Exception:  " + e);
			addActionError("ERROR : " + e.getLocalizedMessage());
			return "error";
		}
		return SUCCESS;

	}

	public String save() throws Exception {
		try {
			System.out.println("..id..[" + id + "]");
			System.out.println("..name.." + name);
			System.out.println("..password.." + password);
			// name = (new String(name.getBytes("ISO8859_1"), "UTF-8"));
			// password = (new String(password.getBytes("ISO8859_1"), "UTF-8"));
			Person person;
			if (null == id || id.equals("")) {
				log.debug("Add Person");
				person = new Person();
				String nextid = getUUID();
				log.debug("Id for ne Person is " + nextid);
				person.setId(nextid);
				person.setName(name);
				person.setPassword(password);
				log.info("Person:" + person);
				// PersonDao.save(Person);
				personDao.insert(person);

			} else {
				log.debug("Edit Person");

				person = personDao.findById(id);
				person.setName(name);
				person.setPassword(password);
				log.info("Person:" + person);
				personDao.update(person);
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
				log.debug("Delete Person " + modify_id);
				personDao.delete(modify_id);
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

		System.out.println("..modify.." + modify_id);
		Person Person;
		if (null != modify_id) {
			log.debug("Delete Person " + modify_id);
			Person = personDao.findById(modify_id);
			if (null != Person) {
				id = Person.getId();
				name = Person.getName();
				password = Person.getPassword();
			}
		}
		query();
		return SUCCESS;

	}

	/**
	 * @return an collection that contains the actual data
	 */
	public List<Person> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            an collection that contains the actual data
	 */
	public void setGridModel(List<Person> gridModel) {
		this.gridModel = gridModel;
	}

	/**
	 * @return sorting order
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord
	 *            sorting order
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @return get index row - i.e. user click to sort.
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx
	 *            get index row - i.e. user click to sort.
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
