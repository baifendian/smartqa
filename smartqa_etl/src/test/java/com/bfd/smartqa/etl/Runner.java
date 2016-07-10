package com.bfd.smartqa.etl;

import java.text.ParseException;

import org.quartz.CronExpression;
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
			SchedulerFactory schedFactory = new StdSchedulerFactory();
			Scheduler sched = schedFactory.getScheduler();
			sched.start();
			
			JobDetail jobDetail = JobBuilder.newJob(QuartzTest.class)
					.withIdentity("watchman", "schdjob")
					.build();
			
			Trigger trigger;
			try {
				trigger = TriggerBuilder.newTrigger()
						.withIdentity("tagTrigger", "tagTriggerGroup")
						.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("*/5 * * * * ?")))
						.forJob(jobDetail)
						.build();
				
				sched.scheduleJob(jobDetail,trigger);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
