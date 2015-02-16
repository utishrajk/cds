package com.feisystems.bham.service.reference;

import java.util.List;

import com.feisystems.bham.service.LookupDto;

public interface StatusCodeService {
	public abstract List<LookupDto> findAllStatusCodes();
}
