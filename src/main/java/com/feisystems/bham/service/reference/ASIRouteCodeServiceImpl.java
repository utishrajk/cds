package com.feisystems.bham.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.reference.ASIRouteCode;
import com.feisystems.bham.domain.reference.ASIRouteCodeRepository;
import com.feisystems.bham.domain.reference.RouteCode;
import com.feisystems.bham.domain.reference.RouteCodeRepository;
import com.feisystems.bham.service.LookupDto;

@Service
@Transactional
public class ASIRouteCodeServiceImpl implements ASIRouteCodeService {

	@Autowired
	ASIRouteCodeRepository aSIRouteCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllASIRouteCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<ASIRouteCode> aSIRouteCodeList = aSIRouteCodeRepository.findAll();

		for (ASIRouteCode entity : aSIRouteCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}	
}
