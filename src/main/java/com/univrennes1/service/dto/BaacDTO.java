package com.univrennes1.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Baac entity.
 */
public class BaacDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateCreation;

    private Long userId;

    private Set<ValeurDTO> valeurs = new HashSet<>();

    private Long accidentId;

    private Long rubriqueId;

    private Long vehiculeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long utilisateurId) {
        this.userId = utilisateurId;
    }

    public Set<ValeurDTO> getValeurs() {
        return valeurs;
    }

    public void setValeurs(Set<ValeurDTO> valeurs) {
        this.valeurs = valeurs;
    }


    public Long getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(Long accidentId) {
        this.accidentId = accidentId;
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long baacId) {
        this.vehiculeId = baacId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaacDTO baacDTO = (BaacDTO) o;

        if ( ! Objects.equals(id, baacDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BaacDTO{" +
            "id=" + id +
            ", dateCreation='" + dateCreation + "'" +
            '}';
    }
}
