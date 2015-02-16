package com.feisystems.bham.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.bham.service.IndividualProviderDto;

@Component
@Configurable
public class IndividualProviderDataOnDemand {

	@Autowired
    IndividualProviderRepository repository;
	
	@Autowired
	private DtoToDomainEntityMapper<IndividualProviderDto, IndividualProvider> individualProviderDtoToIndividualProviderMapper;

	public IndividualProviderDto getRandomIndividualProviderDto() {
		IndividualProviderDto dto = new IndividualProviderDto();
		dto.setEmail(getRandomString() + "@mailinator.com");
		dto.setFirstName(getRandomString());
		dto.setLastName(getRandomString());
		dto.setMailingAddressTelephoneNumber("4103409987");
		dto.setCredential("P@ssword1234");
		dto.setConfirmPassword("P@ssword1234");
		dto.setSecurityQuestion1Code("1");
		dto.setSecurityAnswer1("kkk");
		dto.setSecurityQuestion2Code("2");
		dto.setSecurityAnswer2("kkk");
		dto.setAcceptTermsOfUse(true);
		dto.setDateOfBirth(new Date());
		return dto;
	}
	
	public IndividualProvider getRandomIndividualProvider() {
		List<IndividualProvider> list = repository.findAll();
		
		return list.get(list.size()-1);
	}
	
	public IndividualProvider getNewIndividualProvider() {
		IndividualProviderDto dto = getRandomIndividualProviderDto();
		return individualProviderDtoToIndividualProviderMapper.map(dto);
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
}
