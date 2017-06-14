package com.test.qingcity.entity;

import java.util.Date;

/**
 * 
 * @author leehotin
 * @Date 2017年5月8日 下午8:39:20
 * @Description TODO
 */
public class ChatMessage {

	private int id;
	private String nickname;
	private String avatar;
	private Long time;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Chat [nickname=" + nickname + ", avatar=" + avatar + ", time=" + new Date(time) + ", content=" + content
				+ "]";
	}

}
