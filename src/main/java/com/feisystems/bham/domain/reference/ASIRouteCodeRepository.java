package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ASIRouteCodeRepository extends JpaRepository<ASIRouteCode, Long>, JpaSpecificationExecutor<ASIRouteCode> {

	@Cacheable("asiRouteCodes")
	public abstract ASIRouteCode findByCode(String code);
}
