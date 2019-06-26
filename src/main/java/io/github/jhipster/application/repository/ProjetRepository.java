package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Projet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Projet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

}
