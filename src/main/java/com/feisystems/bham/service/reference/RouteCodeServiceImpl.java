package com.feisystems.bham.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.reference.RouteCode;
import com.feisystems.bham.domain.reference.RouteCodeRepository;
import com.feisystems.bham.service.LookupDto;

@Service
@Transactional
public class RouteCodeServiceImpl implements RouteCodeService {

	@Autowired
	RouteCodeRepository routeCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllRouteCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<RouteCode> routeCodeList = routeCodeRepository.findAll();

		for (RouteCode entity : routeCodeList) {
			// Only positive codes are valid for the dropdown
			if (Integer.parseInt(entity.getCode()) > 0) {
				lookups.add(modelMapper.map(entity, LookupDto.class));
			}
		}

		return lookups;
	}

}
