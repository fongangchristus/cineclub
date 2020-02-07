package com.itgstore.cineclub.web.rest.vm;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    private String key;
    
    private String email;
  
    private String password;
 
    private String newPassword;
    
    private String retryNewPassword;
    
    private int errorUser;
    
    private int errorPwd;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

	public String getRetryNewPassword() {
		return retryNewPassword;
	}

	public void setRetryNewPassword(String retryNewPassword) {
		this.retryNewPassword = retryNewPassword;
	}

	public int getErrorUser() {
		return errorUser;
	}

	public void setErrorUser(int errorUser) {
		this.errorUser = errorUser;
	}

	public int getErrorPwd() {
		return errorPwd;
	}

	public void setErrorPwd(int errorPwd) {
		this.errorPwd = errorPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
