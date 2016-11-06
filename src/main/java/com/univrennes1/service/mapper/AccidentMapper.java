package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.AccidentDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity Accident and its DTO AccidentDTO.
 */
@Mapper(componentModel = "spring", uses = {CauseMapper.class,})
public interface AccidentMapper {

    @Mapping(source = "user.id", target = "userId")
    AccidentDTO accidentToAccidentDTO(Accident accident);

    List<AccidentDTO> accidentsToAccidentDTOs(List<Accident> accidents);

    @Mapping(target = "baacs", ignore = true)
    @Mapping(source = "userId", target = "user")
    Accident accidentDTOToAccident(AccidentDTO accidentDTO);

    List<Accident> accidentDTOsToAccidents(List<AccidentDTO> accidentDTOs);

    default Cause causeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cause cause = new Cause();
        cause.setId(id);
        return cause;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User utilisateur = new User();
        utilisateur.setId(id);
        return utilisateur;
    }
}
