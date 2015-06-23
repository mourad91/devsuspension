package fr.gpe.ds.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.gpe.ds.domain.Shock;
import fr.gpe.ds.repository.ShockRepository;
import fr.gpe.ds.repository.search.ShockSearchRepository;
import fr.gpe.ds.web.rest.dto.ShockDTO;
import fr.gpe.ds.web.rest.mapper.ShockMapper;
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
 * REST controller for managing Shock.
 */
@RestController
@RequestMapping("/api")
public class ShockResource {

    private final Logger log = LoggerFactory.getLogger(ShockResource.class);

    @Inject
    private ShockRepository shockRepository;

    @Inject
    private ShockMapper shockMapper;

    @Inject
    private ShockSearchRepository shockSearchRepository;

    /**
     * POST  /shocks -> Create a new shock.
     */
    @RequestMapping(value = "/shocks",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ShockDTO shockDTO) throws URISyntaxException {
        log.debug("REST request to save Shock : {}", shockDTO);
        if (shockDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shock cannot already have an ID").build();
        }
        Shock shock = shockMapper.shockDTOToShock(shockDTO);
        shockRepository.save(shock);
        shockSearchRepository.save(shock);
        return ResponseEntity.created(new URI("/api/shocks/" + shockDTO.getId())).build();
    }

    /**
     * PUT  /shocks -> Updates an existing shock.
     */
    @RequestMapping(value = "/shocks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ShockDTO shockDTO) throws URISyntaxException {
        log.debug("REST request to update Shock : {}", shockDTO);
        if (shockDTO.getId() == null) {
            return create(shockDTO);
        }
        Shock shock = shockMapper.shockDTOToShock(shockDTO);
        shockRepository.save(shock);
        shockSearchRepository.save(shock);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shocks -> get all the shocks.
     */
    @RequestMapping(value = "/shocks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ShockDTO> getAll() {
        log.debug("REST request to get all Shocks");
        return shockRepository.findAll().stream()
            .map(shock -> shockMapper.shockToShockDTO(shock))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /shocks/:id -> get the "id" shock.
     */
    @RequestMapping(value = "/shocks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShockDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Shock : {}", id);
        return Optional.ofNullable(shockRepository.findOne(id))
            .map(shockMapper::shockToShockDTO)
            .map(shockDTO -> new ResponseEntity<>(
                shockDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shocks/:id -> delete the "id" shock.
     */
    @RequestMapping(value = "/shocks/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Shock : {}", id);
        shockRepository.delete(id);
        shockSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/shocks/:query -> search for the shock corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/shocks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Shock> search(@PathVariable String query) {
        return StreamSupport
            .stream(shockSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
