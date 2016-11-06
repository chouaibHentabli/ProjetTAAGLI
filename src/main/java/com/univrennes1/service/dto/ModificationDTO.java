package com.univrennes1.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Modification entity.
 */
public class ModificationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateMod;

    private Long baacId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public ZonedDateTime getDateMod() {
        return dateMod;
    }

    public void setDateMod(ZonedDateTime dateMod) {
        this.dateMod = dateMod;
    }

    public Long getBaacId() {
        return baacId;
    }

    public void setBaacId(Long baacId) {
        this.baacId = baacId;
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

        ModificationDTO modificationDTO = (ModificationDTO) o;

        if ( ! Objects.equals(id, modificationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModificationDTO{" +
            "id=" + id +
            ", dateMod='" + dateMod + "'" +
            '}';
    }
}
