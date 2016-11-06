package com.univrennes1.service.impl;

import com.univrennes1.domain.User;
import com.univrennes1.repository.UserRepository;
import com.univrennes1.security.SecurityUtils;
import com.univrennes1.service.AccidentService;
import com.univrennes1.domain.Accident;
import com.univrennes1.repository.AccidentRepository;
import com.univrennes1.service.dto.AccidentDTO;
import com.univrennes1.service.dto.CauseDTO;
import com.univrennes1.service.mapper.AccidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Accident.
 */
@Service
@Transactional
public class AccidentServiceImpl implements AccidentService{

    private final Logger log = LoggerFactory.getLogger(AccidentServiceImpl.class);

    @Inject
    private AccidentRepository accidentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AccidentMapper accidentMapper;

    /**
     * Save a accident.
     *
     * @param accidentDTO the entity to save
     * @return the persisted entity
     */
    public AccidentDTO save(AccidentDTO accidentDTO) {
        log.debug("Request to save Accident : {}", accidentDTO);
        Accident accident = accidentMapper.accidentDTOToAccident(accidentDTO);
        accident = accidentRepository.save(accident);
        AccidentDTO result = accidentMapper.accidentToAccidentDTO(accident);
        return result;
    }



    /**
     *  Get all the causes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AccidentDTO> findAll() {
        log.debug("Request to get all Accidents");
        List<AccidentDTO> result = accidentRepository.findAll().stream()
            .map(accidentMapper::accidentToAccidentDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get all the accidents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities created by current user
     */
    @Transactional(readOnly = true)
    public Page<AccidentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accidents");
        String login = SecurityUtils.getCurrentUserLogin();
        Optional optUser  = userRepository.findOneByLogin(login);
        User user  = (User) optUser.get();
        Page<Accident> result = accidentRepository.findAllByUser(user.getId(), pageable);
        return result.map(accident -> accidentMapper.accidentToAccidentDTO(accident));
    }

    /**
     *  Get one accident by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AccidentDTO findOne(Long id) {
        log.debug("Request to get Accident : {}", id);
        Accident accident = accidentRepository.findOneWithEagerRelationships(id);
        AccidentDTO accidentDTO = accidentMapper.accidentToAccidentDTO(accident);
        return accidentDTO;
    }

    /**
     *  Delete the  accident by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Accident : {}", id);
        accidentRepository.delete(id);
    }
}
