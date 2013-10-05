package com.yubi.core.messaging;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity(name = "INT_MESSAGE")
@Table(indexes = {@Index(name="INT_MESSAGE_IX1", columnNames = {"CREATED_DATE"})}, appliesTo="INT_MESSAGE")
public class Message {

	@Id
	@Column(name = "MESSAGE_ID", unique = true, nullable = false, length = 36)
	private String messageId;

	@Column(name = "REGION", length = 100)
	private String region;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	private Date createdDate;

	@Lob
	@Column(name = "MESSAGE_BYTES")
	private byte[] messageBytes;

	
	
	
	
	// -- Getters and setters
	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public byte[] getMessageBytes() {
		return this.messageBytes;
	}

	public void setMessageBytes(byte[] messageBytes) {
		this.messageBytes = messageBytes;
	}

}
