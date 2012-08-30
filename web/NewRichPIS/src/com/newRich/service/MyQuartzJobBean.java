package com.newRich.service;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.newRich.util.SystemUtil;

public class MyQuartzJobBean extends QuartzJobBean{

	private static final long serialVersionUID = 7865776832948628396L;
	private Logger logger = LoggerFactory.getLogger(MyQuartzJobBean.class);
	static long counter = 0;  //記數器
	//	private SimpleService simpleService;
	private QuartzService quartzService;

//	public void setSimpleService(SimpleService simpleService) {
//		this.simpleService = simpleService;
//	}
	
	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}
	
	@Override
	protected synchronized void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		Trigger trigger = jobexecutioncontext.getTrigger();
		String triggerName = trigger.getName();
		String group = trigger.getGroup();
		
//		if (counter > 0){
//			System.out.println("Job Dauble!!");
//			return;
//		}
//		counter++;
		//根據Trigger組別調用不同的應用邏輯方法QUARTZ_CLASS_NAME_xxxx
//		if (StringUtils.equals(group, Scheduler.DEFAULT_GROUP)) {
//			simpleService.testMethod2(triggerName, group);
//		} else 
		if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_JobDemo)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_JobDemo().");
			quartzService.JobDemo(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockCode2DB)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_StockCode2DB().");
			quartzService.StockCode2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockFindGood2DB)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_StockFindGood2DB().");
			quartzService.StockFindGood2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockIchart2DB)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_StockIchart2DB().");
			quartzService.StockIchart2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockSector2DB)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_StockSector2DB().");
			quartzService.StockSector2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockStrategy2DB)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_StockStrategy2DB().");
			quartzService.StockStrategy2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockValue2DB)) {
			logger.info("executeInternal QUARTZ_CLASS_NAME_StockValue2DB().");
			quartzService.StockValue2DB(triggerName, group);
		}
	}

}
