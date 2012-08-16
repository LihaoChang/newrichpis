<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jsp/include/re.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=urf-8">
        <title>排程任務列表</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css" />
		<script type="text/javascript" src='<%=request.getContextPath()%>/js/jquery-1.5.1.js'>
        </script>
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
		</script>
    </head>
<form name="queryForm" action="queryQuartz.action">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">
		<table width="100%" border="0">
			<tr>
				<!-- 查詢條件區 -->
				<td><%@ include file="/jsp/include/query_tag_start_p1.jsp"%>
				<div class="mainSubTitle"><s:text name="Quartz" />
				<%@ include file="/jsp/include/query_tag_start_p2.jsp"%>
				<table width="100%" border="0">
					<tr>
						<th width="20%"><s:text name="quartz_name" />:</th>
						<td width="40%">
							<input type="text" id="quartzName" name="quartzName" value='<s:property value="quartzName" />'></td>
						<td width="40%"></td>
					</tr>
				</table>
				<p>
		            <!-- <a href="${pageContext.request.contextPath}/goEditQuartz.action"><s:text name="trigger_add" /></a> -->
		            <input type="button" id="addTrigger" value="<s:text name="trigger_add" />" onclick="toAdd();">
		        </p>
				<%@ include file="/jsp/include/query_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>
        
        <table width="100%" border="0">
            <tr>
                <th><s:text name="trigger_name" /></th>
                <th><s:text name="trigger_group" /></th>
                <th><s:text name="trigger_next_time" /></th>
                <th><s:text name="trigger_last_time" /></th>
                <th><s:text name="trigger_priority" /></th>
                <th><s:text name="trigger_status" /></th>
                <th><s:text name="trigger_type" /></th>
                <th><s:text name="trigger_start_time" /></th>
                <th><s:text name="trigger_end_time" /></th>
                <th><s:text name="trigger_action_command" /></th>
            </tr>
            <s:iterator value="gridModel" status="status">
                <s:if test="#status.even == true">
					<tr class="changeTr">
				</s:if>
				<s:else>
					<tr>
				</s:else>
                    <td><s:property value="triggerName" /></td>
                    <td><s:property value="triggerGroup" /></td>
                    <td><s:property value="nextFireTime" /></td>
                    <td><s:property value="prevFireTime" /></td>
                    <td><s:property value="priority" /></td>
                    <td><s:property value="triggerState" /></td>
                    <td><s:property value="triggerType" /></td>
                    <td><s:property value="startTime" /></td>
                    <td><s:property value="endTime" /></td>
                    <td>
	                   	<input type="button" id="pause" value="<s:text name="trigger_pause" />" onclick="doCmd('pause','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
						<input type="button" id="resume" value="<s:text name="trigger_resume" />" onclick="doCmd('resume','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
						<input type="button" id="remove" value="<s:text name="trigger_remove" />" onclick="doCmd('remove','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">						
                    </td>
                </tr>
            </s:iterator>
        </table>
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
</html>