package fr.gpe.ds.repository;

import fr.gpe.ds.domain.Shock;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shock entity.
 */
public interface ShockRepository extends JpaRepository<Shock,Long> {

}
