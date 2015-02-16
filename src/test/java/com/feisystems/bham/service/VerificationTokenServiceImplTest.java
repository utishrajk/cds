package com.feisystems.bham.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.drools.util.codec.Base64;
import org.junit.Before;
import org.junit.Test;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.VerificationToken;
import com.feisystems.bham.domain.VerificationTokenRepository;
import com.feisystems.bham.exceptions.TokenHasExpiredException;
import com.feisystems.bham.infrastructure.ApplicationConfig;
import com.feisystems.bham.infrastructure.EmailServiceTokenModel;
import com.feisystems.bham.infrastructure.EmailServicesGateway;
import com.feisystems.bham.service.VerificationTokenService;
import com.feisystems.bham.service.VerificationTokenServiceImpl;

public class VerificationTokenServiceImplTest {

	private EmailServicesGateway emailServicesGateway;
	private IndividualProviderRepository userRepository;
	private VerificationTokenRepository tokenRepository;
	private List<String> tokens;
	private VerificationTokenService verificationTokenService;

	@Before
	public void setUp() {
		tokens = new ArrayList<String>();

		emailServicesGateway = new EmailServicesGateway() {
			@Override
			public void sendVerificationToken(EmailServiceTokenModel model) {
				tokens.add(model.getToken());
			}
		};

		userRepository = mock(IndividualProviderRepository.class);
		tokenRepository = mock(VerificationTokenRepository.class);
		ApplicationConfig config = mock(ApplicationConfig.class);
		verificationTokenService = new VerificationTokenServiceImpl(userRepository, emailServicesGateway, tokenRepository);

		((VerificationTokenServiceImpl) verificationTokenService).setConfig(config);
		when(config.getHostNameUrl()).thenReturn(new String("http://localhost:8080"));
		when(config.getTokenExpiryTime()).thenReturn(120);

	}

	@Test
	public void testSendEmailToken() {
		IndividualProvider user = generateTestIndividualProvider();
		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);

		VerificationToken token = verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.Type.lostPassword);
		assertThat(user.getVerificationTokens().size(), equalTo(1));
	}

	@Test
	public void testSendEmailTokenAgain() {
		IndividualProvider user = generateTestIndividualProvider();
		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);

		VerificationToken token1 = verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.Type.lostPassword);
		VerificationToken token2 = verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.Type.lostPassword);
		assertThat(token1, equalTo(token2));
		assertThat(user.getVerificationTokens().size(), equalTo(1));
		assertThat(tokens.size(), equalTo(2));

	}

	@Test
	public void testVerifyAValidToken() {
		IndividualProvider user = generateTestIndividualProvider();

		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
		VerificationToken token = verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.Type.emailRegistration);

		when(tokenRepository.findByToken(token.getToken())).thenReturn(token);
		String encodedToken = new String(Base64.encodeBase64(token.getToken().getBytes()));

		VerificationToken verifiedToken = verificationTokenService.verify(encodedToken);
		assertThat(verifiedToken.isVerified(), equalTo(true));
		assertThat(user.isVerified(), equalTo(true));
	}

	@Test(expected = TokenHasExpiredException.class)
	public void testTokenHasExpired() {
		IndividualProvider user = generateTestIndividualProvider();

		VerificationToken token = mock(VerificationToken.class);

		when(token.getToken()).thenReturn(UUID.randomUUID().toString());
		when(token.getUser()).thenReturn(user);
		when(token.hasExpired()).thenReturn(true);
		when(tokenRepository.findByToken(token.getToken())).thenReturn(token);
		String encodedToken = new String(Base64.encodeBase64(token.getToken().getBytes()));

		verificationTokenService.verify(encodedToken);
	}

	@Test
	public void testRetrieveUserForToken() {
		IndividualProvider user = generateTestIndividualProvider();

		VerificationToken token = mock(VerificationToken.class);

		when(token.getToken()).thenReturn(UUID.randomUUID().toString());
		when(token.getUser()).thenReturn(user);
		when(tokenRepository.findByToken(token.getToken())).thenReturn(token);
		String encodedToken = new String(Base64.encodeBase64(token.getToken().getBytes()));

		IndividualProvider retrievedUser = verificationTokenService.retrieveUser(encodedToken);

		assertThat(user.getUserName(), equalTo(retrievedUser.getUserName()));
	}

	private IndividualProvider generateTestIndividualProvider() {
		IndividualProvider user = new IndividualProvider();
		user.setFirstName("Tommy");
		user.setLastName("Ng");
		user.setUserName("tommy.ng@mailinator.com");
		return user;
	}

}
