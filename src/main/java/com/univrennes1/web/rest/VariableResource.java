package com.univrennes1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univrennes1.service.VariableService;
import com.univrennes1.web.rest.util.HeaderUtil;
import com.univrennes1.web.rest.util.PaginationUtil;
import com.univrennes1.service.dto.VariableDTO;
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
 * REST controller for managing Variable.
 */
@RestController
@RequestMapping("/api")
public class VariableResource {

    private final Logger log = LoggerFactory.getLogger(VariableResource.class);
        
    @Inject
    private VariableService variableService;

    /**
     * POST  /variables : Create a new variable.
     *
     * @param variableDTO the variableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variableDTO, or with status 400 (Bad Request) if the variable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/variables",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VariableDTO> createVariable(@RequestBody VariableDTO variableDTO) throws URISyntaxException {
        log.debug("REST request to save Variable : {}", variableDTO);
        if (variableDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("variable", "idexists", "A new variable cannot already have an ID")).body(null);
        }
        VariableDTO result = variableService.save(variableDTO);
        return ResponseEntity.created(new URI("/api/variables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("variable", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variables : Updates an existing variable.
     *
     * @param variableDTO the variableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variableDTO,
     * or with status 400 (Bad Request) if the variableDTO is not valid,
     * or with status 500 (Internal Server Error) if the variableDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/variables",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VariableDTO> updateVariable(@RequestBody VariableDTO variableDTO) throws URISyntaxException {
        log.debug("REST request to update Variable : {}", variableDTO);
        if (variableDTO.getId() == null) {
            return createVariable(variableDTO);
        }
        VariableDTO result = variableService.save(variableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("variable", variableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variables : get all the variables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of variables in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/variables",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VariableDTO>> getAllVariables(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Variables");
        Page<VariableDTO> page = variableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/variables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /variables/:id : get the "id" variable.
     *
     * @param id the id of the variableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variableDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/variables/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VariableDTO> getVariable(@PathVariable Long id) {
        log.debug("REST request to get Variable : {}", id);
        VariableDTO variableDTO = variableService.findOne(id);
        return Optional.ofNullable(variableDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /variables/:id : delete the "id" variable.
     *
     * @param id the id of the variableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/variables/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVariable(@PathVariable Long id) {
        log.debug("REST request to delete Variable : {}", id);
        variableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("variable", id.toString())).build();
    }

}
