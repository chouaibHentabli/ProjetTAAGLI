package com.univrennes1.service;

import com.univrennes1.service.dto.AccidentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Accident.
 */
public interface AccidentService {

    /**
     * Save a accident.
     *
     * @param accidentDTO the entity to save
     * @return the persisted entity
     */
    AccidentDTO save(AccidentDTO accidentDTO);


    List<AccidentDTO> findAll();


    /**
     *  Get all the accidents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AccidentDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" accident.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AccidentDTO findOne(Long id);

    /**
     *  Delete the "id" accident.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
