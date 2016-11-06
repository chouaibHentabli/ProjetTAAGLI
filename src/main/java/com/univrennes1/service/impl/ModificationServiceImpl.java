package com.univrennes1.service.impl;

import com.univrennes1.service.ModificationService;
import com.univrennes1.domain.Modification;
import com.univrennes1.repository.ModificationRepository;
import com.univrennes1.service.dto.ModificationDTO;
import com.univrennes1.service.mapper.ModificationMapper;
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
 * Service Implementation for managing Modification.
 */
@Service
@Transactional
public class ModificationServiceImpl implements ModificationService{

    private final Logger log = LoggerFactory.getLogger(ModificationServiceImpl.class);
    
    @Inject
    private ModificationRepository modificationRepository;

    @Inject
    private ModificationMapper modificationMapper;

    /**
     * Save a modification.
     *
     * @param modificationDTO the entity to save
     * @return the persisted entity
     */
    public ModificationDTO save(ModificationDTO modificationDTO) {
        log.debug("Request to save Modification : {}", modificationDTO);
        Modification modification = modificationMapper.modificationDTOToModification(modificationDTO);
        modification = modificationRepository.save(modification);
        ModificationDTO result = modificationMapper.modificationToModificationDTO(modification);
        return result;
    }

    /**
     *  Get all the modifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ModificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modifications");
        Page<Modification> result = modificationRepository.findAll(pageable);
        return result.map(modification -> modificationMapper.modificationToModificationDTO(modification));
    }

    /**
     *  Get one modification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ModificationDTO findOne(Long id) {
        log.debug("Request to get Modification : {}", id);
        Modification modification = modificationRepository.findOne(id);
        ModificationDTO modificationDTO = modificationMapper.modificationToModificationDTO(modification);
        return modificationDTO;
    }

    /**
     *  Delete the  modification by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Modification : {}", id);
        modificationRepository.delete(id);
    }
}
