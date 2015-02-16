package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseCodeRepository extends JpaSpecificationExecutor<ResponseCode>, JpaRepository<ResponseCode, Long> {

	@Cacheable("responseCodes")
	public abstract ResponseCode findByCode(String code);

}
