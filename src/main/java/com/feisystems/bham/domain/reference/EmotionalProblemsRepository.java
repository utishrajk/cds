package com.feisystems.bham.domain.reference;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionalProblemsRepository extends JpaRepository<EmotionalProblemsCode, Long>, JpaSpecificationExecutor<EmotionalProblemsCode> {

	@Cacheable("emotionalProblemsCodes")
	public abstract EmotionalProblemsCode findByCode(String code);

}
