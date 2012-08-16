package com.newRich.service;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("simpleService")
public class SimpleService implements Serializable{
	
	private static final long serialVersionUID = 122323233244334343L;
	private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);

	
	public void testMethod(String triggerName, String group){
		//这里执行定时调度业务
		logger.info("AAAA:"+triggerName+"=="+group);
		
	}
	
	public void testMethod2( String triggerName,String group){
		//这里执行定时调度业务
		logger.info("BBBB:"+triggerName+"=="+group);
	}


	
	
}
