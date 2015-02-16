package com.feisystems.bham.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.bham.domain.Authority;
import com.feisystems.bham.domain.Credential;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.bham.domain.reference.SecurityQuestionsCode;
import com.feisystems.bham.domain.reference.SecurityQuestionsRepository;
import com.feisystems.bham.domain.reference.StateCodeRepository;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.bham.security.AuthoritiesConstants;

/**
 * The Class IndividualProviderDtoToIndividualProviderMapper.
 */
@Component
public class IndividualProviderDtoToIndividualProviderMapper implements DtoToDomainEntityMapper<IndividualProviderDto, IndividualProvider> {

	/** The individualProvider repository. */
	private IndividualProviderRepository individualProviderRepository;

	/** The administrative gender code repository. */
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	private SecurityQuestionsRepository securityQuestionsRepository;

	public static final String FAKE_ANSWER = "";

	private PasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new individualProvider dto to individualProvider mapper.
	 *
	 * @param individualProviderRepository
	 *            the individualProvider repository
	 * @param stateCodeRepository
	 *            the state code repository
	 * @param countryCodeRepository
	 *            the country code repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 */
	@Autowired
	public IndividualProviderDtoToIndividualProviderMapper(IndividualProviderRepository individualProviderRepository,
			StateCodeRepository stateCodeRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			SecurityQuestionsRepository securityQuestionsRepository) {

		super();

		this.individualProviderRepository = individualProviderRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.securityQuestionsRepository = securityQuestionsRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.feisystems.bham.infrastructure.DtoToDomainEntityMapper#map(java.lang
	 * .Object)
	 */
	@Override
	public IndividualProvider map(IndividualProviderDto individualProviderDto) {
		IndividualProvider individualProvider = null;

		if (individualProviderDto.getUserName() != null) {
			individualProvider = individualProviderRepository.findByUserName(individualProviderDto.getUserName());
		} else {
			individualProvider = new IndividualProvider();
		}

		// Return null if no IndividualProvider found
		if (individualProvider == null) {
			return null;
		}

		individualProvider.setStaffId(individualProviderDto.getStaffId());

		individualProvider.setUserName(individualProviderDto.getUserName());

		// for Create
		if (individualProvider.getUserName() == null) {
			individualProvider.setUserName(individualProviderDto.getEmail());
			Authority authority = new Authority();
			authority.setName(AuthoritiesConstants.ROLE_USER);

			Set<Authority> set = new HashSet<>();
			set.add(authority);

			individualProvider.setAuthorities(set);
			individualProvider.setAccountNonLocked(true);
		}

		// Begin: Security Q and A
		SecurityQuestionsCode code1 = securityQuestionsRepository.findByCode(individualProviderDto.getSecurityQuestion1Code());
		SecurityQuestionsCode code2 = securityQuestionsRepository.findByCode(individualProviderDto.getSecurityQuestion2Code());

		String answer1 = individualProviderDto.getSecurityAnswer1();
		String answer2 = individualProviderDto.getSecurityAnswer2();

		// Persist security code and answers only when a valid answer is chosen
		if (answer1 != null && !answer1.equals("")) {
			individualProvider.setQuestion1(code1);
			individualProvider.setAnswer1(passwordEncoder.encode(answer1));
		}

		if (answer2 != null && !answer2.equals("")) {
			individualProvider.setQuestion2(code2);
			individualProvider.setAnswer2(passwordEncoder.encode(answer2));
		}

		// End: Security Q and A

		String credential = individualProviderDto.getCredential();
		if (credential != null && !credential.equals("")) {
			Credential newCredential = new Credential();
			newCredential.setCreatedDate(new Date());
			newCredential.setPassword(passwordEncoder.encode(individualProviderDto.getCredential()));
			newCredential.setUser(individualProvider);
			individualProvider.addCredential(newCredential);
		}
		individualProvider.setFirstName(individualProviderDto.getFirstName());
		individualProvider.setLastName(individualProviderDto.getLastName());
		individualProvider.setDateOfBirth(individualProviderDto.getDateOfBirth());
		individualProvider.setEmail(individualProviderDto.getEmail());
		individualProvider.setOrgName(individualProviderDto.getOrgName());
		individualProvider.setNamePrefixCode(individualProviderDto.getNamePrefixCode());
		individualProvider.setMiddleName(individualProviderDto.getMiddleName());
		individualProvider.setNameSuffix(individualProviderDto.getNameSuffix());

		if (StringUtils.hasText(individualProviderDto.getAdministrativeGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository.findByCode(individualProviderDto
					.getAdministrativeGenderCode());
			individualProvider.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			individualProvider.setAdministrativeGenderCode(null);
		}

		individualProvider.setNpi(individualProviderDto.getNpi());

		// Contact
		individualProvider.setWebsite(individualProviderDto.getWebsite());
		individualProvider.setMailingAddressTelephoneNumber(individualProviderDto.getMailingAddressTelephoneNumber());
		individualProvider.setMailingAddressFaxNumber(individualProviderDto.getMailingAddressFaxNumber());

		// Mailing Address
		individualProvider.setFirstLineMailingAddress(individualProviderDto.getFirstLineMailingAddress());
		individualProvider.setSecondLineMailingAddress(individualProviderDto.getSecondLineMailingAddress());
		individualProvider.setMailingAddressCityName(individualProviderDto.getMailingAddressCityName());
		individualProvider.setMailingAddressStateName(individualProviderDto.getMailingAddressStateName());
		individualProvider.setNonUSState(individualProviderDto.getNonUSState());
		individualProvider.setMailingAddressPostalCode(individualProviderDto.getMailingAddressPostalCode());
		individualProvider.setMailingAddressCountryCode(individualProviderDto.getMailingAddressCountryCode());

		individualProvider.setAcceptTermsOfUse(individualProviderDto.isAcceptTermsOfUse());

		return individualProvider;
	}

	@Inject
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
