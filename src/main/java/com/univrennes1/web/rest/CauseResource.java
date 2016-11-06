package com.univrennes1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univrennes1.domain.*;
import com.univrennes1.repository.UserRepository;
import com.univrennes1.service.*;
import com.univrennes1.service.dto.AccidentDTO;
import com.univrennes1.service.dto.RubriqueDTO;
import com.univrennes1.service.mapper.AccidentMapper;
import com.univrennes1.service.mapper.BaacMapper;
import com.univrennes1.service.mapper.CauseMapper;
import com.univrennes1.service.mapper.RubriqueMapper;
import com.univrennes1.web.rest.util.HeaderUtil;
import com.univrennes1.web.rest.util.PaginationUtil;
import com.univrennes1.service.dto.CauseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Cause.
 */
@RestController
@RequestMapping("/api")
public class CauseResource {

    private final Logger log = LoggerFactory.getLogger(CauseResource.class);

    @Inject
    private CauseService causeService;

    ////
    @Inject
    private AccidentService accService;

    @Inject
    private RubriqueService rubriqueService;

    @Inject
    private UserService userService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private RubriqueMapper rubriqueMapper;

    @Inject
    private AccidentMapper accMapper;
    @Inject
    private CauseMapper causeMapper;
    @Inject
    private BaacMapper baacMapper;
    @Inject
    private BaacService baacService;

    /**
     * POST  /causes : Create a new cause.
     *
     * @param causeDTO the causeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new causeDTO, or with status 400 (Bad Request) if the cause has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/causes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CauseDTO> createCause(@RequestBody CauseDTO causeDTO) throws URISyntaxException {
        log.debug("REST request to save Cause : {}", causeDTO);
        if (causeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cause", "idexists", "A new cause cannot already have an ID")).body(null);
        }
        CauseDTO result = causeService.save(causeDTO);
        return ResponseEntity.created(new URI("/api/causes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cause", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /causes : Updates an existing cause.
     *
     * @param causeDTO the causeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated causeDTO,
     * or with status 400 (Bad Request) if the causeDTO is not valid,
     * or with status 500 (Internal Server Error) if the causeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/causes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CauseDTO> updateCause(@RequestBody CauseDTO causeDTO) throws URISyntaxException {
        log.debug("REST request to update Cause : {}", causeDTO);
        if (causeDTO.getId() == null) {
            return createCause(causeDTO);
        }
        CauseDTO result = causeService.save(causeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cause", causeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /causes : get all the causes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of causes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/causes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CauseDTO>> getAllCauses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Causes");
        Page<CauseDTO> page = causeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/causes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /causes/:id : get the "id" cause.
     *
     * @param id the id of the causeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the causeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/causes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CauseDTO> getCause(@PathVariable Long id) {
        log.debug("REST request to get Cause : {}", id);
        CauseDTO causeDTO = causeService.findOne(id);
        return Optional.ofNullable(causeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /causes/:id : delete the "id" cause.
     *
     * @param id the id of the causeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/causes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCause(@PathVariable Long id) {
        log.debug("REST request to delete Cause : {}", id);
        causeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cause", id.toString())).build();
    }

}
