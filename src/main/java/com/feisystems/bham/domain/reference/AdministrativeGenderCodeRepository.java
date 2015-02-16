package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeGenderCodeRepository extends JpaSpecificationExecutor<AdministrativeGenderCode>,
		JpaRepository<AdministrativeGenderCode, Long> {

	@Cacheable("genderCodes")
	public abstract AdministrativeGenderCode findByCode(String code);

}
