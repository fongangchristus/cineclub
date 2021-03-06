package com.itgstore.cineclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgstore.cineclub.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
