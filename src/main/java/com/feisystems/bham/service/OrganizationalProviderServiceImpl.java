package com.feisystems.bham.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.OrganizationalProvider;
import com.feisystems.bham.domain.OrganizationalProviderRepository;
import com.feisystems.bham.exceptions.ClinicalDataNotFoundException;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;

@Service
@Transactional
public class OrganizationalProviderServiceImpl implements OrganizationalProviderService {
	
	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/** The individualProvider profile dto to individualProvider mapper. */
	@Autowired
	private DtoToDomainEntityMapper<OrganizationalProviderDto, OrganizationalProvider> organizationalProviderDtoToOrganizationalProviderMapper;

	
	public long countAllOrganizationalProviders() {
        return organizationalProviderRepository.count();
    }

	public void deleteOrganizationalProvider(OrganizationalProvider organizationalProvider) {
        organizationalProviderRepository.delete(organizationalProvider);
    }

	public List<OrganizationalProviderDto> findAllOrganizationalProviders() {
		List<OrganizationalProviderDto> OrganizationalProviderProviderProfileDtoList = new ArrayList<>();
        List<OrganizationalProvider> OrganizationalProviderProviderList =  organizationalProviderRepository.findAll();
        
        if (OrganizationalProviderProviderList != null &&  OrganizationalProviderProviderList.size() > 0) {
            for(OrganizationalProvider organizationalProvider: OrganizationalProviderProviderList) {
            	OrganizationalProviderDto organizationalProviderDto = modelMapper.map(organizationalProvider, OrganizationalProviderDto.class);
            	OrganizationalProviderProviderProfileDtoList.add(organizationalProviderDto);
            }
            return OrganizationalProviderProviderProfileDtoList;
        }
		throw new IllegalArgumentException("No Care Manager Found");
    }

	public List<OrganizationalProvider> findOrganizationalProviderEntries(int firstResult, int maxResults) {
        return organizationalProviderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	@Override
	public void saveOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto) {
		OrganizationalProvider organizationalProvider = organizationalProviderDtoToOrganizationalProviderMapper.map(organizationalProviderDto);
		organizationalProviderRepository.save(organizationalProvider);
	}

	@Override
	public OrganizationalProvider updateOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto) {
		OrganizationalProvider organizationalProvider = organizationalProviderDtoToOrganizationalProviderMapper.map(organizationalProviderDto);
		
		if (organizationalProvider != null) {
			return organizationalProviderRepository.save(organizationalProvider);	
		} 
		throw new ClinicalDataNotFoundException(organizationalProviderDto.getId());
	}

	@Override
	public OrganizationalProviderDto findOrganizationalProvider() {
		
		OrganizationalProvider organizationalProvider = null;
		
		try {
			organizationalProvider = organizationalProviderRepository.findOne(1L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(organizationalProvider != null) {			
			OrganizationalProviderDto dto = modelMapper.map(organizationalProvider, OrganizationalProviderDto.class);
			
			if(organizationalProvider.isAsi()) {
				dto.setAssessment("asi");
			} else {
				dto.setAssessment("gpra");
			}
			
			if (dto.getMailingAddressStateName().equals("NON US STATE")) {
				dto.setMailingAddressStateName("");
			}
			return dto;
		}
		throw new ClinicalDataNotFoundException(1L);
	}
}
