package com.newRich.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import com.newRich.service.SchedulerService;
import com.newRich.util.SpringQuartzConstant;

/**
 * Servlet implementation class JobProcessServlet
 */
public class JobProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationContext ctx;
	private SchedulerService schedulerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JobProcessServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		schedulerService = (SchedulerService) ctx.getBean("schedulerService");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jobtype = request.getParameter("jobtype");
		String action = request.getParameter("action");

		if (jobtype.equals("0") && action.equals("add")) {//simple
			this.addSimpleTrigger(request, response);
		} else if (jobtype.equals("1") && action.equals("add")) {//cron
			this.addCronTriggerByExpression(request, response);
		} else if (jobtype.equals("2") && action.equals("add")) {
			this.addCronTriggerBy(request, response);
		} else if (jobtype.equals("100") && action.equals("query")) {
			this.getQrtzTriggers(request, response);
		} else if (jobtype.equals("200") && action.equals("pause")) {
			this.pauseTrigger(request, response);
		} else if (jobtype.equals("200") && action.equals("resume")) {
			this.resumeTrigger(request, response);
		} else if (jobtype.equals("200") && action.equals("remove")) {
			this.removeTrigdger(request, response);
		}
	}

	/**
	 * 新增simple Trigger
	 * 
	 * @param request
	 * @param response
	 */
	private void addSimpleTrigger(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 取得參數
		Map<String, String> filterMap = WebUtils.getParametersStartingWith(request, "p_");
		if (StringUtils.isEmpty(filterMap.get(SpringQuartzConstant.STARTTIME))) {
			response.getWriter().println(1);
		}

		// 新增任務
		schedulerService.schedule(filterMap);

		// response.setContentType("text/xml;charset=utf-8");
		response.getWriter().println(0);

	}

	/**
	 * 根據Cron表示式新增Cron Trigger，
	 * 
	 * @param request
	 * @param response
	 */
	private void addCronTriggerByExpression(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//取得參數
		String triggerName = request.getParameter("triggerName");
		String cronExpression = request.getParameter("cronExpression");
		String triggerGroup = request.getParameter("triggerGroup");
		if (StringUtils.isEmpty(triggerName) || StringUtils.isEmpty(cronExpression)) {
			response.getWriter().println(1);
		}

		//新增任務
		//schedulerService.schedule(triggerName, cronExpression);
		schedulerService.schedule(triggerName, cronExpression, triggerGroup);

		// response.setContentType("text/xml;charset=utf-8");
		response.getWriter().println(0);

	}

	/**
	 * 根據新增Cron Trigger，
	 * 
	 * @param request
	 * @param response
	 */
	private void addCronTriggerBy(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//取得參數
		String triggerName = request.getParameter("triggerName");
		String val = request.getParameter("val");
		String selType = request.getParameter("selType");
		if (StringUtils.isEmpty(triggerName) || StringUtils.isEmpty(val) || NumberUtils.toLong(val) < 0 || NumberUtils.toLong(val) > 59) {
			response.getWriter().println(1);
		}

		String expression = null;
		if (StringUtils.equals(selType, "second")) {
			// 每多秒執行一次
			expression = "0/" + val + " * * ? * * *";
		} else if (StringUtils.equals(selType, "minute")) {
			// 每多少分執行一次
			expression = "0 0/" + val + " * ? * * *";
		}

		//新增任務
		schedulerService.schedule(triggerName, expression);

		// response.setContentType("text/xml;charset=utf-8");
		response.getWriter().println(0);

	}

	/**
	 * 取得所有Trigger
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getQrtzTriggers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String, Object>> results = this.schedulerService.getQrtzTriggers();
		request.setAttribute("list", results);
		request.getRequestDispatcher("/list.jsp").forward(request, response);
	}

	/**
	 * 根據名稱和組別暫停Trigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void pauseTrigger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");

		schedulerService.pauseTrigger(triggerName, group);
		response.getWriter().println(0);
	}

	/**
	 * 根據名稱和組別再啟動Trigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void resumeTrigger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");

		schedulerService.resumeTrigger(triggerName, group);
		response.getWriter().println(0);
	}

	/**
	 * 根據名稱和組別移除Trigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void removeTrigdger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");

		boolean rs = schedulerService.removeTrigdger(triggerName, group);
		if (rs) {
			response.getWriter().println(0);
		} else {
			response.getWriter().println(1);
		}
	}

}
