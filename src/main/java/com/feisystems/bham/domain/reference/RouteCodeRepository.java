package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteCodeRepository extends JpaSpecificationExecutor<RouteCode>, JpaRepository<RouteCode, Long> {

	@Cacheable("routeCodes")
	public abstract RouteCode findByCode(String code);

}
