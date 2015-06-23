package fr.gpe.ds.repository;

import fr.gpe.ds.domain.Fork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fork entity.
 */
public interface ForkRepository extends JpaRepository<Fork,Long> {

}
