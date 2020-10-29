package com.app.covidfree.repository;

import java.util.Optional;

import com.app.covidfree.domain.OtpCodes;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OtpCodes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtpCodesRepository extends JpaRepository<OtpCodes, Long> {

	@Query("Select o from OtpCodes o where o.mobileUser.citizenId = :citizenId and o.mobileUser.phoneNumber = :phoneNumber")
	Optional<OtpCodes> findOtpCodeByCitizen(@Param("citizenId") Integer citizenId, @Param("phoneNumber") String phoneNumber);
}
