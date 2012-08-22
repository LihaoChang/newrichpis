package com.newRich.service;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.newRich.util.SystemUtil;

public class MyQuartzJobBean extends QuartzJobBean {

	private SimpleService simpleService;
	private QuartzService quartzService;

	public void setSimpleService(SimpleService simpleService) {
		this.simpleService = simpleService;
	}
	
	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		Trigger trigger = jobexecutioncontext.getTrigger();
		String triggerName = trigger.getName();
		String group = trigger.getGroup();
		
		//根據Trigger組別調用不同的應用邏輯方法QUARTZ_CLASS_NAME_xxxx
		if (StringUtils.equals(group, Scheduler.DEFAULT_GROUP)) {
			simpleService.testMethod2(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_JobDemo)) {
			quartzService.JobDemo(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockCode2DB)) {
			quartzService.StockCode2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockFindGood2DB)) {
			quartzService.StockFindGood2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockIchart2DB)) {
			quartzService.StockIchart2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockSector2DB)) {
			quartzService.StockSector2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockStrategy2DB)) {
			quartzService.StockStrategy2DB(triggerName, group);
		} else if (StringUtils.equals(group, SystemUtil.QUARTZ_CLASS_NAME_StockValue2DB)) {
			quartzService.StockValue2DB(triggerName, group);
		}
	}

}
