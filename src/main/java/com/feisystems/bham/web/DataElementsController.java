package com.feisystems.bham.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.service.AsiDto;
import com.feisystems.bham.service.AsiFlagDto;
import com.feisystems.bham.service.DataElementsDto;
import com.feisystems.bham.service.DataElementsService;
import com.feisystems.bham.service.GpraDto;
import com.feisystems.bham.service.GpraFlagDto;
import com.feisystems.bham.service.UsernameService;
import com.feisystems.bham.service.recommendation.RecommendationService;
import com.feisystems.bham.util.CommonUtility;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/dataelements")
public class DataElementsController {

	@Autowired
	DataElementsService dataElementsService;

	@Autowired
	RecommendationService recommendationService;
	
	@Autowired
	UsernameService usernameService;
	
	@Autowired
	private IndividualProviderRepository individualProviderRepository;

	@RequestMapping(value = "/{requestId}", method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
	@ResponseBody
	public DataElementsDto findDtoByRequestId(@PathVariable("requestId") String requestId) {
		GpraDto dto = dataElementsService.findGpraDto(requestId, retrieveStaffId());

		if (dto != null) {
			dto.setGpra(true);
			return dto;
		} else {
			AsiDto asiDto = dataElementsService.findAsiDto(requestId, retrieveStaffId());
			asiDto.setAsi(true);
			return asiDto;
		}

	}

	@RequestMapping(value = "/flag/{requestId}", method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
	@ResponseBody
	public DataElementsDto findDtoFlagByRequestId(@PathVariable("requestId") String requestId) throws Exception {
		GpraFlagDto dto = dataElementsService.findGpraFlagDto(requestId, retrieveStaffId());

		if (dto != null) {
			return dto;
		} else {
			AsiFlagDto asiDto = dataElementsService.findAsiFlagDto(requestId, retrieveStaffId());
			return asiDto;
		}

	}

	@RequestMapping(value = "/gpra/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> createGpra(@RequestBody GpraDto dto, @PathVariable("id") Long id) {
		recommendationService.saveGpraRecommendations(dto);
		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/asi/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> createAsi(@RequestBody AsiDto dto, @PathVariable("id") Long id) {
		recommendationService.saveAsiRecommendations(dto);
		return new ResponseEntity<String>(CommonUtility.getHeaders(), HttpStatus.CREATED);
	}
	
	private String retrieveStaffId() {
		return individualProviderRepository.findByUserName(usernameService.retrieveUsername()).getStaffId();
	}

}
