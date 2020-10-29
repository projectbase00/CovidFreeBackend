package com.app.covidfree.repository;

import com.app.covidfree.domain.OtpCodes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OtpCodes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtpCodesRepository extends JpaRepository<OtpCodes, Long> {
}
