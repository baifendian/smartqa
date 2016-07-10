package com.bfd.smartqa.etl.watchman;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.biz.SuperTagScoreFetcher;
import com.bfd.smartqa.etl.biz.UserTagScoreFetcher;
import com.bfd.smartqa.etl.dao.po.SuperTagScore;
import com.bfd.smartqa.etl.dao.po.UserTagScore;
import com.bfd.smartqa.etl.util.AlgoConst;

public class Worker implements Job {
	private static Logger logger = Logger.getLogger(Worker.class);
	
	private UserTagScoreFetcher uFetcher;
	private SuperTagScoreFetcher sFetcher;
	
	private void init() {
		uFetcher = new UserTagScoreFetcher();
		sFetcher = new SuperTagScoreFetcher();
	}
	
	private void free() {
		uFetcher.free();
		sFetcher.free();
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.debug("Wake up daily watchman!");
		this.init();
		
		List<UserTagScore> list = uFetcher.getAllUserTagScore();
		for (UserTagScore item : list) {
			float score = item.getScore();
			item.setScore(score * AlgoConst.DAILY_DECAY_POINT);
			uFetcher.setUserTagScore(item);
		}
		
		List<SuperTagScore> slist = sFetcher.getAllSuperTagScore();
		for (SuperTagScore item : slist) {
			float score = item.getScore();
			item.setScore(score * AlgoConst.DAILY_DECAY_POINT);
			sFetcher.setUserTagScore(item);
		}
		
		this.free();
		logger.debug("Finish job daily watchman!");
	}
	
	public static void main(String[] args) {
		GlobalHolder.init();
		Worker demo = new Worker();
		
		try {
			demo.execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
