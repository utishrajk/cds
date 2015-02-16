package com.feisystems.bham.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.OrganizationalProvider;
import com.feisystems.bham.domain.OrganizationalProviderRepository;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;

@Component
public class OrganizationalProviderDtoToOrganizationalProviderMapper implements
		DtoToDomainEntityMapper<OrganizationalProviderDto, OrganizationalProvider> {

	private static final String ASI = "asi";
	/** The OrganizationalProvider repository. */
	
	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;
	
	@Override
	public OrganizationalProvider map(OrganizationalProviderDto organizationalProviderDto) {
		OrganizationalProvider organizationalProvider = null;

		if(organizationalProviderDto.getEmail() != null) {
			//Only one Organization currently exists
			organizationalProvider = organizationalProviderRepository.findOne(1L);
		}
		else {
			organizationalProvider = new OrganizationalProvider();
		}

		// Return null if no IndividualProvider found
		if (organizationalProvider == null) {
			return null;
		}
		
		if(organizationalProviderDto != null){
			//General
			organizationalProvider.setOrgName(organizationalProviderDto.getOrgName());
			organizationalProvider.setNpi(organizationalProviderDto.getNpi());
			organizationalProvider.setAuthorizedOfficialNamePrefix(organizationalProviderDto.getAuthorizedOfficialNamePrefix());
			organizationalProvider.setAuthorizedOfficialFirstName(organizationalProviderDto.getAuthorizedOfficialFirstName());
			organizationalProvider.setAuthorizedOfficialLastName(organizationalProviderDto.getAuthorizedOfficialLastName());
			organizationalProvider.setAuthorizedOfficialTelephoneNumber(organizationalProviderDto.getAuthorizedOfficialTelephoneNumber());
			
			//Contact
			organizationalProvider.setEmail(organizationalProviderDto.getEmail());
			organizationalProvider.setWebsite(organizationalProviderDto.getWebsite());
			organizationalProvider.setMailingAddressTelephoneNumber(organizationalProviderDto.getMailingAddressTelephoneNumber());
			organizationalProvider.setMailingAddressFaxNumber(organizationalProviderDto.getMailingAddressFaxNumber());
			
			//Mailing Address
			organizationalProvider.setFirstLineMailingAddress(organizationalProviderDto.getFirstLineMailingAddress());
			organizationalProvider.setSecondLineMailingAddress(organizationalProviderDto.getSecondLineMailingAddress());
			organizationalProvider.setMailingAddressCityName(organizationalProviderDto.getMailingAddressCityName());
			organizationalProvider.setMailingAddressStateName(organizationalProviderDto.getMailingAddressStateName());
			organizationalProvider.setNonUSState(organizationalProviderDto.getNonUSState());
			organizationalProvider.setMailingAddressCountryCode(organizationalProviderDto.getMailingAddressCountryCode());
			organizationalProvider.setMailingAddressPostalCode(organizationalProviderDto.getMailingAddressPostalCode());
			
			if(organizationalProviderDto.getAssessment() != null) {
				if(organizationalProviderDto.getAssessment().equals(ASI)) {
					organizationalProvider.setAsi(true);
					organizationalProvider.setGpra(false);
				} else {
					organizationalProvider.setGpra(true);
					organizationalProvider.setAsi(false);
				}
			}
		}

		return organizationalProvider;
	}

}
