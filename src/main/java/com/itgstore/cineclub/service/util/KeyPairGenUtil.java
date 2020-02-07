/**
 * 
 */
package com.itgstore.cineclub.service.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.util.Pair;

/**
 * @author : p.djomga
 * @date : 4 janv. 2019
 *
 */
public class KeyPairGenUtil {
	
	public static Pair<String, String> generateKeyPair(String pass) throws NoSuchAlgorithmException, NoSuchProviderException {
		
//		
//		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");		
//		keyGen.initialize(512);
//		KeyPair pair = keyGen.generateKeyPair();
//        byte[] publicKey = pair.getPublic().getEncoded();
//        byte[] privateKey = pair.getPrivate().getEncoded();
//        
//        
//        StringBuffer retPubString = new StringBuffer();
//        for (int i = 0; i < publicKey.length; ++i) {
//        	retPubString.append(Integer.toHexString(0x0100 + (publicKey[i] & 0x00FF)).substring(1));
//        }
//        System.out.println(retPubString);
//        
//        StringBuffer retPrivString = new StringBuffer();
//        for (int i = 0; i < privateKey.length; ++i) {
//        	retPrivString.append(Integer.toHexString(0x0100 + (privateKey[i] & 0x00FF)).substring(1));
//        }
//        System.out.println(retPrivString);
	        

		String retPubString = RandomStringUtils.random(20, true, true);
		String retPrivString = RandomStringUtils.random(20, true, true);
		
        Pair<String, String> valeur = Pair.of(retPubString.toString(), retPrivString.toString()) ;
        
        return valeur;
		
		
		
	}
	
	

}
