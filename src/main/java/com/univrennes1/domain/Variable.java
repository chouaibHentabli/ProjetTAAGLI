package com.univrennes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.univrennes1.domain.enumeration.Type;

/**
 * A Variable.
 */
@Entity
@Table(name = "variable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Variable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "obligatoire")
    private Boolean obligatoire;

    @Enumerated(EnumType.STRING)
    @Column(name = "var_type")
    private Type varType;

    @Column(name = "var_regex")
    private String varRegex;

    @OneToMany(mappedBy = "variable")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Valeur> valeurs = new HashSet<>();

    @ManyToOne
    private Rubrique rubrique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public Variable nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Variable description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isObligatoire() {
        return obligatoire;
    }

    public Variable obligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
        return this;
    }

    public void setObligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
    }

    public Type getVarType() {
        return varType;
    }

    public Variable varType(Type varType) {
        this.varType = varType;
        return this;
    }

    public void setVarType(Type varType) {
        this.varType = varType;
    }

    public String getVarRegex() {
        return varRegex;
    }

    public Variable varRegex(String varRegex) {
        this.varRegex = varRegex;
        return this;
    }

    public void setVarRegex(String varRegex) {
        this.varRegex = varRegex;
    }

    public Set<Valeur> getValeurs() {
        return valeurs;
    }

    public Variable valeurs(Set<Valeur> valeurs) {
        this.valeurs = valeurs;
        return this;
    }

    public Variable addValeur(Valeur valeur) {
        valeurs.add(valeur);
        valeur.setVariable(this);
        return this;
    }

    public Variable removeValeur(Valeur valeur) {
        valeurs.remove(valeur);
        valeur.setVariable(null);
        return this;
    }

    public void setValeurs(Set<Valeur> valeurs) {
        this.valeurs = valeurs;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public Variable rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variable variable = (Variable) o;
        if(variable.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, variable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Variable{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", description='" + description + "'" +
            ", obligatoire='" + obligatoire + "'" +
            ", varType='" + varType + "'" +
            ", varRegex='" + varRegex + "'" +
            '}';
    }
}
