package com.feisystems.bham.service.reference;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.Access;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.bham.security.SecurityUtils;
import com.feisystems.bham.service.AccessDto;

@Component
public class AccessDtotoAccessMapper implements DtoToDomainEntityMapper<AccessDto, Access> {
	
	private static Logger log = LoggerFactory.getLogger(AccessDtotoAccessMapper.class);
	
	@Override
	public Access map(AccessDto dto) {
		Access access = new Access();

		if (dto != null) {
			access.setDescription(dto.getDescription());			
			access.setHost(dto.getHost());
			access.setTimestamp(new Date());
			access.setUsername(SecurityUtils.getCurrentLogin());
			access.setPath(dto.getPath());
		}else{
			log.debug("Access dto un defined. ");
		}
		
		return access;
	}
}
