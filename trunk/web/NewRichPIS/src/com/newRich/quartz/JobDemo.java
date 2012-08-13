package com.newRich.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobDemo implements Job {
	static Logger loger = Logger.getLogger(JobDemo.class.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String name = context.getJobDetail().getJobDataMap().getString("name");
		Date thisDate = new Date();
		String dateString = sdf.format(thisDate);
		System.out.println("run " + name + " at " + dateString);
		loger.warn("Executing job: executing at " + dateString);

		try {
			System.out.println("-------------------job_mail_demo-----------------------");
			loger.warn("-------------------job_mail_demo-----------------------");

			Date endDate = new Date();
			String endDateString = sdf.format(endDate);
			System.out.println("END " + name + " at " + endDateString);
			loger.warn("job END: END at " + endDateString);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}