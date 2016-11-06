package com.univrennes1.repository;

import com.univrennes1.domain.Modification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Modification entity.
 */
@SuppressWarnings("unused")
public interface ModificationRepository extends JpaRepository<Modification,Long> {

}
