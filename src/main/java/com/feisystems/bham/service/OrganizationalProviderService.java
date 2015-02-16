package com.feisystems.bham.service;
import com.feisystems.bham.domain.OrganizationalProvider;

import java.util.List;

public interface OrganizationalProviderService {

	public abstract long countAllOrganizationalProviders();


	public abstract void deleteOrganizationalProvider(OrganizationalProvider organizationalProvider);


	public abstract OrganizationalProviderDto findOrganizationalProvider();


	public abstract List<OrganizationalProviderDto> findAllOrganizationalProviders();


	public abstract List<OrganizationalProvider> findOrganizationalProviderEntries(int firstResult, int maxResults);


	public abstract void saveOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto);


	public abstract OrganizationalProvider updateOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto);
	
}
