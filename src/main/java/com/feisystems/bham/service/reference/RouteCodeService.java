package com.feisystems.bham.service.reference;

import java.util.List;

import com.feisystems.bham.service.LookupDto;

public interface RouteCodeService {

	public abstract List<LookupDto> findAllRouteCodes();

}
