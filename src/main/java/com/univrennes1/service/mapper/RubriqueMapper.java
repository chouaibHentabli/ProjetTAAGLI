package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.RubriqueDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Rubrique and its DTO RubriqueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RubriqueMapper {

    RubriqueDTO rubriqueToRubriqueDTO(Rubrique rubrique);

    List<RubriqueDTO> rubriquesToRubriqueDTOs(List<Rubrique> rubriques);

    @Mapping(target = "variables", ignore = true)
    @Mapping(target = "baacs", ignore = true)
    Rubrique rubriqueDTOToRubrique(RubriqueDTO rubriqueDTO);

    List<Rubrique> rubriqueDTOsToRubriques(List<RubriqueDTO> rubriqueDTOs);
}
