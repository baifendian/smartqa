package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfd.smartqa.dao.po.DiscussionPo;

public interface IDiscussionOperator {
	
	@Insert(("insert into smart_ods_discussion(caption,content,captionintag,name,creator) "
			+ "values(#{caption},#{content},#{captionintag},#{name},#{creator})"))
	public int insertChatroom(DiscussionPo disccusionPo);

	@Select("select * from smart_ods_discussion where Name = #{name}")
	public DiscussionPo getDiscussionByName(String name);
	
	@Update("update smart_ods_discussion set Content = #{content} where ID = #{id}")
	public void updateContent(DiscussionPo obj);
	
	@Update("update smart_ods_discussion set Status = 1 where ID = #{id}")
	public void updateStatus(DiscussionPo obj);
	
	//返回热门的讨论话题
	@Select("select * from smart_ods_discussion where Ishandle=1 order by Hot desc limit #{offset}")
	public List<DiscussionPo> getHotDiscussion(int offset);

	@Select("select * from smart_ods_discussion where Status = 1 order by Hot desc")
	public List<DiscussionPo> getAllDiscussion();
	
	@Insert(("insert into smart_ods_discussion_user(DiscussionID,UserID) "
			+ "values(#{1},#{2})"))
	public int insertRoomIdUid(String discussionID,String user_id);
}
