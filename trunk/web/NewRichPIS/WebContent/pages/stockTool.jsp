<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ include file="/jsp/include/re.jsp"%>

<script type='text/javascript'>
<!--
	function check_up() {

		if ((updateForm.name.value) == "") {
			window
					.alert('[<s:text name="stockTool_name" />]<s:text name="must_have_var" />');
			//document.updateForm.elements(1).focus();
			return;
		}

		if ((updateForm.url.value) == "") {
			window
					.alert('[<s:text name="stockTool_url" />] <s:text name="must_have_var" />');
			//document.updateForm.elements(3).focus();
			return;
		}

		if ((updateForm.remark.value) == "") {
			window
					.alert('[<s:text name="note" />] <s:text name="must_have_var" />');
			//document.updateForm.elements(3).focus();
			return;
		}
		document.updateForm.submit(); //送出表單中的資料
	}

	function searchGo() {
		document.updateForm.action = "stockTool.action";
		document.updateForm.submit(); //送出表單中的資料
	}

	function toModify(str, checkstr,num) {
		document.modifyForm.modify_id.value = str;
		if (checkstr == "M") {
			document.modifyForm.action = "stockToolModify.action";
			document.modifyForm.submit(); //送出表單中的資料
		}
		if (checkstr == "D") {
			document.modifyForm.action = "stockToolDelete.action";
			if (confirm("Delete Item ["+num+"] ?")) {
				document.modifyForm.submit(); //送出表單中的資料
			} else {

			}
		}
	}
	
	function searchGoPage(strPage) {
		document.updateForm.page.value = strPage;
		document.updateForm.action = "stockTool.action";
		document.updateForm.submit(); //送出表單中的資料
	}		
//-->
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">

		<table width="100%" border="0">

			<tr>
				<td><%@ include file="/jsp/include/stockTool_yellow_tag_start.jsp"%>
				<form name="updateForm" action="stockToolSave.action">
				<table width="100%" border="0">

					<tr>
						<th width="20%"><s:text name="stockTool_name" />:</th>
						<td width="40%"><input type="text" id="name" name="name" size="50" maxlength="50"
							value='<s:property value="name"/>'></td>
						<td width="40%"></td>
					</tr>
					<tr>
						<th width="20%"><s:text name="stockTool_url" />:</th>
						<td width="40%"><input type="text" id="url" name="url"  size="100" maxlength="140"
							value='<s:property value="url"/>'></td>
						<td width="40%"></td>
					</tr>
					<tr>
						<th width="20%"><s:text name="note" />:</th>
						<td width="40%">
                        <textarea name="remark" rows="4" cols="50"><s:property value="remark" /></textarea></td>
						<td width="40%">
						<s:if test='#session["login_role"] != null && #session["login_role"] =="A" '>
						<input type="button" name="button" value="<s:text name="save" />" onclick="check_up()" />
						</s:if> 
							<input type="button"
							value="<s:text name="clean" />" onclick="self.location.href='stockTool.action'" /> <input
							type="button" value="<s:text name="search" />" onclick="searchGo();" /></td>
					</tr>
				</table>
				<input type="hidden" id="id" name="id" value='<s:property value="id" />'>
				<input type="hidden" id="page" name="page" value='0'>
				</form>
				<%@ include file="/jsp/include/stockTool_yellow_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>



				<table width="100%" border="0">

					<tr>
						<th width="5%"></th>
						<th width="20%"><s:text name="stockTool_name" /></th>
						<th width="55%"><s:text name="note" /></th>
						<th width="20%"></th>
					</tr>
					<s:iterator value="gridModel" status="status">
						<s:if test="#status.even == true">
							<tr class="changeTr">
						</s:if>
						<s:else>
							<tr>
						</s:else>

						<td><s:property value="#status.count" /></td>
						<td><a href='#' onclick="window.open('<s:property value="url" />')"><s:property value="name" /></td>
						<td><s:property value="remark" /></td>
						<td><input type="button" name="button" value="<s:text name="action_edit" />"
							onclick="toModify('<s:property value="id" />','M','')" /> <input
							type="button" name="button" value="<s:text name="action_delete" />"
							onclick="toModify('<s:property value="id" />','D','<s:property value="#status.count" />')" /></td>
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



