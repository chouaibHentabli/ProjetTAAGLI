package com.univrennes1.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.univrennes1.domain.enumeration.Type;

/**
 * A DTO for the Variable entity.
 */
public class VariableDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Boolean obligatoire;

    private Type varType;

    private String varRegex;

    private Long rubriqueId;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
    }
    public Type getVarType() {
        return varType;
    }

    public void setVarType(Type varType) {
        this.varType = varType;
    }
    public String getVarRegex() {
        return varRegex;
    }

    public void setVarRegex(String varRegex) {
        this.varRegex = varRegex;
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VariableDTO variableDTO = (VariableDTO) o;

        if ( ! Objects.equals(id, variableDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VariableDTO{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", description='" + description + "'" +
            ", obligatoire='" + obligatoire + "'" +
            ", varType='" + varType + "'" +
            ", varRegex='" + varRegex + "'" +
            '}';
    }
}
