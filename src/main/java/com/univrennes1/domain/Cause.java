package com.univrennes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cause.
 */
@Entity
@Table(name = "cause")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cause implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cause_intitule")
    private String causeIntitule;

    @ManyToMany(mappedBy = "causes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Accident> accidents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCauseIntitule() {
        return causeIntitule;
    }

    public Cause causeIntitule(String causeIntitule) {
        this.causeIntitule = causeIntitule;
        return this;
    }

    public void setCauseIntitule(String causeIntitule) {
        this.causeIntitule = causeIntitule;
    }

    public Set<Accident> getAccidents() {
        return accidents;
    }

    public Cause accidents(Set<Accident> accidents) {
        this.accidents = accidents;
        return this;
    }

    public Cause addAccident(Accident accident) {
        accidents.add(accident);
        accident.getCauses().add(this);
        return this;
    }

    public Cause removeAccident(Accident accident) {
        accidents.remove(accident);
        accident.getCauses().remove(this);
        return this;
    }

    public void setAccidents(Set<Accident> accidents) {
        this.accidents = accidents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cause cause = (Cause) o;
        if(cause.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cause.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cause{" +
            "id=" + id +
            ", causeIntitule='" + causeIntitule + "'" +
            '}';
    }
}
