package com.yubi.application.admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopStatus {

	@Id
	@GeneratedValue
	private long id;
	private boolean open;
	private String closedMessage;

	public String getClosedMessage() {
		return closedMessage;
	}

	public void setClosedMessage(String closedMessage) {
		this.closedMessage = closedMessage;
	}

	public long getId() {
		return id;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
