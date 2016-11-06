package com.univrennes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Baac.
 */
@Entity
@Table(name = "baac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Baac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "baac")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Modification> modifications = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Baac> usagers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "baac_valeur",
        joinColumns = @JoinColumn(name = "baacs_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "valeurs_id", referencedColumnName = "ID"))
    private Set<Valeur> valeurs = new HashSet<>();

    @ManyToOne
    private Accident accident;

    @ManyToOne
    private Rubrique rubrique;

    @ManyToOne
    private Baac vehicule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public Baac dateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Set<Modification> getModifications() {
        return modifications;
    }

    public Baac modifications(Set<Modification> modifications) {
        this.modifications = modifications;
        return this;
    }

    public Baac addModification(Modification modification) {
        modifications.add(modification);
        modification.setBaac(this);
        return this;
    }

    public Baac removeModification(Modification modification) {
        modifications.remove(modification);
        modification.setBaac(null);
        return this;
    }

    public void setModifications(Set<Modification> modifications) {
        this.modifications = modifications;
    }

    public Set<Baac> getUsagers() {
        return usagers;
    }

    public Baac usagers(Set<Baac> baacs) {
        this.usagers = baacs;
        return this;
    }

    public Baac addUsager(Baac baac) {
        usagers.add(baac);
        baac.setVehicule(this);
        return this;
    }

    public Baac removeUsager(Baac baac) {
        usagers.remove(baac);
        baac.setVehicule(null);
        return this;
    }

    public void setUsagers(Set<Baac> baacs) {
        this.usagers = baacs;
    }

    public Set<Valeur> getValeurs() {
        return valeurs;
    }

    public Baac valeurs(Set<Valeur> valeurs) {
        this.valeurs = valeurs;
        return this;
    }

    public Baac addValeur(Valeur valeur) {
        valeurs.add(valeur);
        valeur.getBaacs().add(this);
        return this;
    }

    public Baac removeValeur(Valeur valeur) {
        valeurs.remove(valeur);
        valeur.getBaacs().remove(this);
        return this;
    }

    public void setValeurs(Set<Valeur> valeurs) {
        this.valeurs = valeurs;
    }

    public Accident getAccident() {
        return accident;
    }

    public Baac accident(Accident accident) {
        this.accident = accident;
        return this;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public Baac rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public Baac getVehicule() {
        return vehicule;
    }

    public Baac vehicule(Baac baac) {
        this.vehicule = baac;
        return this;
    }

    public void setVehicule(Baac baac) {
        this.vehicule = baac;
    }

    public User getUser() {
        return user;
    }

    public Baac user(User utilisateur) {
        this.user = utilisateur;
        return this;
    }

    public void setUser(User utilisateur) {
        this.user = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Baac baac = (Baac) o;
        if (baac.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, baac.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Baac{" +
            "id=" + id +
            ", dateCreation='" + dateCreation + "'" +
            '}';
    }
}
