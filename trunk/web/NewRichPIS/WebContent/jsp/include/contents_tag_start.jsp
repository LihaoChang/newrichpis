

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
	<bean:message key="contents_item"/>
</div>

	<html:form action="/Contents_add">
	<table width="70%" border="0" align="center">
  <tr>
    <td><bean:message key="time"/> :</td>
    <td>
    <span class="stylered">*</span><input type="text" name="time" size="11" maxlength="11" readonly="Y">	
    <img src="<%=request.getContextPath()%>/images/icons/calendar.jpg" width="16" height="15" title="<bean:message key="clicktime"/>" onClick="fPopCalendar(time,time); return false"> 
    </td>
    <td rowspan="5" align="right" valign="bottom">
    <input type="button" name="button" value="<bean:message key="yes"/>"  onclick="check_up()"/>	
    <input type="reset" name="cancel" value="<bean:message key="cancel"/>" />	
    </td>
  </tr>
  <tr>
    <td><bean:message key="income_or_expense"/> :</td>
    <td><span class="stylered">*</span><select name="item_category" id="items" onchange="refreshBikeList();"></select></td>
  </tr>
  <tr>
    <td><bean:message key="item_name"/> :</td>
    <td><span class="stylered">*</span><select name="item_id" id="itemcontent"></select></td>
  </tr>
  <tr>
    <td><bean:message key="describe"/> :</td>
    <td>&nbsp;&nbsp;<input type="text" name="note" size="20%" maxlength="30" >	</td>
  </tr>
  <tr>
    <td><bean:message key="money"/> :</td>
    <td><span class="stylered">*</span><input type="text" name="money" size="20%" maxlength="10" onBlur="javascript:if(checkNumber(document.ContentsForm.money.value)==1){alert('<bean:message key="money_error"/>!') ;document.ContentsForm.money.focus();};" >	</td>
  </tr>
  <tr>
    <td colspan="3">
    <div class="errorstyle">
    <%
      if(String.valueOf(request.getAttribute("contents_add")).equals("error")){
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