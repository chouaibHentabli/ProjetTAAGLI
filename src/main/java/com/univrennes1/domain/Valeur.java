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
 * Task entity.@author The JHipster team.                                      
 * 
 */
@ApiModel(description = ""
    + "Task entity.@author The JHipster team.                                 "
    + "")
@Entity
@Table(name = "valeur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Valeur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Variable variable;

    @ManyToMany(mappedBy = "valeurs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Baac> baacs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Valeur title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Valeur description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Variable getVariable() {
        return variable;
    }

    public Valeur variable(Variable variable) {
        this.variable = variable;
        return this;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Set<Baac> getBaacs() {
        return baacs;
    }

    public Valeur baacs(Set<Baac> baacs) {
        this.baacs = baacs;
        return this;
    }

    public Valeur addBaac(Baac baac) {
        baacs.add(baac);
        baac.getValeurs().add(this);
        return this;
    }

    public Valeur removeBaac(Baac baac) {
        baacs.remove(baac);
        baac.getValeurs().remove(this);
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
        Valeur valeur = (Valeur) o;
        if(valeur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, valeur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Valeur{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
