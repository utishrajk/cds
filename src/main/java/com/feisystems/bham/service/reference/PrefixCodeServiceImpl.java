package com.feisystems.bham.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.reference.PrefixCode;
import com.feisystems.bham.domain.reference.PrefixCodeRepository;
import com.feisystems.bham.service.LookupDto;

@Service
@Transactional
public class PrefixCodeServiceImpl implements PrefixCodeService {

	@Autowired
    PrefixCodeRepository prefixCodeRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<LookupDto> findAllPrefixCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<PrefixCode> prefixCodeList = prefixCodeRepository.findAll();

		for (PrefixCode entity : prefixCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;

	}
	
}
