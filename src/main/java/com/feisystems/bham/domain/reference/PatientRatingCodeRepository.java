package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRatingCodeRepository extends JpaSpecificationExecutor<PatientRatingCode>, JpaRepository<PatientRatingCode, Long> {

	@Cacheable("patingRatingCodes")
	public abstract PatientRatingCode findByCode(String code);

}
