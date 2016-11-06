package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.ModificationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Modification and its DTO ModificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModificationMapper {

    @Mapping(source = "baac.id", target = "baacId")
    @Mapping(source = "user.id", target = "userId")
    ModificationDTO modificationToModificationDTO(Modification modification);

    List<ModificationDTO> modificationsToModificationDTOs(List<Modification> modifications);

    @Mapping(source = "baacId", target = "baac")
    @Mapping(source = "userId", target = "user")
    Modification modificationDTOToModification(ModificationDTO modificationDTO);

    List<Modification> modificationDTOsToModifications(List<ModificationDTO> modificationDTOs);

    default Baac baacFromId(Long id) {
        if (id == null) {
            return null;
        }
        Baac baac = new Baac();
        baac.setId(id);
        return baac;
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
