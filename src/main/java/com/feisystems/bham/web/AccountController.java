package com.feisystems.bham.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feisystems.bham.domain.Authority;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.VerificationToken;
import com.feisystems.bham.exceptions.AlreadyVerifiedException;
import com.feisystems.bham.exceptions.CaptchaException;
import com.feisystems.bham.exceptions.ReAuthenticationException;
import com.feisystems.bham.exceptions.TokenHasExpiredException;
import com.feisystems.bham.exceptions.TokenNotFoundException;
import com.feisystems.bham.exceptions.UserNotFoundException;
import com.feisystems.bham.infrastructure.ApplicationConfig;
import com.feisystems.bham.service.ApplicationDto;
import com.feisystems.bham.service.IdentityDto;
import com.feisystems.bham.service.IndividualProviderDto;
import com.feisystems.bham.service.IndividualProviderService;
import com.feisystems.bham.service.SecurityQuestionsDto;
import com.feisystems.bham.service.VerificationTokenService;
import com.feisystems.bham.util.CommonUtility;
import com.feisystems.bham.util.Constant;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/app")
public class AccountController {

	private final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private MessageSource messageSource;

	@Inject
	private IndividualProviderService individualProviderService;

	@Autowired
	VerificationTokenService verificationTokenService;

	@Autowired
	ApplicationConfig config;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * GET /rest/authenticate -> check if the user is authenticated, and return
	 * its login.
	 */
	@RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = Constant.APPLICATION_JSON)
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	/**
	 * GET /rest/account -> get the current user.
	 */
	@RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = Constant.APPLICATION_JSON)
	public IndividualProviderDto getAccount(HttpServletResponse response) {
		IndividualProvider individualProvider = individualProviderService.getUserWithAuthorities();	
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8 );

		if (individualProvider == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		//List<String> roles = individualProvider.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
		
		List<String> roles = new ArrayList<>();
		
		Set<Authority> authorities = individualProvider.getAuthorities();
		
		for(Authority authority : authorities) {
			roles.add(authority.getName());
		}
		
		IndividualProviderDto individualProviderDto = new IndividualProviderDto(individualProvider.getId(), individualProvider.getUserName(), individualProvider.getFirstName(),
				individualProvider.getLastName(), individualProvider.getEmail(), roles);
		individualProviderDto.setShowTrainingVideo(individualProvider.getShowTrainingVideo());
		
		return individualProviderDto;
	}

	/**
	 * POST /rest/account -> update the current user information.
	 */
	@RequestMapping(value = "/rest/account", method = RequestMethod.POST, produces = Constant.APPLICATION_JSON)
	public void saveAccount(@RequestBody IndividualProviderDto individualProviderDto) throws IOException {
		individualProviderService.updateUserInformation(individualProviderDto.getFirstName(), individualProviderDto.getLastName(), individualProviderDto.getEmail());
	}

	/**
	 * POST /rest/change_password -> changes the current user's password
	 */
	@RequestMapping(value = "/rest/account/change_password", method = RequestMethod.POST, produces = Constant.APPLICATION_JSON)
	public void changePassword(@RequestBody String password, HttpServletResponse response) throws IOException {
		if (password == null || password.equals("")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Password should not be empty");
		} else {
			individualProviderService.changePassword(password);
		}
	}

	/**
	 * GET sends back a json error message if the user is not authorized
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET, produces = Constant.APPLICATION_JSON)
	public ErrorInfo accessDeniedMessageHandler(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage("error.bad.authorization", null, locale);
		String errorURL = request.getRequestURL().toString();

		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@RequestMapping(value = "/public/identifyuser", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> identifyUser(@RequestBody IdentityDto dto) {
		IndividualProvider user = individualProviderService.findByUsernameAndDateOfBirth(dto.getUserName(), dto.getDateOfBirth());
		if (user == null) {
			throw new UserNotFoundException("Username is not valid");
		}
		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/public/verifyIdentity", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> verifyIdentity(@RequestBody IdentityDto dto) {
		HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;

		IndividualProvider user = individualProviderService.findByUsername(dto.getUserName());
		if (user != null && dto.getUserName() != null && dto.getUserName().equals(user.getEmail()) && compareStrings(dto.getCredential(), user.getCredential())) {
			httpStatus = HttpStatus.OK;
		} else {
			throw new ReAuthenticationException("Invalid Credentials");
		}
		return new ResponseEntity<String>(CommonUtility.getHeaders(), httpStatus);
	}

	@RequestMapping(value = "/public/verifyAnswers", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> verifyAnswers(@RequestBody IndividualProviderDto dto) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;

		IndividualProvider user = individualProviderService.findByUsername(dto.getUserName());

		if (compareStrings(dto.getSecurityAnswer1(), user.getAnswer1()) && compareStrings(dto.getSecurityAnswer2(), user.getAnswer2())) {
			httpStatus = HttpStatus.OK;

			verificationTokenService.sendEmailToken(dto.getUserName(), VerificationToken.Type.lostPassword);
		}

		return new ResponseEntity<String>(CommonUtility.getHeaders(), httpStatus);
	}

	@RequestMapping(value = "/public/sendVerificationEmail", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> sendVerificationEmail(@RequestBody IndividualProviderDto dto) {
		verificationTokenService.sendEmailToken(dto.getUserName(), VerificationToken.Type.lostPasswordAndSecurityQuestions);

		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.OK);
	}

	private boolean compareStrings(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@RequestMapping(value = "/public/retrieveSecurityQuestions/{userName}/{dateOfBirth}", method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
	public SecurityQuestionsDto retrieveSecurityQuestions(@PathVariable("userName") String userName, @PathVariable("dateOfBirth") String dateOfBirth) {
		// TODO: Also use dob
		IndividualProvider user = individualProviderService.findByUsername(userName);
		if (user == null) {
			throw new UserNotFoundException("Username is not valid");
		}

		SecurityQuestionsDto dto = new SecurityQuestionsDto();
		dto.setQuestion1(user.getQuestion1().getDisplayName());
		dto.setQuestion2(user.getQuestion2().getDisplayName());

		return dto;
	}

	@RequestMapping(value = "/public/register", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> createFromJson(HttpServletRequest request, @RequestBody IndividualProviderDto dto) {

		// Captcha will only work in production
		if (!config.getHostNameUrl().contains("localhost") && !config.getHostNameUrl().contains("bham")) {
			String remoteAddr = request.getRemoteAddr();

			ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
			reCaptcha.setPrivateKey(messageSource.getMessage("captcha.private.key", null, CommonUtility.getLocale()));

			String challenge = dto.getCaptcha().getChallenge();
			String uresponse = dto.getCaptcha().getResponse();

			ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

			if (!reCaptchaResponse.isValid()) {
				throw new CaptchaException("Captcha response is not valid");
			}
		}

		if (!dto.getCredential().equals(dto.getConfirmPassword()) || dto.getSecurityQuestion1Code().equals(dto.getSecurityQuestion2Code())) {
			return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.FORBIDDEN);
		}

		individualProviderService.saveIndividualProvider(dto);
		IndividualProvider user = individualProviderService.findByUsername(dto.getEmail());
		if (user == null) {
			throw new UserNotFoundException("Username is not valid");
		}

		verificationTokenService.sendEmailToken(dto.getEmail(), VerificationToken.Type.emailRegistration);
		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/public/verify/{base64EncodedToken}")
	public void verifyUser(HttpServletResponse httpServletResponse, @PathVariable String base64EncodedToken) throws IOException {
		try {
			VerificationToken token = verificationTokenService.verify(base64EncodedToken);
			if (token != null && token.isVerified()) {
				// happy path..
				httpServletResponse.sendRedirect(config.getLogin() + "?registrationMessage=verified");
			}
		} catch (TokenNotFoundException e) {
			httpServletResponse.sendRedirect(config.getLogin() + "?registrationMessage=tokenNotFound");

		} catch (TokenHasExpiredException e) {
			httpServletResponse.sendRedirect(config.getLogin() + "?registrationMessage=tokenHasExpired");

		} catch (AlreadyVerifiedException e) {
			httpServletResponse.sendRedirect(config.getLogin());

		}

	}

	@RequestMapping(value = "/public/resetpassword", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> resetPassword(@RequestBody IndividualProviderDto dto) {

		if (!dto.getCredential().equals(dto.getConfirmPassword())) {
			return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.FORBIDDEN);
		}

		IndividualProvider user = verificationTokenService.retrieveUser(dto.getToken());

		individualProviderService.changePasswordForUser(dto.getCredential(), user.getUserName());
		verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.Type.emailConfirmation);
		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/public/resetpasswordandsecurityquestions", method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
	public ResponseEntity<String> resetPasswordAndSecurityQuestions(@RequestBody IndividualProviderDto dto) {

		if (!dto.getCredential().equals(dto.getConfirmPassword()) || dto.getSecurityQuestion1Code().equals(dto.getSecurityQuestion2Code())) {
			return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.FORBIDDEN);
		}

		IndividualProvider user = verificationTokenService.retrieveUser(dto.getToken());

		individualProviderService.changePasswordForUser(dto.getCredential(), user.getUserName());
		dto.setUserName(user.getUserName());
		individualProviderService.changeSecurityQuestions(dto);
		verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.Type.emailConfirmation);
		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/public/environment", method = RequestMethod.GET)
	public ApplicationDto getEnvironment() {
		ApplicationDto dto = new ApplicationDto();
		dto.setEnvironment(config.getHostNameUrl());
		log.debug("The database being used is : " + config.getHostNameUrl());
		return dto;
	}

	
}
