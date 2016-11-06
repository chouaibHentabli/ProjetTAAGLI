package com.univrennes1.service;

import com.univrennes1.service.dto.CauseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Cause.
 */
public interface CauseService {

    /**
     * Save a cause.
     *
     * @param causeDTO the entity to save
     * @return the persisted entity
     */
    CauseDTO save(CauseDTO causeDTO);


    /**
     *  Get all the causes.
     *
     *  @return the list of entities
     */
    List<CauseDTO> findAll();

    /**
     *  Get all the causes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CauseDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cause.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CauseDTO findOne(Long id);

    /**
     *  Delete the "id" cause.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
