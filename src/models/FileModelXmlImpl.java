package models;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
  public void writeFile(String pathToFile) {}

  public Document getDocument() {
    return this.document;
  }
}
