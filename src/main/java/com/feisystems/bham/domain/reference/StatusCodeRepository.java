package com.feisystems.bham.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCodeRepository extends JpaRepository<StatusCode, Long>, JpaSpecificationExecutor<StatusCode> {
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the state code
	 */
	public abstract StatusCode findByCode(String code);

}
