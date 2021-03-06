package com.app.covidfree.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.app.covidfree.domain.MobileUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MobileUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileUserRepository extends JpaRepository<MobileUser, Long> {

	Optional<MobileUser> findByCitizenId(Integer citizenId);

	Optional<MobileUser> findByCitizenIdAndPhoneNumber(Integer citizenId, String phoneNumber);

	@Modifying
	@Transactional
	@Query("update MobileUser m set m.statusType = :statusType where m.id = :id " )
	int updateUserValidationById(@Param("id") Long id, @Param("statusType") Integer statusType);
}
