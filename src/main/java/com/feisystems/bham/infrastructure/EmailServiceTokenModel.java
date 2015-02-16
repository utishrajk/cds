package com.feisystems.bham.infrastructure;

import java.io.Serializable;
import java.text.DateFormat;

import org.apache.commons.codec.binary.Base64;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.VerificationToken;

public class EmailServiceTokenModel implements Serializable {

	private final String emailAddress;
	private final String token;
	private final VerificationToken.Type tokenType;
	private final String hostNameUrl;
	private final String fullName;
	private final String tokenExpiryDate;

	public EmailServiceTokenModel(IndividualProvider user, VerificationToken token, String hostNameUrl) {
		this.fullName = changeFirstCharToUpper(user.getFirstName()) + " " + changeFirstCharToUpper(user.getLastName());
		this.emailAddress = user.getEmail();
		this.token = token.getToken();
		this.tokenType = token.getTokenType();
		this.hostNameUrl = hostNameUrl;
		this.tokenExpiryDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(token.getExpiryDate());
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getEncodedToken() {
		return new String(Base64.encodeBase64(token.getBytes()));
	}

	public String getToken() {
		return token;
	}

	public VerificationToken.Type getTokenType() {
		return tokenType;
	}

	public String getHostNameUrl() {
		return hostNameUrl;
	}

	public String getFullName() {
		return fullName;
	}

	public String getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	private static String changeFirstCharToUpper(String inputString) {
		return Character.toUpperCase(inputString.charAt(0)) + inputString.substring(1);
	}

}
