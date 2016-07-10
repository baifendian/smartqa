package com.bfd.smartqa.biz;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.entity.Input;

import io.netty.channel.Channel;

public class Dispather {

	private Map<String, BizMapper> map = null;

	public Dispather() {
		this.map = new HashMap<String, BizMapper>();
		BaseBiz login = new LoginBiz();
		
		this.map.put("login", new BizMapper(login,"login"));
		
		BaseBiz chat = new ChatBiz();
		this.map.put("chat", new BizMapper(chat,"chat"));
		this.map.put("creat_chat_room", new BizMapper(chat,"createChatRoom"));
		this.map.put("close_chat_room", new BizMapper(chat,"closeChatRoom"));
		
		BaseBiz recommend = new RecommendBiz();
		this.map.put("recommenduser", new BizMapper(recommend,"recommendUser") );
		this.map.put("recommenddiscussion", new BizMapper(recommend,"recommendDiscussion") );

		BaseBiz discussion = new DiscussionBiz();
		this.map.put("refreshhotlist", new BizMapper(discussion,"refreshHotList") );
		
		
	}

	public void process(Input input, Channel incoming) {
		try {
			
			Object[] args = new Object[3];
			args[0] = incoming;
			args[1] = input.getToken();
			args[2] = input.getData();
			GlobalHolder.getLogger().info("dispather process, token:"+args[1] +"data"+input.getData());
			//invokeMethod(this.map.get(input.getMethod()), input.getMethod(),
			//		args);
			BizMapper bizMapper = this.map.get(input.getMethod());
			invokeMethod(bizMapper.getBiz(),bizMapper.getMethod(),args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void invokeMethod(Object owner, String methodName, Object[] args)
			throws Exception {
		Class<? extends Object> ownerClass = owner.getClass();
		Class<? extends Object>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		method.invoke(owner, args);
	}
}
