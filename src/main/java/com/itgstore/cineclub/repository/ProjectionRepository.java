package com.itgstore.cineclub.repository;

import com.itgstore.cineclub.domain.Projection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Projection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long> {
    @Query("select distinct projection from Projection projection left join fetch projection.listeSeances")
    List<Projection> findAllWithEagerRelationships();

    @Query("select projection from Projection projection left join fetch projection.listeSeances where projection.id =:id")
    Projection findOneWithEagerRelationships(@Param("id") Long id);

}
