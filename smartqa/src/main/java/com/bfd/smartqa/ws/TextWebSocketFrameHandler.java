package com.bfd.smartqa.ws;

import org.apache.ibatis.annotations.Case;

import com.bfd.smartqa.App;
import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.biz.AuthAgent;
import com.bfd.smartqa.biz.Dispather;
import com.bfd.smartqa.entity.Input;
import com.bfd.smartqa.entity.MsgCode;
import com.bfd.smartqa.entity.Output;
import com.bfd.smartqa.util.JsonHelper;
import com.bfd.smartqa.util.UtilHelper;
import com.bfd.smartqa.ws.serverholder.ChannelPo;
import com.bfd.smartqa.ws.serverholder.Holder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 处理TextWebSocketFrame
 * 
 */
public class TextWebSocketFrameHandler extends
		SimpleChannelInboundHandler<TextWebSocketFrame> {

	private AuthAgent authBiz = new AuthAgent();
	private Dispather dispather = new Dispather();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			TextWebSocketFrame msg) throws Exception {
		String msgData = msg.text();
		GlobalHolder.getLogger().info("Server Recived:"+msgData);
		Input msgInput;
		try {
			msgInput = JsonHelper.paserInputJson(msgData);
		} catch (Exception ex) {
			GlobalHolder.getLogger().error("network param is wrong");
			Output res = new Output();
			res.setCode(MsgCode.Exception.getCode());
			res.setMsg("协议异常");
			ctx.channel().writeAndFlush(
					new TextWebSocketFrame(JsonHelper.paserOutputObj(res)));
			return;
		}
		// Auth process 考虑多线程如何处理
		boolean result = authBiz.check(ctx.channel(),msgInput);
		GlobalHolder.getLogger().info("authBiz check " + result);
		if (false == result) {
			Output res = new Output();
			res.setCode(MsgCode.NotAuth.getCode());
			res.setMsg("请求非法");
			ctx.channel().writeAndFlush(
					new TextWebSocketFrame(JsonHelper.paserOutputObj(res)));
			//ctx.channel().close();
			return;
		}
		// Dispather api网关
		this.dispather.process(msgInput, ctx.channel());
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		GlobalHolder.getLogger().info("Client:" + incoming.remoteAddress() + "加入");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		
		Channel incoming = ctx.channel();
		String channel_code = UtilHelper.hashCode(incoming);
		ChannelPo channelPo = Holder.getChannal(channel_code);
		String user_id = channelPo.getUser_id();
		Holder.removeChannel(channel_code);
		Holder.removeUidChannel(user_id, channel_code);
		
		GlobalHolder.getLogger().info("Client:" + incoming.remoteAddress() + "离开");
		incoming.close();

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "掉线");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) // (7)
			throws Exception {
		Channel incoming = ctx.channel();
		String channel_code = UtilHelper.hashCode(incoming);
		ChannelPo channelPo = Holder.getChannal(channel_code);
		String user_id = channelPo.getUser_id();
		Holder.removeChannel(channel_code);
		Holder.removeUidChannel(user_id, channel_code);
		
		GlobalHolder.getLogger().error("Client:" + incoming.remoteAddress() + "异常,错误信息："
				+cause.getMessage());
		ctx.close();
	}

}
