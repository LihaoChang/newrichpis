<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jsp/include/re.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/components/calendar/skins/aqua/theme.css" />
<script type="text/javascript" src='<%=request.getContextPath()%>/js/components/calendar/calendar.js'>
</script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/components/calendar/calendar-setup.js'>
</script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/components/calendar/lang/calendar-zh_CN.js'>
</script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/jquery/jquery-1.8.0.min.js'>
</script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/components/jquery/jquery.form.js'>
</script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/job.js'>
        </script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td VALIGN="TOP" width="75%">
		<table width="100%" border="0">
			<tr>
				<!-- 查詢條件區 -->
				<td><%@ include file="/jsp/include/query_tag_start_p1.jsp"%>
				<div class="mainSubTitle"><s:text name="quartz_edit" />
				<%@ include file="/jsp/include/query_tag_start_p2.jsp"%>
<form id="myForm1" action="${pageContext.request.contextPath}/JobProcessServlet?jobtype=0&action=add" method="post">
  <table width="100%" border="0">
      <tr>
          <th colspan="2" bgcolor="00ff00">
              <b><s:text name="trigger_simple_trigger" /></b>
          </th>
      </tr>
      <tr>
          <td nowrap width="10%"><s:text name="trigger_name" />： </td>
          <td>
              <input type="text" name ="p_triggerName" size="20">
          </td>
      </tr>
      <tr>
          <td nowrap><s:text name="trigger_group" />：</td>
          <td>
              <select name="p_triggerGroup">
              	<!-- <option value="DEFAULT">default</option> -->
              	<s:iterator value="quartzTypeList" status="status">
					<option value="<s:property value="value" />">
					<s:property value="string" /></option>
				</s:iterator>
              </select>
              	（Trigger分组，Quartz默許為DEFAULT）
          </td>
      </tr>
      <tr>
          <td nowrap><s:text name="trigger_start_time" />：</td>
          <td>
              <input type="text" name="p_startTime" id="p_startTime" size="20">
              <img id="calBegin" src="<%=request.getContextPath()%>/js/components/calendar/skins/aqua/cal.gif" border="0" alt="開始時間" style="cursor:pointer">（Trigger執行開始時間Ex:2012-08-31 13:00，<span style="font:red">*</span>必填）
          </td>
      </tr>
      <tr>
          <td nowrap><s:text name="trigger_end_time" />：</td>
          <td>
              <input type="text" name="p_endTime" id="p_endTime" size="20">
              <img id="calEnd" src="<%=request.getContextPath()%>/js/components/calendar/skins/aqua/cal.gif" border="0" alt="結束時間" style="cursor:pointer">（Trigger執行結束時間Ex:2012-08-31 13:00，可以不填寫）
          </td>
      </tr>
      <tr>
          <td nowrap><s:text name="trigger_times" />：</td>
          <td>
              <input type="text" name="p_repeatCount" size="20">
               	次（表示Trigger啟動後執行多少次結束，不填寫預設執行一次） <img src="<%=request.getContextPath()%>/images/help.png" title="執行次數值『0』:表示執行一次；執行次數值『1』:表示執行二次；執行次數值『2』:表示執行三次，以此類推。" >
          </td>
      </tr>
      <tr>
          <td nowrap><s:text name="trigger_imp_time" />：</td>
          <td>
              <input type="text" name="p_repeatInterval" size="20">
              	 秒（表示Trigger間隔多長時間執行一次，不填寫前後執行就沒的間隔時間，直到任務結束）
          </td>
      </tr>
      <tr>
          <td colspan="2" align="center">
              <input type="submit" value="<s:text name="action_new" />">
          </td>
      </tr>
  </table>
  <script type="text/javascript">
    Calendar.setup({
        inputField: "p_startTime",
        ifFormat: "%Y-%m-%d %H:%M",
        showsTime: "true",
        button: "calBegin",
        step: 1
    });
    
    Calendar.setup({
        inputField: "p_endTime",
        ifFormat: "%Y-%m-%d %H:%M",
        showsTime: "true",
        button: "calEnd",
        step: 1
    });
</script>
</form>


<br>

<form id="myForm2" action="${pageContext.request.contextPath}/JobProcessServlet?jobtype=1&action=add" method="post">
    <table width="100%" border="0">
        <tr>
            <th colspan="2" bgcolor="00ff00">
                <b><s:text name="trigger_cron_trigger" /></b>
            </th>
        </tr>
        <tr>
            <td nowrap width="10%"><s:text name="trigger_name" />：</td>
            <td>
                <input type="text" name ="triggerName" size="20">
                <span style="font-color:red;">*（必填）</span>
            </td>
        </tr>
        <tr>
          <td nowrap><s:text name="trigger_group" />：</td>
          <td>
              <select name="triggerGroup">
              	<!-- <option value="DEFAULT">default</option> -->
              	<s:iterator value="quartzTypeList" status="status">
					<option value="<s:property value="value" />">
					<s:property value="string" /></option>
				</s:iterator>
              </select>
              	（Trigger分组，Quartz默許為DEFAULT）
          </td>
      	</tr>
        <tr>
            <td nowrap><s:text name="trigger_expressions" />：</td>
            <td>
                <input type="text" name ="cronExpression" size="20">
                	（<span style="font-color:red;">*必填</span>，Cron表示式(如"0/10 * * ? * * *"，每10秒中執行一次)
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <input type="submit" value="<s:text name="action_new" />">
            </td>
        </tr>
    </table>
</form>

<br>
<!-- 
<form id="myForm3" action="${pageContext.request.contextPath}/JobProcessServlet?jobtype=2&action=add" method="post">
	<table>
        <tr>
            <th colspan="4" bgcolor="00ff00" width="100%">
                <b>Cron Trigger 2</b>
            </th>
        </tr>
        <tr>
            <td nowrap>Trigger 名稱：</td>
            <td colspan="3">
                <input type="text" name ="triggerName" size="20">（必填）
            </td>                         
        </tr>
        <tr>
            <td nowrap>每 </td>
            <td nowrap>
                <input type="text" name ="val" size="5">
            </td>
            <td nowrap>
              <select name="selType">
				<option value="second">秒</option>
				<option value="minute">分</option>									
              </select>
            </td>
            <td>執行一次（必填，範圍 0-59）</td>
        </tr>
        <tr>
            <td colspan="4">
                <input type="submit" value="新增Trigger">
            </td>
        </tr>
    </table>
</form>
 -->
<br>
<%@ include file="/jsp/include/findGood_yellow_tag_end.jsp"%>
		</td>
	</tr>
</table>

		</td>
	</tr>
</table>
