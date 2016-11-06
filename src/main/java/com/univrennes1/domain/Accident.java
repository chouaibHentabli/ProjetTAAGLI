package com.univrennes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * entity Utilisateur {username String,password String,nom  String,prenom String,birthDate ZonedDateTime,hireDate ZonedDateTime,phoneNumber String,mail String}
 *
 */
@ApiModel(description = ""
    + "entity Utilisateur {username String,password String,nom  String,prenom String,birthDate ZonedDateTime,hireDate ZonedDateTime,phoneNumber String,mail String}"
    + "")
@Entity
@Table(name = "accident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_acc")
    private ZonedDateTime dateAcc;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @Column(name = "heure")
    private ZonedDateTime heure;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "nbr_blesse")
    private Integer nbrBlesse;

    @Column(name = "nbr_hospitalise")
    private Integer nbrHospitalise;

    @Column(name = "nbr_morts")
    private Integer nbrMorts;

    @Column(name = "nbr_indemne")
    private Integer nbrIndemne;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "accident")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Baac> baacs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "accident_cause",
               joinColumns = @JoinColumn(name="accidents_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="causes_id", referencedColumnName="ID"))
    private Set<Cause> causes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAcc() {
        return dateAcc;
    }

    public Accident dateAcc(ZonedDateTime dateAcc) {
        this.dateAcc = dateAcc;
        return this;
    }

    public void setDateAcc(ZonedDateTime dateAcc) {
        this.dateAcc = dateAcc;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public Accident dateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getHeure() {
        return heure;
    }

    public Accident heure(ZonedDateTime heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(ZonedDateTime heure) {
        this.heure = heure;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Accident latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Accident longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isStatus() {
        return status;
    }

    public Accident status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getNbrBlesse() {
        return nbrBlesse;
    }

    public Accident nbrBlesse(Integer nbrBlesse) {
        this.nbrBlesse = nbrBlesse;
        return this;
    }

    public void setNbrBlesse(Integer nbrBlesse) {
        this.nbrBlesse = nbrBlesse;
    }

    public Integer getNbrHospitalise() {
        return nbrHospitalise;
    }

    public Accident nbrHospitalise(Integer nbrHospitalise) {
        this.nbrHospitalise = nbrHospitalise;
        return this;
    }

    public void setNbrHospitalise(Integer nbrHospitalise) {
        this.nbrHospitalise = nbrHospitalise;
    }

    public Integer getNbrMorts() {
        return nbrMorts;
    }

    public Accident nbrMorts(Integer nbrMorts) {
        this.nbrMorts = nbrMorts;
        return this;
    }

    public void setNbrMorts(Integer nbrMorts) {
        this.nbrMorts = nbrMorts;
    }

    public Integer getNbrIndemne() {
        return nbrIndemne;
    }

    public Accident nbrIndemne(Integer nbrIndemne) {
        this.nbrIndemne = nbrIndemne;
        return this;
    }

    public void setNbrIndemne(Integer nbrIndemne) {
        this.nbrIndemne = nbrIndemne;
    }

    public Set<Baac> getBaacs() {
        return baacs;
    }

    public Accident baacs(Set<Baac> baacs) {
        this.baacs = baacs;
        return this;
    }

    public Accident addBaac(Baac baac) {
        baacs.add(baac);
        baac.setAccident(this);
        return this;
    }

    public Accident removeBaac(Baac baac) {
        baacs.remove(baac);
        baac.setAccident(null);
        return this;
    }

    public void setBaacs(Set<Baac> baacs) {
        this.baacs = baacs;
    }

    public Set<Cause> getCauses() {
        return causes;
    }

    public Accident causes(Set<Cause> causes) {
        this.causes = causes;
        return this;
    }

    public Accident addCause(Cause cause) {
        causes.add(cause);
        cause.getAccidents().add(this);
        return this;
    }

    public Accident removeCause(Cause cause) {
        causes.remove(cause);
        cause.getAccidents().remove(this);
        return this;
    }

    public void setCauses(Set<Cause> causes) {
        this.causes = causes;
    }

    public User getUser() {
        return user;
    }

    public Accident user(User utilisateur) {
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
        Accident accident = (Accident) o;
        if(accident.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, accident.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Accident{" +
            "id=" + id +
            ", dateAcc='" + dateAcc + "'" +
            ", dateCreation='" + dateCreation + "'" +
            ", heure='" + heure + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", status='" + status + "'" +
            ", nbrBlesse='" + nbrBlesse + "'" +
            ", nbrHospitalise='" + nbrHospitalise + "'" +
            ", nbrMorts='" + nbrMorts + "'" +
            ", nbrIndemne='" + nbrIndemne + "'" +
            '}';
    }
}
