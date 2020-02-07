package com.itgstore.cineclub.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgstore.cineclub.config.Constants;
import com.itgstore.cineclub.config.DiversConfigProperties;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
@Transactional
public class FileStorageService {
	 private final Path fileStorageLocation;
     
	 private final Path fileStorageContratLocation;
	  
	    @Autowired
	    public FileStorageService(DiversConfigProperties  fileStorageProperties) {
	    	
	        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
	                .toAbsolutePath().normalize();
	        this.fileStorageContratLocation = Paths.get(fileStorageProperties.getUploadDirContrats())
	                .toAbsolutePath().normalize();
	        
	        try {
	            Files.createDirectories(this.fileStorageLocation);
	            Files.createDirectories( this.fileStorageContratLocation);
	        } catch (Exception ex) {
	            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
	        }
	    }

	    


	    /**
	     *
	     * @param fileName
	     * @return
	     */

	    public Resource loadFileAsResource(String fileName) {	
	        try {
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new RuntimeException("File not found " + fileName);
	            }
	        } catch (MalformedURLException ex) {
	            throw new RuntimeException("File not found " + fileName, ex);
	        }
	        
	    }
	    
	    
	    /**
	     * 
	     * @param jasperPrint
	     * @param fileName
	     */
	    
	  public Integer saveContratMarchand(JasperPrint jasperPrint, String fileName) {
		  Path targetLocation = this.fileStorageContratLocation.resolve(fileName+Constants.EXTENSION_FILE);
		    File file = new File(targetLocation.toString());
			OutputStream output = null;
			try {
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				 return 0;
			}
			try {
				JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				return 0;
			}
		  
		  return 1;
	  }
	  
	  
	  /**
	   * recuperer le contrat d'un marchand 
	   * @param fileName
	   * @return
	   */
	  public Resource loadFileAsContratResource(String fileName) {	
	        try {
	            Path filePath = this.fileStorageContratLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new RuntimeException("File not found " + fileName);
	            }
	        } catch (MalformedURLException ex) {
	            throw new RuntimeException("File not found " + fileName, ex);
	        }
	        
	    }
}
