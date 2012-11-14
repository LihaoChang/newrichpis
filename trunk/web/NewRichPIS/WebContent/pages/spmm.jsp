<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ include file="/jsp/include/re.jsp"%>
<s:if test="%{theme == 'showcase' || theme == null}">
	<sj:head debug="true" compressed="false" jquerytheme="showcase"
		customBasepath="themes" loadFromGoogle="%{google}"
		ajaxhistory="%{ajaxhistory}" defaultIndicator="myDefaultIndicator"
		defaultLoadingText="Please wait ..." />
</s:if>
<s:else>

</s:else>

<!-- This file includes necessary functions/topics for validation and all topic examples -->
<script type="text/javascript" src="js/showcase.js"></script>
<!-- Extend the Struts2 jQuery Plugin with an richtext editor -->
<script type="text/javascript" src="js/extendplugin.js"></script>
<script type='text/javascript'>
<!--
	function searchGo() {
		if ((updateForm.exDividendDateStart.value) == "" && (updateForm.exDividendDateEnd.value) != "") {
			window.alert('[<s:text name="findGood_exDividendDate" /> <s:text name="start_date" />]<s:text name="must_have_var" />');
			return;
		}
		if ((updateForm.exDividendDateStart.value) != "" && (updateForm.exDividendDateEnd.value) == "") {
			window.alert('[<s:text name="findGood_exDividendDate" /> <s:text name="end_date" />]<s:text name="must_have_var" />');
			return;
		}			
		document.updateForm.action = "spmm.action";
		document.updateForm.submit(); //送出表單中的資料
	}

	function searchGoPage(strPage) {
		if ((updateForm.exDividendDateStart.value) == "" && (updateForm.exDividendDateEnd.value) != "") {
			window.alert('[<s:text name="findGood_exDividendDate" /> <s:text name="start_date" />]<s:text name="must_have_var" />');
			return;
		}
		if ((updateForm.exDividendDateStart.value) != "" && (updateForm.exDividendDateEnd.value) == "") {
			window.alert('[<s:text name="findGood_exDividendDate" /> <s:text name="end_date" />]<s:text name="must_have_var" />');
			return;
		}			
		document.updateForm.page.value = strPage;
		document.updateForm.action = "spmm.action";
		document.updateForm.submit(); //送出表單中的資料
	}
	
	function orderByGoPage() {
		if ((updateForm.exDividendDateStart.value) == "" && (updateForm.exDividendDateEnd.value) != "") {
			window.alert('[<s:text name="findGood_exDividendDate" /> <s:text name="start_date" />]<s:text name="must_have_var" />');
			return;
		}
		if ((updateForm.exDividendDateStart.value) != "" && (updateForm.exDividendDateEnd.value) == "") {
			window.alert('[<s:text name="findGood_exDividendDate" /> <s:text name="end_date" />]<s:text name="must_have_var" />');
			return;
		}			
		var orderByStr = document.updateForm.orderBy.value ;		
		var orderByTypeStr = document.updateForm.orderByType.value;
		if( orderByStr != null && orderByStr != "" ){			
		  if(orderByTypeStr != null && orderByTypeStr != "" ){
			if(orderByTypeStr =="asc"){
				document.updateForm.orderByType.value = "desc";
			}else if(orderByTypeStr =="desc"){
				document.updateForm.orderByType.value = "asc";
			}else{
				document.updateForm.orderByType.value = "asc";
			}
		  }else{
			  document.updateForm.orderByType.value = "asc";
		  }
		}else{
			document.updateForm.orderBy.value = "exDividendDate";
			document.updateForm.orderByType.value = "asc";
		}
		document.updateForm.action = "spmm.action";
		document.updateForm.submit(); //送出表單中的資料
	}	
	
	function openwin(stockCode) {
		//window.open("http://stockcharts.com/h-sc/ui?symbol="+stockCode);
		window.open("http://www.finviz.com/quote.ashx?t="+stockCode.toLowerCase()+"&ty=c&ta=1&p=d&b=1", 'Joseph');
	}

	 //處理表格style的function	 
	function senfe(o, a, b, c, d) {
		var t = document.getElementById(o).getElementsByTagName("tr");
		for ( var i = 0; i < t.length; i++) {
			t[i].style.backgroundColor = (t[i].sectionRowIndex % 2 == 0) ? a
					: b;
			t[i].onclick = function() {
				if (this.x != "1") {
					this.x = "1";
					this.style.backgroundColor = d;
				} else {
					this.x = "0";
					this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a
							: b;
				}
			}
			t[i].onmouseover = function() {
				if (this.x != "1")
					this.style.backgroundColor = c;
			}
			t[i].onmouseout = function() {
				if (this.x != "1")
					this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a
							: b;
			}
		}
	}

	function enterEvent(btn) {
	    if (event.keyCode == 13){  
	    	searchGo();
	    }  
	} 
//-->
</script>
<script type="text/javascript">
	$.subscribe('onDpChangeMonthAndYear', function(event, data) {
		//alert('Change month to : '+event.originalEvent.month+' and year to '+event.originalEvent.year);
	});
	$.subscribe('onDpClose', function(event, data) {
		//alert('Selected Date : '+event.originalEvent.dateText);
	});
</script>
<body onkeydown="enterEvent('event.keyCode | event.which');">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">

		<table width="100%" border="0">

			<tr>
				<td><%@ include file="/jsp/include/spmm_yellow_tag_start.jsp"%>
				<form name="updateForm" action="personSave.action">
				<table width="100%" border="0">

					<tr>
						<th width="15%" align="left"><s:text name="findGood_stockCode" />:
						</th>
						<td width="18%"><input type="text" id="stockCodeStr"
							size="12" maxlength="10" name="stockCodeStr"
							value='<s:property value="stockCodeStr" />'>
						</td>
						<th width="15%" align="left"><s:text name="findGood_dividend" />:
						</th>
						<td width="18%"><select name="dividendType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='dividendType ==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="dividendStr" size="12" maxlength="10"
							value='<s:property value="dividendStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.dividendStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.dividendStr.focus();};">
						%
						</td>
						<th width="15%" align="left"><s:text name="findGood_sharesTraded" />:
						</th>
						<td width="18%">
                              <select name="sharesTradedType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='sharesTradedType ==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="sharesTradedStr" size="12" maxlength="10"
							value='<s:property value="sharesTradedStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.sharesTradedStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.sharesTradedStr.focus();};">
											
						</td>
					</tr>
					<tr>
						<th width="15%" align="left"><s:text name="findGood_sector" />:
						</th>
						<td width="18%">
							<select name="sectorStr" size="1">
								<s:iterator value="sectorList" status="status">
									<option value="<s:property value="value" />" <s:if test='sectorStr==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>
							</select>
							
						</td>
						<th width="15%" align="left"><s:text name="findGood_options" />:
						</th>
						<td width="18%">
							<select name="optionsStr" size="1">
								<s:iterator value="optionsList" status="status">
									<option value="<s:property value="value" />" <s:if test='optionsStr==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>
							</select>						
						</td>
						<th width="15%" align="left">
						</th>
						<td width="18%">
                        </td>
					</tr>					
					
					<tr>
						<th width="15%" align="left"><s:text name="findGood_exDividendDate" />:
						</th>
						<td width="51%" colspan="3">	
						
          <sj:datepicker id="exDividendDateStart" name="exDividendDateStart" label="Start Date" 
                    changeMonth="true" changeYear="true" onChangeMonthYearTopics="onDpChangeMonthAndYear"
					displayFormat="yy-mm-dd" yearRange="2012:2025" /> 
					~
					 <sj:datepicker id="exDividendDateEnd" name="exDividendDateEnd" label="End Date"
					changeMonth="true" changeYear="true" onChangeMonthYearTopics="onDpChangeMonthAndYear" 
					displayFormat="yy-mm-dd" yearRange="2012:2025" />						
						
									
						</td>
						<td width="15%" align="left">
						</td>
						<td width="18%" align="right"><input type="button" value="<s:text name="action_search" />"
							onclick="searchGo();" />
						</td>
					</tr>					

				</table>
				<input type="hidden" id="id" name="id"
					value='<s:property value="id" />'> 
					<input type="hidden" id="page" name="page" value='0'>
					<input type="hidden" id="orderBy" name="orderBy" value='<s:property value="orderBy" />'>
					<input type="hidden" id="orderByType" name="orderByType" value='<s:property value="orderByType" />'>
				</form>
				<%@ include file="/jsp/include/spmm_yellow_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>



				<table width="100%" border="0" id="GridView1">

					<tr>
						<th width="2%"></th>
						<th width="15%" align="center"><s:text name="findGood_stockCode" /></th>
						<th width="15%" align="center"><s:text name="findGood_nowPrice" /></th>
						<th width="8%" align="center"><s:text name="findGood_sector" /></th>
						<th width="19%" align="center"><s:text name="findGood_sharesTraded" /></th>
						<th width="19%" align="center"><s:text name="findGood_dividend" /></th>
						<th width="19%" align="center">
						<a href='#' onclick="orderByGoPage()">
						<strong><s:text name="findGood_exDividendDate" /></strong>
							<s:if test="orderByType != null && orderByType != ''">								
								<s:if test="orderByType == 'asc'">
									<img src='<%=request.getContextPath()%>/images/Darrow.gif' border="0" >
								</s:if>		
								<s:if test="orderByType == 'desc'">
									<img src='<%=request.getContextPath()%>/images/Upp.gif' border="0" >
								</s:if>									
							</s:if>	
						</a>
						</th>
					</tr>
					<s:iterator value="gridModel" status="status">
						<s:if test="#status.even == true">
							<tr class="changeTr">
						</s:if>
						<s:else>
							<tr>
						</s:else>

						<td align="center"><s:property value="#status.count" /></td>
						<!-- <td align="center"><a href='http://www.finviz.com/quote.ashx?t=<s:property value="stockCode" />&ty=c&ta=1&p=d&b=1' target='_blank'><s:property value="stockCode" /></a></td> -->
						<td align="center">
						<a href='#' onclick="openwin('<s:property value="stockCode" />')">
						<s:property value="stockCode" />						
						<s:if test="options != null && options != ''">
						<font color="red">(<s:property value="options" />)</font>
						</s:if>
						<s:if test="weeklyoptions != null && weeklyoptions != ''">
						<font color="red">(<s:property value="weeklyoptions" />)</font>
						</s:if>							
						</a>
						</td>
						<td align="right"><s:property value="nowPrice" /></td>
						<td align="center"><s:property value="sector" /></td>
						<td align="right"><s:property value="sharesTraded" /></td>
						<td align="right"><s:property value="dividend" /></td>
						<td align="center"><s:property value="exDividendDate" /></td>
						</tr>
					</s:iterator>
				</table>
				<form name="modifyForm"><input type="hidden" id="modify_id"
					name="modify_id" value=""></form>
				<table width="100%" border="0">
					<tr align="right">
						<td align="right"><s:if test="%{page == 1}">
							<input type="button" value="<s:text name="page_first" />" disabled=true />&nbsp;&nbsp;
							<input type="button" value="<s:text name="page_up" />" disabled=true />
						</s:if> <s:else>

							<input type="button" value="<s:text name="page_first" />" onclick="searchGoPage('1');" />						
							&nbsp;&nbsp;
             				<input type="button" value="<s:text name="page_up" />"
								onclick="searchGoPage('<s:property value="%{page-1}"/>');" />
							</a>
						</s:else> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Page <s:property
							value="page" /> of <s:property value="total" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

						<s:if test="%{page < total}">

							<input type="button" value="<s:text name="page_down" />"
								onclick="searchGoPage('<s:property value="%{page+1}"/>');" />						
							&nbsp;&nbsp;
              				<input type="button" value="<s:text name="page_last" />"
								onclick="searchGoPage('<s:property value="total"/>');" />
						</s:if>
						<s:else>
							<input type="button" value="<s:text name="page_down" />" disabled=true />&nbsp;&nbsp;
							<input type="button" value="<s:text name="page_last" />" disabled=true />
						</s:else>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="totalCount" />:<s:property value="records" />
						</td>

					</tr>
				</table>
				<%@ include file="/jsp/include/in_tag_end.jsp"%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<script language="javascript">
	//senfe("表格id","奇數行背景","偶數行背景","鼠數移動背景","點擊後背景");
	senfe("GridView1", "#FFFFFF", "#F7F7F4", "#ecfbd4", "#bce777");
</script>
</body>

