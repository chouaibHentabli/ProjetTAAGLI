package com.univrennes1.service.impl;

import com.univrennes1.service.VariableService;
import com.univrennes1.domain.Variable;
import com.univrennes1.repository.VariableRepository;
import com.univrennes1.service.dto.VariableDTO;
import com.univrennes1.service.mapper.VariableMapper;
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
 * Service Implementation for managing Variable.
 */
@Service
@Transactional
public class VariableServiceImpl implements VariableService{

    private final Logger log = LoggerFactory.getLogger(VariableServiceImpl.class);
    
    @Inject
    private VariableRepository variableRepository;

    @Inject
    private VariableMapper variableMapper;

    /**
     * Save a variable.
     *
     * @param variableDTO the entity to save
     * @return the persisted entity
     */
    public VariableDTO save(VariableDTO variableDTO) {
        log.debug("Request to save Variable : {}", variableDTO);
        Variable variable = variableMapper.variableDTOToVariable(variableDTO);
        variable = variableRepository.save(variable);
        VariableDTO result = variableMapper.variableToVariableDTO(variable);
        return result;
    }

    /**
     *  Get all the variables.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<VariableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Variables");
        Page<Variable> result = variableRepository.findAll(pageable);
        return result.map(variable -> variableMapper.variableToVariableDTO(variable));
    }

    /**
     *  Get one variable by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VariableDTO findOne(Long id) {
        log.debug("Request to get Variable : {}", id);
        Variable variable = variableRepository.findOne(id);
        VariableDTO variableDTO = variableMapper.variableToVariableDTO(variable);
        return variableDTO;
    }

    /**
     *  Delete the  variable by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Variable : {}", id);
        variableRepository.delete(id);
    }
}
