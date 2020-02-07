package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.CategorieFilm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CategorieFilm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieFilmRepository extends JpaRepository<CategorieFilm, Long> {

}
