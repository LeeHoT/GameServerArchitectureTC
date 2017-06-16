package com.test.qingcity.base.task;

import com.qingcity.base.util.ExecutorServiceUtil;
import com.test.qingcity.base.util.BaseConnection;


public class CheckChannelStatusTask implements Runnable {

	public static final Long RUNNING = 10000L;//10秒轮询一次。。

	@Override
	public void run() {
		
		if(!BaseConnection.getInstance().getChannel().isActive()){
			System.out.println("==============>: 您已经离线");
			ExecutorServiceUtil.stop();
			
		}
	}	
	
}
