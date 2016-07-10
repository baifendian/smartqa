package com.bfd.smartqa.etl.scheduler;

import java.text.ParseException;
import java.util.Properties;

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


public class AlgoTagRunner extends Thread {

	public void run() {
		try {
			StdSchedulerFactory schedFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "TagSched");
			props.put("org.quartz.threadPool.threadCount", "10");
			schedFactory.initialize(props);
			Scheduler sched = schedFactory.getScheduler();
			sched.start();
			
			JobDetail jobDetail = JobBuilder.newJob(AlgoTagWorker.class)
					.withIdentity("algoTag", "tagjob")
					.build();
			
			Trigger trigger;
			try {
				trigger = TriggerBuilder.newTrigger()
						.withIdentity("tagTrigger", "tagTriggerGroup")
						.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0 */10 * * * ?")))
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
