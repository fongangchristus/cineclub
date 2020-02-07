package com.itgstore.cineclub.service.util;
///**
// * 
// */
//package com.itgstore.pgwitg.service.util;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//
///**
// * @author : p.djomga
// * @date : 19 oct. 2018
// *
// */
//public class XMLFileHelper {
//
//	public static void main(String[] argv)
//	  {
//	    try
//	    {
//	      Map<String, String> map = new HashMap();
//	      
//	      map = processResponse("");
//	      System.out.println("StatusCode: " + (String)map.get("StatusCode"));
//	      System.out.println("StatusDesc: " + (String)map.get("StatusDesc"));
//	    }
//	    catch (Exception sae)
//	    {
//	      sae.printStackTrace();
//	    }
//	  }
//	  
//	  private static void editXml()
//	  {
//	    try
//	    {
//	      String filepath = "C:\\data\\MoMoSOAPRequest.xml";
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node company = doc.getFirstChild();
//	      
//	      Node spId = doc.getElementsByTagName("spId").item(0);
//	      spId.setTextContent("35000001_test");
//	      
//	      Node spPassword = doc.getElementsByTagName("spPassword").item(0);
//	      spPassword.setTextContent("de96d901b3bad1db2aab76b7b0b202f2_test");
//	      
//	      Node bundleID = doc.getElementsByTagName("bundleID").item(0);
//	      bundleID.setTextContent("256000039_test");
//	      
//	      Node serviceId = doc.getElementsByTagName("serviceId").item(0);
//	      serviceId.setTextContent("35000001000035_test");
//	      Node serviceId2 = doc.getElementsByTagName("serviceId").item(1);
//	      serviceId2.setTextContent("200_test");
//	      
//	      Node timeStamp = doc.getElementsByTagName("timeStamp").item(0);
//	      timeStamp.setTextContent("20100727_test");
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(0);
//	      value1.setTextContent("10_test");
//	      Node value2 = doc.getElementsByTagName("value").item(0);
//	      value2.setTextContent("10_test");
//	      
//	      NodeList list = doc.getElementsByTagName("value");
//	      for (int i = 0; i < list.getLength(); i++)
//	      {
//	        Node n = list.item(i);
//	        n.setTextContent(n.getTextContent() + "_test");
//	      }
//	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
//	      Transformer transformer = transformerFactory.newTransformer();
//	      DOMSource source = new DOMSource(doc);
//	      StreamResult result = new StreamResult(new File(filepath));
//	      transformer.transform(source, result);
//	      
//	      System.out.println("Done");
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	    }
//	    catch (TransformerException tfe)
//	    {
//	      tfe.printStackTrace();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	    }
//	  }
//	  
//	  private static void editXml(String spIdText, String spPasswordText, String bundleIDText, String serviceIdText, String timeStampText, String serviceIdText2, String dueAmountText, String mSISDNNumText, String serviceIdText3)
//	  {
//	    try
//	    {
//	      String filepath = "/home/MoMoSOAPRequest.xml";
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node spId = doc.getElementsByTagName("spId").item(0);
//	      spId.setTextContent(spIdText);
//	      
//	      Node spPassword = doc.getElementsByTagName("spPassword").item(0);
//	      spPassword.setTextContent(spPasswordText);
//	      
//	      Node bundleID = doc.getElementsByTagName("bundleID").item(0);
//	      bundleID.setTextContent(bundleIDText);
//	      
//	      Node serviceId = doc.getElementsByTagName("serviceId").item(0);
//	      serviceId.setTextContent(serviceIdText);
//	      Node serviceId2 = doc.getElementsByTagName("serviceId").item(1);
//	      serviceId2.setTextContent(serviceIdText2);
//	      
//	      Node timeStamp = doc.getElementsByTagName("timeStamp").item(0);
//	      timeStamp.setTextContent(timeStampText);
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(0);
//	      value1.setTextContent(dueAmountText);
//	      Node value2 = doc.getElementsByTagName("value").item(1);
//	      value2.setTextContent(mSISDNNumText);
//	      Node value3 = doc.getElementsByTagName("value").item(3);
//	      value3.setTextContent(serviceIdText3);
//	      
//	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
//	      Transformer transformer = transformerFactory.newTransformer();
//	      DOMSource source = new DOMSource(doc);
//	      StreamResult result = new StreamResult(new File(filepath));
//	      transformer.transform(source, result);
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	    }
//	    catch (TransformerException tfe)
//	    {
//	      tfe.printStackTrace();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	    }
//	  }
//	  
//	  public static void editXml(String filepath, String spIdText, String spPasswordText, String bundleIDText, String serviceIdText, String timeStampText, String serviceIdText2, String dueAmountText, String mSISDNNumText, String serviceIdText3)
//	    throws FileNotFoundException, ParserConfigurationException, IOException, SAXException, TransformerConfigurationException, Exception
//	  {
//	    if (filepath == null) {
//	      throw new FileNotFoundException("FileNotFoundException");
//	    }
//	    try
//	    {
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node spId = doc.getElementsByTagName("spId").item(0);
//	      spId.setTextContent(spIdText);
//	      
//	      Node spPassword = doc.getElementsByTagName("spPassword").item(0);
//	      String genPwd = GenerateMD5.generatePwd(spIdText + spPasswordText + timeStampText);
//	      System.out.println("Hashed pwd: " + genPwd);
//	      spPassword.setTextContent(genPwd);
//	      
//	      Node bundleID = doc.getElementsByTagName("bundleID").item(0);
//	      bundleID.setTextContent(bundleIDText);
//	      
//	      Node serviceId = doc.getElementsByTagName("serviceId").item(0);
//	      serviceId.setTextContent(serviceIdText);
//	      Node serviceId2 = doc.getElementsByTagName("serviceId").item(1);
//	      serviceId2.setTextContent(serviceIdText2);
//	      
//	      Node timeStamp = doc.getElementsByTagName("timeStamp").item(0);
//	      System.out.println("timeStamp: " + timeStampText);
//	      timeStamp.setTextContent(timeStampText);
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(0);
//	      value1.setTextContent(dueAmountText);
//	      Node value2 = doc.getElementsByTagName("value").item(1);
//	      value2.setTextContent(mSISDNNumText);
//	      Node value3 = doc.getElementsByTagName("value").item(3);
//	      value3.setTextContent(serviceIdText3);
//	      
//	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
//	      Transformer transformer = transformerFactory.newTransformer();
//	      DOMSource source = new DOMSource(doc);
//	      StreamResult result = new StreamResult(new File(filepath));
//	      transformer.transform(source, result);
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	      throw new ParserConfigurationException();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	      throw new IOException();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	      throw new SAXException();
//	    }
//	    catch (TransformerConfigurationException tce)
//	    {
//	      tce.printStackTrace();
//	      throw new TransformerConfigurationException();
//	    }
//	    catch (Exception e)
//	    {
//	      e.printStackTrace();
//	      throw new Exception();
//	    }
//	  }
//	  
//	  private static Map<String, String> processResponse()
//	    throws ParserConfigurationException, IOException, SAXException
//	  {
//	    Map<String, String> map = new HashMap();
//	    try
//	    {
//	      String filepath = "C:\\data\\pgw_src\\pgw_src_31072017\\momo\\MoMoSOAPConfirmationResponse_10082017.xml";
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(3);
//	      map.put("StatusCode", value1.getTextContent());
//	      Node value2 = doc.getElementsByTagName("value").item(4);
//	      map.put("StatusDesc", value2.getTextContent());
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	      throw new ParserConfigurationException();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	      throw new IOException();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	      throw new SAXException();
//	    }
//	    return map;
//	  }
//	  
//	  private static Map<String, String> processResponseUpd(String filepath)
//	    throws ParserConfigurationException, IOException, SAXException
//	  {
//	    Map<String, String> map = new HashMap();
//	    try
//	    {
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(3);
//	      map.put("StatusCode", value1.getTextContent());
//	      Node value2 = doc.getElementsByTagName("value").item(4);
//	      map.put("StatusDesc", value2.getTextContent());
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	      throw new ParserConfigurationException();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	      throw new IOException();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	      throw new SAXException();
//	    }
//	    return map;
//	  }
//	  
//	  public static Map<String, String> processResponse(String filepath, String dir)
//	    throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, Exception
//	  {
//	    Map<String, String> map = new HashMap();
//	    if (filepath == null) {
//	      throw new FileNotFoundException("FileNotFoundException");
//	    }
//	    if (dir == null) {
//	      throw new FileNotFoundException("DirNotFoundException");
//	    }
//	    File file = null;
//	    try
//	    {
//	      file = FileHelper.findXMLFileByProcessingNum(filepath, dir);
//	    }
//	    catch (FileNotFoundException ex)
//	    {
//	      Logger.getLogger(XMLFileHelper.class.getName()).log(Level.SEVERE, null, ex);
//	      
//	      map.putIfAbsent("StatusCode", "-1");
//	      map.putIfAbsent("StatusDesc", "TimeOut or Response not available");
//	    }
//	    catch (Exception ex)
//	    {
//	      Logger.getLogger(XMLFileHelper.class.getName()).log(Level.SEVERE, null, ex);
//	      
//	      map.putIfAbsent("StatusCode", "-1");
//	      map.putIfAbsent("StatusDesc", "TimeOut or Response not available");
//	    }
//	    if (file == null)
//	    {
//	      map.putIfAbsent("StatusCode", "-1");
//	      map.putIfAbsent("StatusDesc", "TimeOut or Response not available");
//	    }
//	    try
//	    {
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(1);
//	      map.put("StatusCode", value1.getTextContent());
//	      Node value2 = doc.getElementsByTagName("value").item(2);
//	      map.put("StatusDesc", value2.getTextContent());
//	      
//	      Node value3 = doc.getElementsByTagName("value").item(0);
//	      map.put("ProcessingNumber", value3.getTextContent());
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	      throw new ParserConfigurationException();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	      throw new IOException();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	      throw new SAXException();
//	    }
//	    return map;
//	  }
//	  
//	  public static Map<String, String> processResponse(String filepath)
//	    throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, Exception
//	  {
//	    Map<String, String> map = new HashMap();
//	    if (filepath == null) {
//	      throw new FileNotFoundException("FileNotFoundException");
//	    }
//	    File file = null;
//	    try
//	    {
//	      file = FileHelper.findXMLFileByProcessingNum(filepath, "/home/input/");
//	    }
//	    catch (FileNotFoundException ex)
//	    {
//	      Logger.getLogger(XMLFileHelper.class.getName()).log(Level.SEVERE, null, ex);
//	      
//	      map.putIfAbsent("StatusCode", "-1");
//	      map.putIfAbsent("StatusDesc", "TimeOut or Response not available");
//	    }
//	    catch (Exception ex)
//	    {
//	      Logger.getLogger(XMLFileHelper.class.getName()).log(Level.SEVERE, null, ex);
//	      
//	      map.putIfAbsent("StatusCode", "-1");
//	      map.putIfAbsent("StatusDesc", "TimeOut or Response not available");
//	    }
//	    if (file == null)
//	    {
//	      map.putIfAbsent("StatusCode", "-1");
//	      map.putIfAbsent("StatusDesc", "TimeOut or Response not available");
//	    }
//	    try
//	    {
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      Document doc = docBuilder.parse(filepath);
//	      
//	      Node value1 = doc.getElementsByTagName("value").item(1);
//	      map.put("StatusCode", value1.getTextContent());
//	      Node value2 = doc.getElementsByTagName("value").item(2);
//	      map.put("StatusDesc", value2.getTextContent());
//	      
//	      Node value3 = doc.getElementsByTagName("value").item(0);
//	      map.put("ProcessingNumber", value3.getTextContent());
//	    }
//	    catch (ParserConfigurationException pce)
//	    {
//	      pce.printStackTrace();
//	      throw new ParserConfigurationException();
//	    }
//	    catch (IOException ioe)
//	    {
//	      ioe.printStackTrace();
//	      throw new IOException();
//	    }
//	    catch (SAXException sae)
//	    {
//	      sae.printStackTrace();
//	      throw new SAXException();
//	    }
//	    return map;
//	  }
//	  
//	  public static void stringToDom(String xmlSource, String fileAbsolutePath)
//	    throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException, Exception
//	  {
//	    if (fileAbsolutePath == null) {
//	      throw new Exception("File path required!");
//	    }
//	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	    DocumentBuilder builder = factory.newDocumentBuilder();
//	    Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
//	    
//	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
//	    Transformer transformer = transformerFactory.newTransformer();
//	    DOMSource source = new DOMSource(doc);
//	    
//	    StreamResult result = new StreamResult(new File(fileAbsolutePath));
//	    transformer.transform(source, result);
//	  }
//}
