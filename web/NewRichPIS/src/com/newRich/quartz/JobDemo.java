package com.newRich.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobDemo implements Job {
	static Logger loger = Logger.getLogger(JobDemo.class.getName());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	public static void main(String[] args) {
		run();
	}
	
	public static void run(){
		///String name = context.getJobDetail().getJobDataMap().getString("name");
		Date thisDate = new Date();
		String dateString = sdf.format(thisDate);
		System.out.println("run " + JobDemo.class.getName() + " at " + dateString);
		loger.warn("job start: start at " + dateString);

		try {
			loger.warn("-------------------job_mail_demo-----------------------");
			Date endDate = new Date();
			String endDateString = sdf.format(endDate);
			loger.warn("job END: END at " + endDateString);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}