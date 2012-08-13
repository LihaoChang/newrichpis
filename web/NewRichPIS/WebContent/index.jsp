<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="org.apache.log4j.*"%>
<%
	//get user ip
	String clientip = "";
	if (request.getHeader("X-FORWARDED-FOR") == null) {
		clientip = request.getRemoteAddr();
	} else {
		clientip = request.getHeader("X-FORWARDED-FOR");
	}

	Logger logger = Logger.getLogger("index.jsp");
	logger.info("Login ip =" + clientip);
%>
<html>
<head>
<title><s:text name="welcome_title" /></title>
</head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"
	TYPE="text/css">
<body bgcolor="white">

<s:form action="Login" method="POST">
	<%@ include file="/jsp/include/first_all_tag_start.jsp"%>

	<table width="250" border="0" align="center">
		<tr>
			<td>
			<table width="250" border="0" align="center" cellpadding="2"
				cellspacing="2">
				<tr>
					<td></td>
				</tr>
				<tr>
					<td><s:textfield name="name" label="%{getText('login_name')}" /></td>
				</tr>
				<tr>
					<td><s:password name="password"
						label="%{getText('login_password')}" /></td>
				</tr>
				<tr align="right">
					<td colspan="2"><input type="submit" name="save"
						value="<s:text name="login"/>"> &nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td><s:actionerror /> <br><s:fielderror /> <br><s:actionmessage /></td>
				</tr>

			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/include/first_all_tag_end.jsp"%>
</s:form>

</body>
</html>
