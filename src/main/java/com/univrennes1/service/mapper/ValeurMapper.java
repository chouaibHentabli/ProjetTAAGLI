package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.ValeurDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Valeur and its DTO ValeurDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ValeurMapper {

    @Mapping(source = "variable.id", target = "variableId")
    ValeurDTO valeurToValeurDTO(Valeur valeur);

    List<ValeurDTO> valeursToValeurDTOs(List<Valeur> valeurs);

    @Mapping(source = "variableId", target = "variable")
    @Mapping(target = "baacs", ignore = true)
    Valeur valeurDTOToValeur(ValeurDTO valeurDTO);

    List<Valeur> valeurDTOsToValeurs(List<ValeurDTO> valeurDTOs);

    default Variable variableFromId(Long id) {
        if (id == null) {
            return null;
        }
        Variable variable = new Variable();
        variable.setId(id);
        return variable;
    }
}
