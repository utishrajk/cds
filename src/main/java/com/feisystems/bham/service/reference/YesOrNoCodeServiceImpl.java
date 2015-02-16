package com.feisystems.bham.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.reference.YesOrNoCode;
import com.feisystems.bham.domain.reference.YesOrNoCodeRepository;
import com.feisystems.bham.service.LookupDto;

@Service
@Transactional
public class YesOrNoCodeServiceImpl implements YesOrNoCodeService {
	
	@Autowired
	YesOrNoCodeRepository yesOrNoCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllYesOrNoCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<YesOrNoCode> yesOrNoCodeList = yesOrNoCodeRepository.findAll();

		for (YesOrNoCode entity : yesOrNoCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
