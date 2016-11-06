package com.univrennes1.service.impl;

import com.univrennes1.service.CauseService;
import com.univrennes1.domain.Cause;
import com.univrennes1.repository.CauseRepository;
import com.univrennes1.service.dto.CauseDTO;
import com.univrennes1.service.mapper.CauseMapper;
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
 * Service Implementation for managing Cause.
 */
@Service
@Transactional
public class CauseServiceImpl implements CauseService{

    private final Logger log = LoggerFactory.getLogger(CauseServiceImpl.class);

    @Inject
    private CauseRepository causeRepository;

    @Inject
    private CauseMapper causeMapper;

    /**
     * Save a cause.
     *
     * @param causeDTO the entity to save
     * @return the persisted entity
     */
    public CauseDTO save(CauseDTO causeDTO) {
        log.debug("Request to save Cause : {}", causeDTO);
        Cause cause = causeMapper.causeDTOToCause(causeDTO);
        cause = causeRepository.save(cause);
        CauseDTO result = causeMapper.causeToCauseDTO(cause);
        return result;
    }

    /**
     *  Get all the causes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CauseDTO> findAll() {
        log.debug("Request to get all Causes");
        List<CauseDTO> result = causeRepository.findAll().stream()
            .map(causeMapper::causeToCauseDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     *  Get all the causes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CauseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Causes");
        Page<Cause> result = causeRepository.findAll(pageable);
        return result.map(cause -> causeMapper.causeToCauseDTO(cause));
    }

    /**
     *  Get one cause by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CauseDTO findOne(Long id) {
        log.debug("Request to get Cause : {}", id);
        Cause cause = causeRepository.findOne(id);
        CauseDTO causeDTO = causeMapper.causeToCauseDTO(cause);
        return causeDTO;
    }

    /**
     *  Delete the  cause by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cause : {}", id);
        causeRepository.delete(id);
    }
}
