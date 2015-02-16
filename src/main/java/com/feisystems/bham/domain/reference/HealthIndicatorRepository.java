package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthIndicatorRepository extends JpaRepository<HealthIndicatorCode, Long>, JpaSpecificationExecutor<HealthIndicatorCode> {

	@Cacheable("healthIndicatorCodes")
	public abstract HealthIndicatorCode findByCode(String code);

}
