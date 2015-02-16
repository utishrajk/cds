package com.feisystems.bham.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Access implements Serializable {
	
	private static final long serialVersionUID = 32437479345277L;
	
 	@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long accessId;
	
	@NotNull
	@Column
	private String username;

	@NotNull
	@Column
    @Temporal(TemporalType.TIMESTAMP)	
    private Date timestamp; 

	@Column
    private String description;

	@Column
	private String host;
	
	@Column
	private String path;
	


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
}
