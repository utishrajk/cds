package com.feisystems.bham.service.reference;

import java.util.List;

import com.feisystems.bham.service.LookupDto;

public interface YesOrNoCodeService {
	public abstract List<LookupDto> findAllYesOrNoCodes();
}
