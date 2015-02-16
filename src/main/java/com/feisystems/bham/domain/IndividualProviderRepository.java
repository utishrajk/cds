package com.feisystems.bham.domain;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualProviderRepository extends JpaRepository<IndividualProvider, Long>, JpaSpecificationExecutor<IndividualProvider> {

	IndividualProvider findByUserName(String userName);

	IndividualProvider findByUserNameAndDateOfBirth(String userName, Date dateOfBirth);
}
