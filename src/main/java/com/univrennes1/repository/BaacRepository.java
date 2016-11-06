package com.univrennes1.repository;

import com.univrennes1.domain.Baac;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Baac entity.
 */
@SuppressWarnings("unused")
public interface BaacRepository extends JpaRepository<Baac,Long> {

    @Query("select distinct baac from Baac baac left join fetch baac.valeurs")
    List<Baac> findAllWithEagerRelationships();

    @Query("select baac from Baac baac left join fetch baac.valeurs where baac.id =:id")
    Baac findOneWithEagerRelationships(@Param("id") Long id);

}
