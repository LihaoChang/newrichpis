package com.newRich.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newRich.backRun.vo.RtMember;
import com.newRich.dao.MemberDao;

public class LoginEncodingFilter extends HttpServlet implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static transient Log log = LogFactory.getLog(LoginEncodingFilter.class);
	private static final String REDIRECTPATH = "/index.jsp";
	private FilterConfig filterConfig;
	private static final String CHARACTER_ENCODING = "UTF-8";
	private String this_check_login;
	private String this_login_user_id;
	private String this_login_user_name;

	// Handle the passed-in FilterConfig
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	// Process the request/response pair
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			request.setCharacterEncoding("UTF-8");

			// http://localhost:8080/NewRichPIS/filter_check.jsp?id=11&ticket=1352711478182
			String requestedPage = null;
			if (httpRequest.getRequestURL() != null) {
				int hit = httpRequest.getRequestURL().lastIndexOf("/");
				if (hit > 0) {
					requestedPage = httpRequest.getRequestURL().substring(hit + 1);
				}
			}
			// log.info("----------requestedPage:" + requestedPage);

			if (requestedPage != null && (requestedPage.endsWith(".jsp") || requestedPage.endsWith(".action"))
					&& !requestedPage.endsWith("filter_error.jsp") && !requestedPage.endsWith("index.jsp")
					&& !requestedPage.endsWith("login_error.jsp") && !requestedPage.endsWith("Login.action")
					&& !requestedPage.endsWith("ssoGenerateMenu.action") && !requestedPage.endsWith("logout.action")) {
				String id = "";
				if (null != request.getParameter("id")) {
					id = request.getParameter("id");
				}

				String ticket = "";
				if (null != request.getParameter("ticket")) {
					ticket = request.getParameter("ticket");
				}
				// log.info("--------11111111111--requestedPage------------- " + requestedPage);
				if (hasSessionUser(id, httpRequest, httpResponse)) {
					// do nothing, get access to the system
					// log.info("Session user found, get access to the system.");
					if (null == httpRequest.getSession().getAttribute("repository")) {
						httpRequest.getSession().setAttribute("check_login", "logoutsuccess");
						httpRequest.getSession().invalidate();
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/filter_error.jsp");
						// httpResponse.sendRedirect(httpRequest.getContextPath() + "/ssoGenerateMenu.action");
						return;
					} else {
						MenuRepository repository = (MenuRepository) httpRequest.getSession().getAttribute("repository");
						if (null != repository.getMenu("Home")) {
							if (!StringUtils.isBlank(repository.getMenu("Home").getName())) {
							} else {
								httpRequest.getSession().setAttribute("check_login", "logoutsuccess");
								httpRequest.getSession().invalidate();
								httpResponse.sendRedirect(httpRequest.getContextPath() + "/filter_error.jsp");
							}
						}
						httpRequest.getSession().setAttribute("repository", repository);
					}

					if (requestedPage.indexOf("filter_check.jsp") >= 0) {
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/home.action");
						return;
					} else {
						filterChain.doFilter(request, response);
					}
				} else {
					// TODO: check ticket

					log.info("----------id:" + id);
					log.info("----------ticket:" + ticket);
					if (!StringUtils.isBlank(id) && !StringUtils.isBlank(ticket)) {

						try {
							long ticketLong = Long.parseLong(ticket);
							try {
								Date checkDate = new Date(ticketLong);
							} catch (Exception ex) {
								log.error(String.format("Login fail: %s", ex.getMessage()), ex);
								httpResponse.sendRedirect(httpRequest.getContextPath() + "/filter_error.jsp");
								return;
							}
							Date nowDate = new Date();
							long theday = (nowDate.getTime() - ticketLong) / (1000 * 60 * 5);
							// 超過5分鐘，就不能連線
							if (theday >= 1) {
								httpResponse.sendRedirect(httpRequest.getContextPath() + "/filter_error.jsp");
								return;
							} else {
								login(id, httpRequest, httpResponse);
							}
						} catch (Exception ex) {
							log.error(String.format("Login fail: %s", ex.getMessage()), ex);
							httpResponse.sendRedirect(httpRequest.getContextPath() + "/filter_error.jsp");
							return;
						}
					} else {
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/filter_error.jsp");
						return;
					}
				}
			} else {
				filterChain.doFilter(request, response);
			}

		} catch (Exception e) {
			filterConfig.getServletContext().log(e.getMessage());
		}
	}

	private boolean hasSessionUser(String id, HttpServletRequest req, HttpServletResponse resp) {
		if (null == req.getSession().getAttribute("login_user_id")) {
			log.info("hasSessionUser login_user_id: [" + req.getSession().getAttribute("login_user_id")
					+ "] , false");
			return false;
		} else {
			String userId = req.getSession().getAttribute("login_user_id").toString();
			if (!StringUtils.isBlank(id)) {
				if (!userId.equals(id)) {
					log.info("--hasSessionUser-req.getSession().getAttribute(login_user_id): [" + req.getSession().getAttribute("login_user_id")
							+ "] , false");
					return false;
				}
			} else {

			}
		}
		log.info("--hasSessionUser-req.getSession().getAttribute(login_user_id): [" + req.getSession().getAttribute("login_user_id") + "] , true");
		return true;
	}

	public void login(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login_user_id = "loginerror";
		String role = "";
		MemberDao MemberDao = new MemberDao();
		RtMember rtMember = MemberDao.findById(id);
		if (null == rtMember) {
			response.sendRedirect(request.getContextPath() + "/filter_error.jsp");
		} else {
			login_user_id = rtMember.getMemberId();
			role = "";
			if (!login_user_id.equals("loginerror")) {
				request.getSession(true).setAttribute("check_login", "loginsuccess");
				request.getSession(true).setAttribute("login_user_id", login_user_id);
				request.getSession(true).setAttribute("login_user_name", rtMember.getEmail());
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
			}

			// log login action
			log.info("Login username: " + rtMember.getEmail() + " ,IP: " + request.getRemoteAddr() + " ,Session Id: " + request.getSession().getId()
					+ " ,Login time: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			response.sendRedirect(request.getContextPath() + "/ssoGenerateMenu.action");

		}
	}

	// Clean up resources
	public void destroy() {
	}
}