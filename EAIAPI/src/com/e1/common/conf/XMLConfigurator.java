/*
* @(#)XMLConfigurator.java   1.0 2005/07/14
 */
package com.e1.common.conf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/14
 *  
 * XML Property 파일의 Configurator <br>
 * XML 포맷의 설정파일에서 Property 값을 추출한다.
 *
 * <pre>
 * 예) config.xml
 * <?xml version="1.0" encoding="euc-kr"?>
 * <config>
 *   <base>
 *     <logConf>log.conf</logConf>
 *     <database>
 *       <connection name="default">
 *         <db>oracle<db>
 *       </connection>
 *       <connection name="laputa">
 *         <db>unisql</db>
 *       </connection>
 *       <connection name="conan">
 *         <db>mssql</db>
 *       </connection>
 *     </database>
 *   </base>
 * </config>
 *
 * 위와 같은 설정에서 키의 값을 추출시 XPath를 따른다.
 * (see <a href="http://www.w3.org/TR/xpath">XML Path Language (XPath) Version 1.0 W3C Recommendation.</a>)
 *
 * getString("//base/logConf/text()") => log.conf
 * getStringArray("//base/database/connection/@name")
 *                => String [] = {"default", "laputa", "conan"} 로 반환
 * getString("//base/database/connnection[@name='conan']/db/text()") => mssql
 * </pre>
 */
public class XMLConfigurator extends Config {

  private Document document;

  /**
   * XMLConfigurator 생성자
   *
   * @param confFile 상대 또는 절대경로를 포함하는 xml 파일명
   * @exception      ConfigurationException
   */
  public XMLConfigurator(String confFile) throws ConfigurationException 
  {
    try 
    {
      parse(new FileInputStream( confFile ));
    } 
    catch (FileNotFoundException fne) 
    {
      throw new ConfigurationException(fne.getMessage(), fne);
    }
  }

  /**
   * XMLConfigurator 생성자
   *
   * @param is  XML 의 InputStream
   * @exception ConfigurationException
   */
  public XMLConfigurator(InputStream is) throws ConfigurationException {
    parse(is);
  }

  /**
   * key 에 해당되는 node 에 값을 셋팅한다.
   *
   * @param key    Property의 key
   * @param value
   */
  public void setValue(String key, String value) {
    if (value == null) return;

    try {
      Node node = XPathAPI.selectSingleNode(this.document, key);

      if( node != null ) {
        switch( node.getNodeType() ) {
          case Node.ELEMENT_NODE:
            NodeList nodeList = node.getChildNodes();
            Node childNode = null;

            for( int i=0; i<nodeList.getLength(); i++ ) {
              childNode = nodeList.item(i);
              if( childNode.getNodeType() == Node.TEXT_NODE ) {
                childNode.setNodeValue(value);
                return;
              }
            }

            node.appendChild( this.document.createTextNode(value) );

            break;

          default:
            node.setNodeValue(value);
          break;
        }
      }
    } catch(TransformerException te) {
      // ignore
    }
  }


  /**
   * key 에 해당되는 값을 String 로 return
   *
   * @param   key    Property의 key
   * @return  String
   */
  public String getString(String key) {
    String nodeValue = null;

    try {
      Node node = XPathAPI.selectSingleNode(this.document, key);

      if(node != null) {
        nodeValue = node.getNodeValue();
      }

      if( nodeValue == null ) {
        NodeList nodeList = node.getChildNodes();
        for(int i=0; i<nodeList.getLength(); i++) {
          if( nodeList.item(i).getNodeType() == Node.CDATA_SECTION_NODE ) {
            nodeValue = nodeList.item(i).getNodeValue();
            break;
          } else if( nodeList.item(i).getNodeType() == Node.TEXT_NODE ) {
            nodeValue = nodeList.item(i).getNodeValue();
          }
        } // end of for
      }
    } catch(TransformerException te) {
      // ignore
    } catch(NullPointerException e) {
      // ignore
    }

    return nodeValue;
  }

  /**
   * key 에 해당되는 값을 String Array 로 return
   *
   * @param   key    Property의 key
   * @return  String[]
   */
  public String [] getStringArray(String key) {
    String [] nodeValue = null;

    try {
      NodeList nodeList = XPathAPI.selectNodeList(this.document, key);

      nodeValue = new String[nodeList.getLength()];
      for(int i=0; i<nodeList.getLength(); i++) {
        nodeValue[i] = nodeList.item(i).getNodeValue();
      }
    } catch(TransformerException te) {
      // ignore
    }

    return nodeValue;
  }

  /**
   * XML 설정파일의 {@link org.w3c.dom.Document}를 반환
   *
   * @return  Document
   */
  public Document getDocument() {
    return this.document;
  }

  /**
   * key 에 해당하는 Node 를 반환
   *
   * @param nodeKey Node의 key( XPath 포맷 )
   * @return Node
   */
  public Node getNode(String nodeKey) {
    Node node = null;
    try {
      node = XPathAPI.selectSingleNode(this.document, nodeKey);
    } catch(TransformerException te) {
      // ignore
    }

    return node;
  }

  /**
   * This method writes a DOM document to a file
   *
   * @param file     output file
   */
  public void writeXML(File file) throws ConfigurationException {
    XMLConfigurator.writeXML(this.document, file);
  }

  /**
   * This method writes a DOM document to a file
   *
   * @param node     DOM Node for writing
   * @param file     output file
   */
  public static void writeXML(Node node, File file)
      throws ConfigurationException {
    try {
      // Prepare the DOM document for writing
      Source source = new DOMSource(node);

      // Prepare the output file
      Result result = new StreamResult(file);

      // Write the DOM document to the file
      Transformer xformer = TransformerFactory.newInstance().newTransformer();

      // Set the public and system id
      xformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
      xformer.setOutputProperty(OutputKeys.INDENT, "yes");
      //xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "config");
      //xformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "config.dtd");

      xformer.transform(source, result);
    } catch (TransformerConfigurationException tfce) {
      throw new ConfigurationException(tfce.getMessage(), tfce);
    } catch (TransformerException tfe) {
      throw new ConfigurationException(tfe.getMessage(), tfe);
    }
  }

  /**
   * This method writes a DOM document to a OutputStream
   *
   * @param outstream OutputStream
   */
  public void writeXML(OutputStream outstream)
      throws ConfigurationException  {
    XMLConfigurator.writeXML(this.document, outstream);
  }

  /**
   * This method writes a DOM document to a OutputStream
   *
   * @param node      DOM Node for writing
   * @param outstream OutputStream
   */
  public static void writeXML(Node node, OutputStream outstream)
      throws ConfigurationException {
    try {
      // Prepare the DOM document for writing
      Source source = new DOMSource(node);

      // Prepare the output file
      Result result = new StreamResult(outstream);

      // Write the DOM document to the stream
      Transformer xformer = TransformerFactory.newInstance().newTransformer();

      // Set the public and system id
      xformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
      xformer.setOutputProperty(OutputKeys.INDENT, "yes");
      //xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "config");
      //xformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "config.dtd");

      xformer.transform(source, result);
    } catch (TransformerConfigurationException tfce) {
      throw new ConfigurationException(tfce.getMessage(), tfce);
    } catch (TransformerException tfe) {
      throw new ConfigurationException(tfe.getMessage(), tfe);
    }
  }

  /**
   * XML Stream 을 파싱한다.
   * @param is xml stream
   * @throws ConfigurationException
   */
  private void parse(InputStream is) throws ConfigurationException 
  {
    // get document builder factory
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //factory.setValidating(true);                       // default is false
    //factory.setNamespaceAware(true);                   // default is false
    factory.setIgnoringElementContentWhitespace(true); // default is false
    factory.setIgnoringComments(true);                 // default is false
    //factory.setCoalescing(true);                       // default is false
    factory.setExpandEntityReferences(true);           // default is true

    byte [] baXML = read(is);

    try 
    {
      DocumentBuilder builder = factory.newDocumentBuilder();
      builder.setErrorHandler(new ConfErrorHandler());

      this.document = builder.parse( new ByteArrayInputStream(baXML) );

    } 
    catch (SAXParseException spe) 
    {
      // Error generated by the parser
      throw new ConfigurationException("\n" + new String(baXML), spe);
    } 
    catch (SAXException sxe) 
    {
      // Error generated by this application
      // (or a parser-initialization error)
      throw new ConfigurationException("\n" + new String(baXML), sxe);
    } 
    catch (ParserConfigurationException pce) 
    {
      // Parser with specified options can't be built
      throw new ConfigurationException("\n" + new String(baXML), pce);
    } 
    catch (IOException ioe) 
    {
      // I/O error
      throw new ConfigurationException("\n" + new String(baXML), ioe);
    }
  }

  /**
   * 파싱 실패했을 경우 디버그 위한 용도로 사용
   * @param is
   * @return ByteArrayOutputStream
   */
  private byte[] read(InputStream is) {
    ByteArrayOutputStream os = null;

    try {
      os = new ByteArrayOutputStream();

      byte[] buffer = new byte[1024];
      int length = -1;

      while ((length = is.read(buffer)) != -1) {
        os.write(buffer, 0, length);
      }
    } catch(IOException ioe) {
      // ignore
    } finally {
      try {
        if( os != null ) os.close();
      } catch(IOException ioe) {
        // ignore
      }
    }

    return os.toByteArray();
  }


  // Error handler to report errors and warnings
  private static class ConfErrorHandler implements ErrorHandler {
    /*
    * Returns a string describing parse exception details
     */
    private String getParseExceptionInfo(SAXParseException spe) {
      String systemId = spe.getSystemId();
      if (systemId == null) {
        systemId = "null";
      }
      String info = "URI=" + systemId +
                    " Line=" + spe.getLineNumber() +
                    ": " + spe.getMessage();
      return info;
    }

    // The following methods are standard SAX ErrorHandler methods.
    // See SAX documentation for more info.

    public void warning(SAXParseException spe) throws SAXException {
      System.out.println("Warning: " + getParseExceptionInfo(spe));
    }

    public void error(SAXParseException spe) throws SAXException {
      String message = "Error: " + getParseExceptionInfo(spe);
      throw new SAXException(message);
    }

    public void fatalError(SAXParseException spe) throws SAXException {
      String message = "Fatal Error: " + getParseExceptionInfo(spe);
      throw new SAXException(message);
    }
  }

}