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
	if ((updateForm.loginDateStart.value) == "" || (updateForm.loginDateEnd.value) == "") {
		window.alert('[<s:text name="start_date" /> <s:text name="end_date" />]<s:text name="must_have_var" />');
		return;
	}
	document.updateForm.action = "loginCount.action";
	document.updateForm.submit(); //送出表單中的資料
}

function searchGoPage(strPage) {
	if ((updateForm.loginDateStart.value) == "" || (updateForm.loginDateEnd.value) == "") {
		window.alert('[<s:text name="start_date" /> <s:text name="end_date" />]<s:text name="must_have_var" />');
		return;
	}
	document.updateForm.page.value = strPage;
	document.updateForm.action = "loginCount.action";
	document.updateForm.submit(); //送出表單中的資料
}		
//-->
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">
		<table width="100%" border="0">
			<tr>
				<td><%@ include file="/jsp/include/query_tag_start_p1.jsp"%>
				<div class="mainSubTitle"><s:text name="login_manager" />
				<%@ include file="/jsp/include/query_tag_start_p2.jsp"%>
				<form name="updateForm" action="loginCount.action" METHOD="post">
				<table width="100%" border="0">

					<tr>
						<th width="15%" align="left"><s:text name="login_start_end_date" />:
						</th>
						<td width="51%" colspan="3">	
						
	          			<sj:datepicker id="loginDateStart" name="loginDateStart" label="Start Date" 
	                    changeMonth="true" changeYear="true" onChangeMonthYearTopics="onDpChangeMonthAndYear"
						displayFormat="yy-mm-dd" yearRange="2012:2099" />
						~
						 <sj:datepicker id="loginDateEnd" name="loginDateEnd" label="End Date"
						changeMonth="true" changeYear="true" onChangeMonthYearTopics="onDpChangeMonthAndYear" 
						displayFormat="yy-mm-dd" yearRange="2012:2099" />
						
						</td>
						<td width="15%" align="left">
						</td>
						<td width="18%" align="right"><input type="button" value="<s:text name="action_search" />"
							onclick="searchGo();" />
						</td>
					</tr>
				</table>
				</form>
				<%@ include file="/jsp/include/query_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>
				<table width="100%" border="0">
					<tr>
						<th></th>
						<th><s:text name="RtMember_memberId" /></th>
						<th><s:text name="RtMember_realname" /></th>
						<th><s:text name="RtMember_updateDate" /></th>
					</tr>
					<s:iterator value="gridModel" status="status">
						<s:if test="#status.even == true">
							<tr class="changeTr">
						</s:if>
						<s:else>
							<tr>
						</s:else>
						<td><s:property value="#status.count" /></td>
						<td><s:property value="memberId" /></td>
						<td><s:property value="realName" /></td>
						<td><s:property value="updateDate" /></td>
					</tr>
					</s:iterator>
				</table>
				<form name="modifyForm">
					<input type="hidden" id="modify_id" name="modify_id" value="">
				</form>
				<table width="100%" border="0">
					<tr align="right">
						<td align="right"><s:if test="%{page == 1}">
							<input type="button" value="<s:text name="page_first" />" disabled="true" />&nbsp;&nbsp;
							<input type="button" value="<s:text name="page_up" />" disabled="true" />
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
							<input type="button" value="<s:text name="page_down" />" disabled="true" />&nbsp;&nbsp;
							<input type="button" value="<s:text name="page_last" />" disabled="true" />
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



