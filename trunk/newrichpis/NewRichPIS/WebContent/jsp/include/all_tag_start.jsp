<%
  System.out.println("--all--check_login-----"+session.getAttribute("check_login"));
  System.out.println("--login_user_id-----"+session.getAttribute("login_user_id"));
  System.out.println("--login_user_name-----"+session.getAttribute("login_user_name"));

  String login_user_name =String.valueOf(session.getAttribute("login_user_name")); 
  java.util.Date date_Ut = new java.util.Date();
  java.sql.Timestamp date_ts = new java.sql.Timestamp( date_Ut.getTime());  
  System.out.println(date_Ut);
  System.out.println(date_ts);
  System.out.println(date_ts.getTime());

  String check_login = String.valueOf(session.getAttribute("check_login"));
  if(!check_login.equals("loginsuccess")){
    response.sendRedirect(request.getContextPath()+"/index.jsp");
  }
%>    
<table width="900" height="98%" align="center">
<tr width="100%" align="center">
<td valign="middle" align="center" width="100%">

<table cellspacing="0" cellpadding="0" border="0">
<tr><td width="7"><img src='<%=request.getContextPath()%>/images/parts/white_01.gif' width="7" height="7" alt=''></td>
<td background='<%=request.getContextPath()%>/images/parts/white_02.gif'>
<img src='<%=request.getContextPath()%>/images/parts/white_02.gif' width="7" height="7" alt=''></td>
<td width="7"><img src='<%=request.getContextPath()%>/images/parts/white_03.gif' width=7 height=7 alt=''></td>
</tr>
<tr><td background='<%=request.getContextPath()%>/images/parts/white_04.gif'>
<img src='<%=request.getContextPath()%>/images/parts/white_04.gif' width="7" height="7" alt=''></td><td bgcolor='white'>