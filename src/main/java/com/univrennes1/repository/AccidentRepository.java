package com.univrennes1.repository;

import com.univrennes1.domain.Accident;

import com.univrennes1.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Accident entity.
 */
@SuppressWarnings("unused")
public interface AccidentRepository extends JpaRepository<Accident,Long> {

    @Query("select distinct accident from Accident accident left join fetch accident.causes")
    List<Accident> findAllWithEagerRelationships();

    @Query("select accident from Accident accident left join fetch accident.causes where accident.id =:id")
    Accident findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select distinct accident from Accident accident where accident.user.id =:id",
        countQuery = "select count(accident) from Accident accident where accident.user.id =:id")
    Page<Accident> findAllByUser(@Param("id") Long id, Pageable pageable);

}
