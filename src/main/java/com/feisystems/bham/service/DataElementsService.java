package com.feisystems.bham.service;

import java.util.Set;

import com.feisystems.bham.domain.AuditRecommendations;
import com.feisystems.bham.domain.asi.AsiVariableContainer;
import com.feisystems.bham.domain.gpra.GpraVariableContainer;

public interface DataElementsService {

	public GpraDto findGpraDto(String requestId, String staffId);

	public AsiDto findAsiDto(String requestId, String staffId);
	
	public GpraFlagDto findGpraFlagDto(String requestId, String staffId) throws Exception;

	public AsiFlagDto findAsiFlagDto(String requestId, String staffId) throws Exception;

	public void saveGpra(GpraDto dto);

	public void saveAsi(AsiDto dto);

	public void saveGpraVariableContainer(GpraVariableContainer container, String requestId, String staffId, String uniqueClientNumber,
			Set<AuditRecommendations> auditRecommendations);

	public void saveAsiVariableContainer(AsiVariableContainer container, String requestId, String staffId, String uniqueClientNumber,
			Set<AuditRecommendations> auditRecommendations);

}
