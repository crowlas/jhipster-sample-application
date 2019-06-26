package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Rapport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {

}
