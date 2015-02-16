package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.feisystems.bham.domain.Credential;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.reference.SecurityQuestionsCode;
import com.feisystems.bham.domain.reference.SecurityQuestionsRepository;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.bham.service.IndividualProviderDto;
import com.feisystems.bham.service.IndividualProviderService;
import com.feisystems.bham.service.IndividualProviderServiceImpl;
import com.feisystems.bham.service.IndividualProviderTrainingDto;

@RunWith(MockitoJUnitRunner.class)
public class IndividualProviderServiceImplTest {

	@InjectMocks
	private IndividualProviderService service = new IndividualProviderServiceImpl();

	@Mock
	private IndividualProviderRepository individualProviderRepository;

	@Mock
	private DtoToDomainEntityMapper<IndividualProviderDto, IndividualProvider> mapper;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private SecurityQuestionsRepository securityQuestionsRepository;

	@Test
	public void testFindInvidualProvider() {
		// Arrange
		final IndividualProvider user = mock(IndividualProvider.class);
		when(individualProviderRepository.findOne(anyLong())).thenReturn(user);

		// Act
		IndividualProviderDto dto = service.findIndividualProvider(1L);

		// Assert
		assertEquals(user.getId(), dto.getId());
	}

	@Test
	public void testUpdateIndividualProvider() {
		// Arrange
		final IndividualProviderDto dto = mock(IndividualProviderDto.class);
		final IndividualProvider user = mock(IndividualProvider.class);
		when(mapper.map(dto)).thenReturn(user);

		// Act
		service.updateIndividualProvider(dto);

		// Assert
		verify(individualProviderRepository, times(1)).save(user);
	}

	@Test
	public void testFindAllIndividualProviders() {
		// Arrange
		final IndividualProvider user1 = mock(IndividualProvider.class);
		final IndividualProvider user2 = mock(IndividualProvider.class);

		List<IndividualProvider> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);

		when(individualProviderRepository.findAll()).thenReturn(list);

		final IndividualProviderDto dto1 = mock(IndividualProviderDto.class);
		final IndividualProviderDto dto2 = mock(IndividualProviderDto.class);

		when(modelMapper.map(user1, IndividualProviderDto.class)).thenReturn(dto1);
		when(modelMapper.map(user2, IndividualProviderDto.class)).thenReturn(dto2);

		// Act
		List<IndividualProviderDto> users = service.findAllIndividualProviders();

		// Assert
		assertEquals(list.size(), users.size());
	}

	@Test
	public void testFindByUsername() {
		// Arrange
		final IndividualProvider user = mock(IndividualProvider.class);
		when(individualProviderRepository.findByUserName(anyString())).thenReturn(user);

		// Act
		IndividualProvider user2 = service.findByUsername("username");

		// Assert
		assertEquals(user.getId(), user2.getId());
	}

	@Test
	public void testDeleteIndividualProvider() {
		// Arrange
		final IndividualProvider user = mock(IndividualProvider.class);

		// Act
		service.deleteIndividualProvider(user);

		// Assert
		verify(individualProviderRepository, times(1)).delete(user);
	}

	@Test
	public void testSaveIndividualProvider() {
		// Arrange
		final IndividualProvider user = mock(IndividualProvider.class);
		final IndividualProviderDto dto = mock(IndividualProviderDto.class);
		when(mapper.map(dto)).thenReturn(user);

		// Act
		service.saveIndividualProvider(dto);

		// Assert
		verify(individualProviderRepository, times(1)).save(user);
	}

	@Test
	public void testUpdateUserInformation() {
		// issue with a static method call in a final class. Use PowerMock?
	}

	@Test
	public void testChangePassword() {
		// issue with a static method call in a final class. Use PowerMock?
	}

	@Test
	public void testChangePasswordForUser() {
		// Arrange
		final IndividualProvider user = mock(IndividualProvider.class);

		final Credential credential1 = mock(Credential.class);
		when(credential1.getPassword()).thenReturn("password1");
		final Credential credential2 = mock(Credential.class);
		when(credential2.getPassword()).thenReturn("password2");

		SortedSet<Credential> set = new TreeSet<>();
		set.add(credential1);
		set.add(credential2);
		when(user.getCredentials()).thenReturn(set);

		when(individualProviderRepository.findByUserName("username")).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		// Act
		service.changePasswordForUser("newPassword", "username");

		// Assert
		verify(individualProviderRepository, times(1)).save(user);
	}

	@Test
	public void testChangeSecurityQuestions() {
		// Arrange
		final IndividualProvider user = mock(IndividualProvider.class);
		when(individualProviderRepository.findByUserName(anyString())).thenReturn(user);

		final SecurityQuestionsCode code1 = mock(SecurityQuestionsCode.class);
		final SecurityQuestionsCode code2 = mock(SecurityQuestionsCode.class);

		when(securityQuestionsRepository.findByCode("question1")).thenReturn(code1);
		when(securityQuestionsRepository.findByCode("question2")).thenReturn(code2);

		when(passwordEncoder.encode("answer1")).thenReturn("encodedPassword1");
		when(passwordEncoder.encode("answer2")).thenReturn("encodedPassword2");

		final IndividualProviderDto dto = mock(IndividualProviderDto.class);
		when(dto.getSecurityQuestion1Code()).thenReturn("question1");
		when(dto.getSecurityQuestion2Code()).thenReturn("question2");
		when(dto.getSecurityAnswer1()).thenReturn("answer1");
		when(dto.getSecurityAnswer2()).thenReturn("answer2");

		// Act
		service.changeSecurityQuestions(dto);

		// Assert
		verify(individualProviderRepository, times(1)).save(user);
	}

	@Test
	public void testFindByUsernameAndDataOfBirth() {
		// Arrange
		final Date date = mock(Date.class);
		final IndividualProvider user = mock(IndividualProvider.class);
		when(individualProviderRepository.findByUserNameAndDateOfBirth(anyString(), Mockito.<Date> any())).thenReturn(user);

		// Act
		IndividualProvider user1 = service.findByUsernameAndDateOfBirth("username", date);

		// Assert
		assertEquals(user.getId(), user1.getId());
	}

//	@Test
//	public void testUpdateTrainingVideoChoice() {
//		// Arrange
//		final IndividualProvider user = mock(IndividualProvider.class);
//		when(individualProviderRepository.findByUserName(anyString())).thenReturn(user);
//		final IndividualProviderTrainingDto dto = mock(IndividualProviderTrainingDto.class);
//
//		// Act
//		IndividualProvider user1 = service.updateTrainingVideoChoice(dto);
//
//		// Assert
//		verify(individualProviderRepository, times(1)).save(user);
//	}
}
