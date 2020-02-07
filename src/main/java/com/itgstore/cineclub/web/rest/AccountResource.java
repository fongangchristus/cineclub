package com.itgstore.cineclub.web.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.domain.User;
import com.itgstore.cineclub.repository.UserRepository;
import com.itgstore.cineclub.security.SecurityUtils;
import com.itgstore.cineclub.security.jwt.TokenProvider;
import com.itgstore.cineclub.service.MailService;
import com.itgstore.cineclub.service.UserService;
import com.itgstore.cineclub.service.dto.UserDTO;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.errors.EmailAlreadyUsedException;
import com.itgstore.cineclub.web.rest.errors.EmailNotFoundException;
import com.itgstore.cineclub.web.rest.errors.InternalServerErrorException;
import com.itgstore.cineclub.web.rest.errors.InvalidPasswordException;
import com.itgstore.cineclub.web.rest.errors.LoginAlreadyUsedException;
import com.itgstore.cineclub.web.rest.vm.KeyAndPasswordVM;
import com.itgstore.cineclub.web.rest.vm.LoginVM;
import com.itgstore.cineclub.web.rest.vm.ManagedUserVM;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    
    private static final String ENTITY_NAME = "AccountResource";

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;
    
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).ifPresent(u -> {throw new LoginAlreadyUsedException();});
        userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).ifPresent(u -> {throw new EmailAlreadyUsedException();});
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping("/account")
    @Timed
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
   }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param password the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(password);
   }

  /**
   *     
   * @param keyAndPasswordVM
   */
    @PostMapping(path = "/account/change-password-merchant")
    @Timed
    public ResponseEntity<KeyAndPasswordVM> changePassword(@RequestBody KeyAndPasswordVM keyAndPasswordVM) {
    	KeyAndPasswordVM keyAndPwd = new KeyAndPasswordVM();
    	LoginVM loginVM = new LoginVM();
        if (!checkPasswordLength(keyAndPasswordVM.getNewPassword())) {
            //throw new InvalidPasswordException();
            keyAndPwd.setErrorPwd(1);
          return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd));
        }
        
        if (!keyAndPasswordVM.getNewPassword().equals(keyAndPasswordVM.getRetryNewPassword())) {
        	//throw new InvalidPasswordException();
        	keyAndPwd.setErrorPwd(1);
          return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd));
        }
        
        loginVM.setUsername(keyAndPasswordVM.getEmail());
        loginVM.setPassword(keyAndPasswordVM.getPassword());
        String jWTToken = authorizes(loginVM);
        
        if(jWTToken == null) {
        	keyAndPwd.setErrorPwd(1);
          return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd));
        }
          
        keyAndPwd = userService.changePassword(keyAndPasswordVM);
        
      return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd)); 
   }
    
    
    
    
    /**
     * 
     * @param keyAndPasswordVM
     * @return
     */
    @PostMapping(path = "/account/change-password-admin")
    @Timed
    public ResponseEntity<KeyAndPasswordVM> changePasswordAdmin(@RequestBody KeyAndPasswordVM keyAndPasswordVM) {
    	KeyAndPasswordVM keyAndPwd = new KeyAndPasswordVM();
    	LoginVM loginVM = new LoginVM();
        if (!checkPasswordLength(keyAndPasswordVM.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        
        if (!keyAndPasswordVM.getNewPassword().equals(keyAndPasswordVM.getRetryNewPassword())) {
        	throw new InvalidPasswordException(); 	
        }
        
        loginVM.setUsername(keyAndPasswordVM.getEmail());
        loginVM.setPassword(keyAndPasswordVM.getPassword());
        String jWTToken = authorizes(loginVM);
        
        if(jWTToken == null) {
        	throw new BadRequestAlertException("Utilisateur introuvable", ENTITY_NAME, "Utilisateur introuvable");
        }
        
        keyAndPwd = userService.changePassword(keyAndPasswordVM);
        
        if(keyAndPwd.getErrorUser() == 1) {
        	throw new BadRequestAlertException("Utilisateur introuvable", ENTITY_NAME, "Utilisateur introuvable");
        }
        
      return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd)); 
   }

    
    /**
     * 
     * @param loginVM
     * @return
     */
  public String authorizes(LoginVM loginVM) {
	String jwt = null;
    try {	
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwt = tokenProvider.createToken(authentication, false);
    }catch (BadCredentialsException e) {
		// TODO: handle exception
    	return jwt;
	}  
        return jwt;
    }

    
    
    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password-merchant/init")
    @Timed
    public void requestPasswordResetMerchant(@RequestBody String mail) {
       mailService.sendPasswordResetMailMerchant(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    
   
    
    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        System.out.println("===========================================================================");
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    
    
    
    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password-merchand/finish")
    @Timed
    public ResponseEntity<KeyAndPasswordVM> finishPasswordResetMerchand(@RequestBody KeyAndPasswordVM keyAndPassword) {
        System.out.println("===========================================================================");
        KeyAndPasswordVM keyAndPwd = new KeyAndPasswordVM();
         
        keyAndPwd.setKey(keyAndPassword.getKey());
        
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            //throw new InvalidPasswordException();
        	keyAndPwd.setErrorPwd(1);
          return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd));
        }
        
        if(!keyAndPassword.getNewPassword().equals(keyAndPassword.getRetryNewPassword())) {
        	keyAndPwd.setErrorPwd(1);
          return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd));	
        }
        
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            //throw new InternalServerErrorException("No user was found for this reset key");
            keyAndPwd.setErrorUser(1);
        }
             
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyAndPwd));
    }
    
    
    
    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
