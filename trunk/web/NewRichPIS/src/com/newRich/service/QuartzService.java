package com.newRich.service;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newRich.quartz.JobDemo;
import com.newRich.quartz.StockCode2DB;
import com.newRich.quartz.StockFindGood2DB;
import com.newRich.quartz.StockIchart2DB;
import com.newRich.quartz.StockSector2DB;
import com.newRich.quartz.StockStrategy2DB;
import com.newRich.quartz.StockValue2DB;

@Service("quartzService")
public class  QuartzService implements Serializable{
	
	private static final long serialVersionUID = 122323233244334343L;
	private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);
	
	public synchronized void JobDemo(String triggerName, String group){
		logger.info("QuartzService JobDemo():"+triggerName+"=="+group+"=Running!!=");
		JobDemo.run();
	}
	
	public synchronized void StockCode2DB(String triggerName, String group){
		logger.info("QuartzService StockCode2DB():"+triggerName+"=="+group+"=Running!!=");
		StockCode2DB.run();
	}
	
	public synchronized void StockFindGood2DB(String triggerName, String group){
		logger.info("QuartzService StockFindGood2DB():"+triggerName+"=="+group+"=Running!!=");
		StockFindGood2DB.run();
	}
	
	public synchronized void StockIchart2DB(String triggerName, String group){
		logger.info("QuartzService StockIchart2DB():"+triggerName+"=="+group+"=Running!!=");
		StockIchart2DB.run();
	}
	
	public synchronized void StockSector2DB(String triggerName, String group){
		logger.info("QuartzService StockSector2DB():"+triggerName+"=="+group+"=Running!!=");
		StockSector2DB.run();
	}
	
	public synchronized void StockStrategy2DB(String triggerName, String group){
		logger.info("QuartzService StockStrategy2DB():"+triggerName+"=="+group+"=Running!!=");
		StockStrategy2DB.run();
	}
	
	public synchronized void StockValue2DB(String triggerName, String group){
		logger.info("QuartzService StockValue2DB():"+triggerName+"=="+group+"=Running!!=");
		StockValue2DB.run();
	}
	
	public synchronized void testMethod2( String triggerName,String group){
		logger.info("QuartzService testMethod2():"+triggerName+"=="+group+"=Running!!=");
	}
}