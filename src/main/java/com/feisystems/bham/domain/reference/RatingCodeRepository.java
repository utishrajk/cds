package com.feisystems.bham.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingCodeRepository extends JpaRepository<PatientRatingCode, Long>, JpaSpecificationExecutor<PatientRatingCode> {

	public abstract PatientRatingCode findByCode(String code);

}
