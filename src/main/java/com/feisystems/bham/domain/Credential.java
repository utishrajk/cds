package com.feisystems.bham.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "credential")
public class Credential implements Serializable, Comparable<Credential> {

	private static final long serialVersionUID = 1205828237939434173L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long credentialId;

	@NotNull
	@Size(min = 12)	
	String password;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	IndividualProvider user;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public IndividualProvider getUser() {
		return user;
	}

	public void setUser(IndividualProvider user) {
		this.user = user;
	}

	public static class CredentialComparator implements Comparator<Credential> {
		public int compare(Credential c1, Credential c2) {
			return c1.getCreatedDate().compareTo(c2.getCreatedDate());
		}
	}

	@Override
	public int compareTo(Credential credential) {
		return this.getCreatedDate().compareTo(credential.getCreatedDate());
	};

}
