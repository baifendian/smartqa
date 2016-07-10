package com.bfd.smartqa.etl.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.algo.GetDiscussFreq;
import com.bfd.smartqa.etl.biz.DiscussionFetcher;
import com.bfd.smartqa.etl.biz.SuperTagFetcher;
import com.bfd.smartqa.etl.biz.SuperTagScoreFetcher;
import com.bfd.smartqa.etl.biz.TagFetcher;
import com.bfd.smartqa.etl.biz.TmpTagFetcher;
import com.bfd.smartqa.etl.biz.UserTagScoreFetcher;
import com.bfd.smartqa.etl.dao.po.Discussion;
import com.bfd.smartqa.etl.dao.po.SuperTagScore;
import com.bfd.smartqa.etl.dao.po.Tag;
import com.bfd.smartqa.etl.dao.po.TmpTag;
import com.bfd.smartqa.etl.dao.po.UserTagScore;
import com.bfd.smartqa.etl.exception.SuperTagScoreNotFoundException;
import com.bfd.smartqa.etl.exception.TagNotFoundException;
import com.bfd.smartqa.etl.exception.TmpTagNotFoundException;
import com.bfd.smartqa.etl.exception.UserTagScoreNotFoundException;
import com.bfd.smartqa.etl.util.AlgoConst;

public class AlgoScoreWorker implements Job {
	private static Logger logger = Logger.getLogger(AlgoScoreWorker.class);
	
	private DiscussionFetcher dFetcher;
	private SuperTagFetcher stFetcher;
	private SuperTagScoreFetcher stsFetcher;
	private TagFetcher tFetcher;
	private UserTagScoreFetcher utsFetcher;
	private TmpTagFetcher ttFetcher;
	
	private void init() {
		dFetcher = new DiscussionFetcher();
		stFetcher = new SuperTagFetcher();
		stsFetcher = new SuperTagScoreFetcher();
		tFetcher = new TagFetcher();
		utsFetcher = new UserTagScoreFetcher();
		ttFetcher = new TmpTagFetcher();
	}
	
	private void free() {
		dFetcher.free();
		stFetcher.free();
		stsFetcher.free();
		tFetcher.free();
		utsFetcher.free();
		ttFetcher.free();
	}
	
	private String[] cutCaptionTag(String captionInTag) {
		return captionInTag.split(",");
	}
	
	private int findTagId(String name) throws TagNotFoundException {
		Tag tag = tFetcher.getTagByName(name);
		return tag.getID();
	}
	
	private int analyzeTopicHot(Map<Integer,Integer> data) {
		int uidCnt = data.size();
		int contentCnt = 0;
		int hot = 0;
		
		for (int key : data.keySet()) {
			contentCnt += data.get(key);
		}
		
		hot = uidCnt * contentCnt;
		return hot;
	}
	
	private void storeTmpTag(String tagName) {
		try {
			TmpTag tmpTag = ttFetcher.getTmpTag(tagName);
			int finalPoint = tmpTag.getHotPoint() + AlgoConst.HOTPOINT_UNIT;
			tmpTag.setHotPoint(finalPoint);
			ttFetcher.setTmpTag(tmpTag);
		} catch (TmpTagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// add new tmp tag in system
			TmpTag tmpTag = new TmpTag();
			tmpTag.setTag(tagName);
			tmpTag.setHotPoint(AlgoConst.HOTPOINT_INIT);
			ttFetcher.addTmpTag(tmpTag);
		}
		
	}
	
	private void storeNewScore(List<Integer> tagIdList, Map<Integer,Integer> talkFreqMap) {
		
		for (int tagId : tagIdList) {
			for (int uid : talkFreqMap.keySet()) {
				float deltaScore = 0F;
				
				try {
					UserTagScore userTagScore = utsFetcher.getUserTagScore(tagId, uid);
					float score = userTagScore.getScore();
					int talkFreq = talkFreqMap.get(uid);
					deltaScore = talkFreq * AlgoConst.MY_TAG_SCORE_WEIGHT;
					score = score + deltaScore;
					userTagScore.setScore(score);
					// update UserTagScore table
					utsFetcher.setUserTagScore(userTagScore);
				} catch (UserTagScoreNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					int talkFreq = talkFreqMap.get(uid);
					// add usertagscore in system
					deltaScore = AlgoConst.INIT_USER_TAG_SCORE + talkFreq * AlgoConst.NOTMY_TAG_SCORE_WEIGHT;
					UserTagScore uts = new UserTagScore();
					uts.setScore(deltaScore);
					uts.setTagID(tagId);
					uts.setUid(uid);
					utsFetcher.addUserTagScore(uts);
				}
				
				// store supertagscore to system
				int superId = stFetcher.getSuperTag(tagId).getSuperID();
				try {
					SuperTagScore superTagScore = stsFetcher.getSuperTagScore(uid, superId);
					float finalScore = superTagScore.getScore() + talkFreqMap.get(uid) * AlgoConst.MY_SUPERTAG_SCORE_WEIGHT;
					superTagScore.setScore(finalScore);
					stsFetcher.setUserTagScore(superTagScore);
				} catch (SuperTagScoreNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					float finalScore = AlgoConst.INIT_USER_TAG_SCORE + talkFreqMap.get(uid) * AlgoConst.NOTMY_SUPERTAG_SCORE_WEIGHT;
					// super tag score not exist
					SuperTagScore superTagScore = new SuperTagScore();
					superTagScore.setUid(uid);
					superTagScore.setSuperID(superId);
					superTagScore.setScore(finalScore);
					stsFetcher.addUserTagScore(superTagScore);
				}
			}
		}
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.debug("Wake up Score refreshing!");
		this.init();
		
		List<Discussion> list = dFetcher.getAllDiscussion();
		for (Discussion item : list) {
			String caption = item.getCaptionInTag();
			String content = item.getContent();
			String[] tagsOfCaption = this.cutCaptionTag(caption);
			// regular tag ID list existing in system
			List<Integer> regTagIdList = new ArrayList<Integer>();
			
			for (int i=0;i<tagsOfCaption.length;i++) {
				String tagName = tagsOfCaption[i];
				
				try {
					int tagId = this.findTagId(tagName);
					// store found existing tag in the system
					regTagIdList.add(tagId);
				} catch (TagNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// tag do not exist in the system
					// store it in our db
					this.storeTmpTag(tagName);
				}
			}
			
			// parse the discussion and compute the score
			Map<Integer,Integer> talkFreq = GetDiscussFreq.getDiscussFreq(content);
			this.storeNewScore(regTagIdList, talkFreq);
			
			// calculate topic hot point
			int hot = this.analyzeTopicHot(talkFreq);
			item.setHot(hot);
			dFetcher.setHot(item);
			
			// mark discussion has been handled
			item.setIshandle(1);
			dFetcher.setDiscussion(item);
		}
		
		this.free();
		logger.debug("Finish job Score refreshing!");
	}

	public static void main(String[] args) {
		GlobalHolder.init();
		AlgoScoreWorker demo = new AlgoScoreWorker();
		try {
			demo.execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
