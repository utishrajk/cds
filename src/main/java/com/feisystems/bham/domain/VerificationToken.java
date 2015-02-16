package com.feisystems.bham.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "verification_token")
public class VerificationToken extends BaseEntity {

	private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; // 24 hours

	@Column(length = 36)
	private final String token;

	private Date expiryDate;

	@Enumerated(EnumType.STRING)
	private Type tokenType;

	private boolean verified;

	@ManyToOne
	@JoinColumn(name = "user_id")
	IndividualProvider user;

	public VerificationToken() {
		super();
		this.token = UUID.randomUUID().toString();
		this.expiryDate = calculateExpiryDate(DEFAULT_EXPIRY_TIME_IN_MINS);
	}

	public VerificationToken(IndividualProvider user, Type tokenType, int expirationTimeInMinutes) {
		this();
		this.user = user;
		this.tokenType = tokenType;
		this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
	}

	public Type getTokenType() {
		return tokenType;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public IndividualProvider getUser() {
		return user;
	}

	public void setUser(IndividualProvider user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public String getToken() {
		return token;
	}

	public enum Type {
		lostPassword, emailConfirmation, emailRegistration, lostPasswordAndSecurityQuestions, generic
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		DateTime now = new DateTime();
		return now.plusMinutes(expiryTimeInMinutes).toDate();
	}

	public boolean hasExpired() {
		DateTime tokenDate = new DateTime(getExpiryDate());
		return tokenDate.isBeforeNow();
	}

}
