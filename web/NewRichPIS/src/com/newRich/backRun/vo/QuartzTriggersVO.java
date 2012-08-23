package com.newRich.backRun.vo;

import java.sql.Blob;

public class QuartzTriggersVO {
	public String triggerName;
	public String triggerGroup;
	public String jobName;
	public String jobGroup;
	public String isVolatile;
	public String description;
	public String nextFireTime;
	public String prevFireTime;
	public int priority;
	public String triggerState;
	public String triggerType;
	public String startTime;
	public String endTime;
	public String calendarName;
	public int misfireInstr;
	public Blob jobData;
	public String triggerNameReal;

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getIsVolatile() {
		return isVolatile;
	}

	public void setIsVolatile(String isVolatile) {
		this.isVolatile = isVolatile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public int getMisfireInstr() {
		return misfireInstr;
	}

	public void setMisfireInstr(int misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	public Blob getJobData() {
		return jobData;
	}

	public void setJobData(Blob jobData) {
		this.jobData = jobData;
	}

	public String getTriggerNameReal() {
		return triggerNameReal;
	}

	public void setTriggerNameReal(String triggerNameReal) {
		this.triggerNameReal = triggerNameReal;
	}
	
}
