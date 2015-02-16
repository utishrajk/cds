package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.bham.domain.reference.SecurityQuestionsCode;
import com.feisystems.bham.domain.reference.SecurityQuestionsRepository;
import com.feisystems.bham.domain.reference.StateCodeRepository;
import com.feisystems.bham.service.IndividualProviderDto;
import com.feisystems.bham.service.IndividualProviderDtoToIndividualProviderMapper;

@RunWith(MockitoJUnitRunner.class)
public class IndividualProviderDtoToIndividualProviderMapperTest {
	
	@InjectMocks
	private IndividualProviderDtoToIndividualProviderMapper mapper;
	
	@Mock
	private IndividualProviderRepository individualProviderRepository;
	
	@Mock
	private StateCodeRepository stateCodeRepository;
	
	@Mock
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;
	
	@Mock
	private SecurityQuestionsRepository securityQuestionsRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Before
	public void setUp() {
		//Not sure why @Mock for passwordEncoder is not instantiating the dependency and injecting into mapper (like other dependencies)
		mapper.setPasswordEncoder(passwordEncoder);
	}
	
	@Test
	public void testMapForCreate() {
		// Arrange
		IndividualProviderDto dto = mock(IndividualProviderDto.class);
		IndividualProvider user = mock(IndividualProvider.class);
		
		when(dto.getEmail()).thenReturn("tommy.ng@mailinator.com");
		
		when(individualProviderRepository.findByUserName(anyString())).thenReturn(user);
		
		SecurityQuestionsCode code1 = mock(SecurityQuestionsCode.class);
		when(dto.getSecurityQuestion1Code()).thenReturn("question1");
		when(securityQuestionsRepository.findByCode("question1")).thenReturn(code1);
		
		SecurityQuestionsCode code2 = mock(SecurityQuestionsCode.class);
		when(dto.getSecurityQuestion2Code()).thenReturn("question2");
		when(securityQuestionsRepository.findByCode("question2")).thenReturn(code2);
		
		when(dto.getSecurityAnswer1()).thenReturn("answer1");
		when(dto.getSecurityAnswer2()).thenReturn("answer2");
		
		when(dto.getCredential()).thenReturn("credential");
		when(passwordEncoder.encode("credential")).thenReturn("encodedpassword");
		
		AdministrativeGenderCode admCode = mock(AdministrativeGenderCode.class);
		when(administrativeGenderCodeRepository.findByCode("admCode")).thenReturn(admCode);
		when(dto.getAdministrativeGenderCode()).thenReturn("admCode");
		
		
		// Act
		IndividualProvider user1 = mapper.map(dto);
		
		// Assert
		assertEquals(dto.getEmail(), user1.getUserName());
	}

}
