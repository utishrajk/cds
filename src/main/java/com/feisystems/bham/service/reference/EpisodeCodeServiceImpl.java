package com.feisystems.bham.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.reference.EpisodeCode;
import com.feisystems.bham.domain.reference.EpisodeCodeRepository;
import com.feisystems.bham.service.LookupDto;

@Service
@Transactional
public class EpisodeCodeServiceImpl implements EpisodeCodeService{

	@Autowired
	EpisodeCodeRepository episodeCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	
	@Override
	public List<LookupDto> findAllEpisodeCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<EpisodeCode> episodeCodeList = episodeCodeRepository.findAll();

		for (EpisodeCode entity : episodeCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
