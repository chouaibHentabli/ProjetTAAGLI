package com.univrennes1.service.impl;

import com.univrennes1.service.RubriqueService;
import com.univrennes1.domain.Rubrique;
import com.univrennes1.repository.RubriqueRepository;
import com.univrennes1.service.dto.RubriqueDTO;
import com.univrennes1.service.mapper.RubriqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Rubrique.
 */
@Service
@Transactional
public class RubriqueServiceImpl implements RubriqueService{

    private final Logger log = LoggerFactory.getLogger(RubriqueServiceImpl.class);

    @Inject
    private RubriqueRepository rubriqueRepository;

    @Inject
    private RubriqueMapper rubriqueMapper;

    /**
     * Save a rubrique.
     *
     * @param rubriqueDTO the entity to save
     * @return the persisted entity
     */
    public RubriqueDTO save(RubriqueDTO rubriqueDTO) {
        log.debug("Request to save Rubrique : {}", rubriqueDTO);
        Rubrique rubrique = rubriqueMapper.rubriqueDTOToRubrique(rubriqueDTO);
        rubrique = rubriqueRepository.save(rubrique);
        RubriqueDTO result = rubriqueMapper.rubriqueToRubriqueDTO(rubrique);
        return result;
    }


    /**
     *  Get all the rubriques.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RubriqueDTO> findAll() {
        log.debug("Request to get all Rubriques");
        List<RubriqueDTO> result = rubriqueRepository.findAll().stream()
            .map(rubriqueMapper::rubriqueToRubriqueDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     *  Get all the rubriques.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RubriqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rubriques");
        Page<Rubrique> result = rubriqueRepository.findAll(pageable);
        return result.map(rubrique -> rubriqueMapper.rubriqueToRubriqueDTO(rubrique));
    }

    /**
     *  Get one rubrique by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RubriqueDTO findOne(Long id) {
        log.debug("Request to get Rubrique : {}", id);
        Rubrique rubrique = rubriqueRepository.findOne(id);
        RubriqueDTO rubriqueDTO = rubriqueMapper.rubriqueToRubriqueDTO(rubrique);
        return rubriqueDTO;
    }

    /**
     *  Delete the  rubrique by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rubrique : {}", id);
        rubriqueRepository.delete(id);
    }
}
