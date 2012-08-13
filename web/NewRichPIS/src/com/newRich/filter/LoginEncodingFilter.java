package com.newRich.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class LoginEncodingFilter extends HttpServlet implements Filter {
	private static final String REDIRECTPATH = "/index.jsp";
	private FilterConfig filterConfig;

	// Handle the passed-in FilterConfig
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	// Process the request/response pair
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {

			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
			boolean isValid = true;
			String uriStr = httpRequest.getRequestURI();
			// System.out.println("---------------------------------httpRequest.getContextPath():" + httpRequest.getContextPath());
			// System.out.println("---------------------------------uriStr:" + uriStr);
			if (uriStr.indexOf(".jsp") == -1 && uriStr.indexOf(".action") == -1) {
				isValid = true;
			} else if (uriStr.indexOf("index.jsp") == -1 && uriStr.indexOf("Login.action") == -1
					&& httpRequest.getSession().getAttribute("check_login") == null) {
				isValid = false;
			}

			if (isValid) {
				request.setCharacterEncoding("UTF-8");
				filterChain.doFilter(request, response);
				// System.out.println("------------3---------------------uriStr:" + uriStr);
			} else {
				request.setCharacterEncoding("UTF-8");
				PrintWriter out = httpResponse.getWriter();
				if (uriStr.indexOf("index.jsp") == -1) {
					wrapper.sendRedirect(httpRequest.getContextPath() + REDIRECTPATH);
					// System.out.println("------1---------------------------uriStr:" + uriStr);
					// httpResponse.sendRedirect(REDIRECTPATH);
				} else {
					wrapper.sendRedirect(httpRequest.getContextPath() + REDIRECTPATH);
					// System.out.println("-------2--------------------------uriStr:" + uriStr);
					// httpResponse.sendRedirect(REDIRECTPATH);
				}

			}

		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
	}

	// Clean up resources
	public void destroy() {
	}
}