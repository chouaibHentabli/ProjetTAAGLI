package com.univrennes1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univrennes1.service.AccidentService;
import com.univrennes1.web.rest.util.HeaderUtil;
import com.univrennes1.web.rest.util.PaginationUtil;
import com.univrennes1.service.dto.AccidentDTO;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Accident.
 */
@RestController
@RequestMapping("/api")
public class AccidentResource {

    private final Logger log = LoggerFactory.getLogger(AccidentResource.class);
        
    @Inject
    private AccidentService accidentService;

    /**
     * POST  /accidents : Create a new accident.
     *
     * @param accidentDTO the accidentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accidentDTO, or with status 400 (Bad Request) if the accident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/accidents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AccidentDTO> createAccident(@RequestBody AccidentDTO accidentDTO) throws URISyntaxException {
        log.debug("REST request to save Accident : {}", accidentDTO);
        if (accidentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("accident", "idexists", "A new accident cannot already have an ID")).body(null);
        }
        AccidentDTO result = accidentService.save(accidentDTO);
        return ResponseEntity.created(new URI("/api/accidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("accident", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accidents : Updates an existing accident.
     *
     * @param accidentDTO the accidentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accidentDTO,
     * or with status 400 (Bad Request) if the accidentDTO is not valid,
     * or with status 500 (Internal Server Error) if the accidentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/accidents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AccidentDTO> updateAccident(@RequestBody AccidentDTO accidentDTO) throws URISyntaxException {
        log.debug("REST request to update Accident : {}", accidentDTO);
        if (accidentDTO.getId() == null) {
            return createAccident(accidentDTO);
        }
        AccidentDTO result = accidentService.save(accidentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("accident", accidentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accidents : get all the accidents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accidents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/accidents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AccidentDTO>> getAllAccidents(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Accidents");
        Page<AccidentDTO> page = accidentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accidents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /accidents/:id : get the "id" accident.
     *
     * @param id the id of the accidentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accidentDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/accidents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AccidentDTO> getAccident(@PathVariable Long id) {
        log.debug("REST request to get Accident : {}", id);
        AccidentDTO accidentDTO = accidentService.findOne(id);
        return Optional.ofNullable(accidentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /accidents/:id : delete the "id" accident.
     *
     * @param id the id of the accidentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/accidents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAccident(@PathVariable Long id) {
        log.debug("REST request to delete Accident : {}", id);
        accidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("accident", id.toString())).build();
    }

}
