package com.feisystems.bham.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.reference.StatusCode;
import com.feisystems.bham.domain.reference.StatusCodeRepository;
import com.feisystems.bham.service.LookupDto;

@Service
@Transactional
public class StatusCodeServiceImpl implements StatusCodeService {

	@Autowired
	StatusCodeRepository statusCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	
	@Override
	public List<LookupDto> findAllStatusCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<StatusCode> statusCodeList = statusCodeRepository.findAll();

		for (StatusCode entity : statusCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}
}
