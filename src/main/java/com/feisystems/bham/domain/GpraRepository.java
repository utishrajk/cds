package com.feisystems.bham.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GpraRepository extends JpaSpecificationExecutor<Gpra>, JpaRepository<Gpra, Long> {

	public Page<Gpra> findByRequestIdAndStaffId(String requestId, String staffId, Pageable pageable);

}
