<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="welcome_title" /></title>
<!-- 
<title><tiles:insertAttribute name="title" ignore="true" /></title>
-->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"
	TYPE="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/main2.css" TYPE="text/css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.8.12.custom.css"
	TYPE="text/css">


<script src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.datepicker.js"></script>
<script src="<%=request.getContextPath()%>/js/check_number.js"></script>

</head>
<body>

<table width="100%" border="0" align="center">
	<tr>
		<td><tiles:insertAttribute name="header" /></td>
	</tr>
	<tr>
		<td><tiles:insertAttribute name="menu" /></td>
	</tr>
	<tr>
		<td><tiles:insertAttribute name="body" /></td>
	</tr>
	<tr>
		<td><tiles:insertAttribute name="footer" /></td>
	</tr>
</table>
</body>
</html>
