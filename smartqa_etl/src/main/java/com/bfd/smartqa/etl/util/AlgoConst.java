package com.bfd.smartqa.etl.util;

public class AlgoConst {

	public static final float DAILY_DECAY_POINT = 0.95F;
	
	public static final float MY_TAG_SCORE_WEIGHT = 1;		// 用户累计算分权重
	public static final float NOTMY_TAG_SCORE_WEIGHT = 1;		// 用户未有正式标签算分权重; 临时标签升级正式标签算分权重
	
	public static final float MY_SUPERTAG_SCORE_WEIGHT = 1;
	public static final float NOTMY_SUPERTAG_SCORE_WEIGHT = 1;
	
	public static final float INIT_USER_TAG_SCORE = 20F;
	public static final float INIT_TMP_TAG_SCORE = 10F;
	
	public static final int HOTPOINT_UNIT = 1;
	public static final int HOTPOINT_INIT = 1;
	public static final int HOTPOINT_THRESHOLD = 20;
}
