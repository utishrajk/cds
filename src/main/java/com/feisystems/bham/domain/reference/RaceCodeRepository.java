package com.feisystems.bham.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceCodeRepository extends JpaSpecificationExecutor<RaceCode>, JpaRepository<RaceCode, Long> {
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the race code
	 */
	public abstract RaceCode findByCode(String code);
}
