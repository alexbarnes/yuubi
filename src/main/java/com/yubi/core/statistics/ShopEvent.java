package com.yubi.core.statistics;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ShopEvent {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String sessionId;
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ShopEventType type;
	
	private String entityKey;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public ShopEventType getType() {
		return type;
	}

	public void setType(ShopEventType type) {
		this.type = type;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}