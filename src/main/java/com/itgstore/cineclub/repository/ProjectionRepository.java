package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Projection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Projection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long> {

}
