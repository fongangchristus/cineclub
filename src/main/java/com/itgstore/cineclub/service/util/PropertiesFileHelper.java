package com.itgstore.cineclub.service.util;
///**
// * 
// */
//package com.itgstore.pgwitg.service.util;
//
//import java.io.InputStream;
//import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// * @author : p.djomga
// * @date : 19 oct. 2018
// *
// */
//public class PropertiesFileHelper {
//
//	public static String getPropertyValue(String propertyFile, String propertyName) throws Exception {
//		String res = null;
//		Properties prop = new Properties();
//		InputStream input = null;
//		if (propertyFile == null) {
//			throw new Exception("No properties file found!");
//		}
//		if (propertyName == null) {
//			throw new Exception("No property name found!");
//		}
//		try {
//			input = PropertiesFileHelper.class.getClassLoader().getResourceAsStream(propertyFile);
//			if (input == null) {
//				throw new Exception("No properties file found!");
//			}
//			prop.load(input);
//			res = prop.getProperty(propertyName);
//		} catch (Exception e) {
//			throw e;
//		}
//		return res;
//	}
//
//	public static void main(String[] args) {
//		try {
//			System.out.println("log4j.appender.file.File value is"
//					+ getPropertyValue("log4j.properties", "log4j.appender.file.File"));
//		} catch (Exception ex) {
//			Logger.getLogger(PropertiesFileHelper.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
//
//}
