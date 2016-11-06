package com.univrennes1.service.impl;

import com.univrennes1.service.ValeurService;
import com.univrennes1.domain.Valeur;
import com.univrennes1.repository.ValeurRepository;
import com.univrennes1.service.dto.ValeurDTO;
import com.univrennes1.service.mapper.ValeurMapper;
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
 * Service Implementation for managing Valeur.
 */
@Service
@Transactional
public class ValeurServiceImpl implements ValeurService{

    private final Logger log = LoggerFactory.getLogger(ValeurServiceImpl.class);
    
    @Inject
    private ValeurRepository valeurRepository;

    @Inject
    private ValeurMapper valeurMapper;

    /**
     * Save a valeur.
     *
     * @param valeurDTO the entity to save
     * @return the persisted entity
     */
    public ValeurDTO save(ValeurDTO valeurDTO) {
        log.debug("Request to save Valeur : {}", valeurDTO);
        Valeur valeur = valeurMapper.valeurDTOToValeur(valeurDTO);
        valeur = valeurRepository.save(valeur);
        ValeurDTO result = valeurMapper.valeurToValeurDTO(valeur);
        return result;
    }

    /**
     *  Get all the valeurs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ValeurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Valeurs");
        Page<Valeur> result = valeurRepository.findAll(pageable);
        return result.map(valeur -> valeurMapper.valeurToValeurDTO(valeur));
    }

    /**
     *  Get one valeur by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ValeurDTO findOne(Long id) {
        log.debug("Request to get Valeur : {}", id);
        Valeur valeur = valeurRepository.findOne(id);
        ValeurDTO valeurDTO = valeurMapper.valeurToValeurDTO(valeur);
        return valeurDTO;
    }

    /**
     *  Delete the  valeur by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Valeur : {}", id);
        valeurRepository.delete(id);
    }
}
