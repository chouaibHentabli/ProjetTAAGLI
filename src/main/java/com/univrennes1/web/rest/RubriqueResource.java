package com.univrennes1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univrennes1.service.RubriqueService;
import com.univrennes1.web.rest.util.HeaderUtil;
import com.univrennes1.web.rest.util.PaginationUtil;
import com.univrennes1.service.dto.RubriqueDTO;
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
 * REST controller for managing Rubrique.
 */
@RestController
@RequestMapping("/api")
public class RubriqueResource {

    private final Logger log = LoggerFactory.getLogger(RubriqueResource.class);
        
    @Inject
    private RubriqueService rubriqueService;

    /**
     * POST  /rubriques : Create a new rubrique.
     *
     * @param rubriqueDTO the rubriqueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rubriqueDTO, or with status 400 (Bad Request) if the rubrique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rubriques",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RubriqueDTO> createRubrique(@RequestBody RubriqueDTO rubriqueDTO) throws URISyntaxException {
        log.debug("REST request to save Rubrique : {}", rubriqueDTO);
        if (rubriqueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rubrique", "idexists", "A new rubrique cannot already have an ID")).body(null);
        }
        RubriqueDTO result = rubriqueService.save(rubriqueDTO);
        return ResponseEntity.created(new URI("/api/rubriques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rubrique", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rubriques : Updates an existing rubrique.
     *
     * @param rubriqueDTO the rubriqueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rubriqueDTO,
     * or with status 400 (Bad Request) if the rubriqueDTO is not valid,
     * or with status 500 (Internal Server Error) if the rubriqueDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rubriques",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RubriqueDTO> updateRubrique(@RequestBody RubriqueDTO rubriqueDTO) throws URISyntaxException {
        log.debug("REST request to update Rubrique : {}", rubriqueDTO);
        if (rubriqueDTO.getId() == null) {
            return createRubrique(rubriqueDTO);
        }
        RubriqueDTO result = rubriqueService.save(rubriqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rubrique", rubriqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rubriques : get all the rubriques.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rubriques in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rubriques",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RubriqueDTO>> getAllRubriques(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Rubriques");
        Page<RubriqueDTO> page = rubriqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rubriques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rubriques/:id : get the "id" rubrique.
     *
     * @param id the id of the rubriqueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rubriqueDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rubriques/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RubriqueDTO> getRubrique(@PathVariable Long id) {
        log.debug("REST request to get Rubrique : {}", id);
        RubriqueDTO rubriqueDTO = rubriqueService.findOne(id);
        return Optional.ofNullable(rubriqueDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rubriques/:id : delete the "id" rubrique.
     *
     * @param id the id of the rubriqueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rubriques/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRubrique(@PathVariable Long id) {
        log.debug("REST request to delete Rubrique : {}", id);
        rubriqueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rubrique", id.toString())).build();
    }

}
