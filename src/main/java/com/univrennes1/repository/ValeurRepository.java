package com.univrennes1.repository;

import com.univrennes1.domain.Valeur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Valeur entity.
 */
@SuppressWarnings("unused")
public interface ValeurRepository extends JpaRepository<Valeur,Long> {

}
