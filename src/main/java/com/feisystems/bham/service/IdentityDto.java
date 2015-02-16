package com.feisystems.bham.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IdentityDto {

	private String userName;

	private String credential;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone = "America/New_York")
	private Date dateOfBirth;

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
