package com.univrennes1.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Rubrique entity.
 */
public class RubriqueDTO implements Serializable {

    private Long id;

    private String nom;

    private Boolean rubUnique;

    private Boolean active;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public Boolean getRubUnique() {
        return rubUnique;
    }

    public void setRubUnique(Boolean rubUnique) {
        this.rubUnique = rubUnique;
    }
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RubriqueDTO rubriqueDTO = (RubriqueDTO) o;

        if ( ! Objects.equals(id, rubriqueDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RubriqueDTO{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", rubUnique='" + rubUnique + "'" +
            ", active='" + active + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
