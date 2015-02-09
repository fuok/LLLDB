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
import java.util.ArrayList;

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
	// 测试列表
	ArrayList<String> urlList;

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	// @Override
	// public String toString() {
	// return "HooMsgBean [msgId=" + msgId + ", receiveId=" + receiveId +
	// ", senderId=" + senderId + ", groupId=" + groupId + ", msgType=" +
	// msgType + ", subject=" + subject + ", senderName="
	// + senderName + ", senderLogo=" + senderLogo + ", content=" + content +
	// ", contentType=" + contentType + ", mediaName=" + mediaName +
	// ", mediaId=" + mediaId + ", mediaPoster="
	// + mediaPoster + ", sendTime=" + sendTime + ", url=" + url +
	// ", mediaStartTime=" + mediaStartTime + ", mediaEndTime=" + mediaEndTime +
	// ", channelId=" + channelId + ", status=" + status
	// + ", isChoice=" + isChoice + "]";
	// }

	// public String getInsertSql() {
	// String escapeStr = (content != null) ? content.replace("'", "''") : "";//
	// 单引号字符转义
	// String sql = MessageFormat.format(HooDBSql.HOO_SQL_MSG_INSERT,
	// HooDBSql.HOO_DATABASE_MESSAGE, msgId + "", receiveId, senderId, msgType +
	// "", subject, escapeStr, contentType + "", mediaName,
	// mediaPoster, sendTime, senderName, senderLogo, status + "", mediaId + "",
	// groupId, mediaStartTime, mediaEndTime, url, channelId);
	// return sql;
	// }
	//
	// public String getDeleteSql() {
	// String sql = MessageFormat.format(HooDBSql.HOO_SQL_MSG_DELETE,
	// HooDBSql.HOO_DATABASE_MESSAGE, msgId);
	// return sql;
	// }
	//
	// public String getUpdateSql() {
	// String escapeStr = (content != null) ? content.replace("'", "''") : "";//
	// 单引号字符转义
	// String sql = MessageFormat.format(HooDBSql.HOO_SQL_MSG_UPDATE,
	// HooDBSql.HOO_DATABASE_MESSAGE, receiveId, senderId, msgType + "",
	// subject, escapeStr, contentType + "", mediaName, mediaPoster,
	// sendTime, senderName, senderLogo, status + "", mediaId + "", groupId,
	// mediaStartTime, mediaEndTime, url, channelId, msgId + "");
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
