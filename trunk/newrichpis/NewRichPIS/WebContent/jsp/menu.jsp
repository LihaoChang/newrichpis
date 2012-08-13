<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	/*         */
	function IEHoverPseudo() {

		var navItems = document.getElementById("primary-nav")
				.getElementsByTagName("li");

		for ( var i = 0; i < navItems.length; i++) {
			if (navItems[i].className == "menubar") {
				navItems[i].onmouseover = function() {
					this.className += " over";
				}
				navItems[i].onmouseout = function() {
					this.className = "menubar";
				}
			}
		}

	}
	window.onload = IEHoverPseudo;
	/*   */
</script>

<style type="text/css">
body {
	font: normal 100% verdana;
}

ul#primary-nav,ul#primary-nav ul {
	width: 110px;
	margin: 0;
	padding: 0;
	background: #3f54bb; /*IE6 Bug */
	font-size: 100%;
	text-align: center;
	font-weight: bold;
	/*background-color: #3f54bb;*/
	white-space: nowrap;
	/*background: transparent url(images/Banner_BG2.gif) no-repeat bottom right;*/
}

ul#primary-nav {
	position:relative; 
	left: 6px;
	float: left;
	width: 100%;
}

ul#primary-nav:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}

ul#primary-nav li {
	position: relative;
	list-style: none;
	float: left;
	background: transparent url(images/Banner_BG2.gif);
	width: 110px; /* Width of Menu Items */
	text-align: left;
	z-index: 24;
	white-space: nowrap;
}

ul#primary-nav li a,ul#primary-nav li li a {
	display: block;
	text-decoration: none;
	color: #FFFFFF;
	padding: 5px;
	border: 0px solid #f9f9ff;
	z-index: 24;
}

/* Fix IE. Hide from IE Mac \*/
* html ul#primary-nav li {
	float: left;
	height: 1%;
}

* html ul#primary-nav li a {
	height: 1%;
}

/* End */
ul#primary-nav ul {
	position: absolute;
	display: none;
  background-color: #4378B6;
	border: 1px solid #CCC;
	z-index: 24;
		
}

ul#primary-nav ul ul {
	left: 110px;
	top: 0;
	z-index: 24;
	
}

ul#primary-nav li ul li a {
	padding: 5px 5px;
}  /* Sub Menu Styles */
ul#primary-nav li:hover ul ul,ul#primary-nav li:hover ul ul ul,ul#primary-nav li.over ul ul,ul#primary-nav li.over ul ul ul
	{
	display: none;
}  /* Hide sub-menus initially */
ul#primary-nav li:hover ul,ul#primary-nav li li:hover ul,ul#primary-nav li li li:hover ul,ul#primary-nav li.over ul,ul#primary-nav li li.over ul,ul#primary-nav li li li.over ul
	{
	display: block;
}  /* The magic */
ul#primary-nav li.menubar {
	background: transparent url(images/Darrow.gif) 80px center no-repeat;
}

ul#primary-nav li li.menubar {
	background: transparent url(images/Banner_BG2.gif);
}

ul#primary-nav li:hover,ul#primary-nav li.over {
	background-color: #3f54bb !important;
	background: transparent url(images/Banner_BG2.gif);
}

ul#primary-nav li a:hover {
	color: #DDDDDD;
	/*background-color: #6a99c7; */
}
</style>


<c:if test="${not empty repository}">
	<menu:useMenuDisplayer name="CSSListMenu" id="primary-nav"
		repository="repository">
		<c:forEach var="menu" items="${repository.topMenus}">
			<menu-el:displayMenu name="${menu.name}" />
		</c:forEach>
	</menu:useMenuDisplayer>
</c:if>
<%-- 
<menu:useMenuDisplayer name="ListMenu" bundle="org.apache.struts.action.MESSAGE" repository="repository">
	<c:forEach var="menu" items="${repository.topMenus}">
		<menu-el:displayMenu name="${menu.name}" />
	</c:forEach>
</menu:useMenuDisplayer>
--%>
