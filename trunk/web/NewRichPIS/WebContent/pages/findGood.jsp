<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ include file="/jsp/include/re.jsp"%>

<script type='text/javascript'>
<!--
	function searchGo() {
		document.updateForm.action = "findGood.action";
		document.updateForm.submit(); //送出表單中的資料
	}

	function searchGoPage(strPage) {
		document.updateForm.page.value = strPage;
		document.updateForm.action = "findGood.action";
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
<body onkeydown="enterEvent('event.keyCode | event.which');">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">

		<table width="100%" border="0">

			<tr>
				<td><%@ include file="/jsp/include/findGood_yellow_tag_start.jsp"%>
				<form name="updateForm" action="personSave.action">
				<table width="100%" border="0">

					<tr>
						<th width="15%" align="left"><s:text name="findGood_stockCode" />:
						</th>
						<td width="18%"><input type="text" id="stockCodeStr"
							size="12" maxlength="10" name="stockCodeStr"
							value='<s:property value="stockCodeStr" />'>
						</td>
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
						<th width="15%" align="left"><s:text name="findGood_netIncome" />:
						</th>
						<td width="18%"><select name="netIncomeType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='netIncomeType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>													
						</select> <input type="text" name="netIncomeStr" size="12" maxlength="10"
							value='<s:property value="netIncomeStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.netIncomeStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.netIncomeStr.focus();};">
						<select name="netIncomeType2" size="1">
							<option value="">...</option>
							<s:if test='netIncomeType2=="K"'>
								<option value="K" selected="selected">K</option>
								<option value="M">M</option>
								<option value="B">B</option>
							</s:if>
							<s:elseif test='netIncomeType2=="M"'>
								<option value="K">K</option>
								<option value="M" selected="selected">M</option>
								<option value="B">B</option>
							</s:elseif>
							<s:elseif test='netIncomeType2=="B"'>
								<option value="K">K</option>
								<option value="M">M</option>
								<option value="B" selected="selected">B</option>
							</s:elseif>
							<s:else>
								<option value="K">K</option>
								<option value="M">M</option>
								<option value="B">B</option>
							</s:else>
						</select>
						</td>						
					</tr>
					
					
					<tr>
						<th width="15%" align="left"><s:text name="findGood_netIncomeGrowth" />:
						</th>
						<td width="18%"><select name="netIncomeGrowthType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='netIncomeGrowthType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="netIncomeGrowthStr" size="12"
							maxlength="10" value='<s:property value="netIncomeGrowthStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.netIncomeGrowthStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.netIncomeGrowthStr.focus();};">
						%
						</td>
						<th width="15%" align="left"><s:text name="findGood_netMargin" />:
						</th>
						<td width="18%"><select name="netMarginType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='netMarginType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="netMarginStr" size="12" maxlength="10"
							value='<s:property value="netMarginStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.netMarginStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.netMarginStr.focus();};">
						%
						</td>
						<th width="15%" align="left"><s:text name="findGood_debtEquity" />:
						</th>
						<td width="18%"><select name="debtEquityType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='debtEquityType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="debtEquityStr" size="12" maxlength="10"
							value='<s:property value="debtEquityStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.debtEquityStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.debtEquityStr.focus();};">
						
						</td>						
					</tr>						

					<tr>
						<th width="15%" align="left"><s:text name="findGood_bookValuePerShare" />:
						</th>
						<td width="18%"><select name="bookValuePerShareType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='bookValuePerShareType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="bookValuePerShareStr" size="12"
							maxlength="10"
							value='<s:property value="bookValuePerShareStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.bookValuePerShareStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.bookValuePerShareStr.focus();};">
						
						</td>
						<th width="15%" align="left"><s:text name="findGood_cashPerShare" />:
						</th>
						<td width="18%"><select name="cashPerShareType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='cashPerShareType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="cashPerShareStr" size="12"
							maxlength="10" value='<s:property value="cashPerShareStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.cashPerShareStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.cashPerShareStr.focus();};">
						
						</td>
						<th width="15%" align="left"><s:text name="findGood_roe" />:
						</th>
						<td width="18%"><select name="roeType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='roeType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="roeStr" size="12" maxlength="10"
							value='<s:property value="roeStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.roeStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.roeStr.focus();};">
						%
						</td>						
					</tr>	
					
					<tr>
						<th width="15%" align="left"><s:text name="findGood_roa" />:
						</th>
						<td width="18%"><select name="roaType" size="1">
								<s:iterator value="typeList" status="status">
									<option value="<s:property value="value" />" <s:if test='roaType==value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
						</select> <input type="text" name="roaStr" size="12" maxlength="10"
							value='<s:property value="roaStr" />'
							onBlur="javascript:if(checkNumber(document.updateForm.roaStr.value)==1){alert('<s:text name="num_error2" />!') ;document.updateForm.roaStr.focus();};">
						%
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
						<td width="15%" align="left">
						</td>
						<td width="18%">
						</td>
						<td width="15%" align="left">
						</td>
						<td width="18%">
						</td>
						<td width="15%" align="left">
						</td>
						<td width="18%" align="right"><input type="button" value="<s:text name="action_search" />"
							onclick="searchGo();" />
						</td>
					</tr>					

				</table>
				<input type="hidden" id="id" name="id"
					value='<s:property value="id" />'> <input type="hidden"
					id="page" name="page" value='0'></form>
				<%@ include file="/jsp/include/findGood_yellow_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>



				<table width="100%" border="0" id="GridView1">

					<tr>
						<th width="2%"></th>
						<th width="6%" align="center"><s:text name="findGood_stockCode" /></th>
						<th width="6%" align="center"><s:text name="findGood_sector" /></th>
						<th width="6%" align="center"><s:text name="findGood_sharesTraded" /></th>
						<th width="6%" align="center"><s:text name="findGood_netIncome" /></th>
						<th width="6%" align="center"><s:text name="findGood_netIncomeGrowth" /></th>
						<th width="6%" align="center"><s:text name="findGood_netMargin" /></th>
						<th width="6%" align="center"><s:text name="findGood_debtEquity" /></th>
						<th width="6%" align="center"><s:text name="findGood_bookValuePerShare" /></th>
						<th width="6%" align="center"><s:text name="findGood_cashPerShare" /></th>
						<th width="6%" align="center"><s:text name="findGood_roe" /></th>
						<th width="6%" align="center"><s:text name="findGood_roa" /></th>
						<th width="6%" align="center"><s:text name="findGood_dividend" /></th>
						<th width="28%" align="center"><s:text name="findGood_strategy" /></th>
					</tr>
					<s:iterator value="gridModel" status="status">
						<s:if test="#status.even == true">
							<tr class="changeTr">
						</s:if>
						<s:else>
							<tr>
						</s:else>

						<td align="left"><s:property value="#status.count" /></td>
						<!-- <td align="center"><a href='http://www.finviz.com/quote.ashx?t=<s:property value="stockCode" />&ty=c&ta=1&p=d&b=1' target='_blank'><s:property value="stockCode" /></a></td> -->
						<td align="center"><a href='#' onclick="openwin('<s:property value="stockCode" />')"><s:property value="stockCode" /></a></td>
						<td align="center"><s:property value="sector" /></td><!--  -->
						<td align="right"><s:property value="sharesTraded" /></td>
						<td align="right"><s:property value="netIncome" /></td>
						<td align="right"><s:property value="netIncomeGrowth" /></td>
						<td align="right"><s:property value="netMargin" /></td>
						<td align="right"><s:property value="debtEquity" /></td>
						<td align="right"><s:property value="bookValuePerShare" /></td>
						<td align="right"><s:property value="cashPerShare" /></td>
						<td align="right"><s:property value="roe" /></td>
						<td align="right"><s:property value="roa" /></td>
						<td align="right"><s:property value="dividend" /></td>
						<td align="left"><s:property value="strategy" /></td>
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

