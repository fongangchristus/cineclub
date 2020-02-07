package com.itgstore.cineclub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "diversconfig")
public class DiversConfigProperties {
	
	private String baseCpteOpPaiement;
	private String baseCpteOpReversement;
	private String baseCpteOpRetroCommission;
	private String baseCpteCommissionPgw;
	private String baseCpteTiersMarchand;
	private String baseCpteTresoPsp;
	private String baseCpteTresoPgw;
	private String baseUrl;
	private String urlWalletCreateAccount; 
	private String urlWalletFind;
	private String urlWallet;
	private String urlSms;
	private String urlActivationMarchand ;
	private String uploadDir;
	private String uploadDirContrats;
	
	public String getBaseCpteOpPaiement() {
		return baseCpteOpPaiement;
	}
	public void setBaseCpteOpPaiement(String baseCpteOpPaiement) {
		this.baseCpteOpPaiement = baseCpteOpPaiement;
	}
	public String getBaseCpteOpReversement() {
		return baseCpteOpReversement;
	}
	public void setBaseCpteOpReversement(String baseCpteOpReversement) {
		this.baseCpteOpReversement = baseCpteOpReversement;
	}
	public String getBaseCpteOpRetroCommission() {
		return baseCpteOpRetroCommission;
	}
	public void setBaseCpteOpRetroCommission(String baseCpteOpRetroCommission) {
		this.baseCpteOpRetroCommission = baseCpteOpRetroCommission;
	}
	public String getBaseCpteCommissionPgw() {
		return baseCpteCommissionPgw;
	}
	public void setBaseCpteCommissionPgw(String baseCpteCommissionPgw) {
		this.baseCpteCommissionPgw = baseCpteCommissionPgw;
	}
	public String getBaseCpteTiersMarchand() {
		return baseCpteTiersMarchand;
	}
	public void setBaseCpteTiersMarchand(String baseCpteTiersMarchand) {
		this.baseCpteTiersMarchand = baseCpteTiersMarchand;
	}
	public String getBaseCpteTresoPsp() {
		return baseCpteTresoPsp;
	}
	public void setBaseCpteTresoPsp(String baseCpteTresoPsp) {
		this.baseCpteTresoPsp = baseCpteTresoPsp;
	}
	public String getBaseCpteTresoPgw() {
		return baseCpteTresoPgw;
	}
	public void setBaseCpteTresoPgw(String baseCpteTresoPgw) {
		this.baseCpteTresoPgw = baseCpteTresoPgw;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getUrlWalletCreateAccount() {
		return urlWalletCreateAccount;
	}
	public void setUrlWalletCreateAccount(String urlWalletCreateAccount) {
		this.urlWalletCreateAccount = urlWalletCreateAccount;
	}
	public String getUrlWalletFind() {
		return urlWalletFind;
	}
	public void setUrlWalletFind(String urlWalletFind) {
		this.urlWalletFind = urlWalletFind;
	}
	public String getUrlWallet() {
		return urlWallet;
	}
	public void setUrlWallet(String urlWallet) {
		this.urlWallet = urlWallet;
	}
	public String getUrlSms() {
		return urlSms;
	}
	public void setUrlSms(String urlSms) {
		this.urlSms = urlSms;
	}
	public String getUrlActivationMarchand() {
		return urlActivationMarchand;
	}
	public void setUrlActivationMarchand(String urlActivationMarchand) {
		this.urlActivationMarchand = urlActivationMarchand;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String getUploadDirContrats() {
		return uploadDirContrats;
	}
	public void setUploadDirContrats(String uploadDirContrats) {
		this.uploadDirContrats = uploadDirContrats;
	}	
	
}
