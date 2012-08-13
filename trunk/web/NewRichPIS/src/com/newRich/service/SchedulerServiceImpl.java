package com.newRich.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.newRich.dao.QuartzDao;
import com.newRich.util.SpringQuartzConstant;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

	private Scheduler scheduler;
	private JobDetail jobDetail;
	private QuartzDao quartzDao;

	private static final Logger logger = LoggerFactory
			.getLogger(SchedulerServiceImpl.class);

	@Autowired
	public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Autowired
	public void setQuartzDao(@Qualifier("quartzDao") QuartzDao quartzDao) {
		this.quartzDao = quartzDao;
	}

	@Override
	public void schedule(String cronExpression) {
		schedule("", cronExpression);
	}

	@Override
	public void schedule(String name, String cronExpression) {
		schedule(name, cronExpression, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(String name, String cronExpression, String group) {
		try {
			schedule(name, new CronExpression(cronExpression), group);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(CronExpression cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, CronExpression cronExpression) {
		schedule(name, cronExpression, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(String name, CronExpression cronExpression,
			String group) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 名稱後面加上UUID，確保名稱的唯一性
			name += "&" + UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name, group, jobDetail
					.getName(), Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
			scheduler.rescheduleJob(cronTrigger.getName(), cronTrigger
					.getGroup(), cronTrigger);

		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(Date startTime) {
		schedule(startTime, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(Date startTime, String group) {
		schedule(startTime, null, group);
	}

	@Override
	public void schedule(String name, Date startTime) {
		schedule(name, startTime, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(String name, Date startTime, String group) {
		schedule(name, startTime, null, group);
	}

	@Override
	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(Date startTime, Date endTime, String group) {
		schedule(startTime, endTime, 0, group);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, String group) {
		schedule(name, startTime, endTime, 0, group);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule(startTime, endTime, 0, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount,
			String group) {
		schedule(null, startTime, endTime, 0, group);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount) {
		schedule(name, startTime, endTime, 0, Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, String group) {
		schedule(name, startTime, endTime, 0, 1L, group);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval) {
		schedule(startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String group) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval, group);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval) {
		this.schedule(name, startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String group) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 名稱後面加上UUID，確保名稱的唯一性
			name += "&" + UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			SimpleTrigger SimpleTrigger = new SimpleTrigger(name, group,
					jobDetail.getName(), Scheduler.DEFAULT_GROUP, startTime,
					endTime, repeatCount, repeatInterval);
			scheduler.scheduleJob(SimpleTrigger);
			scheduler.rescheduleJob(SimpleTrigger.getName(), SimpleTrigger
					.getGroup(), SimpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(Map<String, String> map) {

		String temp = null;
		// 實例化SimpleTrigger
		SimpleTrigger SimpleTrigger = new SimpleTrigger();

		// 這些值的設置也可以由外面傳入，目前使用默許值
		SimpleTrigger.setJobName(jobDetail.getName());
		SimpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);
		SimpleTrigger.setRepeatInterval(1000L);

		// 設置名稱
		temp = map.get(SpringQuartzConstant.TRIGGERNAME);
		if (StringUtils.isEmpty(StringUtils.trim(temp))) {
			temp = UUID.randomUUID().toString();
		} else {
			// 在名稱後面加上UUID，確保名稱的唯一性
			temp += "&" + UUID.randomUUID().toString();
		}
		SimpleTrigger.setName(temp);

		// 設置Trigger分組
		temp = map.get(SpringQuartzConstant.TRIGGERGROUP);
		if (StringUtils.isEmpty(temp)) {
			temp = Scheduler.DEFAULT_GROUP;
		}
		SimpleTrigger.setGroup(temp);

		// 設置開始時間
		temp = map.get(SpringQuartzConstant.STARTTIME);
		if (StringUtils.isNotEmpty(temp)) {
			SimpleTrigger.setStartTime(this.parseDate(temp));
		}

		// 設置結束時間
		temp = map.get(SpringQuartzConstant.ENDTIME);
		if (StringUtils.isNotEmpty(temp)) {
			SimpleTrigger.setEndTime(this.parseDate(temp));
		}

		// 設置執行次數
		temp = map.get(SpringQuartzConstant.REPEATCOUNT);
		if (StringUtils.isNotEmpty(temp) && NumberUtils.toInt(temp) > 0) {
			SimpleTrigger.setRepeatCount(NumberUtils.toInt(temp));
		}

		// 設置執行時間間隔
		temp = map.get(SpringQuartzConstant.REPEATINTERVEL);
		if (StringUtils.isNotEmpty(temp) && NumberUtils.toLong(temp) > 0) {
			SimpleTrigger.setRepeatInterval(NumberUtils.toLong(temp) * 1000);
		}

		try {
			scheduler.addJob(jobDetail, true);

			scheduler.scheduleJob(SimpleTrigger);
			scheduler.rescheduleJob(SimpleTrigger.getName(), SimpleTrigger
					.getGroup(), SimpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getQrtzTriggers() {
		return quartzDao.getQrtzTriggers();
	}

	@Override
	public void pauseTrigger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(triggerName, group);// 停止Trigger
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resumeTrigger(String triggerName, String group) {
		try {
			// Trigger trigger = scheduler.getTrigger(triggerName, group);

			scheduler.resumeTrigger(triggerName, group);// 重啟Trigger
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean removeTrigdger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(triggerName, group);// 停止Trigger
			return scheduler.unscheduleJob(triggerName, group);// 移除
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private Date parseDate(String time) {
		try {
			return DateUtils.parseDate(time,
					new String[] { "yyyy-MM-dd HH:mm" });
		} catch (ParseException e) {
			logger.error("日期格式錯誤{}，正確的格式為：yyyy-MM-dd HH:mm", time);
			throw new RuntimeException(e);
		}
	}
}
