package com.feisystems.bham.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface YesOrNoCodeRepository extends JpaRepository<YesOrNoCode, Long>, JpaSpecificationExecutor<YesOrNoCode> {
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the state code
	 */
	public abstract YesOrNoCode findByCode(String code);

}
