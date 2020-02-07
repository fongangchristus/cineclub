package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.PrixTicket;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrixTicket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrixTicketRepository extends JpaRepository<PrixTicket, Long> {

}
