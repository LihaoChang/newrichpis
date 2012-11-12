package com.newRich.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.newRich.dao.MenuItemDao;
import com.newRich.dao.PersonDao;
import com.newRich.model.MenuItem;
import com.newRich.model.Person;
import com.newRich.util.SystemUtil;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends DefaultAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3799435975524775234L;
	private static Log log = LogFactory.getLog(LoginAction.class);
	// private static Logger log = Logger.getLogger("LoginAction.class");
	private PersonDao personDao = new PersonDao();
	private String name;
	private String password;
	private String message;
	private List<MenuItem> gridModel;
	private MenuItemDao menuItemDao = new MenuItemDao();

	private String this_check_login;
	private String this_login_user_id;
	private String this_login_user_name;
	SystemUtil systemUtil = new SystemUtil();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String login() throws Exception {
		log.info("check login name :" + name);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();

		// LoginBusiness business = new LoginBusiness();

		String login_user_id = "loginerror";
		String role = "";
		// login_user_id = business.check_login(name, password);
		List<Person> personList = personDao.findByUserPwd(name, password);
		for (Person person : personList) {
			if (person.getName().equals(name) && person.getPassword().equals(password)) {
				login_user_id = person.getId();
				role = person.getRole();
			}
		}
		log.info("check login role :" + role);
		if (login_user_id.equals("loginerror")) {
			log.error("check login error !!");
			addActionError("Enter Error!!");
			return ERROR;
		}
		if (!login_user_id.equals("loginerror")) {
			request.getSession(true).setAttribute("check_login", "loginsuccess");
			request.getSession(true).setAttribute("login_user_id", login_user_id);
			request.getSession(true).setAttribute("login_user_name", name);
			request.getSession(true).setAttribute("login_role", role);
			// intln("=-===========================role:" + role);

			if (request.getSession(true).getAttribute("check_login") != null) {
				this_check_login = request.getSession(true).getAttribute("check_login").toString();
			}
			if (request.getSession(true).getAttribute("login_user_id") != null) {
				this_login_user_id = request.getSession(true).getAttribute("login_user_id").toString();

			}
			if (request.getSession(true).getAttribute("login_user_name") != null) {
				this_login_user_name = request.getSession(true).getAttribute("login_user_name").toString();
			}
			try {
				gridModel = menuItemDao.findAll();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Get Connection Error!", e);
			}

			// menu.listMenuItem();
			// intln("gridModel : "+gridModel.size());
			// intln("gridModel : " + gridModel);
			MenuRepository repository = new MenuRepository();
			HttpSession httpsession = (HttpSession) request.getSession();
			ServletContext application = (ServletContext) httpsession.getServletContext();
			MenuRepository defaultRepository = (MenuRepository) application.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
			repository.setDisplayers(defaultRepository.getDisplayers());
			for (int i = 0; i < gridModel.size(); i++) {
				MenuComponent mc = new MenuComponent();
				MenuItem mi = (MenuItem) gridModel.get(i);

				if (!StringUtils.isBlank(mi.getTarget()) && mi.getTarget().equals(SystemUtil.ADMIN_ROLE)) {
					if (!StringUtils.isBlank(role) && role.equals(SystemUtil.ADMIN_ROLE)) {
						String name = mi.getName();
						mc.setName(name);
						String parent = (String) mi.getParent_name();
						// intln(name + ", parent is: " + parent);
						if (parent != null) {
							MenuComponent parentMenu = repository.getMenu(parent);
							if (parentMenu == null) {
								// intln("parentMenu '" + parent + "' doesn't exist!");
								// create a temporary parentMenu
								parentMenu = new MenuComponent();
								parentMenu.setName(parent);
								repository.addMenu(parentMenu);
							}
							mc.setParent(parentMenu);
						}
						String title = (String) mi.getTitle();
						mc.setTitle(getText(title));
						String location = (String) mi.getLocation();
						mc.setLocation(location);
						repository.addMenu(mc);
					}
				} else {
					String name = mi.getName();
					mc.setName(name);
					String parent = (String) mi.getParent_name();
					// intln(name + ", parent is: " + parent);
					if (parent != null) {
						MenuComponent parentMenu = repository.getMenu(parent);
						if (parentMenu == null) {
							// intln("parentMenu '" + parent + "' doesn't exist!");
							// create a temporary parentMenu
							parentMenu = new MenuComponent();
							parentMenu.setName(parent);
							repository.addMenu(parentMenu);
						}
						mc.setParent(parentMenu);
					}
					String title = (String) mi.getTitle();
					mc.setTitle(getText(title));
					String location = (String) mi.getLocation();
					mc.setLocation(location);
					repository.addMenu(mc);
				}
			}
			// request.setAttribute("repository", repository);
			session.setAttribute("repository", repository);
			session.setAttribute("gridModelMenu", gridModel);
			log.info("check login success !!");
			return SUCCESS;
		}
		return SUCCESS;
	}

	public String ssoGenerateMenu() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String login_user_id = request.getSession().getAttribute("login_user_id").toString();
		String role = request.getSession().getAttribute("login_role").toString();

		try {
			gridModel = menuItemDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Get Connection Error!", e);
		}

		// menu.listMenuItem();
		// intln("gridModel : "+gridModel.size());
		// intln("gridModel : " + gridModel);
		MenuRepository repository = new MenuRepository();
		HttpSession httpsession = (HttpSession) request.getSession();
		ServletContext application = (ServletContext) httpsession.getServletContext();
		MenuRepository defaultRepository = (MenuRepository) application.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		repository.setDisplayers(defaultRepository.getDisplayers());
		for (int i = 0; i < gridModel.size(); i++) {
			MenuComponent mc = new MenuComponent();
			MenuItem mi = (MenuItem) gridModel.get(i);

			if (!StringUtils.isBlank(mi.getTarget()) && mi.getTarget().equals(SystemUtil.ADMIN_ROLE)) {
				if (!StringUtils.isBlank(role) && role.equals(SystemUtil.ADMIN_ROLE)) {
					String name = mi.getName();
					mc.setName(name);
					String parent = (String) mi.getParent_name();
					// intln(name + ", parent is: " + parent);
					if (parent != null) {
						MenuComponent parentMenu = repository.getMenu(parent);
						if (parentMenu == null) {
							// intln("parentMenu '" + parent + "' doesn't exist!");
							// create a temporary parentMenu
							parentMenu = new MenuComponent();
							parentMenu.setName(parent);
							repository.addMenu(parentMenu);
						}
						mc.setParent(parentMenu);
					}
					String title = (String) mi.getTitle();
					mc.setTitle(getText(title));
					String location = (String) mi.getLocation();
					mc.setLocation(location);
					repository.addMenu(mc);
				}
			} else {
				String name = mi.getName();
				mc.setName(name);
				String parent = (String) mi.getParent_name();
				// intln(name + ", parent is: " + parent);
				if (parent != null) {
					MenuComponent parentMenu = repository.getMenu(parent);
					if (parentMenu == null) {
						// intln("parentMenu '" + parent + "' doesn't exist!");
						// create a temporary parentMenu
						parentMenu = new MenuComponent();
						parentMenu.setName(parent);
						repository.addMenu(parentMenu);
					}
					mc.setParent(parentMenu);
				}
				String title = (String) mi.getTitle();
				mc.setTitle(getText(title));
				String location = (String) mi.getLocation();
				mc.setLocation(location);
				repository.addMenu(mc);
			}
		}
		// request.setAttribute("repository", repository);
		session.setAttribute("repository", repository);
		session.setAttribute("gridModelMenu", gridModel);
		log.info("check login success !!");
		return SUCCESS;
	}

}