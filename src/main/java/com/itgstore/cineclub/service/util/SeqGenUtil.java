/**
 * 
 */
package com.itgstore.cineclub.service.util;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgstore.cineclub.domain.SeqGen;
import com.itgstore.cineclub.repository.SeqGenRepository;


/**
 * @author : p.djomga
 * @date : 15 déc. 2018
 *
 */
@Service
@Transactional(readOnly = true)
public class SeqGenUtil {
	
	private final Logger log = LoggerFactory.getLogger(SeqGenUtil.class);
	private static final int DEFAULT_CODE_SIZE = 7;
	
	SeqGenRepository seqGenRepository;

	public SeqGenUtil(SeqGenRepository seqGenRepository) {
		
		this.seqGenRepository = seqGenRepository;
	}
	
	public Long nextId(String code) {
		
		Optional<SeqGen> sg = seqGenRepository.findOneByCode(code);
		if(sg.isPresent()) {
			SeqGen sge = sg.get();
			long current = sge.getNextid();
			
			sge = sge.increment();
			seqGenRepository.save(sge);
			
			return current;
			
		}else {
			SeqGen sge = new SeqGen(code, 2L);
			seqGenRepository.save(sge);
			return 1L;
		}		
	}
	
	public String nextCode(String code, int size, String prefix) {
		
		Long nextid = nextId(code);		
		
		return prefix + StringUtils.leftPad(String.valueOf(nextid), size, '0');
	}
	
	public String nextCode(String code) {
		return nextCode(code, DEFAULT_CODE_SIZE, "C");
	}
	
	public String nextCode(Class code) {
		return nextCode(code.getSimpleName(), DEFAULT_CODE_SIZE, "C");
	}
	
	public String nextCode(String code, String prefix) {
		return nextCode(code, DEFAULT_CODE_SIZE, prefix);
	}
	

}
