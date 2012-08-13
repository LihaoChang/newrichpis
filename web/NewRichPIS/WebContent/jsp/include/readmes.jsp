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
					.alert('[<s:text name="describe" />]<s:text name="must_have_var" />');
			//document.updateForm.elements(1).focus();
			return;
		}

		if ((updateForm.note.value) == "") {
			window
					.alert('[<s:text name="note" />]1 <s:text name="must_have_var" />');
			//document.updateForm.elements(3).focus();
			return;
		}
		if ((updateForm.readme.value) == "") {
			window
					.alert('[<s:text name="note" />]2 <s:text name="must_have_var" />');
			//document.updateForm.elements(3).focus();
			return;
		}

		document.updateForm.submit(); //送出表單中的資料
	}

	function searchGo() {
		document.updateForm.action = "readmes.action";
		document.updateForm.submit(); //送出表單中的資料
	}

	function toModify(str, checkstr) {
		document.modifyForm.modify_id.value = str;
		if (checkstr == "M") {
			document.modifyForm.action = "readmesModify.action";
			document.modifyForm.submit(); //送出表單中的資料
		}
		if (checkstr == "D") {
			document.modifyForm.action = "readmesDelete.action";
			if (confirm("Delete?")) {
				document.modifyForm.submit(); //送出表單中的資料
			} else {

			}
		}

	}
//-->
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="25%">
		</td>
		<td VALIGN="TOP" width="75%">

		<table width="100%" border="0">

			<tr>
				<td><%@ include file="/jsp/include/cash_yellow_tag_start.jsp"%>
				<form name="updateForm" action="readmesSave.action">
				<table width="100%" border="0">

					<tr>
						<th width="20%"><s:text name="describe" />:</th>
						<td width="40%"><input type="text" id="name" name="name"
							value='<s:property value="name" />'></td>
						<td width="40%"></td>
					</tr>

					<tr>
						<th width="20%"><s:text name="note" />1:</th>
						<td width="40%"><input type="text" id="note" name="note"
							value='<s:property value="note" />'></td>
						<td width="40%"></td>
					</tr>

					<tr>
						<th width="20%"><s:text name="note" />2:</th>
						<td width="40%"><input type="text" name="readme"
							value='<s:property value="readme" />' size="50%" maxlength="30"></td>
						<td width="40%"><input type="button" name="button"
							value="Save" onclick="check_up()" /> <input type="button"
							value="New" onclick="self.location.href='readmes.action'" /> <input
							type="button" value="Search" onclick="searchGo();" /></td>
					</tr>
				</table>
				<input type="hidden" id="id" name="id"
					value='<s:property value="id" />'></form>
				<%@ include file="/jsp/include/cash_yellow_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>



				<table width="100%" border="0">

					<tr>
						<th width="5%"></th>
						<th width="10%"><s:text name="describe" /></th>
						<th width="15%"><s:text name="note" />1</th>
						<th width="50%"><s:text name="note" />2</th>
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
						<td><s:property value="name" /></td>
						<td><s:property value="note" /></td>
						<td><s:property value="readme" /></td>
						<td><input type="button" name="button" value="Modify"
							onclick="toModify('<s:property value="id" />','M')" /> <input
							type="button" name="button" value="Delete"
							onclick="toModify('<s:property value="id" />','D')" /></td>
						</tr>
					</s:iterator>
				</table>
				<form name="modifyForm"><input type="hidden" id="modify_id"
					name="modify_id" value=""></form>
				<table width="100%" border="0">
					<tr>
						<td align="left"><s:if test="%{page == 1}">
							<input type="button" value="First" disabled=true />&nbsp;&nbsp;
							<input type="button" value="Up" disabled=true />
						</s:if> <s:else>

							<input type="button" value="First"
								onclick="self.location.href='readmes.action?page=1'" />						
							&nbsp;&nbsp;
<input type="button" value="Up"
								onclick="self.location.href='readmes.action?page=<s:property value="%{page-1}"/>'" />
							</a>
						</s:else> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Page <s:property
							value="page" /> of <s:property value="total" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

						<s:if test="%{page < total}">

							<input type="button" value="Down"
								onclick="self.location.href='readmes.action?page=<s:property value="%{page+1}"/>'" />						
							&nbsp;&nbsp;
<input type="button" value="Last"
								onclick="self.location.href='readmes.action?page=<s:property value="total"/>'" /></td>
						</s:if>
						<s:else>
							<input type="button" value="Down" disabled=true />&nbsp;&nbsp;
							<input type="button" value="Last" disabled=true />
						</s:else>
						</td>
						<td align="right">All Row:<s:property value="records" /></td>

					</tr>
				</table>
				<%@ include file="/jsp/include/in_tag_end.jsp"%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>



