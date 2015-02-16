package com.feisystems.bham.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_attempt")
public class LoginAttempt {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long attemptId;

	private int attempts;

	private Date lastModified;

	private String username;

	public Long getAttemptId() {
		return attemptId;
	}

	public void setAttemptId(Long attemptId) {
		this.attemptId = attemptId;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
