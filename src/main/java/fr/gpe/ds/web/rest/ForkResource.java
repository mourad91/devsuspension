package fr.gpe.ds.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gpe.ds.domain.Fork;
import fr.gpe.ds.repository.ForkRepository;
import fr.gpe.ds.repository.search.ForkSearchRepository;
import fr.gpe.ds.web.rest.dto.ForkDTO;
import fr.gpe.ds.web.rest.mapper.ForkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Fork.
 */
@RestController
@RequestMapping("/api")
public class ForkResource {

    private final Logger log = LoggerFactory.getLogger(ForkResource.class);

    @Inject
    private ForkRepository forkRepository;

    @Inject
    private ForkMapper forkMapper;

    @Inject
    private ForkSearchRepository forkSearchRepository;

    /**
     * POST  /forks -> Create a new fork.
     */
    @RequestMapping(value = "/forks",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ForkDTO forkDTO) throws URISyntaxException {
        log.debug("REST request to save Fork : {}", forkDTO);
        if (forkDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fork cannot already have an ID").build();
        }
        Fork fork = forkMapper.forkDTOToFork(forkDTO);
        forkRepository.save(fork);
        forkSearchRepository.save(fork);
        return ResponseEntity.created(new URI("/api/forks/" + forkDTO.getId())).build();
    }

    /**
     * PUT  /forks -> Updates an existing fork.
     */
    @RequestMapping(value = "/forks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ForkDTO forkDTO) throws URISyntaxException {
        log.debug("REST request to update Fork : {}", forkDTO);
        if (forkDTO.getId() == null) {
            return create(forkDTO);
        }
        Fork fork = forkMapper.forkDTOToFork(forkDTO);
        forkRepository.save(fork);
        forkSearchRepository.save(fork);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /forks -> get all the forks.
     */
    @RequestMapping(value = "/forks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ForkDTO> getAll() {
        log.debug("REST request to get all Forks");
        return forkRepository.findAll().stream()
            .map(fork -> forkMapper.forkToForkDTO(fork))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /forks/:id -> get the "id" fork.
     */
    @RequestMapping(value = "/forks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ForkDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Fork : {}", id);
        return Optional.ofNullable(forkRepository.findOne(id))
            .map(forkMapper::forkToForkDTO)
            .map(forkDTO -> new ResponseEntity<>(
                forkDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /forks/:id -> delete the "id" fork.
     */
    @RequestMapping(value = "/forks/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Fork : {}", id);
        forkRepository.delete(id);
        forkSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/forks/:query -> search for the fork corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/forks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fork> search(@PathVariable String query) {
        return StreamSupport
            .stream(forkSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
