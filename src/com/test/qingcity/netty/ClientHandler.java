package com.test.qingcity.netty;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qingcity.client.MainFrame;
import com.qingcity.proto.Chat.S2C_Chat;
import com.qingcity.proto.PlayerInfo.S2C_GetPlayerInfo;
import com.qingcity.constants.ChatConstant;
import com.qingcity.constants.CmdConstant;
import com.test.qingcity.entity.ChatMessage;
import com.test.qingcity.entity.MsgEntity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Administrator
 * @Date 2017年3月25日 下午5:11:18
 * @Description TODO
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

	private MainFrame window = null;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		MsgEntity message = (MsgEntity) msg;
		short cmd = message.getCmdCode();
		try {
			//检测是否已经登录，保证window不为空
			if (cmd != CmdConstant.S2C_USER_LOGIN && window == null) {
				System.out.println("未登录");
				return;
			}
			//保存不同类型的数据
			switch (cmd) {
			case CmdConstant.S2C_USER_LOGIN:
				// 创建登录界面，设置玩家数据
				window = new MainFrame();
				window.setVisible(true);
				window.setLogin(message);
				break;
			case CmdConstant.S2C_USER_GET_BASE_INFO:
				S2C_GetPlayerInfo info = S2C_GetPlayerInfo.parseFrom(message.getData());
				System.out.println("玩家id" + message.getUserId());
				System.out.println("玩家昵称" + info.getPlayer().getNickname());
				System.out.println("玩家等级" + info.getPlayer().getLevel());
				System.out.println("玩家钻石" + info.getPlayer().getWealth().getDiamond());
				window.setBasePlayerInfo(info);
				window.setBaseInfo(info, message.getUserId());
				break;
			case ChatConstant.WORLD_MSG:
				S2C_Chat s2c_chat = S2C_Chat.parseFrom(message.getData());
				ChatMessage chat = new ChatMessage();
				chat.setContent(s2c_chat.getContent());
				chat.setAvatar(s2c_chat.getAvatar());
				chat.setNickname(s2c_chat.getNickname());
				chat.setId(message.getUserId());
				window.setChatMessage(chat);
				break;
			default:
				break;
			}
		} catch (InvalidProtocolBufferException e) {
			System.out.println(e.getMessage());
		}

	}
}
