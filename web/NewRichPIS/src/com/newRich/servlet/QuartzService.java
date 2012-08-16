package com.newRich.servlet;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newRich.quartz.JobDemo;
import com.newRich.quartz.StockCode2DB;
import com.newRich.quartz.StockFindGood2DB;
import com.newRich.quartz.StockIchart2DB;
import com.newRich.quartz.StockSector2DB;
import com.newRich.quartz.StockValue2DB;

@Service("quartzService")
public class QuartzService implements Serializable{
	
	private static final long serialVersionUID = 122323233244334343L;
	private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);

	public void JobDemo(String triggerName, String group){
		logger.info("QuartzService JobDemo():"+triggerName+"=="+group);
		JobDemo.run();
	}
	
	public void StockCode2DB(String triggerName, String group){
		logger.info("QuartzService StockCode2DB():"+triggerName+"=="+group);
		StockCode2DB.run();
	}
	
	public void StockFindGood2DB(String triggerName, String group){
		logger.info("QuartzService StockFindGood2DB():"+triggerName+"=="+group);
		StockFindGood2DB.run();
	}
	
	public void StockIchart2DB(String triggerName, String group){
		logger.info("QuartzService StockIchart2DB():"+triggerName+"=="+group);
		StockIchart2DB.run();
	}
	
	public void StockSector2DB(String triggerName, String group){
		logger.info("QuartzService StockSector2DB():"+triggerName+"=="+group);
		StockSector2DB.run();
	}
	
	public void StockValue2DB(String triggerName, String group){
		logger.info("QuartzService StockValue2DB():"+triggerName+"=="+group);
		StockValue2DB.run();
	}
	
	public void testMethod2( String triggerName,String group){
		//这里执行定时调度业务
		logger.info("QuartzService BBBB:"+triggerName+"=="+group);
	}
}