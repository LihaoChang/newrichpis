package com.newRich.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.newRich.model.MenuItem;
import com.newRich.util.SystemUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DefaultAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1687858875295777908L;
	private static Log log = LogFactory.getLog(DefaultAction.class);
	private String this_check_login;
	private String this_login_user_id;
	private String this_login_user_name;
	SystemUtil systemUtil = new SystemUtil();
	
	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	protected Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	protected Integer page = 0;

	// sorting order - asc or desc
	protected String sord = "asc";

	// get index row - i.e. user click to sort.
	protected String sidx;

	// Your Total Pages
	protected Integer total = 0;

	// All Records
	protected Integer records = 0;
	
	public DefaultAction() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getSession(true).getAttribute("check_login") != null) {
			this_check_login = request.getSession(true).getAttribute("check_login").toString();
		}
		if (request.getSession(true).getAttribute("login_user_id") != null) {
			this_login_user_id = request.getSession(true).getAttribute("login_user_id").toString();

		}
		if (request.getSession(true).getAttribute("login_user_name") != null) {
			this_login_user_name = request.getSession(true).getAttribute("login_user_name").toString();
		}
		// log.info("this_check_login:[" + this_check_login + "]");
		// log.info("this_login_user_id :[" + this_login_user_id + "]");
		// log.info("this_login_user_name:[" + this_login_user_name + "]");
		// log.info("getUUID:[" + getUUID() + "]");

		if (null != request.getAttribute("request_locale")) {
			String local = request.getAttribute("request_locale").toString();
			generateMenu(local, request);
		} else if (null != request.getParameter("request_locale")) {
			String local = request.getParameter("request_locale").toString();
			generateMenu(local, request);
		}

	}

	public void generateMenu(String locale, HttpServletRequest request) {
		try {
			if (StringUtils.isNotBlank(locale)) {
				Locale defaultLocale = getSysLocale(locale);
				ActionContext.getContext().setLocale(defaultLocale);
				System.out.println("rrrrrrrrrrrrrrrrdefaultLocale:" + defaultLocale);
				request.getSession().setAttribute("WW_TRANS_I18N_LOCALE", defaultLocale);
			}
			System.out.println("1111111111111111locale:" + locale);
			if (null != request.getSession(true).getAttribute("gridModelMenu")) {

				List<MenuItem> gridModel = (List<MenuItem>) request.getSession(true).getAttribute("gridModelMenu");
				System.out.println("??qqqqqqqqqqqqgridModel:" + gridModel);
				HttpSession session = request.getSession();
				MenuRepository repository = new MenuRepository();
				HttpSession httpsession = (HttpSession) request.getSession();
				ServletContext application = (ServletContext) httpsession.getServletContext();
				MenuRepository defaultRepository = (MenuRepository) application.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
				repository.setDisplayers(defaultRepository.getDisplayers());
				for (int i = 0; i < gridModel.size(); i++) {
					MenuComponent mc = new MenuComponent();
					MenuItem mi = (MenuItem) gridModel.get(i);
					String name = mi.getName();
					mc.setName(name);
					String parent = (String) mi.getParent_name();
					// System.out.println(name + ", parent is: " + parent);
					if (parent != null) {
						MenuComponent parentMenu = repository.getMenu(parent);
						if (parentMenu == null) {
							// System.out.println("parentMenu '" + parent + "' doesn't exist!");
							// create a temporary parentMenu
							parentMenu = new MenuComponent();
							parentMenu.setName(parent);
							repository.addMenu(parentMenu);
						}
						mc.setParent(parentMenu);
					}
					System.out.println("getContext-getText(cancel)---" + ActionContext.getContext().getLocale());
					String title = (String) mi.getTitle();
					System.out.println("-getText(cancel)---" + this.getText("cancel"));
					mc.setTitle(this.getText(title));
					String location = (String) mi.getLocation();
					mc.setLocation(location);
					repository.addMenu(mc);
				}
				session.setAttribute("repository", repository);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// Date date_Ut = new Date();
		// String time_id = String.valueOf(date_Ut.getTime());
		return str;
	}

	/**
	 * @return the this_check_login
	 */
	public String getThis_check_login() {
		return this_check_login;
	}

	/**
	 * @param this_check_login
	 *            the this_check_login to set
	 */
	public void setThis_check_login(String this_check_login) {
		this.this_check_login = this_check_login;
	}

	/**
	 * @return the this_login_user_id
	 */
	public String getThis_login_user_id() {
		return this_login_user_id;
	}

	/**
	 * @param this_login_user_id
	 *            the this_login_user_id to set
	 */
	public void setThis_login_user_id(String this_login_user_id) {
		this.this_login_user_id = this_login_user_id;
	}

	/**
	 * @return the this_login_user_name
	 */
	public String getThis_login_user_name() {
		return this_login_user_name;
	}

	/**
	 * @param this_login_user_name
	 *            the this_login_user_name to set
	 */
	public void setThis_login_user_name(String this_login_user_name) {
		this.this_login_user_name = this_login_user_name;
	}

	public String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 進行轉換
		String dateString = sdf.format(new Date());
		System.out.println(dateString);
		return dateString;
	}

	public static Locale getSysLocale(String localeCode) {
		Locale result = null;
		if (localeCode != null) {
			if (localeCode.startsWith("zh")) {
				if (!localeCode.equals("zh_CN")) {
					localeCode = "zh_TW";
				}
			} else {
				localeCode = "en";
			}

			int indexOfUnderscore = localeCode.indexOf('_');
			if (indexOfUnderscore != -1) {
				String language = localeCode.substring(0, indexOfUnderscore);
				String country = localeCode.substring(indexOfUnderscore + 1);
				result = new Locale(language, country);
			} else {
				result = new Locale(localeCode, "US");
			}
		}
		return result;
	}
	
	/**
	 * @return how many rows we want to have into the grid
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            how many rows we want to have into the grid
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return current page of the query
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            current page of the query
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return total pages for the query
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            total pages for the query
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return total number of records for the query. e.g. select count(*) from table
	 */
	public Integer getRecords() {
		return records;
	}

	/**
	 * @param record
	 *            total number of records for the query. e.g. select count(*) from table
	 */
	public void setRecords(Integer records) {

		this.records = records;

		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records / (double) this.rows);
		} else {
			this.total = 0;
		}
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
}
