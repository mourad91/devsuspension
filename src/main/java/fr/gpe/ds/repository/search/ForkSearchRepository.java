package fr.gpe.ds.repository.search;

import fr.gpe.ds.domain.Fork;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Fork entity.
 */
public interface ForkSearchRepository extends ElasticsearchRepository<Fork, Long> {
}
