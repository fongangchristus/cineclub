package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Salle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Salle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

}
