<%
if(null != session.getAttribute("check_login")){
%>
<%
}else{
	response.sendRedirect("index.jsp"); 	
}
%>