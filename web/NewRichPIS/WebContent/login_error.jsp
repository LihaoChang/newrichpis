<%@ page contentType="text/html; charset=utf-8" %>	
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title><s:text name="welcome_title" /></title>

</head>
<body bgcolor="white">

<s:text name="error" />
<br>
<s:property value="exception.message" />
<br>
<s:property value="exceptionStack" />
</body>
</html>

