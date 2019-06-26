package io.github.jhipster.application.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Personne entity. Can be a client or a project leader
 * @author sbiou
 */
@ApiModel(description = "Personne entity. Can be a client or a project leader @author sbiou")
@Entity
@Table(name = "personne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "personne")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Projet> responsableDes = new HashSet<>();

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

    public Personne name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Projet> getResponsableDes() {
        return responsableDes;
    }

    public Personne responsableDes(Set<Projet> projets) {
        this.responsableDes = projets;
        return this;
    }

    public Personne addResponsableDe(Projet projet) {
        this.responsableDes.add(projet);
        projet.setPersonne(this);
        return this;
    }

    public Personne removeResponsableDe(Projet projet) {
        this.responsableDes.remove(projet);
        projet.setPersonne(null);
        return this;
    }

    public void setResponsableDes(Set<Projet> projets) {
        this.responsableDes = projets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
