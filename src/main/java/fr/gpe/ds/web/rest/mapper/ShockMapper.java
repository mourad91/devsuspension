package fr.gpe.ds.web.rest.mapper;

import fr.gpe.ds.domain.*;
import fr.gpe.ds.web.rest.dto.ShockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shock and its DTO ShockDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShockMapper {

    ShockDTO shockToShockDTO(Shock shock);

    Shock shockDTOToShock(ShockDTO shockDTO);
}
