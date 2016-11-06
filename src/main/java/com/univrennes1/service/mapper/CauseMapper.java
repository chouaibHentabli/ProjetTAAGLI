package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.CauseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Cause and its DTO CauseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CauseMapper {

    CauseDTO causeToCauseDTO(Cause cause);

    List<CauseDTO> causesToCauseDTOs(List<Cause> causes);

    @Mapping(target = "accidents", ignore = true)
    Cause causeDTOToCause(CauseDTO causeDTO);

    List<Cause> causeDTOsToCauses(List<CauseDTO> causeDTOs);
}
