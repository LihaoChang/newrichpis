<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jsp/include/re.jsp"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		</script>
    </head>
<body>
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
		            <a href="${pageContext.request.contextPath}/goEditQuartz.action">新增排程</a>
		        </p>
				<%@ include file="/jsp/include/query_tag_end.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/jsp/include/in_tag_start.jsp"%>
        
        <table width="100%" border="0" id="GridView1">
            <tr>
                <th nowrap>Trigger 名稱</th>
                <th nowrap>Trigger 分組</th>
                <th nowrap>下次執行時間</th>
                <th nowrap>上次執行時間</th>
                <th nowrap>優先級</th>
                <th nowrap>Trigger 狀態</th>
                <th nowrap>Trigger 類型</th>
                <th nowrap>開始時間</th>
                <th nowrap>結束時間</th>
                <th nowrap>動作命令</th>
            </tr>
            <c:forEach var="map" items="${list}">
                <tr>
                    <td nowrap>${map.display_name}</td>
                    <td nowrap>${map.trigger_group}</td>
                    <td nowrap>${map.next_fire_time}</td>
                    <td nowrap>${map.prev_fire_time}</td>
                    <td nowrap>${map.priority}</td>
                    <td nowrap>${map.statu}</td>
                    <td nowrap>${map.trigger_type}</td>
                    <td nowrap>${map.start_time}</td>
                    <td nowrap>${map.end_time}</td>
                    <td nowrap>
                    	<input type="button" id="pause" value="暫停" onclick="doCmd('pause','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
						<input type="button" id="resume" value="啟動" onclick="doCmd('resume','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
						<input type="button" id="remove" value="刪除" onclick="doCmd('remove','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">						
                    </td>
                </tr>
            </c:forEach>
        </table>
        <%@ include file="/jsp/include/in_tag_end.jsp"%></td>
		</tr>
	</table>
    </body>
</html>