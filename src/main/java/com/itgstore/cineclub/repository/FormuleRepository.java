package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Formule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Formule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormuleRepository extends JpaRepository<Formule, Long> {

}
