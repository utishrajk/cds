package com.feisystems.bham.service.reference;

import java.util.List;

import com.feisystems.bham.service.LookupDto;

public interface EpisodeCodeService {
	public abstract List<LookupDto> findAllEpisodeCodes();
}
