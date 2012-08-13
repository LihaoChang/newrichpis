package com.newRich.util;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;

import com.opensymphony.xwork2.ActionInvocation;

public class MyTilesTemplateResult extends StrutsResultSupport {

	public MyTilesTemplateResult() {
		super();
	}

	public MyTilesTemplateResult(String location) {
		super(location);
	}

	@Override
	public void doExecute(String location, ActionInvocation ai) throws Exception {
		setLocation(location);
//System.out.println("rrrrrrrrrrrrrrrrrrrrrr");
		String definitionName = "default";
		String attributeName = "body";
		ServletContext servletContext = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		TilesContainer container = TilesAccess.getContainer(servletContext);

		Attribute attribute = new Attribute(location);
		AttributeContext attributeContext = container.startContext(request, response);
		attributeContext.putAttribute(attributeName, attribute);
		container.render(definitionName, request, response);
		container.endContext(request, response);
	}
}