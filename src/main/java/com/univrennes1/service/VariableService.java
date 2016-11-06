package com.univrennes1.service;

import com.univrennes1.service.dto.VariableDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Variable.
 */
public interface VariableService {

    /**
     * Save a variable.
     *
     * @param variableDTO the entity to save
     * @return the persisted entity
     */
    VariableDTO save(VariableDTO variableDTO);

    /**
     *  Get all the variables.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VariableDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" variable.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VariableDTO findOne(Long id);

    /**
     *  Delete the "id" variable.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
