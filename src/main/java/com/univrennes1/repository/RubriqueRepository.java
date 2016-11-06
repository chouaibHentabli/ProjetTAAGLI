package com.univrennes1.repository;

import com.univrennes1.domain.Rubrique;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rubrique entity.
 */
@SuppressWarnings("unused")
public interface RubriqueRepository extends JpaRepository<Rubrique,Long> {

}
