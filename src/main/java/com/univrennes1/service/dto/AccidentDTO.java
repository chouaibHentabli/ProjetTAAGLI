package com.univrennes1.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Accident entity.
 */
public class AccidentDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateAcc;

    private ZonedDateTime dateCreation;

    private ZonedDateTime heure;

    private Double latitude;

    private Double longitude;

    private Boolean status;

    private Integer nbrBlesse;

    private Integer nbrHospitalise;

    private Integer nbrMorts;

    private Integer nbrIndemne;


    private Set<CauseDTO> causes = new HashSet<>();

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateAcc() {
        return dateAcc;
    }

    public void setDateAcc(ZonedDateTime dateAcc) {
        this.dateAcc = dateAcc;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getHeure() {
        return heure;
    }

    public void setHeure(ZonedDateTime heure) {
        this.heure = heure;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getNbrBlesse() {
        return nbrBlesse;
    }

    public void setNbrBlesse(Integer nbrBlesse) {
        this.nbrBlesse = nbrBlesse;
    }

    public Integer getNbrHospitalise() {
        return nbrHospitalise;
    }

    public void setNbrHospitalise(Integer nbrHospitalise) {
        this.nbrHospitalise = nbrHospitalise;
    }

    public Integer getNbrMorts() {
        return nbrMorts;
    }

    public void setNbrMorts(Integer nbrMorts) {
        this.nbrMorts = nbrMorts;
    }

    public Integer getNbrIndemne() {
        return nbrIndemne;
    }

    public void setNbrIndemne(Integer nbrIndemne) {
        this.nbrIndemne = nbrIndemne;
    }

    public Set<CauseDTO> getCauses() {
        return causes;
    }

    public void setCauses(Set<CauseDTO> causes) {
        this.causes = causes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long utilisateurId) {
        this.userId = utilisateurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccidentDTO accidentDTO = (AccidentDTO) o;

        if (!Objects.equals(id, accidentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccidentDTO{" +
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
