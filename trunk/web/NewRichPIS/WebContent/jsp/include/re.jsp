<%
System.out.println("--/jsp/include/re.jsp--check_login--------:"+session.getAttribute("check_login"));
if(null != session.getAttribute("check_login")){
%>
<%
}else{
	response.sendRedirect("index.jsp"); 	
}
%>