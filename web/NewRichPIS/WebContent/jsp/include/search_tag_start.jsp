

<!--####################### tag start ############################-->
<div style="padding:4px">
<table cellspacing=0 cellpadding=0 border=0 width=95% align="center"><tr><td width=7>
<img src='<%=request.getContextPath()%>/images/parts/blue_01.gif' width=7 height=7 alt=''></td>
<td background='<%=request.getContextPath()%>/images/parts/blue_02.gif'>
<img src='<%=request.getContextPath()%>/images/parts/blue_02.gif' width=7 height=7 alt=''></td>
<td width=7><img src='<%=request.getContextPath()%>/images/parts/blue_03.gif' width=7 height=7 alt=''></td></tr>
<tr><td background='<%=request.getContextPath()%>/images/parts/blue_04.gif'>
<img src='<%=request.getContextPath()%>/images/parts/blue_04.gif' width=7 height=7 alt=''></td>
<td bgcolor='#D3E6FE'><table border='0' cellspacing='0' cellpadding='0' width='100%'>
<tr><td>
<div class="mainSubTitle">
	<bean:message key="search"/>
</div>

<!--  contents  start   -->
<html:form action="/Search_action">
<table width="95%" border="0" align="center">
  <tr>
    <td><bean:message key="start_date"/> :</td>
    <td>
    <span class="stylered">*</span><input type="text" name="start_date" size="11" maxlength="11" readonly="Y" onClick="CreateMonthView(this,0)" onBlur="DeleteMonthView(this)">	
    <img src="<%=request.getContextPath()%>/images/icons/calendar.jpg" width="16" height="15" title="<bean:message key="clicktime"/>" onClick="CreateMonthView(start_date,0)" onBlur="DeleteMonthView(start_date)">
    </td>
    <td><bean:message key="end_date"/> :</td>
    <td>
    <span class="stylered">*</span><input type="text" name="end_date" size="11" maxlength="11" readonly="Y" onClick="CreateMonthView(this,0)" onBlur="DeleteMonthView(this)">	
    <img src="<%=request.getContextPath()%>/images/icons/calendar.jpg" width="16" height="15" title="<bean:message key="clicktime"/>" onClick="CreateMonthView(end_date,0)" onBlur="DeleteMonthView(end_date)">
    </td>
    <td align="center">
    	&nbsp;
    </td>    
  </tr>
  <tr>
    <td><bean:message key="bank"/>/<bean:message key="cash"/></td>
    <td>  
    	<label><input type="radio" name="categoryitem" value="All"  checked="checked"/>All</label>
      <label><input type="radio" name="categoryitem" value="Bank" />Bank</label>
      <label><input type="radio" name="categoryitem" value="Cash" />Cash</label>
    </td>
    <td><bean:message key="income_or_expense"/></td>
    <td>
    	<label><input type="radio" name="categoryitems" value="All"  checked="checked"/>All</label>
      <label><input type="radio" name="categoryitems" value="Income" />Income</label>
      <label><input type="radio" name="categoryitems" value="Expense" />Expense</label>    	
    </td>    
    <td align="center">
    <input type="button" name="button" value="<bean:message key="yes"/>" onClick="check_up();"/>	
    <input type="reset" name="cancel" value="<bean:message key="cancel"/>" />	    	
    </td>
  </tr>
</table>
<input type="hidden" name="searchaction" value="true"/>
</html:form>
<!--  contents  end   -->		
	
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