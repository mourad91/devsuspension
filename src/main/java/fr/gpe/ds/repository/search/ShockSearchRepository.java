package fr.gpe.ds.repository.search;

import fr.gpe.ds.domain.Shock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Shock entity.
 */
public interface ShockSearchRepository extends ElasticsearchRepository<Shock, Long> {
}
