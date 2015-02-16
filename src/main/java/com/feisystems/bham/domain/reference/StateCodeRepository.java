package com.feisystems.bham.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StateCodeRepository extends JpaRepository<StateCode, Long>, JpaSpecificationExecutor<StateCode> {
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the state code
	 */
	public abstract StateCode findByCode(String code);
	
}
