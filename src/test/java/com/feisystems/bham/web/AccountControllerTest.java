package com.feisystems.bham.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.feisystems.bham.domain.Credential;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderTestContext;
import com.feisystems.bham.domain.VerificationToken;
import com.feisystems.bham.domain.reference.SecurityQuestionsCode;
import com.feisystems.bham.infrastructure.ApplicationConfig;
import com.feisystems.bham.service.IdentityDto;
import com.feisystems.bham.service.IndividualProviderDto;
import com.feisystems.bham.service.IndividualProviderService;
import com.feisystems.bham.service.VerificationTokenService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IndividualProviderTestContext.class})
@WebAppConfiguration
public class AccountControllerTest {

	@Autowired
	IndividualProviderService individualProviderService;
	
	@Autowired
	VerificationTokenService verificationTokenService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ApplicationConfig config;
	
	MockMvc mockMvc;

	@Resource
	private WebApplicationContext context;
	
	@Mock
	HttpServletRequest httpServletRequest;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testList() throws Exception {
		when(config.getHostNameUrl()).thenReturn("HostURL");
		mockMvc.perform(get("/app/public/environment")).andExpect(status().isOk());
	}

	@Test
	public void testIdentifyUser() throws Exception {
		when(individualProviderService.findByUsernameAndDateOfBirth(any(String.class), any(Date.class))).thenReturn(new IndividualProvider());
		
		IdentityDto dto = new IdentityDto();
		dto.setUserName("tommy@mailinator.com");
		dto.setDateOfBirth(new Date());
		mockMvc.perform(post("/app/public/identifyuser").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isOk());
	}
	
	@Test
	public void testVerifyIdentity() throws Exception {
		IndividualProvider user = new IndividualProvider();
		user.setUserName("tommy@mailinator.com");
		user.setEmail(user.getUserName());
		
		Credential c = new Credential();
		c.setPassword("P@ssword1234");
		c.setCreatedDate(new Date());
		user.addCredential(c);
		when(individualProviderService.findByUsername(any(String.class))).thenReturn(user);
		when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
		
		IdentityDto dto = new IdentityDto();
		dto.setCredential("P@ssword1234");
		dto.setUserName("tommy@mailinator.com");
		
		mockMvc.perform(post("/app/public/verifyIdentity").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isOk());
	}
	
	@Test
	public void testVerifyAnswers() throws Exception {
		IndividualProvider user = new IndividualProvider();
		user.setUserName("tommy@mailinator.com");
		VerificationToken dummyToken = new VerificationToken();
		
		when(individualProviderService.findByUsername(any(String.class))).thenReturn(user);
		when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
		when(verificationTokenService.sendEmailToken(any(String.class), any(VerificationToken.Type.class))).thenReturn(dummyToken);
		
		IndividualProviderDto dto = new IndividualProviderDto();
		mockMvc.perform(post("/app/public/verifyAnswers").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isOk());
	}
	
	@Test
	public void testSendVerificationEmail() throws Exception {
		VerificationToken dummyToken = new VerificationToken();
		
		when(verificationTokenService.sendEmailToken(any(String.class), any(VerificationToken.Type.class))).thenReturn(dummyToken);
		
		IndividualProviderDto dto = new IndividualProviderDto();
		mockMvc.perform(post("/app/public/sendVerificationEmail").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isOk());
	}
	
	@Test
	public void testRetrieveSecurityQuestions() throws Exception {
		IndividualProvider user = new IndividualProvider();
		user.setEmail("tommy@mailinator.com");
		SecurityQuestionsCode code1 = new SecurityQuestionsCode();
		code1.setDisplayName("Question1");
		user.setQuestion1(code1);
		SecurityQuestionsCode code2 = new SecurityQuestionsCode();
		code2.setDisplayName("Question2");
		user.setQuestion2(code2);
		
		when(individualProviderService.findByUsername(any(String.class))).thenReturn(user);
		
		mockMvc.perform(get("/app/public/retrieveSecurityQuestions/tommy@mailinator.com/dob")).andExpect(status().isOk()).andExpect(jsonPath("question1", is("Question1"))); 
	}
	
	@Test
	public void testCreateFromJson() throws Exception {
		IndividualProviderDto dto = new IndividualProviderDto();
		dto.setEmail("tommy@mailinator.com");
		dto.setCredential("P@ssword1234");
		dto.setConfirmPassword("P@ssword1234");
		dto.setDateOfBirth(new Date());
		dto.setSecurityQuestion1Code("1");
		dto.setSecurityAnswer1("kkk");
		dto.setSecurityQuestion2Code("2");
		dto.setSecurityAnswer2("kkk");
		dto.setAcceptTermsOfUse(true);
		VerificationToken dummyToken = new VerificationToken();
		IndividualProvider user = new IndividualProvider();
		user.setId(1L);
		
		when(config.getHostNameUrl()).thenReturn("localhost");
		when(individualProviderService.findByUsername(any(String.class))).thenReturn(user);
		when(verificationTokenService.sendEmailToken(any(String.class), any(VerificationToken.Type.class))).thenReturn(dummyToken);
		
		mockMvc.perform(post("/app/public/register").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isCreated());
	}
	
	@Test
	public void testVerifyUser() throws Exception {
		VerificationToken dummyToken = new VerificationToken();
		dummyToken.setVerified(true);
		
		when(verificationTokenService.verify(any(String.class))).thenReturn(dummyToken);
		when(config.getLogin()).thenReturn("bham/#/login");
		
		mockMvc.perform(get("/app/public/verify/encodedString")).andExpect(redirectedUrl("bham/#/login?registrationMessage=verified"));
	}
	
	@Test
	public void resetPassword() throws Exception {
		IndividualProviderDto dto = new IndividualProviderDto();
		dto.setCredential("P@ssword1234");
		dto.setConfirmPassword("P@ssword1234");
		
		VerificationToken dummyToken = new VerificationToken();
		
		IndividualProvider user = new IndividualProvider();
		when(verificationTokenService.retrieveUser(any(String.class))).thenReturn(user);
		when(verificationTokenService.sendEmailToken(any(String.class), any(VerificationToken.Type.class))).thenReturn(dummyToken);
		
		mockMvc.perform(post("/app/public/resetpassword").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isOk());
	}
	
	@Test
	public void resetPasswordAndSecurityQuestions() throws Exception {
		IndividualProviderDto dto = new IndividualProviderDto();
		
		dto.setCredential("P@ssword1234");
		dto.setConfirmPassword("P@ssword1234");
		dto.setSecurityQuestion1Code("1");
		dto.setSecurityQuestion2Code("2");
		
		VerificationToken dummyToken = new VerificationToken();
		
		IndividualProvider user = new IndividualProvider();
		when(verificationTokenService.retrieveUser(any(String.class))).thenReturn(user);
		when(verificationTokenService.sendEmailToken(any(String.class), any(VerificationToken.Type.class))).thenReturn(dummyToken);
		
		mockMvc.perform(post("/app/public/resetpasswordandsecurityquestions").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isOk());
	}

}
