package com.itgstore.cineclub.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgstore.cineclub.domain.Authority;
import com.itgstore.cineclub.domain.RoleApp;
import com.itgstore.cineclub.domain.User;
import com.itgstore.cineclub.repository.RoleAppRepository;
import com.itgstore.cineclub.repository.UserRepository;
import com.itgstore.cineclub.service.dto.RoleAppDTO;
import com.itgstore.cineclub.service.mapper.RoleAppMapper;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;


/**
 * Service Implementation for managing PgwRoleApp.
 */
@Service
@Transactional
public class RoleAppService {

    private final Logger log = LoggerFactory.getLogger(RoleAppService.class);
    
    private static final String ENTITY_NAME = "pgwRoleApp";

    private final RoleAppRepository pgwRoleAppRepository;

    private final RoleAppMapper pgwRoleAppMapper;
    
    private final UserRepository userRepository;

    public RoleAppService(RoleAppRepository pgwRoleAppRepository, RoleAppMapper pgwRoleAppMapper, UserRepository userRepository) {
        this.pgwRoleAppRepository = pgwRoleAppRepository;
        this.pgwRoleAppMapper = pgwRoleAppMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a pgwRoleApp.
     *
     * @param pgwRoleAppDTO the entity to save
     * @return the persisted entity
     */
    public RoleAppDTO save(RoleAppDTO pgwRoleAppDTO) {
        log.debug("Request to save PgwRoleApp : {}", pgwRoleAppDTO);
         RoleApp pgwRoleApp = pgwRoleAppMapper.toEntity(pgwRoleAppDTO);
          
             if(!pgwRoleAppDTO.getPermissions().isEmpty()) {
            	 pgwRoleApp.setRoles(authoritiesFromStrings(pgwRoleAppDTO.getPermissions()));  
             }
         pgwRoleApp = pgwRoleAppRepository.save(pgwRoleApp);
         final Set<Authority> authorities = pgwRoleApp.getRoles();
         //formatage de le reponse après enregistrement
         RoleAppDTO result = pgwRoleAppMapper.toDto(pgwRoleApp); 
         
         // mise à jour des entités possédant ce rôle s'il existe
         List<User> users = userRepository.findUserByRoles(pgwRoleApp);
         if(!users.isEmpty()) {
        	log.debug("mise à jour du role des entités");
        	for ( User user : users ) {		
       		    user.setAuthorities(authoritiesFromStrings(pgwRoleAppDTO.getPermissions()));
        	}
        	userRepository.save(users);
         }
         
         if(!pgwRoleApp.getRoles().isEmpty()) {
        	 result.setPermissions(StringsFromAuthorities( pgwRoleApp.getRoles()));
         }      
         
        return result;
    }

    /**
     * Get all the pgwRoleApps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RoleAppDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PgwRoleApps");
        return pgwRoleAppRepository.findAll(pageable)
            .map(pgwRoleAppMapper::toDto);
    }

    /**
     * Get one pgwRoleApp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RoleAppDTO findOne(Long id) {
        log.debug("Request to get PgwRoleApp : {}", id);
        RoleApp pgwRoleApp = pgwRoleAppRepository.findOne(id);
        RoleAppDTO pgwRoleAppDTO = pgwRoleAppMapper.toDto(pgwRoleApp);
        if(!pgwRoleApp.getRoles().isEmpty()) {
        	pgwRoleAppDTO.setPermissions(StringsFromAuthorities( pgwRoleApp.getRoles())); 
        }       
        return pgwRoleAppDTO;
    }

    /**
     * Delete the pgwRoleApp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PgwRoleApp : {}", id);
        RoleApp pgwRoleApp = pgwRoleAppRepository.findOne(id);
       
        if(!pgwRoleApp.getRoles().isEmpty()) {
        	 throw new  BadRequestAlertException("Impossible de supprimer le rôle: Merçi de vérifier qu'il ne possède pas de permissions",ENTITY_NAME,
        			 "Impossible de supprimer le rôle: Merçi de vérifier qu'il ne possède pas de permissions");
        }
        
        List<User> users = userRepository.findUserByRoles(pgwRoleApp);
        
        if(!users.isEmpty()) {
        	throw new  BadRequestAlertException("Impossible de supprimer le rôle: Merçi de vérifier qu'il n'est "
         	   		+ "pas affecté à un utilisateur ",ENTITY_NAME,"Impossible de supprimer le rôle: Merçi de vérifier qu'il n'est pas affecté à un utilisateur");
        }
      
         pgwRoleAppRepository.delete(id);
      
    }
    
    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
    
    public Set<String> StringsFromAuthorities( Set<Authority> roles) {
    	Set<String> authorities = new HashSet<>();   
           for(Authority a : roles) {
        	   authorities.add(a.getName());
           }   	
    	return authorities;
    }
}
