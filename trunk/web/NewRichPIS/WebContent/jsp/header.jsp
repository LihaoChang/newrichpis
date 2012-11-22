<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String login_user_name = String.valueOf(session
			.getAttribute("login_user_name"));
	java.util.Date date_Ut = new java.util.Date();
	java.sql.Timestamp date_ts = new java.sql.Timestamp(
			date_Ut.getTime());

	String check_login = String.valueOf(session
			.getAttribute("check_login"));
	if (!check_login.equals("loginsuccess")) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
%>
<script type="text/javascript">
<!--

	function langSelecter_onChanged() {
		//document.langForm.submit();
		//alert(document.getElementById("langSelecter").value);
		//location.href = '<s:url includeParams="get" encode="true"/>?request_locale='+document.getElementById("langSelecter").value;
		//http://localhost:8081/NewRich/Login.action
		//http://localhost:8081/NewRich/home.action

		var locale = document.getElementById("langSelecter").value;
		var url = '<s:url includeParams="get" encode="true"/>';
		/*** Cut off the String "request_locale=xxx" Begin***/
		var idxReqLoc = url.indexOf("request_locale=");
		var idxAnd = -1;
		var urlPrefix = "";
		url = url.replace("Login.action", "home.action");
		if (url.indexOf("?") < 0) {
			url += "?request_locale=" + locale;
		} else {
			if (idxReqLoc > 0) {
				urlPrefix = url.substring(0, idxReqLoc);
				idxAnd = url.substring(idxReqLoc).indexOf("&");
				if (idxAnd > 0) {
					url = url.substring(0, idxReqLoc)
							+ url.substring(idxAnd + urlPrefix.length);
				} else {
					url = urlPrefix;
				}
			} else {

			}

			//url= url.substring(0, url.indexOf("?"));
			//url += "?request_locale=" + locale;
			if (url.indexOf("?") < 0) {
				url += "?request_locale=" + locale;
			} else {
				url += "&request_locale=" + locale;
			}
		}
		location.href = url;
	}
	//-->
</script>
<table width="99%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<table width="100%" border="0">
				<tr>
					<td align="left"><img
						src='<%=request.getContextPath()%>/images/logo.gif' width="176"
						height="83" alt=''></td>
					<td style="vertical-align: bottom" align="right" width="250"><s:text
							name="Hello" /> <%=login_user_name%></td>
					<td style="vertical-align: bottom" align="right" width="50">
					     <s:set name="SESSION_LOCALE" value="#session['WW_TRANS_I18N_LOCALE']" />
						 <s:bean id="locales" name="com.newRich.util.I18NLocales" />
						 <!-- <s:text name="label_language" />:-->
					     <s:select label="Language" list="#locales.locales" listKey="value" listValue="key"
								value="#SESSION_LOCALE == null ? locale : #SESSION_LOCALE"
								name="request_locale" id="langSelecter"
								onchange="langSelecter_onChanged()" theme="simple" />
						</td>
					<td style="vertical-align: bottom" align="right" width="50"><a
						href="<%=request.getContextPath()%>/logout.action"> [<s:text
								name="logout" />] </a></td>
				</tr>
			</table></td>
	</tr>
</table>
