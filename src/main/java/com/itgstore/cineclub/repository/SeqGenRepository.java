package com.itgstore.cineclub.repository;

import org.springframework.stereotype.Repository;

import com.itgstore.cineclub.domain.SeqGen;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the SeqGen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeqGenRepository extends JpaRepository<SeqGen, Long> {


	@Query("select sg.nextid from SeqGen sg where sg.code = :code")
	Optional<Long> getCurrentId(@Param("code") String code);
	
	Optional<SeqGen> findOneByCode(String code);
}
