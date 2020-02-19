package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.TicketReserve;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TicketReserve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketReserveRepository extends JpaRepository<TicketReserve, Long> {

}
