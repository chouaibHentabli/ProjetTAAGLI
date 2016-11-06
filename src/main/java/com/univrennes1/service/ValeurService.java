package com.univrennes1.service;

import com.univrennes1.service.dto.ValeurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Valeur.
 */
public interface ValeurService {

    /**
     * Save a valeur.
     *
     * @param valeurDTO the entity to save
     * @return the persisted entity
     */
    ValeurDTO save(ValeurDTO valeurDTO);

    /**
     *  Get all the valeurs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ValeurDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" valeur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ValeurDTO findOne(Long id);

    /**
     *  Delete the "id" valeur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
