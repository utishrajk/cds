package com.feisystems.bham.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.Credential;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.reference.SecurityQuestionsCode;
import com.feisystems.bham.domain.reference.SecurityQuestionsRepository;
import com.feisystems.bham.exceptions.ClinicalDataNotFoundException;
import com.feisystems.bham.exceptions.PasswordAlreadyUsedException;
import com.feisystems.bham.exceptions.UserNotFoundException;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.bham.security.SecurityUtils;

@Service
@Transactional
public class IndividualProviderServiceImpl implements IndividualProviderService {

	private final Logger log = LoggerFactory.getLogger(IndividualProviderServiceImpl.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Autowired
	IndividualProviderRepository individualProviderRepository;

	@Autowired
	SecurityQuestionsRepository securityQuestionsRepository;

	public static final String FAKE_ANSWER = "";
	
	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	/** The individualProvider profile dto to individualProvider mapper. */
	@Autowired
	private DtoToDomainEntityMapper<IndividualProviderDto, IndividualProvider> individualProviderDtoToIndividualProviderMapper;

	public IndividualProviderDto findIndividualProvider(Long id) {
		IndividualProvider individualProvider = individualProviderRepository.findOne(id);
		// Skipping Model Mapper due to Unnecessary Complexity
		if (individualProvider != null) {
			return convertObjectToDto(individualProvider);
		}
		throw new ClinicalDataNotFoundException(1L);
	}

	private IndividualProviderDto convertObjectToDto(IndividualProvider individualProvider) {
		IndividualProviderDto dto = new IndividualProviderDto();
		
		dto.setId(individualProvider.getId());
		dto.setStaffId(individualProvider.getStaffId());
		dto.setUserName(individualProvider.getUserName());
		dto.setOrgName(individualProvider.getOrgName());
		dto.setEmail(individualProvider.getEmail());
		dto.setFirstName(individualProvider.getFirstName());
		dto.setLastName(individualProvider.getLastName());
		dto.setMailingAddressTelephoneNumber(individualProvider.getMailingAddressTelephoneNumber());
		dto.setMailingAddressCountryCode(individualProvider.getMailingAddressCountryCode());
		dto.setDateOfBirth(individualProvider.getDateOfBirth());
		if (individualProvider.getQuestion1() != null) {
			dto.setSecurityQuestion1Code(individualProvider.getQuestion1().getCode());
		}
		if (individualProvider.getQuestion2() != null) {
			dto.setSecurityQuestion2Code(individualProvider.getQuestion2().getCode());
		}
		// Mocked Values as user would not be seeing the actuals anyway 
		dto.setCredential("");
		dto.setSecurityAnswer1(FAKE_ANSWER);
		dto.setSecurityAnswer2(FAKE_ANSWER);
		
		return dto;
	}
	
	public IndividualProviderDto findByLoggedInUsername() {
		IndividualProvider user = individualProviderRepository.findByUserName(retrieveUsername());
		return convertObjectToDto(user);
	}
	
	private String retrieveUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public IndividualProvider updateByLoggedInUser(IndividualProviderDto dto) {
		IndividualProvider user = individualProviderRepository.findByUserName(retrieveUsername());
		dto.setId(user.getId());
		user =  individualProviderDtoToIndividualProviderMapper.map(dto);
		return individualProviderRepository.save(user);
	}

	public IndividualProvider updateIndividualProvider(IndividualProviderDto individualProviderDto) {
		IndividualProvider individualProvider = individualProviderDtoToIndividualProviderMapper.map(individualProviderDto);
		if (individualProvider != null) {
			// TODO: Need to investigate
			// this.validatePassword(individualProviderDto.getCredential(),
			// individualProvider);
			return individualProviderRepository.save(individualProvider);
		}
		throw new ClinicalDataNotFoundException(individualProviderDto.getId());
	}

	/** {@inheritDoc} */
	public List<IndividualProviderDto> findAllIndividualProviders() {
		List<IndividualProviderDto> individualProviderProfileDtoList = new ArrayList<>();
		List<IndividualProvider> individualProviderList = individualProviderRepository.findAll();

		if (individualProviderList != null && individualProviderList.size() > 0) {
			for (IndividualProvider individualProvider : individualProviderList) {
				IndividualProviderDto individualProviderDto = modelMapper.map(individualProvider, IndividualProviderDto.class);
				individualProviderProfileDtoList.add(individualProviderDto);
			}
			return individualProviderProfileDtoList;
		}
		throw new IllegalArgumentException("No Care Manager Found");
	}

	public IndividualProvider findByUsername(String username) {
		IndividualProvider user = individualProviderRepository.findByUserName(username);
		return user;
	}

	public void deleteIndividualProvider(IndividualProvider individualProvider) {
		individualProviderRepository.delete(individualProvider);
	}

	public void saveIndividualProvider(IndividualProviderDto individualProviderDto) {
		IndividualProvider individualProvider = individualProviderDtoToIndividualProviderMapper.map(individualProviderDto);
		// Not required because it's only called by register so it will have no
		// password
		// this.validatePassword(individualProviderDto.getCredential(),
		// individualProvider);
		individualProviderRepository.save(individualProvider);
	}

	public void updateUserInformation(String firstName, String lastName, String email) {
		IndividualProvider currentUser = individualProviderRepository.findByUserName(SecurityUtils.getCurrentLogin());
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setEmail(email);
		individualProviderRepository.save(currentUser);
		log.debug("Changed Information for IndividualProvider: {}", currentUser);
	}

	public void changePassword(String password) {
		this.changePasswordForUser(password, SecurityUtils.getCurrentLogin());
	}

	public void changePasswordForUser(String rawPassword, String userName) {
		IndividualProvider individualProvider = individualProviderRepository.findByUserName(userName);
		this.validatePassword(rawPassword, individualProvider);

		Credential newCredential = new Credential();
		newCredential.setCreatedDate(new Date());
		newCredential.setPassword(passwordEncoder.encode(rawPassword));
		newCredential.setUser(individualProvider);
		individualProvider.addCredential(newCredential);

		individualProviderRepository.save(individualProvider);
		log.debug("Changed password for IndividualProvider: {}", individualProvider);
	}

	public void changeSecurityQuestions(IndividualProviderDto dto) {
		IndividualProvider individualProvider = individualProviderRepository.findByUserName(dto.getUserName());

		SecurityQuestionsCode code1 = securityQuestionsRepository.findByCode(dto.getSecurityQuestion1Code());
		SecurityQuestionsCode code2 = securityQuestionsRepository.findByCode(dto.getSecurityQuestion2Code());

		individualProvider.setQuestion1(code1);
		individualProvider.setQuestion2(code2);
		individualProvider.setAnswer1(passwordEncoder.encode(dto.getSecurityAnswer1()));
		individualProvider.setAnswer2(passwordEncoder.encode(dto.getSecurityAnswer2()));
		individualProviderRepository.save(individualProvider);
	}

	@Transactional(readOnly = true)
	public IndividualProvider getUserWithAuthorities() {
		IndividualProvider currentUser = individualProviderRepository.findByUserName(SecurityUtils.getCurrentLogin());
		currentUser.getAuthorities().size(); // eagerly load the association
		return currentUser;
	}

	@Override
	public IndividualProvider findByUsernameAndDateOfBirth(String userName, Date dateOfBirth) {
		IndividualProvider user = individualProviderRepository.findByUserNameAndDateOfBirth(userName, dateOfBirth);

		if (user == null) {
			throw new UserNotFoundException("Username is not valid");
		}

		return user;
	}

	private void validatePassword(String newPassword, IndividualProvider individualProvider) {
		if (newPassword != null && !newPassword.equals("")) {
			for (Credential oldCredential : individualProvider.getCredentials()) {
				if (compareStrings(newPassword, oldCredential.getPassword())) {
					throw new PasswordAlreadyUsedException("Password already used");
				}
			}
		}
	}

	private boolean compareStrings(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	@Override
	public IndividualProvider updateTrainingVideoChoice(IndividualProviderTrainingDto individualProviderTrainingDto) {
		IndividualProvider individualProvider = individualProviderRepository.findByUserName(retrieveUsername());
		
		if (individualProvider != null) {			
			individualProvider.setShowTrainingVideo(individualProviderTrainingDto.isShowTrainingVideo());
			return individualProviderRepository.save(individualProvider);
		}
		throw new ClinicalDataNotFoundException(individualProviderTrainingDto.getId());
	}


}
