package com.feisystems.bham.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivityDto {
	
	private String username;
	
	private String methodName;
	
	private String description;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	private Date timestamp;
	
	private String ipAddress;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
