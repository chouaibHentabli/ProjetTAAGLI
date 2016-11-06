package com.univrennes1.repository;

import com.univrennes1.domain.Cause;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cause entity.
 */
@SuppressWarnings("unused")
public interface CauseRepository extends JpaRepository<Cause,Long> {

}
