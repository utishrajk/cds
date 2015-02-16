package com.feisystems.bham.service.recommendation;

import javax.jws.WebService;

import com.feisystems.bham.domain.gpra.AbstractVariableContainer;
import com.feisystems.bham.exceptions.SimpleSoapException;
import com.feisystems.bham.service.AsiDto;
import com.feisystems.bham.service.GpraDto;
import com.feisystems.bham.service.RecommendationServiceResponse;
import com.feisystems.bham.service.RecommendationsDto;

@WebService
public interface RecommendationService {

	public RecommendationServiceResponse saveAssessments(AbstractVariableContainer id) throws SimpleSoapException;

	public void saveGpraRecommendations(GpraDto dataElementsDto);

	public void saveAsiRecommendations(AsiDto asiDto);

	public RecommendationsDto getRecommendations(String requestId);
	
	public RecommendationsDto getRecommendations();

}
