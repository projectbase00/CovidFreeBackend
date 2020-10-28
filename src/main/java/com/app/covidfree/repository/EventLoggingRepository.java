package com.app.covidfree.repository;

import com.app.covidfree.domain.EventLogging;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventLogging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventLoggingRepository extends JpaRepository<EventLogging, Long> {
}
