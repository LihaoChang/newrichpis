<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ include file="/jsp/include/re.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type='text/javascript'>
<!--
	function check_up() {
		//memberId, nickname, realname, email, scale, updateDate
		if ((updateForm.memberId.value) == "") {
			window.alert('[<s:text name="RtMember_memberId" />]<s:text name="must_have_var" />');
			return;
		}
		if ((updateForm.realname.value) == "") {
			window.alert('[<s:text name="RtMember_realname" />] <s:text name="must_have_var" />');
			return;
		}
		if ((updateForm.email.value) == "") {
			window.alert('[<s:text name="RtMember_email" />] <s:text name="must_have_var" />');
			return;
		}
		if ((updateForm.scale.value) == "") {
			window.alert('[<s:text name="RtMember_scale" />] <s:text name="must_have_var" />');
			return;
		}
		document.updateForm.submit(); //送出表單中的資料
	}

	function searchGo() {
		document.updateForm.action = "queryMember.action";
		document.updateForm.submit(); //送出表單中的資料
	}

	function toModify(str, checkstr, num) {
		document.modifyForm.modify_id.value = str;
		if (checkstr == "M") {
			document.modifyForm.action = "memberModify.action";
			document.modifyForm.submit(); //送出表單中的資料
		}
		if (checkstr == "D") {
			document.modifyForm.action = "memberDelete.action";
			if (confirm("Delete Item ["+num+"] ?")) {
				document.modifyForm.submit(); //送出表單中的資料
			} else {

			}
		}
	}
	
	function searchGoPage(strPage) {
		document.updateForm.page.value = strPage;
		document.updateForm.action = "queryMember.action";
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
				<div class="mainSubTitle"><s:text name="Member" />
				<%@ include file="/jsp/include/query_tag_start_p2.jsp"%>
				<form name="updateForm" action="memberSave.action" METHOD="post">
				<table width="100%" border="0">

					<tr>
						<th><s:text name="RtMember_memberId" />:</th>
						<td><input type="text" id="memberId" name="memberId"
							value='<s:property value="memberId" />' <s:if test='modifyType == "M"'>disabled="disabled"</s:if>></td>
						
						<th><s:text name="RtMember_nickname" />:</th>
						<td><input type="text" id="nickname" name="nickname"
							value='<s:property value="nickname" />'></td>
						<th><s:text name="RtMember_realname" />:</th>
						<td><input type="text" id="realname" name="realname"
							value='<s:property value="realname" />'></td>
						<td></td>
					</tr>

					<tr>
						<th><s:text name="RtMember_email" />:</th>
						<td><input type="text" name="email"
							value='<s:property value="email" />'></td>
						<th><s:text name="RtMember_scale" />:</th>
						<td>
							<select name="scale" size="1">
								<s:iterator value="scaleTypeList" status="status">
									<option value="<s:property value="value" />" <s:if test='scale == value'> selected="selected"</s:if>>
									<s:property value="string" /></option>
								</s:iterator>	
							</select>
						</td>
						<td></td>
						<td></td>
						<td>
							<input type="button" name="button" value="Save" onclick="check_up()" /> 
							<input type="button" value="Clean" onclick="self.location.href='queryMember.action'" /> 
							<input type="button" value="Search" onclick="searchGo();" />
						</td>
					</tr>
				</table>
				<input type="hidden" id="memberIdHidden" name="memberIdHidden" value='<s:property value="memberId" />'>
				<input type="hidden" id="page" name="page" value='0'>
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
						<th><s:text name="RtMember_nickname" /></th>
						<th><s:text name="RtMember_realname" /></th>
						<th><s:text name="RtMember_email" /></th>
						<th><s:text name="RtMember_scale" /></th>
						<th><s:text name="RtMember_updateDate" /></th>
						<th></th>
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
						<td><s:property value="nickname" /></td>
						<td><s:property value="realname" /></td>
						<td><s:property value="email" /></td>
						<td><s:if test='scale == 2'>Y</s:if><s:if test='scale == 1'>N</s:if></td>
						<td><s:property value="updateDate" /></td>
						<td>
							<input type="button" name="button" value="Modify" onclick="toModify('<s:property value="memberId" />','M','')" /> 
							<input type="button" name="button" value="Delete" onclick="toModify('<s:property value="memberId" />','D','<s:property value="#status.count" />')" />
						</td>
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



