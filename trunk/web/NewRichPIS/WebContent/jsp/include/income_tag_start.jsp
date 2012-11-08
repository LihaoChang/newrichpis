

<!--####################### tag start ############################-->
<div style="padding:4px">
<table cellspacing=0 cellpadding=0 border=0 width="95%" align="center"><tr><td width=7>
<img src='<%=request.getContextPath()%>/images/parts/blue_01.gif' width=7 height=7 alt=''></td>
<td background='<%=request.getContextPath()%>/images/parts/blue_02.gif'>
<img src='<%=request.getContextPath()%>/images/parts/blue_02.gif' width=7 height=7 alt=''></td>
<td width=7><img src='<%=request.getContextPath()%>/images/parts/blue_03.gif' width=7 height=7 alt=''></td></tr>
<tr><td background='<%=request.getContextPath()%>/images/parts/blue_04.gif'>
<img src='<%=request.getContextPath()%>/images/parts/blue_04.gif' width=7 height=7 alt=''></td>
<td bgcolor='#D3E6FE'><table border='0' cellspacing='0' cellpadding='0' width='100%'>
<tr><td>
<div class="mainSubTitle">
	<bean:message key="income_item"/>
</div>

	<html:form action="/Income_add">	
  <table width="90%" border="0">
    <tr>
    <td><span class="stylered">*</span>
    <bean:message key="item_name"/> :
		<input type="text" name="name" size="20%" maxlength="20" >	
    </td>
    <td>
    <bean:message key="describe"/> :
		<input type="text" name="note" size="20%" maxlength="30" >	
    </td>
    <td>
    <input type="button" name="button" value="<bean:message key="yes"/>"  onclick="check_up()"/>
    <input type="reset" name="cancel" value="<bean:message key="cancel"/>" />	
    
    </td>
    </tr>
    <tr>
    <td colspan="3">
    <div class="errorstyle">
    <%
      if(String.valueOf(request.getAttribute("income_add")).equals("repeat")){
    %>
    <bean:message key="add_error"/><bean:message key="item_name_error2"/>
    <%}%>	
    <%
      if(String.valueOf(request.getAttribute("income_add")).equals("error")){
    %>
    <bean:message key="add_error"/>
    <%}%>


    </div>
    </td>
  </tr>
  </table>
</html:form>
		
	
</td></tr></table></td>
<td background='<%=request.getContextPath()%>/images/parts/blue_06.gif'>
<img src='<%=request.getContextPath()%>/images/parts/blue_06.gif' width=7 height=7 alt=''></td></tr>
<tr><td width=7><img src='<%=request.getContextPath()%>/images/parts/blue_white_07.gif' width=7 height=7 alt=''></td>
<td background='<%=request.getContextPath()%>/images/parts/blue_08.gif'>
<img src='<%=request.getContextPath()%>/images/parts/blue_08.gif' width=7 height=7 alt=''></td>
<td width=7><img src='<%=request.getContextPath()%>/images/parts/blue_white_09.gif' width=7 height=7 alt=''></td></tr>
<tr><td background='<%=request.getContextPath()%>/images/parts/white_04.gif'>
<img src='<%=request.getContextPath()%>/images/parts/white_04.gif' width=7 height=7 alt=''></td>
<td bgcolor='white' style='padding-top:6px;'>	
<!--####################### tag end ############################-->	