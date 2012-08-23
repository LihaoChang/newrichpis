<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jsp/include/re.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.5.1.js"></script>
		<script type="text/javascript">
		function doCmd(state,triggerName,group,triggerState){
			
			if(state == 'pause' && triggerState=='PAUSED'){
				alert("該Trigger已經暫停！");
				return;
			}
			
		    if(state == 'resume' && triggerState != 'PAUSED'){
				alert("該Trigger正在運行中！");
				return;
			}
			//客戶端二次編碼，服務端再解碼，否則中文亂碼 
			triggerName = encodeURIComponent(encodeURIComponent(triggerName));
			group = encodeURIComponent(encodeURIComponent(group));
            $.ajax({
                url: '${pageContext.request.contextPath}/JobProcessServlet?jobtype=200&action='+state+'&triggerName='+triggerName+'&group='+group,
                type: 'post',
                //dataType: 'xml',
               // timeout: 3000,
                error: function(){
                   alert("執行失敗！");		
                },
                success: function(xml){
					if (xml == 0) {
						alert("執行成功！");
						window.location.reload();
					}else{
						alert("執行失敗！");	
					}		   
                }
            });
		}

		function toAdd() {
			document.queryForm.action = "goEditQuartz.action";
			document.queryForm.submit(); //送出表單中的資料
		}

		function searchGo() {
			document.queryForm.action = "queryQuartz.action";
			document.queryForm.submit(); //送出表單中的資料
		}
		</script>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">
		<table width="100%" border="0">
			<tr>
				<!-- 查詢條件區 -->
				<td><%@ include file="/jsp/include/query_tag_start_p1.jsp"%>
				<div class="mainSubTitle"><s:text name="Quartz" />
				<%@ include file="/jsp/include/query_tag_start_p2.jsp"%>
				<form name="queryForm" action="queryQuartz.action">
				<table width="100%" border="0">
					<tr>
						<th width="20%"><s:text name="trigger_group" />:</th>
						<td width="40%">
							<select name="triggerGroup">
								<option value=""></option>
				              	<option value="DEFAULT">default</option>
				              	<s:iterator value="quartzTypeList" status="status">
									<option value="<s:property value="value" />">
									<s:property value="string" /></option>
								</s:iterator>
				              </select>
							<!-- <input type="text" id="triggerGroup" name="triggerGroup" value='<s:property value="triggerGroup" />'></td> -->
						<td width="40%"></td>
					</tr>
					<tr>
						<td width="20%"></th>
						<td width="40%"></td>
						<td width="40%">
							<input type="button" value="<s:text name="action_search" />" onclick="searchGo();" />
							<input type="button" id="addTrigger" value="<s:text name="trigger_add" />" onclick="toAdd();">
						</td>
					</tr>
				</table>
				<input type="hidden" id="id" name="id" value='<s:property value="id" />'>
				<input type="hidden" id="page" name="page" value='0'>
				</form>
				<%@ include file="/jsp/include/query_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>
        
        <table width="100%" border="0">
            <tr>
                <th align="center"><s:text name="trigger_name" /></th>
                <th align="center"><s:text name="trigger_group" /></th>
                <th align="center"><s:text name="trigger_next_time" /></th>
                <th align="center"><s:text name="trigger_last_time" /></th>
                <th align="center"><s:text name="trigger_priority" /></th>
                <th align="center"><s:text name="trigger_status" /></th>
                <th align="center"><s:text name="trigger_type" /></th>
                <th align="center"><s:text name="trigger_start_time" /></th>
                <th align="center"><s:text name="trigger_end_time" /></th>
                <th align="center"><s:text name="trigger_action_command" /></th>
            </tr>
            <s:iterator value="gridModel" status="status">
                <s:if test="#status.even == true">
					<tr class="changeTr">
				</s:if>
				<s:else>
					<tr>
				</s:else>
                    <td align="center"><s:property value="triggerNameReal" /></td>
                    <td align="center"><s:property value="triggerGroup" /></td>
                    <td align="center"><s:property value="nextFireTime" /></td>
                    <td align="center"><s:property value="prevFireTime" /></td>
                    <td align="center"><s:property value="priority" /></td>
                    <td align="center"><s:property value="triggerState" /></td>
                    <td align="center"><s:property value="triggerType" /></td>
                    <td align="center"><s:property value="startTime" /></td>
                    <td align="center"><s:property value="endTime" /></td>
                    <td align="center">
	                   	<input type="button" id="pause" value="<s:text name="trigger_pause" />" onclick="doCmd('pause','<s:property value="triggerName" />','<s:property value="triggerGroup" />','<s:property value="triggerState" />')">
						<input type="button" id="resume" value="<s:text name="trigger_resume" />" onclick="doCmd('resume','<s:property value="triggerName" />','<s:property value="triggerGroup" />','<s:property value="triggerState" />')">
						<input type="button" id="remove" value="<s:text name="trigger_remove" />" onclick="doCmd('remove','<s:property value="triggerName" />','<s:property value="triggerGroup" />','<s:property value="triggerState" />')">						
                    </td>
                </tr>
            </s:iterator>
        </table>
        <form name="modifyForm"><input type="hidden" id="modify_id"
					name="modify_id" value=""></form>
        <!-- 分頁 -->
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
