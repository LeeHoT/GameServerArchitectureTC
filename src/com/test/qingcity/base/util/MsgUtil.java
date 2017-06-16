package com.test.qingcity.base.util;

import com.google.protobuf.GeneratedMessage;
import com.test.qingcity.base.entity.MsgEntity;

import io.netty.channel.Channel;

public class MsgUtil {
	

	/**
	 * 处理返回消息使用该。。 统一进行序列化
	 * 
	 * @param gm
	 * @param cmd
	 *            消息命令码
	 * @param response
	 *            response对象
	 */
	public static void handlerSendMsg(GeneratedMessage gm, Short cmd, int userId, Channel channel) {
		// synchronized (resEntity) {
		MsgEntity msgEntity = new MsgEntity();
		msgEntity.setMsgLength(gm.toByteArray().length);
		msgEntity.setCmdCode(cmd);
		msgEntity.setData(gm.toByteArray());
		msgEntity.setUserId(userId);
		channel.writeAndFlush(msgEntity);
	}

}
