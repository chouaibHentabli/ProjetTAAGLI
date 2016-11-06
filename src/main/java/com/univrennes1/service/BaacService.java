package com.univrennes1.service;

import com.univrennes1.service.dto.BaacDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Baac.
 */
public interface BaacService {

    /**
     * Save a baac.
     *
     * @param baacDTO the entity to save
     * @return the persisted entity
     */
    BaacDTO save(BaacDTO baacDTO);



    List<BaacDTO> findAll();

    /**
     *  Get all the baacs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BaacDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" baac.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BaacDTO findOne(Long id);

    /**
     *  Delete the "id" baac.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
