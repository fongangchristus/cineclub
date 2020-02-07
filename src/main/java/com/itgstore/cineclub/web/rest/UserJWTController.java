package com.itgstore.cineclub.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgstore.cineclub.security.jwt.JWTConfigurer;
import com.itgstore.cineclub.security.jwt.TokenProvider;
import com.itgstore.cineclub.service.UserService;
import com.itgstore.cineclub.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;

     
    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;   
    }

    // @PostMapping("/authenticates")
    //@Timed
    public ResponseEntity<JWTToken> authorizes(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    
    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<JWTTokenPortalId> authorize(@Valid @RequestBody LoginVM loginVM) {
        Long portalId = -1L;
        String username = null;
        Long terminalId = 0L;
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);   
        username=authentication.getName();
        
        return new ResponseEntity<>(new JWTTokenPortalId(jwt,portalId,username,terminalId), httpHeaders, HttpStatus.OK);
    }
    
    
    
    
    
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
    
    static class JWTTokenPortalId {

        private String idToken;
        
        private Long portalId;
        
        private String username;
        
        private Long terminalId;
        
        JWTTokenPortalId(String idToken, Long portalId, String username, Long terminalId) {
            this.idToken = idToken;
            this.portalId = portalId;
            this.username=username;
            this.terminalId=terminalId;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
        
        @JsonProperty("portal_id")
		public Long getPortalId() {
			return portalId;
		}

		public void setPortalId(Long portalId) {
			this.portalId = portalId;
		}

		@JsonProperty("username")
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		@JsonProperty("terminal_id")
		public Long getTerminalId() {
			return terminalId;
		}

		public void setTerminalId(Long terminalId) {
			this.terminalId = terminalId;
		}  
        
		
    }
    
    
    
}
