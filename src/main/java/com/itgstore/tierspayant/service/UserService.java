package com.itgstore.tierspayant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgstore.tierspayant.config.Constants;
import com.itgstore.tierspayant.domain.Authority;
import com.itgstore.tierspayant.domain.RoleApp;
import com.itgstore.tierspayant.domain.User;
import com.itgstore.tierspayant.repository.AuthorityRepository;
import com.itgstore.tierspayant.repository.RoleAppRepository;
import com.itgstore.tierspayant.repository.UserRepository;
import com.itgstore.tierspayant.security.AuthoritiesConstants;
import com.itgstore.tierspayant.security.SecurityUtils;
import com.itgstore.tierspayant.service.dto.UserDTO;
import com.itgstore.tierspayant.service.mapper.RoleAppMapper;
import com.itgstore.tierspayant.service.mapper.UserMapper;
import com.itgstore.tierspayant.service.util.RandomUtil;
import com.itgstore.tierspayant.web.rest.vm.KeyAndPasswordVM;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;
    
    private final RoleAppRepository pgwRoleAppRepository; 

    private final RoleAppMapper pgwRoleAppMapper;
    
    private final UserMapper userMapper;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, 
    		RoleAppRepository pgwRoleAppRepository, RoleAppMapper pgwRoleAppMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.pgwRoleAppRepository = pgwRoleAppRepository;
        this.pgwRoleAppMapper = pgwRoleAppMapper; 
        this.userMapper = userMapper; 
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
           .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }

    public User registerUser(UserDTO userDTO, String password) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
           newUser.setActivated(userDTO.isActivated());
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser = userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        
        
     // on recupère les authorities du rôle et on affecte à l'utilisateur;
        RoleApp pgwRoleApp = pgwRoleAppRepository.findOne(userDTO.getRoles().getId());
        if(!pgwRoleApp.getRoles().isEmpty()) {
        	userDTO.setAuthorities(stringsFromAuthorities( pgwRoleApp.getRoles()));
         }  
        
       if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findOne)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
 
        
        //String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        String encryptedPassword = passwordEncoder.encode(Constants.INITIAL_PWD_USER);
        user.setPassword(encryptedPassword);
        user.setRoles(pgwRoleApp);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(userDTO.isActivated());
        user.setActivationKey(RandomUtil.generateActivationKey());
        user = userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * 
     * @param roles
     * @return
     */
    public Set<String> stringsFromAuthorities( Set<Authority> roles) {
    	Set<String> authorities = new HashSet<>();   
           for(Authority a : roles) {
        	   authorities.add(a.getName());
           }   	
    	return authorities;
    }
    
    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
    	RoleApp pgwRoleApp = pgwRoleAppRepository.findOne(userDTO.getRoles().getId());
    	if(!pgwRoleApp.getRoles().isEmpty()) {
        	userDTO.setAuthorities(stringsFromAuthorities( pgwRoleApp.getRoles()));
         }  
        return Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                user.setRoles(pgwRoleApp);
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String encryptedPassword = passwordEncoder.encode(password);
                user.setPassword(encryptedPassword);
                log.debug("Changed password for User: {}", user);
            });
    }

    
    public KeyAndPasswordVM changePassword(KeyAndPasswordVM keyAndPasswordVM) {
    	Optional<User> optionalUser = userRepository.findOneByEmailIgnoreCase(keyAndPasswordVM.getEmail());
    	if(optionalUser.isPresent()) {
    		String encryptedPassword = passwordEncoder.encode(keyAndPasswordVM.getNewPassword());
    		User user = optionalUser.get(); 
    		user.setPassword(encryptedPassword);
    		userRepository.save(user);
    	} else {
    		keyAndPasswordVM.setErrorUser(1);
    	} 
    	
    	return keyAndPasswordVM;
    }
    
    
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }
    
    /**
     * 
     * @return
     */
    public List<Authority> getAuthority(){
    	return authorityRepository.findAll();
    }

    
    /**
     * 
     * @param idRole
     * @return
     */
    
    public List<UserDTO> findUserByRole(Long idRole){
    	RoleApp pgwRoleApp = pgwRoleAppRepository.findOne(idRole);
    	List<User> results= userRepository.findUserByRoles(pgwRoleApp);
    	return userMapper.usersToUserDTOs(results);
    }
}
