package com.itgstore.cineclub.service.util;

import java.time.Instant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.itgstore.cineclub.service.dto.SmsDTO;

public class SMSSender {
	private static String URL_SMS
	  = "http://137.74.43.214:9000/api/public/sendsms/v1/output=json?user=itgstore&password=itgstore";
	
	/**
	 * 
	 * @param numExp
	 * @param numDest
	 * @param msg
	 * @return
	 */
	public static SmsDTO  sendSMS(String numExp, String numDest, String msg) {
		RestTemplate restTemplate = new RestTemplate();	
		ResponseEntity<SmsDTO> response
  	          = restTemplate.getForEntity(URL_SMS + "&sender="+numExp+"&phone="+numDest+"&message="+msg+"&date_send="+Instant.now(), SmsDTO.class);
		return response.getBody();
	}
}
