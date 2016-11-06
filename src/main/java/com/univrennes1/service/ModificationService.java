package com.univrennes1.service;

import com.univrennes1.service.dto.ModificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Modification.
 */
public interface ModificationService {

    /**
     * Save a modification.
     *
     * @param modificationDTO the entity to save
     * @return the persisted entity
     */
    ModificationDTO save(ModificationDTO modificationDTO);

    /**
     *  Get all the modifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ModificationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" modification.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ModificationDTO findOne(Long id);

    /**
     *  Delete the "id" modification.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
