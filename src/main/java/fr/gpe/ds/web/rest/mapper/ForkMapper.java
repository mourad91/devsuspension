package fr.gpe.ds.web.rest.mapper;

import fr.gpe.ds.domain.*;
import fr.gpe.ds.web.rest.dto.ForkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fork and its DTO ForkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ForkMapper {

    ForkDTO forkToForkDTO(Fork fork);

    Fork forkDTOToFork(ForkDTO forkDTO);
}
