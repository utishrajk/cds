package com.feisystems.bham.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecommendationsRepository extends
		JpaRepository<AuditRecommendations, Long>,
		JpaSpecificationExecutor<AuditRecommendations> {

	public AuditRecommendations findByRequestId(String requestId);
	
	public Page<AuditRecommendations> findByRequestId(String requestId, Pageable pageable);
	
	public Page<AuditRecommendations> findByUsername(String username, Pageable pageable);

}
