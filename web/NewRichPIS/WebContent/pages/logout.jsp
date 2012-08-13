<%@ page contentType="text/html; charset=utf-8" %>	
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%
	session.setAttribute("check_login", "logoutsuccess");
	session.invalidate();
	response.sendRedirect(request.getContextPath() + "/index.jsp");
%>
