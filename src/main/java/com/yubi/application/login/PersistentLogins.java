package com.yubi.application.core.login;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yubi.application.user.User;

@Entity(name = "persistent_logins")
public class PersistentLogins {
	
	@Id
	@Column(length = 64)
	private String series;
	
	@ManyToOne
	@JoinColumn(name = "userName")
	private User user;
	
	@Column(nullable = false, length = 64)
	private String token;
	
	@Column(name = "last_used")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUsed;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLast_used() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
}
