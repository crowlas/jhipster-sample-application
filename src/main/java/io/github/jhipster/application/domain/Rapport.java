package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Rapport.
 */
@Entity
@Table(name = "rapport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month")
    private String month;

    /**
     * good, ok, bad
     */
    @ApiModelProperty(value = "good, ok, bad")
    @Column(name = "meteo")
    private String meteo;

    @Column(name = "resume")
    private String resume;

    @ManyToOne
    @JsonIgnoreProperties("rapports")
    private Projet projet;

    @OneToOne
    @JoinColumn(unique = true)
    private Personne redacteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public Rapport month(String month) {
        this.month = month;
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMeteo() {
        return meteo;
    }

    public Rapport meteo(String meteo) {
        this.meteo = meteo;
        return this;
    }

    public void setMeteo(String meteo) {
        this.meteo = meteo;
    }

    public String getResume() {
        return resume;
    }

    public Rapport resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Projet getProjet() {
        return projet;
    }

    public Rapport projet(Projet projet) {
        this.projet = projet;
        return this;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Personne getRedacteur() {
        return redacteur;
    }

    public Rapport redacteur(Personne personne) {
        this.redacteur = personne;
        return this;
    }

    public void setRedacteur(Personne personne) {
        this.redacteur = personne;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rapport)) {
            return false;
        }
        return id != null && id.equals(((Rapport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rapport{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", meteo='" + getMeteo() + "'" +
            ", resume='" + getResume() + "'" +
            "}";
    }
}
