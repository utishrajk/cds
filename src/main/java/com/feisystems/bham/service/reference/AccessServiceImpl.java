package com.feisystems.bham.service.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.Access;
import com.feisystems.bham.domain.AccessRepository;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.bham.security.SecurityUtils;
import com.feisystems.bham.service.AccessDto;

@Service
@Transactional
public class AccessServiceImpl implements AccessService {
	
	@Autowired
	AccessRepository accessRepository;

	@Autowired
	DtoToDomainEntityMapper<AccessDto, Access> accessDtotoAccessMapper;

	private static Logger log = LoggerFactory.getLogger(AccessServiceImpl.class);
	
	public void save(AccessDto accessDto) {
		if (SecurityUtils.isAuthenticated()) {
			Access access = accessDtotoAccessMapper.map(accessDto);
			accessRepository.save(access);
		} else {
			 log.debug("Trying to save activity for an unknown user. ");
		}
	}

}
