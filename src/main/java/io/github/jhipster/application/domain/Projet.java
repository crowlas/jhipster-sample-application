package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "projet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rapport> rapports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("projets")
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Projet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Rapport> getRapports() {
        return rapports;
    }

    public Projet rapports(Set<Rapport> rapports) {
        this.rapports = rapports;
        return this;
    }

    public Projet addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setProjet(this);
        return this;
    }

    public Projet removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setProjet(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        this.rapports = rapports;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Projet personne(Personne personne) {
        this.personne = personne;
        return this;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
