package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Cinema;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cinema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}
