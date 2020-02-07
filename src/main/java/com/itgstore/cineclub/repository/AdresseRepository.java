package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Adresse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Adresse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

}
