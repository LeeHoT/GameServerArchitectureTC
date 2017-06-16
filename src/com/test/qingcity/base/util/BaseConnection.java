package com.test.qingcity.base.util;

import java.util.ArrayList;
import java.util.List;

import com.qingcity.base.constants.PortConstant;
import com.test.qingcity.base.netty.ClientHandler;
import com.test.qingcity.base.netty.NettyMsgDecoder;
import com.test.qingcity.base.netty.NettyMsgEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class BaseConnection {

	private static BaseConnection instance = null;

	public static BaseConnection getInstance() {
		if (instance == null) {
			instance = new BaseConnection();
		}
		return instance;
	}

	public List<Channel> channelList = new ArrayList<Channel>();
	public List<Channel> chatChannelList = new ArrayList<Channel>();

	private Channel channel;
	private static EventLoopGroup workerGroup = new NioEventLoopGroup();

	private static final int FIANL_DEFAULT_CLIENT = 1;
	private static final String FIANL_LOCAL_URL = "127.0.0.1";
	private static Bootstrap b;

	public void connectServer(int clientNum) {
		b = new Bootstrap();
		b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast("frameDecoder", new NettyMsgDecoder());
				ch.pipeline().addLast("frameEncoder", new NettyMsgEncoder());
				ch.pipeline().addLast("handler", new ClientHandler());
			}
		});

		if (clientNum <= 0) {
			clientNum = FIANL_DEFAULT_CLIENT;
		}
		for (int i = 0; i < clientNum; i++) {
			// ChannelFuture future = b.connect("118.89.233.59",
			// PortConstant.GAME_SERVER);
			ChannelFuture future = b.connect(FIANL_LOCAL_URL, PortConstant.GAME_SERVER);
			this.channelList.add(future.channel());
			channel = future.channel();
		}
		if(channel.isActive()){
			System.out.println("------------已成功和服务器连接------------");
		}else{
			System.out.println("------------和服务器连接失败------------");
		}
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public List<Channel> getChatChannelList() {
		return chatChannelList;
	}

	public void setChatChannelList(List<Channel> chatChannelList) {
		this.chatChannelList = chatChannelList;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
