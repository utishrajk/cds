package com.feisystems.bham.service;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.VerificationToken;


public interface VerificationTokenService {
	
	public VerificationToken sendEmailToken(String userName, VerificationToken.Type type);
	
	public VerificationToken verify(String base64EncodedToken);
	
	public IndividualProvider retrieveUser(String base64EncodedToken);
	
}