package com.feisystems.bham.service.reference;
import com.feisystems.bham.service.LookupDto;

import java.util.List;

public interface RaceCodeService {

	/**
	 * Find all race codes.
	 * @return the list
	 */
	List<LookupDto> findAllRaceCodes();

}
