package com.feisystems.bham.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class AuditRecommendations {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long auditRecommendationId;

	@NotNull
	@Column
	private String requestId;

	@Lob
	private String recommendationXML;

	@NotNull
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@ManyToOne
	@JoinColumn(name = "gpra")
	Gpra gpra;

	@ManyToOne
	@JoinColumn(name = "asi")
	Asi asi;
	
	private String username;

	public Long getAuditRecommendationId() {
		return auditRecommendationId;
	}

	public void setAuditRecommendationId(Long auditRecommendationId) {
		this.auditRecommendationId = auditRecommendationId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRecommendationXML() {
		return recommendationXML;
	}

	public void setRecommendationXML(String recommendationXML) {
		this.recommendationXML = recommendationXML;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Gpra getGpra() {
		return gpra;
	}

	public void setGpra(Gpra gpra) {
		this.gpra = gpra;
	}

	public Asi getAsi() {
		return asi;
	}

	public void setAsi(Asi asi) {
		this.asi = asi;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
