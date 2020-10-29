package com.app.covidfree.repository;

import com.app.covidfree.domain.MobileUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MobileUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileUserRepository extends JpaRepository<MobileUser, Long> {
}
