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
        <title>增加排程任務</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/components/calendar/skins/aqua/theme.css" />
        <script type="text/javascript" src='<%=request.getContextPath()%>/js/components/calendar/calendar.js'>
        </script>
        <script type="text/javascript" src='<%=request.getContextPath()%>/js/components/calendar/calendar-setup.js'>
        </script>
        <script type="text/javascript" src='<%=request.getContextPath()%>/js/components/calendar/lang/calendar-zh_CN.js'>
        </script>
        <script type="text/javascript" src='<%=request.getContextPath()%>/js/jquery-1.5.1.js'>
        </script>
        <script type="text/javascript" src='<%=request.getContextPath()%>/js/components/jquery/jquery.form.js'>
        </script>
        <script type="text/javascript" src='<%=request.getContextPath()%>/js/job.js'>
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
				<div class="mainSubTitle"><s:text name="quartz_edit" />
				<%@ include file="/jsp/include/query_tag_start_p2.jsp"%>
<form id="myForm1" action="${pageContext.request.contextPath}/JobProcessServlet?jobtype=0&action=add" method="post">
  <table>
      <tr>
          <th colspan="3" bgcolor="00ff00">
              <b>Simple Trigger</b>
          </th>
      </tr>
      <tr>
          <td nowrap>Trigger名稱： </td>
          <td>
              <input type="text" name ="p_triggerName" size="20">
          </td>
          <td>（必填） </td>
      </tr>
      <tr>
          <td nowrap>Trigger分組：</td>
          <td>
              <select name="p_triggerGroup">
                  <option value="DEFAULT">default</option>
              </select>
          </td>
          <td>（Trigger分组，Quartz默許為DEFAULT）</td>
      </tr>
      <tr>
          <td nowrap>開始時間：</td>
          <td>
              <input type="text" name="p_startTime" size="20">
          </td>
          <td>
              <img id="calBegin" src="<%=request.getContextPath()%>/js/components/calendar/skins/aqua/cal.gif" border="0" alt="開始時間" style="cursor:pointer">（Trigger執行開始時間，必填） 
          </td>
      </tr>
      <tr>
          <td nowrap>結束時間：</td>
          <td>
              <input type="text" name="p_endTime" size="20">
          </td>
          <td>
              <img id="calEnd" src="<%=request.getContextPath()%>/js/components/calendar/skins/aqua/cal.gif" border="0" alt="結束時間" style="cursor:pointer">（Trigger執行結束時間，可以不填寫） 
          </td>
      </tr>
      <tr>
          <td nowrap>執行次數：</td>
          <td>
              <input type="text" name="p_repeatCount" size="20">
          </td>
          <td>  次（表示Trigger啟動後執行多少次結束，不填寫預設執行一次） </td>
      </tr>
      <tr>
          <td nowrap>執行間隔：</td>
          <td>
              <input type="text" name="p_repeatInterval" size="20">
          </td>
          <td>  秒（表示Trigger間隔多長時間執行一次，不填寫前後執行就沒的間隔時間，直到任務結束）</td>
      </tr>
      <tr>
          <td colspan="3">
              <input type="submit" value="新增Trigger">
          </td>
      </tr>
  </table>
</form>
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

<br>

<form id="myForm2" action="${pageContext.request.contextPath}/JobProcessServlet?jobtype=1&action=add" method="post">
    <table>
        <tr>
            <th colspan="3" bgcolor="00ff00">
                <b>Cron Trigger 1</b>
            </th>
        </tr>
        <tr>
            <td nowrap>Trigger 名稱：</td>
            <td>
                <input type="text" name ="triggerName" size="20">
            </td>
            <td>（必填）</td>
        </tr>
        <tr>
            <td nowrap>Cron：</td>
            <td>
                <input type="text" name ="cronExpression" size="20">
            </td>
            <td>（必填，Cron表示式(如"0/10 * * ? * * *"，每10秒中執行一次)，對使用者要求比較，要會寫Cron表示式，實際項目中不適用）
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="submit" value="新增Trigger">
            </td>
        </tr>
    </table>
</form>

<br>

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

<br>
<%@ include file="/jsp/include/findGood_yellow_tag_end.jsp"%>
		</td>
	</tr>
</table>

		</td>
	</tr>
</table>
</body>
</html>