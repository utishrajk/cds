package com.feisystems.bham.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryCodeRepository extends JpaRepository<CountryCode, Long>, JpaSpecificationExecutor<CountryCode> {
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the Contry code
	 */
	public abstract CountryCode findByCode(String code);
}
