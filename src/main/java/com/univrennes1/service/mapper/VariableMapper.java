package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.VariableDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Variable and its DTO VariableDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VariableMapper {

    @Mapping(source = "rubrique.id", target = "rubriqueId")
    VariableDTO variableToVariableDTO(Variable variable);

    List<VariableDTO> variablesToVariableDTOs(List<Variable> variables);

    @Mapping(target = "valeurs", ignore = true)
    @Mapping(source = "rubriqueId", target = "rubrique")
    Variable variableDTOToVariable(VariableDTO variableDTO);

    List<Variable> variableDTOsToVariables(List<VariableDTO> variableDTOs);

    default Rubrique rubriqueFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rubrique rubrique = new Rubrique();
        rubrique.setId(id);
        return rubrique;
    }
}
