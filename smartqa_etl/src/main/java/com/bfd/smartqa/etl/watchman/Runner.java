package com.bfd.smartqa.etl.watchman;

import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Runner extends Thread {

	public void run() {
		
		try {
			StdSchedulerFactory schedFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "WatchmanSched");
			props.put("org.quartz.threadPool.threadCount", "10");
			schedFactory.initialize(props);
			Scheduler sched = schedFactory.getScheduler();
			sched.start();
			
			JobDetail jobDetail = JobBuilder.newJob(Worker.class)
					.withIdentity("watchman", "schdjob")
					.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("dailyTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 1))
					.forJob(jobDetail)
					.build();
			
			sched.scheduleJob(jobDetail,trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
