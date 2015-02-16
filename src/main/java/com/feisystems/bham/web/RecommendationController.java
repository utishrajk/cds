package com.feisystems.bham.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.service.AsiFlagDto;
import com.feisystems.bham.service.DataElementsService;
import com.feisystems.bham.service.GpraFlagDto;
import com.feisystems.bham.service.IndividualProviderService;
import com.feisystems.bham.service.RecommendationsDto;
import com.feisystems.bham.service.recommendation.RecommendationService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/recommendations")
public class RecommendationController {

	@Autowired
	RecommendationService recommendationService;
	
	@Autowired
	DataElementsService dataElementsService;
	
    @Autowired
    IndividualProviderService individualProviderService;

	@RequestMapping(value = "/{requestId}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public RecommendationsDto getRecommendations(@PathVariable("requestId") String requestId) {

		RecommendationsDto recommendationsDto = recommendationService.getRecommendations(requestId);
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
		return recommendationsDto;

	}
	
	@RequestMapping(value = "/norequestid", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public RecommendationsDto getRecommendationsWithoutRequestId() {
		RecommendationsDto recommendationsDto = recommendationService.getRecommendations();
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
		return recommendationsDto;

	}
	
	@RequestMapping(value = "/downloadPDF/{requestId}", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@PathVariable("requestId") String requestId) {
		ModelAndView modelAndView = new ModelAndView("pdfView");

		RecommendationsDto recommendationsDto = recommendationService.getRecommendations(requestId);
		modelAndView.addObject("recommendationsDto", recommendationsDto);
		
		try {
			String username =  SecurityContextHolder.getContext().getAuthentication().getName();			
			IndividualProvider individualProvider = individualProviderService.findByUsername(username);
			
			GpraFlagDto gpraDto = dataElementsService.findGpraFlagDto(requestId, individualProvider.getStaffId());
			
			if (gpraDto != null) {
				gpraDto.setGpra(true);
				modelAndView.addObject("gpraDto", gpraDto);
			} else {
				AsiFlagDto asiDto = dataElementsService.findAsiFlagDto(requestId, individualProvider.getStaffId());
				if (asiDto != null) {
					asiDto.setAsi(true);
					modelAndView.addObject("asiDto", asiDto);
				}
			}
			
			
			modelAndView.addObject("fullName", individualProvider.getFirstName() + " " + individualProvider.getLastName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.CONTENT_TYPE, "Accept=application/pdf");		
	
		return modelAndView;
	}
}
