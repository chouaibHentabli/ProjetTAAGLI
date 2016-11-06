package com.univrennes1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Modification.
 */
@Entity
@Table(name = "modification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Modification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_mod")
    private ZonedDateTime dateMod;

    @ManyToOne
    private Baac baac;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ZonedDateTime getDateMod() {
        return dateMod;
    }

    public Modification dateMod(ZonedDateTime dateMod) {
        this.dateMod = dateMod;
        return this;
    }

    public void setDateMod(ZonedDateTime dateMod) {
        this.dateMod = dateMod;
    }

    public Baac getBaac() {
        return baac;
    }

    public Modification baac(Baac baac) {
        this.baac = baac;
        return this;
    }

    public void setBaac(Baac baac) {
        this.baac = baac;
    }


    public User getUser() {
        return user;
    }

    public Modification user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Modification modification = (Modification) o;
        if (modification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Modification{" +
            "id=" + id +
            ", dateMod='" + dateMod + "'" +
            '}';
    }
}
