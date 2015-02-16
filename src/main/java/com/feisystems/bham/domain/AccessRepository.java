package com.feisystems.bham.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long>, JpaSpecificationExecutor<Access> {
	
}
