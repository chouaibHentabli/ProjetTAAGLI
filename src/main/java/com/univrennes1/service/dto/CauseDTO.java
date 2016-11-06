package com.univrennes1.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Cause entity.
 */
public class CauseDTO implements Serializable {

    private Long id;

    private String causeIntitule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCauseIntitule() {
        return causeIntitule;
    }

    public void setCauseIntitule(String causeIntitule) {
        this.causeIntitule = causeIntitule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CauseDTO causeDTO = (CauseDTO) o;

        if ( ! Objects.equals(id, causeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CauseDTO{" +
            "id=" + id +
            ", causeIntitule='" + causeIntitule + "'" +
            '}';
    }
}
