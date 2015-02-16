package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeCodeRepository extends JpaRepository<EpisodeCode, Long>, JpaSpecificationExecutor<EpisodeCode> {

	@Cacheable("episodeCodes")
	public abstract EpisodeCode findByCode(String code);

}
