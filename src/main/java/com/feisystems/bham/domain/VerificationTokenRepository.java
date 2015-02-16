package com.feisystems.bham.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("select t from VerificationToken t where uuid = ?")
    VerificationToken findByUuid(String uuid);

    @Query("select t from VerificationToken t where token = ?")
    VerificationToken findByToken(String token);
}
