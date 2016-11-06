package com.univrennes1.repository;

import com.univrennes1.domain.Variable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Variable entity.
 */
@SuppressWarnings("unused")
public interface VariableRepository extends JpaRepository<Variable,Long> {

}
