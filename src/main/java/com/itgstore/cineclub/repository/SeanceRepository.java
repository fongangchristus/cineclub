package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Seance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Seance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

}
