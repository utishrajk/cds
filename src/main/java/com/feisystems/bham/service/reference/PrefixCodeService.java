package com.feisystems.bham.service.reference;

import java.util.List;

import com.feisystems.bham.service.LookupDto;

public interface PrefixCodeService {
	public abstract List<LookupDto> findAllPrefixCodes();
}
