package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Personne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
