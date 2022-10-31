package models;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FileModelXmlImpl implements FileModel {

  private DocumentBuilder builder;
  private Document document;

  public FileModelXmlImpl() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      this.builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void readFile(String pathToFile) {
    try {
      this.document = builder.parse(new File(pathToFile));
    } catch (SAXException | IOException e) {
      throw new RuntimeException(e);
    }
    document.getDocumentElement().normalize();
  }

  @Override
  public void writeFile(String pathToFile) {
    try {
      Transformer tr = TransformerFactory.newInstance().newTransformer();
      tr.setOutputProperty(OutputKeys.INDENT, "yes");
      tr.setOutputProperty(OutputKeys.METHOD, "xml");
      tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      tr.transform(new DOMSource(document),
              new StreamResult(new FileOutputStream(pathToFile)));
    } catch (TransformerException | IOException te) {
      System.out.println(te.getMessage());
    }
  }

  public Document getDocument() {
    return this.document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }
}
