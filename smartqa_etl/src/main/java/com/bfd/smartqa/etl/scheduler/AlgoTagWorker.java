package com.bfd.smartqa.etl.scheduler;

import java.util.HashMap;
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
import com.bfd.smartqa.etl.dao.po.SuperTag;
import com.bfd.smartqa.etl.dao.po.SuperTagScore;
import com.bfd.smartqa.etl.dao.po.Tag;
import com.bfd.smartqa.etl.dao.po.TmpTag;
import com.bfd.smartqa.etl.dao.po.UserTagScore;
import com.bfd.smartqa.etl.exception.SuperTagScoreNotFoundException;
import com.bfd.smartqa.etl.exception.TagNotFoundException;
import com.bfd.smartqa.etl.exception.TmpTagNotFoundException;
import com.bfd.smartqa.etl.exception.UserTagScoreNotFoundException;
import com.bfd.smartqa.etl.util.AlgoConst;

public class AlgoTagWorker implements Job {
	private static Logger logger = Logger.getLogger(AlgoTagWorker.class);
	
	private TagFetcher tFetcher;
	private TmpTagFetcher ttFetcher;
	private DiscussionFetcher dFetcher;
	private UserTagScoreFetcher utsFetcher;
	private SuperTagScoreFetcher stsFetcher;
	private SuperTagFetcher stFetcher;
	
	private void init() {
		tFetcher = new TagFetcher();
		ttFetcher = new TmpTagFetcher();
		dFetcher = new DiscussionFetcher();
		utsFetcher = new UserTagScoreFetcher();
		stsFetcher = new SuperTagScoreFetcher();
		stFetcher = new SuperTagFetcher();
	}
	
	private void free() {
		tFetcher.free();
		ttFetcher.free();
		dFetcher.free();
		utsFetcher.free();
		stsFetcher.free();
		stFetcher.free();
	}
	
	private boolean searchCaptionTag(String captionInTag, String tagName) {
		String[] tags =  captionInTag.split(",");
		boolean result = false;
		
		for (int i=0;i<tags.length;i++) {
			if (tags[i].equals(tagName)) {
				result = true;
			}
		}
		return result;
	}
	
	private void scoreNewUserAndSuperTag(List<Discussion> list, String tagName) {
		Map<Integer,Integer> talkFreqSum = new HashMap<Integer,Integer>();
		int tagId;
		int superId;
		
		try {
			tagId = tFetcher.getTagByName(tagName).getID();
			superId = stFetcher.getSuperTag(tagId).getSuperID();
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		for (Discussion item : list) {
			String caption = item.getCaptionInTag();
			
			if (this.searchCaptionTag(caption, tagName)) {
				String content = item.getContent();
				Map<Integer,Integer> talkFreq = GetDiscussFreq.getDiscussFreq(content);
				
				// summary uid-talkfreq
				for (int key : talkFreq.keySet()) {
					if (talkFreqSum.containsKey(key)) {
						int freqSum = talkFreqSum.get(key) + talkFreq.get(key);
						talkFreqSum.put(key, freqSum);
					} else {
						talkFreqSum.put(key, talkFreq.get(key));
					}
				}
				float userScore;
				
				for (int uid : talkFreqSum.keySet()) {
					// store user tag score
//					try {
//						UserTagScore userTagScore = utsFetcher.getUserTagScore(tagId, uid);
//						userScore = userTagScore.getScore() + talkFreqSum.get(uid) * AlgoConst.NOTMY_TAG_SCORE_WEIGHT;
//						userTagScore.setScore(userScore);
//						utsFetcher.setUserTagScore(userTagScore);
//					} catch (UserTagScoreNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
					
					// add user tag score
					userScore = AlgoConst.INIT_TMP_TAG_SCORE;
					UserTagScore userTagScore = new UserTagScore();
					userTagScore.setTagID(tagId);
					userTagScore.setUid(uid);
					userTagScore.setScore(userScore);
					utsFetcher.addUserTagScore(userTagScore);
//					}
					
					// store super tag score
//					try {
//						SuperTagScore superTagScore = stsFetcher.getSuperTagScore(uid, superId);
//						float finalScore = superTagScore.getScore() + userScore;
//						superTagScore.setScore(finalScore);
//						stsFetcher.setUserTagScore(superTagScore);
//					} catch (SuperTagScoreNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
					
					// add super tag score
					SuperTagScore superTagScore = new SuperTagScore();
					superTagScore.setScore(userScore);
					superTagScore.setSuperID(superId);
					superTagScore.setUid(uid);
					stsFetcher.addUserTagScore(superTagScore);
//					}
				}
			}
		}
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.debug("Wake up Tmp Tag refreshing!");
		this.init();
		
		try {
			List<TmpTag> list = ttFetcher.getAllTmpTag();
			List<Discussion> discussions = dFetcher.getFullDiscussion();
			
			for (TmpTag tmpTag : list) {
				int hotPoint = tmpTag.getHotPoint();
				String tmpTagName = tmpTag.getTag();
				
				if (hotPoint >= AlgoConst.HOTPOINT_THRESHOLD) {
					ttFetcher.removeTmpTag(tmpTag);
					Tag tag = new Tag();
					tag.setTag(tmpTagName);
					tFetcher.addTag(tag);
					
					// add super-tag relation
					try {
						Tag newTag = tFetcher.getTagByName(tmpTagName);
						int superId = stFetcher.getMaxSuperID() + 1;
						SuperTag superTag = new SuperTag();
						superTag.setSuperID(superId);
						superTag.setTagID(newTag.getID());
						stFetcher.addSuperTag(superTag);
					} catch (TagNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// freshly build per user-tag score and super-tag score
					this.scoreNewUserAndSuperTag(discussions, tmpTagName);
				}
			}
		} catch (TmpTagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.free();
		logger.debug("Finish job Tmp Tag refreshing!");
	}
	
	public static void main(String[] args) {
		GlobalHolder.init();
		AlgoTagWorker demo = new AlgoTagWorker();
		try {
			demo.execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
