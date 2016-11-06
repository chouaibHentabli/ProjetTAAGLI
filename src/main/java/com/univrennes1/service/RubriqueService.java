package com.univrennes1.service;

import com.univrennes1.service.dto.RubriqueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Rubrique.
 */
public interface RubriqueService {

    /**
     * Save a rubrique.
     *
     * @param rubriqueDTO the entity to save
     * @return the persisted entity
     */
    RubriqueDTO save(RubriqueDTO rubriqueDTO);


    /**
     *  Get all the rubriques.
     *
     *  @return the list of entities
     */
    List<RubriqueDTO> findAll();

    /**
     *  Get all the rubriques.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RubriqueDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" rubrique.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RubriqueDTO findOne(Long id);

    /**
     *  Delete the "id" rubrique.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
