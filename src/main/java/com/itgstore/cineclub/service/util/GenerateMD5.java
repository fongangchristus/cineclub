package com.itgstore.cineclub.service.util;
///**
// * 
// */
//package com.itgstore.pgwitg.service.util;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class GenerateMD5
//{
//  public static void main(String[] args)
//  {
//    String password = "123456";
//    password = "2370110003910Huawei12320100727";
//    
//    MessageDigest md = null;
//    try
//    {
//      md = MessageDigest.getInstance("MD5");
//    }
//    catch (NoSuchAlgorithmException ex)
//    {
//      Logger.getLogger(GenerateMD5.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    md.update(password.getBytes());
//    
//    byte[] byteData = md.digest();
//    
//    StringBuffer sb = new StringBuffer();
//    for (int i = 0; i < byteData.length; i++) {
//      sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
//    }
//    System.out.println("Digest(in hex format):: " + sb.toString());
//    
//    StringBuffer hexString = new StringBuffer();
//    for (int i = 0; i < byteData.length; i++)
//    {
//      String hex = Integer.toHexString(0xFF & byteData[i]);
//      if (hex.length() == 1) {
//        hexString.append('0');
//      }
//      hexString.append(hex);
//    }
//    System.out.println("Digest(in hex format):: " + hexString.toString());
//  }
//  
//  public static String generatePwd(String password)
//  {
//    MessageDigest md = null;
//    try
//    {
//      md = MessageDigest.getInstance("MD5");
//    }
//    catch (NoSuchAlgorithmException ex)
//    {
//      Logger.getLogger(GenerateMD5.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    md.update(password.getBytes());
//    
//    byte[] byteData = md.digest();
//    
//    StringBuffer sb = new StringBuffer();
//    for (int i = 0; i < byteData.length; i++) {
//      sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
//    }
//    return sb.toString();
//  }
//}
