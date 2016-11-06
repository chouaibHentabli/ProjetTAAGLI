package com.univrennes1.service.mapper;

import com.univrennes1.domain.*;
import com.univrennes1.service.dto.BaacDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Baac and its DTO BaacDTO.
 */
@Mapper(componentModel = "spring", uses = {ValeurMapper.class, })
public interface BaacMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "accident.id", target = "accidentId")
    @Mapping(source = "rubrique.id", target = "rubriqueId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    BaacDTO baacToBaacDTO(Baac baac);

    List<BaacDTO> baacsToBaacDTOs(List<Baac> baacs);

    @Mapping(target = "modifications", ignore = true)
    @Mapping(target = "usagers", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "accidentId", target = "accident")
    @Mapping(source = "rubriqueId", target = "rubrique")
    @Mapping(source = "vehiculeId", target = "vehicule")
    Baac baacDTOToBaac(BaacDTO baacDTO);

    List<Baac> baacDTOsToBaacs(List<BaacDTO> baacDTOs);

    default Valeur valeurFromId(Long id) {
        if (id == null) {
            return null;
        }
        Valeur valeur = new Valeur();
        valeur.setId(id);
        return valeur;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User utilisateur = new User();
        utilisateur.setId(id);
        return utilisateur;
    }

    default Accident accidentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Accident accident = new Accident();
        accident.setId(id);
        return accident;
    }

    default Rubrique rubriqueFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rubrique rubrique = new Rubrique();
        rubrique.setId(id);
        return rubrique;
    }

    default Baac baacFromId(Long id) {
        if (id == null) {
            return null;
        }
        Baac baac = new Baac();
        baac.setId(id);
        return baac;
    }
}
