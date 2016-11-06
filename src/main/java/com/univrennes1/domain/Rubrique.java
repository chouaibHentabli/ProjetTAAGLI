package com.univrennes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 *
 */
@ApiModel(description = ""
    + "not an ignored comment                                                 "
    + "")
@Entity
@Table(name = "rubrique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rubrique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "rub_unique")
    private Boolean rubUnique;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "rubrique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Variable> variables = new HashSet<>();

    @OneToMany(mappedBy = "rubrique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Baac> baacs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public Rubrique nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isRubUnique() {
        return rubUnique;
    }

    public Rubrique rubUnique(Boolean rubUnique) {
        this.rubUnique = rubUnique;
        return this;
    }

    public void setRubUnique(Boolean rubUnique) {
        this.rubUnique = rubUnique;
    }

    public Boolean isActive() {
        return active;
    }

    public Rubrique active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Rubrique description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public Rubrique variables(Set<Variable> variables) {
        this.variables = variables;
        return this;
    }

    public Rubrique addVariable(Variable variable) {
        variables.add(variable);
        variable.setRubrique(this);
        return this;
    }

    public Rubrique removeVariable(Variable variable) {
        variables.remove(variable);
        variable.setRubrique(null);
        return this;
    }

    public void setVariables(Set<Variable> variables) {
        this.variables = variables;
    }

    public Set<Baac> getBaacs() {
        return baacs;
    }

    public Rubrique baacs(Set<Baac> baacs) {
        this.baacs = baacs;
        return this;
    }

    public Rubrique addBaac(Baac baac) {
        baacs.add(baac);
        baac.setRubrique(this);
        return this;
    }

    public Rubrique removeBaac(Baac baac) {
        baacs.remove(baac);
        baac.setRubrique(null);
        return this;
    }

    public void setBaacs(Set<Baac> baacs) {
        this.baacs = baacs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rubrique rubrique = (Rubrique) o;
        if(rubrique.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rubrique.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rubrique{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", rubUnique='" + rubUnique + "'" +
            ", active='" + active + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
