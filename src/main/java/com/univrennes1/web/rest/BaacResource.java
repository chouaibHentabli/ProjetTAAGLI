package com.univrennes1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univrennes1.service.BaacService;
import com.univrennes1.web.rest.util.HeaderUtil;
import com.univrennes1.web.rest.util.PaginationUtil;
import com.univrennes1.service.dto.BaacDTO;
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
 * REST controller for managing Baac.
 */
@RestController
@RequestMapping("/api")
public class BaacResource {

    private final Logger log = LoggerFactory.getLogger(BaacResource.class);

    @Inject
    private BaacService baacService;

    /**
     * POST  /baacs : Create a new baac.
     *
     * @param baacDTO the baacDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new baacDTO, or with status 400 (Bad Request) if the baac has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/baacs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BaacDTO> createBaac(@RequestBody BaacDTO baacDTO) throws URISyntaxException {
        log.debug("REST request to save Baac : {}", baacDTO);
        if (baacDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("baac", "idexists", "A new baac cannot already have an ID")).body(null);
        }
        BaacDTO result = baacService.save(baacDTO);
        return ResponseEntity.created(new URI("/api/baacs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("baac", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /baacs : Updates an existing baac.
     *
     * @param baacDTO the baacDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated baacDTO,
     * or with status 400 (Bad Request) if the baacDTO is not valid,
     * or with status 500 (Internal Server Error) if the baacDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/baacs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BaacDTO> updateBaac(@RequestBody BaacDTO baacDTO) throws URISyntaxException {
        log.debug("REST request to update Baac : {}", baacDTO);
        if (baacDTO.getId() == null) {
            return createBaac(baacDTO);
        }
        BaacDTO result = baacService.save(baacDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("baac", baacDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /baacs : get all the baacs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of baacs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/baacs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BaacDTO>> getAllBaacs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Baacs");
        Page<BaacDTO> page = baacService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/baacs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /baacs/:id : get the "id" baac.
     *
     * @param id the id of the baacDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the baacDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/baacs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BaacDTO> getBaac(@PathVariable Long id) {
        log.debug("REST request to get Baac : {}", id);
        BaacDTO baacDTO = baacService.findOne(id);
        return Optional.ofNullable(baacDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /baacs/:id : delete the "id" baac.
     *
     * @param id the id of the baacDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/baacs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBaac(@PathVariable Long id) {
        log.debug("REST request to delete Baac : {}", id);
        baacService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("baac", id.toString())).build();
    }

}
