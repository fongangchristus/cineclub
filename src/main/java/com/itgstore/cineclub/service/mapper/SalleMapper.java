package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.SalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Salle and its DTO SalleDTO.
 */
@Mapper(componentModel = "spring", uses = {AdresseMapper.class, VilleMapper.class, CinemaMapper.class})
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {

    @Mapping(source = "adresse.id", target = "adresseId")
    @Mapping(source = "ville.libele", target = "villeLibele")
    @Mapping(source = "cinema.libele", target = "cinemaLibele")
    @Mapping(source = "ville.id", target = "villeId")
    @Mapping(source = "cinema.id", target = "cinemaId")
    SalleDTO toDto(Salle salle);

    @Mapping(source = "adresseId", target = "adresse")
    @Mapping(source = "villeId", target = "ville")
    @Mapping(source = "cinemaId", target = "cinema")
    Salle toEntity(SalleDTO salleDTO);

    default Salle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Salle salle = new Salle();
        salle.setId(id);
        return salle;
    }
}
