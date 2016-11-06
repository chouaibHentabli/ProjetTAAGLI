package com.univrennes1.service.impl;

import com.univrennes1.service.BaacService;
import com.univrennes1.domain.Baac;
import com.univrennes1.repository.BaacRepository;
import com.univrennes1.service.dto.BaacDTO;
import com.univrennes1.service.mapper.BaacMapper;
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
 * Service Implementation for managing Baac.
 */
@Service
@Transactional
public class BaacServiceImpl implements BaacService {

    private final Logger log = LoggerFactory.getLogger(BaacServiceImpl.class);

    @Inject
    private BaacRepository baacRepository;

    @Inject
    private BaacMapper baacMapper;

    /**
     * Save a baac.
     *
     * @param baacDTO the entity to save
     * @return the persisted entity
     */
    public BaacDTO save(BaacDTO baacDTO) {
        log.debug("Request to save Baac : {}", baacDTO);
        Baac baac = baacMapper.baacDTOToBaac(baacDTO);
        baac = baacRepository.save(baac);
        BaacDTO result = baacMapper.baacToBaacDTO(baac);
        return result;
    }

    /**
     * Get all the baacs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BaacDTO> findAll() {
        log.debug("Request to get all Baacs");
        List<BaacDTO> result = baacRepository.findAllWithEagerRelationships().stream()
            .map(baacMapper::baacToBaacDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     * Get all the baacs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BaacDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Baacs");
        Page<Baac> result = baacRepository.findAll(pageable);
        return result.map(baac -> baacMapper.baacToBaacDTO(baac));
    }

    /**
     * Get one baac by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BaacDTO findOne(Long id) {
        log.debug("Request to get Baac : {}", id);
        Baac baac = baacRepository.findOneWithEagerRelationships(id);
        BaacDTO baacDTO = baacMapper.baacToBaacDTO(baac);
        return baacDTO;
    }

    /**
     * Delete the  baac by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete Baac : {}", id);
        baacRepository.delete(id);
    }
}
