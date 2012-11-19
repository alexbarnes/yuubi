package com.yubi.core.messaging;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "INT_MESSAGE_GROUP")
public class MessageGroup implements Serializable {

	private static final long serialVersionUID = -6046579557342395101L;

	@Id
	@Column(name = "MESSAGE_ID", unique = true, nullable = false, length = 36)
	private String messageId;

	@Id
	@Column(length = 36, nullable = false, name = "GROUP_KEY")
	private String groupKey;

	@Column(name = "REGION", length = 100)
	private String region;

	@Column(name = "MARKED")
	private Long marked;

	@Column(name = "COMPLETE")
	private Long complete;

	@Column(name = "LAST_RELEASED_SEQUENCE")
	private Long lastReleasedSequence;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", length = 19)
	private Date updatedDate;

	@Lob
	@Column(name = "MESSAGE_BYTES")
	private byte[] messageBytes;

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Long getMarked() {
		return this.marked;
	}

	public void setMarked(Long marked) {
		this.marked = marked;
	}

	public Long getComplete() {
		return this.complete;
	}

	public void setComplete(Long complete) {
		this.complete = complete;
	}

	public Long getLastReleasedSequence() {
		return this.lastReleasedSequence;
	}

	public void setLastReleasedSequence(Long lastReleasedSequence) {
		this.lastReleasedSequence = lastReleasedSequence;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public byte[] getMessageBytes() {
		return this.messageBytes;
	}

	public void setMessageBytes(byte[] messageBytes) {
		this.messageBytes = messageBytes;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

}
