/**
 * 文件名称 : HooMsgBean.java
 * <p>
 * 作者信息 : LIJUNJIE
 * <p>
 * 创建时间 : 2014年5月14日, 下午4:28:09
 * <p>
 * 版权声明 : Copyright (c) Hooray Ltd. All rights reserved
 * <p>
 **/

/**
 * 文件名称 : HooMsgBean.java
 * <p>
 * 作者信息 : LIJUNJIE
 * <p>
 * 创建时间 : 2014年5月14日, 下午4:28:09
 * <p>
 * 版权声明 : Copyright (c) Hooray Ltd. All rights reserved
 * <p>
 **/
package com.example.model;

import java.io.Serializable;

/**
 * @author LIJUNJIE
 * 
 */
public class HooMsgBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// -----------------------通过后台返回的属性---------------------------------

	// 消息Id，据说是16位顺序数字
	long msgId;
	// 接收用户Id
	String receiveId;
	// 发送用户Id
	String senderId;
	// 消息分组ID(绑定、导流、系统消息为10000)，其它则是发送者ID
	String groupId;
	// 消息类型
	// 1用户消息 2请求绑定消息 3推送消息 4导流消息 5系统消息 6绑定确认 7解除绑定确认
	int msgType;
	// 消息主题
	String subject;
	// 发送消息的用户昵称
	String senderName;
	// 发送消息的用户头像
	String senderLogo;
	// 消息内容
	String content;
	// 消息体类型
	// 1文本 2图片 3音频 4直播 5点播 6WEB 7导流APP 8导流直播 9导流点播 10导流视频
	int contentType;
	// 影片/频道名称
	String mediaName;
	// 可能是节目的频道id，推送节目用
	int mediaId;
	// 海报/台标
	String mediaPoster;
	// 发送时间
	String sendTime;
	// 导流时填写点击进去url或打开app代码 (扩展字段)
	String url;
	// 回看时传入开始时间(yyyy-MM-dd hh:mm:ss)
	String mediaStartTime;
	// 回看时结束时间(yyyy-MM-dd hh:mm:ss)
	String mediaEndTime;
	// 频道ID
	int channelId;
	// -----------------------自定义属性---------------------------------

	// 绑定请求的绑定状态：0,未处理 1,已通过 2,已拒绝
	int status;
	// 用于消息编辑选中
	private boolean isChoice = false;
	// 用于标识图片上传进度
	int progress;

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaPoster() {
		return mediaPoster;
	}

	public void setMediaPoster(String mediaPoster) {
		this.mediaPoster = mediaPoster;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderLogo() {
		return senderLogo;
	}

	public void setSenderLogo(String senderLogo) {
		this.senderLogo = senderLogo;
	}

	public boolean isChoice() {
		return isChoice;
	}

	public void setChoice(boolean isChoice) {
		this.isChoice = isChoice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMediaStartTime() {
		return mediaStartTime;
	}

	public void setMediaStartTime(String mediaStartTime) {
		this.mediaStartTime = mediaStartTime;
	}

	public String getMediaEndTime() {
		return mediaEndTime;
	}

	public void setMediaEndTime(String mediaEndTime) {
		this.mediaEndTime = mediaEndTime;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "HooMsgBean [msgId=" + msgId + ", receiveId=" + receiveId + ", senderId=" + senderId + ", groupId=" + groupId + ", msgType=" + msgType + ", subject=" + subject + ", senderName="
				+ senderName + ", senderLogo=" + senderLogo + ", content=" + content + ", contentType=" + contentType + ", mediaName=" + mediaName + ", mediaId=" + mediaId + ", mediaPoster="
				+ mediaPoster + ", sendTime=" + sendTime + ", url=" + url + ", mediaStartTime=" + mediaStartTime + ", mediaEndTime=" + mediaEndTime + ", channelId=" + channelId + ", status=" + status
				+ ", isChoice=" + isChoice + "]";
	}

	// public String getInsertSql() {
	// String escapeStr = (content != null) ? content.replace("'", "''") : "";// 单引号字符转义
	// String sql = MessageFormat.format(HooDBSql.HOO_SQL_MSG_INSERT, HooDBSql.HOO_DATABASE_MESSAGE, msgId + "", receiveId, senderId, msgType + "", subject, escapeStr, contentType + "", mediaName,
	// mediaPoster, sendTime, senderName, senderLogo, status + "", mediaId + "", groupId, mediaStartTime, mediaEndTime, url, channelId);
	// return sql;
	// }
	//
	// public String getDeleteSql() {
	// String sql = MessageFormat.format(HooDBSql.HOO_SQL_MSG_DELETE, HooDBSql.HOO_DATABASE_MESSAGE, msgId);
	// return sql;
	// }
	//
	// public String getUpdateSql() {
	// String escapeStr = (content != null) ? content.replace("'", "''") : "";// 单引号字符转义
	// String sql = MessageFormat.format(HooDBSql.HOO_SQL_MSG_UPDATE, HooDBSql.HOO_DATABASE_MESSAGE, receiveId, senderId, msgType + "", subject, escapeStr, contentType + "", mediaName, mediaPoster,
	// sendTime, senderName, senderLogo, status + "", mediaId + "", groupId, mediaStartTime, mediaEndTime, url, channelId, msgId + "");
	// return sql;
	// }
	//
	// @Override
	// public int compareTo(HooMsgBean another) {
	// if (another.getSendTime() != null && this.sendTime != null) {
	// return DateUtil.compareMsgTime(this.sendTime, another.getSendTime());
	// }
	// return 0;
	// }
}
